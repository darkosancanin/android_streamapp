package com.idarko.streamapp;

import java.math.BigInteger;
import java.util.LinkedList;

import com.idarko.streamapp.managers.TweetsManager;
import com.idarko.streamapp.model.Tweet;
import com.idarko.streamapp.model.TweetViewHolder;
import com.idarko.streamapp.model.TweetsResult;
import com.idarko.streamapp.util.ImageDownloader;
import com.idarko.streamapp.widget.ActionBar;
import com.idarko.streamapp.widget.PullToRefreshListView;
import com.idarko.streamapp.widget.PullToRefreshListView.OnRefreshListener;
import com.idarko.streamapp.widget.ActionBar.Action;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public abstract class TweetsActivity extends BaseActivity {
	private final ImageDownloader imageDownloader = new ImageDownloader();
	private LinkedList<Tweet> tweets = new LinkedList<Tweet>(); 
	private PullToRefreshListView listView;
	protected ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets);
        setUpActionBar();
        setUpListView(); 
    }
    
    protected void start(){
    	new GetLatestTweetsTask(true).execute();
    }
    
    public void setUpListView(){
    	listView = (PullToRefreshListView) findViewById(R.id.list_view);
        listView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetLatestTweetsTask(false).execute();
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent tweetIntent = new Intent(getApplicationContext(), TweetActivity.class);
		    	Tweet tweet = tweets.get(position - 1);
		    	tweetIntent.putExtra(StreamAppIntents.TWEET, tweet);
		    	startActivity(tweetIntent);
		    }
		});
    }
    
    public void setUpActionBar(){
    	actionBar = (ActionBar) findViewById(R.id.actionbar);
    	
    	actionBar.addAction(new Action() {
            @Override
            public void performAction(View view) {
                refreshTweets();
            }
            @Override
            public int getDrawable() {
                return R.drawable.ic_menu_refresh;
            }
        });
    }
    
    public void refreshTweets(){
    	tweets = new LinkedList<Tweet>();
    	HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) listView.getAdapter(); 
    	if(headerViewListAdapter != null){
    		((BaseAdapter)headerViewListAdapter.getWrappedAdapter()).notifyDataSetChanged();
    	}
    	new GetLatestTweetsTask(true).execute();
    }
    
    public ActionBar getActionBar(){
    	return actionBar;
    }
    
    public void showError(String errorMessage){
    	Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    
    protected abstract TweetsResult getTweets(BigInteger minimumTweetId, BigInteger maximumTweetId);
    
    private class EndlessTweetsAdapter extends EndlessAdapter{
    	private TweetsResult cachedTweetsResult;
    	
		public EndlessTweetsAdapter() {
			super(TweetsActivity.this, new TweetsAdapter(), R.layout.loading);
		}

		@Override
		protected boolean cacheInBackground() throws Exception {
			BigInteger maximumTweetId = new BigInteger("0");
			if(tweets.size() > 0){
				maximumTweetId = tweets.getLast().getTweetId().subtract(new BigInteger("1"));
			}
			cachedTweetsResult = getTweets(new BigInteger("0"), maximumTweetId);	
			return true;
		}

		@Override
		protected void appendCachedData() {
			if(cachedTweetsResult.getHasException()){
				showError(cachedTweetsResult.getErrorMessage());
			}
			else{
				tweets.addAll(tweets.size(), cachedTweetsResult.getTweets());
				TweetsAdapter tweetsAdapter = (TweetsAdapter)getWrappedAdapter();
				tweetsAdapter.notifyDataSetChanged();
			}
		}
    }
    
    private class TweetsAdapter extends ArrayAdapter<Tweet>{

		public TweetsAdapter() {
			super(TweetsActivity.this, R.layout.tweet_list_item, tweets);
		}
    	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView; 
			TweetViewHolder tweetViewHolder;
			if (row==null) {
				LayoutInflater inflater=getLayoutInflater(); 
				row=inflater.inflate(R.layout.tweet_list_item, parent, false);
				tweetViewHolder = new TweetViewHolder();
				tweetViewHolder.fullNameLabel = (TextView)row.findViewById(R.id.full_name);
				tweetViewHolder.userNameLabel = (TextView)row.findViewById(R.id.username);
				tweetViewHolder.dateLabel = (TextView)row.findViewById(R.id.date);
				tweetViewHolder.textLabel = (TextView)row.findViewById(R.id.text);
				tweetViewHolder.profileImageView = (ImageView)row.findViewById(R.id.profile_image);
				row.setTag(tweetViewHolder);
			}
			else{
				tweetViewHolder = (TweetViewHolder)row.getTag();
			}
			
			if(tweets.size() > position){
				Tweet tweet = tweets.get(position);
				tweetViewHolder.fullNameLabel.setText(tweet.getFullName());
				tweetViewHolder.userNameLabel.setText(tweet.getUserName());
				tweetViewHolder.dateLabel.setText(tweet.humanFriendlyCreatedDate());
				tweetViewHolder.textLabel.setText(tweet.getText());
				imageDownloader.download(tweet.getProfileImageUrl(), tweetViewHolder.profileImageView);
			}
			else{
				tweetViewHolder.fullNameLabel.setText("");
				tweetViewHolder.userNameLabel.setText("");
				tweetViewHolder.dateLabel.setText("");
				tweetViewHolder.textLabel.setText("");
				tweetViewHolder.profileImageView.setImageBitmap(null);
			}
			return row;
		}
    }

    private class GetLatestTweetsTask extends AsyncTask<Void, Void, TweetsResult> {
    	private Boolean isLoadingInitialTweets;
    	private ProgressDialog progressDialog = null;
    	
    	public GetLatestTweetsTask(Boolean isLoadingInitialTweets){
    		this.isLoadingInitialTweets = isLoadingInitialTweets;
    	}
    	
    	@Override
		protected void onPreExecute(){
    		if(isLoadingInitialTweets){
    			progressDialog = ProgressDialog.show(TweetsActivity.this, null, "Loading tweets...", true);
    		}
		}
    	@Override
        protected TweetsResult doInBackground(Void... params) {
            BigInteger minimumTweetId = new BigInteger("0");
			if(tweets.size() > 0){
				minimumTweetId = tweets.getFirst().getTweetId().add(new BigInteger("1"));
			}
            return getTweets(minimumTweetId, new BigInteger("0"));	
        }

        @Override
        protected void onPostExecute(TweetsResult result) { 
        	if(progressDialog != null){
        		progressDialog.dismiss();
        		progressDialog = null; 
        	}
        	
        	if(!isLoadingInitialTweets) {
        		listView.onRefreshComplete();
    		}
        	
        	if(result.getHasException()){	
        		showError(result.getErrorMessage());
        	}
        	else{
        		tweets.addAll(0, result.getTweets());
            	if(isLoadingInitialTweets){
            		if(tweets.size() < TweetsManager.getMaxResultSize()){
        				listView.setAdapter(new TweetsAdapter());
        			}
        			else{
        				listView.setAdapter(new EndlessTweetsAdapter());
        			}
        			HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) listView.getAdapter(); 
        			((BaseAdapter)headerViewListAdapter.getWrappedAdapter()).notifyDataSetChanged();
        		}
        	}
            super.onPostExecute(result);
        }
    }
}
