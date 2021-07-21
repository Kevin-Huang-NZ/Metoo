package [=modelPkg];

import java.math.BigDecimal;
import java.sql.Timestamp;

public class [=modelName] {
	private Long id;
	<#list fields as field>
	private [=field.javaDataType] [=field.lcColumnName];
	</#list>

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	<#list fields as field>
	public [=field.javaDataType] get[=field.ucColumnName]() {
		return this.[=field.lcColumnName];
	}
	public void set[=field.ucColumnName]([=field.javaDataType] [=field.lcColumnName]) {
		<#if field.javaDataType == 'String' >
		this.[=field.lcColumnName] = [=field.lcColumnName] == null ? null : [=field.lcColumnName].trim();
		<#else>
		this.[=field.lcColumnName] = [=field.lcColumnName];
		</#if>
	}
	</#list>
}