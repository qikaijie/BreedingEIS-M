package net.cnbec.catering.ui.main.activity.preview;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorPagerAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorViewPagerAdapter;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.PreviewImageVideoBean;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.activity.PerviewFilesActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
import net.cnbec.catering.util.ImageUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.kareluo.intensify.image.IntensifyImageView;

public class GuideActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View{
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    @BindView(R.id.guide_viewPager)
    ViewPager viewPager;
    @BindView(R.id.guide_indicator)
    Indicator indicator;

    @BindView(R.id.downLoad)
    ImageView downloadBtn;

    List<PreviewImageVideoBean> previewImageVideoBeanList;

    /**
     * ??????
     *
     * @param context
     */
    public static void startActivity(Context context, List<PreviewImageVideoBean> previewImageVideoBeanList, Integer businessType) {
        Intent intent = new Intent(context, GuideActivity.class);
        intent.putExtra("previewImageVideoBeanList",new Gson().toJson(previewImageVideoBeanList));
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        previewImageVideoBeanList = new Gson().fromJson(getIntent().getStringExtra("previewImageVideoBeanList"),new TypeToken<List<PreviewImageVideoBean>>(){}.getType());

        viewPager.setAdapter(new MyNewAdapter(this,previewImageVideoBeanList));
    }

    public class MyNewAdapter extends PagerAdapter {
        private Context mContext;
        private List<PreviewImageVideoBean> mData;

        public MyNewAdapter(Context context,List<PreviewImageVideoBean> list) {
            mContext = context;
            mData = list;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.tab_guide,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.intensify_image);
            VideoView videoView = (VideoView) view.findViewById(R.id.videoView);

            PreviewImageVideoBean previewImageVideoBean = mData.get(position);
            if(previewImageVideoBean.getType() == 0){
                //??????
                videoView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
//                intensifyImageView.setBackgroundResource(R.mipmap.login_bg);
//                downloadImage(URLs.BASE_IMAGE_URL +previewImageVideoBean.getUrl(),intensifyImageView);
                Glide.with(getBaseContext()).load(previewImageVideoBean.getUrl()).into(imageView);
            }else if(previewImageVideoBean.getType() == 1){
                //??????
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                /**
                 * videoView.start(); //????????????
                 * videoView.pause(); //??????
                 * videoView.stopPlayback(); //????????????
                 * videoView.isPlaying(); //???????????????????????????
                 * videoView.setVideoURI(Uri uri); //??????????????????uri
                 * videoView.setVideoPath(String path); //????????????????????????
                 * videoView.seekTo(int msec); //?????????????????????
                 * videoView.getCurrentPosition(); //????????????????????????
                 * videoView.getDuration(); //???????????????????????????
                 * videoView.setMediaController(MediaController controller); //?????????????????????
                 * videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener listener); //??????????????????
                 * videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener listener); //????????????????????????
                 * videoView.setOnErrorListener(MediaPlayer.OnErrorListener listener); //??????????????????
                 */
                videoView.setVideoPath(previewImageVideoBean.getVideoUrl());
                MediaController mediaController = new MediaController(mContext);
                mediaController.setVisibility(View.GONE);        //???????????????
                videoView.setMediaController(mediaController);
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
            container.addView(view);

            return view;
        }
    }
    public void downloadImage(String url,IntensifyImageView intensifyImageView) {
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

                            //Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
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

            }
            break;
        }
    }

    @Override
    protected boolean applyFullScreen() {
        return true;
    }
}