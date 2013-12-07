package com.example.scents_up.tabs;


import com.example.scents_up.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class MyInfoActivity extends Activity implements MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener { 
	
	public static final String TAG = "VideoPlayer";
	private VideoView mVideoView;
	private int mPositionWhenPaused = -1;
	private MediaController mMediaController;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		this.setContentView(R.layout.video_main);
		
	
		
		mVideoView = (VideoView)findViewById(R.id.video_view);
		
		String pathString = Environment.getExternalStorageDirectory().getPath();
		mVideoView.setVideoPath(pathString + "/" + "xc_xc.mp4");

		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);
		
}
	
	public void onStart(){
		
		mVideoView.start();
		
		super.onStart();
	}
	
	public void onPause(){
		mPositionWhenPaused = mVideoView.getCurrentPosition();
		mVideoView.stopPlayback();
		Log.d(TAG,"OnStop: mPositionWhenPaused = " + mPositionWhenPaused);
		Log.d(TAG, "OnStop: getDuration  = " + mVideoView.getDuration());
		super.onPause();
	}
	
	public void onResume(){
		if(mPositionWhenPaused >= 0) {
    		mVideoView.seekTo(mPositionWhenPaused);
    		mPositionWhenPaused = -1;
	}
	super.onResume();
	}


	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}


	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}
	

}

