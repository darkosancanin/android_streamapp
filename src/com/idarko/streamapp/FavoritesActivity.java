package com.idarko.streamapp;

import java.math.BigInteger;

import android.os.Bundle;

import com.idarko.streamapp.managers.FavoritesManager;
import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.TweetsResult;

public class FavoritesActivity extends TweetsActivity {
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	private final FavoritesManager favoritesManager = new FavoritesManager();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  getActionBar().setTitle("Favorites");
	  start();
	}
	
	@Override
	protected TweetsResult getTweets(BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return tweetsManager.getTweetsByUsers(favoritesManager.getFavoriteUserNames(), minimumTweetId, maximumTweetId);
	}
}
