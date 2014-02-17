package com.example.networkexample;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	final static String TAG = "NETWORK_EXAMPLE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void downloadWebHandler(View view) {
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    
		    if (networkInfo != null && networkInfo.isConnected()) {
		    		new DownloadWebpageText().execute("http://www.baidu.com");
		    } else {
		    		Log.w(TAG, "network unable");
		    }
	}
	
	private class DownloadWebpageText extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			String str = "";
			
			try {
				str = downloadUrl(urls[0]);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			
			return str;
		}
		
		@Override
        protected void onPostExecute(String result) {
            Log.w(TAG, result.toString());
		}
	}
	
	private String downloadUrl(String request_url) throws IOException {
	    InputStream is = null;
	    int len = 1024;

	    try {
	        URL url = new URL(request_url);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000);
	        conn.setConnectTimeout(15000);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);

	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        String contentAsString = readIt(is, len);
	        return contentAsString;

	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	public void checkNetworkOnline(View view) {
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		
		boolean isWifiConn = networkInfo.isConnected();
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
		boolean isMobileConn = networkInfo.isConnected();
		Log.w(TAG, "Wifi connected: " + isWifiConn);
		Log.w(TAG, "Mobile connected: " + isMobileConn);
		
		// 当前活动的网络是否可用
		connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    networkInfo = connMgr.getActiveNetworkInfo();
	    boolean isEnable = (networkInfo != null && networkInfo.isConnected());
	    Log.w(TAG, "ActiveNetwork " + networkInfo.getTypeName() + " " + isEnable);
	}
}
