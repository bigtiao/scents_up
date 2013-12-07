package com.example.scents_up;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AppStart extends Activity {

	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		 //requestWindowFeature( Window.FEATURE_NO_TITLE );
		 final View view = View.inflate(this, R.layout.start, null);
			setContentView(view);
	        
			//渐变展示启动屏，这里是我的github
			AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
			aa.setDuration(3000);
			view.startAnimation(aa);
			aa.setAnimationListener(new AnimationListener()
			{
				
				public void onAnimationEnd(Animation arg0) {
					redirectTo();
				}
				
				public void onAnimationRepeat(Animation animation) {}
				
				public void onAnimationStart(Animation animation) {}
				
			});
	}
	
	 /**
     * 跳转到...
     */
	protected void redirectTo() {
		Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
	}

}
