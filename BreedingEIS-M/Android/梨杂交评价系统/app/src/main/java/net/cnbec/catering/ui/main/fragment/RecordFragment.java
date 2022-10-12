package net.cnbec.catering.ui.main.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.activity.CaptureActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.siberiadante.customdialoglib.EnsureDialog;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.GermplasmListByYearBean;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.bean.SpeciesListBean;
import net.cnbec.catering.bean.UserInfo;
import net.cnbec.catering.ui.base.BaseLazyFragment;
import net.cnbec.catering.ui.common.dialog.NFCDialog;
import net.cnbec.catering.ui.common.dialog.pop.SelectYearOfDataDialogForRightFragment;
import net.cnbec.catering.ui.main.activity.MainActivity;
import net.cnbec.catering.ui.main.activity.PJActivity;
import net.cnbec.catering.ui.main.activity.RecordDetailsActivity;
import net.cnbec.catering.ui.main.activity.ReleaseFilesActivity;
import net.cnbec.catering.ui.main.activity.SelectYearActivity;
import net.cnbec.catering.ui.main.activity.print.LinkPrintActivity;
import net.cnbec.catering.ui.main.activity.search.SearchActivity;
import net.cnbec.catering.ui.main.adapter.RecordAdapter;
import net.cnbec.catering.ui.main.contract.RecordContract;
import net.cnbec.catering.ui.main.presenter.RecordPresenter;
import net.cnbec.catering.util.StringUtil;
import net.cnbec.catering.util.ToastUtil;
import net.cnbec.catering.util.UserInfoUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Describe: //TODO
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/10/26
 */

public class RecordFragment extends BaseLazyFragment<RecordContract.View, RecordContract.Presenter> implements RecordContract.View {

    public int businessType;
    private int pageNum;
    private int pageSize;

    @BindView(R.id.titleTv01)
    TextView titleTv01;
    @BindView(R.id.titleTv02)
    TextView titleTv02;
    @BindView(R.id.titleTv03)
    TextView titleTv03;
    @BindView(R.id.titleTv04)
    TextView titleTv04;

    Integer delayDay;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchBtn)
    ImageView searchBtn;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private ObjectRecordAdapter objectRecordAdapter;
    private List<Object> dataList;

    SeedlingInfoBean seedlingInfoBean;
    //扫码或输入的查询结果
    PlantInfoBean plantInfoBean;

    Integer hybridId;
    Integer germplasmId;

    @BindView(R.id.scanResultEt)
    EditText scanResultEt;
    @BindView(R.id.cleanBtn)
    ImageView cleanBtn;

    @BindView(R.id.emptyTv)
    TextView emptyTv;

    @BindView(R.id.tianbaoBtn)
    LinearLayout tianbaoBtn;

    @BindView(R.id.shaixuanLL)
    LinearLayout shaixuanLL;
    @BindView(R.id.shaixuanIV)
    ImageView shaixuanIV;
    @BindView(R.id.shaixuanTV)
    TextView shaixuanTV;


    UserInfo userInfo = UserInfoUtil.getUserInfo();

    private EnsureDialog ensureDialog;

    /**
     * 当前选择
     */
    private Integer selectYear;
    private Object listByYearBean;

    public static RecordFragment newInstance(Integer businessType) {
        return new RecordFragment(businessType);
    }

    public RecordFragment(Integer businessType){
        this.businessType = businessType;
    }

    @Override
    protected RecordContract.Presenter initPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        pageNum = 1;
        pageSize = 10;

        title.setText(businessType == 0 ? "杂交评价系统" : "种质评价系统");

        dataList = new ArrayList<>();
        objectRecordAdapter = new ObjectRecordAdapter(R.layout.item_record_layout, dataList,this.businessType);
        recyclerView.setAdapter(objectRecordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        objectRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecordDetailsActivity.startActivity(getContext(),dataList.get(position),businessType);
            }
        });

        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                pageNum = 1;

                if(businessType == 0){
                    getPresenter().plantRecordList(delayDay,hybridId,(plantInfoBean!=null ? plantInfoBean.getId() : null),pageNum,pageSize);
                }else{
                    getPresenter().seedlingRecordList(delayDay,germplasmId,(seedlingInfoBean!=null ? seedlingInfoBean.getId() : null),pageNum,pageSize);
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                pageNum++;
                if(businessType == 0){
                    getPresenter().plantRecordList(delayDay,hybridId,(plantInfoBean!=null ? plantInfoBean.getId() : null),pageNum,pageSize);
                }else{
                    getPresenter().seedlingRecordList(delayDay,germplasmId,(seedlingInfoBean!=null ? seedlingInfoBean.getId() : null),pageNum,pageSize);
                }
            }
        });

        resetTitleTv(1);

        //为EditText设置监听，注意监听类型为TextWatcher
        scanResultEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //toast("您输入的数据为："+s.toString());
                if(!StringUtil.isEmpty(s.toString())){
                    cleanBtn.setVisibility(View.VISIBLE);
                }else{
                    cleanBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        refreshLayout.startRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();

        /**
         * 判断手持蓝牙打印机是否链接
         */
        back.setImageResource(MainActivity.BLUETOOTH_CONNECT == 2 ? R.mipmap.icon_printer_state_success : R.mipmap.icon_printer_state_fail);
    }

    @OnClick({R.id.scanBtn,R.id.searchBtn,R.id.titleTv01,R.id.titleTv02,R.id.titleTv03,R.id.titleTv04,R.id.cleanBtn,R.id.tianbaoBtn,R.id.tianbaoBtnView,R.id.shaixuanLL,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scanBtn:
            {
                //判断是否开户相册权限
                if (PackageManager.PERMISSION_GRANTED ==   ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)) {
                    if (PackageManager.PERMISSION_GRANTED ==   ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Intent intent = new Intent(getContext(), CaptureActivity.class);
                        startActivityForResult(intent, 1024);
//                        Intent intent1 = new Intent(getContext(), ZBarScannerActivity.class);
//                        startActivityForResult(intent1, 1025);
                    }else{
                        //提示用户开户权限
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(getActivity(),perms, 1022);
                    }
                }else{
                    //提示用户开户权限
                    String[] perms = {"android.permission.CAMERA"};
                    ActivityCompat.requestPermissions(getActivity(),perms, 1023);
                }
            }
                break;
            case R.id.searchBtn:
                {
                    SearchActivity.startActivity(getContext(),businessType);
                }
                break;
            case R.id.titleTv01:
            {
                resetTitleTv(1);
            }
            break;
            case R.id.titleTv02:
            {
                resetTitleTv(2);
            }
            break;
            case R.id.titleTv03:
            {
                resetTitleTv(3);
            }
            break;
            case R.id.titleTv04:
            {
                resetTitleTv(4);
            }
            break;
            case R.id.cleanBtn:
            {
                scanResultEt.setText("");
                plantInfoBean = null;

                hybridId = null;
                germplasmId = null;

                selectYear = 0;
                listByYearBean = null;
                shaixuanIV.setImageResource(R.mipmap.icon_shuaixuan_n);
                shaixuanTV.setTextColor(getResources().getColor(R.color.gray));

                refreshLayout.startRefresh();
            }
                break;
            case R.id.tianbaoBtnView:
            case R.id.tianbaoBtn:
            {
                //填报
                if(userInfo.getUser().getSpeciesId() != null){
                    // 查询详情
                    getPresenter().speciesDetails(Integer.valueOf(userInfo.getUser().getSpeciesId()));
                }else{
                    // 选择种类
                    getPresenter().speciesList();
                }
            }
                break;
            case R.id.shaixuanLL:
            {
                SelectYearActivity.startActivity(getContext(), getActivity(), this.businessType, selectYear, businessType == 0 ? (HybridListByYearBean)listByYearBean : (GermplasmListByYearBean)listByYearBean);
            }
                break;
            case R.id.back:
            {
                /**
                 * 链接蓝牙手持打印机
                 */
                checkBluetoothAndLocationPermission();
            }
                break;
        }
    }

    @Override
    public void onSpeciesListCallBack(List<SpeciesListBean> objects) {
        if(objects.size()>0){
            List<String> speciesList = new ArrayList<>();
            for (int i = 0; i < objects.size(); i++) {
                speciesList.add(objects.get(i).getName()+"");
            }
            /**
             * 选择属性
             */
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    //返回的分别是三个级别的选中位置
                    SpeciesListBean speciesListBean = objects.get(options1);
                    PJActivity.startActivity(getContext(),new SpeciesInfoBean(speciesListBean.getId(),speciesListBean.getName(),speciesListBean.getSort()),businessType);
                }
            }).build();
            pvOptions.setTitleText("选择植物");
            pvOptions.setSelectOptions(0);
            pvOptions.setPicker(speciesList);
            pvOptions.show();
        }else{
            ToastUtil.show("未查询到植物信息");
        }
    }

    @Override
    public void onSpeciesDetailsCallBack(SpeciesInfoBean speciesInfoBean) {
        PJActivity.startActivity(getContext(),speciesInfoBean,this.businessType);
    }

    /**
     * @param map 接手过来的实体类
     */
    public void onMoonStickyEvent(Map<String,Object> map){
        selectYear = (Integer)map.get("year");
        if(businessType == 0){
            listByYearBean = (HybridListByYearBean)map.get("data");
        }else{
            listByYearBean = (GermplasmListByYearBean)map.get("data");
        }

        shaixuanIV.setImageResource(R.mipmap.icon_shuaixuan_s);
        shaixuanTV.setTextColor(getResources().getColor(R.color.forestgreen));

        if(businessType == 0){
            plantInfoBean = null;
            hybridId = ((HybridListByYearBean)listByYearBean).getId();
            scanResultEt.setText(((HybridListByYearBean)listByYearBean).getName());
        }else{
            seedlingInfoBean = null;
            germplasmId = ((GermplasmListByYearBean)listByYearBean).getId();
            scanResultEt.setText(((GermplasmListByYearBean)listByYearBean).getName());
        }
        refreshLayout.startRefresh();
    }

    /**
     * @param nfcStr 获取到的NFC数据
     */
    public void onNFCDataEvent(String nfcStr){
        //取出名称
        String[] nfcCodeStrs = nfcStr.split("\n");
        Pattern p = Pattern.compile("\\s*|\t|\r|\n" );
        Matcher m =  p.matcher(nfcCodeStrs[0]);
        String nfcCodeStr = m.replaceAll("");
        nfcCodeStr = nfcCodeStr.replace("名称:","");
        nfcCodeStr = nfcCodeStr.replace("\u0002","");
        String tempNfcCodeStr = nfcCodeStr.substring(0,2);
        if(tempNfcCodeStr.equals("en")){
            nfcCodeStr = nfcCodeStr.substring(2);
        }
        if(tempNfcCodeStr.equals("zh")){
            nfcCodeStr = nfcCodeStr.substring(2);
        }
        String finalNfcCodeStr = nfcCodeStr;
        ensureDialog = new EnsureDialog(getContext()).builder()
                .setGravity(Gravity.CENTER)//默认居中，可以不设置
                .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
                .setCancelable(false)
                .setSubTitle("扫描到芯片:"+finalNfcCodeStr+"，是否读取？")
                .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setPositiveButton("读取", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(businessType == 0){
                            getPresenter().plantInfo(finalNfcCodeStr);
                        }else{
                            getPresenter().seedlingInfo(finalNfcCodeStr);
                        }
                        ensureDialog.dismiss();
                    }
                });
        ensureDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 1024:
                {
                    boolean grantedLocation = true;
                    for(int i : grantResults){
                        if(i != PackageManager.PERMISSION_GRANTED){
                            grantedLocation = false;
                        }
                    }
                    if(!grantedLocation){
                        ToastUtil.show("蓝牙授权失败!!");
                    }else{
                        LinkPrintActivity.startActivity(getContext());
                    }
                }
                break;
            case 1023:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, 1024);
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    ToastUtil.show("请开启应用拍照权限");
                }
                break;
            case 1022:
                boolean albumAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if(albumAccepted){
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, 1024);
                }else{
                    //用户授权拒绝之后，友情提示一下就可以了
                    ToastUtil.show("请开启应用相册权限");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1024) {
                String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                //Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);
                if (!StringUtil.isEmpty(result)) {
                    if(businessType == 0){
                        getPresenter().plantInfo(result);
                    }else{
                        getPresenter().seedlingInfo(result);
                    }
//                    ToastUtil.show(result);
                } else {
                    ToastUtil.show("扫码解析失败");
                }
            }else if(requestCode == 1025){
                // Scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT)
                // Type of the scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE)
                ToastUtil.show("Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT));
                // ToastUtil.show("Scan Result Type = " + data.getIntExtra(ZBarConstants.SCAN_RESULT_TYPE, 0));
                // The value of type indicates one of the symbols listed in Advanced Options below.
            } else if(resultCode == Activity.RESULT_CANCELED) {
                //ToastUtil.show("Camera unavailable");
            }
        }
    }


    public void  onPlantInfoEvent(Map<String,Object> map){
        PlantInfoBean plantInfoB = new Gson().fromJson((String)map.get("plantInfoBean"),new TypeToken<PlantInfoBean>(){}.getType());
        if(plantInfoB != null){
            this.plantInfoBean = plantInfoB;
            scanResultEt.setText(plantInfoBean.getCode()+" "+plantInfoBean.getHybridName());

            //刷新
            refreshLayout.startRefresh();
        }else{
            this.plantInfoBean = null;
            ToastUtil.show("编码信息不存在");
        }
    }

    /**
     * 扫码查询商品结果
     * @param plantInfoB
     */
    @Override
    public void onPlantInfoCallBack(PlantInfoBean plantInfoB) {
        if(plantInfoB != null){
            this.plantInfoBean = plantInfoB;

            hybridId = null;
            germplasmId = null;
            selectYear = 0;
            listByYearBean = null;
            shaixuanIV.setImageResource(R.mipmap.icon_shuaixuan_n);
            shaixuanTV.setTextColor(getResources().getColor(R.color.gray));

            scanResultEt.setText(plantInfoBean.getCode()+" "+plantInfoBean.getHybridName());

            //刷新
            refreshLayout.startRefresh();
        }else{
            this.plantInfoBean = null;
            ToastUtil.show("编码信息不存在");
        }
    }

    public void  onSeedlingInfoEvent(Map<String,Object> map){
        SeedlingInfoBean seedlingInfoB = new Gson().fromJson((String)map.get("seedlingInfoBean"),new TypeToken<SeedlingInfoBean>(){}.getType());
        if(seedlingInfoB != null){
            this.seedlingInfoBean = seedlingInfoB;
            scanResultEt.setText(seedlingInfoBean.getCode()+" "+seedlingInfoBean.getGermplasmName());

            //刷新
            refreshLayout.startRefresh();
        }else{
            this.seedlingInfoBean = null;
            ToastUtil.show("编码信息不存在");
        }
    }

    @Override
    public void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoB) {
        if(seedlingInfoB != null){
            this.seedlingInfoBean = seedlingInfoB;

            hybridId = null;
            germplasmId = null;
            selectYear = 0;
            listByYearBean = null;
            shaixuanIV.setImageResource(R.mipmap.icon_shuaixuan_n);
            shaixuanTV.setTextColor(getResources().getColor(R.color.gray));

            scanResultEt.setText(seedlingInfoBean.getCode()+" "+seedlingInfoBean.getGermplasmName());

            //刷新
            refreshLayout.startRefresh();
        }else{
            this.seedlingInfoBean = null;
            ToastUtil.show("编码信息不存在");
        }
    }

    public void resetTitleTv(int indexDay){
        titleTv01.setBackgroundResource(R.drawable.stroke_background_11);
        titleTv02.setBackgroundResource(R.drawable.stroke_background_11);
        titleTv03.setBackgroundResource(R.drawable.stroke_background_11);
        titleTv04.setBackgroundResource(R.drawable.stroke_background_11);
        if(indexDay == 1){
            delayDay = null;
            titleTv01.setBackgroundResource(R.drawable.stroke_background_08);
        }else if(indexDay == 2){
            delayDay = 1;
            titleTv02.setBackgroundResource(R.drawable.stroke_background_08);
        }else if(indexDay == 3){
            delayDay = 3;
            titleTv03.setBackgroundResource(R.drawable.stroke_background_08);
        }else if(indexDay == 4){
            delayDay = 30;
            titleTv04.setBackgroundResource(R.drawable.stroke_background_08);
        }
        refreshLayout.startRefresh();
    }

    @Override
    public void onPlantRecordListCallBack(List<PlantRecordBean> plantRecordBeans) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        //记录详情
        if(pageNum == 1){
            dataList.clear();
        }
        if(plantRecordBeans!=null && plantRecordBeans.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataList.addAll(plantRecordBeans);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        refreshLayout.setEnableLoadmore(!(plantRecordBeans.size()<pageSize));

        objectRecordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSeedlingRecordListCallBack(List<SeedlingRecordBean> seedlingRecordBeans) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        //记录详情
        if(pageNum == 1){
            dataList.clear();
        }
        if(seedlingRecordBeans!=null && seedlingRecordBeans.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataList.addAll(seedlingRecordBeans);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        refreshLayout.setEnableLoadmore(!(seedlingRecordBeans.size()<pageSize));

        objectRecordAdapter.notifyDataSetChanged();
    }

    class ObjectRecordAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        private Integer businessType;

        public ObjectRecordAdapter(int layoutResId, @Nullable List<Object> data,Integer businessType) {
            super(layoutResId, data);
            this.businessType = businessType;
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

            if(businessType == 0){
                PlantRecordBean plantRecordBean = (PlantRecordBean)item;
                ((TextView)helper.getView(R.id.plantIdTv)).setText(plantRecordBean.getPlantCode());
                ((TextView)helper.getView(R.id.nameTv)).setText(plantRecordBean.getHybridName()+"");
                ((TextView)helper.getView(R.id.timeTv)).setText(plantRecordBean.getCreateTime()+"");
            }else{
                SeedlingRecordBean seedlingRecordBean = (SeedlingRecordBean)item;
                ((TextView)helper.getView(R.id.plantIdTv)).setText(seedlingRecordBean.getSeedlingCode());
                ((TextView)helper.getView(R.id.nameTv)).setText(seedlingRecordBean.getGermplasmName()+"");
                ((TextView)helper.getView(R.id.timeTv)).setText(seedlingRecordBean.getCreateTime()+"");
            }

        }

    }

    /**
     * 动态查询蓝牙权限
     */
    private void checkBluetoothAndLocationPermission(){
        //判断是否有访问位置的权限，没有权限，直接申请位置权限
        if((ContextCompat.checkSelfPermission(getContext(),Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN},1024);
        }else{
            LinkPrintActivity.startActivity(getContext());
        }
    }
}
