package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.CollectAddBean;
import net.cnbec.catering.bean.CollectDelBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordAddBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordAddBean;
import net.cnbec.catering.network.requestBean.PlantRecordAddRequestBean;
import net.cnbec.catering.network.requestBean.SeedlingRecordAddRequestBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.PJFromContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class PJFromPresenter extends BasePresenter<PJFromContract.View> implements PJFromContract.Presenter {


    @Override
    public void plantInfo(String code) {
        Disposable disposable = getDataProvider().plantInfo(code)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<PlantInfoBean>() {
                    @Override
                    public void _accept(PlantInfoBean plantInfoBean) {
                        getMvpView().onPlantInfoCallBack(plantInfoBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void plantCollectAdd(Integer level, Integer plantId) {
        Disposable disposable = getDataProvider().plantCollectAdd(level,plantId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object object) {
                        getMvpView().onPlantCollectAddCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

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
    public void plantRecordAdd(PlantRecordAddRequestBean plantRecordAddRequestBean) {
        Disposable disposable = getDataProvider().plantRecordAdd(plantRecordAddRequestBean)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<PlantRecordAddBean>() {
                    @Override
                    public void _accept(PlantRecordAddBean plantRecordAddBean) {
                        getMvpView().onPlantRecordAddCallBack(plantRecordAddBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingInfo(String code) {
        Disposable disposable = getDataProvider().seedlingInfo(code)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<SeedlingInfoBean>() {
                    @Override
                    public void _accept(SeedlingInfoBean object) {
                        getMvpView().onSeedlingInfoCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingCollectAdd(Integer level, Integer seedlingId) {
        Disposable disposable = getDataProvider().seedlingCollectAdd(level,seedlingId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object object) {
                        getMvpView().onSeedlingCollectAddCallBack(object);
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

    @Override
    public void seedlingRecordAdd(SeedlingRecordAddRequestBean seedlingRecordAddRequestBean) {
        Disposable disposable = getDataProvider().seedlingRecordAdd(seedlingRecordAddRequestBean)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<SeedlingRecordAddBean>() {
                    @Override
                    public void _accept(SeedlingRecordAddBean seedlingRecordAddBean) {
                        getMvpView().onSeedlingRecordAddCallBack(seedlingRecordAddBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
