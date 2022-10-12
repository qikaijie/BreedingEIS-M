package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;


public interface BaseContractCode {
    interface View extends IBaseView {
    }

    interface Presenter extends IBasePresenter<View> {
    }
}
