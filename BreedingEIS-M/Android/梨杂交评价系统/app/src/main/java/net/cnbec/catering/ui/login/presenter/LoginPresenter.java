package net.cnbec.catering.ui.login.presenter;

import net.cnbec.catering.bean.UserInfo;
import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.login.contract.LoginContract;

import io.reactivex.disposables.Disposable;

/**
 * @Describe:  登录业务处理
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class LoginPresenter extends BasePresenter<LoginContract.View>implements LoginContract.Presenter {
    @Override
    public void login(String username, String password) {
        Disposable disposable = getDataProvider().login(username,password)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<UserInfo>() {
                    @Override
                    public void _accept(UserInfo userInfo) {
                        getMvpView().onLoginCallback(userInfo);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }
}
