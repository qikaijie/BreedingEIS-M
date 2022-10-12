package net.cnbec.catering.ui.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.ui.login.activity.LoginActivity;
import net.cnbec.catering.util.ToastUtil;
import net.cnbec.catering.util.UserInfoUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Describe: fragment基类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/21
 */


public abstract class BaseFragment<V extends IBaseView, P extends IBasePresenter<V>> extends Fragment {

    /**
     * Presenter
     */
    private P presenter;

    /**
     * 基类Activity
     */
    private BaseActivity activity;

    /**
     * ButterKnife解绑
     */
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (BaseActivity) context;

        presenter = initPresenter();
        if (presenter != null) {
            presenter.setContext(activity);
            presenter.attachView((V) this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        init(savedInstanceState);
        initToolbar(savedInstanceState);
        initViews(view, savedInstanceState);
        initListener();
        initData();

    }

    @Override
    public void onDetach() {

        unbinder.unbind();

        if (presenter != null) {
            presenter.detachView();
        }
        super.onDetach();
    }

    /**
     * 初始化Presenter
     *
     * @return PP
     */
    protected abstract P initPresenter();

    /**
     * 布局layout id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected void initViews(View view, Bundle savedInstanceState) {
    }

    ;

    /**
     * 初始化
     */
    protected void init(Bundle savedInstanceState) {
    }

    /**
     * 初始化toobar
     */
    protected void initToolbar(Bundle savedInstanceState) {
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
     * @return
     */
    protected P getPresenter() {
        return presenter;
    }

    /**
     * 获取宿主Activity
     *
     * @return activity
     */
    protected BaseActivity getHoldingActivity() {
        return activity;
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
     *
     * @param status
     * @param msg
     */
    public void handleMsg(int status, String msg) {
        ToastUtil.show(msg);
        //token失效,跳转到登录
//        if (status == Constant.RESPONSE_TOKEN_INVALID) {
//            UserInfoUtil.exitLoginClearRecord();
//            LoginActivity.startActivity(getHoldingActivity());
//        }
        //Http请求消息体提示错误信息
        if (status == Constant.RESPONSE_SHOW_MSG) {
            ToastUtil.show(msg);
        }
    }
}
