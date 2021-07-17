package me.too.scaffold.web.response;

import lombok.Data;

@Data
public class Root {
	public static final String RESPONSE_STATUS_OK = "ok";
	public static final String RESPONSE_STATUS_ERROR = "error";
	/**
	 * ok / error
	 */
	private String status;
	/**
	 * status是success，data返回前端需要的json
	 * status是failure，data返回通用错误信息
	 */
	private Object data;

	public static Root create(Object data) {
		return Root.create(Root.RESPONSE_STATUS_OK, data);
	}

	public static Root create(String status, Object data) {
		Root type = new Root();
		type.setData(data);
		type.setStatus(status);
		return type;
	}
}
