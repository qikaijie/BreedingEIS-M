package net.cnbec.catering.ui.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

/**
 * @Describe: 懒加载fragment
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/21
 */


public abstract class BaseLazyFragment<V extends IBaseView, P extends IBasePresenter<V>> extends BaseFragment<V,P> {

    /**
     * 是否对用户可见
     */
    protected boolean isVisible;
    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean isPrepare;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isLazyLoad()) {
            isPrepare = true;
            onLazyLoad();
        } else {
            onLazyLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisible() {
        onLazyLoad();
    }

    /**
     * 用户不可见执行
     */
    protected void onInvisible() {

    }

    /**
     * 赖加载数据，只有在显示当前Fragment时才会加载数据
     */
    private void onLazyLoad() {
        if (isVisible && isPrepare) {
            isPrepare = false;
            initLazyData();
        }
    }

    /**
     * 赖加载初始化数据(用户可见时加载)
     */
    protected void initLazyData() {

    }

    /**
     * 是否懒加载
     *
     * @return the boolean
     */
    protected boolean isLazyLoad() {
        return true;
    }

}

