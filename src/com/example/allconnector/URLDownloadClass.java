package com.example.allconnector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class URLDownloadClass {

	public static final int UPDATE_PROGRESS = 8344;
	private String url;
	private String path;
	private ResultReceiver receiver;
	
	public URLDownloadClass(ResultReceiver receiver,String dirPath) {
		// TODO Auto-generated constructor stub
		this.receiver = receiver;
		
		File dir = new File(dirPath);
        if (!dir.exists()){
       	   dir.mkdirs();
        }
	}
	
	public void setURL(String url){
		this.url = url;
	}
	
	public void setSavePath(String path){
		this.path = path;
	}

	public Boolean startDownload(){
		
		try {
			URL url = new URL(this.url);
			URLConnection connection = url.openConnection();
			int fileLength = connection.getContentLength();
			InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(path);
            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                
                Bundle resultData = new Bundle();
                resultData.putString("progress" ,"正在下載資料....."+(int)(total * 100 / fileLength)+"%");
                receiver.send(UPDATE_PROGRESS, resultData);
                
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);
        receiver.send(UPDATE_PROGRESS, resultData);
        */
        return true;
	}
	
	public Boolean unZipFile(){
		
		Log.i("path","path c:"+path.contains("/"));
		Log.i("path","path:"+path);
		String pathdir  = path.substring(0,path.lastIndexOf("/")+1);
		Log.i("path","pathdir:"+pathdir);
		try {
			ZipFile zipFile = new ZipFile(path);
			int fileLength = (int) new File(path).length();
			long total = 0;
			Log.i("unZip","unZip fileLength:"+fileLength);
			Enumeration<? extends ZipEntry> emu = zipFile.entries();
			while(emu.hasMoreElements()){
				ZipEntry entry = (ZipEntry) emu.nextElement();
				//解壓縮要改
				total += entry.getCompressedSize();
				//Log.i("unZip","unZip getCompressedSize:"+entry.getCompressedSize());
				Bundle resultData = new Bundle();
                resultData.putString("progress" ,"正在解壓縮....."+(int)(total*100/fileLength)+"%");
                receiver.send(UPDATE_PROGRESS, resultData);
                
				if (entry.isDirectory())
                {					
                    new File(pathdir + entry.getName()).mkdirs();
                    continue;
                }
				BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(pathdir + entry.getName());
                File parent = file.getParentFile();
                if(parent != null && (!parent.exists())){
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos,2048);           
                
                int count;
                byte data[] = new byte[2048];
                while ((count = bis.read(data, 0, 2048)) != -1)
                {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
			}
			zipFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
