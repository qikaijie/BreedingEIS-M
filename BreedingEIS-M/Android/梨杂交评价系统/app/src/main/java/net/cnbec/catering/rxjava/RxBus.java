package net.cnbec.catering.rxjava;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @Describe: RxBus事件总线
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class RxBus {

    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    private static class Holder {
        private static RxBus instance = new RxBus();
    }

    /**
     * 获取RxBus
     * @return
     */
    public static RxBus getInstance() {
        return Holder.instance;
    }

    /**
     * 发送事件
     * @param event
     */
    public void post(@NonNull RxEvent event) {
        mBus.onNext(event);
    }

    /**
     * 注册事件
     * @param clz
     * @param <T>
     * @return
     */
    public <T> Flowable<T> register(Class<T> clz) {
        return mBus.ofType(clz);
    }

    /**
     * 注册事件
     * @return
     */
    public Flowable<Object> register() {
        return mBus;
    }

    /**
     * 会将所有由mBus生成的Flowable都置completed状态后续的所有消息都收不到了
     */
    public void unregisterAll() {
        mBus.onComplete();
    }

    /**
     * 是否有订阅者
     * @return
     */
    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

}
