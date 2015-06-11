package com.idarko.streamapp;

import java.math.BigInteger;

import android.os.Bundle;

import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.TweetsResult;

public class MyStreamActivity extends TweetsActivity {
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	String userName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  getActionBar().setTitle("My Stream");
	  userName = "Test";
	  start();
	}
	
	@Override
	protected TweetsResult getTweets(BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return tweetsManager.getTweetsByUser(userName, minimumTweetId, maximumTweetId);
	}
}
