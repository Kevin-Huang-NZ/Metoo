package me.too.scaffold.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import me.too.scaffold.core.dao.SysFunMapper;
import me.too.scaffold.core.dao.SysPageMapper;
import me.too.scaffold.core.dao.SysRoleFunMapper;
import me.too.scaffold.core.model.SysPage;
import me.too.scaffold.core.service.SysPageService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.core.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import me.too.scaffold.web.param.CommonRequestParam;

@Service
public class SysPageServiceImpl implements SysPageService {
	Logger logger = LoggerFactory.getLogger(SysPageService.class);
	private final String defaultOrderBy = "t_id desc";

	@Autowired
	private SysPageMapper mapper;
	@Autowired
	private SysFunMapper sysFunMapper;
	@Autowired
	private SysRoleFunMapper sysRoleFunMapper;
	
	@Override
	public Page<SysPage> selectPaged(CommonRequestParam crp, SysPage qp) throws BusinessException {
		if (crp == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		Map<String, Object> criteria = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(crp.getOrder())) {
			criteria.put("orderByClause", crp.getOrder() + "," + defaultOrderBy);
		} else {
			criteria.put("orderByClause", defaultOrderBy);
		}
		if (qp != null) {
			if (!StringUtils.isEmpty(qp.getPageName())) {
				criteria.put("pageName", qp.getPageName() + "%");
			}
			if (!StringUtils.isEmpty(qp.getPageTitle())) {
				criteria.put("pageTitle", qp.getPageTitle() + "%");
			}
		}

		Page<SysPage> resultList = null;
		if (crp.getPageNum() != -1) {
			PageHelper.startPage(crp.getPageNum(), crp.getPageSize());
			resultList = mapper.selectPaged(criteria);
		} else {
			resultList = mapper.selectPaged(criteria);
		}
		return resultList;
	}

	@Override
	public SysPage selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int save(SysPage sysPage, User loginUser) throws BusinessException {
		SysPage checkConflict = mapper.selectByPageName(sysPage.getPageName(), null);
		if (checkConflict != null) {
			throw new BusinessException(EmBusinessError.DATA_CONFLICT, "模块名称重复。");
		}
		return mapper.insert(sysPage);
	}

	@Override
	public int update(SysPage sysPage, User loginUser) throws BusinessException {
		SysPage checkExist = mapper.selectByPrimaryKey(sysPage.getId());
		if (checkExist == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}
		
//		if (sysPage.getPhone() != null) {
//			SysPage checkConflict = mapper.selectByPhone(sysPage.getPhone(), sysPage.getId());
//			if (checkConflict != null) {
//				throw new BusinessException(EmBusinessError.DATA_CONFLICT, "手机号重复。");
//			}
//		}
		return mapper.updateByPrimaryKeySelective(sysPage);
	}

	@Transactional
	@Override
	public int delete(Long id, User loginUser) throws BusinessException {
		SysPage checkExist = mapper.selectByPrimaryKey(id);
		if (checkExist == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}
		this.sysFunMapper.deleteByPageName(checkExist.getPageName());
		this.sysRoleFunMapper.deleteByPageName(checkExist.getPageName()+"/%");
		return mapper.deleteByPrimaryKey(id);
	}

}

