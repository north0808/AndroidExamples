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
 * SimpleAdapter 适配器应用(list加入自定义布局)
 * 
 */
public class SimpleAdapterListView extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 上下文/内容/布局(每一个ITEM的布局)/Map内容的键值/布局的ID
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.simpleadapter,
				new String[] { "标题", "内容", "图片", "ID" }, new int[] {
						R.id.title, R.id.text, R.id.img, R.id.id });
		setListAdapter(adapter);
		//如item加入可获取焦点的子item,则焦点交给子item

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(SimpleAdapterListView.this, "选中的是第:" + position,
				Toast.LENGTH_SHORT).show();
	}

	private List<Map<String, Object>> getData() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("标题", "标题1");
		map.put("内容", "内容1111111111111111");
		map.put("图片", R.drawable.ic_launcher);
		map.put("ID", list.size());

		list.add(map);

		map = new HashMap<String, Object>();
		map.put("标题", "标题2");
		map.put("内容", "内容222222222222222");
		map.put("图片", R.drawable.ic_launcher);
		map.put("ID", list.size());
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("标题", "标题3");
		map.put("内容", "内容33333333333333");
		map.put("图片", R.drawable.ic_launcher);
		map.put("ID", list.size());
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("标题", "标题4");
		map.put("内容", "内容4444444444444");
		map.put("图片", R.drawable.ic_launcher);
		map.put("ID", list.size());
		list.add(map);

		return list;
	}
}
