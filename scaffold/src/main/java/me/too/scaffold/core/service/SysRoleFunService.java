package me.too.scaffold.core.service;

import java.util.List;
import java.util.Map;

import me.too.scaffold.core.model.SysRoleFun;
import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;

public interface SysRoleFunService {
	List<SysRoleFun> selectByRoleNo(String roleNo) throws BusinessException;
	
	int save(SysRoleFun sysRoleFun, User loginUser) throws BusinessException;

	int deleteByNo(SysRoleFun sysRoleFun, User loginUser) throws BusinessException;

	Map<String, String> getAndCacheRoleFuns();
}
