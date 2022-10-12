package net.cnbec.catering.ui.main.activity.history;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.GermplasmListByYearBean;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.bean.PlantHistoryRecordBean;
import net.cnbec.catering.bean.SeedlingHistoryRecordBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.contract.SelectYearContract;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
import net.cnbec.catering.ui.main.presenter.SelectYearPresenter;
import net.cnbec.catering.util.StringUtil;
import net.cnbec.catering.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectTimeActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private List<Object> dataByTimeList;
    private DataAdapter dataAdapter;

    /**
     * 当前选择
     */
    private Integer businessType;
    private Integer selectIndex;
    private Object selectData;

    public static void startActivity(Context context,Activity activity,Integer businessType,Integer selectIndex,Object dataByTimeList,Object selectData) {
        Intent intent = new Intent(context, SelectTimeActivity.class);
        intent.putExtra("businessType", businessType);
        intent.putExtra("selectIndex", selectIndex);
        intent.putExtra("dataByTimeList", new Gson().toJson(dataByTimeList));
        intent.putExtra("selectData", new Gson().toJson(selectData));
        context.startActivity(intent);
        activity.overridePendingTransition(0,0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_time;
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }

    @Override
    protected void initListener() {
        super.initListener();

        Intent intent = getIntent();
        businessType = intent.getIntExtra("businessType", 0);
        selectIndex = intent.getIntExtra("selectIndex", 0);

        dataByTimeList = new ArrayList<>();
        if(businessType == 0){
            dataByTimeList = new Gson().fromJson(intent.getStringExtra("dataByTimeList"),new TypeToken<List<PlantHistoryRecordBean>>(){}.getType());
        }else{
            dataByTimeList = new Gson().fromJson(intent.getStringExtra("dataByTimeList"),new TypeToken<List<SeedlingHistoryRecordBean>>(){}.getType());
        }

        String selectDataStr = intent.getStringExtra("selectData");
        if(!StringUtil.isEmpty(selectDataStr)){
            if(businessType == 0){
                selectData = new Gson().fromJson(selectDataStr,new TypeToken<PlantHistoryRecordBean>(){}.getType());
            }else{
                selectData = new Gson().fromJson(selectDataStr,new TypeToken<SeedlingHistoryRecordBean>(){}.getType());
            }
        }

        dataAdapter = new DataAdapter(R.layout.item_year_data_layout, dataByTimeList);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectTimeActivity.this, LinearLayoutManager.VERTICAL, false));
        dataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectData = dataByTimeList.get(position);
                dataAdapter.notifyDataSetChanged();

                //事件发布者发布事件
                Map<String,Object> map = new HashMap<>();
                map.put("option","selectTime");
                map.put("index",position);
                EventBus.getDefault().postSticky(map);
                finish();
            }
        });
    }

    @OnClick({R.id.leftView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftView:
            {
                finish();
            }
            break;
        }
    }

    @Override
    protected boolean applyFullScreen() {
        return false;
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    class DataAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        public DataAdapter(int layoutResId, @Nullable List<Object> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            TextView dataLabel = (TextView)helper.getView(R.id.dataLabel);
            if(businessType == 0){
                PlantHistoryRecordBean plantHistoryRecordBean = (PlantHistoryRecordBean)item;
                dataLabel.setText(plantHistoryRecordBean.getCreateTime()+"");
                if(selectData != null){
                    PlantHistoryRecordBean selectDataNow = (PlantHistoryRecordBean)selectData;
                    if((int)plantHistoryRecordBean.getId() != (int)selectDataNow.getId()){
                        dataLabel.setTextColor(getResources().getColor(R.color.gray));
                        dataLabel.setBackgroundResource(R.drawable.radio_button_style_n);
                    }else{
                        dataLabel.setTextColor(getResources().getColor(R.color.forestgreen));
                        dataLabel.setBackgroundResource(R.drawable.radio_button_style_s);
                    }
                }else{
                    dataLabel.setTextColor(getResources().getColor(R.color.gray));
                    dataLabel.setBackgroundResource(R.drawable.radio_button_style_n);
                }
            }else{
                SeedlingHistoryRecordBean seedlingHistoryRecordBean = (SeedlingHistoryRecordBean)item;
                dataLabel.setText(seedlingHistoryRecordBean.getCreateTime()+"");
                if(selectData != null){
                    SeedlingHistoryRecordBean selectDataNow = (SeedlingHistoryRecordBean)selectData;
                    if((int)seedlingHistoryRecordBean.getId() != (int)selectDataNow.getId()){
                        dataLabel.setTextColor(getResources().getColor(R.color.gray));
                        dataLabel.setBackgroundResource(R.drawable.radio_button_style_n);
                    }else{
                        dataLabel.setTextColor(getResources().getColor(R.color.forestgreen));
                        dataLabel.setBackgroundResource(R.drawable.radio_button_style_s);
                    }
                }else{
                    dataLabel.setTextColor(getResources().getColor(R.color.gray));
                    dataLabel.setBackgroundResource(R.drawable.radio_button_style_n);
                }
            }
        }
    }
}
