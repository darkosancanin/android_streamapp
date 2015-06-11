package com.idarko.streamapp.model;

import com.google.gson.annotations.SerializedName;

public class TwitterUserTimeLineResult {
    public String text;
    
    public long id;
    
    @SerializedName("id_str")
    public String idStr;
    
   @SerializedName("created_at")
    public String createdAt;
    
    public TwitterUserTimeLineResultUser user;
    
    @SerializedName("in_reply_to_screen_name")
    public String toUser;
}