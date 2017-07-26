package com.qiyue.liveeducation.net;

import android.content.Context;

import com.qiyue.liveeducation.base.BaseModel;
import com.third.party.library.utils.MyLogger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.qiyue.liveeducation.common.AppGlobal.RELEASE_VERSION;


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
        HttpDialogUtils.showDialog(mContext);
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
        HttpDialogUtils.dismissDialog(mContext);
        logger.e( "error:" + e.getMessage());
        onHanderFail(e);
    }

    @Override
    public void onComplete() {
        HttpDialogUtils.dismissDialog(mContext);
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