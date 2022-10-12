package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.bean.SpeciesListBean;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.MeContract;
import net.cnbec.catering.ui.main.contract.RecordContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class RecordPresenter extends BasePresenter<RecordContract.View> implements RecordContract.Presenter {

    @Override
    public void plantRecordList(Integer delayDay,Integer hybridId, Integer plantId,Integer pageNum,Integer pageSize) {
        Disposable disposable = getDataProvider().plantRecordList(delayDay,hybridId,plantId,pageNum,pageSize)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
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

    @Override
    public void seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId, Integer pageNum, Integer pageSize) {
        Disposable disposable = getDataProvider().seedlingRecordList(delayDay,germplasmId,seedlingId,pageNum,pageSize)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<SeedlingRecordBean>>() {
                    @Override
                    public void _accept(List<SeedlingRecordBean> objects) {
                        getMvpView().onSeedlingRecordListCallBack(objects);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void speciesList() {
        Disposable disposable = getDataProvider().speciesList()
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<SpeciesListBean>>() {
                    @Override
                    public void _accept(List<SpeciesListBean> objects) {
                        getMvpView().onSpeciesListCallBack(objects);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void speciesDetails(Integer speciesId) {
        Disposable disposable = getDataProvider().speciesDetails(speciesId)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<SpeciesInfoBean>() {
                    @Override
                    public void _accept(SpeciesInfoBean speciesInfoBean) {
                        getMvpView().onSpeciesDetailsCallBack(speciesInfoBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
