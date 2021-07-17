package me.too.scaffold.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

import me.too.scaffold.GlobalConst;
import me.too.scaffold.core.dao.SysRoleFunMapper;
import me.too.scaffold.core.model.SysRoleFun;
import me.too.scaffold.core.model.SysRoleFunIJFun;
import me.too.scaffold.core.service.SysRoleFunService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.core.model.User;

@Service
public class SysRoleFunServiceImpl implements SysRoleFunService {
	Logger logger = LoggerFactory.getLogger(SysRoleFunService.class);

	public static final String roleFunsCacheKey = GlobalConst.CACHE_KEY_SYS_ROLE_FUNS;
	
	@Autowired
	private SysRoleFunMapper mapper;
	
	@Override
	public List<SysRoleFun> selectByRoleNo(String roleNo) throws BusinessException {
		if (StringUtils.isEmpty(roleNo)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}

		return mapper.selectByRoleNo(roleNo);
	}

	@Override
	@CacheEvict(value=GlobalConst.CACHE_NAME_SYS, key="#root.target.roleFunsCacheKey")
	public int save(SysRoleFun sysRoleFun, User loginUser) throws BusinessException {
//		if (sysRoleFun == null) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//		}
//		if (StringUtils.isEmpty(sysRoleFun.getRoleNo()) || StringUtils.isEmpty(sysRoleFun.getFunNo())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//		}
		List<SysRoleFun> checkConflict = mapper.selectByNo(sysRoleFun.getRoleNo(), sysRoleFun.getFunNo());
		if (checkConflict != null && checkConflict.size() > 0) {
			return 0;
		}
		return mapper.insert(sysRoleFun);
	}
	@Override
	@CacheEvict(value=GlobalConst.CACHE_NAME_SYS, key="#root.target.roleFunsCacheKey")
	public int deleteByNo(SysRoleFun sysRoleFun, User loginUser) throws BusinessException {
//		if (sysRoleFun == null) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//		}
//		if (StringUtils.isEmpty(sysRoleFun.getRoleNo()) || StringUtils.isEmpty(sysRoleFun.getFunNo())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//		}
		return mapper.deleteByNo(sysRoleFun.getRoleNo(), sysRoleFun.getFunNo());
	}

	
	@Override
	@Cacheable(value=GlobalConst.CACHE_NAME_SYS, key="#root.target.roleFunsCacheKey")
	public Map<String, String> getAndCacheRoleFuns() {
		Map<String, String> roleFuns = new HashMap<String, String>();

		Map<String, List<String>> tmpMap = new HashMap<String, List<String>>();
		List<SysRoleFunIJFun> lsRoleFun = mapper.loadSysRoleFun();
		if (lsRoleFun != null && lsRoleFun.size() > 0) {
			for (SysRoleFunIJFun one : lsRoleFun) {
				List<String> oneRoleFuns = tmpMap.get(one.getRoleNo());
				if (oneRoleFuns == null) {
					oneRoleFuns = new ArrayList<String>();
					tmpMap.put(one.getRoleNo(), oneRoleFuns);
				}
				oneRoleFuns.add("'" + one.getFunNo() + "'");
			}

			for (String roleNo : tmpMap.keySet()) {
				roleFuns.put(roleNo, String.join(";", tmpMap.get(roleNo)));
			}
		}

		return roleFuns;
	}
}

