package cy.altair.moremenu;


import cy.altair.view.MenuItemView;
import cy.altair.view.MyAnimations;
import cy.altair.view.OnItemClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener{
	private MenuItemView myViewLT;
	private ImageView imgPlusLT;
	
	private MenuItemView myViewRT;
	private ImageView imgPlusRT;
	
	private MenuItemView myViewLB;
	private ImageView imgPlusLB;
	
	private MenuItemView myViewRB;
	private ImageView imgPlusRB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.relLayLT).setOnClickListener(this);
		findViewById(R.id.relLayRT).setOnClickListener(this);
		findViewById(R.id.relLayLB).setOnClickListener(this);
		findViewById(R.id.relLayRB).setOnClickListener(this);
		
		//获得对象
		myViewLT = (MenuItemView) findViewById(R.id.myViewLT);
		//设置位置
		myViewLT.setPosition(MenuItemView.POSITION_LEFT_TOP);
		//设置半径
		myViewLT.setRadius(130);
		imgPlusLT = (ImageView) findViewById(R.id.imgPlusLT);
		
		myViewRT = (MenuItemView) findViewById(R.id.myViewRT);
		myViewRT.setPosition(MenuItemView.POSITION_RIGHT_TOP);
		myViewRT.setRadius(70);
		imgPlusRT = (ImageView) findViewById(R.id.imgPlusRT);
		
		myViewLB = (MenuItemView) findViewById(R.id.myViewLB);
		myViewLB.setPosition(MenuItemView.POSITION_LEFT_BOTTOM);
		myViewLB.setRadius(70);
		imgPlusLB = (ImageView) findViewById(R.id.imgPlusLB);
		
		myViewRB = (MenuItemView) findViewById(R.id.myViewRB);
		myViewRB.setPosition(MenuItemView.POSITION_RIGHT_BOTTOM);
		myViewRB.setRadius(150);
		imgPlusLT = (ImageView) findViewById(R.id.imgPlusLT);
		imgPlusRT = (ImageView) findViewById(R.id.imgPlusRT);
		imgPlusLB = (ImageView) findViewById(R.id.imgPlusLB);
		imgPlusRB = (ImageView) findViewById(R.id.imgPlusRB);
		
		setMenuItemView();
	}


	private void setMenuItemView() {
		ImageView img1 = new ImageView(this);
		img1.setBackgroundResource(R.drawable.composer_music);
		ImageButton imgBtn2 = new ImageButton(this);
		imgBtn2.setBackgroundResource(R.drawable.composer_camera);
		ImageButton imgBtn3 = new ImageButton(this);
		imgBtn3.setBackgroundResource(R.drawable.composer_place);
		ImageButton imgBtn4 = new ImageButton(this);
		imgBtn4.setBackgroundResource(R.drawable.composer_sleep);
		ImageButton imgBtn5 = new ImageButton(this);
		imgBtn5.setBackgroundResource(R.drawable.composer_sun);
		ImageButton imgBtn6 = new ImageButton(this);
		imgBtn6.setBackgroundResource(R.drawable.composer_thought);
		ImageButton imgBtn7 = new ImageButton(this);
		imgBtn7.setBackgroundResource(R.drawable.composer_music);
		ImageButton imgBtn8 = new ImageButton(this);
		imgBtn8.setBackgroundResource(R.drawable.composer_camera);
		ImageButton imgBtn9 = new ImageButton(this);
		imgBtn9.setBackgroundResource(R.drawable.composer_place);
		ImageButton imgBtn10 = new ImageButton(this);
		imgBtn10.setBackgroundResource(R.drawable.composer_sleep);
		ImageButton imgBtn11 = new ImageButton(this);
		imgBtn11.setBackgroundResource(R.drawable.composer_sun);
		ImageButton imgBtn12 = new ImageButton(this);
		imgBtn12.setBackgroundResource(R.drawable.composer_thought);
		ImageButton imgBtn13 = new ImageButton(this);
		imgBtn13.setBackgroundResource(R.drawable.composer_music);
		ImageButton imgBtn14 = new ImageButton(this);
		imgBtn14.setBackgroundResource(R.drawable.composer_camera);
		ImageButton imgBtn15 = new ImageButton(this);
		imgBtn15.setBackgroundResource(R.drawable.composer_place);
		ImageButton imgBtn16 = new ImageButton(this);
		imgBtn16.setBackgroundResource(R.drawable.composer_sleep);
		ImageButton imgBtn17 = new ImageButton(this);
		imgBtn17.setBackgroundResource(R.drawable.composer_sun);
		myViewLT.addView(img1);
		myViewLT.addView(imgBtn2);
		myViewLT.addView(imgBtn3);
		myViewLT.addView(imgBtn4);
		
		myViewRT.addView(imgBtn5);
		myViewRT.addView(imgBtn6);
		myViewRT.addView(imgBtn7);
		
		myViewLB.addView(imgBtn8);
		myViewLB.addView(imgBtn9);
		myViewLB.addView(imgBtn10);
		
		myViewRB.addView(imgBtn11);
		myViewRB.addView(imgBtn12);
		myViewRB.addView(imgBtn13);
		myViewRB.addView(imgBtn14);
		myViewRB.addView(imgBtn15);
		myViewRB.addView(imgBtn16);
		myViewRB.addView(imgBtn17);
	}
	

	@Override
	public void onclick(int item) {
		Toast.makeText(MainActivity.this, ""+item, 1).show();
	}


	@Override
	public void onClick(View v) {
		int vid = v.getId();
		switch (vid) {
		case R.id.relLayLT:
			MyAnimations.getRotateAnimation(imgPlusLT, 0f, 270f, 300);
			MyAnimations.startAnimations(MainActivity.this, myViewLT, 300);
			break;
		case R.id.relLayRT:
			MyAnimations.getRotateAnimation(imgPlusRT, 0f, 270f, 300);
			MyAnimations.startAnimations(MainActivity.this, myViewRT, 300);
			break;
		case R.id.relLayLB:
			MyAnimations.getRotateAnimation(imgPlusLB, 0f, 270f, 300);
			MyAnimations.startAnimations(MainActivity.this, myViewLB, 300);
			break;
		case R.id.relLayRB:
			MyAnimations.getRotateAnimation(imgPlusRB, 0f, 270f, 300);
			MyAnimations.startAnimations(MainActivity.this, myViewRB, 300);
			break;
		}
	}


}
