package [=mapperPkg];

import java.util.Map;
import org.apache.ibatis.annotations.Param;

import [=modelPkg].[=modelName];
import com.github.pagehelper.Page;

public interface [=mapperName] {
	[=modelName] selectByPrimaryKey(Long id);
	int deleteByPrimaryKey(Long id);
	int insert([=modelName] record);
	int updateByPrimaryKeySelective([=modelName] record);
	Page<[=modelName]> selectPaged(Map<String, Object> criteria);
	<#if hasUniqueKey >
	[=modelName] selectByUniqueKey(<#list ukFields as field>@Param("[=field.lcColumnName]") [=field.javaDataType] [=field.lcColumnName], </#list>@Param("notThisId") Long notThisId);
	</#if>
}