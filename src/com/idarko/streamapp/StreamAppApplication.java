package com.idarko.streamapp;

import android.app.Application;
import android.content.Context;

public class StreamAppApplication extends Application {
	private static Context context = null;
	
	@Override
	public void onCreate(){
		super.onCreate();
		context=getApplicationContext();
    }
	
	public static Context getAppContext(){
		return context;
	}
}
