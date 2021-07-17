package me.too.scaffold.util;

public class ValidationResult {
	private boolean hasError = false;
	private String errorMsg = "";
	
	public ValidationResult(){
		
	}
	
	public ValidationResult(String errorMsg) {
		this.hasError = true;
		this.errorMsg = errorMsg;
	}
	
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
