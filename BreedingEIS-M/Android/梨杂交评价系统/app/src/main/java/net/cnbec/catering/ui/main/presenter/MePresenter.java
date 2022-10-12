package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.MeContract;
//import com.lwy.smartupdate.data.VersionBean;
import net.cnbec.catering.bean.VersionBean;

import io.reactivex.disposables.Disposable;

public class MePresenter extends BasePresenter<MeContract.View> implements MeContract.Presenter {

    @Override
    public void userInfo() {
        Disposable disposable = getDataProvider().userInfo()
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<UserDetailsBean>() {
                    @Override
                    public void _accept(UserDetailsBean userDetailsBean) {
                        getMvpView().onUserInfoCallBack(userDetailsBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void logout() {
        Disposable disposable = getDataProvider().logout()
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<Object>() {
                    @Override
                    public void _accept(Object object) {
                        getMvpView().onLogoutCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }

    @Override
    public void downloadAndroid() {
        Disposable disposable = getDataProvider().downloadAndroid()
                .compose(RxSchedulers.inIoMainLoading(getContext(),false))
                .subscribe(new RxConsumer<VersionBean>() {
                    @Override
                    public void _accept(VersionBean object) {
                        getMvpView().onDownloadAndroidCallBack(object);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
