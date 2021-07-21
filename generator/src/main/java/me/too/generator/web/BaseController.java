package me.too.generator.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BaseController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";
	
	public static final String CONTENT_TYPE_JSON="application/JSON";
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object handleException(HttpServletRequest request, Exception ex) {
		return CommonReturnType.create(ex.getMessage(), "fail");
	}

}
