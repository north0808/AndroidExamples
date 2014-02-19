package com.example.usbexample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private static String TAG = "USB_EXAMPLE";
	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
	
	PendingIntent mPermissionIntent;
	UsbManager mUsbManager;
	HashMap<String, UsbDevice> mDeviceMap;
	
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
	
	public void usbManager(View view) {
		mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
		mDeviceMap = mUsbManager.getDeviceList();
		
		Log.w(TAG, "发现" + mDeviceMap.size() + "个USB设备");
		
		Iterator iter = mDeviceMap.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			
			Log.w(TAG, entry.getKey().toString());
			Log.w(TAG, entry.getValue().toString());
			
			Boolean hasPermission = mUsbManager.hasPermission((UsbDevice)entry.getValue());
			Log.w(TAG, "has permission?" + hasPermission);
		}
	}
	
	private void printUsbDevice(UsbDevice usbDevice) {
		Log.w(TAG, "usb设备的类别: " + usbDevice.getDeviceClass());
		Log.w(TAG, "usb设备的ID号: " + usbDevice.getDeviceId());
		Log.w(TAG, "usb设备的名称: " + usbDevice.getDeviceName());
		Log.w(TAG, "usb设备的协议类别: " + usbDevice.getDeviceProtocol());
		Log.w(TAG, "usb设备的子类别: " + usbDevice.getDeviceSubclass());
		Log.w(TAG, "usb设备的生产商ID: " + usbDevice.getVendorId());
		Log.w(TAG, "usb设备的产品ID: " + usbDevice.getProductId());
		Log.w(TAG, "usb设备的接口数量: " + usbDevice.getInterfaceCount());
		
		printUsbInterface(usbDevice.getInterface(0));
	}
	
	private void printUsbInterface(UsbInterface usbInterface) {
		Log.w(TAG, "usb设备接口的ID号: " + usbInterface.getId());
		Log.w(TAG, "usb设备接口的类别: " + usbInterface.getInterfaceClass());
		Log.w(TAG, "usb设备接口的协议类别: " + usbInterface.getInterfaceProtocol());
		Log.w(TAG, "usb设备接口的节点数量: " + usbInterface.getEndpointCount());
		Log.w(TAG, "usb设备接口的ID号: " + usbInterface.getId());
		
		printUsbEndpoint(usbInterface.getEndpoint(0));
	}
	
	private void printUsbEndpoint(UsbEndpoint usbEndpoint) {
		Log.w(TAG, "usb设备节点的地址: " + usbEndpoint.getAddress());
		Log.w(TAG, "usb设备节点的属性: " + usbEndpoint.getAttributes());
		Log.w(TAG, "usb设备节点的数据传输方向: " + usbEndpoint.getDirection());
	}
	
	public void openUsbDevice(View view) {
		Entry entry = (Entry)mDeviceMap.entrySet().toArray()[0];
		UsbDevice device = (UsbDevice)entry.getValue();
		
		if(device != null) {
			mUsbManager.requestPermission(device, mPermissionIntent);
			sendData(device);
		} else {
			Log.e(TAG, "device NULL");
		}
	}
	
	private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction(); 
			
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					
					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						if(device != null) {
							mUsbManager.requestPermission(device, mPermissionIntent);
							sendData(device);
						} else {
							Log.e(TAG, "device NULL");
						}
					}
				}
			}
		}
	};
	
	private void sendData(UsbDevice device) {
		UsbInterface usbInterface = device.getInterface(0);
		UsbEndpoint usbEndpoint = usbInterface.getEndpoint(0);
		
		boolean forceClaim = true;
		byte[] bytes = "hello usb".getBytes();
		int timeout = 0;
		
		UsbDeviceConnection connection = mUsbManager.openDevice(device);
		
		if(connection != null) {
			connection.claimInterface(usbInterface, forceClaim);
			
			int length = connection.bulkTransfer(usbEndpoint, bytes, bytes.length, timeout);
			Log.w(TAG, length + "length bytes sent");
			
			if(-1 == length) {
				Log.e(TAG, "send data failed");
			}
			
			connection.releaseInterface(usbInterface);
			connection.close();
		}
	}
	
	public void startDetect(View view) {	
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		registerReceiver(usbReceiver, filter);
	}
	
	private UsbEndpoint[] getOutInEndpoint(UsbDeviceConnection connection, UsbInterface intf) {
		UsbEndpoint outEndpoint = null;
		UsbEndpoint inEndpoint = null;
		
		if (intf.getEndpoint(1) != null) { 
	    		// 1 - OUT
	    		outEndpoint = intf.getEndpoint(1);
	    }
	    
	    if (intf.getEndpoint(0) != null) {
	    		// 0 - IN
	    		inEndpoint = intf.getEndpoint(0);
	    }
	    
	    return new UsbEndpoint[] {outEndpoint, inEndpoint};
	} 
	
	public void getOutIn(View view) {
		Entry entry = (Entry)mDeviceMap.entrySet().toArray()[0];
		UsbDevice device = (UsbDevice)entry.getValue();
		
		UsbDeviceConnection connection = mUsbManager.openDevice(device);
		UsbEndpoint[] points = getOutInEndpoint(connection, device.getInterface(0));
		
		Log.w(TAG, "points = " + points.toString());
	}
	
	public void testBulkTransfer(View view) {
		// 
	}
	
	public void testControlTransfer(View view) {
		// 
	}
}
