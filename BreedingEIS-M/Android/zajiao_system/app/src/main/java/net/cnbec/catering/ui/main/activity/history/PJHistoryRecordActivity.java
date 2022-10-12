package net.cnbec.catering.ui.main.activity.history;

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

import net.cnbec.catering.R;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.BaseContractCode;
import net.cnbec.catering.ui.main.fragment.HistoryFragment;
import net.cnbec.catering.ui.main.fragment.RecordFragment;
import net.cnbec.catering.ui.main.presenter.BasePresenterCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PJHistoryRecordActivity extends BaseActivity<BaseContractCode.View, BaseContractCode.Presenter> implements BaseContractCode.View {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    Integer businessType;
    Integer detailId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pj_history_record;
    }

    public static void startActivity(Context context,Integer businessType,Integer detailId) {
        Intent intent = new Intent(context, PJHistoryRecordActivity.class);
        intent.putExtra("businessType",businessType);
        intent.putExtra("detailId",detailId);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        context.startActivity(intent);
    }


    @Override
    protected BaseContractCode.Presenter initPresenter() {
        return new BasePresenterCode();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 订阅者处理粘性事件
     * @param map 接手过来的实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMoonStickyEvent(Map<String,Object> map){

        String option = (String) map.get("option");
        if(!option.equals("selectTime")){
            return;
        }

        for(int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if(fragment != null && fragment.isAdded() && fragment.isMenuVisible()) {
                // ToastUtil.show(i+"");
                if(i == 0){
                    ((HistoryFragment)fragment).onMoonStickyEvent(map);
                }else if(i == 1){
                    ((HistoryFragment)fragment).onMoonStickyEvent(map);
                }
            }
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        businessType = getIntent().getIntExtra("businessType",0);
        detailId = getIntent().getIntExtra("detailId",0);

        title.setText("历史记录");
        fragments.add(HistoryFragment.newInstance(1,businessType,detailId));
        titles.add("初选记录");
        fragments.add(HistoryFragment.newInstance(2,businessType,detailId));
        titles.add("高接记录");
        fragments.add(HistoryFragment.newInstance(3,businessType,detailId));
        titles.add("区试记录");

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

            @io.reactivex.annotations.Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
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
}
