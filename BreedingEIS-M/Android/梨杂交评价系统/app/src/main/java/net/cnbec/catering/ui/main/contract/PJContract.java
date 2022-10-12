package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;


public interface PJContract {
    interface View extends IBaseView {

        void onGroupListCallBack(List<GroupListBean> groupListBeans);
    }

    interface Presenter extends IBasePresenter<View> {

        void groupList(Integer speciesId);
    }
}
