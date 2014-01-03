package com.example.allconnector;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.example.hearthstone.DownloadReceiver;
import com.example.hearthstone.StartService;

public class OpenStartServiceMethodClass {

	private Context context;
	private static DownloadReceiver downloadReceiver;
	private static TextView progressTextView;
	
	public OpenStartServiceMethodClass(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	/**
	 * ServiceIntent
	 * 
	 * @return Intent
	 */
	public Intent getStartServiceIntent(){
		getDownloadReceiver();
		Intent intent = new Intent();
		intent.setClass(this.context, StartService.class);
		intent.putExtra("receiver", downloadReceiver);
		return intent;
	}
	
	/**
	 * DownloadReceiver 用來更新介面
	 */
	private void getDownloadReceiver(){
		if(downloadReceiver == null){
			downloadReceiver = new DownloadReceiver(new Handler(), context);
			downloadReceiver.setProgressTextview(progressTextView);
		}
		
	}
	
	/**
	 * TextView 用來通知使用者當下狀態
	 * 
	 * @return TextView
	 */
	public static void setProgressTextView(TextView progressTextView) {
		OpenStartServiceMethodClass.progressTextView = progressTextView;
	}

}
