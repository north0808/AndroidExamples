package com.example.batteryexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

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
	
	public void isCharge(View view) {
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		// 持续广播，第一个参数传null即可
		Context context = getApplicationContext();
		Intent battery = context.registerReceiver(null, ifilter);
		
		// Are we charging / charged?
		int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) || (status == BatteryManager.BATTERY_STATUS_FULL);
		
		// How are we charging?
		int chargePlug = battery.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean usbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
		boolean acCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);
	}
	
	public void chargeLevel(View view) {
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Context context = getApplicationContext();
		Intent battery = context.registerReceiver(null, ifilter);
		
		int level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = battery.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		float batteryPct = level / (float)scale;
		Log.w("BATTER_EXAMPLE", String.valueOf(batteryPct));
	}

}
