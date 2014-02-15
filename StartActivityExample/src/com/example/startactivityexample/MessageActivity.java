package com.example.startactivityexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends Activity {
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		System.out.println("MessageActivity Destroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// UI sub-invisible, cannot run code
		
		// the best place to free the resources, such as audio, gps...
		System.out.println("MessageActivity Pause, the best place to free the resources, gps...");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// pair of onPause
		System.out.println("MessageActivity Resume");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		// UI invisible totally, cannot run code
		// free all resources
		// maybe be destroyed or killed by VM
		// void memory leak
		
		// the best place to persistent data
		System.out.println("MessageActivity Stop, the best place to persistent data.");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// execute when start to stop
		savedInstanceState.putInt("one", 1);
		savedInstanceState.putInt("two", 2);
		
		// Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		// pair of onStop(onStart recommended!!!)
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		// pair of onStop
		// called every show
		
		System.out.println("MessageActivity Start");
		
		// check gps is ready or not every visible
		LocationManager locationManager = 
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gpsEnabled) {
        		System.out.println("GPS Enabled.");
        }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		Intent intent = getIntent();
	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	    
	    boolean getResult = intent.getBooleanExtra(MainActivity.WANT_GET_RESULT, false);
	    
	    if(!getResult) {
	    		TextView fromWordsView = (TextView)findViewById(R.id.fromWords);
	    		fromWordsView.setText(message);
	    		
	    		// hide buttons
	    		findViewById(R.id.buttonReturnApple).setVisibility(Button.INVISIBLE);
	    		findViewById(R.id.buttonReturnOrange).setVisibility(Button.INVISIBLE);
	    }
	    
	    // reCreate, restore data
	    if (savedInstanceState != null) {
	    		// restore the state
	    		// onRestoreInstanceState recommended!!!
	    		int one = savedInstanceState.getInt("one");
	    		int two = savedInstanceState.getInt("two");
	    		
	    		System.out.println("one = " + one + ", two = " + two);
	    }
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// execute after onStart
		
		// Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        
        // it's not need to check the savedInstanceState is valid or not
        int one = savedInstanceState.getInt("one");
		int two = savedInstanceState.getInt("two");
		
		System.out.println("one = " + one + ", two = " + two);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}
	
	public void returnApple(View view) {
		Intent intent = new Intent();
        intent.putExtra("return", "Apple");  
        setResult(RESULT_OK, intent);  
        finish(); 
	}

	public void returnOrange(View view) {
		Intent intent = new Intent();  
        intent.putExtra("return", "Orange");  
        setResult(RESULT_OK, intent);  
        finish(); 
	}
}
