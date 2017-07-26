package com.qiyue.liveeducation.net;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.third.party.library.utils.MyLogger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.qiyue.liveeducation.common.AppGlobal.BaseConfigs.baseurlhttp;
import static com.qiyue.liveeducation.common.AppGlobal.RELEASE_VERSION;

/**
 * Created by ZY on 2017/6/15.
 */

public class RetrofitFactory {

    protected static MyLogger logger = MyLogger.getLogger("retrofit Factory", RELEASE_VERSION);
    private static final String BASE_URL = baseurlhttp;
    private static Context mContext = Utils.getContext();
    private static Boolean isCache ;
    private static Boolean addUnifiedParam ;

    private static final HttpLoggingInterceptor.Level loglevel =
            RELEASE_VERSION ? HttpLoggingInterceptor.Level.NONE : HttpLoggingInterceptor.Level.BODY;

    /*超时时间*/
    private static final long CONNECT_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 5;
    private static final long WRITE_TIMEOUT = 5;

    /**
     * 不缓存不添加统一参数
     *
     * @param endurl
     * @param server
     * @param <T>
     * @return
     */
    public static <T> T getApi(String endurl, Class<T> server) {
        return getApi(endurl, server, false, false);
    }

    /**
     * @param endurl
     * @param server
     * @param isCache         缓存
     * @param addUnifiedParam 添加通用参数
     * @param <T>
     * @return
     */
    public static <T> T getApi(String endurl, Class<T> server, boolean isCache, boolean addUnifiedParam) {
        RetrofitFactory.isCache = isCache;
        RetrofitFactory.addUnifiedParam = addUnifiedParam;
        OkHttpClient okHttpClient = getOkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL + endurl)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(server);
    }

    /**
     * Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okbuilder = new OkHttpClient.Builder();
        okbuilder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                logger.e(message);
            }
        }).setLevel(loglevel));
        okbuilder.addInterceptor(new UnifiedParamterInterceptor(mContext, addUnifiedParam));
        okbuilder.addInterceptor(new CaCheInterceptor());
        okbuilder.addNetworkInterceptor(new CaCheInterceptor());//添加缓存拦截器
        okbuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okbuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        okbuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
        okbuilder.connectionPool(new ConnectionPool(8, 10, TimeUnit.SECONDS));
        //是否添加缓存
        if (isCache) {
            //缓存容量
            long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
            //缓存路径
            String cacheFile = mContext.getCacheDir() + "/okhttp";
            Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);
            okbuilder.cache(cache);
        }
        OkHttpClient okHttpClient = okbuilder.build();

        return okHttpClient;
    }


    /**
     * 这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
     * 出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
     */
    private static Interceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            logger.e(message);
        }
    }).setLevel(loglevel);

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
                .create();
    }
}
