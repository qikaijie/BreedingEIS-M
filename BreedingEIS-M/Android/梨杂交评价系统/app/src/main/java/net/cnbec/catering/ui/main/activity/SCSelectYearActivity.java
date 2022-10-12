package net.cnbec.catering.ui.main.activity;

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
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.SelectYearContract;
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

public class SCSelectYearActivity extends BaseActivity<SelectYearContract.View, SelectYearContract.Presenter> implements SelectYearContract.View {

    @BindView(R.id.selectYearLabel)
    Button selectYearLabel;
    @BindView(R.id.emptyTv)
    TextView emptyTv;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_sumbit)
    Button btn_sumbit;

    @BindView(R.id.leftView)
    View leftView;
    @BindView(R.id.topView)
    View topView;

    private List<String> options1Items = new ArrayList<>();
    private int selectYearIndex;

    private List<Object> dataByYearList;
    private DataAdapter dataAdapter;

    /**
     * 当前选择
     */
    private Integer businessType;
    private Integer selectYear;
    private Object selectData;

    public static void startActivity(Context context,Activity activity,Integer businessType,Integer selectYear,Object selectData) {
        Intent intent = new Intent(context, SCSelectYearActivity.class);
        intent.putExtra("businessType", businessType);
        if(selectYear != null){
            intent.putExtra("selectYear", selectYear);
        }
        if(selectData != null){
            intent.putExtra("selectData", new Gson().toJson(selectData));
        }
        context.startActivity(intent);
        activity.overridePendingTransition(0,0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_year;
    }

    @Override
    protected SelectYearContract.Presenter initPresenter() {
        return new SelectYearPresenter();
    }

    @Override
    protected void initListener() {
        super.initListener();

        Intent intent = getIntent();
        businessType = intent.getIntExtra("businessType", 0);
        selectYear = intent.getIntExtra("selectYear", 0);

        String selectDataStr = intent.getStringExtra("selectData");
        if(!StringUtil.isEmpty(selectDataStr)){
            if(businessType == 0){
                selectData = new Gson().fromJson(selectDataStr,new TypeToken<HybridListByYearBean>(){}.getType());
            }else{
                selectData = new Gson().fromJson(selectDataStr,new TypeToken<GermplasmListByYearBean>(){}.getType());
            }
        }

        dataByYearList = new ArrayList<>();
        dataAdapter = new DataAdapter(R.layout.item_year_data_layout, dataByYearList);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SCSelectYearActivity.this, LinearLayoutManager.VERTICAL, false));
        dataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectData = dataByYearList.get(position);
                dataAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 时间初始化
         */
        selectYearIndex = 0;
        Calendar cd = Calendar.getInstance();
        int toyear = cd.get(Calendar.YEAR);
        for (int i = 0; i > 2000-toyear; i--) {
            options1Items.add((toyear+i)+"");
            if(toyear+i == selectYear){
                selectYearIndex = -1*i;
            }
        }
        selectYearLabel.setText(selectYearIndex>0 ? selectYear+"" : toyear+"");

        refreshData();
    }

    public void refreshData(){
        if(businessType == 0){
            getPresenter().hybridListByYear(options1Items.get(selectYearIndex));
        }else{
            getPresenter().germplasmListByYear(options1Items.get(selectYearIndex));
        }
    }

    @OnClick({R.id.btn_cancel,R.id.leftView,R.id.topView,R.id.btn_sumbit,R.id.selectYearLabel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
            case R.id.topView:
            case R.id.leftView:
            {
                finish();
            }
            break;
            case R.id.btn_sumbit:
            {
                if(selectData != null){
                    //事件发布者发布事件
                    Map<String,Object> map = new HashMap<>();
                    map.put("option","scSelectYear");
                    map.put("year",Integer.valueOf(options1Items.get(selectYearIndex)));
                    map.put("data",selectData);
                    EventBus.getDefault().postSticky(map);
                    finish();
                }else{
                    ToastUtil.show("请先选择组合");
                }
            }
            break;
            case R.id.selectYearLabel:
            {
                //选择时间
                OptionsPickerView pvOptions = new OptionsPickerBuilder(SCSelectYearActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        selectYearIndex = options1;
                        String tx = options1Items.get(options1);
                        selectYearLabel.setText(tx);

                        refreshData();
                    }
                }).build();
                pvOptions.setTitleText("选择年份");
                pvOptions.setSelectOptions(selectYearIndex);
                pvOptions.setPicker(options1Items);
                pvOptions.show();
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
                HybridListByYearBean hybridListByYearBean = (HybridListByYearBean)item;
                dataLabel.setText(hybridListByYearBean.getName()+"");
                if(selectData != null){
                    if(hybridListByYearBean.getId() != ((HybridListByYearBean)selectData).getId()){
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
                GermplasmListByYearBean germplasmListByYearBean = (GermplasmListByYearBean)item;
                dataLabel.setText(germplasmListByYearBean.getName()+"");
                if(selectData != null){
                    if(germplasmListByYearBean.getId() != ((GermplasmListByYearBean)selectData).getId()){
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


    @Override
    public void onGermplasmListByYearCallBack(List<GermplasmListByYearBean> object) {
        dataByYearList.clear();
        if(object!=null && object.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataByYearList.addAll(object);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHybridListByYearCallBack(List<HybridListByYearBean> object) {
        dataByYearList.clear();
        if(object!=null && object.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataByYearList.addAll(object);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        dataAdapter.notifyDataSetChanged();
    }
}
