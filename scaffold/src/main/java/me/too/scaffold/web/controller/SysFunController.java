package me.too.scaffold.web.controller;

import static me.too.scaffold.util.CheckUtil.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.too.scaffold.core.model.SysFun;
import me.too.scaffold.core.service.SysFunService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;

import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;
import me.too.scaffold.web.param.IdWrap;
import me.too.scaffold.web.response.ListData;
import me.too.scaffold.web.response.PaginationInfo;
import me.too.scaffold.web.response.Root;

@RestController
@RequestMapping("/api/sysfun")
public class SysFunController extends BaseController {

	Logger logger = LoggerFactory.getLogger(SysFunController.class);

	@Autowired
	private SysFunService sysFunService;
	
	@GetMapping(value = { "/l" })
	public Root list(CommonRequestParam crp, SysFun criteria) throws BusinessException {

		Page<SysFun> searchResult = this.sysFunService.selectPaged(crp, criteria);

		ListData<SysFun> wrapper = new ListData<SysFun>();
		wrapper.setDataList(searchResult.getResult()); 

		PaginationInfo paginationInfo = convertPage(searchResult);
		wrapper.setPagination(paginationInfo);

		return Root.create(wrapper);
	}

	@GetMapping(value = { "/r" })
	public Root read(Long id) throws BusinessException {

		SysFun sysFun = this.sysFunService.selectById(id);
		if (sysFun == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}

		return Root.create(sysFun);
	}
	
	@PostMapping(value = { "/c" })
	public Root create(@RequestBody SysFun sysFun) throws BusinessException {
		this.checkCreate(sysFun);

		int result = this.sysFunService.save(sysFun, authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/u" })
	public Root update(@RequestBody SysFun sysFun) throws BusinessException {
		this.checkCreate(sysFun);

		int result = this.sysFunService.update(sysFun, authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/d" })
	public Root delete(@RequestBody IdWrap id) throws BusinessException {
		int result = this.sysFunService.delete(id.getId(), authContext.get());
		return Root.create(result);
	}
	
	private void checkCreate(SysFun sysFun) throws BusinessException {
		if (sysFun == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
		}
		
		if (isEmpty(sysFun.getPageName())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"页面不能为空。");
		}

		if (!patternCheck("[a-zA-Z0-9]{1,30}", sysFun.getActionNo())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请填写正确格式的动作编号,动作编号由1~30位字母或数字组成。");
		}
		
		if (isEmpty(sysFun.getActionName())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"名称不能为空。");
		}
		if (overLength(sysFun.getActionName(), 30)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"名称超长，最多50个字符。");
		}
		
		if (overLength(sysFun.getMemo(), 500)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"备注超长，最多500个字符。");
		}
		sysFun.setFunNo(sysFun.getPageName() + "/" + sysFun.getActionNo());
//		if (!patternCheck(PATTERN_MOBILE, sysFun.getPhone())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请填写正确格式的手机号。");
//		}
	}

	
//	private void checkUpdate(SysFun sysFun) throws BusinessException {
//		if (sysFun == null) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
//		}
////		if (sysFun.getUserName() != null) {
////			if (isEmpty(sysFun.getUserName())) {
////				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名不能为空。");
////			}
////			if (overLength(sysFun.getUserName(), 50)) {
////				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名超长，最多50个字符。");
////			}
////		}
//	}
}
