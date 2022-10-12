package net.cnbec.catering.ui.main.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.UploadImageBean;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.network.api.NetWorkManager;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
import net.cnbec.catering.util.FileDirUtil;
import net.cnbec.catering.util.ToastUtil;

import java.io.File;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;
import me.kareluo.intensify.image.IntensifyImageView;

/**
 * @Describe: 测试页面，用于开发过程中测试使用，后期删掉
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class PhotoPerviewActivity1 extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View {


    @BindView(R.id.intensify_image)
    IntensifyImageView intensifyImageView;

    @BindView(R.id.videoView)
    VideoView videoView;

    PlantRecordInfoBean.ImgListBean imgListBean;

    Integer businessType;

    String downLoadFilePath;

    @BindView(R.id.downLoad)
    LinearLayout downLoadLL;

    public static void startActivity(Context context, Object imgListBean,Integer businessType) {
        Intent intent = new Intent(context, PhotoPerviewActivity1.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        intent.putExtra("imgListBean",new Gson().toJson(imgListBean));
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_perview;
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 将对象注册到Aria
         */
        Aria.download(this).register();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        businessType = getIntent().getIntExtra("businessType", 0);

        imgListBean = new Gson().fromJson(getIntent().getStringExtra("imgListBean"), new TypeToken<PlantRecordInfoBean.ImgListBean>() {
        }.getType());

        String filename = imgListBean.getPath().substring(imgListBean.getPath().lastIndexOf("/") + 1);
        downLoadFilePath = this.getExternalMediaDirs()[0].getAbsolutePath() + "/" + filename;//FileDirUtil.getInstance().getExternalFilesDir((imgListBean.getType() == 0 ? "image_" : "video_")+System.currentTimeMillis()+(imgListBean.getType() == 0 ? ".jpeg" : ".mp4"));
        File file = new File(downLoadFilePath);
        downLoadLL.setVisibility(!file.exists() ? View.VISIBLE : View.GONE);

        if(imgListBean.getType() == 0){
            //图片
            intensifyImageView.setVisibility(View.VISIBLE);
            downloadImage(URLs.BASE_IMAGE_URL +imgListBean.getPath());
        }else if(imgListBean.getType() == 1){
            //视频
            videoView.setVisibility(View.VISIBLE);
            /**
             * videoView.start(); //开始播放
             * videoView.pause(); //暂停
             * videoView.stopPlayback(); //停止播放
             * videoView.isPlaying(); //获取是否在播放状态
             * videoView.setVideoURI(Uri uri); //设置视频播放uri
             * videoView.setVideoPath(String path); //设置视频播放路径
             * videoView.seekTo(int msec); //跳转到设置时间
             * videoView.getCurrentPosition(); //获取当前播放时间
             * videoView.getDuration(); //获取该视频播放时长
             * videoView.setMediaController(MediaController controller); //设置播放控制器
             * videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener listener); //加载完成监听
             * videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener listener); //设置播放完成监听
             * videoView.setOnErrorListener(MediaPlayer.OnErrorListener listener); //播放失败监听
             */
            videoView.setVideoPath(URLs.BASE_IMAGE_URL +imgListBean.getPath());
            videoView.setMediaController(new MediaController(this));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();
                }
            });
        }
    }

    public void downloadImage(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Context context = getApplicationContext();
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intensifyImageView.setImage(imageFile);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @OnClick({R.id.back,R.id.downLoad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
            case R.id.downLoad:
            {
                showProgressDialog(this, "资源保存中...");
                long taskId = Aria.download(this)
                        .load(URLs.BASE_IMAGE_URL +imgListBean.getPath())     //读取下载地址
                        .setFilePath(downLoadFilePath) //设置文件保存的完整路径
                        .create();   //创建并启动下载
            }
            break;
        }
    }

    @Override
    protected boolean applyFullScreen() {
        return true;
    }



    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning protected void running(DownloadTask task) {
//        if(task.getKey().eques(url)){
//            //可以通过url判断是否是指定任务的回调
//        }
        int p = task.getPercent();	//任务进度百分比
        String speed = task.getConvertSpeed();	//转换单位后的下载速度，单位转换需要在配置文件中打开
        long speed1 = task.getSpeed(); //原始byte长度速度

        showProgressDialog(this, "资源保存中...("+p+"%)");

    }
    @Download.onTaskComplete void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        dismissProgressDialog();
        ToastUtil.show("保存成功");
        downLoadLL.setVisibility(View.GONE);
    }

    @Download.onTaskFail void taskFail(DownloadTask task){
        dismissProgressDialog();
        ToastUtil.show("下载失败");
    }

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
}
