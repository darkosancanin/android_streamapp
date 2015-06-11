package com.idarko.streamapp.managers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idarko.streamapp.R;
import com.idarko.streamapp.StreamAppApplication;
import com.idarko.streamapp.model.ApplicationData;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.StreamCategory;
import com.idarko.streamapp.model.StreamSection;
import com.idarko.streamapp.model.User;

public final class ApplicationDataManager {
	private static final ApplicationDataManager INSTANCE = new ApplicationDataManager();
	private ApplicationData applicationData = null;
	private HashMap<String, String> fullNameUserLookup = null;
	
	public static ApplicationDataManager getInstance(){
		return INSTANCE;
	}

	public ApplicationData getApplicationData() {
		if(applicationData == null){
			InitializeApplicationData();
		}
		return applicationData;
	}

	private void InitializeApplicationData() {
		InputStreamReader inputStreamReader = getApplicationDataInputStreamReader();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		Gson gson = gsonBuilder.create();
		applicationData = gson.fromJson(inputStreamReader,ApplicationData.class);
		createFullNameUserLookup();
	}
	
	public void createFullNameUserLookup(){
		fullNameUserLookup = new HashMap<String, String>();
		ApplicationData appData = getApplicationData();
		for(StreamCategory streamCategory : appData.getStreamCategories()){
			for(StreamSection streamSection : streamCategory.getStreamSections()){
				for(Stream stream : streamSection.getStreams()){
					for(User user : stream.getUsers()){
						fullNameUserLookup.put(user.getUserName().toLowerCase(), user.getFullName());
					}
				}
			}
		}
	}
	
	public HashMap<String, String> getFullNameUserLookUp(){
		if(fullNameUserLookup == null){
			createFullNameUserLookup();
		}
		return fullNameUserLookup;
	}

	public void setApplicationData(ApplicationData applicationData) {
		this.applicationData = applicationData;
	}
	
	private InputStreamReader getApplicationDataInputStreamReader(){
		InputStream inputStream = StreamAppApplication.getAppContext().getResources().openRawResource(R.raw.application_data);
		return new InputStreamReader(inputStream);
	}
}
