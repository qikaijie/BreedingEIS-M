package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.PlantRecordDelBean;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.SeedlingRecordInfoBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.PJHistoryRecordDetailsContract;
import net.cnbec.catering.ui.main.contract.RecordDetailsContract;

import io.reactivex.disposables.Disposable;

public class RecordDetailsPresenter extends BasePresenter<RecordDetailsContract.View> implements RecordDetailsContract.Presenter {


    @Override
    public void plantRecordInfo(Integer plantId) {
        Disposable disposable = getDataProvider().plantRecordInfo(plantId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<PlantRecordInfoBean>() {
                    @Override
                    public void _accept(PlantRecordInfoBean plantRecordInfoBean) {
                        getMvpView().onPlantRecordInfoCallBack(plantRecordInfoBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void plantRecordDel(Integer id) {
        Disposable disposable = getDataProvider().plantRecordDel(id)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<PlantRecordDelBean>() {
                    @Override
                    public void _accept(PlantRecordDelBean plantRecordDelBean) {
                        getMvpView().onPlantRecordDelCallBack(plantRecordDelBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingRecordInfo(Integer id) {
        Disposable disposable = getDataProvider().seedlingRecordInfo(id)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<SeedlingRecordInfoBean>() {
                    @Override
                    public void _accept(SeedlingRecordInfoBean seedlingRecordInfoBean) {
                        getMvpView().onSeedlingRecordInfoCallBack(seedlingRecordInfoBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingRecordDel(Integer id) {
        Disposable disposable = getDataProvider().seedlingRecordDel(id)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object object) {
                        getMvpView().onSeedlingRecordDelCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
