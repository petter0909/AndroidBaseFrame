package com.zhangyong.demo;

import android.app.Application;
import android.os.Debug;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;
import com.zhangyong.demo.utils.InitializeService;
import com.zhangyong.demo.utils.MyFileUtil;

/**
 * Created by ZY on 2017/6/14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //检测app启动耗时
        Debug.startMethodTracing("trace.trace");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.init(this);
        InitializeService.start(this);

        Debug.stopMethodTracing();

    }
}
