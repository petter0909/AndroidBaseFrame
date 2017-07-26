package com.third.party.library.views;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.third.party.library.R;


/**
 * Created by xiaozeng on 2015/11/15 0015.1
 * 自定义的提示  Toast
 */
public class ToastView extends Toast {
    private static Toast mToast;

    public ToastView(Context context) {
        super(context);
    }

    /**
     * 自定义的 Toast 样式
     *
     * @param context
     * @param resId
     * @param text
     * @param duration
     * @return
     */
    private static void extentMakeText(Context context, int resId, CharSequence text, int duration) {
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.extent_toast_dialog, null);
        //实例化ImageView和TextView对象
        LinearLayout toast_tv_layout = (LinearLayout) layout.findViewById(R.id.toast_tv_layout);
        if (resId == 0) {
            RelativeLayout imageviewlaout = (RelativeLayout) layout.findViewById(R.id.imageviewlaout);
            imageviewlaout.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(text)) {
                ImageView toastImage = (ImageView) layout.findViewById(R.id.toast_img);
                toastImage.setImageResource(resId);
                toast_tv_layout.setVisibility(View.VISIBLE);
            } else {
                toast_tv_layout.setVisibility(View.GONE);
            }

        }
        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);

        toastText.setText(text);

        mHandler.removeCallbacks(r);
        if (mToast == null) {//只有mToast==null时才重新创建，否则只需更改提示文字111
            mToast = new Toast(context);
            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            mToast.setDuration(duration);
        } else {
            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            mToast.setDuration(duration);
        }
        mHandler.postDelayed(r, duration);//延迟1秒隐藏toast
        mToast.show();
    }

    /**
     * 自定义的 Toast 样式  在下面
     *
     * @param context
     * @param text
     * @param duration
     * @return
     */
    private static void extentMakeTextBottom(Context context, CharSequence text, int duration) {
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.toast_view_dialog_bottom, null);
        //实例化ImageView和TextView对象
        TextView toastText = (TextView) layout.findViewById(R.id.toast_text);
        if (!TextUtils.isEmpty(text)) {
            toastText.setVisibility(View.VISIBLE);
        } else {
            toastText.setVisibility(View.GONE);
        }

        toastText.setText(text);

        mHandler.removeCallbacks(r);
        if (mToast == null) {//只有mToast==null时才重新创建，否则只需更改提示文字111
            mToast = new Toast(context);
            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, SizeUtils.dp2px(63));
            mToast.setDuration(duration);
        } else {
            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, SizeUtils.dp2px(63));
            mToast.setDuration(duration);
        }
        mHandler.postDelayed(r, duration);//延迟1秒隐藏toast
        mToast.show();
    }

    public static void show( int resId, String content, int duration) {
        extentMakeText(Utils.getContext(), resId, content, duration);
    }

    public static void show(int resId, String content) {
        extentMakeText(Utils.getContext(), resId, content, 1000);
    }

    public static void show(String content, int duration) {
        extentMakeText(Utils.getContext(), 0, content, duration);
    }

    public static void show(String content) {
        extentMakeText(Utils.getContext(), 0, content, 1000);
    }

    public static void showBottom(String content) {
        extentMakeTextBottom(Utils.getContext(), content, 1000);
    }

    public static void showBottom(String content, int duration) {
        extentMakeTextBottom(Utils.getContext(), content, duration);
    }

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;//toast隐藏后，将其置为null
        }
    };

}
