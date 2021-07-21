package me.too.generator.service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DataTypeMapper {
	private Map<String, String> dataTypeMap = Stream.of(new String[][] { 
		{ "char", "String" }, 
		{ "varchar", "String" }, 
		{ "bigint", "Long" }, 
		{ "int", "Integer" }, 
		{ "decimal", "BigDecimal" }, 
		{ "timestamp", "Timestamp" }
	}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	public String getDataType(String jdbcType) {
		if (StringUtils.isEmpty(jdbcType)) {
			return null;
		}
		return dataTypeMap.get(jdbcType);
	}
}
