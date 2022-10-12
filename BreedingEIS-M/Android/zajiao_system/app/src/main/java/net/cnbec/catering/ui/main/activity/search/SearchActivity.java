package net.cnbec.catering.ui.main.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.GermplasmListByNameBean;
import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantListByCodeBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingListByCodeBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.SearchContract;
import net.cnbec.catering.ui.main.fragment.PJFragment;
import net.cnbec.catering.ui.main.presenter.SearchPresenter;
import net.cnbec.catering.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchContract.View, SearchContract.Presenter> implements SearchContract.View, TextView.OnEditorActionListener,TextWatcher {

    @BindView(R.id.title)
    TextView titleTv;

    @BindView(R.id.searchEt)
    EditText searchEt;

    @BindView(R.id.cleanBtn)
    ImageView cleanBtn;

    @BindView(R.id.emptyTv)
    TextView emptyTv;

    //已上传
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    List<PlantListByCodeBean> plantListByCodeBeans;
    PlantItemAdapter plantItemAdapter;
    List<SeedlingListByCodeBean> seedlingListByCodeBeans;
    GermplasmItemAdapter germplasmItemAdapter;

    Integer businessType;

    public static void startActivity(Context context,Integer businessType) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("businessType",businessType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        businessType = getIntent().getIntExtra("businessType",0);
        titleTv.setText(businessType == 0 ? "杂交搜索" : "种质搜索");
        searchEt.setHint(businessType == 0 ? "请输入要搜索的杂交编码" : "请输入要搜索的种质植物");
        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEND);
        searchEt.addTextChangedListener(this);
        searchEt.setOnEditorActionListener(this);

        if(businessType == 0){
            plantListByCodeBeans = new ArrayList<>();
            plantItemAdapter = new PlantItemAdapter(R.layout.item_search_layout, plantListByCodeBeans);
            plantItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    getPresenter().plantInfo(plantListByCodeBeans.get(position).getCode());
                }
            });
            recyclerView.setAdapter(plantItemAdapter);
        }else{
            seedlingListByCodeBeans = new ArrayList<>();
            germplasmItemAdapter = new GermplasmItemAdapter(R.layout.item_search_layout, seedlingListByCodeBeans);
            germplasmItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    getPresenter().seedlingInfo(seedlingListByCodeBeans.get(position).getCode());
                }
            });
            recyclerView.setAdapter(germplasmItemAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);

    }

    @OnClick({R.id.back,R.id.cleanBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
            case R.id.cleanBtn:
            {
                searchEt.setText("");
                cleanBtn.setVisibility(View.GONE);
            }
                break;
        }
    }



    @Override
    protected boolean applyFullScreen() {
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            }

            if(!TextUtils.isEmpty(searchEt.getText().toString())){
                if(businessType == 0){
                    getPresenter().plantListByName(searchEt.getText().toString());
                }else{
                    getPresenter().seedlingListByName(searchEt.getText().toString());
                }
            }else{
                ToastUtil.show("搜索内容不能为空");
            }

            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //编辑框的内容发生改变之前的回调方法
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //编辑框的内容正在发生改变时的回调方法 >>用户正在输入
        //我们可以在这里实时地 通过搜索匹配用户的输入
    }

    @Override
    public void afterTextChanged(Editable s) {
        //编辑框的内容改变以后,用户没有继续输入时 的回调方法
        String searchStr = s.toString();
        Log.e("SearchActivity", "即将搜索的内容: " + searchStr);
    }

    @Override
    public void onPlantListByNameCallBack(List<PlantListByCodeBean> plantListByCodeBeanList) {
        plantListByCodeBeans.clear();
        if(plantListByCodeBeanList != null && plantListByCodeBeanList.size()>0){
            plantListByCodeBeans.addAll(plantListByCodeBeanList);
            emptyTv.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
        plantItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSeedlingListByNameCallBack(List<SeedlingListByCodeBean> seedlingListByCodeBeanList) {
        seedlingListByCodeBeans.clear();
        if(seedlingListByCodeBeanList != null && seedlingListByCodeBeanList.size()>0){
            seedlingListByCodeBeans.addAll(seedlingListByCodeBeanList);
            emptyTv.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
        germplasmItemAdapter.notifyDataSetChanged();
    }

    class PlantItemAdapter extends BaseQuickAdapter<PlantListByCodeBean, BaseViewHolder> {
        public PlantItemAdapter(int layoutResId, @Nullable List<PlantListByCodeBean> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, PlantListByCodeBean item) {
            TextView nameTv = ((TextView)helper.getView(R.id.nameTv));
            nameTv.setText(item.getCode()+"");
        }
    }
    class GermplasmItemAdapter extends BaseQuickAdapter<SeedlingListByCodeBean, BaseViewHolder> {
        public GermplasmItemAdapter(int layoutResId, @Nullable List<SeedlingListByCodeBean> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, SeedlingListByCodeBean item) {
            TextView nameTv = ((TextView)helper.getView(R.id.nameTv));
            nameTv.setText(item.getCode()+"");
        }
    }

    @Override
    public void onPlantInfoCallBack(PlantInfoBean plantInfoBean) {
        //事件发布者发布事件
        Map<String,Object> map = new HashMap<>();
        map.put("option","plantInfo");
        map.put("plantInfoBean",new Gson().toJson(plantInfoBean));
        EventBus.getDefault().postSticky(map);
        finish();
    }

    @Override
    public void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoBean) {
        //事件发布者发布事件
        Map<String,Object> map = new HashMap<>();
        map.put("option","seedlingInfo");
        map.put("seedlingInfoBean",new Gson().toJson(seedlingInfoBean));
        EventBus.getDefault().postSticky(map);
        finish();
    }
}
