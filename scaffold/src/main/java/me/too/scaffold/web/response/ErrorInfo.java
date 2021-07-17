package me.too.scaffold.web.response;

import lombok.Data;

@Data
public class ErrorInfo {
	private int errCode;
	private String errMsg;
}