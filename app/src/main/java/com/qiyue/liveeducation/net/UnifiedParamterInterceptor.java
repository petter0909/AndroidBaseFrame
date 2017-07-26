package com.qiyue.liveeducation.net;

import android.content.Context;

import com.qiyue.liveeducation.net.api.UserApi;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZY on 2017/6/20.
 */

public class UnifiedParamterInterceptor implements Interceptor {


    private Context context;
    private boolean addUnifiedParam;//是否添加统一参数

    public UnifiedParamterInterceptor(Context context, boolean addUnifiedParam) {
        this.context = context;
        this.addUnifiedParam = addUnifiedParam;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //是否添加统一请求参数，
        HttpUrl url = null;
        if (addUnifiedParam) {
            url = original.url().newBuilder()
                    .addQueryParameter("userId", "8a9852065b0e8db2015b0e92ed2f0000")
                    .build();
        }
        Request.Builder builder = original.newBuilder();
        builder.method(original.method(), original.body());
        if (url != null) {
            builder.url(url);
        }
        return chain.proceed(builder.build());
    }

}
