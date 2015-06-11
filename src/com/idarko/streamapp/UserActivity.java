package com.idarko.streamapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.idarko.streamapp.managers.FavoritesManager;
import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.User;
import com.idarko.streamapp.model.UserResult;
import com.idarko.streamapp.widget.UserInfoHeaderView;

public class UserActivity extends BaseActivity {
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	private User user;
	private View userTable;
	UserInfoHeaderView userInfoHeaderView;
	Button addToFavoritesButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.user);
	  user = (User)getIntent().getExtras().get(StreamAppIntents.USER);
	  userInfoHeaderView = (UserInfoHeaderView)findViewById(R.id.user_info_header);
	  userInfoHeaderView.setUser(user);
	  userTable = findViewById(R.id.user_table);
	  setUpFavoritesButton();
	  new GetUserTask().execute();
	}
	
	private void setUpFavoritesButton(){
		addToFavoritesButton = (Button)findViewById(R.id.add_to_favourites);
		addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	FavoritesManager favoritesManager = FavoritesManager.getInstance();
            	if(favoritesManager.isFavoriteUser(user.getUserName())){
            		favoritesManager.removeUser(user.getUserName());
        		}
        		else{
        			favoritesManager.addUser(user.getUserName());
        		}
            	setFavoritesButtonText();
            }
        });
		setFavoritesButtonText();
	}
	
	private void setFavoritesButtonText(){
		if(FavoritesManager.getInstance().isFavoriteUser(user.getUserName())){
			addToFavoritesButton.setText("remove from favorites");
		}
		else{
			addToFavoritesButton.setText("add to favorites");
		}
	}
	
	private void showError(String errorMessage){
    	Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
	
	private class GetUserTask extends AsyncTask<Void, Void, UserResult> {
    	private ProgressDialog progressDialog = null;

    	@Override
		protected void onPreExecute(){
    		progressDialog = ProgressDialog.show(UserActivity.this, null, "Loading user details...", true);
		}
    	@Override
        protected UserResult doInBackground(Void... params) {
			return tweetsManager.getUserByUserName(user.getUserName());
        }

        @Override
        protected void onPostExecute(UserResult result) { 
        	progressDialog.dismiss();
        	progressDialog = null;
        	
        	if(result.getHasException()){	
        		showError(result.getErrorMessage());
        	}
        	else{
        		user = result.getUser();
        		userInfoHeaderView.setUser(user);
        		TextView locationLabel = (TextView)findViewById(R.id.location);
        		locationLabel.setText(user.getLocation());
        		TextView bioLabel = (TextView)findViewById(R.id.bio);
        		bioLabel.setText(user.getBio());
        		TextView webLabel = (TextView)findViewById(R.id.web);
        		webLabel.setText(user.getUrl());
        		TextView numberOfFollowersLabel = (TextView)findViewById(R.id.followers);
        		numberOfFollowersLabel.setText(user.getNumberOfFollowers() + "");
        		TextView numberOfTweetsLabel = (TextView)findViewById(R.id.tweets);
        		numberOfTweetsLabel.setText(user.getNumberOfTweets() + "");
        		TextView isVerifiedLabel = (TextView)findViewById(R.id.verified);
        		isVerifiedLabel.setText(user.getIsVerified() ? "Yes" : "No");
        		userTable.setVisibility(View.VISIBLE);
        	}
            super.onPostExecute(result);
        }
    }
}
