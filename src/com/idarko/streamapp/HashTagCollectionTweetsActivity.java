package com.idarko.streamapp;

import java.math.BigInteger;

import android.os.Bundle;

import com.idarko.streamapp.managers.ApplicationDataManager;
import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.ApplicationData;
import com.idarko.streamapp.model.HashTagCollection;
import com.idarko.streamapp.model.TweetsResult;

public class HashTagCollectionTweetsActivity extends TweetsActivity {
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	private HashTagCollection hashTagCollection;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  Bundle intentExtras = getIntent().getExtras();
	  int hashTagsCategoryIndex = intentExtras.getInt(StreamAppIntents.HASHTAG_CATEGORY_INDEX);
	  int hashTagsSectionIndex = intentExtras.getInt(StreamAppIntents.HASHTAG_SECTION_INDEX);
	  int hashTagCollectionIndex = intentExtras.getInt(StreamAppIntents.HASHTAG_COLLECTION_INDEX);
	  ApplicationData applicationData = ApplicationDataManager.getInstance().getApplicationData();
	  hashTagCollection = applicationData.getHashtagCategories().get(hashTagsCategoryIndex).getHashTagSections().get(hashTagsSectionIndex).getHashTagCollections().get(hashTagCollectionIndex);
	  getActionBar().setTitle(hashTagCollection.getName());
	  start();
	}

	@Override
	protected TweetsResult getTweets(BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return tweetsManager.getTweetsByHashTagCollection(hashTagCollection, minimumTweetId, maximumTweetId);
	}
}
