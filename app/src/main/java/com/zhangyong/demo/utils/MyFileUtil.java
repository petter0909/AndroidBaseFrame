package com.zhangyong.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * Created by ZY on 2017/9/11.
 */

public class MyFileUtil {


    public static void deleteImg() {
        File localFile = getOwnCacheDirectory(Utils.getApp());
        long l = FileUtils.getDirLength(localFile);
        if (l > 100000000L) {
            FileUtils.deleteDir(localFile);
        }
    }

    public static File getOwnCacheDirectory(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 优先获取SD卡根目录[/storage/sdcard0]
            return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName());
        } else {
            // 应用缓存目录[/data/data/应用包名/cache]
            return new File(context.getCacheDir().getAbsolutePath());
        }
    }

    public static String getOwnCacheDirectoryPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 优先获取SD卡根目录[/storage/sdcard0]
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName();
        } else {
            // 应用缓存目录[/data/data/应用包名/cache]
            return context.getCacheDir().getAbsolutePath();
        }
    }
}
