package com.idarko.streamapp;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.streams:
	    	Intent streamsIntent = new Intent(getApplicationContext(), StreamsActivity.class);
	    	streamsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(streamsIntent);
	        return true; 
	    case R.id.favorites:
	    	Intent favoritesIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
	    	favoritesIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(favoritesIntent);
	        return true; 
	    case R.id.hashtags:
	    	Intent hashTagsIntent = new Intent(getApplicationContext(), HashTagsActivity.class);
	    	hashTagsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(hashTagsIntent);
	        return true; 
	    case R.id.mystream:
	    	Intent myStreamIntent = new Intent(getApplicationContext(), MyStreamActivity.class);
	    	myStreamIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(myStreamIntent);
	        return true; 
	    case R.id.search:
	    	Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
	    	searchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(searchIntent);
	        return true; 
	    case R.id.settings:
	    	Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
	    	settingsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(settingsIntent);
	        return true; 
	    case R.id.info:
	    	Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
	    	infoIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    	startActivity(infoIntent);
	        return true; 
	    default:
	    	return true;
	    }
	}
}
