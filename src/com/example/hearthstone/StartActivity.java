package com.example.hearthstone;

import com.example.allconnector.StartClass;
import com.example.datebase.DBManger;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;

public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Thread startThread = this.createStartThread();
		startThread.start();	
		
	}	
	
	private void startcheck(StartClass startClass){
		
		/**
		 * 奧利佛 景田 卡莫
		 * 1.確認DateBase是不是存在
		 * 2.前往google更新 自製dialog提醒
		 * 3.下載資料
		 */

		
		Boolean bHasNetwork = startClass.bHasNetwork(); 
		Boolean bHasDateBase = startClass.bHasDateBase(); 
		Log.i("StartActivity","bHasNetwork:"+bHasNetwork);
		bHasDateBase = true;
		//確認網路
		if(bHasNetwork){
			if(bHasDateBase){
				boolean bIsConnect = startClass.connect(getResources().getString(R.string.version_url));
				String sVersion = startClass.getVersion();
				String sNetworkVersion = startClass.getJsonObjectString("version");
				Log.i("StartActivity","sNetworkVersion:"+sNetworkVersion);
				Log.i("StartActivity","sVersion:"+sVersion);
				if(sNetworkVersion.equals(sVersion)){
					//沒有google新版本
					String sNetworkDateBaseVersion = startClass.getJsonObjectString("datebase_version");					
					String sDateBaseVersion = startClass.getDateBaseVersion();
					Log.i("StartActivity","sNetworkDateBaseVersion:"+sNetworkDateBaseVersion);
					Log.i("StartActivity","sDateBaseVersion:"+sDateBaseVersion);
					if(sDateBaseVersion.equals(sNetworkDateBaseVersion)){
					//要更新DateBase	
					}else{
					//不需要更新 進
						
					}
				}else{					
					//去google更新
				}
			}else{
				//下載資料
			}
		}else{
			if(bHasDateBase){
				//有DB 進
			}else{
				//沒有DB 退出
			}
		}
	}
	
	private Thread createStartThread(){

		Runnable startRunnable= new Runnable() {
			public void run() {
				StartClass startClass = new StartClass(StartActivity.this);
				startcheck(startClass);	
			}
		};
		
		Thread startThread = new Thread(startRunnable);
		
		return startThread;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
