package com.zhangyong.demo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.zhangyong.demo.LoginActivity;
import com.zhangyong.demo.R;
import com.zhangyong.demo.net.HttpDialogUtils;
import com.third.party.library.utils.ActivityManagerTool;
import com.third.party.library.utils.ClickToHideKeyBoard;
import com.third.party.library.utils.MyLogger;
import com.third.party.library.utils.StatusBarUtil;
import com.third.party.library.views.MultipleStatusView;
import com.third.party.library.views.ToastView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhangyong.demo.common.AppGlobal.RELEASE_VERSION;


/**
 * Activity基类 , 抽出公共的方法， 包含消息处理，Http请求处理，视图处理
 */
public abstract class TranslucentBarBaseActivity extends AppCompatActivity {

    protected MyLogger logger = MyLogger.getLogger(this.getClass()
            .getSimpleName(), RELEASE_VERSION);

    public Activity mActivity;

    @Nullable
    @BindView(R.id.myMultipleStatusView)
    public
    MultipleStatusView multiplestatusview;

    @Nullable
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Nullable
    @BindView(R.id.btn_go_back)
    View btn_go_back;

    private final View.OnClickListener onRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoadingview();
            requestDataAgain();
        }
    };


    /**
     * 显示加载布局
     */
    public void showLoadingview() {
        if (multiplestatusview != null) multiplestatusview.showLoading();
    }

    /**
     * 显示空布局
     */
    public void showEmptyview() {
        if (multiplestatusview != null) multiplestatusview.showEmpty();
    }

    /**
     * 显示错误布局
     */
    public void showErrorview() {
        if (multiplestatusview != null) multiplestatusview.showError();
    }

    /**
     * 显示无网络布局
     */
    public void showNoNetworkview() {
        if (multiplestatusview != null) multiplestatusview.showNoNetwork();
    }

    /**
     * 显示内容布局
     */
    public void showContentview() {
        if (multiplestatusview != null) multiplestatusview.showContent();
    }

    /**
     * 普通toast
     * @param msg
     */
    public void toast(String msg){
        ToastView.showBottom(msg);
    }

    /**
     * 带图片的toast
     * @param msg
     * @param imgId
     */
    public void toastImg(String msg,int imgId){
        ToastView.show(imgId,msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(initPageLayoutID());
        ActivityManagerTool.getActivityManager().add(this);
        ButterKnife.bind(this);
        initIntentData();
        initPageView();
        initListener();
        steepToolBar();
        process(savedInstanceState);
        if (multiplestatusview != null) {
            multiplestatusview.setOnRetryClickListener(onRetryClickListener);
        }
        setToolbar_title(initPageTitleName());
        if (btn_go_back!=null){
            btn_go_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackListenr();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置title
     * @param title
     */
    public void setToolbar_title(String title){
        if (toolbar_title != null) {
            toolbar_title.setText(title);
        }
    }

    /**
     * 转圈圈dialog show
     */
    protected void showCircleProgressDialog() {
        HttpDialogUtils.showDialog(getApplicationContext());
    }

    /**
     * 转圈圈dialog dismiss
     */
    protected void dismissCircleProgressDialog() {
        HttpDialogUtils.dismissDialog(getApplicationContext());
    }

    protected void steepToolBar( ) {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
            parentView.setBackgroundResource(R.color.app_top_bac);
            getPhoneType();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * 软键盘隐藏的方法1122
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        boolean touchOnEditText = !(new ClickToHideKeyBoard().toListenering(ev, im, v));
        touchOnEditText(touchOnEditText);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    protected int getPhoneType() {
        return StatusBarUtil.StatusBarLightMode(mActivity);
    }

    /**
     * 设置显示资源视图
     *
     * @return
     */
    protected abstract int initPageLayoutID();

    /**
     * 设置状态栏颜色
     *
     * @return
     */
    protected abstract int setStatusBarTintResource();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected abstract void initIntentData();

    /**
     * 重新加载数据
     */
    protected abstract void requestDataAgain();

    /**
     * 初始化页面视图
     */
    protected abstract void initPageView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();



    /**
     * 初始化页面title的名字
     */
    protected abstract String initPageTitleName();

    /**
     * 处理页面数据业务
     *
     * @param savedInstanceState
     */
    protected abstract void process(Bundle savedInstanceState);

    /**
     *  是否退出
     */
    protected  void isDoFinish(){
        onBackPressed();
    };

    /**
     *  返回键监听
     */
    protected  void onBackListenr(){
        finish();
    };

    /**
     *  点击是否是输入框
     * @param touchOnEditText
     */
    protected  void touchOnEditText(boolean touchOnEditText){
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 不保存fragment, 解决activity被回收导致fragment的getActivity为null
        // super.onSaveInstanceState(outState);
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerTool.getActivityManager().removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            isDoFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void gotoActivity(Class<? extends Activity> clazz) {
        gotoActivity(clazz, false, null);
    }

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        gotoActivity(clazz, finish, null);
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle pBundle) {
        gotoActivity(clazz, false, pBundle);
    }

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish,
                             Bundle pBundle) {
        Intent intent = new Intent(this, clazz);
        if (pBundle != null) {
            intent.putExtras(pBundle);

        }
        this.startActivity(intent);
        if (finish) {
            this.finish();
        }
    }

    /**
     * 打开界面传递数据
     *
     * @param pClass
     * @param key
     * @param value
     */
    protected void gotoActivityBindData(Class<?> pClass, String key,
                                        String value) {
        Intent intent = new Intent(this, pClass);
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            intent.putExtra(key, value);
        }
        startActivity(intent);
    }

    /**
     * 打开界面传递数据
     *
     * @param pClass
     * @param key
     * @param value
     */
    protected void gotoActivityBindDoubleData(Class<?> pClass, String key,
                                              double value) {
        Intent intent = new Intent(this, pClass);
        intent.putExtra(key, value);
        startActivity(intent);
    }
}