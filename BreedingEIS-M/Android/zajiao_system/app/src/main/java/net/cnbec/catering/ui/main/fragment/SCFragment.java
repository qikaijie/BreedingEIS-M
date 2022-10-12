package net.cnbec.catering.ui.main.fragment;

import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.siberiadante.customdialoglib.EnsureDialog;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.CollectListBean;
import net.cnbec.catering.bean.GermplasmListByYearBean;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.SeedlingCollectListBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.ui.base.BaseLazyFragment;
import net.cnbec.catering.ui.main.activity.RecordDetailsActivity;
import net.cnbec.catering.ui.main.activity.SCListActivity;
import net.cnbec.catering.ui.main.activity.SCSelectYearActivity;
import net.cnbec.catering.ui.main.activity.SelectYearActivity;
import net.cnbec.catering.ui.main.contract.SCContract;
import net.cnbec.catering.ui.main.presenter.SCPresenter;
import net.cnbec.catering.util.ToastUtil;

import java.util.ArrayList;
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

public class SCFragment extends BaseLazyFragment<SCContract.View, SCContract.Presenter> implements SCContract.View {

    Integer orderBy;
    Integer businessType;

    @BindView(R.id.emptyTv)
    TextView emptyTv;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private SCRecordAdapter scRecordAdapter;
    private List<Object> dataList;

    @BindView(R.id.paixuLL)
    LinearLayout paixuLL;
    @BindView(R.id.paixuIV)
    ImageView paixuIV;
    @BindView(R.id.paixuTV)
    TextView paixuTV;

    @BindView(R.id.tagLayout)
    LinearLayout tagLayout;
    @BindView(R.id.tagText)
    TextView tagText;
    @BindView(R.id.tagClean)
    ImageView tagClean;

    @BindView(R.id.shaixuanLL)
    LinearLayout shaixuanLL;
    @BindView(R.id.shaixuanIV)
    ImageView shaixuanIV;
    @BindView(R.id.shaixuanTV)
    TextView shaixuanTV;

    Integer hybridId;
    Integer germplasmId;

    EnsureDialog ensureDialog;

    /**
     * 当前选择
     */
    private Integer selectYear;
    private Object listByYearBean;

    public static SCFragment newInstance(int businessType) {
        return new SCFragment(businessType);
    }

    public SCFragment(int businessType){
        this.businessType = businessType;
    }

    @Override
    protected SCContract.Presenter initPresenter() {
        return new SCPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sc;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        setOrderBy(-1);

        dataList = new ArrayList<>();
        scRecordAdapter = new SCRecordAdapter(R.layout.item_sc_layout, dataList);
        recyclerView.setAdapter(scRecordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        scRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(businessType == 0){
                    getPresenter().plantInfo(((CollectListBean)(dataList.get(position))).getPlantCode()+"");
                }else{
                    getPresenter().seedlingInfo(((SeedlingCollectListBean)(dataList.get(position))).getSeedlingCode()+"");
                }
            }
        });
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                String order = "";
                if(orderBy == 0){
                    order = "ascending";
                }else if(orderBy == 1){
                    order = "descending";
                }
                if(businessType == 0){
                    String codeTwo = listByYearBean!=null ? ((HybridListByYearBean)listByYearBean).getSowingCode() : "";
                    getPresenter().plantCollectList(codeTwo,order,"");
                }else{
                    String codeTwo = listByYearBean!=null ? ((GermplasmListByYearBean)listByYearBean).getId()+"" : "";
                    getPresenter().seedlingCollectList(codeTwo,order,"");//codeTwo
                }
            }
        });

        refreshLayout.startRefresh();
    }


    /**
     * @param map 接手过来的实体类
     */
    public void onMoonStickyEvent(Map<String,Object> map){
        selectYear = (Integer)map.get("year");
        if(businessType == 0){
            listByYearBean = (HybridListByYearBean)map.get("data");
        }else{
            listByYearBean = (GermplasmListByYearBean)map.get("data");
        }

        shaixuanIV.setImageResource(R.mipmap.icon_shuaixuan_s);
        shaixuanTV.setTextColor(getResources().getColor(R.color.forestgreen));

        tagLayout.setVisibility(View.VISIBLE);
        if(businessType == 0){
            hybridId = ((HybridListByYearBean)listByYearBean).getId();
            tagText.setText(((HybridListByYearBean)listByYearBean).getName());
        }else{
            germplasmId = ((GermplasmListByYearBean)listByYearBean).getId();
            tagText.setText(((GermplasmListByYearBean)listByYearBean).getName());
        }
        refreshLayout.startRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.tagClean,R.id.paixuLL,R.id.shaixuanLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tagClean:
            {
                tagLayout.setVisibility(View.GONE);

                hybridId = null;
                germplasmId = null;

                selectYear = 0;
                listByYearBean = null;
                shaixuanIV.setImageResource(R.mipmap.icon_shuaixuan_n);
                shaixuanTV.setTextColor(getResources().getColor(R.color.gray));

                refreshLayout.startRefresh();
            }
            break;
            case R.id.paixuLL:
            {
                //排序
                setOrderBy(null);
            }
            break;
            case R.id.shaixuanLL:
            {
                SCSelectYearActivity.startActivity(getContext(), getActivity(), this.businessType, selectYear, businessType == 0 ? (HybridListByYearBean)listByYearBean : (GermplasmListByYearBean)listByYearBean);
            }
                break;
        }
    }

    public void setOrderBy(Integer tempOrderBy) {
        if(tempOrderBy == null){
            if(this.orderBy == -1){
                tempOrderBy = 0;
            }else if(this.orderBy == 0){
                tempOrderBy = 1;
            }else if(this.orderBy == 1){
                tempOrderBy = -1;
            }
        }
        this.orderBy = tempOrderBy;
        switch (orderBy){
            case 0:
            {
                paixuTV.setText("短码升序");
                paixuIV.setImageResource(R.mipmap.icon_paixu_sheng);
            }
            break;
            case 1:
            {
                paixuTV.setText("短码降序");
                paixuIV.setImageResource(R.mipmap.icon_paixu_jiang);
            }
            break;
            default:
                paixuTV.setText("默认排序");
                paixuIV.setImageResource(R.mipmap.icon_paixu_defult);
                break;
        }
        refreshLayout.startRefresh();
    }

    class SCRecordAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

        public SCRecordAdapter(int layoutResId, @Nullable List<Object> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

            LinearLayout scLv = (LinearLayout)helper.getView(R.id.scLv);
            ImageView scIv = (ImageView)helper.getView(R.id.scIv);

            TextView plantIdTv = (TextView)helper.getView(R.id.plantIdTv);
            TextView timeTv = (TextView)helper.getView(R.id.timeTv);
            if(businessType == 0){
                CollectListBean collectListBean = (CollectListBean)item;
                plantIdTv.setText(""+collectListBean.getPlantCode());
                timeTv.setText(collectListBean.getCreateTime()+"");
                scIv.setImageResource(collectListBean.getLevel() == 1 ? R.mipmap.icon_sc_s : R.mipmap.icon_sc_ss);
            }else{
                SeedlingCollectListBean collectListBean = (SeedlingCollectListBean)item;
                plantIdTv.setText(""+collectListBean.getSeedlingCode());
                timeTv.setText(collectListBean.getCreateTime()+"");
                scIv.setImageResource(collectListBean.getLevel() == 1 ? R.mipmap.icon_sc_s : R.mipmap.icon_sc_ss);
            }
            scLv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "您确定要转"+ (businessType == 0 ? (((CollectListBean)item).getLevel() == 1 ? "二" : "一") : (((SeedlingCollectListBean)item).getLevel() == 1 ? "二" : "一")) + "级收藏吗";
                    ensureDialog = new EnsureDialog(getContext()).builder()
                                .setGravity(Gravity.CENTER)//默认居中，可以不设置
                                .setTitle("提示", getResources().getColor(R.color.black))//可以不设置标题颜色，默认系统颜色
                                .setCancelable(false)
                                .setSubTitle(message)
                                .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setPositiveButton("确定", getResources().getColor(R.color.red), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(businessType == 0){
                                            CollectListBean collectListBean = (CollectListBean)item;
                                            getPresenter().plantCollectAdd((collectListBean.getLevel() != 1 ? 1 : 2),collectListBean.getPlantId());
                                        }else{
                                            SeedlingCollectListBean collectListBean = (SeedlingCollectListBean)item;
                                            getPresenter().seedlingCollectAdd((collectListBean.getLevel() != 1 ? 1 : 2),collectListBean.getSeedlingId());
                                        }
                                    }
                                });
                    ensureDialog.show();
                }
            });
        }

    }

    @Override
    public void onPlantInfoCallBack(PlantInfoBean plantInfoB) {
        if(plantInfoB != null){
            SCListActivity.startActivity(getContext(),plantInfoB,businessType);
        }else{
            ToastUtil.show("编码信息不存在");
        }
    }

    @Override
    public void onSeedlingInfoCallBack(SeedlingInfoBean seedlingInfoB) {
        if(seedlingInfoB != null){
            SCListActivity.startActivity(getContext(),seedlingInfoB,businessType);
        }else{
            ToastUtil.show("编码信息不存在");
        }
    }

    @Override
    public void onPlantCollectList(List<CollectListBean> collectListBeanList) {
        refreshLayout.finishRefreshing();
        //记录详情
        dataList.clear();
        if(collectListBeanList!=null && collectListBeanList.size()>0){
            dataList.addAll(collectListBeanList);
            emptyTv.setVisibility(View.GONE);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        scRecordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSeedlingCollectListCallBack(List<SeedlingCollectListBean> objects) {
        refreshLayout.finishRefreshing();
        //记录详情
        dataList.clear();
        if(objects!=null && objects.size()>0){
            dataList.addAll(objects);
            emptyTv.setVisibility(View.GONE);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        scRecordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlantCollectAddCallBack(Object object) {
        refreshLayout.startRefresh();
    }

    @Override
    public void onSeedlingCollectAddCallBack(Object object) {
        refreshLayout.startRefresh();
    }

}
