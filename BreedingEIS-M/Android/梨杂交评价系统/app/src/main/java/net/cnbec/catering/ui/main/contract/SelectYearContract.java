package net.cnbec.catering.ui.main.contract;

import net.cnbec.catering.bean.GermplasmListByYearBean;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

import java.util.List;

public interface SelectYearContract {


    interface View extends IBaseView {

        void onGermplasmListByYearCallBack(List<GermplasmListByYearBean> object);

        /**
         * 通过年份查询杂交组合库列表
         */
        void onHybridListByYearCallBack(List<HybridListByYearBean> object);
    }

    interface Presenter extends IBasePresenter<SelectYearContract.View> {

        void germplasmListByYear(String introductionYear);

        /**
         * 通过年份查询杂交组合库列表
         */
        void hybridListByYear(String hybridYear);

    }
}
