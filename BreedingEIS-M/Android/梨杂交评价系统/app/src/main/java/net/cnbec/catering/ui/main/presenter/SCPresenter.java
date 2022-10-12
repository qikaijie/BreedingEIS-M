package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.CollectListBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingCollectListBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.RecordContract;
import net.cnbec.catering.ui.main.contract.SCContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SCPresenter extends BasePresenter<SCContract.View> implements SCContract.Presenter {

    @Override
    public void plantCollectList(String plantCode,String order,String prop) {
        Disposable disposable = getDataProvider().plantCollectList(plantCode,order,prop)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<CollectListBean>>() {
                    @Override
                    public void _accept(List<CollectListBean> collectListBeanList) {
                        getMvpView().onPlantCollectList(collectListBeanList);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingCollectList(String seedlingCode,String order,String prop) {
        Disposable disposable = getDataProvider().seedlingCollectList(seedlingCode,order,prop)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<SeedlingCollectListBean>>() {
                    @Override
                    public void _accept(List<SeedlingCollectListBean> objects) {
                        getMvpView().onSeedlingCollectListCallBack(objects);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void seedlingCollectAdd(Integer level,Integer seedlingId) {
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
    public void plantCollectAdd(Integer level,Integer plantId) {
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
    public void seedlingInfo(String code) {
        Disposable disposable = getDataProvider().seedlingInfo(code)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<SeedlingInfoBean>() {
                    @Override
                    public void _accept(SeedlingInfoBean seedlingInfoBean) {
                        getMvpView().onSeedlingInfoCallBack(seedlingInfoBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

}
