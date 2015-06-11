package com.idarko.streamapp;

import android.os.Bundle;

import com.idarko.streamapp.widget.ActionBar;

public class InfoActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.info);
	  ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	  actionBar.setTitle("Info");
	}
}
