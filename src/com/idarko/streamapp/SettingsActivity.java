package com.idarko.streamapp;

import android.os.Bundle;

import com.idarko.streamapp.widget.ActionBar;

public class SettingsActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.settings);
	  ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	  actionBar.setTitle("Settings");
	}
}
