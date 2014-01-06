package com.example.hearthstone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.R.integer;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.example.allconnector.CardImageLoader;
import com.example.allconnector.WindowsHeightAndWidth;
import com.example.allinterface.CardClass;
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
	private final String[] spinnerBaseItem = {"無限制","0","1","2","3","4","5","6","7以上"};
	private final String[] spinnerTypeItem = {"無限制","法術","手下","武器"};
	private final String[] spinnerLevelItem = {"無限制","免費","普通","精良","史詩","傳說"};
	private final String[] searchFields ={"name","mana","attack","heath","type","rare"};
	private Dialog myDialog;
	private Menu menu;
	private boolean showMenuJobButton = false;
	private boolean showMenuSearchButton = true;
	private boolean showMenuCustomCardButton = false;
	private static RightSideMenuClass rightSideMenuClass;
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.i("onOptionsItemSelected","onOptionsItemSelected item.getItemId():"+item.getItemId());
		switch(item.getItemId()){
			case android.R.id.home: 
				simpleSideDrawer.openLeftSide();
				break;
			case R.id.search_button:
				searchButtonClickEvent();
				break;				
			case R.id.compose_button:
				simpleSideDrawer.openRightSide();
				break;	
			case R.id.hero_button_0:				
				cardJobChange(0,HeroImageClass.getHeroBitmapCahce().get(0));
				break;	
			case R.id.hero_button_1:
				cardJobChange(1,HeroImageClass.getHeroBitmapCahce().get(1));
				break;
			case R.id.hero_button_2:
				cardJobChange(2,HeroImageClass.getHeroBitmapCahce().get(2));
				break;
			case R.id.hero_button_3:
				cardJobChange(3,HeroImageClass.getHeroBitmapCahce().get(3));
				break;
			case R.id.hero_button_4:
				cardJobChange(4,HeroImageClass.getHeroBitmapCahce().get(4));
				break;
			case R.id.hero_button_5:
				cardJobChange(5,HeroImageClass.getHeroBitmapCahce().get(5));
				break;
			case R.id.hero_button_6:
				cardJobChange(6,HeroImageClass.getHeroBitmapCahce().get(6));
				break;
			case R.id.hero_button_7:
				cardJobChange(7,HeroImageClass.getHeroBitmapCahce().get(7));
				break;
			case R.id.hero_button_8:
				cardJobChange(8,HeroImageClass.getHeroBitmapCahce().get(8));
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
		setSearchDialog();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		this.menu = menu;
		new HeroImageClass(this);
		ArrayList<Drawable> HeroBitmapCahce = HeroImageClass.getHeroBitmapCahce();
		
		if(showMenuJobButton){
		SubMenu subMenu1 = menu.addSubMenu(0, R.id.hero_button_parent, 1, "職業").setIcon(HeroBitmapCahce.get(0));
		for(int i=0; i<HeroBitmapCahce.size(); i++){
			subMenu1.add(0, getItemId(i, getPackageName()), 0, jobs[i])
			    	.setIcon(HeroBitmapCahce.get(i));
			    	//.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		
		
		MenuItem subMenu1Item = subMenu1.getItem();
		//subMenu1Item.setIcon(R.drawable.ic_compose);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		if(showMenuSearchButton){
			menu.add(0, R.id.search_button, 2, "搜尋")
	        	.setIcon(R.drawable.ic_search)
	        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
			
		if(showMenuCustomCardButton){
		menu.add(0, R.id.compose_button, 3, "牌組")
			.setIcon(R.drawable.ic_compose)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		
		return true;
	}
	
	private int getItemId(int lastNum, String packageName){
		return getResources().getIdentifier("hero_button_"+lastNum, "id", packageName);
	}

	private Bitmap setSideMenu(){
		
		simpleSideDrawer = new SimpleSideDrawer(MainActivity.this);
		simpleSideDrawer.setLeftBehindContentView(R.layout.activity_left_sidemenu);	
		simpleSideDrawer.setRightBehindContentView(R.layout.activity_right_sidemenu);
		
		return new LeftSideMenuClass(this, this, simpleSideDrawer).createLeftMenu();

	}	
	
	private void setStartActionTab(Bitmap defaultIconBitmap){		
		
		actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle("瀏覽卡片");
	    actionBar.setIcon(new BitmapDrawable(defaultIconBitmap));
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}
	
	private void setStartTab(){
		
		actionBar.removeAllTabs();
		final int modeId = 0;
		
		for(int i=0; i<10;i++){
			Tab tab = getTab(addCardFragment(jobs[i]),jobs[i], modeId);
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
	
	private Tab getTab(CardFragment cardFragment,String job,int modeId){	
		
		TabLister.modeId = modeId;
		return actionBar.newTab().setText(job).setTabListener(new TabLister(cardFragment));
	}
	
	private void setAllJobs(){
		jobs = getAllJobs();
	}
	
	private String[] getAllJobs(){
		return this.getResources().getStringArray(R.array.jobs);
	}
	
	private void cardJobChange(int tabkey ,Drawable drawable){
		
		final int modeId = 1;
		menu.findItem(R.id.hero_button_parent).setIcon(drawable);
		actionBar.removeAllTabs();
		changeActionTab(tabkey, modeId);
		
	}
	
	@Override
	public void leftClickChagneTab(int modeId ,Drawable drawable){
			
		changeMode(modeId);		
		
		if(modeId == 0){			
			setStartTab();
			actionBar.setTitle(getResources().getStringArray(R.array.leftmenu)[modeId]);
		    actionBar.setIcon(drawable);
		}else{
			actionBar.removeAllTabs();
			changeActionTab(0, modeId);
			changeActionTitle(getResources().getStringArray(R.array.leftmenu)[modeId],drawable);
		}
		
		
	}
	
	private void changeMode(int modeId){
		
		//reset
		showMenuJobButton = false;
		showMenuSearchButton = false;
		showMenuCustomCardButton = false;
		
		switch(modeId){
		
			//瀏覽牌組
			case 0:				
				showMenuSearchButton = true;
				break;
			//自訂牌組	
			case 1:
				showMenuJobButton = true;
				showMenuSearchButton = true;
				showMenuCustomCardButton = true;
				break;
			//競技場模式	
			case 2:
				showMenuCustomCardButton = true;
				break;
		}
		
		invalidateOptionsMenu();
	}
	
	private void changeActionTitle(String title, Drawable drawable){
		
		actionBar.setTitle(title);
	    actionBar.setIcon(drawable);		
		
	}
	
	private void changeActionTab(int tabkey, int modeId){		
		
		Tab tab = getTab(addCardFragment(jobs[tabkey]), jobs[tabkey], modeId);		
		Tab tab2 = getTab(addCardFragment(jobs[jobs.length-1]), jobs[jobs.length-1], modeId);
		actionBar.addTab(tab);
		actionBar.addTab(tab2);
		actionBar.selectTab(tab);
		
	}
	
	private int getActionBarHeight(){
		
		TypedValue tv = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
		return TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

	}
	
	private void setSearchDialog(){
		
		myDialog = new Dialog(this);
		
		setBaseDialogSetting();		
		setDialogChild();		
		
	}
	
	private void setDialogChild(){
		
		ArrayAdapter<String> arrayBaseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerBaseItem);
		ArrayAdapter<String> arrayLevelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerLevelItem);
		ArrayAdapter<String> arrayTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerTypeItem);
		
		Spinner spinner1 = (Spinner)myDialog.findViewById(R.id.spinner1);		
		spinner1.setAdapter(arrayBaseAdapter);
		
		Spinner spinner2 = (Spinner)myDialog.findViewById(R.id.spinner2);
		spinner2.setAdapter(arrayBaseAdapter);
		
		Spinner spinner3 = (Spinner)myDialog.findViewById(R.id.spinner3);
		spinner3.setAdapter(arrayBaseAdapter);
		
		Spinner spinner4 = (Spinner)myDialog.findViewById(R.id.spinner4);
		spinner4.setAdapter(arrayTypeAdapter);
		
		Spinner spinner5 = (Spinner)myDialog.findViewById(R.id.spinner5);
		spinner5.setAdapter(arrayLevelAdapter);
		
		Button buttonClear = (Button)myDialog.findViewById(R.id.buttonClear);		
		buttonClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewGroup viewGroup = (ViewGroup) v.getRootView();
				getChildrenAndClear(viewGroup);				
			}
		});
		
		Button buttonSubmit = (Button)myDialog.findViewById(R.id.buttonSubmit);		 
		buttonSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewGroup viewGroup = (ViewGroup) v.getRootView();
				ArrayList<String> arrayList = getAllChildrenValue(new ArrayList<String>() , viewGroup);	
				String where = makeWhereString(arrayList);	
				TabLister.where = where;
				searchInterface.refreshFragment(where);
				
				myDialog.dismiss();
			}
		});
		
	}
	
	private String makeWhereString(ArrayList<String> arrayList){
		
		String whereString = "";
		for(int i=0; i<arrayList.size(); i++){
			if(i==0){
				if((!(arrayList.get(i).isEmpty()))){
					whereString += " AND ( name LIKE '%"+arrayList.get(i)+"%' OR description LIKE '%"+ arrayList.get(i) +"%' ) ";
				}
			}else{ 
				if(!(arrayList.get(i).equals("無限制"))){
					if(arrayList.get(i).equals("7以上")){
						whereString += " AND "+searchFields[i]+" >= '7'";		
					}else{
						whereString += " AND "+searchFields[i]+" = '" + arrayList.get(i) +"'";				
					}
				}
			}
		}
		
		return whereString;
	}
	
	private ArrayList<String> getAllChildrenValue(ArrayList<String> arrayList, ViewGroup viewGroup) {

	    for (int i = 0; i < viewGroup.getChildCount(); i++) {

	    	View view = viewGroup.getChildAt(i);
	    	if (view instanceof EditText) {
	    		arrayList.add(((EditText)view).getText().toString());
		    }else if(view instanceof Spinner){
		    	arrayList.add(((Spinner)view).getSelectedItem().toString());
		    }else if(view instanceof ViewGroup){
		    	getAllChildrenValue(arrayList, (ViewGroup)view);
		    }
	    }

	    return arrayList;
	}
	
	private void getChildrenAndClear(ViewGroup viewGroup) {

	    for (int i = 0; i < viewGroup.getChildCount(); i++) {

	    	View view = viewGroup.getChildAt(i);
	    	if (view instanceof EditText) {
		        ((EditText)view).setText("");
		    }else if(view instanceof Spinner){
		    	((Spinner)view).setSelection(0);
		    }else if(view instanceof ViewGroup){
		    	getChildrenAndClear((ViewGroup)view);
		    }
	    }

	}
	
	private void setBaseDialogSetting(){
		
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		myDialog.setContentView(R.layout.dialog_search);
		
		Window dialogWindow = myDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		lp.x = 0; // 新位置X坐標
		lp.y = (int)(WindowsHeightAndWidth.height*0.015); // 新位置Y坐標
		lp.width = (int)(WindowsHeightAndWidth.width*0.8); // 寬度
		lp.height = (int)(WindowsHeightAndWidth.height*0.7); // 高度
		lp.alpha = 0.9f; // 透明度
		myDialog.onWindowAttributesChanged(lp);
		  //设置点击外围消散
		myDialog.setCanceledOnTouchOutside(false);
		myDialog.setCancelable(false);
		
	}
	
	
	private void searchButtonClickEvent(){		
		
		myDialog.show();
		
	}

	public boolean checkCustomCard(CardClass cardClass){
		
		if(rightSideMenuClass == null){
			rightSideMenuClass = new RightSideMenuClass(this);
		}

		if(cardClass.getRare().equals("傳說")){
			if(rightSideMenuClass.isFullOneCard(Integer.valueOf(cardClass.get_id()))){
				saveCustomCard(cardClass);				
				return true;
			}else{
				Toast.makeText(this, "此卡數量已到達上限", Toast.LENGTH_SHORT).show();				
				return false;
			}
		}else{
			if(rightSideMenuClass.isFullTwoCard(Integer.valueOf(cardClass.get_id()))){
				saveCustomCard(cardClass);
				return true;
			}else{
				Toast.makeText(this, "此卡數量已到達上限", Toast.LENGTH_SHORT).show();
				return false;
			}
		}		
		
	}
	
	private void saveCustomCard(CardClass cardClass){
		rightSideMenuClass.addCard(cardClass);
	}
		

}
