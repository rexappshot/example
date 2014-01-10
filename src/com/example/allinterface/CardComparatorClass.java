package com.example.allinterface;

import java.util.Comparator;

public class CardComparatorClass implements Comparator<CardClass> {

	@Override
	public int compare(CardClass card0, CardClass card1) {
		// TODO Auto-generated method stub

		return card0.getMana().compareTo(card1.getMana());
		/*
		int cardMana0 = Integer.valueOf(card0.getMana());
		int cardMana1 = Integer.valueOf(card1.getMana());
		
		if(cardMana0>cardMana1){
			return 1;
		}else if(cardMana0<cardMana1){
			return -1;
		}else {
			return 0;
		}
		*/
		
	}

}
