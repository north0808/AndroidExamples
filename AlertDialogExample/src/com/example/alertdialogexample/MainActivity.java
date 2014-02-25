package com.example.alertdialogexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
	
	public void confirmMessageBox(View view) {
		new AlertDialog.Builder(this).setMessage("确认对话框").setPositiveButton("确定", 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i){
						Toast.makeText(getApplicationContext(), "确定", Toast.LENGTH_SHORT);
				}
		}).show();
	}

	public void confirmCancelMessageBox(View view) {
	    new AlertDialog.Builder(this).setTitle("提示")
	    		.setMessage("确定退出?")
	    		.setIcon(R.drawable.ic_launcher)
	    		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) {
	    				setResult(RESULT_OK);
	    				finish();
	    			}
	    		})
	    		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) {
	    				// 取消按钮事件
	    				Toast.makeText(getApplicationContext(), "取消了操作", Toast.LENGTH_SHORT);
	    			}
	    }).show();
	}
}
