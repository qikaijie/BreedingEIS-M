package net.cnbec.catering.ui.main.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.previewlibrary.view.BasePhotoFragment;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UserViewInfo;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.util.FileUtils;
import net.cnbec.catering.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author  yangc
 * date 2017/11/22
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class UserFragment extends BasePhotoFragment {
    /****用户具体数据模型***/
    private UserViewInfo b;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b = (UserViewInfo) getBeanViewInfo();
        loading.setVisibility(View.GONE);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("SmoothImageView","onLongClick");
//                Toast.makeText(getContext(), "长按事件:" + b.getUser(), Toast.LENGTH_LONG).show();
                getShareMune();
                return false;
            }
        });
        String video = b.getVideoUrl();
        if (video != null && !video.isEmpty()) {
            btnVideo.setVisibility(View.VISIBLE);
            ViewCompat.animate(btnVideo).alpha(1).setDuration(1000).start();
        } else {
            btnVideo.setVisibility(View.GONE);
        }
    }


    private void getShareMune() {

        final Dialog mdialog = new Dialog(getActivity(), R.style.photo_dialog);
        mdialog.setContentView(View.inflate(getActivity(), R.layout.layout_popwindow, null));
        // 弹出对话框
        Window window = mdialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.y = 20;
        window.setContentView(R.layout.layout_popwindow);
        final Button save = (Button) window.findViewById(R.id.btn_save);
        final Button back = (Button) window.findViewById(R.id.btn_cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showProgressDialog(getContext(), "资源保存中...");
                String filename = b.getUrl().substring(b.getUrl().lastIndexOf("/") + 1);

                String fontFolderPath = FileUtils.getSDPath()+"/AAA_Pear";
                File outFile = new File(fontFolderPath);
                if (!outFile.exists()) {
                    // 创建文件夹
                    outFile.mkdirs();
                }

                String downLoadFilePath = fontFolderPath;// + "/" + filename;

                new Thread() {
                    public void run() {
                        try {
                            Bitmap myBitmap = Glide.with(getContext())
                                    .asBitmap()
                                    .load(b.getUrl())
                                    .submit(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
                            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
                            Log.e("wwww", bitmap.toString());
                            saveImageToGallery(bitmap,downLoadFilePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }.start();


//                long taskId = Aria.download(getActivity())
//                        .load(b.getUrl())     //读取下载地址
//                        .setFilePath(downLoadFilePath) //设置文件保存的完整路径
//                        .create();   //创建并启动下载
//
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(downLoadFilePath))));
//                        ToastUtil.show("图片保存成功");
//                    }
//                };
//                Timer timer = new Timer();
//                timer.schedule(task, 3000);//3秒后执行TimeTask的run方法

                mdialog.dismiss();

            }
        });
        //取消
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
        mdialog.show();

    }

    public void saveImageToGallery(Bitmap bmp,String galleryPath) {
        if (bmp == null) {
//            Log.e(TAG, "bitmap---为空");
            return;
        }
//        String galleryPath = Environment.getExternalStorageDirectory()
//                + File.separator + Environment.DIRECTORY_DCIM
//                + File.separator + "Camera" + File.separator;
        String fileName = +System.currentTimeMillis() + ".jpg";
        File file = new File(galleryPath, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show("保存成功");
                    }
                });
//                Log.e(TAG, "图片保存成功 保存在:" + file.getPath());
            } else {
//                Log.e(TAG, "图片保存失败");
            }
        } catch (IOException e) {
//            Log.e(TAG, "保存图片找不到文件夹");
            e.printStackTrace();
        }
    }

//    //在这里处理任务执行中的状态，如进度进度条的刷新
//    @Download.onTaskRunning protected void running(DownloadTask task) {
////        if(task.getKey().eques(url)){
////            //可以通过url判断是否是指定任务的回调
////        }
//        int p = task.getPercent();	//任务进度百分比
//        String speed = task.getConvertSpeed();	//转换单位后的下载速度，单位转换需要在配置文件中打开
//        long speed1 = task.getSpeed(); //原始byte长度速度
//
//        showProgressDialog(getContext(), "资源保存中...("+p+"%)");
//
//    }
//    @Download.onTaskComplete void taskComplete(DownloadTask task) {
//        //在这里处理任务完成的状态
//        dismissProgressDialog();
//        ToastUtil.show("保存成功");
//    }
//
//    @Download.onTaskFail void taskFail(DownloadTask task){
//        dismissProgressDialog();
//        ToastUtil.show("下载失败");
//    }

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(com.previewlibrary.R.layout.fragment_image_photo_layout, container, false);
    }
}
