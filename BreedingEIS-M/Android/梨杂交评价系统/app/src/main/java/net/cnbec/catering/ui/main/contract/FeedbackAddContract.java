package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

public interface FeedbackAddContract {


    interface View extends IBaseView {

        void onFeedbackAddCallBack(Object object);
    }

    interface Presenter extends IBasePresenter<FeedbackAddContract.View> {

        void feedbackAdd(String username,String content);

    }
}
