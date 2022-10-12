package net.cnbec.catering.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siberiadante.customdialoglib.EnsureDialog;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UploadImageBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;

import butterknife.BindView;
import butterknife.OnClick;
import me.kareluo.intensify.image.IntensifyImageView;

/**
 * @Describe: 测试页面，用于开发过程中测试使用，后期删掉
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class PhotoPerviewActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View {


    @BindView(R.id.intensify_image)
    IntensifyImageView intensifyImageView;

    @BindView(R.id.videoView)
    VideoView videoView;

    UploadImageBean uploadImageBean;

    public static void startActivity(Context context, UploadImageBean uploadImageBean) {
        Intent intent = new Intent(context, PhotoPerviewActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        intent.putExtra("UploadImage",new Gson().toJson(uploadImageBean));
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_perview1;
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        uploadImageBean = new Gson().fromJson(getIntent().getStringExtra("UploadImage"),new TypeToken<UploadImageBean>(){}.getType());
        if(uploadImageBean.getFileType() == 0){
            //图片
            intensifyImageView.setVisibility(View.VISIBLE);
            intensifyImageView.setImage(uploadImageBean.getImageLocalPath());
        }else if(uploadImageBean.getFileType() == 1){
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
            videoView.setVideoPath(uploadImageBean.getVideoLocalPath());
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

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
        }
    }

    @Override
    protected boolean applyFullScreen() {
        return true;
    }
}
