package com.zhangyong.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.blankj.utilcode.util.FragmentUtils;
import com.zhangyong.demo.base.BaseActivity;
import com.zhangyong.demo.ui.SecondFragment;
import com.zhangyong.demo.ui.ThirdFragment;
import com.zhangyong.demo.ui.home.HomeFragment;
import com.zhangyong.demo.ui.mine.MineFragment;
import com.third.party.library.utils.ActivityManagerTool;
import com.third.party.library.views.ToastView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottom_navigation_bar;
    private List<Fragment> fragments;
    private HomeFragment homeFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private MineFragment mineFragment;
    /**
     * 当前展示的tab索引
     **/
    int mCurShowFragmentIndex;

    public static void startActivity(Context context) {

        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected int setStatusBarTintResource() {
        return R.color.white;
    }

    @Override
    protected void initIntentData() {
    }

    @Override
    protected void initPageView() {
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottom_navigation_bar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "第1页")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColorResource(R.color.home_page_selector_checked)
                        .setInActiveColorResource(R.color.home_page_selector_unchecked))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "第2页")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColorResource(R.color.home_page_selector_checked)
                        .setInActiveColorResource(R.color.home_page_selector_unchecked))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "第3页")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColorResource(R.color.home_page_selector_checked)
                        .setInActiveColorResource(R.color.home_page_selector_unchecked))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "第4页")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColorResource(R.color.home_page_selector_checked)
                        .setInActiveColorResource(R.color.home_page_selector_unchecked))
                .setFirstSelectedPosition(mCurShowFragmentIndex)
                .initialise();
        addFragment();
    }

    private void addFragment() {
        fragments = new ArrayList<>();
        homeFragment = HomeFragment.newInstance();
        homeFragment.setArguments(HomeFragment.getBunder("HomeFragment"));
        secondFragment = SecondFragment.newInstance();
        secondFragment.setArguments(SecondFragment.getBunder("SecondFragment"));
        thirdFragment = ThirdFragment.newInstance();
        thirdFragment.setArguments(ThirdFragment.getBunder("ThirdFragment"));
        mineFragment = MineFragment.newInstance();
        mineFragment.setArguments(MineFragment.getBunder("MineFragment"));
        fragments.add(homeFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(mineFragment);
        FragmentUtils.add(getSupportFragmentManager(), fragments, R.id.flContent, 0);
    }

    @Override
    protected void initListener() {
        assert bottom_navigation_bar != null;
        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (mCurShowFragmentIndex==position){
                    return;
                }
                logger.e("bottom_navigation_bar  onTabSelected  position " + position);
                mCurShowFragmentIndex = position;
                FragmentUtils.show(fragments.get(mCurShowFragmentIndex));
            }

            @Override
            public void onTabUnselected(int position) {
                logger.e("bottom_navigation_bar  onTabUnselected  position " + position);
                FragmentUtils.hide(fragments.get(position));
            }

            @Override
            public void onTabReselected(int position) {
                logger.e("bottom_navigation_bar  onTabReselected  position " + position);
            }
        });
    }



    //连续两次按back（返回）键的指定时长差值
    private static final int DOUBLE_CLICK_TIME_SUB = 1000;
    //前一次按back（返回）键的时间
    private long mPreBackTime = 0;

    /**
     * 这个是按下手机返回键的时候  退出提示对话框
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mPreBackTime < DOUBLE_CLICK_TIME_SUB) {
                ActivityManagerTool.getActivityManager().exit();
            } else {
                ToastView.showBottom("再按一次返回键退出");
                mPreBackTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void requestDataAgain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentview();
            }
        }, 2000);

    }

    @Override
    protected String initPageTitleName() {
        return "首页";
    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }
}
