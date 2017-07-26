package com.qiyue.liveeducation.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiyue.liveeducation.R;
import com.qiyue.liveeducation.base.BaseFragment;
import com.qiyue.liveeducation.base.BaseModel;
import com.qiyue.liveeducation.base.BaseRvAdapter;
import com.qiyue.liveeducation.module.UserInfo;
import com.qiyue.liveeducation.net.BaseObserver;
import com.qiyue.liveeducation.net.RetrofitFactory;
import com.qiyue.liveeducation.net.RxSchedulers;
import com.qiyue.liveeducation.net.api.UserApi;
import com.qiyue.liveeducation.utils.MyRecyclerViewLoadingListener;
import com.third.party.library.views.ZRecycleView.ZRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by ZY on 2017/6/14.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.recycleview)
    ZRecyclerView recycleview;

    private String title;
    private ArrayList<UserInfo> list;
    private BaseRvAdapter<UserInfo> adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static Bundle getBunder(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        return bundle;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.activity_fragment_home;
    }

    @Override
    protected void initIntentData(Bundle arguments) {
        if (arguments != null) {
            title = arguments.getString("title");
        }
    }


    @Override
    protected void initPageView(View inflateView, Bundle savedInstanceState) {
        text.setText(title);
        TextView textView = new TextView(mActivity);
        textView.setText(title);
        ImageView imageView = new ImageView(mActivity);
        imageView.setImageResource(R.mipmap.ic_launcher);
        ImageView imageView2 = new ImageView(mActivity);
        imageView2.setImageResource(R.mipmap.ic_launcher);
        recycleview.addHeaderView(imageView);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleview.setLoadingListener(new MyRecyclerViewLoadingListener() {

            @Override
            protected void onRefreshSuccess() {
                getUserInfo();
                recycleview.refreshComplete();
            }

            @Override
            protected void onLoadMoreSuccess() {
                getUserInfo();
                recycleview.loadMoreComplete();
            }


        });

        list = new ArrayList<UserInfo>();
        adapter = new BaseRvAdapter<UserInfo>(getActivity(), R.layout.recycle_adapter_item, list) {
            @Override
            public void initData(ViewHolder holder, UserInfo o) {
                holder.setText(R.id.item_text, o.toString());
            }

        };
        recycleview.setAdapter(adapter);
        getUserInfo();
    }

    private void getUserInfo() {
        UserApi userApi = RetrofitFactory.getApi("userinfo/user/", UserApi.class, true, true);
        userApi.getUserInfo().compose(RxSchedulers.<BaseModel<UserInfo>>compose())
                .subscribe(new BaseObserver<UserInfo>(mActivity) {

                    @Override
                    protected void onHandleSuccess(UserInfo userInfo) {
                        adapter.addData(userInfo);
                        text.setText(userInfo.toString());
                    }

                });
    }

    @Override
    protected void initListener() {
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
            }
        });
    }

    @Override
    protected void requestDataAgain() {

    }

    @Override
    protected String initPageTitleName() {
        return title;
    }

}