package com.example.hearthstone;

import java.util.ArrayList;

import android.R.integer;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.allconnector.CardImageLoader;
import com.example.allconnector.WindowsHeightAndWidth;
import com.example.allinterface.CardClass;
import com.example.allinterface.CardTextControlClass;
import com.example.allinterface.SearchInterface;
import com.example.datebase.DBManager;

public class CardFragment extends SherlockFragment implements SearchInterface{

	private View mView;
	private ArrayList<CardClass> cardArrayList;
	private GestureDetector mGestureDetector;
	private int page = 0;
	private boolean arrivalEnd = false;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.card_fragment, container, false);
		Context context = getActivity().getApplicationContext();
		setCard(context);
		setFling();
		return mView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);

		String jobName = getArguments().getString("job");
		//cardImageLoader = (CardImageLoader) getArguments().getSerializable("cardImageLoader");
		
		MainActivity.searchInterface = this;
		/*
		MainActivity mainActivity = (MainActivity) getActivity();
		mainActivity.passInterface(this);
		*/
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				DBManager.DB_PATH + "/" + getResources().getString(R.string.datebase_name), null);
		
		getCard(database,jobName,page);
		
		database.close();
		
		

	}
	
	
	private void getCard(SQLiteDatabase database, String jobName,int page){
		
		getResources().getStringArray(R.array.jobs);
		Cursor cursor = database.rawQuery("SELECT * FROM card WHERE job = '"+ jobName + "' ORDER BY mana LIMIT "
				+String.valueOf(page*4)+",4 ", null);
		
		if(cursor != null){
			cardArrayList = new ArrayList<CardClass>(cursor.getCount());
			if (cursor.moveToFirst()) {				
                do {
                	CardClass cardClass = new CardClass();
                	cardClass._id = cursor.getString(cursor.getColumnIndex("_id"));                	
                	cardClass.name = cursor.getString(cursor.getColumnIndex("name")); 
                	cardClass.job = cursor.getString(cursor.getColumnIndex("job")); 
                	cardClass.type = cursor.getString(cursor.getColumnIndex("type")); 
                	cardClass.clan = cursor.getString(cursor.getColumnIndex("clan")); 
                	cardClass.mana = cursor.getString(cursor.getColumnIndex("mana")); 
                	cardClass.attack = cursor.getString(cursor.getColumnIndex("attack")); 
                	cardClass.heath = cursor.getString(cursor.getColumnIndex("heath")); 
                	cardClass.rare = cursor.getString(cursor.getColumnIndex("rare")); 
                	cardClass.series = cursor.getString(cursor.getColumnIndex("series")); 
                	cardClass.description = cursor.getString(cursor.getColumnIndex("description")); 
                	cardClass.description_en = cursor.getString(cursor.getColumnIndex("description_en")); 
                	cardClass.story = cursor.getString(cursor.getColumnIndex("story")); 
                	cardClass.story_en = cursor.getString(cursor.getColumnIndex("story_en")); 
                	cardClass.img = cursor.getString(cursor.getColumnIndex("img")); 
                	cardArrayList.add(cardClass);
                } while (cursor.moveToNext());
            }
			cursor.close();
			Log.i("cardArrayList","cardArrayList:"+cardArrayList.size());
		}
		
		
	}
	
	private void setCard(Context context){
		
		String packageName = context.getPackageName();
		CardImageLoader cardImageLoader = new CardImageLoader(context);
		int actionBarHeight = getArguments().getInt("actionBarHeight");
		int windowHeight = WindowsHeightAndWidth.getHeight();
		int windowWidth = WindowsHeightAndWidth.getWidth();
		int fragmentHeight = (Math.abs(windowHeight-actionBarHeight))/2;
		int layoutHeight = fragmentHeight/2;
		int layoutWidth = windowWidth/2;
		RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, (int)(layoutHeight*0.2));
		textViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewLayoutParams.setMargins(0, (int)(layoutHeight*0.8), 0, 0);
		
		RelativeLayout.LayoutParams textViewAreaLayoutParams = new RelativeLayout.LayoutParams(
				(int)(layoutWidth*0.8), (int)(layoutHeight*0.5));
		textViewAreaLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		textViewAreaLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewAreaLayoutParams.setMargins(0, 0, 0, (int)(layoutHeight *0.20));
		
		RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		imageViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		imageViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
		
		for(int i= 0; i<4; i++){		
			if(cardArrayList.size()<=i){
				RelativeLayout relativeLayout = (RelativeLayout) mView.findViewById(getRelativelayoutId(i, packageName));
				relativeLayout.setVisibility(View.INVISIBLE);
				arrivalEnd = true;
			}else{
				arrivalEnd = false;				
				
				CardClass cardClass = cardArrayList.get(i);			
				
				RelativeLayout relativeLayout = (RelativeLayout) mView.findViewById(getRelativelayoutId(i, packageName));
				relativeLayout.setVisibility(View.VISIBLE);
				relativeLayout.removeAllViews();
				//ImageView imageView = (ImageView) mView.findViewById(getImageViewId(i, packageName));
				ImageView imageView = new ImageView(context);
				imageView.setLayoutParams(imageViewLayoutParams);				
				cardImageLoader.loadBitmap(Integer.valueOf(cardClass.get_id()), imageView);	
				relativeLayout.addView(imageView);	
				
				TextView textView = new TextView(context);
				//textView.setText(cardClass.getName());	
				String[] nameArray = cardClass.getName().split("／");
				textView.setText(nameArray[0].replaceAll(" ", ""));
				textView.setGravity(Gravity.CENTER);
				textView.setBackgroundColor(Color.GRAY);
				textView.setLayoutParams(textViewLayoutParams);
				relativeLayout.addView(textView);
				
				if(cardClass.getDescription() != null){
					TextView textViewArea = new TextView(context);	
					textViewArea.setText(cardClass.getDescription());
					textViewArea.setGravity(Gravity.CENTER);
					textViewArea.setBackgroundColor(Color.BLUE);
					textViewArea.setLayoutParams(textViewAreaLayoutParams);
					relativeLayout.addView(textViewArea);
				}
			}
			
		}

		
	}
	
	private int getRelativelayoutId(int lastNum, String packageName){
		return getResources().getIdentifier("Relativelayout"+lastNum, "id", packageName);
	}
	
	private int getImageViewId(int lastNum, String packageName){
		return getResources().getIdentifier("imageView"+lastNum, "id", packageName);
	}
	
	private SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener()
	{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			
			if(e1.getX()-e2.getX() > WindowsHeightAndWidth.getWidth() * 0.3  && Math.abs(velocityX) > 10)//往左滑
			{
				Log.i("onFling","onFling");
				flingChange(false);
			}
			else if(e2.getX() - e1.getX() > WindowsHeightAndWidth.getWidth() * 0.3 && Math.abs(velocityX) > 10)//往右滑
			{
				Log.i("onFling","onFling");
				flingChange(true);
			}
			
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onSingleTapUp(e);
		}
		
	};
	
	private void flingChange(boolean pageKey){
		
		if(pageKey){
			if(page>0){
				
				ObjectAnimator.ofFloat(mView, "rotationY", 0, -90)
                .setDuration(400).start();
                
				page--;
			}
		}else{
			if(!arrivalEnd){
				
				ObjectAnimator.ofFloat(mView, "rotationY", 0, 90)
                .setDuration(400).start();
                
				page++;
			}
		}
		
		
		String jobName = getArguments().getString("job");
		
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				DBManager.DB_PATH + "/" + getResources().getString(R.string.datebase_name), null);
		
		getCard(database,jobName,page);
		Context context = getActivity().getApplicationContext();
		setCard(context);
		
		if(pageKey){
				ObjectAnimator.ofFloat(mView, "rotationY", -90, 0)
                .setDuration(400).start();
			
		}else{
				ObjectAnimator.ofFloat(mView, "rotationY", 90, 0)
                .setDuration(400).start();
		}
	}
	
	private void setFling(){
		
		mGestureDetector = new GestureDetector(getActivity(),simpleOnGestureListener);
		LinearLayout linearLayout = (LinearLayout) mView.findViewById(R.id.LinearParent);
		linearLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub			
				return mGestureDetector.onTouchEvent(event);
			}
		});		

	}

	@Override
	public void whereString(String where) {
		// TODO Auto-generated method stub
		
	}

}
