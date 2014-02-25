package com.example.toastexample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	Handler handler = new Handler();

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

	public void defaultToast(View view) {
		Toast.makeText(getApplicationContext(), "默认效果", Toast.LENGTH_SHORT).show();
	}
	
	public void customPosition(View view) {
	    Toast toast = Toast.makeText(getApplicationContext(), "自定义位置", Toast.LENGTH_SHORT);
	    toast.setGravity(Gravity.CENTER, 0, 0);
	    toast.show();
	}
	
	public void mergePicture(View view) {
	    Toast toast = Toast.makeText(getApplicationContext(), "带图片", Toast.LENGTH_SHORT);
	    toast.setGravity(Gravity.CENTER, 0, 0);
	    
	    LinearLayout toastView = (LinearLayout) toast.getView();
	    ImageView imageView = new ImageView(getApplicationContext());
	    imageView.setImageResource(R.drawable.ic_launcher);
	    toastView.addView(imageView, 0);
	    
	    toast.show();
	}
	
	public void customTotally(View view) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.activity_custom, (ViewGroup)findViewById(R.id.customLayout));
		
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.RIGHT | Gravity.TOP, 20, 100);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	public void fromThread(View view) {
		// 使用handler投递UI更新
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// 可更新UI元素
				MainActivity.this.setTitle("@@@新标题@@");
				Toast.makeText(getApplicationContext(), "来自线程的提示", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
