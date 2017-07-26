package com.third.party.library.views.ZRecycleView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.third.party.library.R;


/**
 * Created by expect_xh on 2016/3/23.
 */
public class ZRecyclerViewFooter extends LinearLayout {
    private RelativeLayout mContainer;//布局指向
    private Context mContext;//上下文引用

    public final static int STATE_INIT = 3; //初始化
    public final static int STATE_LOADING = 0; //正在加载
    public final static int STATE_COMPLETE = 1;  //加载完成
    public final static int STATE_NOMORE = 2;  //没有数据了
    private ProgressBar mProgressView;
    private TextView mTextView;
//    private ImageView mProgressBar;    // 正在刷新的图标


    // 均匀旋转动画
//    private RotateAnimation refreshingAnimation;
    public ZRecyclerViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public ZRecyclerViewFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public ZRecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    //初始化
    private void initView(Context context) {
        mContext = context;
        mContainer = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.xrecyclerview_load_more, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        mProgressView = (ProgressBar) mContainer.findViewById(R.id.progress_view);
        mTextView = (TextView) mContainer.findViewById(R.id.tv_content);
//        mProgressBar= (ImageView) findViewById(R.id.listview_foot_progress);
//
//        ///添加匀速转动动画
//        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
//                context, R.anim.rotating);
//        LinearInterpolator lir = new LinearInterpolator();
//        refreshingAnimation.setInterpolator(lir);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setState(int state) {
        switch (state) {
            case STATE_INIT:
                this.setVisibility(VISIBLE);
                mContainer.setVisibility(VISIBLE);
                mProgressView.setVisibility(INVISIBLE);
                break;
            case STATE_LOADING:
                this.setVisibility(VISIBLE);
//                mTextView.setText("正在加载");
//                mTextView.setVisibility(GONE);
                mProgressView.setVisibility(VISIBLE);
//                mProgressBar.setAnimation(refreshingAnimation);
                break;
            case STATE_COMPLETE:
//                mProgressBar.clearAnimation();
                this.setVisibility(GONE);
                mProgressView.setVisibility(INVISIBLE);
                break;
            case STATE_NOMORE:
//                mProgressBar.clearAnimation();
                this.setVisibility(GONE);
                mContainer.setVisibility(GONE);
//                mTextView.setText(R.string.xrecycleview_load_nomore);
//                mTextView.setVisibility(VISIBLE);
                mProgressView.setVisibility(INVISIBLE);
                break;
            default:
                break;
        }
    }
}
