package com.example.allconnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.allinterface.BaseConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectClass implements BaseConnect {

	private Context context;
	private String sJson;
	
	public ConnectClass(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public boolean connect(String url) {
		// TODO Auto-generated method stub
		
		String sJson = new String();
		try {
			sJson = new AsyncTaskClass().execute(url).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setJsonString(sJson);		
		return true;
	}
	
	public void setJsonString(String sJson){
		this.sJson = sJson;
	}
	
	public String getJsonObjectString(String key){
		String sValue = Jsondecode(sJson,key);
		return sValue;
	}
	
	public class AsyncTaskClass extends AsyncTask<String, Integer, String> {
		

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpGet httpRequest = new HttpGet(params[0]);
			try {
				/* 发送请求并等待响应 */
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
				 /* 若状态码为200 ok */
				if(httpResponse.getStatusLine().getStatusCode() == 200){
		   		   HttpEntity entity = httpResponse.getEntity();
		   		   InputStream is = entity.getContent(); 
		   		   //String result = convertStreamToString(is); 
		   		   BufferedReader r = new BufferedReader(new InputStreamReader(is));
		   		   StringBuilder total = new StringBuilder();
		   		   String line;
		   		   while ((line = r.readLine()) != null) {
		   			   total.append(line);
		   		   }
		   		   return total.toString();

			   	    
			   	 }else{
			   		   Log.e("jsonparser","Error Response:"+ httpResponse.getStatusLine().toString());
			   		   return null;
			   	 }
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
		   	}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		}
	}


	@Override
	public String Jsondecode(String sJson,String key) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(sJson);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jsonObject == null){
			return null;
		}
		String sVersion = null ;
		try {
			sVersion = jsonObject.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sVersion;
	}	
	
	

}
