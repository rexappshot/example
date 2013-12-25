package com.example.allconnector;

import com.example.hearthstone.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class CustomDialogClass {

	private Context context;
	private Dialog dialog ;
	
	public CustomDialogClass(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		dialog = new Dialog(context,R.style.CustomDialog);		
		dialog.setContentView(R.layout.dialog_example);
		//this.setCancelButton();		
		
	}

	public Dialog getDialog(){
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		//dialogWindow.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		lp.x = 0; // 新位置X坐標
		lp.y = (int)(height*0.02);; // 新位置Y坐標
		lp.width = (int)(width*0.8); // 寬度
		lp.height = (int)(height*0.5); // 高度
		lp.alpha = 0.7f; // 透明度
		dialog.onWindowAttributesChanged(lp);
		  //设置点击外围消散
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		
		return dialog;
	}
	
	public void getSpecialDialog(){
		
	}
	
	public LinearLayout getFooterLinearLayout(){
		return (LinearLayout) dialog.findViewById(R.id.footerLinearLayout);
	}
	
	public TextView getTitleTextView(){
		return (TextView) dialog.findViewById(R.id.titleTextview);
	}
	
	public TextView getDescriptionTextView(){
		return (TextView) dialog.findViewById(R.id.descriptTextView);
	}
	/**
	 * 如果listener 傳入 null 則直接使用default cancel listener
	 * 
	 * @param leftButtonText
	 * @param leftClickListener
	 * @param RightButtonText
	 * @param rightClickListener
	 */

	public void setBaseTwoButtonFooter(String leftButtonText, OnClickListener leftClickListener
			, String RightButtonText, OnClickListener rightClickListener){
		
		if(leftClickListener == null){
			leftClickListener = this.getCancelClickListener();
		}
		
		if(rightClickListener == null){
			rightClickListener = this.getCancelClickListener();
		}
		
		LinearLayout footerLinearLayout = this.getFooterLinearLayout();
		RelativeLayout relativeLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams rlParams= new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,0.5f);
		LinearLayout.LayoutParams rlParamsButton = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
		RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
		//ViewGroup.LayoutParams vlParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,50);
		footerLinearLayout.addView(relativeLayout, rlParams);
		
		/**
		 * 
		 * 50高度要改 dp 
		 */
		RelativeLayout relativeLayoutLeftButton = new RelativeLayout(context);
		Button leftButton = new Button(context);
		rLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		leftButton.setId(R.id.left_button);
		leftButton.setText(leftButtonText);
		leftButton.setGravity(Gravity.CENTER);
		leftButton.setOnClickListener(leftClickListener);
		relativeLayoutLeftButton.addView(leftButton, rLayoutParams);
		footerLinearLayout.addView(relativeLayoutLeftButton, rlParamsButton);
		
		RelativeLayout relativeLayoutRightButton = new RelativeLayout(context);
		Button rightButton = new Button(context);
		rLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		rightButton.setId(R.id.right_button);
		rightButton.setText(RightButtonText);
		rightButton.setGravity(Gravity.CENTER);
		rightButton.setOnClickListener(rightClickListener);
		relativeLayoutRightButton.addView(rightButton, rLayoutParams);
		footerLinearLayout.addView(relativeLayoutRightButton, rlParamsButton);
		
		RelativeLayout relativeLayout2 = new RelativeLayout(context);
		footerLinearLayout.addView(relativeLayout2, rlParams);
		
	}
	/**
	 * 按下去Button便關閉Dialog
	 * 
	 * @param ButtonText
	 */
	public void setBaseOneButtonUseDefaultCancelListenterLinearLayout(String ButtonText){
		LinearLayout footerLinearLayout = this.getFooterLinearLayout();
		RelativeLayout relativeLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams rlParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
		LinearLayout.LayoutParams rlParamsButton = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
		RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
		footerLinearLayout.addView(relativeLayout, rlParams);
		
		RelativeLayout relativeLayoutCenterButton = new RelativeLayout(context);
		Button centerButton = new Button(context);
		rLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		centerButton.setId(R.id.center_button);
		centerButton.setText(ButtonText);
		centerButton.setGravity(Gravity.CENTER);
		centerButton.setOnClickListener(getCancelClickListener());
		relativeLayoutCenterButton.addView(centerButton, rLayoutParams);
		footerLinearLayout.addView(relativeLayoutCenterButton, rlParamsButton);
		
		RelativeLayout relativeLayout2 = new RelativeLayout(context);
		footerLinearLayout.addView(relativeLayout2, rlParams);
	}
	/**
	 * 自訂義ButtonClickListenter
	 * 
	 * @param ButtonText
	 * @param centercClickListener
	 */
	
	public void setBaseOneButtonLinearLayout(String ButtonText,OnClickListener centerClickListener){
		LinearLayout footerLinearLayout = this.getFooterLinearLayout();
		RelativeLayout relativeLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams rlParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
		LinearLayout.LayoutParams rlParamsButton = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
		RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
		footerLinearLayout.addView(relativeLayout, rlParams);
		
		RelativeLayout relativeLayoutCenterButton = new RelativeLayout(context);
		Button centerButton = new Button(context);
		rLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		centerButton.setId(R.id.center_button);
		centerButton.setText(ButtonText);
		centerButton.setGravity(Gravity.CENTER);
		centerButton.setOnClickListener(centerClickListener);
		relativeLayoutCenterButton.addView(centerButton, rLayoutParams);
		footerLinearLayout.addView(relativeLayoutCenterButton, rlParamsButton);
		
		RelativeLayout relativeLayout2 = new RelativeLayout(context);
		footerLinearLayout.addView(relativeLayout2, rlParams);
	}
	
	public OnClickListener getCancelClickListener(){
		
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		};
		
	}
	
	public void setTitleText(String titleText){
		getTitleTextView().setText(titleText);		
	}
	public void setDescriptionText(String descriptionText){
		getDescriptionTextView().setText(descriptionText);
	}

}
