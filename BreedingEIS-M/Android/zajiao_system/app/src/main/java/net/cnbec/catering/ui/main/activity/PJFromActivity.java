//package net.cnbec.catering.ui.main.activity;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//
//
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bigkoo.pickerview.builder.TimePickerBuilder;
//import com.bigkoo.pickerview.listener.OnTimeSelectListener;
//import com.bigkoo.pickerview.view.TimePickerView;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import net.cnbec.catering.R;
//import net.cnbec.catering.bean.CollectAddBean;
//import net.cnbec.catering.bean.CollectDelBean;
//import net.cnbec.catering.bean.GroupListBean;
//import net.cnbec.catering.bean.PlantInfoBean;
//import net.cnbec.catering.bean.PlantRecordAddBean;
//import net.cnbec.catering.bean.PlantRecordHistoryInfoBean;
//import net.cnbec.catering.bean.UploadImageBean;
//import net.cnbec.catering.network.requestBean.PlantRecordAddRequestBean;
//import net.cnbec.catering.ui.base.BaseActivity;
//import net.cnbec.catering.ui.main.contract.PJFromContract;
//import net.cnbec.catering.ui.main.presenter.PJFromPresenter;
//import net.cnbec.catering.util.CloneObjectUtils;
//import net.cnbec.catering.util.SPUtils;
//import net.cnbec.catering.util.StringUtil;
//import net.cnbec.catering.util.TimeUtil;
//import net.cnbec.catering.util.ToastUtil;
//import net.cnbec.catering.util.WindowUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//import com.google.zxing.activity.CaptureActivity;
//import com.siberiadante.customdialoglib.EnsureDialog;
//
//public class PJFromActivity extends BaseActivity<PJFromContract.View, PJFromContract.Presenter> implements PJFromContract.View {
//
//    @BindView(R.id.title)
//    TextView title;
//
//    @BindView(R.id.btn_sc)
//    ImageView btn_sc;
//
//    @BindView(R.id.recycle_view)
//    RecyclerView mainRecyclerView;
//
//    String filesName;
//    List<UploadImageBean> uploadedDataList;
//    @BindView(R.id.photoNumTv)
//    TextView photoNumTv;
//
//    @BindView(R.id.scanResultTv)
//    TextView scanResultTv;
//
//    @BindView(R.id.previousBtn)
//    Button previousBtn;
//    @BindView(R.id.nextBtn)
//    Button nextBtn;
//
//    String qrCodeStr;
//    PlantInfoBean plantInfoBean;
//
//    EnsureDialog ensureDialog;
//    Boolean isUploadResult;//??????????????????
//
//    /**
//     * ???????????????????????????????????????
//     */
//    List<GroupListBean> groupListBeans;
//    private PJMainAdapter pjMainAdapter;
//
//    //List<GroupListBean.AttributeListBean> attributeListBeans;
//
//    public static void startActivity(Context context, List<GroupListBean> groupListBeans) {
//        Intent intent = new Intent(context, PJFromActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//????????????
//        intent.putExtra("GroupListBeans",new Gson().toJson(groupListBeans));
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_pj_from;
//    }
//
//
//    @Override
//    protected PJFromContract.Presenter initPresenter() {
//        return new PJFromPresenter();
//    }
//
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//
//        title.setText("??????");
//        uploadedDataList = new ArrayList<>();
//        filesName = "";
//        qrCodeStr = "";
//        isUploadResult = false;
//
//        this.groupListBeans = new Gson().fromJson(getIntent().getStringExtra("GroupListBeans"),new TypeToken<List<GroupListBean>>(){}.getType());
//
//        pjMainAdapter = new PJMainAdapter(R.layout.item_pj_from_layout,this.groupListBeans);
//        mainRecyclerView.setAdapter(pjMainAdapter);
//        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        String byGoOutImages = (String) SPUtils.get(filesName,"");
//        uploadedDataList.clear();
//        if(!StringUtil.isEmpty(byGoOutImages)){
//            uploadedDataList = new Gson().fromJson(byGoOutImages,new TypeToken<List<UploadImageBean>>(){}.getType());
//            if(uploadedDataList.size()!=0){
//                photoNumTv.setVisibility(View.VISIBLE);
//                photoNumTv.setText(uploadedDataList.size()+"");
//            }else{
//                photoNumTv.setVisibility(View.GONE);
//            }
//        }else{
//            photoNumTv.setVisibility(View.GONE);
//        }
//    }
//
//    @OnClick({R.id.back,R.id.btn_sumbit,R.id.btn_make_photo,R.id.btn_scan,R.id.btn_sc,R.id.previousBtn,R.id.nextBtn,R.id.historyRecordBtn})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.back:
//            {
//                finish();
//            }
//                break;
//            case R.id.btn_sumbit:
//            {
//                //??????
//                sumbitData();
//                //PJDetailsActivity.startActivity(this);
//            }
//                break;
//            case R.id.historyRecordBtn:
//            {
//                if(plantInfoBean != null){
//                    //????????????
//                    Intent intent = new Intent(PJFromActivity.this, PJHistoryRecordDetailsActivity.class);
//                    intent.putExtra("plantInfoBean",new Gson().toJson(plantInfoBean));
//                    PJFromActivity.this.startActivityForResult(intent,1025);
//                }else{
//                    ToastUtil.show("??????????????????????????????");
//                }
//            }
//                break;
//            case R.id.btn_make_photo:
//            {
//                //????????????
//                if(!StringUtil.isEmpty(qrCodeStr)){
//                    ReleaseFilesActivity.startActivity(this,plantInfoBean.getCode(),filesName,plantInfoBean.getId());
//                }else{
//                    ToastUtil.show("??????????????????????????????");
//                }
//            }
//            break;
//            case R.id.btn_sc:
//            {
//                //??????
//                if(!StringUtil.isEmpty(qrCodeStr)){
//                    if(plantInfoBean.isIsCollect()){
//                        //????????????
//                        getPresenter().collectDel(plantInfoBean.getCollectId());
//                    }else{
//                        //????????????
////                        CollectAddRequestBean collectAddRequestBean = new CollectAddRequestBean(qrCodeStr,plantInfoBean.getId());
////                        getPresenter().collectAdd(collectAddRequestBean);
//                    }
//                }else{
//                    ToastUtil.show("??????????????????????????????");
//                }
//            }
//                break;
//            case R.id.btn_scan:
//            {
//                //??????????????????????????????
//                if (PackageManager.PERMISSION_GRANTED ==   ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)) {
//                    if (PackageManager.PERMISSION_GRANTED ==   ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        Intent intent = new Intent(this, CaptureActivity.class);
//                        startActivityForResult(intent, 1024);
//                    }else{
//                        //????????????????????????
//                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
//                        ActivityCompat.requestPermissions(this,perms, 1022);
//                    }
//                }else{
//                    //????????????????????????
//                    String[] perms = {"android.permission.CAMERA"};
//                    ActivityCompat.requestPermissions(this,perms, 1023);
//                }
//            }
//                break;
//            case R.id.previousBtn:
//            {
//                if(plantInfoBean != null){
//                    if(!StringUtil.isEmpty(plantInfoBean.getPrevCode())){
//                        if(!isUploadResult){
//                            ensureDialog = new EnsureDialog(PJFromActivity.this).builder()
//                                    .setGravity(Gravity.CENTER)//??????????????????????????????
//                                    .setTitle("??????", getResources().getColor(R.color.black))//????????????????????????????????????????????????
//                                    .setCancelable(false)
//                                    .setSubTitle("??????????????????????????????????????????????????????")
//                                    .setNegativeButton("??????", new View.OnClickListener() {//??????????????????????????????????????????????????????
//                                        @Override
//                                        public void onClick(View view) {
//                                            isUploadResult = false;
//                                            getPresenter().plantInfo(plantInfoBean.getPrevCode());
//                                        }
//                                    })
//                                    .setPositiveButton("????????????", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//
//                                            sumbitData();
//                                            ensureDialog.dismiss();
//                                        }
//                                    });
//                            ensureDialog.show();
//                        }else{
//
//                            isUploadResult = false;
//
//                            getPresenter().plantInfo(plantInfoBean.getPrevCode());
//                        }
//                    }else{
//                        ToastUtil.show("?????????????????????");
//                    }
//                }else{
//                    ToastUtil.show("??????????????????????????????");
//                }
//            }
//            break;
//            case R.id.nextBtn:
//            {
//                if(plantInfoBean != null){
//                    if(!StringUtil.isEmpty(plantInfoBean.getNextCode())){
//                        if(!isUploadResult){
//                            ensureDialog = new EnsureDialog(PJFromActivity.this).builder()
//                                    .setGravity(Gravity.CENTER)//??????????????????????????????
//                                    .setTitle("??????", getResources().getColor(R.color.black))//????????????????????????????????????????????????
//                                    .setCancelable(false)
//                                    .setSubTitle("??????????????????????????????????????????????????????")
//                                    .setNegativeButton("??????", new View.OnClickListener() {//??????????????????????????????????????????????????????
//                                        @Override
//                                        public void onClick(View view) {
//                                            isUploadResult = false;
//
//                                            getPresenter().plantInfo(plantInfoBean.getNextCode());
//
//                                            ensureDialog.dismiss();
//                                        }
//                                    })
//                                    .setPositiveButton("????????????", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            sumbitData();
//                                            ensureDialog.dismiss();
//                                        }
//                                    });
//                            ensureDialog.show();
//                        }else{
//                            isUploadResult = false;
//                            getPresenter().plantInfo(plantInfoBean.getNextCode());
//                        }
//                    }else{
//                        ToastUtil.show("?????????????????????");
//                    }
//                }else{
//                    ToastUtil.show("??????????????????????????????");
//                }
//            }
//            break;
//        }
//    }
//
//
//    public void sumbitData(){
//        if(plantInfoBean != null){
//            PlantRecordAddRequestBean plantRecordAddRequestBean = new PlantRecordAddRequestBean();
//            plantRecordAddRequestBean.setHybridId(plantInfoBean.getHybridId());
//            plantRecordAddRequestBean.setPlantId(plantInfoBean.getId());
//            //??????
//            List<PlantRecordAddRequestBean.ImgListBean> imgListBeans = new ArrayList<>();
//            String byGoOutImages = (String) SPUtils.get(filesName,"");
//            if(!StringUtil.isEmpty(byGoOutImages)){
//                uploadedDataList = new Gson().fromJson(byGoOutImages,new TypeToken<List<UploadImageBean>>(){}.getType());
//                for (int i = 0;i<uploadedDataList.size();i++){
//                    UploadImageBean uploadImageBean = uploadedDataList.get(i);
//                    PlantRecordAddRequestBean.ImgListBean imgListBean = new PlantRecordAddRequestBean.ImgListBean();
//                    imgListBean.setPath(uploadImageBean.getUrlPath());
//                    imgListBean.setType(uploadImageBean.getFileType());
//                    imgListBeans.add(imgListBean);
//                }
//            }
//            plantRecordAddRequestBean.setImgList(imgListBeans);
//            //????????????
//            List<PlantRecordAddRequestBean.LogListBean> logListBeans = new ArrayList<>();
//            for(int i = 0;i<groupListBeans.size();i++){
//                List<GroupListBean.AttributeListBean> attributeListBeans = groupListBeans.get(i).getAttributeList();
//                for (int j = 0;j<attributeListBeans.size();j++){
//                    GroupListBean.AttributeListBean attributeListBean = attributeListBeans.get(j);
//                    PlantRecordAddRequestBean.LogListBean logListBean = new PlantRecordAddRequestBean.LogListBean();
//                    logListBean.setAttributeId(attributeListBean.getId());
//                    logListBean.setAttributeName(attributeListBean.getFieldName());
//                    if(attributeListBean.getFieldType().equals("radio") || attributeListBean.getFieldType().equals("checkbox")){
//                        List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
//                        List<String> selectTitle = new ArrayList<>();
//                        for (int x = 0;x<selectResultBeans.size();x++){
//                            if(selectResultBeans.get(x).getSelect()){
//                                selectTitle.add(selectResultBeans.get(x).getTitle());
//                            }
//                        }
//                        if(selectTitle.size()>0){
//                            logListBean.setAttributeValue(String.join(",", selectTitle));
//                        }
//                    }else if(attributeListBean.getFieldType().equals("input") || attributeListBean.getFieldType().equals("text") || attributeListBean.getFieldType().equals("date")){
//                        logListBean.setAttributeValue(attributeListBean.getResultStr());
//                    }
//
//                    if (!StringUtil.isEmpty(logListBean.getAttributeValue())){
//                        logListBeans.add(logListBean);
//                    }
//                }
//            }
//            plantRecordAddRequestBean.setLogList(logListBeans);
//            getPresenter().plantRecordAdd(plantRecordAddRequestBean);
//        }else{
//            ToastUtil.show("??????????????????????????????");
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
//        switch (permsRequestCode) {
//            case 1023:
//                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                if (cameraAccepted) {
//                    //????????????????????????????????????????????????????????????
//                    Intent intent = new Intent(this, CaptureActivity.class);
//                    startActivityForResult(intent, 1024);
//                } else {
//                    //?????????????????????????????????????????????????????????
//                    ToastUtil.show("???????????????????????????");
//                }
//                break;
//            case 1022:
//                boolean albumAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
//                if(albumAccepted){
//                    Intent intent = new Intent(this, CaptureActivity.class);
//                    startActivityForResult(intent, 1024);
//                }else{
//                    //?????????????????????????????????????????????????????????
//                    ToastUtil.show("???????????????????????????");
//                }
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        /**
//         * ???????????????????????????
//         */
//        super.onActivityResult(requestCode, resultCode, data);
//        if(data!=null){
//            if (requestCode == 1024) {
//                String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
//                //Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);
//                if(!StringUtil.isEmpty(result)){
//                    getPresenter().plantInfo(result);
//                }else{
//                    ToastUtil.show("??????????????????");
//                }
//            }else if (requestCode == 1025) {
//                Bundle bundle = data.getExtras();
//                if(bundle!=null){
//                    String plantRecordInfoBeanStr = bundle.getString("plantRecordHistoryInfoBeanList");
//                    if(!StringUtil.isEmpty(plantRecordInfoBeanStr)){
//                        List<PlantRecordHistoryInfoBean> plantRecordHistoryInfoBeanList = new Gson().fromJson(plantRecordInfoBeanStr,new TypeToken<List<PlantRecordHistoryInfoBean>>(){}.getType());
//
//                        //??????
//                        List<GroupListBean> groupListBeansTemp = CloneObjectUtils.cloneObject(groupListBeans);
//
//                        for(int i = 0;i<groupListBeansTemp.size();i++){
//                            GroupListBean groupListBean = groupListBeansTemp.get(i);
//                            List<GroupListBean.AttributeListBean> attributeListBeans = groupListBean.getAttributeList();
//                            for (int j = 0;j<attributeListBeans.size();j++){
//                                GroupListBean.AttributeListBean attributeListBean = attributeListBeans.get(j);
//
//                                Boolean itNeedCopy = true;
//                                //?????????????????????????????????????????????????????????????????????????????????
////                                if(attributeListBean.getFieldType().equals("radio") || attributeListBean.getFieldType().equals("checkbox")){
////                                    List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
////                                    //List<String> selectTitle = new ArrayList<>();
////                                    for (int x = 0;x<selectResultBeans.size();x++){
////                                        if(selectResultBeans.get(x).getSelect()){
////                                            itNeedCopy = false;
////                                        }
////                                    }
////                                }else{
////                                    if(!StringUtil.isEmpty(attributeListBean.getResultStr())){
////                                        itNeedCopy = false;
////                                    }
////                                }
//
//                                //??????
//                                if(itNeedCopy){
//                                    for (int x = 0;x<plantRecordHistoryInfoBeanList.size();x++){
//                                        if((int)plantRecordHistoryInfoBeanList.get(x).getId() == (int)attributeListBean.getId()){
//                                            if(attributeListBean.getFieldType().equals("radio")){
//                                                List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
//                                                //List<String> selectTitle = new ArrayList<>();
//                                                for (int y = 0;y<selectResultBeans.size();y++){
//                                                    GroupListBean.AttributeListBean.SelectResultBean selectResultBean = selectResultBeans.get(y);
//                                                    boolean flag = selectResultBean.getTitle().equals(plantRecordHistoryInfoBeanList.get(x).getValue());
//                                                    selectResultBean.setSelect(flag);
//                                                    selectResultBeans.set(y,selectResultBean);
//                                                }
//                                                attributeListBean.setResults(selectResultBeans);
//
//                                            }else if(attributeListBean.getFieldType().equals("checkbox")){
//                                                //","
//                                                List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeans = attributeListBean.getResults();
//                                                //List<String> selectTitle = new ArrayList<>();
//                                                for (int y = 0;y<selectResultBeans.size();y++){
//                                                    GroupListBean.AttributeListBean.SelectResultBean selectResultBean = selectResultBeans.get(y);
//                                                    String[] nameValue = plantRecordHistoryInfoBeanList.get(x).getValue().split(",");
//                                                    boolean flag = Arrays.asList(nameValue).contains(selectResultBean.getTitle());
//                                                    selectResultBean.setSelect(flag);
//                                                    selectResultBeans.set(y,selectResultBean);
//                                                }
//                                                attributeListBean.setResults(selectResultBeans);
//                                            }else{
//                                                attributeListBean.setResultStr(plantRecordHistoryInfoBeanList.get(x).getValue());
//                                            }
//                                        }
//                                    }
//                                    //??????????????????
//                                    //List<GroupListBean.AttributeListBean> attributeListBeansTemp = CloneObjectUtils.cloneObject(attributeListBeans);
//                                    attributeListBeans.set(j,attributeListBean);
//                                    groupListBean.setAttributeList(attributeListBeans);
//                                    groupListBeans.set(i,groupListBean);
//                                }
//
//                            }
//                        }
//
//
//                        pjMainAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }
//
//    }
//
//    /**
//     * ????????????????????????
//     * @param plantInfoBean
//     */
//    @Override
//    public void onPlantInfoCallBack(PlantInfoBean plantInfoBean) {
//        if(plantInfoBean != null){
//            this.plantInfoBean = plantInfoBean;
//            qrCodeStr = plantInfoBean.getCode();
//            filesName = "make_a_photo_"+qrCodeStr+"_"+ TimeUtil.getCurrentTime("yyyyMMddHHmmss");
//            scanResultTv.setText(plantInfoBean.getCode()+" "+plantInfoBean.getHybridName());
//
//            //???????????????
//            btn_sc.setImageResource(plantInfoBean.isIsCollect() ? R.mipmap.icon_sc_s : R.mipmap.icon_sc);
//
//            //????????????
//            String byGoOutImages = (String) SPUtils.get(filesName,"");
//            uploadedDataList.clear();
//            if(!StringUtil.isEmpty(byGoOutImages)){
//                uploadedDataList = new Gson().fromJson(byGoOutImages,new TypeToken<List<UploadImageBean>>(){}.getType());
//                if(uploadedDataList.size()!=0){
//                    photoNumTv.setVisibility(View.VISIBLE);
//                    photoNumTv.setText(uploadedDataList.size()+"");
//                }else{
//                    photoNumTv.setVisibility(View.GONE);
//                }
//            }else{
//                photoNumTv.setVisibility(View.GONE);
//            }
//        }else{
//            ToastUtil.show("?????????????????????");
//        }
//    }
//
//    @Override
//    public void onCollectAddCallBack(CollectAddBean collectAddBean) {
//        ToastUtil.show("????????????");
//
////        plantInfoBean.setIsCollect(true);
////        btn_sc.setImageResource(R.mipmap.icon_sc_s);
//
//        getPresenter().plantInfo(qrCodeStr);
//    }
//
//    @Override
//    public void onCollectDelCallBack(CollectDelBean collectDelBean) {
//        ToastUtil.show("???????????????");
//
////        plantInfoBean.setIsCollect(false);
////        btn_sc.setImageResource(R.mipmap.icon_sc);
//
//        getPresenter().plantInfo(qrCodeStr);
//    }
//
//    @Override
//    public void onPlantRecordAddCallBack(PlantRecordAddBean plantRecordAddBean) {
//        //????????????
//        ToastUtil.show("???????????????");
//        isUploadResult = true;
//    }
//
//    @Override
//    protected boolean applyFullScreen() {
//        return false;
//    }
//
//
//    class PJMainAdapter extends BaseQuickAdapter<GroupListBean, BaseViewHolder> {
//
//        public PJMainAdapter(int layoutResId, @Nullable List<GroupListBean> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, GroupListBean item) {
//
//            /**
//             storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
//             storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
//             storeUserCell.priceLable.text = [NSString stringWithFormat:@"??%@",[dataDict objectForKey:@"balance"]];
//             */
//
//            TextView titleTv = ((TextView)helper.getView(R.id.fenleiTitle));
//            RecyclerView recyclerView1 = ((RecyclerView)helper.getView(R.id.recycle_view));
//
//            titleTv.setText(item.getName()+"");
//
//            List<GroupListBean.AttributeListBean> attributeListBeans = CloneObjectUtils.cloneObject(groupListBeans.get(helper.getAdapterPosition()).getAttributeList());
//
//            List<Map<Integer,GroupListBean.AttributeListBean>> mapList = new ArrayList<>();
//            for (int i = 0;i<attributeListBeans.size();i++){
//                Map<Integer,GroupListBean.AttributeListBean> map= new HashMap<>();
//                map.put(helper.getAdapterPosition(),attributeListBeans.get(i));
//                mapList.add(map);
//            }
//
//            PJFromAdapter pjFromAdapter = new PJFromAdapter(R.layout.item_pj_from_item_layout, mapList);
//            recyclerView1.setAdapter(pjFromAdapter);
//            recyclerView1.setLayoutManager(new LinearLayoutManager(PJFromActivity.this, LinearLayoutManager.VERTICAL, false));
//
//        }
//
//    }
//
//
//    class PJFromAdapter extends BaseQuickAdapter<Map<Integer,GroupListBean.AttributeListBean>, BaseViewHolder> {
//
//        public PJFromAdapter(int layoutResId, @Nullable List<Map<Integer,GroupListBean.AttributeListBean>> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, Map<Integer,GroupListBean.AttributeListBean> item) {
//
//            /**
//             storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
//             storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
//             storeUserCell.priceLable.text = [NSString stringWithFormat:@"??%@",[dataDict objectForKey:@"balance"]];
//             */
//
//            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
//            RecyclerView recyclerView2 = ((RecyclerView)helper.getView(R.id.recycle_view));
//            LinearLayout editLayout = (LinearLayout)helper.getView(R.id.editLayout);
//            EditText editText = (EditText)helper.getView(R.id.editText);
//            TextView dateTv = ((TextView)helper.getView(R.id.dateTv));
//
//            Integer mainIndex = 0;
//            for(Integer key : item.keySet()){
//                mainIndex = key;
//            }
//            GroupListBean.AttributeListBean value = item.get(mainIndex);
//
//            titleTv.setText(value.getFieldName()+"");
//
//
//            if(value.getFieldType().equals("checkbox") || value.getFieldType().equals("radio")){
//                if(value.getFieldType().equals("checkbox")){
//                    titleTv.setText(value.getFieldName()+"(??????)");
//                }
//
//                recyclerView2.setVisibility(View.VISIBLE);
//                editLayout.setVisibility(View.GONE);
//                dateTv.setVisibility(View.GONE);
//
//                PJFromItemAdapter pjFromItemAdapter = new PJFromItemAdapter(R.layout.item_pj_from_item_item_layout, value.getResults());
//                recyclerView2.setAdapter(pjFromItemAdapter);
//                recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//
//                pjFromItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//                    @Override
//                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                        switch (view.getId()){
//                            case R.id.titleTv:
//                            {
//                                List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeanListTemp = value.getResults();
//                                GroupListBean.AttributeListBean.SelectResultBean selectResultBeanTemp = selectResultBeanListTemp.get(position);
//                                //????????????,??????????????????
//                                if(selectResultBeanTemp.getSelect()){
//                                    //???????????????????????????
//                                    selectResultBeanTemp.setSelect(false);
//                                }else{
//                                    //??????????????????,???????????????????????????
//                                    if(value.getFieldType().equals("radio")){
//                                        //????????????????????????????????????????????????
//                                        for (int i = 0;i<selectResultBeanListTemp.size();i++){
//                                            GroupListBean.AttributeListBean.SelectResultBean srB = selectResultBeanListTemp.get(i);
//                                            srB.setSelect(false);
//                                            selectResultBeanListTemp.set(i,srB);
//                                        }
//                                        selectResultBeanTemp.setSelect(true);
//                                    }else{
//                                        //????????????
//                                        selectResultBeanTemp.setSelect(true);
//                                    }
//                                }
//                                selectResultBeanListTemp.set(position,selectResultBeanTemp);
//                                value.setResults(selectResultBeanListTemp);
//
//                                Integer index = 0;
//                                for(Integer key : item.keySet()){
//                                    index = key;
//                                }
//                                List<GroupListBean.AttributeListBean> attributeListBeans = CloneObjectUtils.cloneObject(groupListBeans.get(index).getAttributeList());
//                                attributeListBeans.set(helper.getAdapterPosition(),value);
//
//                                GroupListBean groupListBeanTemp = CloneObjectUtils.cloneObject(groupListBeans.get(index));
//                                groupListBeanTemp.setAttributeList(CloneObjectUtils.cloneObject(attributeListBeans));
//                                groupListBeans.set(index,CloneObjectUtils.cloneObject(groupListBeanTemp));
//
//                                //??????
//                                pjFromItemAdapter.notifyDataSetChanged();
//                            }
//                            break;
//                        }
//                    }
//                });
//            }else if(value.getFieldType().equals("input") || value.getFieldType().equals("text")){
//                recyclerView2.setVisibility(View.GONE);
//                editLayout.setVisibility(View.VISIBLE);
//                dateTv.setVisibility(View.GONE);
//
//                editText.setHint("?????????"+value.getFieldName());
//                editText.setText(!StringUtil.isEmpty(value.getResultStr()) ? value.getResultStr() : "");
//
//                //???EditText????????????????????????????????????TextWatcher
//                editText.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        Log.d("??????",s.toString());
//                        value.setResultStr(s.toString());
//
//                        Integer index = 0;
//                        for(Integer key : item.keySet()){
//                            index = key;
//                        }
//                        List<GroupListBean.AttributeListBean> attributeListBeans = CloneObjectUtils.cloneObject(groupListBeans.get(index).getAttributeList());
//                        attributeListBeans.set(helper.getAdapterPosition(),value);
//
//                        GroupListBean groupListBeanTemp = CloneObjectUtils.cloneObject(groupListBeans.get(index));
//                        groupListBeanTemp.setAttributeList(CloneObjectUtils.cloneObject(attributeListBeans));
//                        groupListBeans.set(index,CloneObjectUtils.cloneObject(groupListBeanTemp));
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
//                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if(hasFocus){
//                            //???????????????
//                            Integer index = 0;
//                            for(Integer key : item.keySet()){
//                                index = key;
//                            }
//
//                            int heightPx = 0;
//                            for(int i = 0;i<((int)index);i++){
//                                heightPx = groupListBeans.get(i).getAttributeList().size()*WindowUtils.dip2px(PJFromActivity.this,56) +WindowUtils.dip2px(PJFromActivity.this,44);
//                            }
//                            heightPx += (helper.getAdapterPosition()*WindowUtils.dip2px(PJFromActivity.this,56)+WindowUtils.dip2px(PJFromActivity.this,44));
//
//                            //ToastUtil.show(""+helper.getAdapterPosition());
//                            mainRecyclerView.scrollTo(0,heightPx);
//                        }
//                    }
//                });
//
//            }else if(value.getFieldType().equals("date")){
//                recyclerView2.setVisibility(View.GONE);
//                editLayout.setVisibility(View.GONE);
//                dateTv.setVisibility(View.VISIBLE);
//
//                if(!StringUtil.isEmpty(value.getResultStr())){
//                    dateTv.setText(value.getResultStr());
//                    dateTv.setTextColor(getResources().getColor(R.color.forestgreen));
//                }else{
//                    dateTv.setText("???????????????");
//                    dateTv.setTextColor(getResources().getColor(R.color.gray));
//                }
//
//                dateTv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//                        //???????????????
//                        TimePickerView deliveryTime = new TimePickerBuilder(PJFromActivity.this, new OnTimeSelectListener() {
//                            @Override
//                            public void onTimeSelect(Date date, View v) {//??????????????????
//                                dateTv.setText(TimeUtil.getStringFromTime(date,"yyyy-MM-dd"));
//                                dateTv.setTextColor(getResources().getColor(R.color.forestgreen));
//                                value.setResultStr(TimeUtil.getStringFromTime(date,"yyyy-MM-dd"));
//
//                                Integer index = 0;
//                                for(Integer key : item.keySet()){
//                                    index = key;
//                                }
//                                List<GroupListBean.AttributeListBean> attributeListBeans = CloneObjectUtils.cloneObject(groupListBeans.get(index).getAttributeList());
//                                attributeListBeans.set(helper.getAdapterPosition(),value);
//
//                                GroupListBean groupListBeanTemp = CloneObjectUtils.cloneObject(groupListBeans.get(index));
//                                groupListBeanTemp.setAttributeList(CloneObjectUtils.cloneObject(attributeListBeans));
//                                groupListBeans.set(index,CloneObjectUtils.cloneObject(groupListBeanTemp));
//                            }
//                        }).setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
//                                .build();
//                        Calendar cal = Calendar.getInstance();
//                        if(!StringUtil.isEmpty(value.getResultStr())){
//                            cal.setTime(TimeUtil.getTimeFromString(value.getResultStr(),"yyyy-MM-dd"));
//                        }
//                        deliveryTime.setDate(cal);
//                        deliveryTime.setTitleText(value.getFieldName()+"");
//                        deliveryTime.show();
//                    }
//                });
//
//            }
//
//
//
//        }
//
//    }
//
//    class PJFromItemAdapter extends BaseQuickAdapter<GroupListBean.AttributeListBean.SelectResultBean, BaseViewHolder> {
//
//        public PJFromItemAdapter(int layoutResId, @Nullable List<GroupListBean.AttributeListBean.SelectResultBean> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, GroupListBean.AttributeListBean.SelectResultBean item) {
//
//            /**
//             storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
//             storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
//             storeUserCell.priceLable.text = [NSString stringWithFormat:@"??%@",[dataDict objectForKey:@"balance"]];
//             */
//
//            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
//            titleTv.setText(item.getTitle()+"");
//
//            if(item.getSelect()){
//                titleTv.setTextColor(getResources().getColor(R.color.white));
//                titleTv.setBackgroundResource(R.drawable.stroke_background_08);
//            }else {
//                titleTv.setTextColor(getResources().getColor(R.color.dimgrey));
//                titleTv.setBackgroundResource(R.drawable.stroke_background_07);
//            }
//
//            helper.addOnClickListener(R.id.titleTv);
//
//        }
//
//    }
//
//}
