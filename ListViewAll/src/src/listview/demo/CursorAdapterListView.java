package src.listview.demo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * �α�������
 * 
 * @author Administrator
 * 
 */
public class CursorAdapterListView extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.cursorlistview);
		ListView listView = (ListView) this.findViewById(R.id.listViewCursor);
		// ��ȡ������ϵ��(People��ʱ)
		Cursor cursor = getContentResolver().query(People.CONTENT_URI, null,
				null, null, null);
		startManagingCursor(cursor);

		ListAdapter listAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_1, cursor,
				new String[] { People.NAME }, new int[] { android.R.id.text1 });

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}

		});
	}
}
