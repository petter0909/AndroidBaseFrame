package com.qiyue.liveeducation;
import android.os.Bundle;
import com.qiyue.liveeducation.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    String phones;
    String password;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected int setStatusBarTintResource() {
        return R.color.white;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {
    }

    @Override
    protected void initPageView() {
        /**
         * 此处为界面延迟0秒钟跳转页面
         */
        try {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    login();
                }
            }, 3000);
        } catch (Exception e) {
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void requestDataAgain() {

    }

    @Override
    protected String initPageTitleName() {
        return "欢迎页";
    }

    private void login() {
        //获取主页面page的信息
        logger.e("正在登陆首页……");
        LoginActivity.startActivity(this);
        finish();
    }

}
