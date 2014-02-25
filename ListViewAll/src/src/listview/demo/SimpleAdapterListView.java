package src.listview.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * SimpleAdapter ������Ӧ��(list�����Զ��岼��)
 * 
 */
public class SimpleAdapterListView extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ������/����/����(ÿһ��ITEM�Ĳ���)/Map���ݵļ�ֵ/���ֵ�ID
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.simpleadapter,
				new String[] { "����", "����", "ͼƬ", "ID" }, new int[] {
						R.id.title, R.id.text, R.id.img, R.id.id });
		setListAdapter(adapter);
		//��item����ɻ�ȡ�������item,�򽹵㽻����item

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(SimpleAdapterListView.this, "ѡ�е��ǵ�:" + position,
				Toast.LENGTH_SHORT).show();
	}

	private List<Map<String, Object>> getData() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("����", "����1");
		map.put("����", "����1111111111111111");
		map.put("ͼƬ", R.drawable.ic_launcher);
		map.put("ID", list.size());

		list.add(map);

		map = new HashMap<String, Object>();
		map.put("����", "����2");
		map.put("����", "����222222222222222");
		map.put("ͼƬ", R.drawable.ic_launcher);
		map.put("ID", list.size());
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("����", "����3");
		map.put("����", "����33333333333333");
		map.put("ͼƬ", R.drawable.ic_launcher);
		map.put("ID", list.size());
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("����", "����4");
		map.put("����", "����4444444444444");
		map.put("ͼƬ", R.drawable.ic_launcher);
		map.put("ID", list.size());
		list.add(map);

		return list;
	}
}
