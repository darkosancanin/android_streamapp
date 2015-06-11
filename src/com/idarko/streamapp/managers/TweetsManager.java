package com.idarko.streamapp.managers;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idarko.streamapp.model.HashTagCollection;
import com.idarko.streamapp.model.HttpResult;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.Tweet;
import com.idarko.streamapp.model.TweetsResult;
import com.idarko.streamapp.model.TwitterSearchResult;
import com.idarko.streamapp.model.TwitterSearchResponse;
import com.idarko.streamapp.model.TwitterUserResponse;
import com.idarko.streamapp.model.TwitterUserTimeLineResponse;
import com.idarko.streamapp.model.TwitterUserTimeLineResult;
import com.idarko.streamapp.model.User;
import com.idarko.streamapp.model.UserResult;

public class TweetsManager extends HttpManager implements ITweetsManager {
	public TweetsResult getTweetsByUsers(List<String> userNames, BigInteger minimumTweetId, BigInteger maximumTweetId){
		StringBuilder url = new StringBuilder("http://search.twitter.com/search.json?result_type=recent&q=");
		Boolean isFirstUsername = true;
		for(String userName : userNames){
			if(!isFirstUsername) url.append("+OR+");
			url.append("from%3A");
			url.append(userName);
			isFirstUsername = false;
		}
		addMaxAndMinIdsAndMaxRecordsToUrl(url, minimumTweetId, maximumTweetId);
		return getTweetsFromSearchUrl(url.toString());
	}
	
	public TweetsResult getTweetsForConversation(String firstUserName, String secondUserName, BigInteger minimumTweetId, BigInteger maximumTweetId){
		StringBuilder url = new StringBuilder("http://search.twitter.com/search.json?result_type=recent&q=from%3A");
		url.append(firstUserName);
		url.append("+OR+from%3A");
		url.append(secondUserName);
		addMaxAndMinIdsAndMaxRecordsToUrl(url, minimumTweetId, maximumTweetId);
		return getTweetsFromSearchUrl(url.toString());
	}
	
	public TweetsResult getTweetsByUser(String userName, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		StringBuilder url = new StringBuilder("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=");
		url.append(userName);
		addMaxAndMinIdsAndMaxRecordsToUrl(url, minimumTweetId, maximumTweetId);
		return getTweetsFromUserTimeLineUrl(url.toString());
	}
	
	public TweetsResult getTweetsBySearch(String searchTerm, BigInteger minimumTweetId, BigInteger maximumTweetId){
		StringBuilder url = new StringBuilder("http://search.twitter.com/search.json?result_type=recent&q=");
		url.append(URLEncoder.encode(searchTerm));
		addMaxAndMinIdsAndMaxRecordsToUrl(url, minimumTweetId, maximumTweetId);
		return getTweetsFromSearchUrl(url.toString());
	}
	
	public TweetsResult getTweetsByStream(Stream stream, BigInteger minimumTweetId, BigInteger maximumTweetId){
		StringBuilder url = new StringBuilder("http://search.twitter.com/search.json?result_type=recent&q=");
		Boolean isFirstUsername = true;
		for(User user : stream.getUsers()){
			if(!isFirstUsername) url.append("+OR+");
			url.append("from%3A");
			url.append(user.getUserName());
			isFirstUsername = false;
		}
		addMaxAndMinIdsAndMaxRecordsToUrl(url, minimumTweetId, maximumTweetId);
		return getTweetsFromSearchUrl(url.toString());
	}
	
	public TweetsResult getTweetsByHashTagCollection(HashTagCollection hashTagCollection, BigInteger minimumTweetId, BigInteger maximumTweetId){
		StringBuilder url = new StringBuilder("http://search.twitter.com/search.json?result_type=recent&q=");
		Boolean isFirstHashTag = true;
		for(String hashTag : hashTagCollection.getHashTags()){
			if(!isFirstHashTag) url.append("+OR+");
			url.append("%23");
			url.append(hashTag);
			isFirstHashTag = false;
		}
		addMaxAndMinIdsAndMaxRecordsToUrl(url, minimumTweetId, maximumTweetId);
		return getTweetsFromSearchUrl(url.toString());
	}
	
	private void addMaxAndMinIdsAndMaxRecordsToUrl(StringBuilder url, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		url.append("&rpp=" + getMaxResultSize());
		BigInteger zero = new BigInteger("0");
		if(minimumTweetId.compareTo(zero) > 0){
			url.append("&since_id=" + minimumTweetId.toString());
		}
		if(maximumTweetId.compareTo(zero) > 0){
			url.append("&max_id=" + maximumTweetId.toString());
		}
	}
	
	public UserResult getUserByUserName(String userName) {
		HttpResult httpResult = getHtml("http://api.twitter.com/1/users/show/" + userName + ".json");
		if(httpResult.getHasException()){
			return UserResult.CreateUserResultWithException(httpResult.getErrorMessage());
		}
		else{
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			TwitterUserResponse response = gson.fromJson(httpResult.getHtml(), TwitterUserResponse.class);
			return new UserResult(new User(response));
		}
	}

	private TweetsResult getTweetsFromSearchUrl(String url){
		HttpResult httpResult = getHtml(url);
		if(httpResult.getHasException()){
			return TweetsResult.CreateTweetsResultWithException(httpResult.getErrorMessage());
		}
		else{
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			TwitterSearchResponse response = gson.fromJson(httpResult.getHtml(), TwitterSearchResponse.class);
			List<TwitterSearchResult> results = response.results;
			HashMap<String, String> fullNameLookUp = ApplicationDataManager.getInstance().getFullNameUserLookUp();
			LinkedList<Tweet> tweets = new LinkedList<Tweet>();
			for (TwitterSearchResult result : results) {
				Tweet tweet = new Tweet(result);
				tweet.setFullName(fullNameLookUp.get(tweet.getUserName().toLowerCase()));
				tweets.add(tweet);
				
		    }
			return new TweetsResult(tweets);
		}
	}
	
	private TweetsResult getTweetsFromUserTimeLineUrl(String url){
		HttpResult httpResult = getHtml(url);
		if(httpResult.getHasException()){
			return TweetsResult.CreateTweetsResultWithException(httpResult.getErrorMessage());
		}
		else{
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			TwitterUserTimeLineResponse response = gson.fromJson(httpResult.getHtml(), TwitterUserTimeLineResponse.class);
			LinkedList<Tweet> tweets = new LinkedList<Tweet>();
			for (TwitterUserTimeLineResult result : response) {
				tweets.add(new Tweet(result));
		    }
			return new TweetsResult(tweets);
		}
	}

	public static int getMaxResultSize() {
		return 20;
	}
}
