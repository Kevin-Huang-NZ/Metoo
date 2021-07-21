package [=servicePkg];

import [=modelPkg].[=modelName];
import me.too.scaffold.core.model.User;
import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;
import me.too.scaffold.error.BusinessException;

public interface [=serviceName] {
	Page<[=modelName]> selectPaged(CommonRequestParam crp, [=modelName] criteria) throws BusinessException;
	
	[=modelName] selectById(Long id);

	int save([=modelName] [=lcTableName], User loginUser) throws BusinessException;

	int update([=modelName] [=lcTableName], User loginUser) throws BusinessException;

	int delete(Long id, User loginUser) throws BusinessException;
	
	<#if hasUniqueKey >
	[=modelName] selectByUk(<#list ukFields as field>[=field.javaDataType] [=field.lcColumnName]<#sep>, </#sep></#list>);
	</#if>
}
