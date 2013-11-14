package com.example.allinterface;

public abstract interface BaseConnect {
	
	public boolean connect(String url);
	public String Jsondecode(String sJson,String key);
	public void setJsonString(String sJson);
	public String getJsonObjectString(String key);

}
