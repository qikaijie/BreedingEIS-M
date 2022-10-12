package net.cnbec.catering.ui.login.contract;

import net.cnbec.catering.bean.UserInfo;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

/**
 * @Describe: 登录模块业务调用和回调接口
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public interface LoginContract {
    interface View extends IBaseView {
        /**
         * 登录结果回调
         *
         * @param userInfo
         */
        void onLoginCallback(UserInfo userInfo);
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 登录
         *
         * @param mobile
         * @param password
         */
        void login(String mobile, String password);
    }
}
