//package net.cnbec.catering.ui.main.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.View;
//import android.widget.TextView;
//
//import net.cnbec.catering.R;
//import net.cnbec.catering.bean.PJDetailsBean;
//import net.cnbec.catering.bean.PJKVBean;
//import net.cnbec.catering.ui.base.BaseActivity;
//import net.cnbec.catering.ui.main.adapter.PJDetailsAdapter;
//import net.cnbec.catering.ui.main.contract.BaseContractCode;
//import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//public class PJDetailsActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View {
//
//    @BindView(R.id.title)
//    TextView title;
//
//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;
//
//    private List<PJDetailsBean> dataList;
//    private PJDetailsAdapter pjDetailsAdapter;
//
//    public static void startActivity(Context context) {
//        Intent intent = new Intent(context, PJDetailsActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_pj_details;
//    }
//
//
//    @Override
//    protected BaseContractCode.Presenter initPresenter() {
//        return new BasePresenterCode();
//    }
//
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//
//        title.setText("评价详情");
//
//        dataList = new ArrayList<>();
//
//        List<PJKVBean> maps1 = new ArrayList<>();
//        maps1.add(new PJKVBean("果肉石细胞数量","多"));
//        maps1.add(new PJKVBean("果肉硬度","软(10.1-20kg/cm以上)"));
//        maps1.add(new PJKVBean("果心大小","大(果心直径>果实直径的1/2)"));
//        maps1.add(new PJKVBean("果肉颜色","中位"));
//        maps1.add(new PJKVBean("果心位置","少"));
//        maps1.add(new PJKVBean("汁液","极多"));
//        maps1.add(new PJKVBean("香气","极多"));
//        maps1.add(new PJKVBean("果肉粗细","多"));
//        maps1.add(new PJKVBean("果肉质地","中等"));
//        dataList.add(new PJDetailsBean("内在品质",maps1));
//
//        List<PJKVBean> maps2 = new ArrayList<>();
//        maps2.add(new PJKVBean("整体态势","良好"));
//        maps2.add(new PJKVBean("整体评价","优秀"));
//        maps2.add(new PJKVBean("备注","无"));
//        dataList.add(new PJDetailsBean("综评",maps2));
//
//        pjDetailsAdapter = new PJDetailsAdapter(R.layout.item_pj_details_layout, dataList);
//        recyclerView.setAdapter(pjDetailsAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//    }
//
//    @OnClick({R.id.back,R.id.btn_next})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.back:
//            {
//                finish();
//            }
//                break;
//            case R.id.btn_next:
//            {
//                //PJFromSumbitActivity.startActivity(this);
//            }
//                break;
//        }
//    }
//
//    @Override
//    protected boolean applyFullScreen() {
//        return false;
//    }
//}
