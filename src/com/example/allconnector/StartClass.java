package com.example.allconnector;

import java.io.File;

import com.example.allinterface.BaseCheck;
import com.example.datebase.DBManger;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class StartClass extends ConnectClass implements BaseCheck {

	private Context context;
	
	public StartClass(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		this.context = context;
	}

	@Override
	public boolean bHasNetwork() {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo !=null;
	}

	@Override
	public boolean bHasDateBase() {
		// TODO Auto-generated method stub
		File database=context.getDatabasePath("hearthstone.db");

		if (!database.exists()) {
		    return false;
		} else {
		    return true;
		}
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		String sVersionName = null;  
		try{
			sVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		}catch (NameNotFoundException e){
			e.printStackTrace();
		}
		
		return sVersionName;
	}
	
	public String getDateBaseVersion() {
		
		DBManger dbmanger= new DBManger(context);
		SQLiteDatabase sqLiteDatabase = dbmanger.openDateBase();
		Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM version", null);
		
		String version = null;
		
		if(cursor != null){
			//int cursorcount = cursor.getCount();
			//Log.i("StartClass","cursorcount:"+cursorcount);
            if (cursor.moveToFirst()) {
            	version = cursor.getString(cursor.getColumnIndex("version"));
                //Log.i("StartClass","version:"+version);
            }
        }
		
		return version;
	}

}
