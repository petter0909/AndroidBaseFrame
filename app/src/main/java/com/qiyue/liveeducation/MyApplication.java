package com.qiyue.liveeducation;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by ZY on 2017/6/14.
 */

public class MyApplication extends Application {

    public static Application getInstance() {
        return mInstance;
    }

    public static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Utils.init(getApplicationContext());

    }
}
