package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.CollectListBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.SeedlingCollectListBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;


public interface SCContract {


    interface View extends IBaseView {

        void onPlantCollectList(List<CollectListBean> collectListBeanList);

        void onPlantInfoCallBack(PlantInfoBean plantInfoBean);
        void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoBean);

        /**
         * 收藏
         *
         * level
         */
        void onPlantCollectAddCallBack(Object object);

        /**
         * 收藏
         *
         * level
         */
        void onSeedlingCollectAddCallBack(Object object);

        /**
         * 收藏记录查询
         */
        void onSeedlingCollectListCallBack(List<SeedlingCollectListBean> objects);
    }

    interface Presenter extends IBasePresenter<SCContract.View> {

        void plantCollectList(String plantCode,String order,String prop);

        void plantInfo(String code);
        void seedlingInfo(String code);

        /**
         * 收藏
         *
         * level
         */
        void plantCollectAdd(Integer level,Integer plantId);

        /**
         * 收藏
         *
         * level
         */
        void seedlingCollectAdd(Integer level,Integer seedlingId);

        /**
         * 收藏记录查询
         */
        void seedlingCollectList(String seedlingCode,String order,String prop);

    }
}
