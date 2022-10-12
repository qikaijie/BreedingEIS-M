package net.cnbec.catering.test.contract;

import net.cnbec.catering.db.entity.PrinterEntity;
import net.cnbec.catering.ui.base.IBasePresenter;
import net.cnbec.catering.ui.base.IBaseView;

/**
 * @Describe: //TODO
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/31
 */


public interface TestContract {
    interface View extends IBaseView {


    }

    interface Presenter extends IBasePresenter<View> {

    }
}
