package com.idarko.streamapp.model;

import java.util.LinkedList;

public class TweetsResult {
	private Boolean hasException = false;
	private String errorMessage;
	private LinkedList<Tweet> tweets;
	
	public TweetsResult(){
		setHasException(false);
	}
	
	public TweetsResult(LinkedList<Tweet> tweets){
		setTweets(tweets);
	}
	
	public static TweetsResult CreateTweetsResultWithException(String errorMessage){
		TweetsResult tweetsResult = new TweetsResult();
		tweetsResult.setErrorMessage(errorMessage);
		return tweetsResult;
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

	public LinkedList<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(LinkedList<Tweet> tweets) {
		this.tweets = tweets;
	}
}
