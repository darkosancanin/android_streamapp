package com.idarko.streamapp;

import java.math.BigInteger;

import android.os.Bundle;

import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.TweetsResult;

public class SearchActivity extends TweetsActivity {
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	String searchTerm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  searchTerm = getIntent().getExtras().getString(StreamAppIntents.SEARCH_TERM);
	  getActionBar().setTitle(searchTerm);
	  start();
	}
	
	@Override
	protected TweetsResult getTweets(BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return tweetsManager.getTweetsBySearch(searchTerm, minimumTweetId, maximumTweetId);
	}
}
