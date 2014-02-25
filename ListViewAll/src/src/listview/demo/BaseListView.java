package src.listview.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BaseListView extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.baselistview);
		final ListView lv = (ListView) this.findViewById(R.id.listView1);

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				BaseListView.this,
				// 使用系统的
				android.R.layout.simple_expandable_list_item_1, getData());
		
		lv.setAdapter(arrayAdapter);
		
		// 列表项的点击事件
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 取得具体的项
				final String str = (String)lv.getItemAtPosition(position);
				Toast.makeText(BaseListView.this, "clicked " + position + ": " + str, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		
		for (int i = 0; i < 20; i++) {
			data.add("list item" + i);
		}
		
		return data;
	}
}
