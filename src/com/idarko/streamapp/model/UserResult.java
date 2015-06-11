package com.idarko.streamapp.model;

public class UserResult {
	private Boolean hasException = false;
	private String errorMessage;
	private User user;
	
	public UserResult(){
		setHasException(false);
	}
	
	public UserResult(User user){
		setUser(user);
	}
	
	public static UserResult CreateUserResultWithException(String errorMessage){
		UserResult userResult = new UserResult();
		userResult.setErrorMessage(errorMessage);
		return userResult;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
