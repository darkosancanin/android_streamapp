package com.idarko.streamapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.idarko.streamapp.managers.ApplicationDataManager;
import com.idarko.streamapp.model.ApplicationData;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.User;
import com.idarko.streamapp.widget.ActionBar;

public class StreamUsersActivity extends BaseActivity {
	private Stream stream;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.stream_users);
	  Bundle intentExtras = getIntent().getExtras();
	  int streamCategoryIndex = intentExtras.getInt(StreamAppIntents.STREAM_CATEGORY_INDEX);
	  int streamSectionIndex = intentExtras.getInt(StreamAppIntents.STREAM_SECTION_INDEX);
	  int streamIndex = intentExtras.getInt(StreamAppIntents.STREAM_INDEX);
	  ApplicationData applicationData = ApplicationDataManager.getInstance().getApplicationData();
	  stream = applicationData.getStreamCategories().get(streamCategoryIndex).getStreamSections().get(streamSectionIndex).getStreams().get(streamIndex);
	  ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	  actionBar.setTitle(stream.getName());
	  ListView listView = (ListView)findViewById(R.id.list_view);
	  ArrayAdapter<User> listAdapter = new ArrayAdapter<User>(this, R.layout.stream_user_list_item, stream.getUsers());
	  listView.setAdapter(listAdapter);
	  listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent userIntent = new Intent(getApplicationContext(), UserActivity.class);
		    	User user = stream.getUsers().get(position);
		  	  	userIntent.putExtra(StreamAppIntents.USER, user);
		    	startActivity(userIntent);
		    }
		});
	}
}
