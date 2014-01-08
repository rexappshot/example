package com.example.hearthstone;

import java.util.ArrayList;

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
import android.view.View;
import android.widget.LinearLayout;

public class BarChartClass {

	private Context context;
	private static ArrayList<Integer> manaList = new ArrayList<Integer>(8);
	private String chartTitle;
	
	public BarChartClass(Context context, String chartTitle) {
		// TODO Auto-generated constructor stub
		this.chartTitle = chartTitle;
		this.context = context; 
	}
	
	public View getBarChart(){
		
		for(int i=0;i<8;i++){
			manaList.add(i, i);
		}
		XYSeries Series = new XYSeries(chartTitle);
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();            
		dataset.addSeries(Series);
		
		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();       
		Renderer.addSeriesRenderer(yRenderer);
		
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
		Renderer.setXLabels(0); 
		Renderer.setYAxisMin(0);
		
		yRenderer.setColor(Color.RED);

		for(int r=0; r<manaList.size(); r++) {
                //Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
            Renderer.addXTextLabel(r+1, String.valueOf(r));
            Series.add(r+1, manaList.get(r));    
        }
		
		View view = ChartFactory.getBarChartView(context, dataset, Renderer, Type.DEFAULT);                           
		return view;
	}

}
