package net.cnbec.catering.ui.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.ui.base.BaseLazyFragment;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;
import net.cnbec.catering.util.CloneObjectUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PJFragment extends BaseLazyFragment<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View{

    public int index;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.checkboxIv)
    ImageView checkboxIv;

    Boolean isSelectAll;

    public PJListener listener;

    public interface PJListener{
        public void backSelectResult(int index,List<GroupListBean.AttributeListBean> attributeListBeans);
    }

    @Override
    public void onAttach(Activity activity) {
        listener=(PJListener) activity;
        super.onAttach(activity);
    }

    private PJItemAdapter pjItemAdapter;
    private List<GroupListBean.AttributeListBean> attributeListBeans;
    private GroupListBean groupListBean;

    public static PJFragment newInstance(int index, GroupListBean groupListBean) {
        PJFragment pjFragment = new PJFragment();
        pjFragment.index = index;
        pjFragment.groupListBean = groupListBean;
        return pjFragment;
    }

    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pj_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        isSelectAll = false;
        checkboxIv.setImageResource(R.mipmap.icon_checkbox_normal);

        attributeListBeans = new ArrayList<>();
        if(groupListBean.getAttributeList().size()>0){
            attributeListBeans.addAll(groupListBean.getAttributeList());
        }

        pjItemAdapter = new PJItemAdapter(R.layout.item_pj_layout, attributeListBeans);
        recyclerView.setAdapter(pjItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        //添加Android自带的分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();

        checkQuanxuanState();
    }

    class PJItemAdapter extends BaseQuickAdapter<GroupListBean.AttributeListBean, BaseViewHolder> {

        public PJItemAdapter(int layoutResId, @Nullable List<GroupListBean.AttributeListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GroupListBean.AttributeListBean item) {

            LinearLayout itemLayout = ((LinearLayout)helper.getView(R.id.item_layout));
            TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
            ImageView checkboxIv = ((ImageView)helper.getView(R.id.checkboxIv));

            titleTv.setText(item.getFieldName()+"");

            checkboxIv.setImageResource(item.getSelect() ? R.mipmap.icon_checkbox_selected : R.mipmap.icon_checkbox_normal);

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //选择或取消选择
                    if(item.getSelect()){
                        //已经是选中状态了
                        item.setSelect(false);
                    }else{
                        //未选中状态
                        item.setSelect(true);
                    }
                    attributeListBeans.remove(helper.getAdapterPosition());
                    attributeListBeans.add(helper.getAdapterPosition(),item);

                    //检查全选状态
                    checkQuanxuanState();
                    pjItemAdapter.notifyItemChanged(helper.getAdapterPosition());

                    //刷新主页数据
                    listener.backSelectResult(index,attributeListBeans);
                }
            });
        }

    }

    /**
     * 检查全选状态
     */
    public void checkQuanxuanState(){
        isSelectAll = true;
        for (int i = 0;i<attributeListBeans.size();i++){
            if(!attributeListBeans.get(i).getSelect()){
                //不是选中的
                isSelectAll = false;
                break;
            }
        }

        checkboxIv.setImageResource(isSelectAll ? R.mipmap.icon_checkbox_selected : R.mipmap.icon_checkbox_normal);
    }

    @OnClick({R.id.quanxuan_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quanxuan_layout:
            {
                isSelectAll = !isSelectAll;

                //创建临时对象
                List<GroupListBean.AttributeListBean> attributeListBeanList = new ArrayList<>();
                attributeListBeanList.addAll(attributeListBeans);

                for (int i = 0;i<attributeListBeanList.size();i++){
                    GroupListBean.AttributeListBean tempAttributeListBean = CloneObjectUtils.cloneObject(attributeListBeanList.get(i));
                    tempAttributeListBean.setSelect(isSelectAll);
                    attributeListBeans.remove(i);
                    attributeListBeans.add(i,tempAttributeListBean);
                }
                checkboxIv.setImageResource(isSelectAll ? R.mipmap.icon_checkbox_selected : R.mipmap.icon_checkbox_normal);

                pjItemAdapter.notifyDataSetChanged();

                //刷新主页数据
                listener.backSelectResult(index,attributeListBeans);
            }
            break;
        }
    }


}
