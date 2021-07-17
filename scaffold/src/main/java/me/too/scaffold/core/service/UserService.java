package me.too.scaffold.core.service;

import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;

import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;

public interface UserService {
	Page<User> selectPaged(CommonRequestParam pp, User criteria) throws BusinessException;
	
	User selectById(Long id);

	int save(User record, User loginUser) throws BusinessException;

	int update(User record, User loginUser) throws BusinessException;

	int delete(Long id, User loginUser) throws BusinessException;
}
