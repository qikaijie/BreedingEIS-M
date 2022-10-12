package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.RecordContract;
import net.cnbec.catering.ui.main.contract.SCListContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SCListPresenter extends BasePresenter<SCListContract.View> implements SCListContract.Presenter {

    @Override
    public void plantRecordList(Integer delayDay,Integer hybridId, Integer plantId) {
        Disposable disposable = getDataProvider().plantRecordList(delayDay,hybridId,plantId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<List<PlantRecordBean>>() {
                    @Override
                    public void _accept(List<PlantRecordBean> plantRecordBeans) {
                        getMvpView().onPlantRecordListCallBack(plantRecordBeans);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId) {
        Disposable disposable = getDataProvider().seedlingRecordList(delayDay,germplasmId,seedlingId)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<SeedlingRecordBean>>() {
                    @Override
                    public void _accept(List<SeedlingRecordBean> seedlingRecordBeans) {
                        getMvpView().onSeedlingRecordListCallBack(seedlingRecordBeans);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

//    @Override
//    public void plantInfo(String code) {
//        Disposable disposable = getDataProvider().plantInfo(code)
//                .compose(RxSchedulers.inIoMainLoading(getContext()))
//                .subscribe(new RxConsumer<PlantInfoBean>() {
//                    @Override
//                    public void _accept(PlantInfoBean plantInfoBean) {
//                        getMvpView().onPlantInfoCallBack(plantInfoBean);
//                    }
//
//                    @Override
//                    public void _handleMsg(int status, String msg) {
//                        getMvpView().handleMsg(status, msg);
//                    }
//                }, new RxThrowableConsumer());
//        addTask(disposable);
//    }


    @Override
    public void plantCollectDel(Integer collectId) {
        Disposable disposable = getDataProvider().plantCollectDel(collectId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object o) {
                        getMvpView().onPlantCollectDelCallBack(o);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingCollectDel(Integer collectId) {
        Disposable disposable = getDataProvider().seedlingCollectDel(collectId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object o) {
                        getMvpView().onSeedlingCollectDelCallBack(o);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
