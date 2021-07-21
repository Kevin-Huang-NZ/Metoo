package me.too.generator.service;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

public class NameConverter {
	public static String lowerCamel(String lowerUnderscore) {
		if (StringUtils.isEmpty(lowerUnderscore)) {
			return "";
		}
		
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, lowerUnderscore);
	}
	
	public static String upperCamel(String lowerUnderscore) {
		if (StringUtils.isEmpty(lowerUnderscore)) {
			return "";
		}
		
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, lowerUnderscore);
	}
	
	public static String lowerHyphen(String lowerUnderscore) {
		if (StringUtils.isEmpty(lowerUnderscore)) {
			return "";
		}
		
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, lowerUnderscore);
	}
	
	public static String lowerCase(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		
		return str.toLowerCase();
	}
	
	public static String upperCase(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		
		return str.toUpperCase();
	}
	
	public static String getMapperName(String ucName) {
		if (StringUtils.isEmpty(ucName)) {
			return "Mapper";
		}
		return ucName + "Mapper";
	}
	
	public static String getServiceName(String ucName) {
		if (StringUtils.isEmpty(ucName)) {
			return "Service";
		}
		return ucName + "Service";
	}
	
	public static String getServiceImplName(String ucName) {
		if (StringUtils.isEmpty(ucName)) {
			return "ServiceImpl";
		}
		return ucName + "ServiceImpl";
	}
	
	public static String getControllerName(String ucName) {
		if (StringUtils.isEmpty(ucName)) {
			return "Controller";
		}
		return ucName + "Controller";
	}
	
	public static String getColumnNameWithPrefix(String columnName) {
		if (StringUtils.isEmpty(columnName)) {
			return "t_";
		}
		return "t_" + columnName;
	}
}
