package com.example.scents_up.tabs;

import com.example.scents_up.R;

import android.app.Activity;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;

import android.widget.TextView;


public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
//		TextView textView=new TextView(this);
//		textView.setText("这是首页！");
//		setContentView(textView);
		TextView tv = (TextView)findViewById(R.id.jianjietv);
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	
	
}