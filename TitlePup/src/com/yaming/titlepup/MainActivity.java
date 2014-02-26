package com.yaming.titlepup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class MainActivity extends Activity {

	private TitlePopup titlePopup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		
		((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				titlePopup.show(v);;
			}
		});
		
	}
	
	
	
	
	private void init(){
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titlePopup.addAction(new ActionItem(this, "测试1", R.drawable.mm_title_btn_receiver_normal));
		titlePopup.addAction(new ActionItem(this, "测试2", R.drawable.mm_title_btn_set_normal));
		titlePopup.addAction(new ActionItem(this, "测试3", R.drawable.mm_title_btn_share_normal));
		titlePopup.addAction(new ActionItem(this, "测试4", R.drawable.mm_title_btn_speaker_normal));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
