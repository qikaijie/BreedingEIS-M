package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.io.File;

public interface TakeAPhoneContract {

    interface View extends IBaseView {

        /**
         * 上传文件
         */
        void onUploadFileCallBack(String urlpath);
    }

    interface Presenter extends IBasePresenter<View> {

        /**
         * 上传文件
         */
        void uploadFile(File file, String fileType,String keyStr,Integer valueId,Integer businessType);
    }
}
