package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.PlantHistoryRecordBean;
import net.cnbec.catering.bean.PlantRecordHistoryInfoBean1;
import net.cnbec.catering.bean.SeedlingHistoryRecordBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.HistoryRecordContract;
import net.cnbec.catering.ui.main.contract.PJHistoryRecordDetailsContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class HistoryRecordPresenter extends BasePresenter<HistoryRecordContract.View> implements HistoryRecordContract.Presenter {


    @Override
    public void plantRecordHistoryList(Integer plantId, Integer type) {
        Disposable disposable = getDataProvider().plantRecordHistoryList(plantId,type)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<PlantHistoryRecordBean>>() {
                    @Override
                    public void _accept(List<PlantHistoryRecordBean> plantHistoryRecordBeanList) {
                        getMvpView().onPlantRecordHistoryListCallBack(plantHistoryRecordBeanList);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingRecordHistoryList(Integer seedlingId, Integer type) {
        Disposable disposable = getDataProvider().seedlingRecordHistoryList(seedlingId,type)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<SeedlingHistoryRecordBean>>() {
                    @Override
                    public void _accept(List<SeedlingHistoryRecordBean> seedlingHistoryRecordBeanList) {
                        getMvpView().onSeedlingRecordHistoryListCallBack(seedlingHistoryRecordBeanList);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

}
