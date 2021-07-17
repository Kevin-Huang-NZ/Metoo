package me.too.scaffold.core.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.too.scaffold.core.dao.UserMapper;
import me.too.scaffold.core.model.User;
import me.too.scaffold.core.service.AuthService;
import me.too.scaffold.core.service.SysRoleFunService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;

@Service
public class AuthServiceImpl implements AuthService {
	Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private SysRoleFunService sysRoleFunService;

	@Override
	public boolean checkAuth(String roles, String action) {
		if (StringUtils.isEmpty(roles)) {
			return false;
		}
		Map<String, String> roleFuns = sysRoleFunService.getAndCacheRoleFuns();
		if (roleFuns == null) {
			return false;
		}
		
		String[] roleArr = roles.split(";");
		
		boolean hasAuth = false;
		for (String role : roleArr) {
			String funs = roleFuns.get(role);
			if (!StringUtils.isEmpty(funs)) {
				if (StringUtils.isEmpty(action)) {
					hasAuth = true;
					break;
				} else {
					if (funs.indexOf("'" + action + "'") != -1) {
						hasAuth = true;
						break;
					}
				}
			}
		}
		
		return hasAuth;
	}
	
	@Override
	public User userLogin(String phone, String password) throws BusinessException {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}

		User user = userMapper.selectByPhone(phone, null);
		if (user == null) {
			return null;
		}

		if (StringUtils.equals(password, user.getPassword()) && StringUtils.equals("1", user.getStatus())) {
			return user;
		} else {
			return null;
		}
	}
	
	@Override
	public int userChgPwd(Long loginId, String oldPwd, String newPwd) throws BusinessException {
		User user = userMapper.selectByPrimaryKey(loginId);
		
		if (user == null ) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST, "用户不存在。");
		}
		
		if (!StringUtils.equals(user.getPassword(), oldPwd)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "原密码错误。");
		}
		user.setPassword(newPwd);

		return userMapper.updateByPrimaryKey(user);
	}

}
