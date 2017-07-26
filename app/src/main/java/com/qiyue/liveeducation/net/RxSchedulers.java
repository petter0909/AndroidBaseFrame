package com.qiyue.liveeducation.net;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.third.party.library.utils.MyLogger;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.qiyue.liveeducation.common.AppGlobal.RELEASE_VERSION;

/**
 * Created by ZY on 2017/6/15.
 */

public class RxSchedulers {

    protected static MyLogger logger = MyLogger.getLogger("retrofit RxSchedulers", RELEASE_VERSION);


    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(final Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.newThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                logger.e("accept:"+disposable.toString());
                                if (!NetworkUtils.isConnected()) {
                                    ToastUtils.showLong("网络连接失败,请检查网络");
                                }
                            }
                        })
                        .map(new Function<T, T>() {
                            @Override
                            public T apply(@NonNull T t) throws Exception {
                                logger.e("map Function"+t.toString());
                                return t;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
