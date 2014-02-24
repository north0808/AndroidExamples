package com.example.toastexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	    // 
	}
	
	public void fromThread(View view) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), "来自线程", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
