package com.imherolddev.guessmynumber;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
	}
	
	public void goBack(View v) {
		
		this.finish();
		
	}
	
}
