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

import me.too.scaffold.core.model.SysPage;
import me.too.scaffold.core.service.SysPageService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;

import com.github.pagehelper.Page;

import me.too.scaffold.web.param.CommonRequestParam;
import me.too.scaffold.web.param.IdWrap;
import me.too.scaffold.web.response.ListData;
import me.too.scaffold.web.response.PaginationInfo;
import me.too.scaffold.web.response.Root;

@RestController
@RequestMapping("/api/syspage")
public class SysPageController extends BaseController {

	Logger logger = LoggerFactory.getLogger(SysPageController.class);

	@Autowired
	private SysPageService sysPageService;
	
	@GetMapping(value = { "/l" })
	public Root list(CommonRequestParam crp, SysPage criteria) throws BusinessException {

		Page<SysPage> searchResult = this.sysPageService.selectPaged(crp, criteria);

		ListData<SysPage> wrapper = new ListData<SysPage>();
		wrapper.setDataList(searchResult.getResult()); 

		PaginationInfo paginationInfo = convertPage(searchResult);
		wrapper.setPagination(paginationInfo);

		return Root.create(wrapper);
	}

	@GetMapping(value = { "/r" })
	public Root read(Long id) throws BusinessException {

		SysPage sysPage = this.sysPageService.selectById(id);
		if (sysPage == null) {
			throw new BusinessException(EmBusinessError.DATA_NOT_EXIST);
		}

		return Root.create(sysPage);
	}
	
	@PostMapping(value = { "/c" })
	public Root create(@RequestBody SysPage sysPage) throws BusinessException {
		this.checkCreate(sysPage);

		int result = this.sysPageService.save(sysPage, authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/u" })
	public Root update(@RequestBody SysPage sysPage) throws BusinessException {
		this.checkCreate(sysPage);

		int result = this.sysPageService.update(sysPage, authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/d" })
	public Root delete(@RequestBody IdWrap id) throws BusinessException {
		int result = this.sysPageService.delete(id.getId(), authContext.get());
		return Root.create(result);
	}
	
	private void checkCreate(SysPage sysPage) throws BusinessException {
		if (sysPage == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
		}
		
		if (isEmpty(sysPage.getPageName())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"名称不能为空。");
		}
		if (overLength(sysPage.getPageName(), 50)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"名称超长，最多50个字符。");
		}
		
		if (isEmpty(sysPage.getPageTitle())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"标题不能为空。");
		}
		if (overLength(sysPage.getPageTitle(), 50)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"标题超长，最多50个字符。");
		}
		if (overLength(sysPage.getMemo(), 500)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"备注超长，最多500个字符。");
		}
//		if (!patternCheck(PATTERN_MOBILE, sysPage.getPhone())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请填写正确格式的手机号。");
//		}
	}

	
//	private void checkUpdate(SysPage sysPage) throws BusinessException {
//		if (sysPage == null) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
//		}
////		if (sysPage.getUserName() != null) {
////			if (isEmpty(sysPage.getUserName())) {
////				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名不能为空。");
////			}
////			if (overLength(sysPage.getUserName(), 50)) {
////				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名超长，最多50个字符。");
////			}
////		}
//	}
}
