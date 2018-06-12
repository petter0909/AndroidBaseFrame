package com.zhangyong.demo.ui.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zhangyong.demo.R;
import com.zhangyong.demo.base.BaseFragment;

import butterknife.BindView;


/**
 * Created by ZY on 2017/6/14.
 */
@SuppressLint("ValidFragment")
public class MineFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.text)
    TextView text;

    private String title;

    public static MineFragment newInstance(){
        return new MineFragment();
    }

    public static Bundle getBunder(String title){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        return bundle;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.activity_fragment_home;
    }

    @Override
    protected void initIntentData(Bundle arguments) {
        if (arguments!=null){
            title = arguments.getString("title");
        }
    }


    @Override
    protected void initPageView(View inflateView, Bundle savedInstanceState) {
        text.setText(title);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void requestDataAgain() {

    }

    @Override
    protected String initPageTitleName() {
        return title;
    }

}