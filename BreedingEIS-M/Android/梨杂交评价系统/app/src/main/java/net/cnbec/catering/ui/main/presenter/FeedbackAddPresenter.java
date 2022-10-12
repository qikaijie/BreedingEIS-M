package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.FeedbackAddContract;

import io.reactivex.disposables.Disposable;


public class FeedbackAddPresenter extends BasePresenter<FeedbackAddContract.View> implements FeedbackAddContract.Presenter {

    @Override
    public void feedbackAdd(String username,String content) {
        Disposable disposable = getDataProvider().feedbackAdd(username,content)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object object) {
                        getMvpView().onFeedbackAddCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

}
