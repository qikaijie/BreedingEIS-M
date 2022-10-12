package net.cnbec.catering.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.AttributeValuesBean;
import net.cnbec.catering.bean.PlantHistoryRecordBean;
import net.cnbec.catering.bean.SeedlingHistoryRecordBean;
import net.cnbec.catering.ui.base.BaseLazyFragment;
import net.cnbec.catering.ui.main.activity.history.SelectTimeActivity;
import net.cnbec.catering.ui.main.contract.HistoryRecordContract;
import net.cnbec.catering.ui.main.presenter.HistoryRecordPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Describe: //TODO
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/10/26
 */

public class HistoryFragment extends BaseLazyFragment<HistoryRecordContract.View, HistoryRecordContract.Presenter> implements HistoryRecordContract.View {

    Integer businessType;
    Integer detailId;
    Integer type;

    Integer selectIndex;

    @BindView(R.id.codeTv)
    TextView codeTv;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.timeTv)
    TextView timeTv;
    @BindView(R.id.selectTimeLv)
    LinearLayout selectTimeLv;

    @BindView(R.id.emptyTv)
    TextView emptyTv;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private PJDetailsAdapter pjDetailsAdapter;

    Object dataObjectList;
    Object dataObject;
    List<AttributeValuesBean> dataList;

    public static HistoryFragment newInstance(Integer type,Integer businessType,Integer detailId) {
        return new HistoryFragment(type,businessType,detailId);
    }

    public HistoryFragment(Integer type,Integer businessType,Integer detailId){
        this.type = type;
        this.businessType = businessType;
        this.detailId = detailId;

        this.selectIndex = -1;
    }

    @Override
    protected HistoryRecordContract.Presenter initPresenter() {
        return new HistoryRecordPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_record;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        dataList = new ArrayList<>();

        pjDetailsAdapter = new PJDetailsAdapter(R.layout.item_pj_details_item_layout, dataList);
        recyclerView.setAdapter(pjDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if(businessType == 0){
            getPresenter().plantRecordHistoryList(detailId,type);
        }else{
            getPresenter().seedlingRecordHistoryList(detailId,type);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class PJDetailsAdapter extends BaseQuickAdapter<AttributeValuesBean, BaseViewHolder> {

        public PJDetailsAdapter(int layoutResId, @Nullable List<AttributeValuesBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AttributeValuesBean item) {

            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
            TextView detailsTv = ((TextView)helper.getView(R.id.detailsTv));

            titleTv.setText(item.getName());
            detailsTv.setText(item.getValue());
        }

    }

    @Override
    public void onPlantRecordHistoryListCallBack(List<PlantHistoryRecordBean> plantHistoryRecordBeanList) {
        this.dataObjectList = plantHistoryRecordBeanList;
        if(plantHistoryRecordBeanList.size()>0){
            selectIndex = 0;
            emptyTv.setVisibility(View.GONE);
            refreshPage();
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSeedlingRecordHistoryListCallBack(List<SeedlingHistoryRecordBean> seedlingHistoryRecordBeanList) {
        this.dataObjectList = seedlingHistoryRecordBeanList;
        if(seedlingHistoryRecordBeanList.size()>0){
            selectIndex = 0;
            emptyTv.setVisibility(View.GONE);
            refreshPage();
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 刷新数据
     */
    public void refreshPage(){
        dataList.clear();
        if(businessType == 0){
            // 杂交
            List<PlantHistoryRecordBean> plantHistoryRecordBeanList = (List<PlantHistoryRecordBean>)dataObjectList;
            dataObject = plantHistoryRecordBeanList.get(selectIndex);
            PlantHistoryRecordBean plantHistoryRecordBean = (PlantHistoryRecordBean)dataObject;

            codeTv.setText("条码："+plantHistoryRecordBean.getPlantCode());
            nameTv.setText("杂交资源组合："+plantHistoryRecordBean.getHybridName());
            timeTv.setText(""+plantHistoryRecordBean.getCreateTime());

            /**
             * 答案数据
             */
            String attributeValues = plantHistoryRecordBean.getAttributeValues();
            List<AttributeValuesBean> attributeValuesBeanList = new Gson().fromJson(attributeValues,new TypeToken<List<AttributeValuesBean>>(){}.getType());
            if(attributeValuesBeanList != null && attributeValuesBeanList.size()>0){
                dataList.addAll(attributeValuesBeanList);
            }
            pjDetailsAdapter.notifyDataSetChanged();
        }else{
            // 种质
            List<SeedlingHistoryRecordBean> seedlingHistoryRecordBeanList = (List<SeedlingHistoryRecordBean>)dataObjectList;
            dataObject = seedlingHistoryRecordBeanList.get(selectIndex);
            SeedlingHistoryRecordBean seedlingHistoryRecordBean = (SeedlingHistoryRecordBean)dataObject;

            codeTv.setText("条码："+seedlingHistoryRecordBean.getSeedlingCode());
            nameTv.setText("种质资源组合："+seedlingHistoryRecordBean.getGermplasmName());
            timeTv.setText(""+seedlingHistoryRecordBean.getCreateTime());

            /**
             * 答案数据
             */
            String attributeValues = seedlingHistoryRecordBean.getAttributeValues();
            List<AttributeValuesBean> attributeValuesBeanList = new Gson().fromJson(attributeValues,new TypeToken<List<AttributeValuesBean>>(){}.getType());
            if(attributeValuesBeanList != null && attributeValuesBeanList.size()>0){
                dataList.addAll(attributeValuesBeanList);
            }
            pjDetailsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 订阅者处理粘性事件
     * @param map 接手过来的实体类
     */
    public void onMoonStickyEvent(Map<String,Object> map){
        selectIndex = (Integer)map.get("index");
        refreshPage();
    }

    @OnClick({R.id.selectTimeLv,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectTimeLv:
            {
                //选择时间
                SelectTimeActivity.startActivity(getContext(), getActivity(), this.businessType,selectIndex,dataObjectList,(businessType == 0 ? ((List<PlantHistoryRecordBean>)dataObjectList).get(selectIndex) : ((List<SeedlingHistoryRecordBean>)dataObjectList).get(selectIndex) ));
            }
            break;
            case R.id.btn_next:
            {
                if(dataList != null){
                    //事件发布者发布事件
                    Map<String,Object> map = new HashMap<>();
                    map.put("option","copyData");
                    map.put((businessType == 0 ? "plantRecordHistoryInfoBeanList" : "seedlingRecordHistoryInfoBeanList"),new Gson().toJson(dataList));

                    EventBus.getDefault().postSticky(map);
                }
                getActivity().finish();

            }
            break;
        }
    }

}
