package com.idarko.streamapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	private String userName;
	private String fullName;
	private String profileImageUrl;
	private String url;
	private String bio;
	private String location;
	private Boolean isVerified;
	private long numberOfTweets;
	private long numberOfFollowers;
	
	public User(){}
	
	public User(String userName){
		setUserName(userName);
	}
	
	public User(String userName, String fullName, String profileImageUrl){
		setUserName(userName);
		setFullName(fullName);
		setProfileImageUrl(profileImageUrl);
	}
	
	public User(TwitterUserResponse twitterUserResponse) {
		setLocation(twitterUserResponse.location);
		setBio(twitterUserResponse.description);
		setUrl(twitterUserResponse.url);
		setUserName(twitterUserResponse.userName);
		setFullName(twitterUserResponse.fullName);
		setProfileImageUrl(twitterUserResponse.profileImageUrl);
		setIsVerified(twitterUserResponse.verified);
		setNumberOfFollowers(twitterUserResponse.numberOfFollowers);
		setNumberOfTweets(twitterUserResponse.numberOfTweets);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Boolean getIsVerified() {
		return isVerified;
	}
	
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	public long getNumberOfTweets() {
		return numberOfTweets;
	}
	
	public void setNumberOfTweets(long numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}
	
	public long getNumberOfFollowers() {
		return numberOfFollowers;
	}
	
	public void setNumberOfFollowers(long numberOfFollowers) {
		this.numberOfFollowers = numberOfFollowers;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getUserName());
		out.writeString(getFullName());
		out.writeString(getProfileImageUrl());
		out.writeString(getUrl());
		out.writeString(getBio());
		out.writeString(getLocation());
	}
	
	public User(Parcel source){
		setUserName(source.readString());
		setFullName(source.readString());
		setProfileImageUrl(source.readString());
		setUrl(source.readString());
		setBio(source.readString());
		setLocation(source.readString());
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
	@Override
	public String toString(){
		return getFullName();
	}
}
