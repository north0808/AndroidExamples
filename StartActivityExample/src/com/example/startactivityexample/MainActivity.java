package com.example.startactivityexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	final static String EXTRA_MESSAGE = "com.example.startactivityexample.fromWords";

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
	}
}
