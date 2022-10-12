package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.FeedbackBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;


public interface FeedbackContract {


    interface View extends IBaseView {

        void onFeedbackListCallBack(List<FeedbackBean> feedbackBeans);
    }

    interface Presenter extends IBasePresenter<FeedbackContract.View> {

        void feedbackList();

    }
}
