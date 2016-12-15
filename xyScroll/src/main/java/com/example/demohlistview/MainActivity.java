package com.example.demohlistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demohlistview.ObservableHScrollView.OnScrollChangedListener;

import java.util.ArrayList;
import java.util.List;

/**可以同时xy轴滑动
 * http://www.jb51.net/article/33376.htm
 */
public class MainActivity extends Activity {
	ListView mListView1;
	MyAdapter myAdapter;
	RelativeLayout mHead;
	LinearLayout main;
	ObservableHScrollView mHeaderScrollView1;
	InterceptScrollContainer scroollContainter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mHead = (RelativeLayout) findViewById(R.id.head);
		mHeaderScrollView1 = (ObservableHScrollView) findViewById(R.id.horizontalScrollView1);
		scroollContainter = (InterceptScrollContainer) findViewById(R.id.scroollContainter);
		scroollContainter.setFocusable(true);
		scroollContainter.setClickable(true);
		scroollContainter.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mHead.setBackgroundColor(Color.parseColor("#b2d235"));

//		mHeaderScrollView1.setFocusable(true);
//		mHeaderScrollView1.setClickable(true);
//		mHeaderScrollView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
//		mHead.setFocusable(true);
//		mHead.setClickable(true);
//		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		initHead();
		mListView1 = (ListView) findViewById(R.id.listView1);
		mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

		myAdapter = new MyAdapter(this, R.layout.item);
		mListView1.setAdapter(myAdapter);
//		mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////				Toast.makeText(MainActivity.this, i+"", Toast.LENGTH_SHORT).show();
//			}
//		});
	}

	private void initHead() {
		LinearLayout ll_container  = (LinearLayout) findViewById(R.id.ll_txtcontainer);

		View v = LayoutInflater.from(this).inflate(R.layout.textview,null);
		ll_container.addView(v);


		View v2 = LayoutInflater.from(this).inflate(R.layout.textview,null);
		ll_container.addView(v2);

		View v3 = LayoutInflater.from(this).inflate(R.layout.textview,null);
		ll_container.addView(v3);

		View v4 = LayoutInflater.from(this).inflate(R.layout.textview,null);
		ll_container.addView(v4);
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			//当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			//不要列头
//			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
//					.findViewById(R.id.mHeaderScrollView1);  //MyHScrollView
			mHeaderScrollView1.onTouchEvent(arg1);  //强制执行
			//add by cy 返回FALSE，listview滑动事件可以继续传递，正常响应，且可以把事件传给别处；
			// 返回TRUE，listview将无法获取该touch事件，无响应
			return false;
		}
	}

	public class MyAdapter extends BaseAdapter {
		public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

		int id_row_layout;
		LayoutInflater mInflater;
		Context context;
		public MyAdapter(Context context, int id_row_layout) {
			super();
			this.id_row_layout = id_row_layout;
			mInflater = LayoutInflater.from(context);
			this.context = context;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 55;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parentView) {
			ViewHolder holder = null;
			if (convertView == null) {
				synchronized (MainActivity.this) {
					convertView = mInflater.inflate(id_row_layout, null);
					holder = new ViewHolder();

					ObservableHScrollView scrollView1 = (ObservableHScrollView) convertView
							.findViewById(R.id.horizontalScrollView1);

					holder.scrollView = scrollView1;
					holder.txt1 = (TextView) convertView
							.findViewById(R.id.textView1);
					holder.txt2 = (TextView) convertView
							.findViewById(R.id.textView2);
					holder.tv_container = (LinearLayout) convertView.findViewById(R.id.ll_txtcontainer);

					View v = LayoutInflater.from(context).inflate(R.layout.textview,null);
					holder.txt3 = (TextView) v.findViewById(R.id.textView3);
					holder.tv_container.addView(v);
//					holder.txt3 = v;

					View v2 = LayoutInflater.from(context).inflate(R.layout.textview,null);
					holder.tv_container.addView(v2);

					View v3 = LayoutInflater.from(context).inflate(R.layout.textview,null);
					holder.tv_container.addView(v3);

					View v4 = LayoutInflater.from(context).inflate(R.layout.textview,null);
					holder.tv_container.addView(v4);
//					LinearLayout ll_container  = (LinearLayout) convertView.findViewById(R.id.ll_txtcontainer);
//					LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(180, ViewGroup.LayoutParams.WRAP_CONTENT);
//					lp.gravity = Gravity.CENTER;
//					TextView text1=new TextView(context);
//					text1.setLayoutParams(lp);
//					text1.setText("column8");
//					ll_container.addView(text1);
//					holder.txt3 = text1;
//
//					TextView text2=new TextView(context);
//					text2.setLayoutParams(lp);
//					text2.setText("2");
//					ll_container.addView(text2);
//
//					TextView text3=new TextView(context);
//					text3.setLayoutParams(lp);
//					text3.setText("3");
//					ll_container.addView(text3);
//
//					TextView text4=new TextView(context);
//					text4.setLayoutParams(lp);
//					text4.setText("4");
//					ll_container.addView(text4);
//					holder.txt3 = (TextView) convertView
//							.findViewById(R.id.textView3);
//					holder.txt4 = (TextView) convertView
//							.findViewById(R.id.textView4);
//					holder.txt5 = (TextView) convertView
//							.findViewById(R.id.textView5);

					holder.container = (InterceptScrollContainer) convertView.findViewById(R.id.scroollContainter) ;

//					MyHScrollView headSrcrollView = (MyHScrollView) mHead
//							.findViewById(R.id.mHeaderScrollView1);
					mHeaderScrollView1
							.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
									scrollView1));

					convertView.setTag(holder);
					mHolderList.add(holder);
				}
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt1.setText(position + "" + 1);
			holder.txt2.setText(position + "" + 2);
			holder.txt3.setText(position + "" + 3);
//			holder.txt4.setText(position + "" + 4);
//			holder.txt5.setText(position + "" + 5);
//			holder.txt1.setClickable(false);
//			holder.txt1.setOnTouchListener(null);
			return convertView;
		}

		class OnScrollChangedListenerImp implements OnScrollChangedListener {
			ObservableHScrollView mScrollViewArg;

			public OnScrollChangedListenerImp(ObservableHScrollView scrollViewar) {
				mScrollViewArg = scrollViewar;
			}

			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				mScrollViewArg.smoothScrollTo(l, t);
			}
		};

		class ViewHolder {
			TextView txt1;
			TextView txt2;
			TextView txt3;
			TextView txt4;
			TextView txt5;
			HorizontalScrollView scrollView;
			InterceptScrollContainer container;
			LinearLayout tv_container;
		}
	}// end class my

}
