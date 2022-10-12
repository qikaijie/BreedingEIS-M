package net.cnbec.catering.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.siberiadante.customdialoglib.EnsureDialog;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.CollectListBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingCollectListBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.SCListContract;
import net.cnbec.catering.ui.main.presenter.SCListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SCListActivity extends BaseActivity<SCListContract.View, SCListContract.Presenter> implements SCListContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more_tv)
    TextView more_tv;
    @BindView(R.id.emptyTv)
    TextView emptyTv;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private ObjectRecordAdapter recordAdapter;
    private List<Object> dataList;

    Integer businessType;
    Object infoBean;

    EnsureDialog ensureDialog;

    public static void startActivity(Context context,Object infoBean,Integer businessType) {
        Intent intent = new Intent(context, SCListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        intent.putExtra("infoBean",new Gson().toJson(infoBean));
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sc;
    }


    @Override
    protected SCListContract.Presenter initPresenter() {
        return new SCListPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        title.setText("填报记录");

        businessType = getIntent().getIntExtra("businessType",0);
        if(businessType == 0){
            infoBean = new Gson().fromJson(getIntent().getStringExtra("infoBean"),new TypeToken<PlantInfoBean>(){}.getType());
        }else{
            infoBean = new Gson().fromJson(getIntent().getStringExtra("infoBean"),new TypeToken<SeedlingInfoBean>(){}.getType());
        }

        dataList = new ArrayList<>();
        recordAdapter = new ObjectRecordAdapter(R.layout.item_record_layout, dataList);
        recyclerView.setAdapter(recordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecordDetailsActivity.startActivity(SCListActivity.this,dataList.get(position),businessType);
            }
        });

        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if(businessType == 0){
                    getPresenter().plantRecordList(null,null,(infoBean!=null ? ((PlantInfoBean)infoBean).getId() : null));
                }else{
                    getPresenter().seedlingRecordList(null,null,(infoBean!=null ? ((SeedlingInfoBean)infoBean).getId() : null));
                }
            }

//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                refreshLayout.finishLoadmore();
//
//                dataList.add("");
//
//                refreshLayout.finishRefreshing();
//                recordAdapter.notifyDataSetChanged();
//            }
        });
        refreshLayout.startRefresh();
    }

    @Override
    public void onPlantRecordListCallBack(List<PlantRecordBean> plantRecordBeans) {
        refreshLayout.finishRefreshing();
        //记录详情
        dataList.clear();
        if(plantRecordBeans!=null && plantRecordBeans.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataList.addAll(plantRecordBeans);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        recordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSeedlingRecordListCallBack(List<SeedlingRecordBean> seedlingRecordBeans) {
        refreshLayout.finishRefreshing();
        //记录详情
        dataList.clear();
        if(seedlingRecordBeans!=null && seedlingRecordBeans.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataList.addAll(seedlingRecordBeans);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        recordAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.back,R.id.more_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
            case R.id.more_tv:
            {
                //取消收藏
                ensureDialog = new EnsureDialog(this).builder()
                        .setGravity(Gravity.CENTER)//默认居中，可以不设置
                        .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
                        .setCancelable(false)
                        .setSubTitle("确定是否取消收藏？")
                        .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setPositiveButton("确定", getResources().getColor(R.color.red), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(businessType == 0){
                                    getPresenter().plantCollectDel(((PlantInfoBean)infoBean).getCollectId());
                                }else{
                                    getPresenter().seedlingCollectDel(((SeedlingInfoBean)infoBean).getCollectId());
                                }
                            }
                        });
                ensureDialog.show();
            }
                break;
        }
    }

    class ObjectRecordAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
        public ObjectRecordAdapter(int layoutResId, @Nullable List<Object> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            if(businessType == 0){
                PlantRecordBean plantRecordBean = (PlantRecordBean)item;
                ((TextView)helper.getView(R.id.plantIdTv)).setText(plantRecordBean.getPlantCode());
                ((TextView)helper.getView(R.id.nameTv)).setText(plantRecordBean.getHybridName()+"");
                ((TextView)helper.getView(R.id.timeTv)).setText(plantRecordBean.getCreateTime()+"");
            }else{
                SeedlingRecordBean seedlingRecordBean = (SeedlingRecordBean)item;
                ((TextView)helper.getView(R.id.plantIdTv)).setText(seedlingRecordBean.getSeedlingCode());
                ((TextView)helper.getView(R.id.nameTv)).setText(seedlingRecordBean.getGermplasmName()+"");
                ((TextView)helper.getView(R.id.timeTv)).setText(seedlingRecordBean.getCreateTime()+"");
            }
        }
    }

    @Override
    public void onPlantCollectDelCallBack(Object o) {
        finish();
    }

    @Override
    public void onSeedlingCollectDelCallBack(Object o) {
        finish();
    }
}
