package com.example.datebase;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CardDBManger {
	
	private static CardDBHelper cardDBHelper;

	public CardDBManger(Context context) {
		// TODO Auto-generated constructor stub
		if(cardDBHelper == null){
			cardDBHelper = new CardDBHelper(context);
		}		
	}
	
	public void InsertByRawSQL(String sqlString){
		SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL(sqlString);
		db.execSQL( "INSERT INTO MyTable (_DATA,_DATETIME) VALUES ('Hello World', datetime('now'))");
		db.close();
	}
	
	public void InsertByObjectMethod(LinkedHashMap<String, String> map){
		
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();	    
	    for(Entry<String, String> entry : map.entrySet()) {
	        values.put(entry.getKey(), entry.getValue());
	    }

	    db.insertOrThrow(CardDBHelper._CustomCardSetTableName,null,values);
	    db.close();
	}
	
	public SQLiteDatabase getWritableDatabase(){
		return cardDBHelper.getWritableDatabase();
	}
	
	public void closeDB(){
		cardDBHelper.close();
	}

}
