package com.example.allconnector;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class WindowsHeightAndWidth {

	public static int width;
	public static int height;
	
	public WindowsHeightAndWidth(Context context) {
		// TODO Auto-generated constructor stub
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		width = display.getWidth();  // deprecated
		height = display.getHeight();
	}

	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}
}
