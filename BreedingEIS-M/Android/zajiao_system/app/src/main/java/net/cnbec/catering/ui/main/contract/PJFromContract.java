package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordAddBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordAddBean;
import net.cnbec.catering.network.requestBean.PlantRecordAddRequestBean;
import net.cnbec.catering.network.requestBean.SeedlingRecordAddRequestBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

public interface PJFromContract {
    interface View extends IBaseView {
        void onPlantInfoCallBack(PlantInfoBean plantInfoBean);
        void onPlantCollectAddCallBack(Object object);
        void onPlantCollectDelCallBack(Object o);
        void onPlantRecordAddCallBack(PlantRecordAddBean plantRecordAddBean);

        void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoBean);
        void onSeedlingCollectAddCallBack(Object object);
        void onSeedlingCollectDelCallBack(Object o);
        void onSeedlingRecordAddCallBack(SeedlingRecordAddBean seedlingRecordAddBean);
    }

    interface Presenter extends IBasePresenter<View> {
        void plantInfo(String code);
        void plantCollectAdd(Integer level,Integer plantId);
        void plantCollectDel(Integer collectId);
        void plantRecordAdd(PlantRecordAddRequestBean plantRecordAddRequestBean);

        void seedlingInfo(String code);
        void seedlingCollectAdd(Integer level,Integer seedlingId);
        void seedlingCollectDel(Integer collectId);
        void seedlingRecordAdd(SeedlingRecordAddRequestBean seedlingRecordAddRequestBean);
    }
}
