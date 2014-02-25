package com.example.locationexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

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
	
	public void gpsProvider(View view) {
		LocationManager locationManager = getLocationManager();
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if(gpsEnabled) {
			LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
			showMessage(provider.getName());
		} else {
			showMessage("GPS没有启用.");
			enableLocationSettings();
		}
	}
	
	public void bestProvider(View view) {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		
		String providerName = getLocationManager().getBestProvider(criteria, true);
		
		if (providerName != null) {
			showMessage(providerName);
		}
	}
	
	public void showLocation(View view) {
		LocationManager locationManager = getLocationManager();
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(location != null) {
			showMessage("time = " + location.getTime() + ", accuracy = " + location.getAccuracy());
			showMessage("latitude = " + location.getLatitude() + ", longitude = " + location.getLongitude());
		} else {
			showMessage("location null");
		}
	}
	
	public void trackLocation(View view) {
		LocationManager locationManager = getLocationManager();
		
		final LocationListener listener = new LocationListener() {

		    @Override
		    public void onLocationChanged(Location location) {
		    		showMessage("latitude = " + location.getLatitude() + ", longitude = " + location.getLongitude());
		    }		        

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				showMessage(provider + " disabled");
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				showMessage(provider + " enabled");
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				showMessage(provider + " status changed");
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		        10000,          // 10-second interval.
		        10,             // 10 meters.
		        listener);
	}
	
	private LocationManager getLocationManager() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
	}
	
	private void showMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;		
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
	
	private void enableLocationSettings() {
	    Intent settingsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}
}
