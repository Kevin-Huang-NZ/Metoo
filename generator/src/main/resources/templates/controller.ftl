package [=controllerPkg];

import static me.too.scaffold.util.CheckUtil.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import [=modelPkg].[=modelName];
import [=servicePkg].[=serviceName];
import com.github.pagehelper.Page;

import me.too.scaffold.web.controller.BaseController;
import me.too.scaffold.web.param.CommonRequestParam;
import me.too.scaffold.web.param.IdWrap;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.web.response.ListData;
import me.too.scaffold.web.response.PaginationInfo;
import me.too.scaffold.web.response.Root;

@RestController
@RequestMapping("/api/[=lowerTableName]")
public class [=controllerName] extends BaseController {

	Logger logger = LoggerFactory.getLogger([=controllerName].class);

	@Autowired
	private [=serviceName] service;
	
//	private final String[] ADOPTION_STATUS = {"1", "2"};
	
	@GetMapping(value = { "/l" })
	public Root list(CommonRequestParam crp, [=modelName] criteria) throws BusinessException {

		Page<[=modelName]> searchResult = service.selectPaged(crp, criteria);

		ListData<[=modelName]> wrapper = new ListData<[=modelName]>();
		wrapper.setDataList(searchResult.getResult()); 

		PaginationInfo paginationInfo = convertPage(searchResult);
		wrapper.setPagination(paginationInfo);

		return Root.create(wrapper);
	}

	@GetMapping(value = { "/r" })
	public Root read(Long id) throws BusinessException {

		[=modelName] [=lcTableName] = service.selectById(id);
		if ([=lcTableName] == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}

		return Root.create([=lcTableName]);
	}
	
	@PostMapping(value = { "/c" })
	public Root create(@RequestBody [=modelName] [=lcTableName]) throws BusinessException {
		this.checkCreate([=lcTableName]);

		int result = service.save([=lcTableName], authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/u" })
	public Root update(@RequestBody [=modelName] [=lcTableName]) throws BusinessException {
		this.checkUpdate([=lcTableName]);

		int result = service.update([=lcTableName], authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/d" })
	public Root delete(@RequestBody IdWrap id) throws BusinessException {
		int result = service.delete(id.getId(), authContext.get());
		return Root.create(result);
	}
	
	private void checkCreate([=modelName] [=lcTableName]) throws BusinessException {
		if ([=lcTableName] == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
		}
<#list fields as field>
<#if field.isNullable == 'NO' >
<#if field.javaDataType == 'String' >
		if (StringUtils.isEmpty([=lcTableName].get[=field.ucColumnName]())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"[=field.columnTitle]不能为空。");
		}
<#else>
		if ([=lcTableName].get[=field.ucColumnName]() == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"[=field.columnTitle]不能为空。");
		}
</#if>
</#if>
<#if field.javaDataType == 'String' >
<#if field.characterMaximumLength?has_content >
		if (overLength([=lcTableName].get[=field.ucColumnName](), [=field.characterMaximumLength])) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"[=field.columnTitle]超长，最多[=field.characterMaximumLength]个字符。");
		}
</#if>
</#if>
</#list>
        
//		if (!Arrays.asList(ADOPTION_STATUS).contains([=lcTableName].getAdoptionStatus())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"采纳状态参数非法。");
//		}
//		if (!patternCheck(PATTERN_MOBILE, [=lcTableName].getPhone())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请填写正确格式的手机号。");
//		}
	}

	
	private void checkUpdate([=modelName] [=lcTableName]) throws BusinessException {
		if ([=lcTableName] == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
		}
		
<#list fields as field>
        if ([=lcTableName].get[=field.ucColumnName]() != null) {
<#if field.isNullable == 'NO' >
<#if field.javaDataType == 'String' >
			if (StringUtils.isEmpty([=lcTableName].get[=field.ucColumnName]())) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"[=field.columnTitle]不能为空。");
			}
<#else>
			if ([=lcTableName].get[=field.ucColumnName]() == null) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"[=field.columnTitle]不能为空。");
			}
</#if>
</#if>
<#if field.javaDataType == 'String' >
<#if field.characterMaximumLength?has_content >
			if (overLength([=lcTableName].get[=field.ucColumnName](), [=field.characterMaximumLength])) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"[=field.columnTitle]超长，最多[=field.characterMaximumLength]个字符。");
			}
</#if>
</#if>
		}
</#list>
	}
}
