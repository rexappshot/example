package com.example.datebase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.hearthstone.R;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CardDBHelper extends SQLiteOpenHelper {

	private final static int _DBVersion = 1;
	public final static String _DBName = "CustomCard.db";
	public final static String _CustomCardParentTableName = "CustomCardParent";
	public final static String _CustomCardSetTableName = "CustomCardSet";
	
	public CardDBHelper(Context context) {
		super(context, _DBName, null, _DBVersion);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		final String SQL1 = "CREATE TABLE IF NOT EXISTS " + _CustomCardSetTableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "parent_id VARCHAR(50), " +
	            "card_id VARCHAR(50), " +
	            ");";
		db.execSQL(SQL1);
		
		final String SQL2 = "CREATE TABLE IF NOT EXISTS " + _CustomCardParentTableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "job VARCHAR(50)," +
	            "mode VARCHAR(20)" +
	            ");";
		db.execSQL(SQL2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}	
			

}
