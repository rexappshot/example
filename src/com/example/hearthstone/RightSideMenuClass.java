package com.example.hearthstone;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.opengl.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.allconnector.WindowsHeightAndWidth;
import com.example.allinterface.CardClass;
import com.example.allinterface.CardComparatorClass;

public class RightSideMenuClass {
	
	private Activity activity;
	private static LinkedList<Integer> cardID;
	private static LinkedList<CardClass> cards;
	private static BarChartClass barChartClass;

	public RightSideMenuClass(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		if(cardID == null){
			cardID = new LinkedList<Integer>();
		}
		
		if(cards == null){
			cards = new LinkedList<CardClass>();
		}
		
		if(barChartClass == null){
			barChartClass = new BarChartClass(activity);
		}		
	}
	
	public void addCard(CardClass cardClass){
		
		cardID.add(Integer.valueOf(cardClass.get_id()));
		cards.add(cardClass);
		barChartClass.addCard(Integer.valueOf(cardClass.getMana()));

	}

	/*
	public void subCard(){
		
	}*/
	
	
	public void refreshRightMenu(){		
		
		setBarChart();
		setCardText();
		
		
	}
	
	private void setBarChart(){
		
		RelativeLayout BarChartlayout = (RelativeLayout)activity.findViewById(R.id.BarChartlayout);
		BarChartlayout.removeAllViews();
		BarChartlayout.addView(barChartClass.getBarChart(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
					, (int)(WindowsHeightAndWidth.getHeight()*0.3)));
		
	}
	
	private void setCardText(){
		
		LinearLayout cardlayout = (LinearLayout)activity.findViewById(R.id.Cardslayout);
		cardlayout.removeAllViews();
		Collections.sort(cards, new CardComparatorClass());
		
		int useHeight = (int)(WindowsHeightAndWidth.getHeight()*0.05);
		
		RelativeLayout.LayoutParams manaImageParams = new RelativeLayout.LayoutParams(useHeight
				, useHeight);		
		manaImageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		
		RelativeLayout.LayoutParams cardLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
				, useHeight);
		
		RelativeLayout.LayoutParams counTextParams = new RelativeLayout.LayoutParams(useHeight
				, useHeight);
		counTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		Set<Integer> upTwoSet = new HashSet<Integer>();
		HashSet<Integer> set = new HashSet<Integer>(); 
		Iterator<Integer> iter = cardID.listIterator();  
	    while(iter.hasNext()){  
	        int cardId = iter.next();  
	        if(!set.contains(cardId)){  
	        	//一張
	            set.add(cardId);  
	        }
	        else{
	        	//兩張
	        	upTwoSet.add(cardId);  
	        }
	    } 
	    
	    Set<Integer> doneSet = new HashSet<Integer>();
	    
		for(CardClass cardClass :cards){
			
			//兩張 且做過了
			if(doneSet.contains(Integer.valueOf(cardClass.get_id()))){
				continue;				
			}else{
				RelativeLayout.LayoutParams cardParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
						, RelativeLayout.LayoutParams.MATCH_PARENT);
				cardParams.setMargins((int)(useHeight*0.5), 0, 0, 0);
				RelativeLayout relativeLayout = new RelativeLayout(activity);
				relativeLayout.setTag(cardClass);
				relativeLayout.setOnClickListener(cardCancelClickListener);
				relativeLayout.setBackgroundResource(R.drawable.right_card);
				
				ImageView manaImageView = new ImageView(activity);
				manaImageView.setBackgroundResource(R.drawable.mana);
				manaImageView.setId(Integer.valueOf(cardClass.get_id())+4441);				
								
				TextView counTextView = new TextView(activity);
				counTextView.setGravity(Gravity.CENTER_VERTICAL);
				counTextView.setId(Integer.valueOf(cardClass.get_id())+45672);
				
				TextView cardNameView = new TextView(activity);
				String[] nameArray = cardClass.getName().split("／");
				cardNameView.setText(nameArray[0].replaceAll(" ", ""));
				cardNameView.setGravity(Gravity.CENTER_VERTICAL);
				cardNameView.setTextColor((getTextColor(cardClass.getRare(), cardNameView.getTextColors().getDefaultColor())));
				cardParams.addRule(RelativeLayout.RIGHT_OF, manaImageView.getId());
				cardParams.addRule(RelativeLayout.LEFT_OF, counTextView.getId());				
								
				//兩張
				if(upTwoSet.contains(Integer.valueOf(cardClass.get_id()))){					
					
					counTextView.setText("x2");			
										
					doneSet.add(Integer.valueOf(cardClass.get_id()));
				}else{
					counTextView.setVisibility(View.GONE);
				}
				
				relativeLayout.addView(cardNameView, cardParams);
				relativeLayout.addView(counTextView, counTextParams);
				relativeLayout.addView(manaImageView, manaImageParams);
				
				cardlayout.addView(relativeLayout,cardLayoutParams);
				
				
			}
		}
	}
	
	private OnClickListener cardCancelClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			cardCancel((CardClass)v.getTag(), v);
		}
	}; 
	
	private void cardCancel(CardClass cardClass , View v){
		
		cardID.remove(Integer.valueOf(cardClass.get_id()));
		cards.remove(cardClass);
		barChartClass.subCard(Integer.valueOf(cardClass.getMana()));
		setBarChart();
		
		TranslateAnimation translateAnimation = new TranslateAnimation(0,-30,0,-30);
		translateAnimation.setDuration(200);
		translateAnimation.setZAdjustment(TranslateAnimation.ZORDER_TOP);
		translateAnimation.setAnimationListener(new MyAnimationListener(v));
		
		v.startAnimation(translateAnimation);
		//LinearLayout cardlayout = (LinearLayout)activity.findViewById(R.id.Cardslayout);
		
	}
	
	private class MyAnimationListener implements AnimationListener {
		View view;
	    public MyAnimationListener(View view){
	    	this.view = view;
	    }
	    public void onAnimationEnd(Animation animation) {
	    	Log.i("onAnimationEnd","onAnimationEnd");
	    	view.clearAnimation();
	    	((LinearLayout)view.getParent()).removeView(view);
	    }
	    public void onAnimationRepeat(Animation animation) {
	    }
	    public void onAnimationStart(Animation animation) {
	    }
	}
	
	private int getTextColor(String rare, int defaultColor){
		
		if(rare.equals("免費")){
			return defaultColor;
		}else if(rare.equals("普通")){
			return Color.WHITE;
		}else if(rare.equals("精良")){
			return Color.rgb(0, 102, 204);
		}else if(rare.equals("史詩")){
			return Color.rgb(148, 0, 211);
		}else{
			return Color.rgb(255, 140, 0);
		}
	}
	
	//普通
	public boolean isFullTwoCard(int checkID){
		int count = 0;
		
		for(Integer _Id : cardID){
			if(_Id == checkID){
				count++;
			}
		}
		
		if(count==2){
			return false;
		}else{
			return true;
		}
	}
	
	//傳說
	public boolean isFullOneCard(int checkID){
		
		int count = 0;
		
		for(Integer _Id : cardID){
			if(_Id == checkID){
				count++;
			}
		}
		
		if(count==1){
			return false;
		}else{
			return true;
		}
	}
	
	//已滿30張
	public boolean isFullCard(){
		
		if(cardID.size()>=30){
			return false;
		}else{
			return true;
		}
	}

}
