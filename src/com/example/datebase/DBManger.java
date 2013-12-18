package com.example.datebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.hearthstone.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBManger {

	private final int BUFFER_SIZE = 400000;
	//private final static String DB_NAME = "hearthstone.db";
	private String DB_PATH;
	private SQLiteDatabase database;
	private Context context;
	
	public DBManger(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		DB_PATH = "/data"
        + Environment.getDataDirectory().getAbsolutePath() + "/"
        + context.getPackageName();
		//DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
	}

	public SQLiteDatabase openDateBase(){
		this.database = this.openDateBase(DB_PATH + "/" + context.getResources().getString(R.string.datebase_name));
		
		return this.database ;
	}
	
	private SQLiteDatabase openDateBase(String dbfile){
		try {
            if (!(new File(dbfile).exists())) { //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            	String STORAGE_PATH =android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                InputStream is = new FileInputStream(
                		STORAGE_PATH
                		+context.getResources().getString(R.string.datebase_path)
                		+context.getResources().getString(R.string.datebase_name));
                		
                //this.context.getResources().openRawResource(R.raw.hearthstone); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
	}
	
	public void closeDatabase() {
        this.database.close();
    }
	
	public boolean deleteDatabase() {
		final File file = context.getDatabasePath(DB_PATH + "/" + context.getResources().getString(R.string.datebase_name));
		if(file.exists()){
			return file.delete();
		}else {
			return false;
		}
	}
}
