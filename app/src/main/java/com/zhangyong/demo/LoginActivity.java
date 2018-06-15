package com.zhangyong.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhangyong.demo.base.BaseActivity;
import com.third.party.library.views.SearchEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ZY on 2017/6/14.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.searchedit)
    SearchEditText searchedit;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected int setStatusBarTintResource() {
        return R.color.colorAccent;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void initPageView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void requestDataAgain() {

    }

    @Override
    protected String initPageTitleName() {
        return "登录";
    }

    @OnClick({
        R.id.btnLogin
    })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                MainActivity.startActivity(mActivity);
                onBackPressed();
                break;
        }
    }

    @Override
    protected void touchOnEditText(boolean touchOnEditText) {
        super.touchOnEditText(touchOnEditText);
        logger.e("touchOnEditText:"+touchOnEditText);
        if (!touchOnEditText){
            searchedit.clearFocus();
        }
    }
}