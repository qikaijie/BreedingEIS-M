package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.BaseBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;

import io.reactivex.Flowable;


public interface SCListContract {


    interface View extends IBaseView {

        void onPlantRecordListCallBack(List<PlantRecordBean> plantRecordBeans);

//        void onPlantInfoCallBack(PlantInfoBean plantInfoBean);

        void onSeedlingRecordListCallBack(List<SeedlingRecordBean> seedlingRecordBeans);


        /**
         * 取消收藏
         */
        void onPlantCollectDelCallBack(Object o);

        /**
         * 取消收藏
         */
        void onSeedlingCollectDelCallBack(Object o);
    }

    interface Presenter extends IBasePresenter<SCListContract.View> {

        void plantRecordList(Integer delayDay,Integer hybridId, Integer plantId);

//        void plantInfo(String code);

        void seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId);



        /**
         * 取消收藏
         */
        void plantCollectDel(Integer collectId);

        /**
         * 取消收藏
         */
        void seedlingCollectDel(Integer collectId);

    }
}
