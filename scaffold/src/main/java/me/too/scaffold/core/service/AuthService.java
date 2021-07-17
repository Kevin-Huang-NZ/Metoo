package me.too.scaffold.core.service;

import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;

/**
 * @author huanghao
 *
 */
public interface AuthService {

	boolean checkAuth(String roles, String action);

	User userLogin(String phone, String password) throws BusinessException;
	
	int userChgPwd(Long loginId, String oldPwd, String newPwd) throws BusinessException;
}
