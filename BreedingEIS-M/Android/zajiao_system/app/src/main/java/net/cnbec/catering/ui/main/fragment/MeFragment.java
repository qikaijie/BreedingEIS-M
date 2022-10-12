package net.cnbec.catering.ui.main.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.content.FileProvider;

//import com.lwy.smartupdate.UpdateManager;
//import com.lwy.smartupdate.api.IUpdateCallback;
//import com.lwy.smartupdate.data.VersionBean;
import net.cnbec.catering.bean.VersionBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.siberiadante.customdialoglib.EnsureDialog;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.ui.base.BaseLazyFragment;
import net.cnbec.catering.ui.dialog.UpdateDialog;
import net.cnbec.catering.ui.login.activity.LoginActivity;
import net.cnbec.catering.ui.main.activity.NewSCActivity;
import net.cnbec.catering.ui.main.activity.feedback.FeedbackActivity;
import net.cnbec.catering.ui.main.contract.MeContract;
import net.cnbec.catering.ui.main.presenter.MePresenter;
import net.cnbec.catering.util.ToastUtil;
import net.cnbec.catering.util.UserInfoUtil;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Describe: //TODO
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/10/26
 */

public class MeFragment extends BaseLazyFragment<MeContract.View, MeContract.Presenter> implements MeContract.View {

    /**
     * -1 再次进入该页面不再显示弹框
     * 0 首页进入页面需要弹出更新
     * 1 点击检测更新，所以必弹框
     */
    int needShowDialogUpdata;


    @BindView(R.id.headIv)
    RoundedImageView headIv;
    @BindView(R.id.realNameTv)
    TextView realNameTv;
    @BindView(R.id.jobTv)
    TextView jobTv;
    @BindView(R.id.phoneLabel)
    TextView phoneLabel;
    @BindView(R.id.layout_scddz)
    LinearLayout layoutScddz;
    @BindView(R.id.layout_xgmm)
    LinearLayout layoutXgmm;
    @BindView(R.id.layout_xtbb)
    LinearLayout layoutXtbb;
    @BindView(R.id.layout_yjfk)
    LinearLayout layoutYjfk;
    @BindView(R.id.icon_new_version)
    ImageView iconNewVersion;

    @BindView(R.id.versionTV)
    TextView versionTV;

    private EnsureDialog ensureDialog;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected MeContract.Presenter initPresenter() {
        return new MePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        needShowDialogUpdata = 0;
        versionTV.setText("V"+getVersion());
    }


    @Override
    public void onResume() {
        super.onResume();

        getPresenter().userInfo();
        getPresenter().downloadAndroid();
    }


    @Override
    public void onUserInfoCallBack(UserDetailsBean userDetailsBean) {
        //ToastUtil.show("会员详情");
        realNameTv.setText(""+userDetailsBean.getNickName());
        jobTv.setText("未知工作项目组");
        phoneLabel.setText("未知联系方式");
    }

    @OnClick({R.id.logout_button,R.id.layout_scddz,R.id.layout_yjfk,R.id.layout_xtbb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logout_button: {
                getPresenter().logout();
            }
            break;
            case R.id.layout_scddz:
            {
                NewSCActivity.startActivity(getContext());
            }
            break;
            case R.id.layout_yjfk:
            {
                FeedbackActivity.startActivity(getContext(),realNameTv.getText()+"");
            }
            break;
            case R.id.layout_xtbb:
            {
                //如果有新版本下载新版本
                needShowDialogUpdata = 1;
                getPresenter().downloadAndroid();
            }
            break;
        }
    }

    @Override
    public void onLogoutCallBack(Object object) {
        UserInfoUtil.clearUserInfo();

        LoginActivity.startActivity(getContext());

        getActivity().finish();
    }

    @Override
    public void onDownloadAndroidCallBack(VersionBean versionBean) {
        if(compareVersion(versionBean.getVersion(),getVersion()) >= 1){
            iconNewVersion.setVisibility(View.VISIBLE);
            if(needShowDialogUpdata != -1){
                needShowDialogUpdata = -1;
                UpdateDialog updateDialog = UpdateDialog.createDialog(getActivity());
                updateDialog.setCallBack(new UpdateDialog.CallBack() {

                    @Override
                    public void onIgnored(View view) {
                    }

                    @Override
                    public void onOK(View view) {
                        final Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(versionBean.getDownUrl()));
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            final ComponentName componentName = intent.resolveActivity(getActivity().getPackageManager()); // 打印Log   ComponentName到底是什么 L.d("componentName = " + componentName.getClassName());
                            getActivity().startActivity(Intent.createChooser(intent, "请选择浏览器"));
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
                        }
                        updateDialog.dismiss();
                    }

                    @Override
                    public void onClosed(View view) {
//                        if (mUpdateDialogTarget.get().isUpdating()) {
//                            onBackgroundTrigger();
//                        }
                    }
                });

                updateDialog.setUpdateTitle(String.format(Locale.CHINA, "是否升级到v%s版本?", versionBean.getVersion()));
                updateDialog.setText("当前最新版本是V" + versionBean.getVersion() + "，请更新体验！");
                updateDialog.show();

//                UpdateManager.getInstance().update(getActivity(), versionBean, "当前最新版本是V" + versionBean.getVersion() + "，请更新体验！", false, new IUpdateCallback() {
//                    @Override
//                    public void beforeUpdate() {
//
//                    }
//
//                    @Override
//                    public void onProgress(int percent, long totalLength, int patchIndex, int patchCount) {
//
//                    }
//
//                    @Override
//                    public void onCompleted(String apkPath) {
//                        /**
//                         * 下载完成
//                         */
//                        startInstall(apkPath);
//                    }
//
//                    @Override
//                    public void onError(String error) {
//
//                    }
//
//                    @Override
//                    public void onCancelUpdate() {
//
//                    }
//
//                    @Override
//                    public void onBackgroundTrigger() {
//
//                    }
//                });
            }
        }else{
            iconNewVersion.setVisibility(View.GONE);
        }
    }

    private void startInstall(String filePath){
        //分别进行7.0以上和7.0以下的尝试
        File apkfile = new File(filePath);
        if (!apkfile.exists()) {
            ToastUtil.show("更新包不存在！");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0以上
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getContext(),getContext().getApplicationContext().getPackageName() + ".fileProvider",apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    /**
     * 版本号比较
     * compareTo()方法返回值为int类型，就是比较两个值，如：x.compareTo(y)。如果前者大于后者，返回1，前者等于后者则返回0，前者小于后者则返回-1
     * @param s1
     * @param s2
     * @return 范围可以是"2.20.", "2.10.0"  ,".20","2.10.0",2.1.3 ，3.7.5，10.2.0
     */
    public int compareVersion(String s1, String s2) {
        String[] s1Split = s1.split("\\.", -1);
        String[] s2Split = s2.split("\\.", -1);
        int len1 = s1Split.length;
        int len2 = s2Split.length;
        int lim = Math.min(len1, len2);
        int i = 0;
        while (i < lim) {
            int c1 = "".equals(s1Split[i]) ? 0 : Integer.parseInt(s1Split[i]);
            int c2 = "".equals(s2Split[i]) ? 0 : Integer.parseInt(s2Split[i]);
            if (c1 != c2) {
                return c1 - c2;
            }
            i++;
        }
        return len1 - len2;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0.0";
        }
    }

}
