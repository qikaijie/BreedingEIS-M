package net.cnbec.catering.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.task.DownloadTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.loader.VideoClickListener;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UserViewInfo;
import net.cnbec.catering.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoViewActivity extends Activity {
    private ArrayList<UserViewInfo> mThumbViewInfoList = new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        init();
    }
    //初始化数据和控件
    private void init(){
        //准备数据
//        List<String> urls = ImageUrlConfig.getUrls();
//        mThumbViewInfoList.addAll(ImageUrlConfig.getVideos());
//        for (int i = 0; i < 12; i++) {
//            mThumbViewInfoList.add(new UserViewInfo(urls.get(i)));
//        }
//        mThumbViewInfoList.add(new UserViewInfo("http://img.soogif.com/GB8rgsM1m7FYYRA8q7Xyy6FxtqLBEphz.gif"));
//        mThumbViewInfoList.add(new UserViewInfo("https://hellorfimg.zcool.cn/preview260/1375630694.jpg"));
//        mThumbViewInfoList.addAll(ImageUrlConfig.getVideos());
//        mThumbViewInfoList.addAll(ImageUrlConfig.getGifUrls());

        mRecyclerView = findViewById(R.id.recycler_view);
        mGridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        MyBaseQuickAdapter adapter=new MyBaseQuickAdapter(this);
        adapter.addData(mThumbViewInfoList);
        mRecyclerView.setAdapter(adapter);
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
               GPreviewBuilder.from(VideoViewActivity.this)
                       .setData(mThumbViewInfoList)
                       .setUserFragment(UserFragment.class)
                       .setCurrentIndex(position)
                       .setSingleFling(true)
                    /*   .setOnVideoPlayerListener(new VideoClickListener(){

                           @Override
                           public void onPlayerVideo(String url) {
                               Log.d("onPlayerVideo",url);
                               Intent intent=new Intent(VideoViewActivity.this,VideoPlayerDetailedActivity.class);
                               intent.putExtra("url",url);
                               startActivity(intent);
                           }
                       })*/
                       .setType(GPreviewBuilder.IndicatorType.Number)
                       .start();
           }
       });


    }


    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos;i < mThumbViewInfoList.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView =  itemView.findViewById(R.id.iv);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }
}
