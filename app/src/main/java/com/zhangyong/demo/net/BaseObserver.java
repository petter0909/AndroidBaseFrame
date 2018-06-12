package com.zhangyong.demo.net;

import android.content.Context;

import com.zhangyong.demo.base.BaseModel;
import com.third.party.library.utils.MyLogger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.zhangyong.demo.common.AppGlobal.RELEASE_VERSION;


/**
 * Created by ZY on 2017/6/15.
 */

public abstract class BaseObserver<T> implements Observer<BaseModel<T>> {

    protected  MyLogger logger = MyLogger.getLogger("retrofit BaseObserver", RELEASE_VERSION);

    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        logger.e("onSubscribe  "+d.toString());
    }

    @Override
    public void onNext(BaseModel<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        logger.e( "error:" + e.getMessage());
        onHanderFail(e);
    }

    @Override
    public void onComplete() {
        logger.e(  "onComplete");
    }

    /**
     * 请求数据成功
     * @param t
     */
    protected abstract void onHandleSuccess(T t);

    /**
     * 请求数据失败
     * @param msg
     */
    protected void onHandleError(String msg){

    } ;

    /**
     * 请求数据失败
     * @param e
     */
    protected  void onHanderFail(Throwable e) {

    };




}
