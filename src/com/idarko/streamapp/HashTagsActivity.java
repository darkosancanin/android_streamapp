package com.idarko.streamapp;

import java.util.List;

import com.idarko.streamapp.model.SectionAndRow;
import com.idarko.streamapp.managers.ApplicationDataManager;
import com.idarko.streamapp.model.ApplicationData;
import com.idarko.streamapp.model.HashTagCollection;
import com.idarko.streamapp.model.HashTagsCategory;
import com.idarko.streamapp.model.HashTagsSection;
import com.idarko.streamapp.widget.ActionBar;
import com.idarko.streamapp.widget.SegmentedRadioGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HashTagsActivity extends BaseActivity {
	private List<HashTagsCategory> hashTagsCategories;
	private int selectedHashtagsCategoryIndex = 0;
	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.hashtags);
	  listView = (ListView)findViewById(R.id.list_view);
	  registerForContextMenu(listView);
	  ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	  actionBar.setTitle("Hash Tags");
	  new GetHashTagCategoriesTask().execute();
	}
	
	private void setUpHashTagCategoryButtons(){
		SegmentedRadioGroup categoriesControl = (SegmentedRadioGroup)findViewById(R.id.categories_control);
		if(hashTagsCategories.size() <= 1){
			categoriesControl.setVisibility(View.GONE);
		}
		int count = 0;
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int buttonWidth = width / hashTagsCategories.size();
		for (HashTagsCategory hashTagsCategory : hashTagsCategories) {
			RadioButton radioButton = new RadioButton(this);
			radioButton.setId(count);
			if(count == 0) radioButton.setChecked(true);
			radioButton.setWidth(buttonWidth);
			radioButton.setText(hashTagsCategory.getName());
			radioButton.setTextAppearance(getBaseContext(), android.R.style.TextAppearance_Small);
			radioButton.setMinimumHeight(Math.round(this.getResources().getDisplayMetrics().density * 33));
			radioButton.setButtonDrawable(android.R.color.transparent);
			radioButton.setGravity(Gravity.CENTER);
			radioButton.setTextColor(R.color.radio_colors);
			categoriesControl.addView(radioButton);
			count ++;
		}
		categoriesControl.changeButtonsImages();
		categoriesControl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				selectedHashtagsCategoryIndex = checkedId;
				displayHashTags(); 
			}
		});
	}
	
	private void displayHashTags(){
		HashTagsCategory hashTagsCategory = hashTagsCategories.get(selectedHashtagsCategoryIndex);
		SeparatedListAdapter separatedlistAdapter = new SeparatedListAdapter(this, R.layout.hashtags_section_header);
		for (HashTagsSection hashTagsSection : hashTagsCategory.getHashTagSections()) {
			separatedlistAdapter.addSection(hashTagsSection.getName(), new ArrayAdapter<HashTagCollection>(this, R.layout.hashtags_list_item, hashTagsSection.getHashTagCollections()));
		}
		listView.setAdapter(separatedlistAdapter);
		listView.setFastScrollEnabled(true);
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent tweetsIntent = new Intent(getApplicationContext(), HashTagCollectionTweetsActivity.class);
		    	SeparatedListAdapter listAdapter = (SeparatedListAdapter)listView.getAdapter();
		  	  	SectionAndRow selectedSectionAndRow = listAdapter.getSectionAndRowForPosition(position);
		  	  	tweetsIntent.putExtra(StreamAppIntents.HASHTAG_CATEGORY_INDEX, selectedHashtagsCategoryIndex);
		    	tweetsIntent.putExtra(StreamAppIntents.HASHTAG_SECTION_INDEX, selectedSectionAndRow.section);
		    	tweetsIntent.putExtra(StreamAppIntents.HASHTAG_COLLECTION_INDEX, selectedSectionAndRow.row);
		    	startActivity(tweetsIntent);
		    }
		});
	}
	
	private class GetHashTagCategoriesTask extends AsyncTask<Void, Void, ApplicationData> {
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute(){
			progressDialog = ProgressDialog.show(HashTagsActivity.this, null, "Loading hash tags...", true);
		}
		
        @Override
        protected ApplicationData doInBackground(Void... params) {
            return ApplicationDataManager.getInstance().getApplicationData();
        }

        @Override
        protected void onPostExecute(ApplicationData result) {
        	progressDialog.dismiss();
        	progressDialog = null;
        	hashTagsCategories = result.getHashtagCategories();
        	setUpHashTagCategoryButtons();
        	displayHashTags();
            super.onPostExecute(result);
        }
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.hashtags_context_menu, menu);
	  AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo)menuInfo;
	  SeparatedListAdapter listAdapter = (SeparatedListAdapter)listView.getAdapter();
	  SectionAndRow selectedSectionAndRow = listAdapter.getSectionAndRowForPosition(adapterInfo.position);
	  HashTagCollection hashTagCollection = hashTagsCategories.get(selectedHashtagsCategoryIndex).getHashTagSections().get(selectedSectionAndRow.section).getHashTagCollections().get(selectedSectionAndRow.row);
	  menu.setHeaderTitle(hashTagCollection.getName());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	  case R.id.show_hash_tags:
		  AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		  SeparatedListAdapter listAdapter = (SeparatedListAdapter)listView.getAdapter();
		  SectionAndRow selectedSectionAndRow = listAdapter.getSectionAndRowForPosition(adapterInfo.position);
		  Intent hashTagCollectionHashTagsIntent = new Intent(getApplicationContext(), HashTagCollectionHashTagsActivity.class);
	  	  hashTagCollectionHashTagsIntent.putExtra(StreamAppIntents.HASHTAG_CATEGORY_INDEX, selectedHashtagsCategoryIndex);
	  	  hashTagCollectionHashTagsIntent.putExtra(StreamAppIntents.HASHTAG_SECTION_INDEX, selectedSectionAndRow.section);
	  	  hashTagCollectionHashTagsIntent.putExtra(StreamAppIntents.HASHTAG_COLLECTION_INDEX, selectedSectionAndRow.row);
	  	  startActivity(hashTagCollectionHashTagsIntent);
	  	  return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
}