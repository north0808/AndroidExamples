package com.yt.prefence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PrefenceTestActivity extends Activity {
	private Context contextsContext = PrefenceTestActivity.this;
	private Button button ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);  
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity);
        initWidget();
        setWidgetOnClickListener();
    }
    
    /**
     * initialize Widget
     */
    private void initWidget(){
    	button =(Button)findViewById(R.id.button);
    }
    
    /**
     * Component, OnClickListener
     */
    private void setWidgetOnClickListener(){
    	button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//intent.setAction("android.settings.SETTINGS");
				intent.setClass(contextsContext, Settings.class);
				startActivity(intent);
			}
		});
    }
    
}