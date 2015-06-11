package com.idarko.streamapp.model;

import com.google.gson.annotations.SerializedName;

public class TwitterUserResponse {
    public String location;
    
    @SerializedName("profile_image_url")
    public String profileImageUrl;
    
    @SerializedName("name")
    public String fullName;
    
    public String description;
    
    @SerializedName("screen_name")
    public String userName;
    
    public String url;
    
    public Boolean verified;
    
    @SerializedName("followers_count")
    public long numberOfFollowers;
    
    @SerializedName("statuses_count")
    public long numberOfTweets;
}
