package me.too.scaffold.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import me.too.scaffold.core.dao.UserMapper;
import me.too.scaffold.core.model.User;
import me.too.scaffold.core.service.UserService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import me.too.scaffold.web.param.CommonRequestParam;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	private final String defaultOrderBy = "t_id desc";

	@Autowired
	private UserMapper mapper;


	@Override
	public Page<User> selectPaged(CommonRequestParam crp, User qp) throws BusinessException {
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
			if (!StringUtils.isEmpty(qp.getPhone())) {
				criteria.put("phone", qp.getPhone() + "%");
			}
			if (!StringUtils.isEmpty(qp.getUserName())) {
				criteria.put("userName", qp.getUserName() + "%");
			}
			if (!StringUtils.isEmpty(qp.getRoles())) {
				criteria.put("roles",  "%" + qp.getRoles() + "%");
			}
		}
		Page<User> resultList = null;
		if (crp.getPageNum() != -1) {
			PageHelper.startPage(crp.getPageNum(), crp.getPageSize());
			resultList = mapper.selectPaged(criteria);
		} else {
			resultList = mapper.selectPaged(criteria);
		}
		return resultList;
	}

	@Override
	public User selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int save(User record, User loginUser) throws BusinessException {
		User checkConflict = mapper.selectByPhone(record.getPhone(), null);
		if (checkConflict != null) {
			throw new BusinessException(EmBusinessError.DATA_CONFLICT, "手机号重复。");
		}
		return mapper.insert(record);
	}

	@Override
	public int update(User record, User loginUser) throws BusinessException {
		User checkExist = mapper.selectByPrimaryKey(record.getId());
		if (checkExist == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}
		
		if (record.getPhone() != null) {
			User checkConflict = mapper.selectByPhone(record.getPhone(), record.getId());
			if (checkConflict != null) {
				throw new BusinessException(EmBusinessError.DATA_CONFLICT, "手机号重复。");
			}
		}
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Long id, User loginUser) throws BusinessException {
		User checkExist = mapper.selectByPrimaryKey(id);
		if (checkExist == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}
		return mapper.deleteByPrimaryKey(id);
	}

}
