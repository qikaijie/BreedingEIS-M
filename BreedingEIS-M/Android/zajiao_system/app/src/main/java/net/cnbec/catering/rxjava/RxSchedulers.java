package net.cnbec.catering.rxjava;

import android.content.Context;

import net.cnbec.catering.ui.common.dialog.LoadingDialog;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Describe: RxJava调度管理
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class RxSchedulers {
    /**
     * RxJava io和main线程
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> inIoMain() {

        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * RxJava io和main线程,是否显示加载框
     *
     * @param context 上下文环境
     * @param isShow  是否显示loading
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> inIoMainLoading(Context context, final boolean isShow) {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            if (isShow) {
                                LoadingDialog.show(context);
                            }
                        })
                        .doOnTerminate(() -> {
                            if (isShow) {
                                LoadingDialog.close();
                            }
                        });
            }
        };
    }


    /**
     * RxJava io和main线程,并显示加载框
     *
     * @param context 上下文环境
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> inIoMainLoading(Context context) {
        return inIoMainLoading(context, true);
    }

}
