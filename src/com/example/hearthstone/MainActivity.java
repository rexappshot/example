package com.example.hearthstone;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.example.allinterface.LeftClickInterface;
import com.example.alllistener.TabLister;
import com.navdrawer.SimpleSideDrawer;

public class MainActivity extends SherlockFragmentActivity implements LeftClickInterface{
	
	private static SimpleSideDrawer simpleSideDrawer;
	private static SubMenu cardMenu;
	private static String showTabkey;
	private static ActionBar actionBar; 
	private static String[] jobs;
	private ProgressDialog progressDialog;
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case android.R.id.home: 
				simpleSideDrawer.openLeftSide();
				break;
			case R.id.search_button:
				Toast.makeText(this, "search_button_click", Toast.LENGTH_LONG).show();
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
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		setAllJobs();
		setSideMenu();
		setStartActionTab();
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

	private void setSideMenu(){
		
		simpleSideDrawer = new SimpleSideDrawer(MainActivity.this);
		simpleSideDrawer.setLeftBehindContentView(R.layout.activity_left_sidemenu);
		
		new LeftSideMenuClass(this, this, simpleSideDrawer).createLeftMenu();

	}	
	
	private void setStartActionTab(){		
		
		actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle("全部");
	    actionBar.setIcon(R.drawable.tool_icon);
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}
	
	private void setStartTab(){
		
		actionBar.removeAllTabs();
		
		for(int i=0; i<10;i++){
			Tab tab = getTab(addCardFragment(i),jobs[i]);
			actionBar.addTab(tab);
			
			if(i==0){
				actionBar.selectTab(tab);
			}
			
		}
		
	}
	
	private CardFragment addCardFragment(int key){
		
		CardFragment cardFragment = new CardFragment();
	    Bundle bundle = new Bundle();
	    bundle.putInt("key", key);
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
	public void leftClickChagneTab(int tabkey){
				
		if(tabkey == jobs.length-1){			
			setStartTab();
			actionBar.setTitle("全部");
		    actionBar.setIcon(R.drawable.ic_search);
		}else{
			changeActionTab(tabkey);
			changeActionTitle(tabkey);
		}
		
		
	}
	
	private void changeActionTitle(int tabkey){
		
		actionBar.setTitle(jobs[tabkey]);
	    actionBar.setIcon(R.drawable.ic_search);		
		
	}
	
	private void changeActionTab(int tabkey){
		
		actionBar.removeAllTabs();
		Tab tab = getTab(addCardFragment(tabkey), jobs[tabkey]);		
		Tab tab2 = getTab(addCardFragment(jobs.length-1), jobs[jobs.length-1]);
		actionBar.addTab(tab);
		actionBar.addTab(tab2);
		actionBar.selectTab(tab);
		
	}

}
