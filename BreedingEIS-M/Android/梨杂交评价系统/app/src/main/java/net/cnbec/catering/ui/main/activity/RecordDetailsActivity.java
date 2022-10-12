package net.cnbec.catering.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.PlantRecordDelBean;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.bean.SeedlingRecordInfoBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.RecordDetailsContract;
import net.cnbec.catering.ui.main.presenter.RecordDetailsPresenter;
import net.cnbec.catering.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordDetailsActivity extends BaseActivity<RecordDetailsContract.View, RecordDetailsContract.Presenter> implements RecordDetailsContract.View {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.codeTv)
    TextView codeTv;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.timeTv)
    TextView timeTv;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private List<Object> dataList;
    private PJDetailsAdapter pjDetailsAdapter;

    Object recordBean;
    Object recordInfoBean;

    @BindView(R.id.imageLayout)
    LinearLayout imageLayout;

    Integer businessType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_details;
    }

    public static void startActivity(Context context,Object object,Integer businessType) {
        Intent intent = new Intent(context, RecordDetailsActivity.class);
        intent.putExtra("infoBean",new Gson().toJson(object));
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected RecordDetailsContract.Presenter initPresenter() {
        return new RecordDetailsPresenter();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        title.setText("记录详情");
        businessType = getIntent().getIntExtra("businessType",0);
        if(businessType == 0){
            recordBean = new Gson().fromJson(getIntent().getStringExtra("infoBean"),new TypeToken<PlantRecordBean>(){}.getType());
        }else{
            recordBean = new Gson().fromJson(getIntent().getStringExtra("infoBean"),new TypeToken<SeedlingRecordBean>(){}.getType());
        }

        dataList = new ArrayList<>();

        pjDetailsAdapter = new PJDetailsAdapter(R.layout.item_pj_details_item_layout, dataList);
        recyclerView.setAdapter(pjDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if(businessType == 0){
            getPresenter().plantRecordInfo(((PlantRecordBean)recordBean).getId());
        }else{
            getPresenter().seedlingRecordInfo(((SeedlingRecordBean)recordBean).getId());
        }
    }

    @Override
    public void onPlantRecordInfoCallBack(PlantRecordInfoBean plantRecordInfoBean) {
        this.recordInfoBean = plantRecordInfoBean;
        if(plantRecordInfoBean!=null){
            codeTv.setText("条码："+((PlantRecordBean)recordBean).getPlantCode());
            nameTv.setText("杂交资源组合："+((PlantRecordBean)recordBean).getHybridName());
            timeTv.setText(""+plantRecordInfoBean.getCreateTime());

            dataList.clear();
            if(plantRecordInfoBean.getLogList()!=null && plantRecordInfoBean.getLogList().size()>0) {
                dataList.addAll(plantRecordInfoBean.getLogList());
            }
            pjDetailsAdapter.notifyDataSetChanged();

            if(plantRecordInfoBean.getImgList()!=null && plantRecordInfoBean.getImgList().size()>0) {
                imageLayout.setVisibility(View.VISIBLE);
            }else{
                imageLayout.setVisibility(View.GONE);
            }

        }else{
            ToastUtil.show("查询失败");
        }
    }

    @Override
    public void onSeedlingRecordInfoCallBack(SeedlingRecordInfoBean seedlingRecordInfoBean) {
        this.recordInfoBean = seedlingRecordInfoBean;
        if(seedlingRecordInfoBean!=null){
            codeTv.setText("条码："+((SeedlingRecordBean)recordBean).getSeedlingCode());
            nameTv.setText("种质资源组合："+((SeedlingRecordBean)recordBean).getGermplasmName());
            timeTv.setText(""+seedlingRecordInfoBean.getCreateTime());

            dataList.clear();
            if(seedlingRecordInfoBean.getLogList()!=null && seedlingRecordInfoBean.getLogList().size()>0) {
                dataList.addAll(seedlingRecordInfoBean.getLogList());
            }
            pjDetailsAdapter.notifyDataSetChanged();

            if(seedlingRecordInfoBean.getImgList()!=null && seedlingRecordInfoBean.getImgList().size()>0) {
                imageLayout.setVisibility(View.VISIBLE);
            }else{
                imageLayout.setVisibility(View.GONE);
            }

        }else{
            ToastUtil.show("查询失败");
        }
    }

    class PJDetailsAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        public PJDetailsAdapter(int layoutResId, @Nullable List<Object> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

            if(businessType == 0){
                PlantRecordInfoBean.LogListBean logListBean = (PlantRecordInfoBean.LogListBean)item;

                TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
                TextView detailsTv = ((TextView)helper.getView(R.id.detailsTv));

                titleTv.setText(logListBean.getAttributeName());
                detailsTv.setText(logListBean.getAttributeValue());
            }else{
                SeedlingRecordInfoBean.LogListBean logListBean = (SeedlingRecordInfoBean.LogListBean)item;

                TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
                TextView detailsTv = ((TextView)helper.getView(R.id.detailsTv));

                titleTv.setText(logListBean.getAttributeName());
                detailsTv.setText(logListBean.getAttributeValue());
            }
        }

    }


    @OnClick({R.id.back,R.id.btn_next,R.id.perviewBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
                break;
            case R.id.btn_next:
            {
                if(businessType == 0){
                    getPresenter().plantRecordDel(((PlantRecordBean)recordBean).getId());
                }else{
                    getPresenter().seedlingRecordDel(((SeedlingRecordBean)recordBean).getId());
                }
            }
                break;
            case R.id.perviewBtn:
            {
                //看文件
                PerviewFilesActivity.startActivity(this,(businessType == 0 ? ((PlantRecordInfoBean)recordInfoBean).getImgList() : ((SeedlingRecordInfoBean)recordInfoBean).getImgList()),businessType);
            }
                break;
        }
    }

    @Override
    public void onPlantRecordDelCallBack(PlantRecordDelBean plantRecordDelBean) {
        ToastUtil.show("删除成功！");
        finish();
    }

    @Override
    public void onSeedlingRecordDelCallBack(Object object) {
        ToastUtil.show("删除成功！");
        finish();
    }

    @Override
    protected boolean applyFullScreen() {
        return false;
    }
}
