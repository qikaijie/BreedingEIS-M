package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;
//import com.lwy.smartupdate.data.VersionBean;
import net.cnbec.catering.bean.VersionBean;


public interface MeContract {


    interface View extends IBaseView {

        void onUserInfoCallBack(UserDetailsBean userDetailsBean);

        void onLogoutCallBack(Object object);

        void onDownloadAndroidCallBack(VersionBean object);
    }

    interface Presenter extends IBasePresenter<MeContract.View> {

        void userInfo();

        void logout();

        void downloadAndroid();

    }
}
