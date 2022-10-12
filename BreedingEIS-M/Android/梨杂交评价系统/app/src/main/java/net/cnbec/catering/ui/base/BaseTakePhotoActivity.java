package net.cnbec.catering.ui.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.gyf.barlibrary.ImmersionBar;

import net.cnbec.catering.R;
import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.util.ToastUtil;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Describe: activity基类
 * @Author: huangjc
 * @Email: 252431193@qq.com
 * @Date: 2018/3/19
 */


public abstract class BaseTakePhotoActivity<V extends IBaseView, P extends IBasePresenter<V>> extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {

    /**
     * 当前类实例
     */
    public static BaseTakePhotoActivity instance;

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

    private static final String TAG = BaseTakePhotoActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
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
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
    public void removeTakePhoto() {
        if (takePhoto != null) {
            takePhoto = null;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(org.devio.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
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
    public static BaseTakePhotoActivity getInstance() {
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

