package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.PlantRecordDelBean;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.SeedlingRecordInfoBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

public interface RecordDetailsContract {
    interface View extends IBaseView {

        void onPlantRecordInfoCallBack(PlantRecordInfoBean plantRecordInfoBean);

        void onPlantRecordDelCallBack(PlantRecordDelBean plantRecordDelBean);

        void onSeedlingRecordInfoCallBack(SeedlingRecordInfoBean seedlingRecordInfoBean);

        void onSeedlingRecordDelCallBack(Object object);
    }

    interface Presenter extends IBasePresenter<View> {

        void plantRecordInfo(Integer plantId);

        void plantRecordDel(Integer id);

        void seedlingRecordInfo(Integer id);

        void seedlingRecordDel(Integer id);
    }
}
