package com.qiyue.liveeducation.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.qiyue.liveeducation.R;


public class DialogUtils {

    public static DialogUtils dialogUtils;
    public Context mContext;

    public DialogUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static DialogUtils getInstance(Context mContext) {
        if (dialogUtils == null)
            return new DialogUtils(mContext);
        else
            return dialogUtils;
    }


    /**
     * 退出app
     * @param context
     * @param title
     */
    public static void ExitApp(final Activity context, String title) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_show_exit, null);
        final Dialog mDialog = new Dialog(context);
        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //如果你使用的是API 版本是 14+
        //mDialog.getWindow().setDimAmount(0);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉头部，不然会有黑影
        mDialog.setCancelable(false);//点击dialog外面不会消失
        final float scale = context.getResources().getDisplayMetrics().density;
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();

        params.width = SizeUtils.dp2px(250);
        //params.height = (int)( 130 * scale + 0.5f);

        mDialog.getWindow().setAttributes(params);

        mDialog.setCanceledOnTouchOutside(false);

        mDialog.setContentView(view);
        mDialog.show();
        TextView tvTitle = (TextView) view.findViewById(R.id.Tv_hint1);
        RelativeLayout Rl_layout1 = (RelativeLayout) view.findViewById(R.id.Rl_layout1);
        RelativeLayout Rl_layout2 = (RelativeLayout) view.findViewById(R.id.rl_layout2);
        tvTitle.setText(title);

        Rl_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                context.startActivity(new Intent(Settings.ACTION_SETTINGS));

            }
        });

        Rl_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                context.finish();
                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
    }


}
