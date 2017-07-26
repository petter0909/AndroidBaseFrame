package com.third.party.library.views.ZRecycleView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZY on 2016/8/30.
 */
public class ZRecyclerView extends RecyclerView {
    private static final String TAG = "ZRecyclerView";

    private Context mContext;
    /*正在加载数据*/
    private boolean isLoadingData = false;
    /*没有数据了*/
    private boolean isnomore = false;
    /*头布局*/
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    /*尾部局*/
    private ArrayList<View> mFootViews = new ArrayList<>();
    /*适配器*/
    private Adapter mAdapter;
    private Adapter mWrapAdapter;
    private float mLastY = -1;
    private static final float DRAG_RATE = 3;
    private ZRecyclerViewLoadingListener mLoadingListener;
    private ZRecyclerViewHeader mRefreshHeader;
    /*是否需要下拉刷新*/
    private boolean pullRefreshEnabled = true;
    /*是否需要上拉加载*/
    private boolean loadingMoreEnabled = true;
    private static final int TYPE_REFRESH_HEADER = -5;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;
    private static final int HEADER_INIT_INDEX = 10000;
    private static List<Integer> sHeaderTypes = new ArrayList<>();
    private int mPageCount = 0;
    //adapter没有数据的时候显示,类似于listView的emptyView
    private View mEmptyView;
    /*刷新状态*/
    private boolean isLoadMore;
    /*可以滑动 true 可以 fasle 不可以*/
    private boolean isCanMove = true;

    public ZRecyclerView(Context context) {
        this(context, null);
    }

    public ZRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (pullRefreshEnabled) {
            ZRecyclerViewHeader refreshHeader = new ZRecyclerViewHeader(mContext);
            mHeaderViews.add(0, refreshHeader);
            mRefreshHeader = refreshHeader;
        }
        ZRecyclerViewFooter footView = new ZRecyclerViewFooter(mContext);
        addFootView(footView);
        mFootViews.get(0).setVisibility(GONE);
    }

    public void addHeaderView(View view) {
        if (pullRefreshEnabled && !(mHeaderViews.get(0) instanceof ZRecyclerViewHeader)) {
            ZRecyclerViewHeader refreshHeader = new ZRecyclerViewHeader(mContext);
            mHeaderViews.add(0, refreshHeader);
            mRefreshHeader = refreshHeader;
        }
        mHeaderViews.add(view);
        sHeaderTypes.add(HEADER_INIT_INDEX + mHeaderViews.size());
    }

    public void addFootView(final View view) {
        mFootViews.clear();
        mFootViews.add(view);
    }

    public void reset() {
        setIsnomore(false);
        loadMoreComplete();
        refreshComplete();
    }

    /*设置可不可以滑动*/
    public void setCanMove(boolean isCanMove){
        this.isCanMove = isCanMove;
    }

    public boolean isCanMove() {
        return isCanMove;
    }

    /**
     * 刷新状态
     * @return true = 上拉加载 false = 下拉刷新
     */
    public boolean isLoadMore() {
        return isLoadMore;
    }

    /**
     * 非手动下拉刷新调用 （用于界面未关闭 从不可见再到可见重新获取数据时调用）
     * @param loadMore 传false
     */
    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    /**
     * 下拉刷新，传false=不可以下拉
     * @param enabled
     */
    public void setPullRefreshEnabled(boolean enabled) {
        if (mContext != null)
            pullRefreshEnabled = enabled;
    }

    /**
     * 上拉加载，传false=不可以上拉
     * @param enabled
     */
    public void setLoadingMoreEnabled(boolean enabled) {
        if (mContext != null)
            loadingMoreEnabled = enabled;
        if (!enabled) {
            if (mFootViews.size() > 0) {
//                mFootViews.get(0).setVisibility(GONE);
                mFootViews.clear();
            }
        }
    }

    /**
     * 上拉加载完调用
     */
    public void loadMoreComplete() {
        if (mContext != null) {
            isLoadingData = false;
            View footView = mFootViews.get(0);
            if (footView instanceof ZRecyclerViewFooter) {
                ((ZRecyclerViewFooter) footView).setState(ZRecyclerViewFooter.STATE_COMPLETE);
            } else {
                footView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取数据结束 判断有没有数据 显示不显示加载布局
     * @param datasize 接口获取的数据大小
     * @param pagesize 接口加载数据的大小
     */
    public void getDataComplete(int datasize,int pagesize) {
        if (mContext != null) {
            isLoadingData = false;
            if (loadingMoreEnabled) {
                if (datasize < pagesize) {
                    isnomore = true;
                } else {
                    isnomore = false;
                }
            }

            if(isLoadMore){
                /*上拉加载*/
                View footView = mFootViews.get(0);
                if (footView instanceof ZRecyclerViewFooter) {
                    ((ZRecyclerViewFooter) footView).setState(this.isnomore ?
                            ZRecyclerViewFooter.STATE_NOMORE : ZRecyclerViewFooter.STATE_COMPLETE);
                } else {
                    footView.setVisibility(View.GONE);
                }
            }else {
                /*下拉刷新*/
                mRefreshHeader.refreshComplate();
                /*如果可以上拉加载 下拉刷新成功之后显示上拉加载布局  */
                if (loadingMoreEnabled) {
                    View footView = mFootViews.get(0);
                    if (footView instanceof ZRecyclerViewFooter) {
                        ((ZRecyclerViewFooter) footView).setState(this.isnomore ?
                                ZRecyclerViewFooter.STATE_NOMORE : ZRecyclerViewFooter.STATE_INIT);
                    } else {
                        footView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    /**
     * 上拉加载完，有数据传false
     * @param isnomore
     */
    public void setIsnomore(boolean isnomore) {
        if (mContext != null) {
            this.isnomore = isnomore;
            View footView = mFootViews.get(0);
            ((ZRecyclerViewFooter) footView).setState(this.isnomore ? ZRecyclerViewFooter.STATE_NOMORE : ZRecyclerViewFooter.STATE_COMPLETE);
        }
    }

    /**
     * 上拉加载，没有数据
     */
    public void noMoreLoading() {
        if (mContext != null) {
            isLoadingData = false;
            View footView = mFootViews.get(0);
            isnomore = true;
            if (footView instanceof ZRecyclerViewFooter) {
                ((ZRecyclerViewFooter) footView).setState(ZRecyclerViewFooter.STATE_NOMORE);
            } else {
                footView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 下拉刷新完成调用
     */
    public void refreshComplete() {
        if (mContext != null) {
            mRefreshHeader.refreshComplate();
            /*如果可以上拉加载 下拉刷新成功之后显示上拉加载布局*/
            if (loadingMoreEnabled) {
                View footView = mFootViews.get(0);
                if (footView instanceof ZRecyclerViewFooter) {
                    ((ZRecyclerViewFooter) footView).setState(this.isnomore ?
                            ZRecyclerViewFooter.STATE_NOMORE : ZRecyclerViewFooter.STATE_INIT);
                } else {
                    footView.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 设置空布局
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        mDataObserver.onChanged();
    }


    public View getEmptyView() {
        return mEmptyView;
    }


    public void setRefreshHeader(ZRecyclerViewHeader refreshHeader) {
        if (mContext != null)
            mRefreshHeader = refreshHeader;
    }


    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > layoutManager.getChildCount() && !isnomore && mRefreshHeader.getState() < ZRecyclerViewHeader.STATE_REFRESHING) {

                View footView = mFootViews.get(0);
                isLoadingData = true;
                if (footView instanceof ZRecyclerViewFooter) {
                    Log.e(TAG, "onScrollStateChanged: STATE_LOADING");
                    ((ZRecyclerViewFooter) footView).setState(ZRecyclerViewFooter.STATE_LOADING);
                } else {
                    footView.setVisibility(View.VISIBLE);
                }
                if (mContext != null)
                    isLoadMore = true;
                    mLoadingListener.onLoadMore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isCanMove) {
            return super.onTouchEvent(ev);
        }
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isOnTop() && pullRefreshEnabled) {
                    mRefreshHeader.onMove(deltaY / DRAG_RATE);
                    if (mRefreshHeader.getVisiableHeight() > 0 && mRefreshHeader.getState() < ZRecyclerViewHeader.STATE_REFRESHING) {
                        Log.i("getVisiableHeight", "getVisiableHeight = " + mRefreshHeader.getVisiableHeight());
                        Log.i("getVisiableHeight", " mRefreshHeader.getState() = " + mRefreshHeader.getState());
                        return super.onTouchEvent(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled) {
                    if (mRefreshHeader.releaseAction()) {
                        if (mLoadingListener != null) {
                            isLoadMore = false;
                            mLoadingListener.onRefresh();
                        }
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled) {
                    if (mRefreshHeader.releaseAction()) {
                        if (mLoadingListener != null) {
                            isLoadMore = false;
                            mLoadingListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private boolean isOnTop() {
        if (mHeaderViews == null || mHeaderViews.isEmpty()) {
            return false;
        }

        View view = mHeaderViews.get(0);
        if (view.getParent() != null) {
            return true;
        } else {
            return false;
        }
//        LayoutManager layoutManager = getLayoutManager();
//        int firstVisibleItemPosition;
//        if (layoutManager instanceof GridLayoutManager) {
//            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
//        } else if ( layoutManager instanceof StaggeredGridLayoutManager ) {
//            int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
//            ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(into);
//            firstVisibleItemPosition = findMin(into);
//        } else {
//            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//        }
//        if ( firstVisibleItemPosition <= 1 ) {
//             return true;
//        }
//        return false;
    }

    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && mEmptyView != null) {
                int emptyCount = 0;
                if (pullRefreshEnabled) {
                    emptyCount++;
                }
                if (loadingMoreEnabled) {
                    emptyCount++;
                }
                if (adapter.getItemCount() == emptyCount) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    ZRecyclerView.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    ZRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        private ArrayList<View> mHeaderViews;

        private ArrayList<View> mFootViews;

        private int headerPosition = 1;

        public WrapAdapter(ArrayList<View> headerViews, ArrayList<View> footViews, Adapter adapter) {
            this.adapter = adapter;
            this.mHeaderViews = headerViews;
            this.mFootViews = footViews;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        public boolean isContentHeader(int position) {
            return position >= 1 && position < mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        public boolean isRefreshHeader(int position) {
            return position == 0;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                mCurrentPosition++;
                return new SimpleViewHolder(mHeaderViews.get(0));
            } else if (isContentHeader(mCurrentPosition)) {
                if (viewType == sHeaderTypes.get(mCurrentPosition - 1)) {
                    mCurrentPosition++;
                    return new SimpleViewHolder(mHeaderViews.get(headerPosition++));
                }
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootViews.get(0));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        private int mCurrentPosition;

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)) {
                position = position - 1;
                return sHeaderTypes.get(position);
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public void setLoadingListener(ZRecyclerViewLoadingListener listener) {
        mLoadingListener = listener;
    }



//    public void setRefreshing(boolean refreshing) {
//        if (refreshing && pullRefreshEnabled && mLoadingListener != null) {
//            mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
//            mRefreshHeader.onMove(mRefreshHeader.getMeasuredHeight());
//            mLoadingListener.onRefresh();
//        }
//    }
}
