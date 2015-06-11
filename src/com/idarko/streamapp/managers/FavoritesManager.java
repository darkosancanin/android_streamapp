package com.idarko.streamapp.managers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.idarko.streamapp.StreamAppApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class FavoritesManager {
	private static final FavoritesManager INSTANCE = new FavoritesManager();
	private List<String> favoriteUserNames = null;
	
	public static FavoritesManager getInstance(){
		return INSTANCE;
	}
	
	public List<String> getFavoriteUserNames(){
		initializeFavorites();
		return favoriteUserNames;
	}
	
	private void initializeFavorites(){
		if(favoriteUserNames == null){
			SharedPreferences settings = StreamAppApplication.getAppContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);  
			String userNames = settings.getString("usernames", null);
			if(userNames == null){
				favoriteUserNames = new LinkedList<String>();
			}
			else{
				favoriteUserNames = new LinkedList<String>(Arrays.asList(userNames.split(",")));
			}
		}
	}
	
	public void addUser(String userName){
		initializeFavorites();
		favoriteUserNames.add(userName.toLowerCase());
		saveFavoritesToSharedPreferences(); 
	}
	
	public void removeUser(String userName){
		initializeFavorites();
		favoriteUserNames.remove(userName.toLowerCase());
		saveFavoritesToSharedPreferences();
	}
	
	public Boolean isFavoriteUser(String userName){
		initializeFavorites();
		return favoriteUserNames.contains(userName.toLowerCase());
	}
	
	private void saveFavoritesToSharedPreferences(){
		String userNames = StringUtils.join(favoriteUserNames,",");
		SharedPreferences settings = StreamAppApplication.getAppContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);  
		SharedPreferences.Editor preferencesEditor = settings.edit();  
		preferencesEditor.putString("usernames", userNames);
		preferencesEditor.commit(); 
	}
}
