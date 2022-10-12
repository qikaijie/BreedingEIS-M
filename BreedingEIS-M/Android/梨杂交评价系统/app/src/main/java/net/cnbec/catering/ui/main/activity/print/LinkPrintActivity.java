package net.cnbec.catering.ui.main.activity.print;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.test.contract.TestContract;
import net.cnbec.catering.test.presenter.TestPresenter;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.base.print.DeviceReceiver;
import net.cnbec.catering.ui.common.dialog.LoadingDialog;
import net.cnbec.catering.ui.login.activity.LoginActivity;
import net.cnbec.catering.ui.main.activity.MainActivity;
import net.cnbec.catering.ui.main.activity.PJFromActivityCopy;
import net.cnbec.catering.ui.main.activity.PJFromActivitySeedling;
import net.cnbec.catering.util.CloneObjectUtils;
import net.cnbec.catering.util.SPUtils;
import net.cnbec.catering.util.ToastUtil;
import net.cnbec.catering.util.XPPrintUtils;
import net.posprinter.utils.BitmapProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Describe: 测试页面，用于开发过程中测试使用，后期删掉
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class LinkPrintActivity extends BaseActivity<TestContract.View, TestContract.Presenter> implements TestContract.View {

    private ProgressDialog progressDialog;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.info_text)
    TextView infoText;
    @BindView(R.id.option_button)
    Button optionButton;

    @BindView(R.id.bt_tsp)
    Button bt_tsp;
    @BindView(R.id.bt_text)
    Button bt_text;
    @BindView(R.id.bt_barcode)
    Button bt_barcode;
    @BindView(R.id.bt_qr)
    Button bt_qr;
    @BindView(R.id.bt_bitmap)
    Button bt_bitmap;

    UpdateRwBroadcastReceiver updateRwBroadcastReceiver = new UpdateRwBroadcastReceiver();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LinkPrintActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_link_print;
    }

    @Override
    protected TestContract.Presenter initPresenter() {
        return new TestPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        title.setText("连接便携标签机");

        infoText.setText(MainActivity.BLUETOOTH_CONNECT == 0 ? "未选择" : MainActivity.bluetoothMap.get("address"));
        if(MainActivity.BLUETOOTH_CONNECT == 0){
            /**
             * 未选择蓝牙设备
             */
            infoText.setText("未选择");
            optionButton.setText("选择");
        }else{
            String ipAddress = MainActivity.bluetoothMap.get("address");
            infoText.setText(ipAddress);
            optionButton.setText(MainActivity.BLUETOOTH_CONNECT == 1 ? "连接" : "断开");
        }

        registerUpdateBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterUpdateBroadcast();
    }

    /**
     * 注册广播
     */
    private void registerUpdateBroadcast() {
        IntentFilter filter = new IntentFilter(Constant.BLUETOOTH_CONNECT_REGISTER);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateRwBroadcastReceiver, filter);
    }
    /**
     * 取消广播
     */
    private void unRegisterUpdateBroadcast() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateRwBroadcastReceiver);
    }

    private class UpdateRwBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            cancelProgressDialog();
            /**
             * 1    连接成功
             * -1   连接失败
             * 2    断开成功
             * -2   断开失败
             */
            int state = intent.getIntExtra("state",0);
            switch (state){
                case 1:
                {
                    optionButton.setText("断开");
                }
                break;
                case -1:
                {
                    optionButton.setText("连接");
                }
                break;
                case 2:
                {
                    optionButton.setText("连接");
                }
                break;
                case -2:
                {
                    optionButton.setText("断开");
                }
                break;
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @OnClick({R.id.back,R.id.option_button,R.id.bt_tsp,R.id.bt_text,R.id.bt_barcode,R.id.bt_qr,R.id.bt_bitmap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
            case R.id.option_button:
            {
                if(MainActivity.BLUETOOTH_CONNECT == 0){
                    /**
                     * 选择蓝牙设备
                     */
                    setBluetooth();
                }else if(MainActivity.BLUETOOTH_CONNECT == 1){
                    /**
                     * 连接蓝牙设备
                     */
                    buildProgressDialog("设备连接中...");
                    MainActivity.connectBT(MainActivity.bluetoothMap.get("address"));
                }else if(MainActivity.BLUETOOTH_CONNECT == 2){
                    /**
                     * 断开蓝牙设备
                     */
                    buildProgressDialog("设备断开中...");
                    MainActivity.disConnect();
                }
            }
            break;
            case R.id.bt_tsp:
                XPPrintUtils.printContent(this,"Z9-BM1W-1501-16@1602-434","Z9-BM1W-1501-16@1602-434");
                break;
            case R.id.bt_text:
                XPPrintUtils.printText(this,"Z9-BM1W-1501-16@1602-434");
                break;
            case   R.id.bt_barcode:
                XPPrintUtils.printBarcode(this,"Z9-BM1W-1501-16@1602-434");
                break;
            case R.id.bt_qr:
                XPPrintUtils.printQR(this,"Z9-BM1W-1501-16@1602-434");
                break;
            case R.id.bt_bitmap:
                XPPrintUtils.printbitmap(this,BitmapProcess.compressBmpByYourWidth
                        (BitmapFactory.decodeResource(this.getResources(), R.mipmap.lzj_logo),150));
                break;
        }
    }


    private List<String> btList = new ArrayList<>();
    private ArrayList<String> btFoundList = new ArrayList<>();
    private ArrayAdapter<String> BtBoudAdapter ,BtfoundAdapter;
    private View BtDialogView;
    private ListView BtBoundLv,BtFoundLv;
    private LinearLayout ll_BtFound;
    private AlertDialog btdialog;
    private Button btScan;
    private DeviceReceiver BtReciever;
    private BluetoothAdapter bluetoothAdapter;

    /*
    选择蓝牙设备
     */

    public void setBluetooth(){
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        //判断时候打开蓝牙设备
        if (!bluetoothAdapter.isEnabled()){
            //请求用户开启
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }else {

            showblueboothlist();

        }
    }

    private void showblueboothlist() {
        if (!bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.startDiscovery();
        }
        LayoutInflater inflater=LayoutInflater.from(this);
        BtDialogView=inflater.inflate(R.layout.printer_list, null);
        BtBoudAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, btList);
        BtBoundLv= BtDialogView.findViewById(R.id.listView1);
        btScan= BtDialogView.findViewById(R.id.btn_scan);
        ll_BtFound= BtDialogView.findViewById(R.id.ll1);
        BtFoundLv=(ListView) BtDialogView.findViewById(R.id.listView2);
        BtfoundAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, btFoundList);
        BtBoundLv.setAdapter(BtBoudAdapter);
        BtFoundLv.setAdapter(BtfoundAdapter);
        btdialog=new AlertDialog.Builder(this).setTitle("BLE").setView(BtDialogView).create();
        btdialog.show();

        BtReciever=new DeviceReceiver(btFoundList,BtfoundAdapter,BtFoundLv);

        //注册蓝牙广播接收者
        IntentFilter filterStart=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter filterEnd=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(BtReciever, filterStart);
        registerReceiver(BtReciever, filterEnd);

        setDlistener();
        findAvalibleDevice();
    }


    private void setDlistener() {
        // TODO Auto-generated method stub
        btScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ll_BtFound.setVisibility(View.VISIBLE);
                //btn_scan.setVisibility(View.GONE);
            }
        });
        //已配对的设备的点击连接
        BtBoundLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
                        bluetoothAdapter.cancelDiscovery();

                    }

                    String mac=btList.get(arg2);
                    mac=mac.substring(mac.length()-17);
//                    String name=msg.substring(0, msg.length()-18);
                    //lv1.setSelection(arg2);
                    btdialog.cancel();
                    MainActivity.bluetoothMap.put("address",mac);
                    MainActivity.BLUETOOTH_CONNECT = 1;
                    infoText.setText(mac);
                    optionButton.setText("连接");
                    //Log.i("TAG", "mac="+mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        //未配对的设备，点击，配对，再连接
        BtFoundLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
                        bluetoothAdapter.cancelDiscovery();

                    }
                    String mac;
                    String msg=btFoundList.get(arg2);
                    mac=msg.substring(msg.length()-17);
                    String name=msg.substring(0, msg.length()-18);
                    //lv2.setSelection(arg2);
                    btdialog.cancel();
                    MainActivity.bluetoothMap.put("address",mac);
                    MainActivity.BLUETOOTH_CONNECT = 1;
                    infoText.setText(mac);
                    optionButton.setText("连接");
                    Log.i("TAG", "mac="+mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    找可连接的蓝牙设备
     */
    private void findAvalibleDevice() {
        // TODO Auto-generated method stub
        //获取可配对蓝牙设备
        Set<BluetoothDevice> device=bluetoothAdapter.getBondedDevices();

        btList.clear();
        if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
            BtBoudAdapter.notifyDataSetChanged();
        }
        if(device.size()>0){
            //存在已经配对过的蓝牙设备
            for(Iterator<BluetoothDevice> it = device.iterator(); it.hasNext();){
                BluetoothDevice btd=it.next();
                btList.add(btd.getName()+'\n'+btd.getAddress());
                BtBoudAdapter.notifyDataSetChanged();
            }
        }else{  //不存在已经配对过的蓝牙设备
            btList.add("不存在已经配对过的蓝牙设备");
            BtBoudAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 加载框
     */
    public void buildProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LinkPrintActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    /**
     * 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }

}
