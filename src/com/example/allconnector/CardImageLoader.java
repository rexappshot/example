package com.example.allconnector;

import java.io.Serializable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.example.hearthstone.R;

public class CardImageLoader {

	private final static int cacheSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) /8);
	private static LruCache<String, Bitmap> cardCache = new LruCache<String, Bitmap>(cacheSize){
		@Override
        protected int sizeOf(String key, Bitmap bitmap) { 
            return bitmap.getByteCount() / 1024; 
        } 
	};
	private Context context;
	
	public CardImageLoader(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public void loadBitmap(int resId, ImageView imageView) { 
	    final String imageKey = String.valueOf(resId); 
	    final Bitmap bitmap = getBitmapFromMemCache(imageKey); 
	    if (bitmap != null) { 
	        imageView.setImageBitmap(bitmap); 
	    } else { 
	    	/*
	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	Bitmap bitmap2 = BitmapFactory.decodeFile(baseCardPath()+String.valueOf(resId), options);
	    	addBitmapToMemoryCache(String.valueOf(resId), bitmap2);
	    	imageView.setImageBitmap(bitmap2);
	    	*/
	        BitmapWorkerTask task = new BitmapWorkerTask(imageView); 
	        task.execute(resId);
	    } 
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) { 
	    if (getBitmapFromMemCache(key) == null) { 
	    	cardCache.put(key, bitmap); 
	    } 
	} 
	    
	public Bitmap getBitmapFromMemCache(String key) { 
	    return cardCache.get(key); 
	}

	class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> { 
		
		private ImageView imageView;
		
		public BitmapWorkerTask(ImageView imageView) {
			// TODO Auto-generated constructor stub
			this.imageView = imageView;
		}
				
	    @Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
	    	imageView.setImageBitmap(result);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}		
		
	    @Override
	    protected Bitmap doInBackground(Integer... params) { 

	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	Bitmap bitmap = BitmapFactory.decodeFile(baseCardPath()+String.valueOf(params[0]), options);
	    	addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
	        return bitmap; 
	    } 
	}
	
	private String baseCardPath(){		
		return android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+context.getResources().getString(R.string.card_path);
	}
}
