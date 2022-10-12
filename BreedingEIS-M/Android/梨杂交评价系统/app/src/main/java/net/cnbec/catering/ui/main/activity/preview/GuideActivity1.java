package net.cnbec.catering.ui.main.activity.preview;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PreviewImageVideoBean;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.kareluo.intensify.image.IntensifyImageView;

public class GuideActivity1 extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View, ViewPager.OnPageChangeListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
        MediaPlayer.OnCompletionListener {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    @BindView(R.id.guide_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.guide_indicator)
    Indicator indicator;

    List<PreviewImageVideoBean> previewImageVideoBeanList;

    MyPagerAdapter mAdapter;

    /*** viewpager的根视图数据集合 ***/
    List<ViewGroup> mViewList;

    /*** 当前页面索引 ***/
    int currentItem = 0;

    /*** 上一个页面索引 ***/
    int lastItem = 0;

    /*** 页面的视频控件集合 ***/
    static List<VideoView> mVideoViewList;

    /*** 页面播放进度控制器集合 ***/
    static List<MediaController> mMediaControllerList;

    /*** 页面视频缓冲图集合 ***/
//    static List<View> mCacheViewList;

    /*** 记录每个page页面视频播放的进度 ***/
    static Map<Integer, Integer> mCurrentPositions;

    /*** 记录每个page页面的视频播放状态 ***/
    static Map<Integer, Boolean> mIsPlaying;

    /**
     * 跳转
     *
     * @param context
     */
    public static void startActivity(Context context, List<PreviewImageVideoBean> previewImageVideoBeanList, Integer businessType) {
        Intent intent = new Intent(context, GuideActivity1.class);
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

        initializeData();
    }

    private void initializeData() {
        mViewList = new ArrayList<ViewGroup>();
        mVideoViewList = new ArrayList<VideoView>();
        mMediaControllerList = new ArrayList<MediaController>();
//        mCacheViewList = new ArrayList<View>();

        mCurrentPositions = new HashMap<Integer, Integer>();
        mIsPlaying = new HashMap<Integer, Boolean>();
        // mIsPageFirstAvaliable = new HashMap<Integer, Boolean>();

        for (int i = 0; i < previewImageVideoBeanList.size(); i++) {
            ViewGroup v = (ViewGroup) View.inflate(this, R.layout.tab_guide,
                    null);
            VideoView vv = (VideoView) v.findViewById(R.id.videoView);
            ImageView mView = (ImageView) v.findViewById(R.id.intensify_image);

            MediaController mpc = new MediaController(this);
            if(previewImageVideoBeanList.get(i).getType() == 0){
//                vv.setVideoURI(Uri.parse(previewImageVideoBeanList.get(i).getUrl()+"")); // 网络视频
                Glide.with(getBaseContext()).load(previewImageVideoBeanList.get(i).getUrl()).into(mView);
                vv.setVideoURI(Uri.parse(previewImageVideoBeanList.get(i).getUrl()+"")); // 网络视频vv.setMediaController(mpc);
                mView.setVisibility(View.VISIBLE);
                vv.setVisibility(View.GONE);
            }else{
                vv.setVideoURI(Uri.parse(previewImageVideoBeanList.get(i).getVideoUrl()+"")); // 网络视频vv.setMediaController(mpc);
                mView.setVisibility(View.GONE);
                vv.setVisibility(View.VISIBLE);
            }
            setListener(vv);
            mViewList.add(v);
            mVideoViewList.add(vv);
            mMediaControllerList.add(mpc);
//            mCacheViewList.add(mView);
            mCurrentPositions.put(i, 0);// 每个页面的初始播放进度为0
            mIsPlaying.put(i, true);// 每个页面的初始播放状态false
            // mIsPageFirstAvaliable.put(i, true);// 每个页面默认第一次
        }
        mAdapter = new MyPagerAdapter(this, mViewList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        if (mVideoViewList.get(0) != null) {
            mVideoViewList.get(0).start();
            mIsPlaying.put(0, true);
            // mIsPageFirstAvaliable.put(0, false);
        }
    }

    private void setListener(VideoView vv) {
        vv.setOnInfoListener(this);
        vv.setOnCompletionListener(this);
        vv.setOnErrorListener(this);
        vv.setOnPreparedListener(this);
    }

    class MyPagerAdapter extends PagerAdapter {

        Context context;
        List<ViewGroup> mViewList;

        public MyPagerAdapter(Context context, List<ViewGroup> list) {
            this.context = context;
            this.mViewList = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViewList != null ? mViewList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == (View) arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            // super.destroyItem(container, position, object);
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int pageIndex) {
        lastItem = currentItem;
        currentItem = pageIndex;

        // Log.e(Tag, "currentItem:  " + currentItem);
        if (previewImageVideoBeanList.get(lastItem).getType() == 1) {
//            if (mVideoViewList.get(lastItem) != null) {
            if (mVideoViewList.get(lastItem).isPlaying()) {
                mIsPlaying.put(lastItem, true);// 记录播放状态
                mVideoViewList.get(lastItem).pause();
            } else {
                mIsPlaying.put(lastItem, false);// 记录播放状态
            }
            mCurrentPositions.put(lastItem, mVideoViewList.get(lastItem)
                    .getCurrentPosition());// 记录播放进度
        }

        if (previewImageVideoBeanList.get(lastItem).getType() == 1) {
            if (mMediaControllerList.get(lastItem).isShowing()) {
                mMediaControllerList.get(lastItem)
                        .setVisibility(View.INVISIBLE);
            }
        }

        if (previewImageVideoBeanList.get(lastItem).getType() == 1) {
            // 如果页面第一次加载
            // if (mIsPageFirstAvaliable.get(currentItem)) {
            // mIsPlaying.put(currentItem, true);
            // mIsPageFirstAvaliable.put(currentItem, false);
            // } else {
            // 不是第一次加载跳到当前播放进度
            mVideoViewList.get(currentItem).seekTo(
                    mCurrentPositions.get(currentItem));
            // }
            if (mIsPlaying.get(currentItem)) {
                mVideoViewList.get(currentItem).start();
            }
            mMediaControllerList.get(currentItem).show(1000);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mVideoViewList.get(currentItem) != null) {
            if (mVideoViewList.get(currentItem).isPlaying()) {
                mIsPlaying.put(currentItem, true);
                mVideoViewList.get(currentItem).pause();
            } else {
                mIsPlaying.put(currentItem, false);
            }
            mCurrentPositions.put(currentItem, mVideoViewList.get(currentItem)
                    .getCurrentPosition());
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mVideoViewList.get(currentItem) != null) {
            mVideoViewList.get(currentItem).stopPlayback();
            mVideoViewList.get(currentItem).suspend();
        }
        clearList();
        Log.e("GuideActivity1", "onDestroy-------->");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (mVideoViewList.get(currentItem) != null) {
            mVideoViewList.get(currentItem).seekTo(
                    mCurrentPositions.get(currentItem));
            if (mIsPlaying.get(currentItem)) {
                mVideoViewList.get(currentItem).start();
            }
        }

    }


    @Override
//播放完成监听：
    public void onCompletion(MediaPlayer mp) {
        if (mVideoViewList.get(currentItem) != null) {
            mCurrentPositions.put(currentItem, 0);// 重新播放，进度重置为0
            mIsPlaying.put(currentItem, true);// 播放状态：正在播放
            mVideoViewList.get(currentItem).resume();
            mVideoViewList.get(currentItem).start();
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
//            if (mCacheViewList.get(currentItem) != null) {
//                mCacheViewList.get(currentItem).setVisibility(View.VISIBLE);
//            }
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            // 此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
            if (mp.isPlaying()) {
//                if (mCacheViewList.get(currentItem) != null) {
//                    mCacheViewList.get(currentItem).setVisibility(
//                            View.INVISIBLE);
//                }
            }
        }
        return true;
    }

    //播放出错监听
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        // 这里设置为true防止弹出对话框，屏蔽原始出错的处理
        return true;
    }

    //准备完成监听
    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub

//        if (mCacheViewList.get(currentItem) != null) {
//            mCacheViewList.get(currentItem).setVisibility(View.INVISIBLE);
//        }
        if (mp.isPlaying()) {
            // Log.e(Tag, "mp.isPlaying()");
        }
    }

    /**
     * 清空static数据
     */
    private void clearList() {
        mVideoViewList.clear();
        mMediaControllerList.clear();
//        mCacheViewList.clear();
        mCurrentPositions.clear();
        mIsPlaying.clear();
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