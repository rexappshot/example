package com.example.allconnector;

import com.example.hearthstone.DownloadReceiver;
import com.example.hearthstone.R;
import com.example.hearthstone.StartService;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

public class OpenStartServiceMethodClass {

	private Activity activity;
	
	public OpenStartServiceMethodClass(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}
	
	/**
	 * ServiceIntent
	 * 
	 * @return Intent
	 */
	public Intent getStartServiceIntent(){
		Intent intent = new Intent();
		intent.setClass(this.activity, StartService.class);
		intent.putExtra("receiver", this.getDownloadReceiver());
		return intent;
	}
	
	/**
	 * DownloadReceiver 用來更新介面
	 * @return
	 */
	private DownloadReceiver getDownloadReceiver(){
		DownloadReceiver downloadReceiver = new DownloadReceiver(new Handler(), activity);
		downloadReceiver.setProgressTextview(this.getStartProgressTextView());
		
		return downloadReceiver;
	}
	
	
	/**
	 * TextView 用來通知使用者當下狀態
	 * 
	 * @return TextView
	 */
	private TextView getStartProgressTextView(){
		return (TextView) this.activity.findViewById(R.id.hello_world);
	}
}
