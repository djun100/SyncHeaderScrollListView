package com.test.list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 同时只能在一个方向上滚动（劣势）
 * 带滑动表头与固定列的ListView
 * http://yq135314.iteye.com/blog/1872396
 */
public class HListActivity extends Activity{
	 private ListView mListView;
	 //方便测试，直接写的public 
	 public HorizontalScrollView mTouchView;
	 //装入所有的HScrollView
	 protected List<CHScrollView> mHScrollViews =new ArrayList<CHScrollView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scroll);
		initViews();
	}
	
	private void initViews() {
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		Map<String, String> data = null;
		CHScrollView headerScroll = (CHScrollView) findViewById(R.id.item_scroll_title);
		//添加头滑动事件 
		mHScrollViews.add(headerScroll);
		mListView = (ListView) findViewById(R.id.scroll_list);
		for(int i = 0; i < 100; i++) {
			data = new HashMap<String, String>();
			data.put("title", "Title_" + i);
			data.put("data_" + 1, "Date_" + 1 + "_" +i );
			data.put("data_" + 2, "Date_" + 2 + "_" +i );
			data.put("data_" + 3, "Date_" + 3 + "_" +i );
			data.put("data_" + 4, "Date_" + 4 + "_" +i );
			data.put("data_" + 5, "Date_" + 5 + "_" +i );
			data.put("data_" + 6, "Date_" + 6 + "_" +i );
			datas.add(data);
		}
		SimpleAdapter adapter = new ScrollAdapter(this, datas, R.layout.item
							, new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6", }
							, new int[] { R.id.item_title 
										, R.id.item_data1
										, R.id.item_data2
										, R.id.item_data3
										, R.id.item_data4
										, R.id.item_data5
										, R.id.item_data6 });
		mListView.setAdapter(adapter);
	}
	
	public void addHViews(final CHScrollView hScrollView) {
		if(!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			CHScrollView scrollView = mHScrollViews.get(size - 1);
			final int scrollX = scrollView.getScrollX();
			//第一次满屏后，向下滑动，有一条数据在开始时未加入
			if(scrollX != 0) {
				mListView.post(new Runnable() {
					@Override
					public void run() {
						//当listView刷新完成之后，把该条移动到最终位置
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViews.add(hScrollView);
	}
	
	public void onScrollChanged(int l, int t, int oldl, int oldt){
		for(CHScrollView scrollView : mHScrollViews) {
			//防止重复滑动
			if(mTouchView != scrollView)
				scrollView.smoothScrollTo(l, t);
		}
	}
	
	class ScrollAdapter extends SimpleAdapter {

		private List<? extends Map<String, ?>> datas;
		private int res;
		private String[] from;
		private int[] to;
		private Context context;
		public ScrollAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if(v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				//第一次初始化的时候装进来
				addHViews((CHScrollView) v.findViewById(R.id.item_scroll));
				View[] views = new View[to.length];
				for(int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);;
					tv.setOnClickListener(clickListener);
					views[i] = tv;
				}
				v.setTag(views);
			}
			View[] holders = (View[]) v.getTag();
			int len = holders.length;
			for(int i = 0 ; i < len; i++) {
				((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());
			}
			return v;
		}
	}
	
	//测试点击的事件 
	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(HListActivity.this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
		}
	};
}
