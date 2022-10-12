package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.GermplasmListByYearBean;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.SelectYearContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class SelectYearPresenter extends BasePresenter<SelectYearContract.View> implements SelectYearContract.Presenter {

    @Override
    public void germplasmListByYear(String introductionYear) {
        Disposable disposable = getDataProvider().germplasmListByYear(introductionYear)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<GermplasmListByYearBean>>() {
                    @Override
                    public void _accept(List<GermplasmListByYearBean> object) {
                        getMvpView().onGermplasmListByYearCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    /**
     * 通过年份查询杂交组合库列表
     */
    @Override
    public void hybridListByYear(String hybridYear) {
        Disposable disposable = getDataProvider().hybridListByYear(hybridYear)
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<List<HybridListByYearBean>>() {
                    @Override
                    public void _accept(List<HybridListByYearBean> object) {
                        getMvpView().onHybridListByYearCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
