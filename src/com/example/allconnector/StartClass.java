package com.example.allconnector;

import java.io.File;

import com.example.allinterface.BaseCheck;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
		File database=context.getDatabasePath("hearthstone.example.db");

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

}
