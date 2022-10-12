//package net.cnbec.catering.ui.main.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.View;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
//import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
//
//import net.cnbec.catering.R;
//import net.cnbec.catering.bean.CollectListBean;
//import net.cnbec.catering.bean.PlantInfoBean;
//import net.cnbec.catering.bean.PlantRecordBean;
//import net.cnbec.catering.ui.base.BaseActivity;
//import net.cnbec.catering.ui.main.adapter.RecordAdapter;
//import net.cnbec.catering.ui.main.contract.SCContract;
//import net.cnbec.catering.ui.main.presenter.SCPresenter;
//import net.cnbec.catering.util.ToastUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//
//public class SCActivity extends BaseActivity<SCContract.View, SCContract.Presenter> implements SCContract.View {
//
//    @BindView(R.id.title)
//    TextView title;
//
//    @BindView(R.id.emptyTv)
//    TextView emptyTv;
//
//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;
//    @BindView(R.id.refresh_layout)
//    TwinklingRefreshLayout refreshLayout;
//
//    private RecordAdapter recordAdapter;
//    private List<CollectListBean> dataList;
//
//    public static void startActivity(Context context) {
//        Intent intent = new Intent(context, SCActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_sc;
//    }
//
//
//    @Override
//    protected SCContract.Presenter initPresenter() {
//        return new SCPresenter();
//    }
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//        title.setText("我的收藏");
//
//        dataList = new ArrayList<>();
//        recordAdapter = new RecordAdapter(R.layout.item_sc_layout, dataList);
//        recyclerView.setAdapter(recordAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        recordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                getPresenter().plantInfo(dataList.get(position).getPlantCode()+"");
//                //PJHistoryRecordDetailsActivity.startActivity(SCActivity.this);
//            }
//        });
//
//        refreshLayout.setEnableRefresh(true);
//        refreshLayout.setEnableLoadmore(false);
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                super.onRefresh(refreshLayout);
//                getPresenter().collectList();
//            }
//
////            @Override
////            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
////                super.onLoadMore(refreshLayout);
////                refreshLayout.finishLoadmore();
////
////                dataList.add("");
////
////                refreshLayout.finishRefreshing();
////                recordAdapter.notifyDataSetChanged();
////            }
//        });
//        refreshLayout.startRefresh();
//    }
//
//    class RecordAdapter extends BaseQuickAdapter<CollectListBean, BaseViewHolder> {
//
//        public RecordAdapter(int layoutResId, @Nullable List<CollectListBean> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, CollectListBean item) {
//            ((TextView)helper.getView(R.id.plantIdTv)).setText(""+item.getPlantCode());
//            ((TextView)helper.getView(R.id.timeTv)).setText(item.getCreateTime()+"");
//
//        }
//
//    }
//
//    @Override
//    public void onPlantInfoCallBack(PlantInfoBean plantInfoB) {
//        if(plantInfoB != null){
//            SCListActivity.startActivity(this,plantInfoB);
//        }else{
//            ToastUtil.show("编码信息不存在");
//        }
//    }
//
//
//    @Override
//    public void onCollectListCallBack(List<CollectListBean> collectListBeanList) {
//        refreshLayout.finishRefreshing();
//        //记录详情
//        dataList.clear();
//        if(collectListBeanList!=null && collectListBeanList.size()>0){
//            dataList.addAll(collectListBeanList);
//            emptyTv.setVisibility(View.GONE);
//        }else{
//            emptyTv.setVisibility(View.VISIBLE);
//        }
//        recordAdapter.notifyDataSetChanged();
//    }
//
//
//    @OnClick({R.id.back})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.back:
//            {
//                finish();
//            }
//            break;
//        }
//    }
//
//}
