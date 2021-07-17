package me.too.scaffold.web.controller;

import static me.too.scaffold.util.CheckUtil.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.too.scaffold.core.model.SysRoleFun;
import me.too.scaffold.core.service.SysRoleFunService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.web.response.ListData;
import me.too.scaffold.web.response.Root;

@RestController
@RequestMapping("/api/sysrolefun")
public class SysRoleFunController extends BaseController {

	Logger logger = LoggerFactory.getLogger(SysRoleFunController.class);

	@Autowired
	private SysRoleFunService sysRoleFunService;
	
	@GetMapping(value = { "/l" })
	public Root list(String roleNo) throws BusinessException {

		List<SysRoleFun> searchResult = this.sysRoleFunService.selectByRoleNo(roleNo);

		ListData<SysRoleFun> wrapper = new ListData<SysRoleFun>();
		wrapper.setDataList(searchResult); 
		
		return Root.create(wrapper);
	}
	
	@PostMapping(value = { "/c" })
	public Root create(@RequestBody SysRoleFun sysRoleFun) throws BusinessException {
		this.checkCreate(sysRoleFun);

		int result = this.sysRoleFunService.save(sysRoleFun, authContext.get());

		return Root.create(result);
	}

	@PostMapping(value = { "/d" })
	public Root delete(@RequestBody SysRoleFun sysRoleFun) throws BusinessException {
		this.checkCreate(sysRoleFun);
		
		int result = this.sysRoleFunService.deleteByNo(sysRoleFun, authContext.get());
		return Root.create(result);
	}
	
	private void checkCreate(SysRoleFun sysRoleFun) throws BusinessException {
		if (sysRoleFun == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
		}
		
		if (isEmpty(sysRoleFun.getRoleNo())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色编号参数错误。");
		}
		if (overLength(sysRoleFun.getRoleNo(), 1)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色编号参数错误。");
		}
		
		if (isEmpty(sysRoleFun.getFunNo())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"功能编号参数错误。");
		}
		if (overLength(sysRoleFun.getFunNo(), 80)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"功能编号参数错误。");
		}
//		if (!patternCheck(PATTERN_MOBILE, sysRoleFun.getPhone())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请填写正确格式的手机号。");
//		}
	}

	
//	private void checkUpdate(SysRoleFun sysRoleFun) throws BusinessException {
//		if (sysRoleFun == null) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误。");
//		}
////		if (sysRoleFun.getUserName() != null) {
////			if (isEmpty(sysRoleFun.getUserName())) {
////				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名不能为空。");
////			}
////			if (overLength(sysRoleFun.getUserName(), 50)) {
////				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名超长，最多50个字符。");
////			}
////		}
//	}
}
