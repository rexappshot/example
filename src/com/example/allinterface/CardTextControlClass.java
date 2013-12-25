package com.example.allinterface;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CardTextControlClass {
	
	public static RelativeLayout.LayoutParams getTextViewLayoutParams() {
		textViewLayoutParams = new RelativeLayout.LayoutParams(
				getSpecialWidth(0.5), getSpecialHeighth(0.1));
		textViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewLayoutParams.setMargins(0, getSpecialHeighth(0.4), 0, 0);
		Log.i("getSpecialHeighth(0.5","getSpecialHeighth(0.5):"+getSpecialHeighth(0.5));
		return textViewLayoutParams;

	}

	private static int width;
	private static int height;
	private static RelativeLayout.LayoutParams textViewLayoutParams;

	public CardTextControlClass(RelativeLayout relativeLayout) {
		// TODO Auto-generated constructor stub
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
		relativeLayout.measure(w, h);  
		height =relativeLayout.getMeasuredHeight();  
		width =relativeLayout.getMeasuredWidth();
	}
	
	public static int getSpecialWidth(double ratio){
		return (int) (width*ratio);
	}
	
	public static int getSpecialHeighth(double ratio){
		return (int) (height*ratio);
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		CardTextControlClass.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		CardTextControlClass.height = height;
	}

	

}
