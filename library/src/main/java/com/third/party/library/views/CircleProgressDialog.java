package com.third.party.library.views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.third.party.library.R;


/**
 * 自定义转圈圈dialog
 * 
 * @author Jason
 */
public class CircleProgressDialog extends ProgressDialog {
    private TextView progressTextTxt;
    private String message = "请稍后";
    public CircleProgressDialog(Context context) {
        super(context, R.style.CircleProgressDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.circle_progress_dialog, null);
        ImageView progressCircleImg = (ImageView) view.findViewById(R.id.img_progress_circle);
        progressTextTxt = (TextView) view.findViewById(R.id.txt_progress_text);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_circle_progress);
        progressCircleImg.setAnimation(anim);
        setContentView(view);
        this.setCanceledOnTouchOutside(false);
    }

    /**
     * 设置提示文本
     * @param msg
     */
    public void setMessage(String msg) {
        progressTextTxt.setText(msg);
    }

//    public CircleProgressDialog(Context context) {
//        super(context);
//        setCancelable(true);//是否可以被取消
//        setMessage(message);//加载显示的信息
//        setProgressStyle(ProgressDialog.STYLE_SPINNER);//圆环风格
//        this.setCanceledOnTouchOutside(false);
//    }
//
//    /**
//     * 设置提示文本
//     * @param message
//     */
//    public void setMsg(String message) {
//        this.message=message;
//    }

}