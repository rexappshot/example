package com.example.hearthstone;

import com.example.allconnector.StartClass;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		StartClass startClass = new StartClass(this);
		startcheck(startClass);
		/*
		//如果有網路的話
		if(bHasNetwork()){
			//確認有沒有DB
			if(bHasDateBase){
				//有DB
				
				//確認有沒有新版本
				if(bHasNewVersion){
					//有新版本
					
					//下載資料
					
				}else{
					//沒有新版本
					
				}
				
			}else{
				//沒有DB
				
				//下載資料
			}
		}else{
			
			//確認有沒有DB
			if(bHasDateBase){
				//有DB
			
			}else{
				//沒有
				
			}
		}
		*/
	}
	
	private void startcheck(StartClass startClass){
		
		Boolean bHasNetwork = startClass.bHasNetwork(); 
		Boolean bHasDateBase = startClass.bHasDateBase(); 
		
		//確認網路
		if(bHasNetwork){
			if(bHasDateBase){
				boolean bIsConnect = startClass.connect(getResources().getString(R.string.version_url));
				String sVersion = startClass.getVersion();
				String sNetworkVersion = startClass.getJsonObjectString("version");
				if(sNetworkVersion.equals(sVersion)){
					//沒有google新版本
					String sNetworkDaterBaseVersion = startClass.getJsonObjectString("datebase_version");
					/**
					 * SQL get DateBaseVersion
					 */
					if(startClass.bNeedFreshDateBase){
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
