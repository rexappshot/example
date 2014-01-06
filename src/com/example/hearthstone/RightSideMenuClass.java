package com.example.hearthstone;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;

import com.example.allinterface.CardClass;

import android.R.integer;
import android.app.Activity;
import android.content.Context;

public class RightSideMenuClass {
	
	private Activity activity;
	private static LinkedList<Integer> cardID;
	private static LinkedList<CardClass> cards;

	public RightSideMenuClass(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		if(cardID == null){
			cardID = new LinkedList<Integer>();
		}
		
		if(cards == null){
			cards = new LinkedList<CardClass>();
		}
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
