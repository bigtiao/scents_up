package com.example.scents_up.tabs;


import com.example.scents_up.R;
import com.example.scents_up.service.UpdateManager;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_main);
		
		Button upButton = (Button)findViewById(R.id.updatecheck_btn);
		upButton.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				UpdateManager manager = new UpdateManager(MoreActivity.this);
				// 检查软件更新
				manager.checkUpdate();
			}
		});
	
	
	}
	
}

