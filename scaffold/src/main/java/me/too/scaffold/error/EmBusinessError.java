package me.too.scaffold.error;

public enum EmBusinessError implements CommonError {
	PARAMETER_VALIDATION_ERROR(900000, "参数不合法"), 
	LOGIN_FAILED(900001, "登录失败"), 
	DATA_NOT_EXIST(900100, "数据不存在"),
	DATA_CONFLICT(900101, "数据冲突"), 
	BAD_REQUEST(900400, "非法请求"), 
	UNAUTHORIZED(900401, "没有登录"), 
	FORBIDDEN(900403, "拒绝访问"),
	DATA_TYPE_CAST_ERROR(999000, "业务数据模型转换错误"), 
	UNKNOWN_ERROR(999999, "未知错误");
	private EmBusinessError(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	};

	private int errCode;
	private String errMsg;

	@Override
	public int getErrCode() {
		return this.errCode;
	}

	@Override
	public String getErrMsg() {
		return this.errMsg;
	}
}
