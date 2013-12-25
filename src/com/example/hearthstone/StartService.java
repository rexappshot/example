package com.example.hearthstone;

import java.io.File;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.allconnector.StartServiceMethodClass;
import com.example.allconnector.URLDownloadClass;

public class StartService extends IntentService {

	public static final int UPDATE_PROGRESS = 8344;
	public static final int CLOSE_APP = 1428;
	public static final int FINISH = 7894;
	public static final int OPEN_WEB = 7854;
	private StartServiceMethodClass StartServiceMethodClass = new StartServiceMethodClass(this);
	
	private ResultReceiver receiver;
	
	public StartService() {
		super("StartService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//用來改變顯示狀態
		receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
		
		startcheck();	
	}
	
	//主要function
	protected void startcheck(){

		receiverSendMessage("正在確認網路狀態.....",UPDATE_PROGRESS);
		
		
		//確認網路
		if(StartServiceMethodClass.getIsHasNetwork()){		
			//連結sever 獲取版本資訊
			this.connectVersionSever();
			
			//確認目前架上版本
			if(this.checkAppVersionIsSame()){
				//確認DB版本以及是否存在 如果否 下載 如果是 進下一個Activity 
				if(this.checkDateBaseExist()){					
					if(this.checkDateBaseVersionIsSame()){
						this.accessNextActivity();
						
					}else{
						this.deleteOldData();						
						this.downloadDateBase();
						this.checkDateBaseVersionIsSame();
						this.accessNextActivity();
					}
				}else{					
					this.deleteOldData();
					this.downloadDateBase();
					this.checkDateBaseVersionIsSame();
					this.accessNextActivity();
				}
				
			}else{
				receiverSendMessage("請下載新的版本",OPEN_WEB);
			}			
		}else{		
			//無網路 確認DB是否存在 如果否 關閉APP 如果是進下一個Activity
			if(this.checkDateBaseExist()){				
				this.accessNextActivity();				
			}else{
				receiverSendMessage("程式即將關閉",CLOSE_APP);				
			}
		}
	}
	
	private void deleteOldData(){
		deleteSDCard();
		StartServiceMethodClass.deleteDatabase();		
	}
	
	private void deleteSDCard(){
		receiverSendMessage("有新的更新 正在清除舊資料.....",UPDATE_PROGRESS);
		
		String STORAGE_PATH =android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		String DATEBASE_PATH = getResources().getString(R.string.datebase_path);
		deleteRecursive(new File(STORAGE_PATH+DATEBASE_PATH));
		
	}
	
	private void deleteRecursive(File fileOrDirectory) {
	    if (fileOrDirectory.isDirectory())
	        for (File child : fileOrDirectory.listFiles())
	        	deleteRecursive(child);

	    fileOrDirectory.delete();
	}
	
	private void connectVersionSever(){
		StartServiceMethodClass.connect(getResources().getString(R.string.version_url));
	}
		
	private boolean checkAppVersionIsSame(){
		
		receiverSendMessage("正在確認APP版本.....",UPDATE_PROGRESS);				
		return StartServiceMethodClass.getVersion().equals(StartServiceMethodClass.getJsonObjectString("version"));
		
	}
	
	private boolean checkDateBaseExist(){		
		
		receiverSendMessage("正在確認資料.....",UPDATE_PROGRESS);
		
		String STORAGE_PATH =android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		String DATEBASE_PATH = getResources().getString(R.string.datebase_path);
		File file= new File(STORAGE_PATH+DATEBASE_PATH+getResources().getString(R.string.datebase_name));
		
		return file.exists();
	}
	
	private void downloadDateBase(){
		
		receiverSendMessage("正在準備下載.....",UPDATE_PROGRESS);
		
		String STORAGE_PATH =android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		String DATEBASE_PATH = getResources().getString(R.string.datebase_path);
		URLDownloadClass urlDownloadClass = new URLDownloadClass(receiver,STORAGE_PATH+DATEBASE_PATH);
		urlDownloadClass.setURL(getResources().getString(R.string.datebase_download_url));
		urlDownloadClass.setSavePath(STORAGE_PATH+DATEBASE_PATH+getResources().getString(R.string.rar_name));
		if(urlDownloadClass.startDownload()){
			//解壓縮
			receiverSendMessage("正在解壓縮.....",UPDATE_PROGRESS);
			urlDownloadClass.unZipFile();			
		}
		
	}
	
	private boolean checkDateBaseVersionIsSame(){		
		
		receiverSendMessage("正在確認資料版本.....",UPDATE_PROGRESS);	
		
		Log.i("checkDateBaseVersionIsSame","getJsonObjectString:"+StartServiceMethodClass.getJsonObjectString("datebase_version"));
		Log.i("checkDateBaseVersionIsSame","getDateBaseVersion:"+StartServiceMethodClass.getDateBaseVersion());
		return (StartServiceMethodClass.getJsonObjectFloat("datebase_version")==(StartServiceMethodClass.getDateBaseVersion()));
	}
	
	private void accessNextActivity(){
		
		Bundle resultData = new Bundle();
        //resultData.putInt("progress" ,100);
        receiver.send(FINISH, resultData);
	}
	
	private void receiverSendMessage(String sProgressMessage, int iReceiverCode){
		
		Bundle resultData = new Bundle();
		resultData.putString("progress" ,sProgressMessage);
		receiver.send(iReceiverCode, resultData);
		
	}

}
