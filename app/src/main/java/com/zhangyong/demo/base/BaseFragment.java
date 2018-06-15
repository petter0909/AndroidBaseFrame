package com.zhangyong.demo.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangyong.demo.LoginActivity;
import com.zhangyong.demo.R;
import com.third.party.library.utils.MyLogger;
import com.third.party.library.views.CircleProgressDialog;
import com.third.party.library.views.MultipleStatusView;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhangyong.demo.common.AppGlobal.RELEASE_VERSION;

@SuppressLint("ValidFragment")
public abstract class BaseFragment extends Fragment {
    protected MyLogger logger = MyLogger.getLogger(getClass().getSimpleName(), RELEASE_VERSION);
    /**
     * Fragment Content view
     */
    private View inflateView;
    /**
     * 所属Activity
     */
    public Activity mActivity;
    /**
     * 记录是否已经创建了,防止重复创建
     */
    private boolean viewCreated;

    @Nullable
    @BindView(R.id.myMultipleStatusView)
    public MultipleStatusView multiplestatusview;

    @Nullable
    @BindView(R.id.toolbar_title)
    public TextView toolbar_title;


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * fragment当前状态是否可见
     */
    protected boolean mIsVisible;
    /**
     * fragment当前状态是否影藏
     */
    protected boolean mIsHidden;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        mIsHidden = hidden;
        if (hidden) {
            onHidden();
        } else {
            onDisplay();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     */
    protected void lazyLoad() {

    }

    /**
     * 隐藏
     */
    protected void onHidden() {

    }

    /**
     * 显示
     */
    protected void onDisplay() {

    }

    /**
     * 转圈圈dialog
     **/
    protected CircleProgressDialog mCircleProgressDialog = null;

    /**
     * 转圈圈dialog show
     */
    protected void showCircleProgressDialog() {
        if (mCircleProgressDialog == null) {
            mCircleProgressDialog = new CircleProgressDialog(getActivity());
        }
        if (!getActivity().isFinishing() && !mCircleProgressDialog.isShowing()) {
            mCircleProgressDialog.show();
        }
    }

    /**
     * 转圈圈dialog dismiss
     */
    protected void dismissCircleProgressDialog() {
        if (mCircleProgressDialog != null) {
            mCircleProgressDialog.dismiss();
        }
        mCircleProgressDialog = null;
    }


    @Override
    final public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e("onCreate...");
        // 防止重复调用onCreate方法，造成在initData方法中adapter重复初始化问题
        if (!viewCreated) {
            viewCreated = true;
            initIntentData(getArguments());
        }
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.e("onCreateView...");
        if (null == inflateView) {
            // 强制竖屏显示
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            int layoutResId = getCreateViewLayoutId();
            if (layoutResId > 0)
                inflateView = inflater.inflate(getCreateViewLayoutId(), container, false);

            // 解决点击穿透问题
            inflateView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        return inflateView;
    }

    @Override
    final public void onViewCreated(View view, Bundle savedInstanceState) {
        logger.e("onViewCreated.....");
        if (viewCreated) {
            findView(view, savedInstanceState);
            initPageView(view, savedInstanceState);
            initListener();
            viewCreated = false;
        }
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    @LayoutRes
    protected abstract int getCreateViewLayoutId();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     *
     * @param arguments
     */
    protected abstract void initIntentData(Bundle arguments);

    /**
     * 此方法用于初始化布局中所有的View，如果使用了View注入框架则不需要调用
     */
    protected void findView(View inflateView, Bundle savedInstanceState) {
        ButterKnife.bind(this, inflateView);
        if (toolbar_title != null) {
            toolbar_title.setText(initPageTitleName());
        }
        if (multiplestatusview != null) {
            multiplestatusview.setOnRetryClickListener(onRetryClickListener);
        }
    }

    ;

    /**
     * 此方法用于设置View显示数据
     */
    protected abstract void initPageView(View inflateView, Bundle savedInstanceState);

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 重新加载数据
     */
    protected abstract void requestDataAgain();

    /**
     * 初始化页面title的名字
     */
    protected abstract String initPageTitleName();

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        logger.e("onAttach... activity = " + activity.toString());
        this.mActivity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    protected void showNext(Fragment fragment, int id) {
        showNext(fragment, id, null);
    }

    protected void showNext(Fragment fragment, int id, Bundle b) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(id, fragment);
        if (b != null) {
            fragment.setArguments(b);
        }
        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 返回上一个界面
     */
    protected void back() {
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * 返回指定栈上一个界面
     *
     * @param stackFlag
     */
    protected void back(String stackFlag) {
        getActivity().getSupportFragmentManager().popBackStackImmediate(stackFlag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * 结束activity；
     */
    protected void finish() {
        getActivity().finish();
    }

    /**
     * 跳转activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 跳转activity ，绑定数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     * 打开activity
     *
     * @param pAction activity动作
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 打开activity
     *
     * @param pAction activity动作
     * @param pBundle 数据
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        // 解决ViewPager嵌套引起的crash，参考：
        // http://stackoverflow.com/questions/18977923/viewpager-with-nested-fragments
        // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendEmptyMessageDelayed(int type, int time) {
        mHandler.sendEmptyMessageDelayed(type, time);
    }

    /**
     * 异步消息处理
     **/
    @SuppressLint("HandlerLeak")
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mCircleProgressDialog != null) {
                        mCircleProgressDialog.dismiss();
                        mCircleProgressDialog = null;
                    }
                    break;
                default:
                    handleMsg(msg);
                    break;
            }
        }
    };

    /**
     * 消息处理回调,子类复写
     *
     * @param msg
     */
    protected void handleMsg(Message msg) {
    }

    private final View.OnClickListener onRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiplestatusview != null) {
                multiplestatusview.showLoading();
            }
            requestDataAgain();
        }
    };

}
