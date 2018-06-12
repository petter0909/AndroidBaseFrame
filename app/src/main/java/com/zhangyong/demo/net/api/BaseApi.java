package com.zhangyong.demo.net.api;


import com.zhangyong.demo.base.BaseModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 */
public interface BaseApi {


    @GET("{url}")
    Observable<BaseModel<Object>> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps
    );


    @POST("{url}")
    Observable<BaseModel> executePost(
            @Path("url") String url,
            //  @Header("") String authorization,
            @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(
            @Path("url") String url,
            @Body RequestBody jsonStr);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path("url") String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
