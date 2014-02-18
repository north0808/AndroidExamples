package com.example.cameraexample;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	final static Integer TAKE_PHOTO = 0x0001;
	protected String mCurrentPhotoPath = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 是否有camera特性
		Context ctx = getApplicationContext();
		PackageManager packageManager = ctx.getPackageManager();
		Boolean hasCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
		Log.w("CAMERA_EXAMPLE", String.valueOf(hasCamera));
		
		// MediaStore.ACTION_VIDEO_CAPTURE
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    Log.w("CAMERA_EXMAPLE", String.valueOf(list.size() > 0));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void takePhoto(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		try {
			File f = createImageFile();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch(IOException ex) {
			ex.printStackTrace();
		}

		startActivityForResult(takePictureIntent, TAKE_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		Bitmap imageBitmap = (Bitmap)data.getExtras().get("data");
		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		imageView.setImageBitmap(imageBitmap);
	}
	
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = timeStamp + "_";
	    File image = File.createTempFile(imageFileName, ".bmp", new File("/data/"));
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
}
