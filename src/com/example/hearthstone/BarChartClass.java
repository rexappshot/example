package com.example.hearthstone;

import java.util.ArrayList;
import java.util.Collections;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

public class BarChartClass {

	private Context context;
	private ArrayList<Integer> manaList = new ArrayList<Integer>(8);
	private final String cardCount = "卡片數量";
	private final String manaCost = "魔力消耗";
	private final String[] xTitle = {"0","1","2","3","4","5","6","7+"};
	
	public BarChartClass(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context; 
		
		if(manaList.size() == 0){
			/*
			manaList.add(2);
			manaList.add(5);
			manaList.add(1);
			manaList.add(2);
			manaList.add(4);
			manaList.add(5);
			manaList.add(8);
			manaList.add(10);
			*/			
			for(int i=0; i<8; i++){
				manaList.add(0);
			}
		}
		
	}
	
	public View getBarChart(){
		
		XYSeries Series = new XYSeries(cardCount);
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();            
		dataset.addSeries(Series);
		
		
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();       
		renderer.addSeriesRenderer(yRenderer);
		
		/*
		//Renderer.setApplyBackgroundColor(true);			//設定背景顏色
		//Renderer.setBackgroundColor(Color.BLACK);			//設定圖內圍背景顏色
		Renderer.setMarginsColor(Color.WHITE);				//設定圖外圍背景顏色
		Renderer.setTextTypeface(null, Typeface.BOLD);		//設定文字style

		Renderer.setShowGrid(true);							//設定網格
		Renderer.setGridColor(Color.GRAY);					//設定網格顏色
		
		Renderer.setChartTitle(chartTitle);					//設定標頭文字
		Renderer.setLabelsColor(Color.BLACK);				//設定標頭文字顏色
		Renderer.setChartTitleTextSize(20);					//設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK);					//設定雙軸顏色
		Renderer.setBarSpacing(0.5);						//設定bar間的距離
		
		//Renderer.setXTitle(XTitle);						//設定X軸文字      
		//Renderer.setYTitle(YTitle);						//設定Y軸文字 
		Renderer.setXLabelsColor(Color.BLACK);				//設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK);			//設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER);				//設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER);				//設定Y軸文字置中
		Renderer.setXLabelsAngle(-25); 			
		*/
		//Renderer.setChartTitle(chartTitle);
		renderer.setAxesColor(Color.WHITE);	
		renderer.setZoomRate(1.3f);
		renderer.setBarSpacing(0.5f);						//設定bar間的距離
		renderer.setXLabels(0); 
		renderer.setYLabels(0);
		renderer.setLabelsTextSize(dpToPx(20));
		
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(8.5);
		renderer.setYAxisMin(0);	
		renderer.setYAxisMax(getManaListMax()+2);
		
		renderer.setXTitle(manaCost);
		
		renderer.setZoomEnabled(false, false);
		renderer.setPanEnabled(false, false);				
		
		yRenderer.setColor(Color.YELLOW);
		yRenderer.setChartValuesTextSize(dpToPx(10));
		

		for(int r=0; r<manaList.size(); r++) {
                //Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			renderer.addXTextLabel(r+1, xTitle[r]);
            Series.add(r+1, manaList.get(r));
        }
 
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		View view = ChartFactory.getBarChartView(context, dataset, renderer, Type.DEFAULT);    
		return view;
	}
	
	public void addCard(int mana){
		if(mana > 7){
			mana = 7;
		}
		manaList.set(mana, manaList.get(mana)+1);
	}
	
	public void subCard(int mana){
		if(mana > 7){
			mana = 7;
		}
		
		if(manaList.get(mana) != 0){
			manaList.set(mana, manaList.get(mana)-1);
		}
	}
	
	private int getManaListMax(){
		int max = 0;
		for(int element : manaList){
			if(element > max){
				max = element;
			}
		}
		
		return max;
	}
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}

}
