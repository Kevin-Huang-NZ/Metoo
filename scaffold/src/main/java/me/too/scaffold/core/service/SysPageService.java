package me.too.scaffold.core.service;

import me.too.scaffold.core.model.SysPage;
import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;

import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;

public interface SysPageService {
	Page<SysPage> selectPaged(CommonRequestParam pp, SysPage criteria) throws BusinessException;
	
	SysPage selectById(Long id);

	int save(SysPage sysPage, User loginUser) throws BusinessException;

	int update(SysPage sysPage, User loginUser) throws BusinessException;

	int delete(Long id, User loginUser) throws BusinessException;
}
