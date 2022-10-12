package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.PlantRecordHistoryInfoBean1;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

public interface PJHistoryRecordDetailsContract {
    interface View extends IBaseView {

        void onPlantRecordHistoryInfoCallBack(PlantRecordHistoryInfoBean1 resultStr);
    }

    interface Presenter extends IBasePresenter<View> {

        void plantRecordHistoryInfo(Integer plantId);
    }
}
