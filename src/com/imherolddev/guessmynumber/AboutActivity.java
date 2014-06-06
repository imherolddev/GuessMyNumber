package com.imherolddev.guessmynumber;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		 View decorView = getWindow().getDecorView();
	     // Hide both the navigation bar and the status bar.
	     // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
	     // a general rule, you should design your app to hide the status bar whenever you
	     // hide the navigation bar.
	     int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
	                   | View.SYSTEM_UI_FLAG_FULLSCREEN;
	     decorView.setSystemUiVisibility(uiOptions);
		
	}
	
}
