package net.cnbec.catering.test.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import net.cnbec.catering.R;
import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.db.entity.PrinterEntity;
import net.cnbec.catering.test.contract.TestContract;
import net.cnbec.catering.test.presenter.TestPresenter;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.common.dialog.LoadingDialog;
import net.cnbec.catering.ui.login.activity.LoginActivity;
import net.cnbec.catering.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * @Describe: 测试页面，用于开发过程中测试使用，后期删掉
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class TestActivity extends BaseActivity<TestContract.View, TestContract.Presenter> implements TestContract.View {
    @BindView(R.id.btn_enter_main)
    Button btnEnterMain;
    @BindView(R.id.btn_dialog)
    Button btnDialog;
    /**
     * 保存IP
     */
    @BindView(R.id.btn_save_ip)
    Button btnSaveIp;
    /**
     * 获取ip
     */
    @BindView(R.id.btn_get_ip)
    Button btnGetIp;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected TestContract.Presenter initPresenter() {
        return new TestPresenter();
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(btnEnterMain).throttleFirst(Constant.BUTTON_THROTTLE_TIME, TimeUnit.SECONDS).subscribe(o -> enterMain());
        RxView.clicks(btnDialog).subscribe(o -> LoadingDialog.show(this));
//        RxView.clicks(btnSaveIp).subscribe(o -> getPresenter().saveIp("192.168.0.135"));
//        RxView.clicks(btnGetIp).subscribe(o -> getPresenter().getIp());
    }

    private void enterMain() {
        LoginActivity.startActivity(this);
    }

//    @Override
//    public void onSaveIpCallback(boolean isSuccess) {
//        ToastUtil.show("保存成功！");
//    }
//
//    @Override
//    public void onGetIpCallback(PrinterEntity printerEntity) {
//        ToastUtil.show(printerEntity.ip);
//    }
}
