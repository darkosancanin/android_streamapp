package com.idarko.streamapp;

import java.math.BigInteger;
import java.util.LinkedList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idarko.streamapp.managers.ITweetsManager;
import com.idarko.streamapp.managers.TweetsManagerFactory;
import com.idarko.streamapp.model.Tweet;
import com.idarko.streamapp.model.TweetsResult;
import com.idarko.streamapp.model.User;
import com.idarko.streamapp.util.ImageDownloader;
import com.idarko.streamapp.widget.UserInfoHeaderView;

public class TweetActivity extends BaseActivity {
	private Tweet tweet;
	private LinkedList<Tweet> tweets = new LinkedList<Tweet>(); 
	private final ImageDownloader imageDownloader = new ImageDownloader();
	private final ITweetsManager tweetsManager = TweetsManagerFactory.CreateTweetsManager();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.tweet);
	  Bundle bundle = getIntent().getExtras();
	  tweet = bundle.getParcelable(StreamAppIntents.TWEET);

	  UserInfoHeaderView userInfoHeaderView = (UserInfoHeaderView) findViewById(R.id.user_info_header);
	  userInfoHeaderView.setUser(new User(tweet.getUserName(), tweet.getFullName(), tweet.getProfileImageUrl()));
	  
	  setUpWebView();
      new GetConversationTweetsTask().execute();
	}
  
	private void setUpWebView(){
		WebView webView = (WebView)findViewById(R.id.web_view);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.setBackgroundColor(0x00000000);
		webView.setWebViewClient(new WebViewClient(){
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView  view, String  url){
		    	if(url.startsWith("http")){
		    		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		    		startActivity(browserIntent);
		    		return true;
		    	}
		    	else if(url.startsWith("u://")){
		    		String userName = url.replace("u://", "");
		    		User user = new User(userName);
		    		Intent userIntent = new Intent(getApplicationContext(), UserActivity.class);
		    		userIntent.putExtra(StreamAppIntents.USER, user);
			    	startActivity(userIntent);
			    	return true;
		    	}
		    	else if(url.startsWith("h://")){
		    		String hashTag = url.replace("h://", "");
		    		Intent hashTagIntent = new Intent(getApplicationContext(), SearchActivity.class);
		    		hashTagIntent.putExtra(StreamAppIntents.SEARCH_TERM, hashTag);
			    	startActivity(hashTagIntent);
			    	return true;
		    	}
		        return false;
		    }
		    
		    @Override
		    public void onLoadResource(WebView  view, String  url){
		    	
		    }
		    
		    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		        Toast.makeText(TweetActivity.this, description, Toast.LENGTH_SHORT).show();
		    }
		});
		String html = getHtmlFromTweet(tweet);
		webView.loadData(html, "text/html", "utf-8");
	}
	
	private String getHtmlFromTweet(Tweet tweet){
		String tweetText = tweet.getText()
				.replaceAll("@([1-9a-zA-Z_]+)", "<a href='u://$0'>$0</a>")
				.replaceAll("#([^\\s#@]+)", "<a href='h://$0'>$0</a>")
				.replaceAll("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?������]))", "<a href=\"$1\">$1</a>");
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		html.append("<head>");
		html.append("<style type=\"text/css\">");
		html.append("body {font-family: \"helvetica\"; font-size: 12; color:#FFFFFF; margin: 0; padding: 0; }\n");
		html.append(".date { padding:10px 0px 0px 0px; font-size:12; }\n");
		html.append("a:link, a:visited, a:active { color: #FFFFFF; font-size:12; text-decoration:none; }\n");
		html.append("</style>");
		html.append("<body>");
		html.append("<div class=\"top\"></div>");
		html.append("<div class=\"middle\">");
		html.append("<div class=\"text\">" + tweetText + " ");
		html.append("</div>");
		html.append("<div class=\"date\">" + tweet.humanFriendlyCreatedDate());
		html.append("</div>");
		html.append("</div>");
		html.append("<div class=\"bottom\"></div>");
		html.append("</body>");
		html.append("</head>");
		html.append("</html>");
		return html.toString();	
	}
	
	public void showError(String errorMessage){
    	Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
	
	private class GetConversationTweetsTask extends AsyncTask<Void, Void, TweetsResult> {
    	private ProgressDialog progressDialog = null;

    	@Override
		protected void onPreExecute(){
    		progressDialog = ProgressDialog.show(TweetActivity.this, null, "Loading conversation...", true);
		}
    	@Override
        protected TweetsResult doInBackground(Void... params) {
			return tweetsManager.getTweetsForConversation("", "", new BigInteger("0"), new BigInteger("0"));
        }

        @Override
        protected void onPostExecute(TweetsResult result) { 
        	progressDialog.dismiss();
        	progressDialog = null;
        	
        	if(result.getHasException()){	
        		showError(result.getErrorMessage());
        	}
        	else{
        		tweets = result.getTweets();
        		LinearLayout listView = (LinearLayout)findViewById(R.id.list_view);
        		for(Tweet tweet: tweets){
        			LayoutInflater inflater=getLayoutInflater(); 
    				View row = inflater.inflate(R.layout.tweet_list_item, null);
    				row.setTag(tweet);
    				TextView fullNameLabel = (TextView)row.findViewById(R.id.full_name);
    				TextView userNameLabel = (TextView)row.findViewById(R.id.username);
    				TextView dateLabel = (TextView)row.findViewById(R.id.date);
    				TextView textLabel = (TextView)row.findViewById(R.id.text);
    				ImageView profileImageView = (ImageView)row.findViewById(R.id.profile_image);
    				
    				fullNameLabel.setText(tweet.getFullName());
    				userNameLabel.setText(tweet.getUserName());
    				dateLabel.setText(tweet.humanFriendlyCreatedDate());
    				textLabel.setText(tweet.getText());
    				imageDownloader.download(tweet.getProfileImageUrl(), profileImageView);
    				
    				row.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent tweetIntent = new Intent(getApplicationContext(), TweetActivity.class);
							Tweet selectedTweet = (Tweet)v.getTag();
					    	tweetIntent.putExtra(StreamAppIntents.TWEET, selectedTweet);
					    	startActivity(tweetIntent);
						}
					});
    				
    				listView.addView(row);
        		}
        		
        	}
            super.onPostExecute(result);
        }
    }
}
