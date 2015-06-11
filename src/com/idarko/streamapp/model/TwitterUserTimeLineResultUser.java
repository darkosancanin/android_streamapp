package com.idarko.streamapp.model;

import com.google.gson.annotations.SerializedName;

public class TwitterUserTimeLineResultUser {
    @SerializedName("profile_image_url")
    public String profileImageUrl;
    
    @SerializedName("screen_name")
    public String userName;
    
    @SerializedName("name")
    public String fullName;
}