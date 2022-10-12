package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.BaseBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.bean.SpeciesListBean;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.network.api.NetWorkManager;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;

import io.reactivex.Flowable;


public interface RecordContract {


    interface View extends IBaseView {

        void onPlantRecordListCallBack(List<PlantRecordBean> plantRecordBeans);

        void onPlantInfoCallBack(PlantInfoBean plantInfoBean);

        void onSeedlingRecordListCallBack(List<SeedlingRecordBean> seedlingRecordBeans);

        void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoBean);


        void onSpeciesListCallBack(List<SpeciesListBean> objects);

        void onSpeciesDetailsCallBack(SpeciesInfoBean speciesInfoBean);
    }

    interface Presenter extends IBasePresenter<RecordContract.View> {

        void plantRecordList(Integer delayDay,Integer hybridId, Integer plantId,Integer pageNum,Integer pageSize);

        void plantInfo(String code);


        void seedlingInfo(String code);

        void seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId, Integer pageNum, Integer pageSize);


        /**
         * 查询物种信息列表
         */
        void speciesList();

        /**
         * 物种信息管理（属性挂靠在物种下）
         */
        void speciesDetails(Integer speciesId);


    }
}
