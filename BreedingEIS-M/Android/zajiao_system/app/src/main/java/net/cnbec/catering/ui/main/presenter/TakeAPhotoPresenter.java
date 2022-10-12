package net.cnbec.catering.ui.main.presenter;

import net.cnbec.catering.rxjava.RxConsumer;
import net.cnbec.catering.rxjava.RxSchedulers;
import net.cnbec.catering.rxjava.RxThrowableConsumer;
import net.cnbec.catering.ui.base.BasePresenter;
import net.cnbec.catering.ui.main.contract.TakeAPhoneContract;

import java.io.File;

import io.reactivex.disposables.Disposable;

public class TakeAPhotoPresenter extends BasePresenter<TakeAPhoneContract.View> implements TakeAPhoneContract.Presenter {

    @Override
    public void uploadFile(File file, String fileType,String keyStr,Integer valueId,Integer businessType) {
        Disposable disposable = getDataProvider().uploadFile(file,fileType,keyStr,valueId,businessType)
                .compose(RxSchedulers.inIoMainLoading(getContext()))
                .subscribe(new RxConsumer<String>() {
                    @Override
                    public void _accept(String uploadFileBean) {
                        getMvpView().onUploadFileCallBack(uploadFileBean);
                    }

                    @Override
                    public void _handleMsg(int status, String msg) {
                        getMvpView().handleMsg(status, msg);
                    }
                }, new RxThrowableConsumer());
        addTask(disposable);
    }


}
