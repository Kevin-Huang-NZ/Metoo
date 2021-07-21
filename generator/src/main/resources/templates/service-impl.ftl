package [=serviceImplPkg];

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import [=mapperPkg].[=mapperName];
import [=modelPkg].[=modelName];
import [=servicePkg].[=serviceName];
import me.too.scaffold.core.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import me.too.scaffold.web.param.CommonRequestParam;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;

@Service
public class [=serviceImplName] implements [=serviceName] {
	Logger logger = LoggerFactory.getLogger([=serviceImplName].class);
	private final String defaultOrderBy = "t_id desc";

	@Autowired
	private [=mapperName] mapper;
	
	@Override
	public Page<[=modelName]> selectPaged(CommonRequestParam crp, [=modelName] qp) throws BusinessException {
		if (crp == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		Map<String, Object> criteria = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(crp.getOrder())) {
			criteria.put("orderByClause", crp.getOrder() + "," + defaultOrderBy);
		} else {
			criteria.put("orderByClause", defaultOrderBy);
		}
		if (qp != null) {
			<#list fields as field>
			<#if field.javaDataType == 'String' >
			if (!StringUtils.isEmpty(qp.get[=field.ucColumnName]())) {
				criteria.put("[=field.lcColumnName]", qp.get[=field.ucColumnName]());
			}
			<#else>
			if (qp.get[=field.ucColumnName]() != null) {
				criteria.put("[=field.lcColumnName]", qp.get[=field.ucColumnName]());
			}
			</#if>
			</#list>
		}
		
		Page<[=modelName]> resultList = null;
		if (crp.getPageNum() != -1) {
			PageHelper.startPage(crp.getPageNum(), crp.getPageSize());
			resultList = mapper.selectPaged(criteria);
		} else {
			resultList = mapper.selectPaged(criteria);
		}
		return resultList;
	}

	@Override
	public [=modelName] selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int save([=modelName] [=lcTableName], User loginUser) throws BusinessException {
		<#if hasUniqueKey >
		[=modelName] checkConflict = mapper.selectByUniqueKey(<#list ukFields as field>[=lcTableName].get[=field.ucColumnName]()<#sep>, </#sep></#list>, null);
		if (checkConflict != null) {
			throw new BusinessException(EmBusinessError.DATA_CONFLICT);
		}
		</#if>
		return mapper.insert([=lcTableName]);
	}

	@Override
	public int update([=modelName] [=lcTableName], User loginUser) throws BusinessException {
		[=modelName] checkExist = mapper.selectByPrimaryKey([=lcTableName].getId());
		if (checkExist == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}

		<#if hasUniqueKey >
		[=modelName] checkConflict = mapper.selectByUniqueKey(<#list ukFields as field>[=lcTableName].get[=field.ucColumnName]()<#sep>, </#sep></#list>, [=lcTableName].getId());
		if (checkConflict != null) {
			throw new BusinessException(EmBusinessError.DATA_CONFLICT);
		}
		</#if>
		return mapper.updateByPrimaryKeySelective([=lcTableName]);
	}

	@Override
	public int delete(Long id, User loginUser) throws BusinessException {
		[=modelName] checkExist = mapper.selectByPrimaryKey(id);
		if (checkExist == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}
		return mapper.deleteByPrimaryKey(id);
	}

	<#if hasUniqueKey >
	public [=modelName] selectByUk(<#list ukFields as field>[=field.javaDataType] [=field.lcColumnName]<#sep>, </#sep></#list>) {
		return mapper.selectByUniqueKey(<#list ukFields as field>[=field.javaDataType] [=field.lcColumnName]<#sep>, </#sep></#list>, null);
	}
	</#if>
}

