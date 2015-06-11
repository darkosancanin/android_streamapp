package com.idarko.streamapp.managers;

import com.idarko.streamapp.ApplicationSettings;

public class TweetsManagerFactory {
	public static ITweetsManager CreateTweetsManager(){
		if(ApplicationSettings.IsInDebugMode){
			return new MockTweetsManager();
		}
		else{
			return new TweetsManager();
		}
	}
}
