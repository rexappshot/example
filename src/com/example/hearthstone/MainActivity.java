package com.example.hearthstone;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.example.allconnector.CardImageLoader;
import com.example.allconnector.WindowsHeightAndWidth;
import com.example.allinterface.LeftClickInterface;
import com.example.allinterface.SearchInterface;
import com.example.alllistener.TabLister;
import com.navdrawer.SimpleSideDrawer;

public class MainActivity extends SherlockFragmentActivity implements LeftClickInterface{

	private static SimpleSideDrawer simpleSideDrawer;
	private static SubMenu cardMenu;
	private static int tabkey;
	private static ActionBar actionBar; 
	private static String[] jobs;
	private ProgressDialog progressDialog;	
	public static SearchInterface searchInterface;
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case android.R.id.home: 
				simpleSideDrawer.openLeftSide();
				break;
			case R.id.search_button:
				searchButtonClickEvent();
				break;				
			case R.id.compose_button:
				Toast.makeText(this, "compose_button_click", Toast.LENGTH_LONG).show();
				break;	
				
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
				
		setAllJobs();
		Bitmap defaultIconBitmap = setSideMenu();
		setStartActionTab(defaultIconBitmap);
		setStartTab();		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, R.id.search_button, 0, "搜尋")
        	.setIcon(R.drawable.ic_search)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		menu.add(0, R.id.compose_button, 0, "牌組")
			.setIcon(R.drawable.ic_compose)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}

	private Bitmap setSideMenu(){
		
		simpleSideDrawer = new SimpleSideDrawer(MainActivity.this);
		simpleSideDrawer.setLeftBehindContentView(R.layout.activity_left_sidemenu);		
		
		return new LeftSideMenuClass(this, this, simpleSideDrawer).createLeftMenu();

	}	
	
	private void setStartActionTab(Bitmap defaultIconBitmap){		
		
		actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle("全部");
	    actionBar.setIcon(new BitmapDrawable(defaultIconBitmap));
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}
	
	private void setStartTab(){
		
		actionBar.removeAllTabs();
		
		for(int i=0; i<10;i++){
			Tab tab = getTab(addCardFragment(jobs[i]),jobs[i]);
			actionBar.addTab(tab);
			
			if(i==0){
				actionBar.selectTab(tab);
			}
			
		}
		
	}
	
	private CardFragment addCardFragment(String job){
		CardFragment cardFragment = new CardFragment();
		
	    Bundle bundle = new Bundle();
	    bundle.putString("job", job);
	    bundle.putInt("actionBarHeight", getActionBarHeight());
	    cardFragment.setArguments(bundle);
	    
	    return cardFragment;
	}
	
	private Tab getTab(CardFragment cardFragment,String job){		
		
		return actionBar.newTab().setText(job).setTabListener(new TabLister(cardFragment));
	}
	
	private void setAllJobs(){
		jobs = getAllJobs();
	}
	
	private String[] getAllJobs(){
		return this.getResources().getStringArray(R.array.jobs);
	}
	
	@Override
	public void leftClickChagneTab(int tabkey ,Drawable drawable){
	
		if(tabkey == jobs.length-1){			
			setStartTab();
			actionBar.setTitle("全部");
		    actionBar.setIcon(drawable);
		}else{
			actionBar.removeAllTabs();
			changeActionTab(tabkey);
			changeActionTitle(tabkey,drawable);
		}
		
		
	}
	
	private void changeActionTitle(int tabkey, Drawable drawable){
		
		actionBar.setTitle(jobs[tabkey]);
	    actionBar.setIcon(drawable);		
		
	}
	
	private void changeActionTab(int tabkey){		
		
		Tab tab = getTab(addCardFragment(jobs[tabkey]), jobs[tabkey]);		
		Tab tab2 = getTab(addCardFragment(jobs[jobs.length-1]), jobs[jobs.length-1]);
		actionBar.addTab(tab);
		actionBar.addTab(tab2);
		actionBar.selectTab(tab);
		
	}
	
	private int getActionBarHeight(){
		
		TypedValue tv = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
		return TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

	}
	
	private void searchButtonClickEvent(){
		/*
		Builder alertDialogbBuilder = new AlertDialog.Builder(this);
		AlertDialog alertDialog = alertDialogbBuilder.create();
		alertDialog.show();
		alertDialog.setContentView(R.layout.dialog_search);
		Window dialogWindow = alertDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		lp.x = 0; // 新位置X坐標
		lp.y = (int)(WindowsHeightAndWidth.height*0.02);; // 新位置Y坐標
		lp.width = (int)(WindowsHeightAndWidth.width*0.8); // 寬度
		lp.height = (int)(WindowsHeightAndWidth.height*0.5); // 高度
		lp.alpha = 1.0f; // 透明度
		alertDialog.onWindowAttributesChanged(lp);
		  //设置点击外围消散
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setCancelable(false);
		*/
		
		Dialog myDialog = new Dialog(this);
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		myDialog.setContentView(R.layout.dialog_search);
		
		Window dialogWindow = myDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		lp.x = 0; // 新位置X坐標
		lp.y = (int)(WindowsHeightAndWidth.height*0.015);; // 新位置Y坐標
		lp.width = (int)(WindowsHeightAndWidth.width*0.8); // 寬度
		lp.height = (int)(WindowsHeightAndWidth.height*0.7); // 高度
		lp.alpha = 0.9f; // 透明度
		myDialog.onWindowAttributesChanged(lp);
		  //设置点击外围消散
		myDialog.setCanceledOnTouchOutside(false);
		myDialog.setCancelable(false);

		myDialog.show();
		
	}
	/*
	public void passInterface(SearchInterface searchInterface){
		this.searchInterface = searchInterface;
	}*/
		

}
