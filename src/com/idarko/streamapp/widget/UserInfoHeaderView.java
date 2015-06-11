package com.idarko.streamapp.widget;

import com.idarko.streamapp.R;
import com.idarko.streamapp.model.User;
import com.idarko.streamapp.util.ImageDownloader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoHeaderView extends LinearLayout {
	private final ImageDownloader imageDownloader = new ImageDownloader();
	TextView fullNameTextView;
	TextView userNameTextView;
	ImageView profileImageView;
	
	public UserInfoHeaderView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.user_info_header,this);
	   
		fullNameTextView = (TextView)findViewById(R.id.full_name);
		userNameTextView = (TextView)findViewById(R.id.username);
		profileImageView = (ImageView)findViewById(R.id.profile_image);
	 }
	
	public void setUser(User user){
		fullNameTextView.setText(user.getFullName());
		userNameTextView.setText(user.getUserName());
		String usersProfileImageUrl = user.getProfileImageUrl();
		if(usersProfileImageUrl != null){
			imageDownloader.download(usersProfileImageUrl, profileImageView);
		}
	}
}
