package com.example.hearthstone;

import java.util.LinkedList;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allinterface.CardClass;

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
			barChartClass = new BarChartClass(activity, "卡片/法力消耗");
		}
		LinearLayout linearLayout = (LinearLayout)activity.findViewById(R.id.rightLinearlayout);
		TextView textView = new TextView(activity);
		textView.setText("arar");
		linearLayout.addView(barChartClass.getBarChart(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
					, 400));
		linearLayout.addView(textView);
	}
	
	public void addCard(CardClass cardClass){
		
		cardID.add(Integer.valueOf(cardClass.get_id()));
		cards.add(cardClass);
		
		refreshRightMenu();
	}
	
	
	public void refreshRightMenu(){
		activity.findViewById(R.id.rightLinearlayout);
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

}
