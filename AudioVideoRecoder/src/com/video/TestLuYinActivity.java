package com.video;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TestLuYinActivity extends Activity {
    /** Called when the activity is first created. */
	Button recordButton,stopButton,videoButton,cameraButton;
	MediaRecorder mr;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        recordButton = (Button)findViewById(R.id.btnStart); 
        stopButton = (Button)findViewById(R.id.btnEnd);
        videoButton = (Button)findViewById(R.id.btnVideo);
        cameraButton = (Button)findViewById(R.id.btnCamera);
        
        recordButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File("/sdcard/" + "YY" +
				new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date(System.currentTimeMillis()))+".mp3");
				Toast.makeText(TestLuYinActivity.this, "正在录音...", 2000).show();
				//创建录音对象
				mr = new MediaRecorder();
				//从麦克风源进行录音---还有其他的录音源
				mr.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
				//设置输出格式
				mr.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
				//设置编码格式
				mr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
				//设置输出文件
				mr.setOutputFile(file.getAbsolutePath());
				try {
					//创建文件
					file.createNewFile();
					//准备录制
					mr.prepare();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//开始录制
				mr.start();
				recordButton.setText("录音中......");
				
			}
		});
        stopButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mr != null){
					mr.stop();
					mr.release();
					mr = null;
					recordButton.setText("开始录音");
					
					Toast.makeText(TestLuYinActivity.this, "录音完毕", 2000).show();
					
				}
			}
		});
        
        cameraButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				letCamera();
			}
		});
        
        videoButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(TestLuYinActivity.this,TestBasicVideo.class);
				startActivity(intent);
			}
		});
        
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case 1:// 拍照
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "拍摄成功", Toast.LENGTH_SHORT).show();
            }
            break;
        default:
            break;
        }
    }
    /*
     * 启动照相机
     */
    protected void letCamera() {
        // TODO Auto-generated method stub
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 存放照片的文件夹
        // 照片命名
		File file = new File("/sdcard/" + "TP" +
		new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date(System.currentTimeMillis()))+".jpg");
        
        Uri uri = Uri.fromFile(file.getAbsoluteFile());
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(imageCaptureIntent, 1);
    }
    
}





































