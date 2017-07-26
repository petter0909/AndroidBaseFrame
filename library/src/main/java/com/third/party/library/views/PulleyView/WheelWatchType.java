package com.third.party.library.views.PulleyView;

import android.content.Context;
import android.view.View;


import com.third.party.library.R;

import java.util.List;


public class WheelWatchType {
	private View view;
	private int type ;
	private WheelView_WatchType wv_type;

	public static final int DEFULT_START_YEAR = 1900;
	public static final int DEFULT_END_YEAR = 2015;

	public WheelWatchType(View view, int type) {
		super();
		this.view = view;
		this.type = type;
		setView(view);
	}

	
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void setPicker(Context context , List<String> strings) {

		// 类型
		wv_type = (WheelView_WatchType) view.findViewById(R.id.type);
		wv_type.setAdapter(new TypeAdapter(strings,context));// 设置"年"的显示数据
		if (type == 2) {
			wv_type.setLabel("年　　　　");// 添加文字
		} else {
			wv_type.setLabel("");// 添加文字
		}
		wv_type.setCurrentItem(0);// 初始化时显示的数据



		// 添加"年"监听
		OnItemSelectedListener_WatchType wheelListener_year = new OnItemSelectedListener_WatchType() {
			@Override
			public void onItemSelected(String index) {
			}
		};

		wv_type.setOnItemSelectedListenerWatchType(wheelListener_year);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 6 * 4 ;
		wv_type.setTextSize(textSize);

	}

	/**
	 * 设置是否循环滚动
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic){
		wv_type.setCyclic(false);
	}
	public String getTime() {
		StringBuffer sb = new StringBuffer();
		sb.append((wv_type.getCurrentItem()));
		return sb.toString();
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}



}
