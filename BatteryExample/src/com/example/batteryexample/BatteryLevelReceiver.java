package com.example.batteryexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryLevelReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.w("BATTERY_EXAMPLE", "电量低,考虑停止后台更新.");
	}

}
