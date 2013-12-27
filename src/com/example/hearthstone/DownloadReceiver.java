package com.example.hearthstone;

import com.example.allconnector.CustomDialogClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DownloadReceiver extends ResultReceiver {

	private Context context;
	private TextView textView;
	public DownloadReceiver(Handler handler,Context context) {
		
		super(handler);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	public void setProgressTextview(TextView textView){
		this.textView = textView;
	}
	
	@Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
  
        //TextView textView = (TextView) activity.findViewById(R.string.hello_world);
        
        if (resultCode == StartService.UPDATE_PROGRESS) {
        	//給使用者提示下載時的變化
        	
        	if(textView != null){
	        	String progres = resultData.getString("progress");
	        	//Log.i("onReceiveResult","progres:"+progres);
	        	textView.setText(progres);
        	}
        	/*
            int progress = resultData.getInt("progress");
            mProgressDialog.setProgress(progress);
            if (progress == 100) {
                mProgressDialog.dismiss();
            }
            */
        }else if(resultCode == StartService.FINISH){
        	try {
    			Thread.currentThread();
    			Thread.sleep(2000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	Intent intent= new Intent(context,MainActivity.class);
        	context.startActivity(intent);
        	((Activity)context).finish();
        	Log.i("DownloadReceiver","DownloadReceiver:FINISH");
        }else if(resultCode == StartService.CLOSE_APP){
        	
        	
        	CustomDialogClass customDialog = new CustomDialogClass(context);
        	customDialog.setTitleText("偵測不到網路");
			customDialog.setDescriptionText("第一次進入此APP必須下載必要的資料,請開啟網路再使用!");
			OnClickListener onClickListener = new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((Activity)context).finish();
				}
			};
			customDialog.setBaseOneButtonLinearLayout("確定", onClickListener);
			customDialog.getDialog().show();
        }else if(resultCode == StartService.OPEN_WEB){
        	
        	CustomDialogClass customDialog = new CustomDialogClass(context);
			OnClickListener onClickListener = new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getResources().getString(R.string.googleplay_market_download_url)));
		        	context.startActivity(browserIntent);
				}
			};
			customDialog.setTitleText("通知");
			customDialog.setDescriptionText("有新的版本囉!\n請去google play更新喔...");
			customDialog.setBaseOneButtonLinearLayout("確定", onClickListener);
			customDialog.getDialog().show();
        	
        	
        }
    }

}
