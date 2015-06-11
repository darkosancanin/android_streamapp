package com.idarko.streamapp;

import java.math.BigInteger;

import android.os.Bundle;

import com.idarko.streamapp.managers.ApplicationDataManager;
import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.ApplicationData;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.TweetsResult;

public class StreamTweetsActivity extends TweetsActivity {
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	private Stream stream;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  Bundle intentExtras = getIntent().getExtras();
	  int streamCategoryIndex = intentExtras.getInt(StreamAppIntents.STREAM_CATEGORY_INDEX);
	  int streamSectionIndex = intentExtras.getInt(StreamAppIntents.STREAM_SECTION_INDEX);
	  int streamIndex = intentExtras.getInt(StreamAppIntents.STREAM_INDEX);
	  ApplicationData applicationData = ApplicationDataManager.getInstance().getApplicationData();
	  stream = applicationData.getStreamCategories().get(streamCategoryIndex).getStreamSections().get(streamSectionIndex).getStreams().get(streamIndex);
	  getActionBar().setTitle(stream.getName());
	  start();
	}

	@Override
	protected TweetsResult getTweets(BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return tweetsManager.getTweetsByStream(stream, minimumTweetId, maximumTweetId);
	}
}
