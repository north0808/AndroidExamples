package src.listview.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ListViewActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button ListViewBtn = (Button) this.findViewById(R.id.button1);
		ListViewBtn.setOnClickListener(baseListView);
		ListViewBtn = (Button) this.findViewById(R.id.button2);
		ListViewBtn.setOnClickListener(simpleAdapterPeopleListener);
		ListViewBtn = (Button) this.findViewById(R.id.button3);
		ListViewBtn.setOnClickListener(simpleAdapterListener);
		ListViewBtn = (Button) this.findViewById(R.id.button4);
		ListViewBtn.setOnClickListener(myAdapter);
		ListViewBtn = (Button) this.findViewById(R.id.button5);
		ListViewBtn.setOnClickListener(myBaseAdapter);
	}

	private View.OnClickListener baseListView = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// ª˘¥°ArrayAdapter  ≈‰∆˜
			Intent intent = new Intent();
			intent.setClass(ListViewActivity.this, BaseListView.class);
			startActivity(intent);
		}
	};
	private View.OnClickListener simpleAdapterPeopleListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// CursorAdapter ”Œ±Í  ≈‰∆˜
			Intent intent = new Intent();
			intent.setClass(ListViewActivity.this, CursorAdapterListView.class);
			startActivity(intent);
		}
	};
	private View.OnClickListener simpleAdapterListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// SimpleAdapter  ≈‰∆˜
			Intent intent = new Intent();
			intent.setClass(ListViewActivity.this, SimpleAdapterListView.class);
			startActivity(intent);
		}
	};
	private View.OnClickListener myAdapter = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// listAdatper  ≈‰∆˜
			Intent intent = new Intent();
			intent.setClass(ListViewActivity.this, MyListAdapterListView.class);
			startActivity(intent);
		}
	};
	private View.OnClickListener myBaseAdapter = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// baseAdapter  ≈‰∆˜
			Intent intent = new Intent();
			intent.setClass(ListViewActivity.this, MyBaseAdapterListView.class);
			startActivity(intent);
		}
	};
}