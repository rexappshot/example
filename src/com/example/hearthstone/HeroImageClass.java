package com.example.hearthstone;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.allconnector.WindowsHeightAndWidth;

public class HeroImageClass {	

	public static ArrayList<Drawable> HeroBitmapCahce = new ArrayList<Drawable>();
	private static Context context;

	public HeroImageClass(Context context) {
		// TODO Auto-generated constructor stub		
		HeroImageClass.context = context;		
		if(HeroBitmapCahce.size() == 0){
			makeImages();
		}
		
	}
	
	public static void makeImages(){
		
		String baseHeroPath = baseHeroPath();		
		final int height = (int) (WindowsHeightAndWidth.getHeight()*0.15);
		final int width = (int) (WindowsHeightAndWidth.getWidth()*0.15);
		
		for(int i=0; i<9; i++) {
			heroImageDecode(i, height, width, baseHeroPath);
		}
	}
	
	
	private static void heroImageDecode(int i, int height, int width, String basePath){		
		
		Bitmap bitmap = null;
		Bitmap finalBitmap = null;
		
		try {
		    BitmapFactory.Options opts = new BitmapFactory.Options();
		    //正方形
		    bitmap = BitmapFactory.decodeFile(basePath+String.valueOf(i), opts);
		    finalBitmap = Bitmap.createScaledBitmap(bitmap, width, width, true);
		    bitmap.recycle();
		    
		    HeroBitmapCahce.add(new BitmapDrawable(finalBitmap));
		}catch(Exception e){
			e.printStackTrace();
		}
	    
	}
	

	public static  ArrayList<Drawable> getHeroBitmapCahce() {		
		if(HeroBitmapCahce.size() == 0){
			makeImages();
		}
		return HeroBitmapCahce;
	}
	
	private static String baseHeroPath(){		
		return android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+context.getResources().getString(R.string.hero_path);
	}

}
