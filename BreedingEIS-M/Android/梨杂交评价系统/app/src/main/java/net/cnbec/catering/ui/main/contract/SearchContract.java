package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantListByCodeBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingListByCodeBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;

public interface SearchContract {


    interface View extends IBaseView {

        void onSeedlingListByNameCallBack(List<SeedlingListByCodeBean> seedlingListByCodeBeanList);
        void onPlantListByNameCallBack(List<PlantListByCodeBean> plantListByCodeBeanList);

        void onPlantInfoCallBack(PlantInfoBean plantInfoBean);
        void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoBean);
    }

    interface Presenter extends IBasePresenter<SearchContract.View> {

        void seedlingListByName(String code);
        void plantListByName(String code);

        void plantInfo(String code);
        void seedlingInfo(String code);

    }
}
