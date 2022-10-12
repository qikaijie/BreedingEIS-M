package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.contract.PJContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class PJPresenter extends BasePresenter<PJContract.View> implements PJContract.Presenter {

    @Override
    public void groupList(Integer speciesId) {
        Disposable disposable = getDataProvider().groupList(speciesId)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<List<GroupListBean>>() {
                    @Override
                    public void _accept(List<GroupListBean> groupListBean) {
                        getMvpView().onGroupListCallBack(groupListBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
