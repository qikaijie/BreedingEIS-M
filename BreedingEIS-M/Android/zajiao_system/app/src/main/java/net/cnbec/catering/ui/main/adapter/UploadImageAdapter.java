package net.cnbec.catering.ui.main.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UploadImageBean;
import net.cnbec.catering.constant.URLs;
import net.cnbec.catering.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UploadImageAdapter extends BaseQuickAdapter<UploadImageBean, BaseViewHolder> {

    public UploadImageAdapter(int layoutResId, @Nullable List<UploadImageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UploadImageBean item) {
        ImageView imageView = helper.getView(R.id.imageView);
        ImageView deleteBtn = helper.getView(R.id.deleteBtn);
        ImageView addBtn = helper.getView(R.id.addBtn);
        TextView image_status = helper.getView(R.id.image_status);
        ImageView playIcon = helper.getView(R.id.playIcon);

        imageView.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.GONE);
        addBtn.setVisibility(View.GONE);
        image_status.setVisibility(View.GONE);
        if(item.getUpload()){
            //已上传
            deleteBtn.setVisibility(View.VISIBLE);
            image_status.setVisibility(View.VISIBLE);
            image_status.setText("已上传");
            image_status.setTextColor(Color.GREEN);

            imageView.setVisibility(View.VISIBLE);

            /*为什么图片一定要转化为 Bitmap格式的！！ */
            Bitmap bitmap = getLoacalBitmap(item.getImageLocalPath()); //从本地取图片(在cdcard中获取)  //
            imageView.setImageBitmap(bitmap); //设置Bitmap

            //是否是视频
            playIcon.setVisibility(item.getFileType() == 0 ? View.GONE : View.VISIBLE);
        }else{
            //未上传的情况
            if(!StringUtil.isEmpty(item.getImageLocalPath())){
                //本地图片不是空的
                deleteBtn.setVisibility(View.VISIBLE);
                image_status.setVisibility(View.VISIBLE);
                image_status.setText("待上传");
                image_status.setTextColor(Color.RED);

                imageView.setVisibility(View.VISIBLE);

                /*为什么图片一定要转化为 Bitmap格式的！！ */
                Bitmap bitmap = getLoacalBitmap(item.getImageLocalPath()); //从本地取图片(在cdcard中获取)  //
                imageView.setImageBitmap(bitmap); //设置Bitmap

                //是否是视频
                playIcon.setVisibility(item.getFileType() == 0 ? View.GONE : View.VISIBLE);
            }else{
                //本地图片是空的情况，只有添加按钮
                playIcon.setVisibility(View.GONE);
                addBtn.setVisibility(View.VISIBLE);
            }
        }

        helper.addOnClickListener(R.id.deleteBtn)
                .addOnClickListener(R.id.addBtn)
                .addOnClickListener(R.id.imageView);
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从服务器取图片
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
