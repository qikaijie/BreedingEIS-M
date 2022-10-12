package net.cnbec.catering.ui.main.activity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;

import net.cnbec.catering.R;
import net.cnbec.catering.app.GJApplication;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.fragment.MeFragment;
import net.cnbec.catering.ui.main.fragment.RecordFragment;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
import net.cnbec.catering.util.LogUtil;
import net.cnbec.catering.util.NfcUtils;
import net.cnbec.catering.util.StringUtil;
import net.cnbec.catering.util.ToastUtil;
import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.TaskCallback;
import net.posprinter.service.PosprinterService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View {

    /**
     * 0    未选择
     * 1    未连接
     * 2    已连接
     */
    public static int BLUETOOTH_CONNECT = 0;
    public static Map<String,String> bluetoothMap = new HashMap<>();
    public static IMyBinder myBinder;

    ServiceConnection mSerconnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder= (IMyBinder) service;
            Log.e("myBinder","connect");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("myBinder","disconnect");
        }
    };

    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;

    private String[] tabText = {"杂交记录","种质记录","我的"};
    private int[] normalIcon = {R.mipmap.icon_tab_record_n,R.mipmap.icon_tab_record_n,R.mipmap.icon_tab_me_n};
    private int[] selectIcon = {R.mipmap.icon_tab_record_s,R.mipmap.icon_tab_record_s,R.mipmap.icon_tab_me_s};

    private List<Fragment> fragments = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bind service，get imyBinder
        Intent intent =new Intent(this, PosprinterService.class);
        bindService(intent,mSerconnection,BIND_AUTO_CREATE);
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        fragments.add(new RecordFragment(0));
        fragments.add(new RecordFragment(1));
        fragments.add(new MeFragment());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .normalTextColor(Color.parseColor("#808080"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#4ea964"))   //Tab选中时字体颜色
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
//                .centerImageRes(R.mipmap.icon_tianbao)
//                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
//                .setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
//                    @Override
//                    public boolean onCenterTabSelectEvent(View view) {
//                        PJActivity.startActivity(MainActivity.this);
//                        return false;
//                    }
//                })
                .build();

        PackageManager packageManager = this.getPackageManager();
        boolean canNfc = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC);
        if(!canNfc){
            ToastUtil.show("是否支持nfc：" + canNfc);
        }

    }

    @Override
    protected boolean applyFullScreen() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();

        try {
            //nfc初始化设置
            NfcUtils nfcUtils = new NfcUtils(this);
        }catch (Exception e){}
    }
    @Override
    protected void onResume() {
        super.onResume();
        try{
            //开启前台调度系统
            NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
        }catch (Exception e){}
    }
    @Override
    protected void onPause() {
        super.onPause();
        try{
            //关闭前台调度系统
            NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
        }catch (Exception e){}
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String str = NfcUtils.readNFCFromTag(intent);
            LogUtil.i("近场通信读取到："+str);
            if(!StringUtil.isEmpty(str)){
                for(int i = 0; i < fragments.size(); i++) {
                    Fragment fragment = fragments.get(i);
                    if(fragment != null && fragment.isAdded() && fragment.isMenuVisible()) {
                        if(i == 0 || i == 1){
                            ((RecordFragment)fragment).onNFCDataEvent(str);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        unbindService(mSerconnection);
    }

    /**
     * 订阅者处理粘性事件
     * @param map 接手过来的实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMoonStickyEvent(Map<String,Object> map){

        String option = (String) map.get("option");
        if(option.equals("selectYear")){
            for(int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if(fragment != null && fragment.isAdded() && fragment.isMenuVisible()) {
                    // ToastUtil.show(i+"");
                    if(i == 0){
                        ((RecordFragment)fragment).onMoonStickyEvent(map);
                    }else if(i == 1){
                        ((RecordFragment)fragment).onMoonStickyEvent(map);
                    }
                }
            }
        }else if(option.equals("plantInfo")){
            ((RecordFragment)(fragments.get(0))).onPlantInfoEvent(map);
        }else if(option.equals("seedlingInfo")){
            ((RecordFragment)(fragments.get(1))).onSeedlingInfoEvent(map);
        }

    }

    /**
     * 连接蓝牙
     */
    public static void connectBT(String BtAdress){
        if (BtAdress.equals(null)||BtAdress.equals("")){
            ToastUtil.show(R.string.con_failed);
        }else {
            bluetoothMap.put("address",BtAdress);
            BLUETOOTH_CONNECT = 1;
            myBinder.ConnectBtPort(BtAdress, new TaskCallback() {
                @Override
                public void OnSucceed() {
                    BLUETOOTH_CONNECT=2;
                    ToastUtil.show(R.string.con_success);

                    Intent intent = new Intent(Constant.BLUETOOTH_CONNECT_REGISTER);
                    intent.putExtra("state",1);
                    LocalBroadcastManager.getInstance(GJApplication.getContext()).sendBroadcast(intent);
                }

                @Override
                public void OnFailed() {
                    BLUETOOTH_CONNECT=1;
                    ToastUtil.show(R.string.con_failed);

                    Intent intent = new Intent(Constant.BLUETOOTH_CONNECT_REGISTER);
                    intent.putExtra("state",-1);
                    LocalBroadcastManager.getInstance(GJApplication.getContext()).sendBroadcast(intent);
                }
            } );
        }
    }

    /**
     * 断开蓝牙连接
     */
    public static void disConnect(){
        if (BLUETOOTH_CONNECT == 2){
            myBinder.DisconnectCurrentPort(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    BLUETOOTH_CONNECT = 1;
                    ToastUtil.show("断开成功");

                    Intent intent = new Intent(Constant.BLUETOOTH_CONNECT_REGISTER);
                    intent.putExtra("state",2);
                    LocalBroadcastManager.getInstance(GJApplication.getContext()).sendBroadcast(intent);
                }

                @Override
                public void OnFailed() {
                    BLUETOOTH_CONNECT = 2;
                    ToastUtil.show("断开失败");

                    Intent intent = new Intent(Constant.BLUETOOTH_CONNECT_REGISTER);
                    intent.putExtra("state",-2);
                    LocalBroadcastManager.getInstance(GJApplication.getContext()).sendBroadcast(intent);
                }
            });
        }
    }
}
