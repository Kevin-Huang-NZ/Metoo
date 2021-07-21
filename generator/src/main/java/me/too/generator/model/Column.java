package me.too.generator.model;

import lombok.Data;

@Data
public class Column {
	private Integer ordinalPosition;
	private String columnName;
	private String dataType;
	private String isNullable;
	private String columnDefault;
	private Long characterMaximumLength;
	private Long characterOctetLength;
	private Long numericPrecision;
	private Long numericScale;
	private Integer datetimePrecision;
	// "" "PRI" "UNI" "MUL"
	private String columnKey;
	private String extra;
	private String columnComment;
}
