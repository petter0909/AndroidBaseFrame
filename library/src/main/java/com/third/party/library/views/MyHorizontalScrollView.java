package com.third.party.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.third.party.library.utils.MyLogger;


/**
 * 横向滚动栏
 * 
 * @author Jason
 *
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
	
	MyLogger logger = MyLogger.getLogger("MyHorizontalScrollView",true);
	
	GestureDetector mGesture = null;
	
	private OnScrollListener onScrollListener;
	
	public MyHorizontalScrollView(Context context) {
		this(context, null);
//		 mGesture = new GestureDetector(context, new GestureListener());
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
//		mGesture = new GestureDetector(context, new GestureListener());
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		mGesture = new GestureDetector(context, new GestureListener());
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (onScrollListener != null) {
			onScrollListener.onScroll(l);
		}
	}
	
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent p_event) {
//		return true;
//	}

	
//	@Override
//	public boolean onTouchEvent(MotionEvent p_event) {
//		if (p_event.getAction() == MotionEvent.ACTION_MOVE
//				&& getParent() != null) {
//			getParent().requestDisallowInterceptTouchEvent(true);
//		}
//		logger.e("======== MyHorizontalScrollView onTouchEvent");
//		return super.onTouchEvent(p_event);
//	}
	    
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		logger.e("===== dispatchTouchEvent getWidth"+this.getChildAt(0).getWidth());
//	IndexFragment.viewpager.requestDisallowInterceptTouchEvent(true);
//	return mGesture.onTouchEvent(ev);
//	}
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		logger.e("===== onInterceptTouchEvent getWidth"+this.getChildAt(0).getWidth());
//		IndexFragment.viewpager.requestDisallowInterceptTouchEvent(true);
//	return mGesture.onTouchEvent(ev);
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		logger.e("===== onTouchEvent getWidth"+this.getChildAt(0).getWidth());
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			logger.e("===== getWidth"+this.getChildAt(0).getWidth());
//		}
//		IndexFragment.viewpager.requestDisallowInterceptTouchEvent(true);
//	return mGesture.onTouchEvent(event);
//	}
	
	
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}
	
	public interface OnScrollListener {
		public void onScroll(int scrollX);
	}
	
	class GestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
							   float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
								float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return super.onSingleTapUp(e);
		}

	}
}
