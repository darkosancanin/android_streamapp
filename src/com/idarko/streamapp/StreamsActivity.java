package com.idarko.streamapp;

import java.util.List;

import com.idarko.streamapp.model.SectionAndRow;
import com.idarko.streamapp.managers.ApplicationDataManager;
import com.idarko.streamapp.model.ApplicationData;
import com.idarko.streamapp.model.Stream;
import com.idarko.streamapp.model.StreamCategory;
import com.idarko.streamapp.model.StreamSection;
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

public class StreamsActivity extends BaseActivity {
	private List<StreamCategory> streamCategories;
	private int selectedStreamCategoryIndex = 0;
	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.streams);
	  listView = (ListView)findViewById(R.id.list_view);
	  registerForContextMenu(listView);
	  ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	  actionBar.setTitle("Streams");
	  new GetStreamCategoriesTask().execute();
	}
	
	private void setUpStreamCategoryButtons(){
		SegmentedRadioGroup categoriesControl = (SegmentedRadioGroup)findViewById(R.id.categories_control);
		if(streamCategories.size() <= 1){
			categoriesControl.setVisibility(View.GONE);
		}
		int count = 0;
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int buttonWidth = width / streamCategories.size();
		for (StreamCategory streamCategory : streamCategories) {
			RadioButton radioButton = new RadioButton(this);
			radioButton.setId(count);
			if(count == 0) radioButton.setChecked(true);
			radioButton.setWidth(buttonWidth);
			radioButton.setText(streamCategory.getName());
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
				selectedStreamCategoryIndex = checkedId;
				displayStreams(); 
			}
		});
	}
	
	private void displayStreams(){
		StreamCategory streamCategory = streamCategories.get(selectedStreamCategoryIndex);
		SeparatedListAdapter separatedlistAdapter = new SeparatedListAdapter(this, R.layout.streams_section_header);
		for (StreamSection streamSection : streamCategory.getStreamSections()) {
			separatedlistAdapter.addSection(streamSection.getName(), new ArrayAdapter<Stream>(this, R.layout.stream_list_item, streamSection.getStreams()));
		}
		listView.setAdapter(separatedlistAdapter);
		listView.setFastScrollEnabled(true);
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent tweetsIntent = new Intent(getApplicationContext(), StreamTweetsActivity.class);
		    	SeparatedListAdapter listAdapter = (SeparatedListAdapter)listView.getAdapter();
		  	  	SectionAndRow selectedSectionAndRow = listAdapter.getSectionAndRowForPosition(position);
		  	  	tweetsIntent.putExtra(StreamAppIntents.STREAM_CATEGORY_INDEX, selectedStreamCategoryIndex);
		    	tweetsIntent.putExtra(StreamAppIntents.STREAM_SECTION_INDEX, selectedSectionAndRow.section);
		    	tweetsIntent.putExtra(StreamAppIntents.STREAM_INDEX, selectedSectionAndRow.row);
		    	startActivity(tweetsIntent);
		    }
		});
	}
	
	private class GetStreamCategoriesTask extends AsyncTask<Void, Void, ApplicationData> {
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute(){
			progressDialog = ProgressDialog.show(StreamsActivity.this, null, "Loading streams...", true);
		}
		
        @Override
        protected ApplicationData doInBackground(Void... params) {
            return ApplicationDataManager.getInstance().getApplicationData();
        }

        @Override
        protected void onPostExecute(ApplicationData result) {
        	progressDialog.dismiss();
        	progressDialog = null;
        	streamCategories = result.getStreamCategories();
        	setUpStreamCategoryButtons();
        	displayStreams();
            super.onPostExecute(result);
        }
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.streams_context_menu, menu);
	  AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo)menuInfo;
	  SeparatedListAdapter listAdapter = (SeparatedListAdapter)listView.getAdapter();
	  SectionAndRow selectedSectionAndRow = listAdapter.getSectionAndRowForPosition(adapterInfo.position);
	  Stream stream = streamCategories.get(selectedStreamCategoryIndex).getStreamSections().get(selectedSectionAndRow.section).getStreams().get(selectedSectionAndRow.row);
	  menu.setHeaderTitle(stream.getName());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	  case R.id.show_users:
		  AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		  SeparatedListAdapter listAdapter = (SeparatedListAdapter)listView.getAdapter();
		  SectionAndRow selectedSectionAndRow = listAdapter.getSectionAndRowForPosition(adapterInfo.position);
		  Intent streamUsersIntent = new Intent(getApplicationContext(), StreamUsersActivity.class);
	  	  streamUsersIntent.putExtra(StreamAppIntents.STREAM_CATEGORY_INDEX, selectedStreamCategoryIndex);
	  	  streamUsersIntent.putExtra(StreamAppIntents.STREAM_SECTION_INDEX, selectedSectionAndRow.section);
	  	  streamUsersIntent.putExtra(StreamAppIntents.STREAM_INDEX, selectedSectionAndRow.row);
	  	  startActivity(streamUsersIntent);
	  	  return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
}