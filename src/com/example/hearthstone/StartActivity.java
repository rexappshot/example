package com.example.hearthstone;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import com.example.allconnector.CustomDialogClass;
import com.example.allconnector.OpenStartServiceMethodClass;
import com.example.allconnector.WindowsHeightAndWidth;

public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
				
		new WindowsHeightAndWidth(this);
		
		startService(new OpenStartServiceMethodClass(this).getStartServiceIntent());
		

	}	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
