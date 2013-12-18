package com.example.alllistener;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.example.hearthstone.CardFragment;
import com.example.hearthstone.R;

public class TabLister implements TabListener {
	private CardFragment fragment;
	
	public TabLister(CardFragment fragment){  
        this.fragment = fragment;  
	} 

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		ft.replace(R.id.frameLayout, fragment);
		Log.i("onTabSelected","onTabSelected");
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		ft.remove(fragment); 
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
