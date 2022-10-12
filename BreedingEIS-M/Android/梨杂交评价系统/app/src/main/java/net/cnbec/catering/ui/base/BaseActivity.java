package net.cnbec.catering.ui.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.gyf.barlibrary.ImmersionBar;

import net.cnbec.catering.R;
import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.ui.login.activity.LoginActivity;
import net.cnbec.catering.util.ToastUtil;
import net.cnbec.catering.util.UserInfoUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Describe: activity基类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public abstract class BaseActivity<V extends IBaseView, P extends IBasePresenter<V>> extends AppCompatActivity {

    /**
     * 当前类实例
     */
    public static BaseActivity instance;

    /**
     * Presenter
     */
    private P presenter;

    /**
     * ButterKnife解绑
     */
    private Unbinder unbinder;

    /**
     * 沉浸式状态栏
     */
    private ImmersionBar immersionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        instance = this;

        //是否全屏
        if (applyFullScreen()) {
            setFullScreenModel();
        }
        //是否设置沉浸式状态栏
        else if (applyImmersionBar()) {
            setImmersionBar(getStatusBarColor());
        }
        unbinder = ButterKnife.bind(this);

        presenter = initPresenter();
        if (presenter != null) {
            presenter.setContext(this);
            presenter.attachView((V) this);
        }

        init(savedInstanceState);
        initToolbar(savedInstanceState);
        initViews(savedInstanceState);
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {

        unbinder.unbind();

        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 获取当前类实例
     *
     * @return
     */
    public static BaseActivity getInstance() {
        return instance;
    }


    /**
     * 是否设置沉浸式状态栏
     *
     * @return
     */
    protected boolean applyImmersionBar() {
        return true;
    }

    /**
     * 是否设置全屏显示
     *
     * @return
     */
    protected boolean applyFullScreen() {
        return false;
    }

    /**
     * 系统StatusBar颜色
     *
     * @return
     */
    protected int getStatusBarColor() {
        return R.color.white_bg;
    }

    /**
     * 设置系统statusBar颜色
     *
     * @param statusBarColor 状态栏颜色
     */
    protected void setImmersionBar(int statusBarColor) {
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarColor(statusBarColor)
                .keyboardEnable(true)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    /**
     * 全屏App内容填充状态栏
     */
    protected void setFullScreenModel() {
        immersionBar = ImmersionBar.with(this);
        immersionBar.keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    /**
     * 布局layout id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化Presenter
     *
     * @return P
     */
    protected abstract P initPresenter();

    /**
     * 初始化
     */
    protected void init(Bundle savedInstanceState) {
    }

    /**
     * 初始化toolBar
     */
    protected void initToolbar(Bundle savedInstanceState) {
    }

    /**
     * 初始化view
     */
    protected void initViews(Bundle savedInstanceState) {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 设置监听器
     */
    protected void initListener() {
    }


    /**
     * 获取Presenter
     *
     * @return P
     */
    protected P getPresenter() {
        return presenter;
    }

    /**
     * 显示进度条
     * notice：基类中实现IBaseView的回调
     */
    public void showProgress() {

    }

    /**
     * 隐藏进度条
     * notice：基类中实现IBaseView的回调
     */
    public void hideProgress() {

    }

    /**
     * 处理消息
     * notice：基类中实现IBaseView的回调
     *
     * @param status
     * @param msg
     */
    public void handleMsg(int status, String msg) {
        ToastUtil.show(msg);
        //token失效,跳转到登录
//        if (status == Constant.RESPONSE_TOKEN_INVALID) {
//            UserInfoUtil.exitLoginClearRecord();
//            LoginActivity.startActivity(this);
//        }
        //Http请求消息体提示错误信息
        if (status == Constant.RESPONSE_SHOW_MSG) {
            ToastUtil.show(msg);
        }
    }

}

