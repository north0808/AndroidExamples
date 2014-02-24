package com.example.usbexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static String TAG = "USB_EXAMPLE";
	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
	
	private int VENDOR_ID = 10182;
	private int PRODUCT_ID = 2;
	
	@Override
	protected void onDestroy() { 
		// TODO Auto-generated method stub
		super.onDestroy();
		
		unregisterReceiver(usbReceiver);
	}

	PendingIntent mPermissionIntent;
	UsbManager mUsbManager;
	HashMap<String, UsbDevice> mDeviceMap;
	UsbEndpoint mReadPoint, mWritePoint, mZeroPoint;
	UsbInterface mUsbInterface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startDetect();
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
		
		String str = "发现" + mDeviceMap.size() + "个USB设备";
		Log.w(TAG, str);
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
		
		Iterator<Entry<String, UsbDevice>> iter = mDeviceMap.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String, UsbDevice> entry = iter.next();
			
			Log.w(TAG, entry.getKey().toString());
			Log.w(TAG, entry.getValue().toString());
			
			Boolean hasPermission = mUsbManager.hasPermission((UsbDevice)entry.getValue());
			Log.w(TAG, "has permission?" + hasPermission);
			
			// printUsbDevice((UsbDevice)entry.getValue());
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
		
		Log.w(TAG, "@@接口描述符信息: ");
		for(int k = 0; k < usbDevice.getInterfaceCount(); k++) {
			Log.w(TAG, "----------------------------------------");
			Log.w(TAG, "第" + k + "个接口:");
			printUsbInterface(usbDevice.getInterface(k));
		}
	}
	
	private void printUsbInterface(UsbInterface usbInterface) {
		Log.w(TAG, "接口的ID号: " + usbInterface.getId());
		Log.w(TAG, "接口的类别: " + usbInterface.getInterfaceClass());
		Log.w(TAG, "接口的协议类别: " + usbInterface.getInterfaceProtocol());
		Log.w(TAG, "接口的节点数量: " + usbInterface.getEndpointCount());
		Log.w(TAG, "接口的ID号: " + usbInterface.getId());
		
		Log.w(TAG, "----------------------------------------");
		Log.w(TAG, "@@节点信息: ");
		for(int k = 0; k < usbInterface.getEndpointCount(); k++) {
			Log.w(TAG, "第" + k + "个节点:");
			printUsbEndpoint(usbInterface.getEndpoint(k));
		}
	}
	
	private void printUsbEndpoint(UsbEndpoint usbEndpoint) {
		Log.w(TAG, "节点的地址: " + usbEndpoint.getAddress());
		Log.w(TAG, "节点的属性: " + usbEndpoint.getAttributes());
		Log.w(TAG, "节点的数据传输方向: " + usbEndpoint.getDirection());
	}
	
	private UsbDevice getRightDevice() {
		Set<Entry<String, UsbDevice>> entries = mDeviceMap.entrySet();
		List<Entry<String, UsbDevice>> list = new ArrayList<Entry<String, UsbDevice>>(entries);
		
		for(int k =0; k < list.size(); k++) {
			UsbDevice device = list.get(k).getValue();
			
			if(device.getVendorId() == VENDOR_ID && 
					device.getProductId() == PRODUCT_ID) {
				return device;
			}
		}
		
		return null;
	}
		
	public void requestDeviceAllow(View view) {
		UsbDevice device = getRightDevice();
		
		if(device != null && !mUsbManager.hasPermission(device)) {
			mUsbManager.requestPermission(device, mPermissionIntent);
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
							if(!mUsbManager.hasPermission(device))
								mUsbManager.requestPermission(device, mPermissionIntent);
						} else {
							Log.e(TAG, "device NULL");
						}
					}
				}
			}
		}
	};
	
	private void startDetect() {	
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		registerReceiver(usbReceiver, filter);
	}
	
	private void setEndpointRW(UsbEndpoint point) {
		if(point == null) return;
		
		if(point.getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
			// 中断传输
			if(point.getDirection() == UsbConstants.USB_DIR_IN) {
				mReadPoint = point;
				// device-to-host endpoint
				Log.w(TAG, "detect READ point");
				Toast.makeText(getApplicationContext(), "detect READ point", Toast.LENGTH_SHORT).show();
			}
			
			if (point.getDirection() == UsbConstants.USB_DIR_OUT) {
				mWritePoint = point;
				
				Log.w(TAG, "detect WRITE point");
				Toast.makeText(getApplicationContext(), "detect WRITE point", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void getOutIn(View view) {
		UsbDevice device = getRightDevice();
		
		printUsbDevice(device);
		
		mZeroPoint = device.getInterface(0).getEndpoint(0);
		
		mUsbInterface = device.getInterface(device.getInterfaceCount() <= 1 ? 0 : 1);
		int pointCount = mUsbInterface.getEndpointCount();
		
		for(int k = 0; k < pointCount; k++) {
			setEndpointRW(mUsbInterface.getEndpoint(k));
		}
	}
	
	public void testReadVersion(View view) {
		boolean forceClaim = true;
		int timeout = 50;
		byte[] data = null;
		
		UsbDevice device = getRightDevice();
		UsbDeviceConnection connection = mUsbManager.openDevice(device);
		
		if(connection != null) {
			connection.claimInterface(mUsbInterface, forceClaim);
			
			// max 16384 = 16 bytes
			data = new byte[64];
			byte[] cmd = Tools.HexString2Bytes("08000000");
			System.arraycopy(data, 0, cmd, 0, cmd.length);
			
			int length = connection.bulkTransfer(mWritePoint, data, data.length, timeout);
			Log.w(TAG, length + "length bytes sent");
			
			data = new byte[64];
			connection.bulkTransfer(mReadPoint, data, data.length, timeout);
			Log.w(TAG, "版本数据: " + Tools.Bytes2HexString(data));
			
			Toast.makeText(getApplicationContext(), Tools.Bytes2HexString(data), Toast.LENGTH_LONG).show();
			
			connection.releaseInterface(mUsbInterface);
			connection.close(); 
		}
	}
	
	public void testReadBuffer(View view) {
		boolean forceClaim = true;
		int timeout = 50;
		byte[] data = null;
		
		UsbDevice device = getRightDevice();
		UsbDeviceConnection connection = mUsbManager.openDevice(device);
		
		if(connection != null) {
			connection.claimInterface(mUsbInterface, forceClaim);
			
			// max 16384 = 16 bytes
			data = new byte[64];
			byte[] cmd = Tools.HexString2Bytes("220000050100350014");
			System.arraycopy(data, 0, cmd, 0, cmd.length); 
			
			int length = connection.bulkTransfer(mWritePoint, data, data.length, timeout);
			Log.w(TAG, length + "length bytes sent");
			
			data = new byte[64];
			connection.bulkTransfer(mReadPoint, data, data.length, timeout);
			Log.w(TAG, "缓冲区数据: " + Tools.Bytes2HexString(data));
			
			Toast.makeText(getApplicationContext(), Tools.Bytes2HexString(data), Toast.LENGTH_LONG).show();
			
			connection.releaseInterface(mUsbInterface);
			connection.close();
		}
	}
}
