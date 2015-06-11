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
import com.idarko.streamapp.model.HashTagCollection;
import com.idarko.streamapp.widget.ActionBar;

public class HashTagCollectionHashTagsActivity extends BaseActivity {
private HashTagCollection hashTagCollection;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.hash_tag_collection_hash_tags);
	  Bundle intentExtras = getIntent().getExtras();
	  int hashTagCategoryIndex = intentExtras.getInt(StreamAppIntents.HASHTAG_CATEGORY_INDEX);
	  int hashTagSectionIndex = intentExtras.getInt(StreamAppIntents.HASHTAG_SECTION_INDEX);
	  int hashTagCollectionIndex = intentExtras.getInt(StreamAppIntents.HASHTAG_COLLECTION_INDEX);
	  ApplicationData applicationData = ApplicationDataManager.getInstance().getApplicationData();
	  hashTagCollection = applicationData.getHashtagCategories().get(hashTagCategoryIndex).getHashTagSections().get(hashTagSectionIndex).getHashTagCollections().get(hashTagCollectionIndex);
	  ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	  actionBar.setTitle(hashTagCollection.getName());
	  ListView listView = (ListView)findViewById(R.id.list_view);
	  ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.hash_tag_collection_hash_tags_list_item, hashTagCollection.getHashTags());
	  listView.setAdapter(listAdapter);
	  listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent hashTagIntent = new Intent(getApplicationContext(), SearchActivity.class);
		    	String hashTag = hashTagCollection.getHashTags().get(position);
		  	  	hashTagIntent.putExtra(StreamAppIntents.SEARCH_TERM, "#" + hashTag);
		    	startActivity(hashTagIntent);
		    }
		});
	}
}
