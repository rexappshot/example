package com.example.hearthstone;

import android.content.ClipData.Item;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class CardFragment extends SherlockFragment{

	private View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return mView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		mView = new View(getActivity());
		int itemdId = getArguments().getInt("key");
		if(itemdId == 1){
			mView.setBackgroundColor(Color.GRAY);
		}else{
			mView.setBackgroundColor(Color.WHITE);
		}
		//Log.i("CardFragment","CardFragment"+getArguments().getString("key"));

	}

}
