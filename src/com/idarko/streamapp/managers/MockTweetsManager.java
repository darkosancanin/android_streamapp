package com.idarko.streamapp.managers;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.idarko.streamapp.model.HashTagCollection;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.Tweet;
import com.idarko.streamapp.model.TweetsResult;
import com.idarko.streamapp.model.User;
import com.idarko.streamapp.model.UserResult;

public class MockTweetsManager implements ITweetsManager {
	private TweetsResult getTestTweets(){
		LinkedList<Tweet> tweets = new LinkedList<Tweet>();
		tweets.add(new Tweet(new BigInteger("1"),"So thankful for everyone coming out to our house for the AHLegacyFoundation benefit! @MSGTina did a wonderful job hosting! Checkout http://www.nba.com #NYKnicks", "ALLAN_HOUSTON", "Allan Houston", "http://a0.twimg.com/profile_images/1288315644/lQJOX_normal.jpg", "carmeloanthony", new Date()));
		tweets.add(new Tweet(new BigInteger("2"),"@MikeHillESPN haha. Make sure u hit me up", "CharlesOakley34", "Charles Oakley", "http://a2.twimg.com/profile_images/824213003/C.Oak_in_Miami__0310_cropped_normal.JPG", "", new Date()));
		tweets.add(new Tweet(new BigInteger("3"),"Bringing @thenyknicks swag to @sesamestreet. Check out Grover's warmups. http://twitpic.com/4fgrlq", "Amareisreal", "Amar'e Stoudemire", "http://a1.twimg.com/profile_images/1246226918/Amareisreal_normal.jpg", "", new Date()));
		tweets.add(new Tweet(new BigInteger("4"),"Carmelo's New Billboard (3/29) http://fb.me/Oeb31exH", "carmeloanthony", "Carmelo Anthony", "http://a2.twimg.com/profile_images/1252531937/image_normal.jpg", "", new Date()));
		tweets.add(new Tweet(new BigInteger("5"),"By looking at this photo, I'm willing to bet everyone knows what I am about to do next.. http://twitpic.com/4fefb9", "landryfields", "Landry Fields", "http://a0.twimg.com/profile_images/1289360623/613x459_normal.jpg", "", new Date()));
		tweets.add(new Tweet(new BigInteger("6"),"About to film @sesamestreet with @carmeloanthony. Get ready Grover! http://twitpic.com/4fgi6j", "Amareisreal", "Amar'e Stoudemire", "http://a1.twimg.com/profile_images/1246226918/Amareisreal_normal.jpg", "", new Date()));
		tweets.add(new Tweet(new BigInteger("7"),"One person with courage is a majority.", "landryfields", "Landry Fields", "http://a0.twimg.com/profile_images/1289360623/613x459_normal.jpg", "", new Date()));
		tweets.add(new Tweet(new BigInteger("8"),"A few pics from the crazy week that was... All Star 2011 http://say.ly/lFzaK9", "carmeloanthony", "Carmelo Anthony", "http://a2.twimg.com/profile_images/1252531937/image_normal.jpg", "", new Date()));
		return new TweetsResult(tweets);
	}

	public TweetsResult getTweetsByUsers(List<String> userNames, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return getTestTweets();
	}

	public TweetsResult getTweetsForConversation(String firstUserName, String secondUserName, BigInteger minimumTweetId,  BigInteger maximumTweetId) {
		return getTestTweets();
	}

	public TweetsResult getTweetsBySearch(String searchTerm, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return getTestTweets();
	}

	public TweetsResult getTweetsByStream(Stream stream, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return getTestTweets();
	}

	public TweetsResult getTweetsByHashTagCollection(HashTagCollection hashTagCollection, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return getTestTweets();
	}

	public TweetsResult getTweetsByUser(String userName, BigInteger minimumTweetId, BigInteger maximumTweetId) {
		return getTestTweets();
	}

	public UserResult getUserByUserName(String userName) {
		User user = new User();
		user.setLocation("San Francisco");
		user.setBio("NY Knicks Player.");
		user.setUrl("http://www.melo.com");
		user.setUserName("carmeloanthony");
		user.setFullName("Carmelo Anthony");
		user.setProfileImageUrl("http://a2.twimg.com/profile_images/1252531937/image_normal.jpg");
		user.setIsVerified(true);
		user.setNumberOfFollowers(234234);
		user.setNumberOfTweets(2388);
		return new UserResult(user);
	}
}
