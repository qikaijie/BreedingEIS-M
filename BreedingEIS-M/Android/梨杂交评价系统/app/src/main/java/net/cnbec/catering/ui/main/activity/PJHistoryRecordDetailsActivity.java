package net.cnbec.catering.ui.main.activity;

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
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantRecordHistoryInfoBean;
import net.cnbec.catering.bean.PlantRecordHistoryInfoBean1;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.PJHistoryRecordDetailsContract;
import net.cnbec.catering.ui.main.presenter.PJHistoryRecordDetailsPresenter;
import net.cnbec.catering.util.StringUtil;
import net.cnbec.catering.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PJHistoryRecordDetailsActivity extends BaseActivity<PJHistoryRecordDetailsContract.View, PJHistoryRecordDetailsContract.Presenter> implements PJHistoryRecordDetailsContract.View {

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

    private PJDetailsAdapter pjDetailsAdapter;

    PlantInfoBean plantInfoBean;

    List<PlantRecordHistoryInfoBean> dataList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pj_history_record_details;
    }


    @Override
    protected PJHistoryRecordDetailsContract.Presenter initPresenter() {
        return new PJHistoryRecordDetailsPresenter();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        title.setText("历史记录");
        plantInfoBean = new Gson().fromJson(getIntent().getStringExtra("plantInfoBean"),new TypeToken<PlantInfoBean>(){}.getType());

        codeTv.setText("条码："+plantInfoBean.getCode());
        nameTv.setText("杂交组合："+plantInfoBean.getHybridName());
        //timeTv.setText(""+plantRecordInfoBean.getCreateTime());

        dataList = new ArrayList<>();

        pjDetailsAdapter = new PJDetailsAdapter(R.layout.item_pj_details_item_layout, dataList);
        recyclerView.setAdapter(pjDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getPresenter().plantRecordHistoryInfo(plantInfoBean.getId());
    }

    @Override
    public void onPlantRecordHistoryInfoCallBack(PlantRecordHistoryInfoBean1 plantRecordHistoryInfoBean1) {
        if(!StringUtil.isEmpty(plantRecordHistoryInfoBean1.getAttributeValues())){
            timeTv.setText(plantRecordHistoryInfoBean1.getCreateTime());
            dataList.clear();
            List<PlantRecordHistoryInfoBean> plantRecordHistoryInfoBeanList = new Gson().fromJson(plantRecordHistoryInfoBean1.getAttributeValues(),new TypeToken<List<PlantRecordHistoryInfoBean>>(){}.getType());
            if(plantRecordHistoryInfoBeanList!=null && plantRecordHistoryInfoBeanList.size()>0) {
                dataList.addAll(plantRecordHistoryInfoBeanList);
            }
            pjDetailsAdapter.notifyDataSetChanged();
        }else{
            ToastUtil.show("查询失败");
        }
    }

    class PJDetailsAdapter extends BaseQuickAdapter<PlantRecordHistoryInfoBean, BaseViewHolder> {

        public PJDetailsAdapter(int layoutResId, @Nullable List<PlantRecordHistoryInfoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PlantRecordHistoryInfoBean item) {

            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
            TextView detailsTv = ((TextView)helper.getView(R.id.detailsTv));

            titleTv.setText(item.getName());
            detailsTv.setText(item.getValue());
        }

    }


    @OnClick({R.id.back,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
                break;
            case R.id.btn_next:
            {
                if(dataList != null){
//                    copyResultListener.resultListener(plantInfoBean,plantRecordInfoBean);
                    Intent intent = getIntent();
                    //这里使用bundle绷带来传输数据
                    Bundle bundle =new Bundle();
                    //传输的内容仍然是键值对的形式
                    bundle.putString("plantRecordHistoryInfoBeanList",new Gson().toJson(dataList));//回发的消息,hello world from secondActivity!
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    finish();
                }

            }
                break;
        }
    }

    @Override
    protected boolean applyFullScreen() {
        return false;
    }
}
