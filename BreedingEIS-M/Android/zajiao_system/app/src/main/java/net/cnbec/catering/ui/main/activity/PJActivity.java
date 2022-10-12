package net.cnbec.catering.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.PJContract;
import net.cnbec.catering.ui.main.fragment.PJFragment;
import net.cnbec.catering.ui.main.presenter.PJPresenter;
import net.cnbec.catering.util.CloneObjectUtils;
import net.cnbec.catering.util.SPUtils;
import net.cnbec.catering.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;

public class PJActivity extends BaseActivity<PJContract.View, PJContract.Presenter> implements PJContract.View, PJFragment.PJListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    List<GroupListBean> groupListBeans;

    SpeciesInfoBean speciesInfoBean;

    public Integer businessType;

    public static void startActivity(Context context, SpeciesInfoBean speciesInfoBean,int businessType) {
        Intent intent = new Intent(context, PJActivity.class);
        intent.putExtra("speciesInfoBeanStr",new Gson().toJson(speciesInfoBean));
        intent.putExtra("businessType", businessType);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pj;
    }


    @Override
    protected PJContract.Presenter initPresenter() {
        return new PJPresenter();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        businessType = getIntent().getIntExtra("businessType",0);
        speciesInfoBean = new Gson().fromJson(getIntent().getStringExtra("speciesInfoBeanStr"),new TypeToken<SpeciesInfoBean>(){}.getType());
        title.setText((businessType == 0 ? "杂交填报-" : "种质填报-") + speciesInfoBean.getName());

        getPresenter().groupList(speciesInfoBean.getId());
    }

    @Override
    public void onGroupListCallBack(List<GroupListBean> groupListBeanList) {
        this.groupListBeans = groupListBeanList;

        if(this.groupListBeans != null && this.groupListBeans.size()>0){

            /**
             * 动态添加所有属性
             */
            for (int i = 0;i<this.groupListBeans.size();i++){

                GroupListBean groupListBean = this.groupListBeans.get(i);
                titles.add(groupListBean.getName());

                List<GroupListBean.AttributeListBean> attributeListBeans = new ArrayList<>();
                List<GroupListBean.AttributeListBean> attributeListBeanList = new ArrayList<>();
                if(groupListBean.getAttributeList() != null && groupListBean.getAttributeList().size()>0){
                    attributeListBeanList.addAll(groupListBean.getAttributeList());
                }
                for (int j = 0;j<attributeListBeanList.size();j++){
                    GroupListBean.AttributeListBean attributeListBean = attributeListBeanList.get(j);
                    attributeListBean.setSelect(false);
                    attributeListBeans.add(attributeListBean);
                }
                //重置列表
                groupListBean.setAttributeList(attributeListBeans);
                this.groupListBeans.remove(i);
                this.groupListBeans.add(i,groupListBean);
                fragments.add(PJFragment.newInstance(i,groupListBean));
            }

            /**
             *  存储原属性列表
             */
            SPUtils.put(SPUtils.GROUP_LIST,new Gson().toJson(this.groupListBeans));

            viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments.get(position);
                }

                @Override
                public int getCount() {
                    return fragments.size();
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    super.destroyItem(container, position, object);
                }

                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {

                    return titles.get(position);
                }
            });

            tabLayout.setupWithViewPager(viewPager);
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
                List<GroupListBean> groupListBeanList = new ArrayList<>();
                for (int i = 0;i<this.groupListBeans.size();i++){
                    //临时存储进此数组

                    GroupListBean groupListBeanTemp = CloneObjectUtils.cloneObject(this.groupListBeans.get(i));
                    groupListBeanTemp.setAttributeList(new ArrayList<>());

                    GroupListBean tempGroupListBean = this.groupListBeans.get(i);

                    List<GroupListBean.AttributeListBean> tempAttributeListBean = CloneObjectUtils.cloneObject(tempGroupListBean.getAttributeList());

                    //临时存储进此数组
                    List<GroupListBean.AttributeListBean> attributeListBeanListTemp = new ArrayList<>();

                    for (int j = 0;j<tempAttributeListBean.size();j++){
                        if(tempAttributeListBean.get(j).getSelect()){
                            //加入选择数据中
                            GroupListBean.AttributeListBean alB =  CloneObjectUtils.cloneObject(tempAttributeListBean.get(j));
                            if(alB.getFieldType().equals("checkbox") || alB.getFieldType().equals("radio")){
                                String[] fieldContents = alB.getFieldContent().split("\\|");
                                List<String> fieldContentList = Arrays.asList(fieldContents);
                                List<GroupListBean.AttributeListBean.SelectResultBean> selectResultBeanList = new ArrayList<>();
                                for (int x = 0;x<fieldContentList.size();x++){
                                    GroupListBean.AttributeListBean.SelectResultBean selectResultBean = new GroupListBean.AttributeListBean.SelectResultBean(fieldContentList.get(x),false);
                                    selectResultBeanList.add(selectResultBean);
                                }
                                alB.setResults(selectResultBeanList);
                            }
                            attributeListBeanListTemp.add(alB);
                        }
                    }
                    if(attributeListBeanListTemp.size()>0){
                        groupListBeanTemp.setAttributeList(attributeListBeanListTemp);
                        //存储进总数据
                        groupListBeanList.add(groupListBeanTemp);
                    }
                }

                if(groupListBeanList.size()>0){
                    /**
                     *  存储选择后的属性列表
                     */
                    SPUtils.put(SPUtils.GROUP_LIST_SELECT,new Gson().toJson(groupListBeanList));
                    //获取子页面所有数据
                    if(businessType == 0){
                        PJFromActivityCopy.startActivity(this,groupListBeanList,speciesInfoBean,businessType);
                    }else{
                        PJFromActivitySeedling.startActivity(this,groupListBeanList,speciesInfoBean,businessType);
                    }
                }else{
                    ToastUtil.show("请先选择杂交属性");
                }
            }
                break;
        }
    }

    @Override
    protected boolean applyFullScreen() {
        return false;
    }

    @Override
    public void backSelectResult(int index, List<GroupListBean.AttributeListBean> attributeListBeans) {
        //ToastUtil.show("接收到来自第"+index+"页的数据");
        GroupListBean groupListBean = this.groupListBeans.get(index);
        groupListBean.setAttributeList(attributeListBeans);
        this.groupListBeans.remove(index);
        this.groupListBeans.add(index,groupListBean);
    }
}
