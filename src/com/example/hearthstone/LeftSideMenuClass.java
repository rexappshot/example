package com.example.hearthstone;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;

import com.example.allconnector.WindowsHeightAndWidth;
import com.example.allinterface.LeftClickInterface;
import com.navdrawer.SimpleSideDrawer;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeftSideMenuClass {
	
	private Activity activity;	
	private SimpleSideDrawer simpleSideDrawer;
	private LeftClickInterface leftClickInterface;
	public static String showTabkey;
	public LinkedHashMap<String, Bitmap> bitmapCahce;

	public LeftSideMenuClass(Activity activity, LeftClickInterface leftClickInterface, SimpleSideDrawer simpleSideDrawer) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.simpleSideDrawer = simpleSideDrawer;
		this.leftClickInterface = leftClickInterface;
		this.bitmapCahce= new LinkedHashMap<String, Bitmap>();
	}
	
	/**
	 * 主要方法
	 */
	public Bitmap createLeftMenu(){
		
		LinearLayout.LayoutParams herolLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT
				, LayoutParams.WRAP_CONTENT, 1);
		LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT
				, LayoutParams.WRAP_CONTENT, 1);
		imageLayoutParams.setMargins(10, 0, 0, 0);
		LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT
				, LayoutParams.MATCH_PARENT, 2);
		
		final int height = (int) (WindowsHeightAndWidth.getHeight()*0.15);
		final int width = (int) (WindowsHeightAndWidth.getWidth()*0.15);
		
		String baseHeroPath = baseHeroPath();
		String[] leftMenuText = activity.getResources().getStringArray(R.array.leftmenu);
		int[] leftMenuIconId = getDrawableIconId(activity.getResources().getStringArray(R.array.leftmenuicon));
		LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.leftLinearlayout);
		
		
		
		for(int i=0; i<leftMenuText.length; i++) {
			/*
		    String LinearlayoutID = "Linearlayout"+i;
		    int resID = activity.getResources().getIdentifier(LinearlayoutID, "id", activity.getPackageName());
		    */
		    LinearLayout heroLinearLayout = createHeroLinearLayout(i, herolLayoutParams, imageLayoutParams, textLayoutParams
		    		, leftMenuText[i], leftMenuIconFactory(i, height, width, leftMenuIconId[i]));
		    linearLayout.addView(heroLinearLayout);
		    //linearLayoutArray[i] = ((LinearLayout) findViewById(resID));
		    heroLinearLayout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					leftButtonClickEvent(v);
				}
			});
		}
		
		return bitmapCahce.get("i0");
		
	}
	
	private LinearLayout createHeroLinearLayout(int i, LinearLayout.LayoutParams herolLayoutParams
			, LinearLayout.LayoutParams imageLayoutParams , LinearLayout.LayoutParams textLayoutParams, String text, Bitmap bitmap){
		
		LinearLayout linearLayout = new LinearLayout(activity);
		linearLayout.setTag(String.valueOf(i));
		linearLayout.setLayoutParams(herolLayoutParams);
		
		ImageView imageView = new ImageView(activity);
		imageView.setImageBitmap(bitmap);
		imageView.setTag("i"+String.valueOf(i));
		imageView.setLayoutParams(imageLayoutParams);
		
		TextView textView = new TextView(activity);
		textView.setText(text);
		textView.setLayoutParams(textLayoutParams);
		textView.setGravity(Gravity.CENTER);
		
		linearLayout.addView(imageView);
		linearLayout.addView(textView);
		
		return linearLayout;
	}
	
	private int[] getDrawableIconId(String[] leftMenuIcon){
		
		int[] leftMenuIconId = new int[leftMenuIcon.length] ;
		for(int i=0; i<leftMenuIcon.length; i++) {
			Log.i("getDrawableIconId", "getDrawableIconId:"+activity.getResources().getIdentifier(leftMenuIcon[i], "drawable", activity.getPackageName()));
			leftMenuIconId[i] = activity.getResources().getIdentifier(leftMenuIcon[i], "drawable", activity.getPackageName());			
		}
		
		return leftMenuIconId;
	}
	
	private Bitmap leftMenuIconFactory(int i, int height, int width, int drawableId){
		
		Bitmap bitmap = null;
		Bitmap finalBitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			//opts.inJustDecodeBounds = true;		
			bitmap = BitmapFactory.decodeResource(activity.getResources(), drawableId, opts);
			finalBitmap = Bitmap.createScaledBitmap(bitmap, width, width, true);
	
			bitmap.recycle();	    
		    bitmapCahce.put("i"+String.valueOf(i), finalBitmap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return finalBitmap;
	}
	
	private Bitmap heroImageDecode(int i, int height, int width, String basePath){		
		
		Bitmap bitmap = null;
		Bitmap finalBitmap = null;
		
		//File file = new File(basePath+String.valueOf(i));
		
		try {
		    BitmapFactory.Options opts = new BitmapFactory.Options(); /*
		    opts.inJustDecodeBounds = true;
		    BitmapFactory.decodeStream(new FileInputStream(file), null, opts);
		    
		    int sampleScaleSize = 1;
		    
		    while (opts.outWidth / sampleScaleSize / 2 >= width && opts.outHeight / sampleScaleSize / 2 >= width)
		    	sampleScaleSize *= 2;
		    
		    BitmapFactory.Options opts2 = new BitmapFactory.Options();
		    opts2.inSampleSize = sampleScaleSize;
		    
		    return BitmapFactory.decodeStream(new FileInputStream(f), null, opts2);
		    */
		    //正方形
		    bitmap = BitmapFactory.decodeFile(basePath+String.valueOf(i), opts);
		    finalBitmap = Bitmap.createScaledBitmap(bitmap, width, width, true);
		    //bitmap = BitmapFactory.decodeFile(basePath+String.valueOf(i), opts);
		    bitmap.recycle();
		    
		    bitmapCahce.put("i"+String.valueOf(i), finalBitmap);
		}catch(Exception e){
			e.printStackTrace();
		}
	    
	    return finalBitmap;
	}
	
	private void leftButtonClickEvent(View v){
		if(showTabkey != null){
			View oldvView = v.getRootView().findViewWithTag(showTabkey);
			oldvView.setBackgroundDrawable(null);			
		}				
		showTabkey = v.getTag().toString();
		Drawable drawable = new BitmapDrawable(bitmapCahce.get("i"+showTabkey));
		leftClickInterface.leftClickChagneTab(Integer.valueOf(showTabkey),drawable);
		v.setBackgroundColor(Color.BLUE);
		simpleSideDrawer.closeLeftSide();		
	}

	private String baseHeroPath(){		
		return android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+activity.getResources().getString(R.string.hero_path);
	}
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
}
