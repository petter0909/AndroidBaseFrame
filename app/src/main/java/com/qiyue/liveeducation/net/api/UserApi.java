package com.qiyue.liveeducation.net.api;


import com.qiyue.liveeducation.base.BaseModel;
import com.qiyue.liveeducation.module.UserInfo;

import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ZY on 2016/12/5.
 */

public interface UserApi {

    /**
     *  获取用户信息 http://api.yiqiquxiang.com:9002/api/userinfo/user/getUserInfo?userId=8a9852065b0e8db2015b0e92ed2f0000
     */
    @GET("getUserInfo")
    Observable<BaseModel<UserInfo>> getUserInfo(
            //@Query("userId") String userId

    );
}
