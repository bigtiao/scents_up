package com.example.scents_up.tabs;


import com.example.scents_up.R;

import android.app.Activity;
import android.os.Bundle;

import android.widget.Gallery;


public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  setContentView(R.layout.gallery_main);

	        Gallery gallery=(Gallery)findViewById(R.id.gallery);
	        gallery.setAdapter(new galleryAdapter(this));
	        gallery.setSelection(1);//设置第四个图标居中
	        gallery.setSpacing(20);//图标之间的距离
	    }
	
	
	}