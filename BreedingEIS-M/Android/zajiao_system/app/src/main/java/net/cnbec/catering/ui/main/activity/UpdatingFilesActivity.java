//package net.cnbec.catering.ui.main.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.siberiadante.customdialoglib.EnsureDialog;
//
//import net.cnbec.catering.R;
//import net.cnbec.catering.bean.UploadFileBean;
//import net.cnbec.catering.bean.UploadImageBean;
//import net.cnbec.catering.ui.base.BaseTakePhotoActivity;
//import net.cnbec.catering.ui.main.adapter.UploadImageAdapter;
//import net.cnbec.catering.ui.main.contract.TakeAPhoneContract;
//import net.cnbec.catering.ui.main.presenter.TakeAPhotoPresenter;
//import net.cnbec.catering.util.ImageUtil;
//import net.cnbec.catering.util.SPUtils;
//import net.cnbec.catering.util.StringUtil;
//
//import org.devio.takephoto.compress.CompressConfig;
//import org.devio.takephoto.model.TResult;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//public class UpdatingFilesActivity extends BaseTakePhotoActivity<TakeAPhoneContract.View, TakeAPhoneContract.Presenter> implements TakeAPhoneContract.View{
//
//    int optionIndex;//新增图片
//    String filesName;
//
//    @BindView(R.id.title)
//    TextView titleTv;
//
//    //已上传
//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView1;
//
//    EnsureDialog ensureDialog;
//
//    private UploadImageAdapter uploadedAdapter1;
//    private List<UploadImageBean> uploadedDataList1;
//
//    String qrCodeStr;
//
//    /**
//     * 跳转
//     *
//     * @param context
//     */
//    public static void startActivity(Context context,String qrCodeStr, String filesName) {
//        Intent intent = new Intent(context, UpdatingFilesActivity.class);
//        intent.putExtra("filesName",filesName);
//        intent.putExtra("qrCodeStr",qrCodeStr);
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_updating_files_layout;
//    }
//
//    @Override
//    protected TakeAPhoneContract.Presenter initPresenter() {
//        return new TakeAPhotoPresenter();
//    }
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//
//        titleTv.setText("附件信息");
//        filesName = getIntent().getStringExtra("filesName");
//        qrCodeStr = getIntent().getStringExtra("qrCodeStr");
//
//        uploadedDataList1 = new ArrayList<>();
//        String createImages = (String) SPUtils.get(filesName,"");
//        if(!StringUtil.isEmpty(createImages)){
//            uploadedDataList1 = new Gson().fromJson(createImages,new TypeToken<List<UploadImageBean>>(){}.getType());
//        }
//        uploadedDataList1.add(new UploadImageBean(false));
//
//        initRecycle();
//    }
//
//    //初始化列表
//    public void initRecycle(){
//        uploadedAdapter1 = new UploadImageAdapter(R.layout.item_update_files_local_update_item_layout, uploadedDataList1);
//        recyclerView1.setLayoutManager (new GridLayoutManager(UpdatingFilesActivity.this,3, GridLayoutManager.VERTICAL,false));
//        recyclerView1.setAdapter(uploadedAdapter1);
//        recyclerView1.setItemAnimator (new DefaultItemAnimator());
//        uploadedAdapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()){
//                    case R.id.addBtn:
//                    {
//                        takePhoto();
//                    }
//                    break;
//                    case R.id.deleteBtn:
//                    {
//                        //移除
//                        ensureDialog = new EnsureDialog(UpdatingFilesActivity.this).builder()
//                                .setGravity(Gravity.CENTER)//默认居中，可以不设置
//                                .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
//                                .setCancelable(false)
//                                .setSubTitle("移除照片后不可恢复，是否确认移除")
//                                .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
//                                    @Override
//                                    public void onClick(View view) {
//                                    }
//                                })
//                                .setPositiveButton("删除", getResources().getColor(R.color.red), new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        uploadedDataList1.remove(position);
//                                        uploadedAdapter1.notifyDataSetChanged();
//                                        ensureDialog.dismiss();
//                                    }
//                                });
//                        ensureDialog.show();
//                    }
//                    break;
//                    case R.id.imageView:
//                    {
//                        //预览
//                        PhotoPerviewActivity.startActivity(UpdatingFilesActivity.this,uploadedDataList1.get(position));
//                    }
//                    break;
//                }
//            }
//        });
//    }
//
//
//    @OnClick({R.id.back,R.id.sumbit_data})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.back:
//            {
//                boolean isUploadAll = true;//默认全部上传了
//                for (int i = 0;i<uploadedDataList1.size()-1;i++){
//                    if(!uploadedDataList1.get(i).getUpload()){
//                        isUploadAll = false;
//                    }
//                }
//                if(isUploadAll){
//                    //全部上传了直接退出当前页
//                    finish();
//                }else{
//                    ensureDialog = new EnsureDialog(UpdatingFilesActivity.this).builder()
//                            .setGravity(Gravity.CENTER)//默认居中，可以不设置
//                            .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
//                            .setCancelable(false)
//                            .setSubTitle("有照片未上传，是否确认退出？")
//                            .setNegativeButton("继续退出", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
//                                @Override
//                                public void onClick(View view) {
//                                    finish();
//                                }
//                            })
//                            .setPositiveButton("立即上传", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    optionIndex = 0;
//                                    upLoadImages();
//                                    ensureDialog.dismiss();
//                                }
//                            });
//                    ensureDialog.show();
//                }
//            }
//            break;
//
//            case R.id.sumbit_data:
//            {
//                optionIndex = 0;
//                upLoadImages();
//            }
//            break;
//        }
//    }
//
//    public void takePhoto(){
//        File file = new File(Environment.getExternalStorageDirectory(), "/make_a_photo/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        Uri imageUri = Uri.fromFile(file);
//        CompressConfig config;
//        config = new CompressConfig.Builder().setMaxSize(102400)
//                .enablePixelCompress(false)
//                .enableReserveRaw(false)
//                .create();
//        getTakePhoto().onEnableCompress(config, true);
//        getTakePhoto().onPickFromCapture(imageUri);
//    }
//
//    @Override
//    public void takeCancel() {
//        super.takeCancel();
//    }
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//        super.takeFail(result, msg);
//    }
//
//    @Override
//    public void takeSuccess(TResult result) {
//        super.takeSuccess(result);
//        File file = new File(result.getImage().getCompressPath());
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());//ImageUtil.getBitMBitmap(file.getPath());
//        Bitmap imageBitmap = ImageUtil.drawTextToRightBottom(this, bitmap, qrCodeStr, 16, Color.GREEN, 0, 0);
//        File imageFile = ImageUtil.saveBitmapFile(imageBitmap,(file.getPath()).substring(0,(file.getPath()).length() - 4)+"_bitmap.jpg");
//
//        UploadImageBean uploadImageBean = new UploadImageBean();
//        uploadImageBean.setLocalName(imageFile.getName());
//        uploadImageBean.setImageLocalPath(imageFile.getPath());
//        uploadImageBean.setUpload(false);
//
//        //添加照片
//        uploadedDataList1.add(uploadedDataList1.size()-1,uploadImageBean);
//        uploadedAdapter1.notifyDataSetChanged();
//
//        //清除拍照选择器
//        removeTakePhoto();
//        //getPresenter().uploadFile(imageFile, imageFile.getName());
//    }
//
//    /**
//     * 上传图片
//     */
//    public void upLoadImages(){
//        if(optionIndex < uploadedDataList1.size()-1){
//            if(!uploadedDataList1.get(optionIndex).getUpload()){
//                //未上传的时候
//                getPresenter().uploadFile(new File(uploadedDataList1.get(optionIndex).getImageLocalPath()),uploadedDataList1.get(optionIndex).getLocalName());
//            }else{
//                //已上传的跳过
//                optionIndex++;
//                upLoadImages();
//            }
//        }else{
//            ensureDialog = new EnsureDialog(UpdatingFilesActivity.this).builder()
//                    .setGravity(Gravity.CENTER)//默认居中，可以不设置
//                    .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
//                    .setCancelable(false)
//                    .setSubTitle("上传完成，是否离开当前页面？")
//                    .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
//                        @Override
//                        public void onClick(View view) {
//                        }
//                    })
//                    .setPositiveButton("离开", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //保存数据到本地
//                            uploadedDataList1.remove(uploadedDataList1.size()-1);
//                            SPUtils.put(filesName,new Gson().toJson(uploadedDataList1));
//                            finish();
//                        }
//                    });
//            ensureDialog.show();
//        }
//    }
//
//    @Override
//    public void onUploadFileCallBack(UploadFileBean uploadFileBean) {
//        //替换数据
//        UploadImageBean uploadImageBean = uploadedDataList1.get(optionIndex);
//        uploadImageBean.setUpload(true);
////        uploadImageBean.setDevelopHost(uploadFileBean.getDevelopHost());
////        uploadImageBean.setFileOldName(uploadFileBean.getFileOldName());
////        uploadImageBean.setFileSize(uploadFileBean.getFileSize());
////        uploadImageBean.setImg_SIZE_LITTLE(uploadFileBean.getImg_SIZE_LITTLE());
////        uploadImageBean.setImg_SIZE_MAX(uploadFileBean.getImg_SIZE_MAX());
////        uploadImageBean.setImg_SIZE_MIDDLE(uploadFileBean.getImg_SIZE_MIDDLE());
////        uploadImageBean.setHost(uploadFileBean.getHost());
////        uploadImageBean.setPath(uploadFileBean.getPath());
////        uploadImageBean.setResult(uploadFileBean.getResult());
////        uploadImageBean.setTestHost(uploadFileBean.getTestHost());
//
//        uploadedDataList1.set(optionIndex,uploadImageBean);
//        uploadedAdapter1.notifyDataSetChanged();
//
//        optionIndex++;
//        upLoadImages();
//    }
//
//
//    @Override
//    protected boolean applyImmersionBar() {
//        return true;
//    }
//
//}
