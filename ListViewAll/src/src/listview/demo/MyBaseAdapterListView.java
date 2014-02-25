package src.listview.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义BaseAdapter适配器
 * 
 * @author Administrator
 * 
 * 
 *         .9图片处理工具(9妹)
 */
public class MyBaseAdapterListView extends Activity implements TextWatcher,
		OnClickListener {

	private List<Entity> list = new ArrayList<Entity>();

	/** 自定义的一个base适配器 */
	private MyAdapter md;

	private EditText edit;

	private String text;

	private int Screen_w;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mybaseadapter);
		ListView lv = (ListView) this.findViewById(R.id.listView2);
		md = new MyAdapter(this, list);
		lv.setAdapter(md);
		// 去掉分割线
		lv.setDivider(null);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("========>", "listView_ITEM正在长按" + position);
				return true;
			}

		});

		edit = (EditText) this.findViewById(R.id.edittext1);
		edit.addTextChangedListener(this);// EditText监听内容

		Button btn_1 = (Button) this.findViewById(R.id.button1);
		btn_1.setOnClickListener(this);

		Display display = this.getWindowManager().getDefaultDisplay();
		Screen_w = display.getWidth();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		text = edit.getText().toString();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			md.addText();
		}
	}

	/**
	 * 自定义适配器继承BaseAdapter
	 * 
	 * @author Administrator
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		private List<Entity> list;

		private LayoutInflater layoutInflater;

		private Context context;
		private Rect rect = new Rect();
		private Paint paint = new Paint();

		public MyAdapter(Context context, List<Entity> list) {
			this.list = list;
			this.context = context;
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			convertView = layoutInflater.inflate(list.get(position)
					.getLayoutID(), null);
			// 内容
			final TextView tv = (TextView) convertView.findViewById(R.id.qq);
			tv.setText(list.get(position).getText());
			float size = tv.getTextSize();
			String text = tv.getText().toString();

			paint.setTextSize(size);
			paint.getTextBounds(text, 0, text.length(), rect);
			int width = rect.width() + 20;
			if (width > Screen_w * 3 / 5) {
				tv.setWidth(Screen_w * 3 / 5);
			}
			// 监听
			tv.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						tv.setTextColor(0xffffffff);
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_MOVE:
						tv.setTextColor(0xff000000);
						break;
					}
			
					return true;
				}
			});

			convertView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					new AlertDialog.Builder(context)
							.setTitle("是否要删除吗？")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											delect(position);
											Toast.makeText(context,
													"你删除了第：" + position + "个",
													Toast.LENGTH_SHORT).show();
										}
									}

							)
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									})

							.create().show();
					return true;
				}

			});

			// 时间
			TextView time = (TextView) convertView
					.findViewById(R.id.textView_time);
			time.setText(list.get(position).getTime());

			return convertView;
		}

		/**
		 * 添加数据
		 */
		public void addText() {
			Entity et = new Entity();
			if (list.size() % 2 == 0) {
				et.setLayoutID(R.layout.list_say_ta);
			} else {
				et.setLayoutID(R.layout.list_say_me);
			}
			et.setText(text);
			et.setTime(updataTime());
			// 添加内容
			list.add(et);

			// 清空EditText
			edit.setText(null);

			// 刷新list
			notifyDataSetChanged();
		}

		/**
		 * 删除数据
		 */
		public void delect(int index) {
			list.remove(index);
			// 刷新list
			notifyDataSetChanged();
		}

		/**
		 * 获取当前系统时间
		 * 
		 * @return
		 */
		public String updataTime() {
			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			int mHour = c.get(Calendar.HOUR_OF_DAY);
			int mMinute = c.get(Calendar.MINUTE);
			StringBuffer sb = new StringBuffer();
			sb.append(mMonth + 1).append("-").append(mDay).append("-")
					.append(mYear).append(" ").append(pad(mHour)).append(":")
					.append(pad(mMinute));
			return sb.toString();
		}

		public String pad(int c) {
			if (c >= 10) {
				return String.valueOf(c);
			} else {
				return "0" + String.valueOf(c);
			}
		}
	}

	/**
	 * 内容实体
	 * 
	 * @author Administrator
	 * 
	 */
	class Entity {

		/** 布局ID */
		private int layoutID;

		/** 布局内容 */
		private String text;

		/** 内容时间 */
		private String time;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getLayoutID() {
			return layoutID;
		}

		public void setLayoutID(int layoutID) {
			this.layoutID = layoutID;
		}

	}

}
