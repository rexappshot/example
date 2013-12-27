package com.example.hearthstone;

import java.util.ArrayList;

import android.R.integer;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
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
	private GestureDetector imageGestureDetector;
	private int page = 0;
	private boolean arrivalEnd = false;
	public String where;
	public int clickCardId;
	
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
		ObjectAnimator.ofFloat(mView, "rotationY", -20, 0)
        .setDuration(400).start();
		setFling();
		return mView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);

		//cardImageLoader = (CardImageLoader) getArguments().getSerializable("cardImageLoader");
		
		MainActivity.searchInterface = this;
		/*
		MainActivity mainActivity = (MainActivity) getActivity();
		mainActivity.passInterface(this);
		*/
		
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				DBManager.DB_PATH + "/" + getResources().getString(R.string.datebase_name), null);
		
		getCard(database);
		
		database.close();
		
		

	}
	
	
	private void getCard(SQLiteDatabase database){		
				
		String jobName = getArguments().getString("job");
		
		getResources().getStringArray(R.array.jobs);
		if(where == null){
			where = "";
		}
		Cursor cursor = database.rawQuery("SELECT * FROM card WHERE job = '"+ jobName + "' "+ where +" ORDER BY mana LIMIT "
				+String.valueOf(page*4)+",5 ", null);
		
		Log.i("sql","sql:"+"SELECT * FROM card WHERE job = '"+ jobName + "' "+ where +" ORDER BY mana LIMIT "
				+String.valueOf(page*4)+",5 ");
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
		}		
		
	}
	
	private void setCard(Context context){
		imageGestureDetector = new GestureDetector(getActivity(),imageOnGestureListener);
		
		String packageName = context.getPackageName();
		CardImageLoader cardImageLoader = new CardImageLoader(context);
		int actionBarHeight = getArguments().getInt("actionBarHeight");
		int windowHeight = WindowsHeightAndWidth.getHeight();
		int windowWidth = WindowsHeightAndWidth.getWidth();
		int fragmentHeight = (Math.abs(windowHeight-actionBarHeight))/2;
		int layoutHeight = fragmentHeight/2;
		int layoutWidth = windowWidth/2;
		RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
				(int)(layoutWidth*0.6), (int)(layoutHeight*0.2));
		textViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewLayoutParams.setMargins(0, (int)(layoutHeight*0.9), 0, 0);
		
		RelativeLayout.LayoutParams textViewAreaLayoutParams = new RelativeLayout.LayoutParams(
				(int)(layoutWidth*0.6), (int)(layoutHeight*0.4));
		textViewAreaLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		textViewAreaLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewAreaLayoutParams.setMargins(0, 0, 0, (int)(layoutHeight *0.35));
		
		RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, (int)(fragmentHeight *0.8));
		imageViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		imageViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
		
		if(cardArrayList.size()<5){
			arrivalEnd = true;
		}else{
			arrivalEnd = false;	
		}
		
		for(int i= 0; i<4; i++){		
			RelativeLayout relativeLayout = (RelativeLayout) mView.findViewById(getRelativelayoutId(i, packageName));
			relativeLayout.getLayoutParams().height = fragmentHeight;
			if(cardArrayList.size()<=i){
				relativeLayout.setVisibility(View.INVISIBLE);	
			}else{							
				CardClass cardClass = cardArrayList.get(i);						
				
				relativeLayout.setVisibility(View.VISIBLE);
				relativeLayout.removeAllViews();
				//ImageView imageView = (ImageView) mView.findViewById(getImageViewId(i, packageName));
				ImageView imageView = new ImageView(context);
				imageView.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub		
						clickCardId = +v.getId();
						return imageGestureDetector.onTouchEvent(event);
					}
				});	
				imageView.setId(i+44);
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
					textViewArea.setTextSize(10);
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
	
	private SimpleOnGestureListener imageOnGestureListener = new SimpleOnGestureListener()
	{
		
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub			
			imageClick();
			Log.i("onSingleTapConfirmed","onSingleTapConfirmed");
			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.i("onDown","onDown"+super.onDown(e));
			return true;
			//return super.onDown(e);
			
		}
		
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
		
		
	};
	
	
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
				
				page--;
				
				reDoAllWork();
				
				ObjectAnimator.ofFloat(mView, "x", -(WindowsHeightAndWidth.getWidth()/2), 0)
                .setDuration(500).start();
                
                
				/*
				ObjectAnimator.ofFloat(mView, "rotationY", -120, 0)
                .setDuration(200).start();
                */
			}else{
				Toast.makeText(getActivity(), "已經沒有上一頁了", Toast.LENGTH_SHORT).show();
			}
		}else{
			if(!arrivalEnd){
				

				page++;
				
				reDoAllWork();
				
				ObjectAnimator.ofFloat(mView, "x", WindowsHeightAndWidth.getWidth()/2, 0)
                .setDuration(500).start();
				/*
				ObjectAnimator.ofFloat(mView, "rotationY", 120, 0)
                .setDuration(200).start();
                */
			}else{
				Toast.makeText(getActivity(), "已經沒有下一頁了", Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	private void reDoAllWork(){
		
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				DBManager.DB_PATH + "/" + getResources().getString(R.string.datebase_name), null);
		
		getCard(database);
		Context context = getActivity().getApplicationContext();
		setCard(context);
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
	public void refreshFragment(String where) {
		// TODO Auto-generated method stub
		page = 0;
		this.where = where;
		
		reDoAllWork();
		
		ObjectAnimator.ofFloat(mView, "rotationY", -90, 0)
        .setDuration(400).start();
	}
	
	private void imageClick(){		
		
		Context context = getActivity();
		Dialog myDialog = new Dialog(context);
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		myDialog.setContentView(R.layout.dialog_card);
		
		Window dialogWindow = myDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		lp.width = WindowsHeightAndWidth.width; // 寬度
		lp.height = WindowsHeightAndWidth.height; // 高度
		myDialog.onWindowAttributesChanged(lp);
		
		setCardDialogChild(myDialog, context);
		
		
		myDialog.show();
		
	}
	
	private void setCardDialogChild(Dialog myDialog, Context context){		
		
		CardClass cardClass = cardArrayList.get((clickCardId-44));
		
		CardImageLoader cardImageLoader = new CardImageLoader(context);
		int windowHeight = WindowsHeightAndWidth.getHeight();
		int windowWidth = WindowsHeightAndWidth.getWidth();

		RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
				(int)(windowWidth*0.64), (int)(windowHeight*0.05));
		textViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewLayoutParams.setMargins(0, (int)(windowHeight*0.28), 0, 0);
		
		RelativeLayout.LayoutParams textViewAreaLayoutParams = new RelativeLayout.LayoutParams(
				(int)(windowWidth*0.49), (int)(windowHeight*0.15));
		textViewAreaLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewAreaLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewAreaLayoutParams.setMargins(0, (int)(windowHeight *0.4), 0, 0);
		
		RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, (int)(windowHeight *0.6));
		imageViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		imageViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		
		RelativeLayout.LayoutParams textViewStoryLayoutParams = new RelativeLayout.LayoutParams(
				(int)(windowWidth*0.8), (int)(windowHeight*0.3));
		textViewStoryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewStoryLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textViewStoryLayoutParams.setMargins(0, (int)(windowHeight * 0.6), 0, 0);
		
		RelativeLayout relativeLayout = (RelativeLayout) myDialog.findViewById(R.id.relativeLayout1);
		
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
			textViewArea.setTextSize(14);
			textViewArea.setBackgroundColor(Color.BLUE);
			textViewArea.setLayoutParams(textViewAreaLayoutParams);
			relativeLayout.addView(textViewArea);
		}
		
		if(cardClass.getStory() != null){
			
			TextView textViewStory = new TextView(context);	
			textViewStory.setText(cardClass.getStory());
			textViewStory.setGravity(Gravity.CENTER);
			textViewStory.setTextSize(20);
			textViewStory.setLayoutParams(textViewStoryLayoutParams);
			relativeLayout.addView(textViewStory);
		}
	}

}
