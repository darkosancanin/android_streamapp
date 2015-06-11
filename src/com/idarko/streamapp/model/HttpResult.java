package com.idarko.streamapp.model;

public class HttpResult {
	private Boolean hasException;
	private String errorMessage;
	private String html;
	
	public HttpResult(){
		setHasException(false);
	}
	
	public HttpResult(String html){
		setHtml(html);
		setHasException(false);
	}
	
	public static HttpResult CreateHttpResultWithException(String errorMessage){
		HttpResult httpResult = new HttpResult();
		httpResult.setErrorMessage(errorMessage);
		return httpResult;
	}
	
	public Boolean getHasException() {
		return hasException;
	}
	
	public void setHasException(Boolean hasException) {
		this.hasException = hasException;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		this.hasException = true;
	}
	
	public String getHtml() {
		return html;
	}
	
	public void setHtml(String html) {
		this.html = html;
	}
}
