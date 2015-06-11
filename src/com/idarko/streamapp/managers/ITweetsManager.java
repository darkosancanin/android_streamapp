package com.idarko.streamapp.managers;

import java.math.BigInteger;
import java.util.List;

import com.idarko.streamapp.model.HashTagCollection;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.TweetsResult;
import com.idarko.streamapp.model.UserResult;

public interface ITweetsManager {
	UserResult getUserByUserName(String userName);
	TweetsResult getTweetsByUsers(List<String> userNames, BigInteger minimumTweetId, BigInteger maximumTweetId);
	TweetsResult getTweetsByUser(String userName, BigInteger minimumTweetId, BigInteger maximumTweetId);
	TweetsResult getTweetsForConversation(String firstUserName, String secondUserName, BigInteger minimumTweetId, BigInteger maximumTweetId);
	TweetsResult getTweetsBySearch(String searchTerm, BigInteger minimumTweetId, BigInteger maximumTweetId);
	TweetsResult getTweetsByStream(Stream stream, BigInteger minimumTweetId, BigInteger maximumTweetId);
	TweetsResult getTweetsByHashTagCollection(HashTagCollection hashTagCollection, BigInteger minimumTweetId, BigInteger maximumTweetId);
}
