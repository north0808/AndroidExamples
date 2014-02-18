package com.example.bluetoothexample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private static Integer REQUEST_ENABLE_BT = 0x001;
	
	private static String TAG = "BLUETOOTH_EXAMPLE";
	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (mBluetoothAdapter == null) {
			Log.e(TAG, "设备不支持蓝牙");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void findPairedDevices(View view) {
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				Log.w(TAG, device.getName() + "n" + device.getAddress());
			}
		}
	}
	
	public void openBlueTooth(View view) {
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            Log.w(TAG, device.getName() + "n" + device.getAddress());
	        }
	    }
	};
	
	public void searchDevices(View view) {
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		if(mBluetoothAdapter.startDiscovery()) {
			Log.w(TAG, "开始搜索蓝牙设备");
		}
	}
	
	public void enableDiscover(View view) {
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		// 第二个参数表示多久后可以被发现, 0表示总是
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
		startActivity(discoverableIntent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		unregisterReceiver(mReceiver);
	}
	
	private class AcceptThread extends Thread {
	    private final BluetoothServerSocket mmServerSocket;

	    public AcceptThread() {
	        // Use a temporary object that is later assigned to mmServerSocket,
	        // because mmServerSocket is final
	        BluetoothServerSocket tmp = null;
	        
	        try {
	            // MY_UUID is the app's UUID string, also used by the client code
	            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME", UUID.fromString("uuid"));
	        } catch (IOException ex) { 
	        		// 
	        }
	        
	        mmServerSocket = tmp;
	    }

	    public void run() {
	        BluetoothSocket socket = null;
	        // Keep listening until exception occurs or a socket is returned
	        
	        while (true) {
	            try {
	                socket = mmServerSocket.accept();
	            } catch (IOException e) {
	                break;
	            }
	            
	            // If a connection was accepted
	            if (socket != null) {
	                // Do work to manage the connection (in a separate thread)
	                manageConnectedSocket(socket);
	                	
	                try {
	                		mmServerSocket.close();
	                } catch(IOException ex) {
	                		//
	                }
	                break;
	            }
	        }
	    }

	    public void cancel() {
	        try {
	            mmServerSocket.close();
	        } catch (IOException ex) { 
	        		// 
	        }
	    }
	}
	
	public void listenAsServer(View view) {
		new AcceptThread().start();
	}
	
	private class ConnectThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final BluetoothDevice mmDevice;

	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	        mmDevice = device;

	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("uuid"));
	        } catch (IOException ex) { 
	        		//
	        }
	        
	        mmSocket = tmp;
	    }

	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        mBluetoothAdapter.cancelDiscovery();

	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }

	        // Do work to manage the connection (in a separate thread)
	        manageConnectedSocket(mmSocket);
	    }

	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException ex) { 
	        		//
	        }
	    }
	}
	
	private void manageConnectedSocket(BluetoothSocket socket) {
		Log.w(TAG, socket.toString());
	}
	
	public void listenAsClient(View view) {
		Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
		BluetoothDevice device = devices.toArray(new BluetoothDevice[0])[0];
		new ConnectThread(device).start();
	}
	
	private class ConnectedThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final InputStream mmInStream;
	    private final OutputStream mmOutStream;
	    private final Message mHandler = new Message();

	    public ConnectedThread(BluetoothSocket socket) {
	        mmSocket = socket;
	        InputStream tmpIn = null;
	        OutputStream tmpOut = null;

	        // Get the input and output streams, using temp objects because
	        // member streams are final
	        try {
	            tmpIn = socket.getInputStream();
	            tmpOut = socket.getOutputStream();
	        } catch (IOException ex) { }

	        mmInStream = tmpIn;
	        mmOutStream = tmpOut;
	    }

	    public void run() {
	        byte[] buffer = new byte[1024];  // buffer store for the stream

	        // Keep listening to the InputStream until an exception occurs
	        while (true) {
	            try {
	                // Read from the InputStream
	                int bytes = mmInStream.read(buffer);
	                // Send the obtained bytes to the UI activity
	                Log.w(TAG, new String(buffer) + ", " + bytes + "read");
	            } catch (IOException ex) {
	                break;
	            }
	        }
	    }

	    public void write(byte[] bytes) {
	        try {
	            mmOutStream.write(bytes);
	        } catch (IOException ex) { 
	        		// 
	        }
	    }

	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException ex) { 
	        		// 
	        }
	    }
	}
}
