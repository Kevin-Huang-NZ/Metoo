package me.too.scaffold.web.controller;

import static me.too.scaffold.util.CheckUtil.PATTERN_PASSWORD;
import static me.too.scaffold.util.CheckUtil.patternCheck;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.too.scaffold.core.model.User;
import me.too.scaffold.core.service.AuthService;
import me.too.scaffold.core.service.UserService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.web.vo.AuthVo;
//import com.github.pagehelper.Page;

import me.too.scaffold.util.JwtUtil;
import me.too.scaffold.util.RedisUtil;
import me.too.scaffold.web.response.Root;

@RestController
@RequestMapping("/api/auth")
public class AuthorityController extends BaseController {

	Logger logger = LoggerFactory.getLogger(AuthorityController.class);

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Value("${jwt.tokenheader:x-auth-token}")
	private String tokenHeader;

	@Value("${jwt.expiration:28800}")
	private Long expiration;

//	@GetMapping(value = { "/currentUser" }, produces = { "application/json;charset=UTF-8" })
//	public Root getLoginUserFuns(HttpServletRequest request) throws BusinessException {
//		LoginUser loginUser = authContext.get();
////		if (loginUser == null) {
////			throw new BusinessException(EmBusinessError.UNAUTHORIZED);
////		}
//
//		Map<String, String> roleFuns = authService.getAndCacheRoleFuns();
//		String funs = roleFuns.get(loginUser.getLoginRole());
//		if (StringUtils.isEmpty(funs)) {
//			throw new BusinessException(EmBusinessError.FORBIDDEN);
//		}
//		String jwtToken = request.getHeader(tokenHeader);
//
//		AuthVo auth = new AuthVo();
////		auth.setIsLogin("1");
//		auth.setFuns(funs);
////		auth.setRoleNo(loginUser.getLoginRole());
//		auth.setJwtToken(jwtToken);
//		return Root.create(auth);
//	}
	
	@GetMapping(value = { "/currentUser" }, produces = { "application/json;charset=UTF-8" })
	public Root getLoginUserFuns() throws BusinessException {
		User loginUser = authContext.get();
		
		User currentUser = userService.selectById(loginUser.getId());
		
		return Root.create(currentUser);
	}

	@PostMapping(value = { "/signin" }, produces = { "application/json;charset=UTF-8" })
	public Root signIn(@RequestBody Map<String, String> params) throws BusinessException {
		String phone = params.get("phone");
		String password = params.get("password");
		User loginUser = authService.userLogin(phone, password);

		if (loginUser == null) {
			throw new BusinessException(EmBusinessError.LOGIN_FAILED);
		}

		if (!authService.checkAuth(loginUser.getRoles(), null)) {
			throw new BusinessException(EmBusinessError.FORBIDDEN);
		}

		String jwtToken = jwtUtil.generateToken(loginUser);
		redisUtil.set(jwtToken, loginUser, expiration);

		AuthVo auth = new AuthVo();
//		auth.setIsLogin("1");
//		auth.setIdentification(loginUser.getIdentification());
//		auth.setFuns(funs);
//		auth.setRoles(loginUser.getRoles());
		auth.setToken(jwtToken);
//		auth.setUserName(loginUser.getUserName());

		return Root.create(auth);
	}

	@PostMapping(value = { "/signout" }, produces = { "application/json;charset=UTF-8" })
	public Root signOut(HttpServletRequest request) throws BusinessException {

		String jwtToken = request.getHeader(tokenHeader);
		redisUtil.del(jwtToken);
		return Root.create(null);
	}

	@PostMapping(value = { "/chgpwd" }, produces = { "application/json;charset=UTF-8" })
	public Root changePassword(@RequestBody Map<String, String> params) throws BusinessException {

		String newPwd = params.get("newPwd");
		String oldPwd = params.get("oldPwd");

		if (!patternCheck(PATTERN_PASSWORD, newPwd)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "密码必须是大小写字母和数字的组合，长度在8到20之间(包含)。");
		}

		User loginUser = authContext.get();

		authService.userChgPwd(loginUser.getId(), oldPwd, newPwd);

		String jwtToken = jwtUtil.generateToken(loginUser);
		redisUtil.set(jwtToken, loginUser, expiration);

		AuthVo auth = new AuthVo();
		auth.setToken(jwtToken);

		return Root.create(auth);
	}
	
	public AuthVo convertToVo(User src) throws BusinessException {
		if (src == null) {
			return null;
		}
		AuthVo vo = new AuthVo();
		try {
			BeanUtils.copyProperties(vo, src);
		} catch (Exception e) {
			logger.error("类型转换错误。", e);
			throw new BusinessException(EmBusinessError.DATA_TYPE_CAST_ERROR);
		}

		return vo;
	}
}
