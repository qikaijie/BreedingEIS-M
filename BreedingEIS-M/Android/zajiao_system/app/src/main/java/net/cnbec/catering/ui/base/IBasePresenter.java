package net.cnbec.catering.ui.base;

import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * @Describe: 基础presenter
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public interface IBasePresenter<V> {
    /**
     * 设置上下文环境
     *
     * @param context
     */
    void setContext(Context context);

    /**
     * 获取上下文环境
     *
     * @return
     */
    Context getContext();

    /**
     * 关联view
     *
     * @param mvpView 业务回调view
     */
    void attachView(V mvpView);

    /**
     * 销毁view
     */
    void detachView();

    /**
     * 添加任务
     *
     * @param disposable 观察对象
     */
    void addTask(Disposable disposable);
}
