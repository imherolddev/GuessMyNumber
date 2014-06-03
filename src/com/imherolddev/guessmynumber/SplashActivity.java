package com.imherolddev.guessmynumber;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 3000;
	 
    @SuppressLint("InlinedApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        View decorView = getWindow().getDecorView();
     // Hide both the navigation bar and the status bar.
     // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
     // a general rule, you should design your app to hide the status bar whenever you
     // hide the navigation bar.
     int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                   | View.SYSTEM_UI_FLAG_FULLSCREEN;
     decorView.setSystemUiVisibility(uiOptions);
 
        new Handler().postDelayed(new Runnable() {
        	
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
