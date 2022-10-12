package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.PlantRecordHistoryInfoBean1;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.PJHistoryRecordDetailsContract;

import io.reactivex.disposables.Disposable;

public class PJHistoryRecordDetailsPresenter extends BasePresenter<PJHistoryRecordDetailsContract.View> implements PJHistoryRecordDetailsContract.Presenter {

    @Override
    public void plantRecordHistoryInfo(Integer plantId) {
        Disposable disposable = getDataProvider().plantRecordHistoryInfo(plantId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<PlantRecordHistoryInfoBean1>() {
                    @Override
                    public void _accept(PlantRecordHistoryInfoBean1 resultStr) {
                        getMvpView().onPlantRecordHistoryInfoCallBack(resultStr);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

}
