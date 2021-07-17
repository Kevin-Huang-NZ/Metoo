package me.too.scaffold.core.service;

import me.too.scaffold.core.model.SysFun;
import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;

import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;

public interface SysFunService {
	Page<SysFun> selectPaged(CommonRequestParam crp, SysFun criteria) throws BusinessException;
	
	SysFun selectById(Long id);

	int save(SysFun sysFun, User loginUser) throws BusinessException;

	int update(SysFun sysFun, User loginUser) throws BusinessException;

	int delete(Long id, User loginUser) throws BusinessException;
}
