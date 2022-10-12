package net.cnbec.catering.ui.main.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.task.DownloadTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.ffmpeg.call.CommonCallBack;
import com.coder.ffmpeg.call.IFFmpegCallBack;
import com.coder.ffmpeg.jni.FFmpegCommand;
import com.coder.ffmpeg.utils.FFmpegUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.matisse.Matisse;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.loader.VideoClickListener;
import com.siberiadante.customdialoglib.EnsureDialog;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UploadImageBean;
import net.cnbec.catering.bean.UserViewInfo;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.adapter.UploadImageAdapter;
import net.cnbec.catering.ui.main.contract.TakeAPhoneContract;
import net.cnbec.catering.ui.main.presenter.TakeAPhotoPresenter;
import net.cnbec.catering.util.CloneObjectUtils;
import net.cnbec.catering.util.FileUtils;
import net.cnbec.catering.util.ImageUtil;
import net.cnbec.catering.util.SPUtils;
import net.cnbec.catering.util.StringUtil;
import net.cnbec.catering.util.ToastUtil;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReleaseFilesActivity extends BaseActivity<TakeAPhoneContract.View, TakeAPhoneContract.Presenter> implements TakeAPhoneContract.View{

    int optionIndex;//新增图片
    String filesName;

    Integer businessId;
    Integer businessType;

    @BindView(R.id.title)
    TextView titleTv;

    //已上传
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    EnsureDialog ensureDialog;

    private UploadImageAdapter uploadedAdapter;
    private List<UploadImageBean> uploadedDataList;

    String qrCodeStr;

    //加载框变量
    private ProgressDialog progressDialog;

    public void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);	//设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();

//        设置超时自动消失
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //取消加载框
//          if(dismissProgressDialog()){
//            //超时处理
//          }
//            }
//        }, 60000);//超时时间60秒
    }

    public Boolean dismissProgressDialog() {
        if (progressDialog != null){
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void startActivity(Context context,String qrCodeStr, String filesName,Integer businessId,Integer businessType) {
        Intent intent = new Intent(context, ReleaseFilesActivity.class);
        intent.putExtra("businessType",businessType);
        intent.putExtra("filesName",filesName);
        intent.putExtra("qrCodeStr",qrCodeStr);
        intent.putExtra("businessId",businessId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_updating_files_layout;
    }

    @Override
    protected TakeAPhoneContract.Presenter initPresenter() {
        return new TakeAPhotoPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        titleTv.setText("附件信息");
        this.businessId = getIntent().getIntExtra("businessId",0);
        this.businessType = getIntent().getIntExtra("businessType",0);
        filesName = getIntent().getStringExtra("filesName");
        qrCodeStr = getIntent().getStringExtra("qrCodeStr");

        uploadedDataList = new ArrayList<>();
        String createImages = (String) SPUtils.get(filesName,"");
        if(!StringUtil.isEmpty(createImages)){
            uploadedDataList = new Gson().fromJson(createImages,new TypeToken<List<UploadImageBean>>(){}.getType());
        }
        uploadedDataList.add(new UploadImageBean(false));

        initRecycle();
    }

    //初始化列表
    public void initRecycle(){
        uploadedAdapter = new UploadImageAdapter(R.layout.item_update_files_local_update_item_layout, uploadedDataList);

        recyclerView.setLayoutManager(new GridLayoutManager(ReleaseFilesActivity.this,3, LinearLayout.VERTICAL,false));
        recyclerView.setAdapter(uploadedAdapter);
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        uploadedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.addBtn:
                    {
                        takePhoto();
                    }
                    break;
                    case R.id.deleteBtn:
                    {
                        //移除
                        ensureDialog = new EnsureDialog(ReleaseFilesActivity.this).builder()
                                .setGravity(Gravity.CENTER)//默认居中，可以不设置
                                .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
                                .setCancelable(false)
                                .setSubTitle("移除照片后不可恢复，是否确认移除")
                                .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setPositiveButton("删除", getResources().getColor(R.color.red), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        uploadedDataList.remove(position);
                                        uploadedAdapter.notifyDataSetChanged();
                                        ensureDialog.dismiss();
                                    }
                                });
                        ensureDialog.show();
                    }
                    break;
                    case R.id.imageView:
                    {
                        //预览
//                        PhotoPerviewActivity.startActivity(ReleaseFilesActivity.this,uploadedDataList.get(position));
                        ArrayList<UserViewInfo> mThumbViewInfoList = new ArrayList<>();

                        for (int i = 0;i<uploadedDataList.size()-1;i++){
                            if(StringUtil.isEmpty(uploadedDataList.get(i).getVideoLocalPath())){
                                mThumbViewInfoList.add(new UserViewInfo(uploadedDataList.get(i).getImageLocalPath()));
                            }else{
                                mThumbViewInfoList.add(new UserViewInfo(uploadedDataList.get(i).getVideoLocalPath(),uploadedDataList.get(i).getImageLocalPath()));
                            }
                        }
                        GPreviewBuilder.from(ReleaseFilesActivity.this)
                                .setData(mThumbViewInfoList)
                                .setUserFragment(UserFragment.class)
                                .setCurrentIndex(position)
                                .setSingleFling(true)
                                .setOnVideoPlayerListener(new VideoClickListener(){
                                    @Override
                                    public void onPlayerVideo(String url) {
                                        Log.d("onPlayerVideo",url);
//                                        PhotoPerviewActivity1.startActivity(ReleaseFilesActivity.this,uploadedDataList.get(position),businessType);
                                        PhotoPerviewActivity.startActivity(ReleaseFilesActivity.this,uploadedDataList.get(position));
//                                   Intent intent=new Intent(VideoViewActivity.this,VideoPlayerDetailedActivity.class);
//                                   intent.putExtra("url",url);
//                                   startActivity(intent);
                                    }
                                })
                                .setType(GPreviewBuilder.IndicatorType.Number)
                                .start();

                    }
                    break;
                }
            }
        });
    }



    @OnClick({R.id.back,R.id.sumbit_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                boolean isUploadAll = true;//默认全部上传了
                for (int i = 0;i<uploadedDataList.size()-1;i++){
                    if(!uploadedDataList.get(i).getUpload()){
                        isUploadAll = false;
                    }
                }
                if(isUploadAll){
                    //全部上传了直接退出当前页
                    finish();
                }else{
                    ensureDialog = new EnsureDialog(ReleaseFilesActivity.this).builder()
                            .setGravity(Gravity.CENTER)//默认居中，可以不设置
                            .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
                            .setCancelable(false)
                            .setSubTitle("有照片未上传，是否确认退出？")
                            .setNegativeButton("继续退出", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            })
                            .setPositiveButton("立即上传", getResources().getColor(R.color.forestgreen), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    optionIndex = 0;
                                    upLoadImages();
                                    ensureDialog.dismiss();
                                }
                            });
                    ensureDialog.show();
                }
            }
            break;

            case R.id.sumbit_data:
            {
                optionIndex = 0;
                upLoadImages();
            }
            break;
        }
    }

    public void takePhoto(){
        //拍摄
        Matisse.from(this)
                .jumpCapture()//直接跳拍摄，默认可以同时拍摄照片和视频
                //.jumpCapture(CaptureMode.Image)//只拍照片
                //.jumpCapture(CaptureMode.Video)//只拍视频
                .isCrop(false) //开启裁剪
                .forResult(1024);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1024 && resultCode == Activity.RESULT_OK) {
            //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
            String captureImagePath = Matisse.obtainCaptureImageResult(data);
            //获取拍摄的视频路径
            String captureVideoPath = Matisse.obtainCaptureVideoResult(data);


            UploadImageBean uploadImageBean = new UploadImageBean(false);


            if(!StringUtil.isEmpty(captureVideoPath)){
                //拍摄的视频
                File file = new File(captureVideoPath);
                uploadImageBean.setImageLocalPath(captureImagePath);
//                uploadImageBean.setVideoLocalPath(captureVideoPath);
                uploadImageBean.setFileType(1);
//                uploadImageBean.setLocalName(file.getName());

                /**
                 * 使用ffmpeg命令行给视频添加水印
                 *
                 * @param srcFile    源文件
                 * @param waterMark  水印文件路径
                 * @param targetFile 目标文件
                 * @return 添加水印后的文件
                 */
                showProgressDialog(this, "视频转码中...");

                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(captureVideoPath);
                int width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)); //宽
                int height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)); //高
                int rotation = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));//视频的方向角度
                //long duration = Long.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000;//视频的长度
                if(rotation==90 || rotation==270){
                    int tempSize = width;
                    width = height;
                    height = tempSize;
                }
                String shuiyinImagePath = ImageUtil.drawSYBitmap(this, qrCodeStr, 6, Color.GREEN, 20, 20,width,height);

                final long startTime = System.currentTimeMillis();
                Thread thread=new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        FFmpegCommand.runCmd(FFmpegUtils.addWaterMark(captureVideoPath, shuiyinImagePath, "/sdcard/yuzhong_shuiyin/" + file.getName()), new IFFmpegCallBack() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onProgress(int i, long l) {
                                Log.d("FFmpegTest",i+"-"+l);
                                /**要实现的方法*/
                                Message message=new Message();
                                message.what=2;
                                message.obj = "视频转码中："+i+"%";
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onCancel() {
                                Log.d("FFmpegTest", "Cancel");
//                                ToastUtil.show("视频取消处理");

                                /**要实现的方法*/
                                Message message=new Message();
                                message.what=1;
                                message.obj = "视频取消转码";
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onComplete() {
                                Log.d("FFmpegTest", "run: 耗时：" + (System.currentTimeMillis() - startTime) + "\n地址：" + "/sdcard/yuzhong_shuiyin/"+file.getName());
                                //ToastUtil.show("视频水印添加完成");

                                uploadImageBean.setVideoLocalPath("/sdcard/yuzhong_shuiyin/"+file.getName());
                                uploadImageBean.setLocalName(file.getName());

                                //添加照片
                                uploadedDataList.add(uploadedDataList.size()-1,uploadImageBean);

                                /**要实现的方法*/
                                Message message=new Message();
                                message.what=3;
                                message.obj = "视频转码完成";
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onError(int i, @Nullable String s) {
                                Log.d("FFmpegTest", "Cancel");

                                /**要实现的方法*/
                                Message message=new Message();
                                message.what=1;
                                message.obj = "视频转码失败："+""+s+"("+i+")";
                                handler.sendMessage(message);
                            }
                        });
                    }
                });
                thread.start();
            }else{
                try{
                    // 拍摄的照片
                    File file = new File(captureImagePath);
                    //保存到本地
                    FileUtils.saveImage(ReleaseFilesActivity.this,file);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());//ImageUtil.getBitMBitmap(file.getPath());
                    Bitmap imageBitmap = ImageUtil.drawTextToRightBottom(this, bitmap, qrCodeStr, 16, Color.GREEN, 20, 20);
                    File imageFile = ImageUtil.saveBitmapFile(imageBitmap,(file.getPath()).substring(0,(file.getPath()).length() - 5)+"_bitmap.jpeg");
                    ImageUtil.saveExif(file.getAbsolutePath(),imageFile.getAbsolutePath());
                    try {
                        ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
                        String make = exif.getAttribute(ExifInterface.TAG_MAKE);
                        Log.i("ReleaseFilesActivity", "EXif:"+make);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uploadImageBean.setImageLocalPath(imageFile.getPath());
                    uploadImageBean.setFileType(0);
                    uploadImageBean.setLocalName(imageFile.getName());

                    //添加照片
                    uploadedDataList.add(uploadedDataList.size()-1,uploadImageBean);
                    uploadedAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    ToastUtil.show("手机内存不足...\n"+e.toString());
                }
            }

            //获取裁剪结果的路径，不管是选择照片裁剪还是拍摄照片裁剪，结果都从这里取
            //String cropPath = Matisse.obtainCropResult(data);
            //获取选择图片或者视频的结果路径，如果开启裁剪的话，获取的是原图的地址
            //Matisse.obtainSelectUriResult(data);//uri形式的路径
            //Matisse.obtainSelectPathResult(data);//文件形式路径

            //ToastUtil.show(captureImagePath+"\n"+captureVideoPath+"\n");
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                ToastUtil.show(msg.obj+"");
                dismissProgressDialog();
            }else if(msg.what == 2){
                showProgressDialog(ReleaseFilesActivity.this,msg.obj+"");
            }else if(msg.what == 3){
                uploadedAdapter.notifyDataSetChanged();
                dismissProgressDialog();
            }
        }
    };

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
//        uploadImageBean.setLocalPath(imageFile.getPath());
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

    /**
     * 上传图片
     */
    public void upLoadImages(){
        if(optionIndex < uploadedDataList.size()-1){
            if(!uploadedDataList.get(optionIndex).getUpload()){
                //未上传的时候
                //String localName = uploadedDataList.get(optionIndex).getLocalName();
                //String[] names = localName.split("\\.");

                String businessTypeStr = (businessType == 0) ? "plant" : "seedling";

                if(uploadedDataList.get(optionIndex).getFileType() == 0){
                    /**
                     * 保存图片到相册
                     */
                    File file = new File(uploadedDataList.get(optionIndex).getImageLocalPath());
                    try {
                        ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                        String make = exif.getAttribute(ExifInterface.TAG_MAKE);
                        Log.i("ReleaseFilesActivity", "EXif:"+make);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileUtils.saveImage(ReleaseFilesActivity.this,file);
                    getPresenter().uploadFile(file,"image/jpeg",businessTypeStr,businessId,businessType);
                }else if(uploadedDataList.get(optionIndex).getFileType() == 1){
                    /**
                     * 保存视频到相册
                     */
                    File file = new File(uploadedDataList.get(optionIndex).getVideoLocalPath());
                    // 其次把文件插入到系统图库
//                    try {
//                        MediaStore.Images.Media.insertImage(ReleaseFilesActivity.this.getContentResolver(),
//                                file.getAbsolutePath(), file.getName(), null);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    // 最后通知图库更新
//                    ReleaseFilesActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
                    FileUtils.saveVideo(ReleaseFilesActivity.this,file);
                    getPresenter().uploadFile(file,"video/mpeg4",businessTypeStr,businessId,businessType);
                }
            }else{
                //已上传的跳过
                optionIndex++;
                upLoadImages();
            }
        }else{
            //保存数据到本地
            uploadedDataList.remove(uploadedDataList.size()-1);
            SPUtils.put(filesName,new Gson().toJson(uploadedDataList));
            finish();
        }
    }

    @Override
    public void onUploadFileCallBack(String urlPath) {
        //替换数据
        UploadImageBean uploadImageBean = CloneObjectUtils.cloneObject(uploadedDataList.get(optionIndex));
        uploadImageBean.setUpload(true);
        uploadImageBean.setUrlPath(urlPath);
        uploadedDataList.set(optionIndex,uploadImageBean);
        uploadedAdapter.notifyDataSetChanged();

        optionIndex++;
        upLoadImages();
    }


    @Override
    protected boolean applyImmersionBar() {
        return true;
    }

}
