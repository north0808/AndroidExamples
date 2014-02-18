package com.example.wifip2pexample;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private final IntentFilter intentFilter = new IntentFilter();
	
	private Channel mChannel;
	private WifiP2pManager mManager;
	private WiFiDirectBroadcastReceiver receiver;
	private List<WifiP2pDevice> peers = new ArrayList();
	
	public static String TAG = "WIFIP2P_EXAMPLE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//表示Wi-Fi对等网络状态发生了改变
	    intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

	    //表示可用的对等点的列表发生了改变
	    intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

	    //表示Wi-Fi对等网络的连接状态发生了改变
	    intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

	    //设备配置信息发生了改变
	    intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
	    
	    mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
	    mChannel = mManager.initialize(this, getMainLooper(), null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
		public WiFiDirectBroadcastReceiver(WifiP2pManager mManager, Channel mChannel, Activity activity) {
			// 
		}
		
		@Override
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        
	        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
	            // 确定Wi-Fi Direct模式是否已经启用，并提醒Activity。
	            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
	            
	            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
	                // MainActivity.setIsWifiP2pEnabled(true);
	            } else {
	            		// MainActivity.setIsWifiP2pEnabled(false);
	            }
	        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

	        		// 对等点列表已经改变！我们可能需要对此做出处理。
	        		// 从Wi-Fi P2P管理器中请求可用的对等点。
	            // 这是个异步的调用，
	            // 并且，调用行为是通过PeerListListener.onPeersAvailable()上的一个回调函数来通知的。
	            if (mManager != null) {
	                mManager.requestPeers(mChannel, peerListener);
	            }
	            
	            Log.d(MainActivity.TAG, "P2P peers changed");
	        		

	        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

	        		//连接状态已经改变！我们可能需要对此做出处理。

	        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
	            // 
	        }
	    }
	}
	
	@Override
    public void onResume() {
        super.onResume();
        
        receiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        
        unregisterReceiver(receiver);
    }
    
    public void searchPeers(View view) {
	    	mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            		// 查找初始化成功时的处理写在这里。

            		// 实际上并没有发现任何服务，所以该方法可以置空。
            		// 对等点搜索的代码在onReceive方法中，详见下文。
            }

            @Override
            public void onFailure(int reasonCode) {
            		// 查找初始化失败时的处理写在这里。
            		// 警告用户出错了。
            }
	    });
    }
    
    private PeerListListener peerListener = new PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            //旧的不去，新的不来
            peers.clear();
            peers.addAll(peerList.getDeviceList());
            
            if (peers.size() == 0) {
                Log.w(MainActivity.TAG, "No devices found");
                return;
            }
        }
    };
    
    public void connect() {
        //使用在网络上找到的第一个设备。
        WifiP2pDevice device = peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new ActionListener() {

            @Override
            public void onSuccess() {
            		// WiFiDirectBroadcastReceiver将会通知我们。现在可以先忽略。
            }

            @Override
            public void onFailure(int reason) {
                // 
            }
        });
    }
}
