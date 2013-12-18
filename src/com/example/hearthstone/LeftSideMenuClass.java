package com.example.hearthstone;

import com.example.allinterface.LeftClickInterface;
import com.navdrawer.SimpleSideDrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

public class LeftSideMenuClass {
	
	private Activity activity;	
	private SimpleSideDrawer simpleSideDrawer;
	private LeftClickInterface leftClickInterface;
	public static String showTabkey;

	public LeftSideMenuClass(Activity activity, LeftClickInterface leftClickInterface, SimpleSideDrawer simpleSideDrawer) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.simpleSideDrawer = simpleSideDrawer;
		this.leftClickInterface = leftClickInterface;
	}
	
	/**
	 * 主要方法
	 */
	public void createLeftMenu(){
		
		for(int i=0; i<10; i++) {
		    String LinearlayoutID = "Linearlayout"+i;
		    int resID = activity.getResources().getIdentifier(LinearlayoutID, "id", activity.getPackageName());
		    //linearLayoutArray[i] = ((LinearLayout) findViewById(resID));
		    ((LinearLayout) activity.findViewById(resID)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					leftButtonClickEvent(v);
				}
			});
		}
		
	}
	
	private void leftButtonClickEvent(View v){
		if(showTabkey != null){
			View oldvView = v.getRootView().findViewWithTag(showTabkey);
			oldvView.setBackgroundDrawable(null);
		}
		showTabkey = v.getTag().toString();
		leftClickInterface.leftClickChagneTab(Integer.valueOf(showTabkey));
		v.setBackgroundColor(Color.BLUE);
		simpleSideDrawer.closeLeftSide();		
	}

}
