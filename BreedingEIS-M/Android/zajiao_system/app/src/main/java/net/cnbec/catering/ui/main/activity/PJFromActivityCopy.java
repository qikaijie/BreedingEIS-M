package net.cnbec.catering.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.AttributeBean;
import net.cnbec.catering.bean.AttributeValuesBean;
import net.cnbec.catering.bean.CollectAddBean;
import net.cnbec.catering.bean.CollectDelBean;
import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.bean.PlantHistoryRecordBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordAddBean;
import net.cnbec.catering.bean.PlantRecordHistoryInfoBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordAddBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.bean.UploadImageBean;
import net.cnbec.catering.network.requestBean.PlantRecordAddRequestBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.activity.history.PJHistoryRecordActivity;
import net.cnbec.catering.ui.main.contract.PJFromContract;
import net.cnbec.catering.ui.main.fragment.HistoryFragment;
import net.cnbec.catering.ui.main.fragment.RecordFragment;
import net.cnbec.catering.ui.main.presenter.PJFromPresenter;
import net.cnbec.catering.util.CloneObjectUtils;
import net.cnbec.catering.util.LogUtil;
import net.cnbec.catering.util.NfcUtils;
import net.cnbec.catering.util.SPUtils;
import net.cnbec.catering.util.StringUtil;
import net.cnbec.catering.util.TimeUtil;
import net.cnbec.catering.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.google.zxing.activity.CaptureActivity;
import com.siberiadante.customdialoglib.EnsureDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PJFromActivityCopy extends BaseActivity<PJFromContract.View, PJFromContract.Presenter> implements PJFromContract.View {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.btn_sc)
    ImageView btn_sc;

    @BindView(R.id.recycle_view)
    RecyclerView mainRecyclerView;

    String filesName;
    List<UploadImageBean> uploadedDataList;
    @BindView(R.id.photoNumTv)
    TextView photoNumTv;

    @BindView(R.id.scanResultTv)
    TextView scanResultTv;

    @BindView(R.id.previousBtn)
    Button previousBtn;
    @BindView(R.id.nextBtn)
    Button nextBtn;

    @BindView(R.id.btn_sumbit)
    Button btnSumbit;

    String qrCodeStr;
    PlantInfoBean plantInfoBean;

    EnsureDialog ensureDialog;
    Boolean isUploadResult;//??????????????????
    Boolean isChangeUpdata;//??????????????????????????????????????????


    SpeciesInfoBean speciesInfoBean;
    public Integer businessType;

    /**
     * ???????????????????????????????????????
     */
    List<GroupListBean> groupListBL;
    List<AttributeBean> attributeBL;
    private PJMainAdapter pjMainAdapter;

    public static void startActivity(Context context, List<GroupListBean> groupListBeans, SpeciesInfoBean speciesInfoBean, Integer businessType) {
        Intent intent = new Intent(context, PJFromActivityCopy.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//????????????
        intent.putExtra("GroupListBeans",new Gson().toJson(groupListBeans));
        intent.putExtra("speciesInfoBeanStr",new Gson().toJson(speciesInfoBean));
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pj_from_copy;
    }


    @Override
    protected PJFromContract.Presenter initPresenter() {
        return new PJFromPresenter();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        this.businessType = getIntent().getIntExtra("businessType",0);
        this.speciesInfoBean = new Gson().fromJson(getIntent().getStringExtra("speciesInfoBeanStr"),new TypeToken<SpeciesInfoBean>(){}.getType());
        title.setText("????????????-" + speciesInfoBean.getName());
        this.groupListBL = new Gson().fromJson(getIntent().getStringExtra("GroupListBeans"),new TypeToken<List<GroupListBean>>(){}.getType());

        uploadedDataList = new ArrayList<>();
        filesName = "";
        qrCodeStr = "";
        isUploadResult = false;
        isChangeUpdata = false;//???????????????
        btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));

        attributeBL = new ArrayList<>();
        for (int i = 0;i<this.groupListBL.size();i++){
            attributeBL.add(new AttributeBean(this.groupListBL.get(i).getName()));
            List<GroupListBean.AttributeListBean> attributeListBeanList = this.groupListBL.get(i).getAttributeList();
            for (int j = 0;j<attributeListBeanList.size();j++){
                attributeBL.add(new AttributeBean(this.groupListBL.get(i).getName(),attributeListBeanList.get(j)));
            }
        }

        pjMainAdapter = new PJMainAdapter(R.layout.item_pj_from_item_layout,this.attributeBL);
        mainRecyclerView.setAdapter(pjMainAdapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void initData() {
        super.initData();
        try{
            //nfc???????????????
            NfcUtils nfcUtils = new NfcUtils(this);
        }catch (Exception e){}
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            //????????????????????????
            NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
        }catch (Exception e){}
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //??????Activity?????????NFC???????????????????????????
        //???????????????????????????NFC??????
        try {
            String nfcStr = NfcUtils.readNFCFromTag(intent);
            String[] nfcCodeStrs = nfcStr.split("\n");
            Pattern p = Pattern.compile("\\s*|\t|\r|\n" );
            Matcher m =  p.matcher(nfcCodeStrs[0]);
            String nfcCodeStr = m.replaceAll("");
            nfcCodeStr = nfcCodeStr.replace("??????:","");
            nfcCodeStr = nfcCodeStr.replace("\u0002","");
            String tempNfcCodeStr = nfcCodeStr.substring(0,2);
            if(tempNfcCodeStr.equals("en")){
                nfcCodeStr = nfcCodeStr.substring(2);
            }
            if(tempNfcCodeStr.equals("zh")){
                nfcCodeStr = nfcCodeStr.substring(2);
            }
            String finalNfcCodeStr = nfcCodeStr;
            ensureDialog = new EnsureDialog(PJFromActivityCopy.this).builder()
                    .setGravity(Gravity.CENTER)//??????????????????????????????
                    .setTitle("??????", getResources().getColor(R.color.black))//????????????????????????????????????????????????
                    .setCancelable(false)
                    .setSubTitle("???????????????:"+finalNfcCodeStr+"??????????????????")
                    .setNegativeButton("??????", new View.OnClickListener() {//??????????????????????????????????????????????????????
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setPositiveButton("??????", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try{
            //????????????????????????
            NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
        }catch (Exception e){}

        String byGoOutImages = (String) SPUtils.get(filesName,"");
        uploadedDataList.clear();
        if(!StringUtil.isEmpty(byGoOutImages)){
            uploadedDataList = new Gson().fromJson(byGoOutImages,new TypeToken<List<UploadImageBean>>(){}.getType());
            if(uploadedDataList.size()!=0){
                photoNumTv.setVisibility(View.VISIBLE);
                photoNumTv.setText(uploadedDataList.size()+"");
            }else{
                photoNumTv.setVisibility(View.GONE);
            }
        }else{
            photoNumTv.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.back,R.id.btn_sumbit,R.id.btn_make_photo,R.id.btn_scan,R.id.btn_sc,R.id.previousBtn,R.id.nextBtn,R.id.historyRecordBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
                break;
            case R.id.btn_sumbit:
            {
                //??????
                sumbitData();
                //PJDetailsActivity.startActivity(this);
            }
                break;
            case R.id.historyRecordBtn:
            {
                if(plantInfoBean != null){
                    //????????????
                    PJHistoryRecordActivity.startActivity(PJFromActivityCopy.this,businessType,plantInfoBean.getId());
                }else{
                    ToastUtil.show("??????????????????????????????");
                }
            }
                break;
            case R.id.btn_make_photo:
            {
                //????????????
                if(!StringUtil.isEmpty(qrCodeStr)){
                    isChangeUpdata = true;//????????????
                    ReleaseFilesActivity.startActivity(this,plantInfoBean.getCode(),filesName,plantInfoBean.getId(),businessType);
                }else{
                    ToastUtil.show("??????????????????????????????");
                }
            }
            break;
            case R.id.btn_sc:
            {
                //??????
                if(!StringUtil.isEmpty(qrCodeStr)){
                    if(plantInfoBean.isIsCollect()){
                        if(plantInfoBean.getCollectLevel() == 1){
                            getPresenter().plantCollectAdd(2,plantInfoBean.getId());
                        }else{
                            //????????????
                            getPresenter().plantCollectDel(plantInfoBean.getCollectId());
                        }
                    }else{
                        //????????????
                        getPresenter().plantCollectAdd(1,plantInfoBean.getId());
                    }
                }else{
                    ToastUtil.show("??????????????????????????????");
                }
            }
                break;
            case R.id.btn_scan:
            {
                if(plantInfoBean != null) {
                    if (!StringUtil.isEmpty(plantInfoBean.getPrevCode())) {
                        if (!isUploadResult && isChangeUpdata) {
                            //???????????????+?????????????????????
                            ensureDialog = new EnsureDialog(PJFromActivityCopy.this).builder()
                                    .setGravity(Gravity.CENTER)//??????????????????????????????
                                    .setTitle("??????", getResources().getColor(R.color.black))//????????????????????????????????????????????????
                                    .setCancelable(false)
                                    .setSubTitle("??????????????????????????????????????????????????????")
                                    .setNegativeButton("??????", new View.OnClickListener() {//??????????????????????????????????????????????????????
                                        @Override
                                        public void onClick(View view) {
                                            gotoScanCode();
                                        }
                                    })
                                    .setPositiveButton("????????????", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            sumbitData();
                                            ensureDialog.dismiss();
                                        }
                                    });
                            ensureDialog.show();
                        }else{
                            //?????????
                            gotoScanCode();
                        }
                    }else{
                        //?????????
                        gotoScanCode();
                    }
                }else{
                    //?????????
                    gotoScanCode();
                }
            }
                break;
            case R.id.previousBtn:
            {
                if(plantInfoBean != null){
                    if(!StringUtil.isEmpty(plantInfoBean.getPrevCode())){
                        if(!isUploadResult && isChangeUpdata){
                            ensureDialog = new EnsureDialog(PJFromActivityCopy.this).builder()
                                    .setGravity(Gravity.CENTER)//??????????????????????????????
                                    .setTitle("??????", getResources().getColor(R.color.black))//????????????????????????????????????????????????
                                    .setCancelable(false)
                                    .setSubTitle("??????????????????????????????????????????????????????")
                                    .setNegativeButton("??????", new View.OnClickListener() {//??????????????????????????????????????????????????????
                                        @Override
                                        public void onClick(View view) {
                                            isUploadResult = false;
                                            isChangeUpdata = false;
                                            btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));
                                            getPresenter().plantInfo(plantInfoBean.getPrevCode());
                                        }
                                    })
                                    .setPositiveButton("????????????", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            sumbitData();
                                            ensureDialog.dismiss();
                                        }
                                    });
                            ensureDialog.show();
                        }else{

                            isUploadResult = false;
                            isChangeUpdata = false;
                            btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));

                            getPresenter().plantInfo(plantInfoBean.getPrevCode());
                        }
                    }else{
                        ToastUtil.show("?????????????????????");
                    }
                }else{
                    ToastUtil.show("??????????????????????????????");
                }
            }
            break;
            case R.id.nextBtn:
            {
                if(plantInfoBean != null){
                    if(!StringUtil.isEmpty(plantInfoBean.getNextCode())){
                        if(!isUploadResult && isChangeUpdata){
                            ensureDialog = new EnsureDialog(PJFromActivityCopy.this).builder()
                                    .setGravity(Gravity.CENTER)//??????????????????????????????
                                    .setTitle("??????", getResources().getColor(R.color.black))//????????????????????????????????????????????????
                                    .setCancelable(false)
                                    .setSubTitle("??????????????????????????????????????????????????????")
                                    .setNegativeButton("??????", new View.OnClickListener() {//??????????????????????????????????????????????????????
                                        @Override
                                        public void onClick(View view) {
                                            isUploadResult = false;
                                            isChangeUpdata = false;
                                            btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));

                                            getPresenter().plantInfo(plantInfoBean.getNextCode());

                                            ensureDialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("????????????", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sumbitData();
                                            ensureDialog.dismiss();
                                        }
                                    });
                            ensureDialog.show();
                        }else{
                            isUploadResult = false;
                            isChangeUpdata = false;
                            btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));
                            getPresenter().plantInfo(plantInfoBean.getNextCode());
                        }
                    }else{
                        ToastUtil.show("?????????????????????");
                    }
                }else{
                    ToastUtil.show("??????????????????????????????");
                }
            }
            break;
        }
    }


    public void gotoScanCode(){
        //??????????????????????????????
        if (PackageManager.PERMISSION_GRANTED ==   ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            if (PackageManager.PERMISSION_GRANTED ==   ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 1024);
            }else{
                //????????????????????????
                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                ActivityCompat.requestPermissions(this,perms, 1022);
            }
        }else{
            //????????????????????????
            String[] perms = {"android.permission.CAMERA"};
            ActivityCompat.requestPermissions(this,perms, 1023);
        }
    }

    public void sumbitData(){
        if(plantInfoBean != null){
            PlantRecordAddRequestBean plantRecordAddRequestBean = new PlantRecordAddRequestBean();
            plantRecordAddRequestBean.setHybridId(plantInfoBean.getHybridId());
            plantRecordAddRequestBean.setPlantId(plantInfoBean.getId());
            //??????
            List<PlantRecordAddRequestBean.ImgListBean> imgListBeans = new ArrayList<>();
            String byGoOutImages = (String) SPUtils.get(filesName,"");
            if(!StringUtil.isEmpty(byGoOutImages)){
                uploadedDataList = new Gson().fromJson(byGoOutImages,new TypeToken<List<UploadImageBean>>(){}.getType());
                for (int i = 0;i<uploadedDataList.size();i++){
                    UploadImageBean uploadImageBean = uploadedDataList.get(i);
                    PlantRecordAddRequestBean.ImgListBean imgListBean = new PlantRecordAddRequestBean.ImgListBean();
                    imgListBean.setPath(uploadImageBean.getUrlPath());
                    imgListBean.setType(uploadImageBean.getFileType());
                    imgListBeans.add(imgListBean);
                }
            }
            plantRecordAddRequestBean.setImgList(imgListBeans);
            //????????????
            List<PlantRecordAddRequestBean.LogListBean> logListBeans = new ArrayList<>();
            for(int i = 0;i<attributeBL.size();i++){
                AttributeBean attributeBean = attributeBL.get(i);
                if(attributeBean.getType() == 1){
                    GroupListBean.AttributeListBean attributeListBean = attributeBean.getAttributeListBean();
                    PlantRecordAddRequestBean.LogListBean logListBean = new PlantRecordAddRequestBean.LogListBean();
                    logListBean.setAttributeId(attributeListBean.getId());
                    logListBean.setAttributeName(attributeListBean.getFieldName());
                    if(attributeListBean.getFieldType().equals("radio") || attributeListBean.getFieldType().equals("checkbox")){
                        List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
                        List<String> selectTitle = new ArrayList<>();
                        for (int x = 0;x<selectResultBeans.size();x++){
                            if(selectResultBeans.get(x).getSelect()){
                                selectTitle.add(selectResultBeans.get(x).getTitle());
                            }
                        }
                        if(selectTitle.size()>0){
                            logListBean.setAttributeValue(String.join(",", selectTitle));
                        }
                    }else if(attributeListBean.getFieldType().equals("input") || attributeListBean.getFieldType().equals("text") || attributeListBean.getFieldType().equals("date")){
                        logListBean.setAttributeValue(attributeListBean.getResultStr());
                    }

                    if (!StringUtil.isEmpty(logListBean.getAttributeValue())){
                        logListBeans.add(logListBean);
                    }
                }
            }
            plantRecordAddRequestBean.setLogList(logListBeans);
            getPresenter().plantRecordAdd(plantRecordAddRequestBean);
        }else{
            ToastUtil.show("??????????????????????????????");
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 1023:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //????????????????????????????????????????????????????????????
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivityForResult(intent, 1024);
                } else {
                    //?????????????????????????????????????????????????????????
                    ToastUtil.show("???????????????????????????");
                }
                break;
            case 1022:
                boolean albumAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if(albumAccepted){
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivityForResult(intent, 1024);
                }else{
                    //?????????????????????????????????????????????????????????
                    ToastUtil.show("???????????????????????????");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * ???????????????????????????
         */
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if (requestCode == 1024) {
                String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                //Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);
                if(!StringUtil.isEmpty(result)){
                    isUploadResult = false;//?????????
                    isChangeUpdata = false;//???????????????
                    btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));
                    if(plantInfoBean != null && result.equals(plantInfoBean.getId())){
                        //????????????
                    }else{

                        getPresenter().plantInfo(result);

                        //????????????
                        String byGoOutImages = (String) SPUtils.get(filesName,"");
                        uploadedDataList.clear();
                        if(!StringUtil.isEmpty(byGoOutImages)){
                            uploadedDataList = new Gson().fromJson(byGoOutImages,new TypeToken<List<UploadImageBean>>(){}.getType());
                            if(uploadedDataList.size()!=0){
                                photoNumTv.setVisibility(View.VISIBLE);
                                photoNumTv.setText(uploadedDataList.size()+"");
                            }else{
                                photoNumTv.setVisibility(View.GONE);
                            }
                        }else{
                            photoNumTv.setVisibility(View.GONE);
                        }
                    }
                }else{
                    ToastUtil.show("??????????????????");
                }
            }
        }

    }

    /**
     * ????????????????????????
     * @param plantInfoBean
     */
    @Override
    public void onPlantInfoCallBack(PlantInfoBean plantInfoBean) {
        if(plantInfoBean != null){
            this.plantInfoBean = plantInfoBean;
            qrCodeStr = plantInfoBean.getCode();
            filesName = "make_a_photo_"+qrCodeStr+"_"+ TimeUtil.getCurrentTime("yyyyMMddHHmmss");
            scanResultTv.setText(plantInfoBean.getCode()+" "+plantInfoBean.getHybridName());

            isUploadResult = false;//?????????
            isChangeUpdata = false;//???????????????
            btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));

            //???????????????
            if(plantInfoBean.isIsCollect()){
                btn_sc.setImageResource(plantInfoBean.getCollectLevel() == 1 ? R.mipmap.icon_sc_s : R.mipmap.icon_sc_ss);
            }else{
                btn_sc.setImageResource(R.mipmap.icon_sc);
            }
        }else{
            ToastUtil.show("?????????????????????");
        }
    }

    @Override
    public void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoBean) {
    }

    @Override
    public void onPlantCollectAddCallBack(Object object) {
        ToastUtil.show("????????????");
        getPresenter().plantInfo(qrCodeStr);
    }

    @Override
    public void onSeedlingCollectAddCallBack(Object object) {
    }

    @Override
    public void onPlantCollectDelCallBack(Object o) {
        ToastUtil.show("???????????????");
        getPresenter().plantInfo(qrCodeStr);
    }

    @Override
    public void onSeedlingCollectDelCallBack(Object o) {
    }

    @Override
    public void onPlantRecordAddCallBack(PlantRecordAddBean plantRecordAddBean) {

        isChangeUpdata = false;//?????????,?????????????????????

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4ea964"));
        pDialog.setTitleText("???????????????");
        pDialog.setCancelable(false);
        pDialog.hideConfirmButton();
        pDialog.show();

        /** ?????????60????????????1??? */
        new CountDownTimer(3*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                pDialog.setTitleText("???????????????("+(millisUntilFinished/1000+1)+")");
            }

            @Override
            public void onFinish() {
                pDialog.dismiss();
                isUploadResult = true;
                /**
                 * ???????????????
                 */
                btnSumbit.setBackground(!isUploadResult ? getResources().getDrawable(R.drawable.stroke_background_05) : getResources().getDrawable(R.drawable.stroke_background_05_n));
            }
        }.start();
    }

    @Override
    public void onSeedlingRecordAddCallBack(SeedlingRecordAddBean seedlingRecordAddBean) {
    }

    @Override
    protected boolean applyFullScreen() {
        return false;
    }


    class PJMainAdapter extends BaseQuickAdapter<AttributeBean, BaseViewHolder> {

        public PJMainAdapter(int layoutResId, @Nullable List<AttributeBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AttributeBean item) {

            /**
             storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
             storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
             storeUserCell.priceLable.text = [NSString stringWithFormat:@"??%@",[dataDict objectForKey:@"balance"]];
             */

            TextView fenleiTitle = ((TextView)helper.getView(R.id.fenleiTitle));

            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
            RecyclerView recyclerView2 = ((RecyclerView)helper.getView(R.id.recycle_view));
            LinearLayout editLayout = (LinearLayout)helper.getView(R.id.editLayout);
            EditText editText = (EditText)helper.getView(R.id.editText);
            TextView dateTv = ((TextView)helper.getView(R.id.dateTv));

            if(item.getType() != 0){
                fenleiTitle.setVisibility(View.GONE);

                GroupListBean.AttributeListBean attributeListBean = item.getAttributeListBean();
                titleTv.setText(attributeListBean.getFieldName()+"");
                if(attributeListBean.getFieldType().equals("checkbox") || attributeListBean.getFieldType().equals("radio")){
                    if(attributeListBean.getFieldType().equals("checkbox")){
                        titleTv.setText(attributeListBean.getFieldName()+"(??????)");
                    }

                    recyclerView2.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);
                    dateTv.setVisibility(View.GONE);

                    PJFromItemAdapter pjFromItemAdapter = new PJFromItemAdapter(R.layout.item_pj_from_item_item_layout, attributeListBean.getResults());
                    recyclerView2.setAdapter(pjFromItemAdapter);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

                    pjFromItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            isChangeUpdata = true;//????????????
                            switch (view.getId()){
                                case R.id.titleTv:
                                {
                                    List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeanListTemp = attributeListBean.getResults();
                                    GroupListBean.AttributeListBean.SelectResultBean selectResultBeanTemp = selectResultBeanListTemp.get(position);
                                    //????????????,??????????????????
                                    if(selectResultBeanTemp.getSelect()){
                                        //???????????????????????????
                                        selectResultBeanTemp.setSelect(false);
                                    }else{
                                        //??????????????????,???????????????????????????
                                        if(attributeListBean.getFieldType().equals("radio")){
                                            //????????????????????????????????????????????????
                                            for (int i = 0;i<selectResultBeanListTemp.size();i++){
                                                GroupListBean.AttributeListBean.SelectResultBean srB = selectResultBeanListTemp.get(i);
                                                srB.setSelect(false);
                                                selectResultBeanListTemp.set(i,srB);
                                            }
                                            selectResultBeanTemp.setSelect(true);
                                        }else{
                                            //????????????
                                            selectResultBeanTemp.setSelect(true);
                                        }
                                    }
                                    selectResultBeanListTemp.set(position,selectResultBeanTemp);
                                    attributeListBean.setResults(selectResultBeanListTemp);
                                    item.setAttributeListBean(attributeListBean);
                                    attributeBL.set(helper.getAdapterPosition(),item);
                                    //??????
                                    pjFromItemAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                        }
                    });
                }else if(attributeListBean.getFieldType().equals("input") || attributeListBean.getFieldType().equals("text")){
                    recyclerView2.setVisibility(View.GONE);
                    editLayout.setVisibility(View.VISIBLE);
                    dateTv.setVisibility(View.GONE);

                    editText.setHint("?????????"+attributeListBean.getFieldName());
                    editText.setText(!StringUtil.isEmpty(attributeListBean.getResultStr()) ? attributeListBean.getResultStr() : "");

                    //???EditText????????????????????????????????????TextWatcher
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            isChangeUpdata = true;//????????????
                            Log.d("??????",s.toString());
                            attributeListBean.setResultStr(s.toString());

                            item.setAttributeListBean(attributeListBean);
                            attributeBL.set(helper.getAdapterPosition(),item);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }else if(attributeListBean.getFieldType().equals("date")){
                    recyclerView2.setVisibility(View.GONE);
                    editLayout.setVisibility(View.GONE);
                    dateTv.setVisibility(View.VISIBLE);

                    if(!StringUtil.isEmpty(attributeListBean.getResultStr())){
                        dateTv.setText(attributeListBean.getResultStr());
                        dateTv.setTextColor(getResources().getColor(R.color.forestgreen));
                    }else{
                        dateTv.setText("???????????????");
                        dateTv.setTextColor(getResources().getColor(R.color.gray));
                    }

                    dateTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isChangeUpdata = true;//????????????
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            //???????????????
                            TimePickerView deliveryTime = new TimePickerBuilder(PJFromActivityCopy.this, new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {//??????????????????
                                    dateTv.setText(TimeUtil.getStringFromTime(date,"yyyy-MM-dd"));
                                    dateTv.setTextColor(getResources().getColor(R.color.forestgreen));
                                    attributeListBean.setResultStr(TimeUtil.getStringFromTime(date,"yyyy-MM-dd"));

                                    item.setAttributeListBean(attributeListBean);
                                    attributeBL.set(helper.getAdapterPosition(),item);
                                }
                            }).setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
                                    .build();
                            Calendar cal = Calendar.getInstance();
                            if(!StringUtil.isEmpty(attributeListBean.getResultStr())){
                                cal.setTime(TimeUtil.getTimeFromString(attributeListBean.getResultStr(),"yyyy-MM-dd"));
                            }
                            deliveryTime.setDate(cal);
                            deliveryTime.setTitleText(attributeListBean.getFieldName()+"");
                            deliveryTime.show();
                        }
                    });

                }

            }else{
                fenleiTitle.setVisibility(View.VISIBLE);
                fenleiTitle.setText(item.getName()+"");
            }
        }

    }

    class PJFromItemAdapter extends BaseQuickAdapter<GroupListBean.AttributeListBean.SelectResultBean, BaseViewHolder> {

        public PJFromItemAdapter(int layoutResId, @Nullable List<GroupListBean.AttributeListBean.SelectResultBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GroupListBean.AttributeListBean.SelectResultBean item) {

            /**
             storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
             storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
             storeUserCell.priceLable.text = [NSString stringWithFormat:@"??%@",[dataDict objectForKey:@"balance"]];
             */

            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
            titleTv.setText(item.getTitle()+"");

            if(item.getSelect()){
                titleTv.setTextColor(getResources().getColor(R.color.white));
                titleTv.setBackgroundResource(R.drawable.stroke_background_08);
            }else {
                titleTv.setTextColor(getResources().getColor(R.color.dimgrey));
                titleTv.setBackgroundResource(R.drawable.stroke_background_07);
            }

            helper.addOnClickListener(R.id.titleTv);

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
    }

    /**
     * ???????????????????????????
     * @param map ????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMoonStickyEvent(Map<String,Object> map){

        String option = (String) map.get("option");
        if(!option.equals("copyData")){
            return;
        }
        String plantRecordInfoBeanStr = (String) map.get("plantRecordHistoryInfoBeanList");
        if(!StringUtil.isEmpty(plantRecordInfoBeanStr)){
            //??????
            List<AttributeValuesBean> attributeValuesBeanList = new Gson().fromJson(plantRecordInfoBeanStr,new TypeToken<List<AttributeValuesBean>>(){}.getType());
            List<AttributeBean> attributeBLTemp = CloneObjectUtils.cloneObject(attributeBL);
            if(attributeBLTemp != null){
                for(int i = 0;i<attributeBLTemp.size();i++){
                    AttributeBean attributeBean = attributeBLTemp.get(i);
                    if(attributeBean.getType() == 1){
                        //????????????
                        GroupListBean.AttributeListBean attributeListBean = attributeBean.getAttributeListBean();
                        Boolean itNeedCopy = true;
                        //?????????????????????????????????????????????????????????????????????????????????
                        if(itNeedCopy){
                            for (int x = 0;x<attributeValuesBeanList.size();x++){
                                if((int)attributeValuesBeanList.get(x).getId() == (int)attributeListBean.getId()){
                                    if(attributeListBean.getFieldType().equals("radio")){
                                        List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
                                        //List<String> selectTitle = new ArrayList<>();
                                        for (int y = 0;y<selectResultBeans.size();y++){
                                            GroupListBean.AttributeListBean.SelectResultBean selectResultBean = selectResultBeans.get(y);
                                            boolean flag = selectResultBean.getTitle().equals(attributeValuesBeanList.get(x).getValue());
                                            selectResultBean.setSelect(flag);
                                            selectResultBeans.set(y,selectResultBean);
                                        }
                                        attributeListBean.setResults(selectResultBeans);

                                    }else if(attributeListBean.getFieldType().equals("checkbox")){
                                        //","
                                        List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
                                        //List<String> selectTitle = new ArrayList<>();
                                        for (int y = 0;y<selectResultBeans.size();y++){
                                            GroupListBean.AttributeListBean.SelectResultBean selectResultBean = selectResultBeans.get(y);
                                            String[] nameValue = attributeValuesBeanList.get(x).getValue().split(",");
                                            boolean flag = Arrays.asList(nameValue).contains(selectResultBean.getTitle());
                                            selectResultBean.setSelect(flag);
                                            selectResultBeans.set(y,selectResultBean);
                                        }
                                        attributeListBean.setResults(selectResultBeans);
                                    }else{
                                        attributeListBean.setResultStr(attributeValuesBeanList.get(x).getValue());
                                    }
                                }
                            }
                            //??????????????????
                            attributeBean.setAttributeListBean(attributeListBean);
                            attributeBL.set(i,attributeBean);
                        }
                    }

                }
                pjMainAdapter.notifyDataSetChanged();
            }
        }

    }

}
