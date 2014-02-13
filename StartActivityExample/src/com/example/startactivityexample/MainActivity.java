package com.example.startactivityexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == PICK_RESULT_REQUEST) {
			if(resultCode == RESULT_OK)
				System.out.println("RESULT_OK returned");
		}
	}

	final static String EXTRA_MESSAGE = "com.example.startactivityexample.fromWords";
	final static int PICK_RESULT_REQUEST = 1;
	
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

	public void startActivity(View view) {
		// start activity
		Intent intent = new Intent(this, MessageActivity.class);

		// transfer extra data
		EditText editText = (EditText)findViewById(R.id.textMessage);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		
		startActivity(intent);
	}
	
	public void startActivityAndGetResult(View view) {
		// start activity and get the result
		Intent intent = new Intent(this, MessageActivity.class);
		intent.putExtra(EXTRA_MESSAGE, "会返回结果的窗口");
		startActivityForResult(intent, PICK_RESULT_REQUEST);
	}
}
