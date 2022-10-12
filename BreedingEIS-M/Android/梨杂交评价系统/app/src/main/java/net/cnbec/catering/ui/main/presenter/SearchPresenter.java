package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantListByCodeBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingListByCodeBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.SearchContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    @Override
    public void seedlingListByName(String code) {
        Disposable disposable = getDataProvider().seedlingListByName(code)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<SeedlingListByCodeBean>>() {
                    @Override
                    public void _accept(List<SeedlingListByCodeBean> seedlingListByCodeBeanList) {
                        getMvpView().onSeedlingListByNameCallBack(seedlingListByCodeBeanList);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void plantListByName(String code) {
        Disposable disposable = getDataProvider().plantListByName(code)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<PlantListByCodeBean>>() {
                    @Override
                    public void _accept(List<PlantListByCodeBean> plantListByCodeBeanList) {
                        getMvpView().onPlantListByNameCallBack(plantListByCodeBeanList);
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
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
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


}
