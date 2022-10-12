package net.cnbec.catering.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.task.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.loader.VideoClickListener;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.PreviewImageVideoBean;
import net.cnbec.catering.bean.SeedlingRecordInfoBean;
import net.cnbec.catering.bean.UserViewInfo;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.activity.preview.GuideActivity;
import net.cnbec.catering.ui.main.activity.preview.GuideActivity1;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
import net.cnbec.catering.util.ToastUtil;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PerviewFilesActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View{

    @BindView(R.id.title)
    TextView titleTv;

    //已上传
//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;

//    private PerviewImageAdapter uploadedAdapter;
    private List<PlantRecordInfoBean.ImgListBean> uploadedDataList = new ArrayList<>();

//    private PerviewImageAdapter1 uploadedAdapter1;
    private List<SeedlingRecordInfoBean.ImgListBean> uploadedDataList1 = new ArrayList<>();

    Integer businessType;

    @BindView(R.id.listView)
    GridView listView;
    private MyListAdapter adapter;
    private ArrayList<UserViewInfo> mThumbViewInfoList = new ArrayList<>();

    /**
     * 跳转
     *
     * @param context
     */
    public static void startActivity(Context context,Object imgListBeanList,Integer businessType) {
        Intent intent = new Intent(context, PerviewFilesActivity.class);
        intent.putExtra("imgListBeanList",new Gson().toJson(imgListBeanList));
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perview_files_layout;
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        titleTv.setText("附件信息");

        businessType = getIntent().getIntExtra("businessType",0);

        String imgListBeanListStr = getIntent().getStringExtra("imgListBeanList");
        if(businessType == 0){
            uploadedDataList.addAll(new Gson().fromJson(imgListBeanListStr,new TypeToken<List<PlantRecordInfoBean.ImgListBean>>(){}.getType()));
//            uploadedAdapter = new PerviewImageAdapter(R.layout.item_perview_files_item_layout, uploadedDataList);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(PerviewFilesActivity.this,3, RecyclerView.HORIZONTAL,false);
//            recyclerView.setLayoutManager(gridLayoutManager);
//            recyclerView.setAdapter(uploadedAdapter);
//            recyclerView.setItemAnimator (new DefaultItemAnimator());
//            uploadedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    //PhotoPerviewActivity1.startActivity(PerviewFilesActivity.this,uploadedDataList.get(position),businessType);
//                    List<PreviewImageVideoBean> previewImageVideoBeanList = new ArrayList<>();
//                    for (int i = 0; i < uploadedDataList.size(); i++) {
//                        PlantRecordInfoBean.ImgListBean imgListBean = uploadedDataList.get(i);
//
//                        if(imgListBean.getType() == 0){
//                            previewImageVideoBeanList.add(new PreviewImageVideoBean(0,URLs.BASE_IMAGE_URL+imgListBean.getPath()));
//                        }else{
//                            previewImageVideoBeanList.add(new PreviewImageVideoBean(1,URLs.BASE_IMAGE_URL+imgListBean.getPath(),""));
//                        }
//                    }
////                    GuideActivity.startActivity(PerviewFilesActivity.this,previewImageVideoBeanList,businessType);
//                    PhotoPerviewActivity1.startActivity(PerviewFilesActivity.this,uploadedDataList.get(position),businessType);
//                }
//            });
        }else{
            uploadedDataList1.addAll(new Gson().fromJson(imgListBeanListStr,new TypeToken<List<SeedlingRecordInfoBean.ImgListBean>>(){}.getType()));
//            uploadedAdapter1 = new PerviewImageAdapter1(R.layout.item_perview_files_item_layout, uploadedDataList1);
//            recyclerView.setLayoutManager(new GridLayoutManager(PerviewFilesActivity.this,3, RecyclerView.HORIZONTAL,false));
//            recyclerView.setAdapter(uploadedAdapter1);
//            recyclerView.setItemAnimator (new DefaultItemAnimator());
//            uploadedAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    PhotoPerviewActivity1.startActivity(PerviewFilesActivity.this,uploadedDataList1.get(position),businessType);
//                }
//            });
        }


        //准备数据
        if(uploadedDataList.size()>0){
            for (int i = 0; i < uploadedDataList.size(); i++) {
                if(uploadedDataList.get(i).getType() == 0){
                    mThumbViewInfoList.add(new UserViewInfo(URLs.BASE_IMAGE_URL+uploadedDataList.get(i).getPath()));
                }else{
                    mThumbViewInfoList.add(new UserViewInfo(URLs.BASE_IMAGE_URL+uploadedDataList.get(i).getPath(),URLs.BASE_IMAGE_URL+uploadedDataList.get(i).getPath()));
                }
            }
        }else if(uploadedDataList1.size()>0){
            for (int i = 0; i < uploadedDataList1.size(); i++) {
                if(uploadedDataList1.get(i).getType() == 0){
                    mThumbViewInfoList.add(new UserViewInfo(URLs.BASE_IMAGE_URL+uploadedDataList1.get(i).getPath()));
                }else{
                    mThumbViewInfoList.add(new UserViewInfo(URLs.BASE_IMAGE_URL+uploadedDataList1.get(i).getPath(),URLs.BASE_IMAGE_URL+uploadedDataList1.get(i).getPath()));
                }
            }
        }

        adapter = new MyListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                computeBoundsBackward(listView.getFirstVisiblePosition());
//                GPreviewBuilder.from(PerviewFilesActivity.this)
//                        .setData(mThumbViewInfoList)
//                        .setCurrentIndex(position)
//                        .setType(GPreviewBuilder.IndicatorType.Dot)
//                        .start();
                GPreviewBuilder.from(PerviewFilesActivity.this)
                        .setData(mThumbViewInfoList)
                        .setUserFragment(UserFragment.class)
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setOnVideoPlayerListener(new VideoClickListener(){
                               @Override
                               public void onPlayerVideo(String url) {
                                   Log.d("onPlayerVideo",url);
                                   PhotoPerviewActivity1.startActivity(PerviewFilesActivity.this,businessType == 0 ? uploadedDataList.get(position) : uploadedDataList1.get(position),businessType);
//                                   Intent intent=new Intent(VideoViewActivity.this,VideoPlayerDetailedActivity.class);
//                                   intent.putExtra("url",url);
//                                   startActivity(intent);
                               }
                           })
                        .setType(GPreviewBuilder.IndicatorType.Number)
                        .start();

            }
        });
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
    protected boolean applyImmersionBar() {
        return true;
    }

//    class PerviewImageAdapter extends BaseQuickAdapter<PlantRecordInfoBean.ImgListBean, BaseViewHolder> {
//
//        public PerviewImageAdapter(int layoutResId, @Nullable List<PlantRecordInfoBean.ImgListBean> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, PlantRecordInfoBean.ImgListBean imgListBean) {
//            ImageView imageView = helper.getView(R.id.imageView);
//            ImageView playIcon = helper.getView(R.id.playIcon);
//            /*为什么图片一定要转化为 Bitmap格式的！！ */
////            Bitmap bitmap = getLoacalBitmap(item.getPath()); //从本地取图片(在cdcard中获取)  //
////            imageView.setImageBitmap(bitmap); //设置Bitmap
//            if(imgListBean.getType() == 0){
//                Glide.with(getBaseContext()).load(URLs.BASE_IMAGE_URL+imgListBean.getPath()).into(imageView);
//            }else{
//                loadVideoScreenshot(getBaseContext(),URLs.BASE_IMAGE_URL+imgListBean.getPath(),imageView,1);
//            }
//            //是否是视频
//            playIcon.setVisibility(imgListBean.getType() == 0 ? View.GONE : View.VISIBLE);
//        }
//    }
//
//    class PerviewImageAdapter1 extends BaseQuickAdapter<SeedlingRecordInfoBean.ImgListBean, BaseViewHolder> {
//
//        public PerviewImageAdapter1(int layoutResId, @Nullable List<SeedlingRecordInfoBean.ImgListBean> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, SeedlingRecordInfoBean.ImgListBean imgListBean) {
//            ImageView imageView = helper.getView(R.id.imageView);
//            ImageView playIcon = helper.getView(R.id.playIcon);
//            /*为什么图片一定要转化为 Bitmap格式的！！ */
////            Bitmap bitmap = getLoacalBitmap(item.getPath()); //从本地取图片(在cdcard中获取)  //
////            imageView.setImageBitmap(bitmap); //设置Bitmap
//            if(imgListBean.getType() == 0){
//                Glide.with(getBaseContext()).load(URLs.BASE_IMAGE_URL+imgListBean.getPath()).into(imageView);
//            }else{
//                loadVideoScreenshot(getBaseContext(),URLs.BASE_IMAGE_URL+imgListBean.getPath(),imageView,1);
//            }
//            //是否是视频
//            playIcon.setVisibility(imgListBean.getType() == 0 ? View.GONE : View.VISIBLE);
//        }
//    }
//
//    public void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros) {
//        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
//        requestOptions.set(VideoDecoder.FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
//        requestOptions.transform(new BitmapTransformation() {
//            @Override
//            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
//                return toTransform;
//            }
//            @Override
//            public void updateDiskCacheKey(MessageDigest messageDigest) {
//                try {
//                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
//    }

    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = listView.getChildAt(i - firstCompletelyVisiblePos);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.iv);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mThumbViewInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return mThumbViewInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_image, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            Glide.with(PerviewFilesActivity.this)
                    .load(mThumbViewInfoList.get(position).getUrl())
//                    .error(R.mipmap.ic_iamge_zhanwei)
                    .into(iv);
            iv.setTag(R.id.iv, mThumbViewInfoList.get(position));
            return view;
        }
    }


}
