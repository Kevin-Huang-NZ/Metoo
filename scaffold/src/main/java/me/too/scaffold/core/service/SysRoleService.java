package me.too.scaffold.core.service;

import me.too.scaffold.core.model.SysRole;
import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;

import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;

public interface SysRoleService {
	Page<SysRole> selectPaged(CommonRequestParam crp, SysRole criteria) throws BusinessException;
	
	SysRole selectById(Long id);

	int save(SysRole sysRole, User loginUser) throws BusinessException;

	int update(SysRole sysRole, User loginUser) throws BusinessException;

	int delete(Long id, User loginUser) throws BusinessException;
	
	SysRole selectByRoleNo(String roleNo);
}
