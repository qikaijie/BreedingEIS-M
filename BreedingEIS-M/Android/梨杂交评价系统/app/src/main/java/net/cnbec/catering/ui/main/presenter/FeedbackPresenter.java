package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.CollectListBean;
import net.cnbec.catering.bean.FeedbackBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingCollectListBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.FeedbackContract;

import java.util.List;

import io.reactivex.disposables.Disposable;


public class FeedbackPresenter extends BasePresenter<FeedbackContract.View> implements FeedbackContract.Presenter {
    @Override
    public void feedbackList() {
        Disposable disposable = getDataProvider().feedbackList()
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<FeedbackBean>>() {
                    @Override
                    public void _accept(List<FeedbackBean> feedbackBeans) {
                        getMvpView().onFeedbackListCallBack(feedbackBeans);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
