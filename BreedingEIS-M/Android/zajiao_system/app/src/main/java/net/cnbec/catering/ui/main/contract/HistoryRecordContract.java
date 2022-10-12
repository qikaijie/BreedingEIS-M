package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.PlantHistoryRecordBean;
import net.cnbec.catering.bean.SeedlingHistoryRecordBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;

public interface HistoryRecordContract {
    interface View extends IBaseView {

        void onSeedlingRecordHistoryListCallBack(List<SeedlingHistoryRecordBean> seedlingHistoryRecordBeanList);
        void onPlantRecordHistoryListCallBack(List<PlantHistoryRecordBean> plantHistoryRecordBeanList);
    }

    interface Presenter extends IBasePresenter<View> {


        /**
         * 查询种质记录的历史信息列表
         */
        void seedlingRecordHistoryList(Integer seedlingId, Integer type);

        void plantRecordHistoryList(Integer plantId,Integer type);
    }
}
