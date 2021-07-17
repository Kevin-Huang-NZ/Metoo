package me.too.scaffold.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import me.too.scaffold.core.model.User;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;

import com.github.pagehelper.Page;

import me.too.scaffold.GlobalConst;
import me.too.scaffold.web.response.ErrorInfo;
import me.too.scaffold.web.response.PaginationInfo;
import me.too.scaffold.web.response.Root;

public class BaseController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

	public static final String CONTENT_TYPE_JSON = "application/json";

	ThreadLocal<User> authContext = new ThreadLocal<>();

	@ModelAttribute
	public void initUser(HttpServletRequest request) {
		Object obj = request.getAttribute(GlobalConst.SESSION_LOGIN_USER);
		if (obj instanceof User) {
			User loginUser = (User) obj;
			authContext.set(loginUser);
		}
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object handleException(HttpServletRequest request, Exception ex) {
		ErrorInfo errorInfo = new ErrorInfo();
		if (ex instanceof BusinessException) {
			BusinessException bex = (BusinessException) ex;
			errorInfo.setErrCode(bex.getErrCode());
			errorInfo.setErrMsg(bex.getErrMsg());
		} else {
			logger.error(EmBusinessError.UNKNOWN_ERROR.getErrCode() + ":" + EmBusinessError.UNKNOWN_ERROR.getErrMsg(), ex);
			errorInfo.setErrCode(EmBusinessError.UNKNOWN_ERROR.getErrCode());
			errorInfo.setErrMsg(EmBusinessError.UNKNOWN_ERROR.getErrMsg());
		}
		return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
	}

	public PaginationInfo convertPage(Page<?> src) throws BusinessException {
		if (src == null) {
			return null;
		}
		PaginationInfo vo = new PaginationInfo();
		try {
			BeanUtils.copyProperties(vo, src);
		} catch (Exception e) {
			logger.error("类型转换错误。", e);
			throw new BusinessException(EmBusinessError.DATA_TYPE_CAST_ERROR);
		}

		return vo;
	}
}
