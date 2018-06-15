package com.zhangyong.demo.net.api;


import com.zhangyong.demo.base.BaseModel;
import com.zhangyong.demo.module.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ZY on 2016/12/5.
 */

public interface UserApi {

    /**
     *  获取用户信息 http://www.51chawujia.com/dnf/app/loginuser?bindPhone=15000855025&password=5201314wcx
     */
    @POST("app/loginuser")
    Observable<BaseModel<UserInfo>> getUserInfo(
            @Part("bindPhone") String bindPhone,
            @Part("password") String password

    );
}
