package com.qiyue.liveeducation.utils;

import android.os.Handler;

import com.third.party.library.views.ZRecycleView.ZRecyclerViewLoadingListener;

/**
 * Created by ZY on 2017/7/4.
 */

public abstract class MyRecyclerViewLoadingListener implements ZRecyclerViewLoadingListener {
    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadMoreSuccess();
            }
        }, 500);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefreshSuccess();
            }
        }, 500);
    }

    /**
     * 下拉刷新成功
     */
    protected abstract void onRefreshSuccess();

    /**
     * 上拉加载成功
     */
    protected abstract void onLoadMoreSuccess();


}
