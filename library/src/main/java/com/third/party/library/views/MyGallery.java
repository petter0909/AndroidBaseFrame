package com.third.party.library.views;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.third.party.library.utils.MyLogger;


/**
 * 自定义 gallery
 * 
 * @author Jason
 *
 */
public class MyGallery extends LinearLayout {
	
	MyLogger logger = MyLogger.getLogger("MyGallery",true);
	
	private Context mContext;
	private BaseAdapter adapter;
	private AdapterView.OnItemSelectedListener onItemSelectedListener;
	private AdapterView.OnItemClickListener mOnItemClickListener;
	public static int selectedId = 0;

	public MyGallery(Context context) {
		super(context);
		mContext = context;
		setOrientation(HORIZONTAL);
	}

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setOrientation(HORIZONTAL);
	}
	
	public BaseAdapter getAdapter() {
		return this.adapter;
	}
	
	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
		for (int i = 0; i < adapter.getCount(); i++) {
			View view = adapter.getView(i, null, null);
			final int position = i;
			final long id = adapter.getItemId(position);
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction()== MotionEvent.ACTION_UP) {
						if (mOnItemClickListener != null) {
							mOnItemClickListener.onItemClick(null, v, position, id);
//							onItemSelectedListener.onItemSelected(null, v, position, id);
						}
					}
					// 选中的位置
					selectedId = position;
					return true;
				}
			});
			this.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}

	public void setItemSelected(int position) {
		selectedId = position;
		// 选中的视图
		View view = (View) adapter.getView(position, null, null);
		onItemSelectedListener.onItemSelected(null, view, position, getItemIdAtPosition(position));
	}
	
	public int getSelectedItemPosition() {
		return selectedId;
	}

	public long getItemIdAtPosition(int position) {
		return (adapter == null || position < 0) ? null : adapter.getItemId(position);
	}

	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}
	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}
}
