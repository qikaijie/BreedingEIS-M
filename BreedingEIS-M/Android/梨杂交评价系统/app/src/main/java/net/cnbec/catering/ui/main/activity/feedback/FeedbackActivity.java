package net.cnbec.catering.ui.main.activity.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.FeedbackBean;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.main.contract.FeedbackContract;
import net.cnbec.catering.ui.main.presenter.FeedbackPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class FeedbackActivity extends BaseActivity<FeedbackContract.View, FeedbackContract.Presenter> implements FeedbackContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more_tv)
    TextView more_tv;
    @BindView(R.id.emptyTv)
    TextView emptyTv;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private ObjectRecordAdapter recordAdapter;
    private List<FeedbackBean> dataList;

    String username;

    @Override
    protected void onResume() {
        super.onResume();

        getPresenter().feedbackList();
    }

    public static void startActivity(Context context, String username) {
        Intent intent = new Intent(context, FeedbackActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }


    @Override
    protected FeedbackContract.Presenter initPresenter() {
        return new FeedbackPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        title.setText("我的反馈");
        more_tv.setText("新增");

        username = getIntent().getStringExtra("username");

        dataList = new ArrayList<>();
        recordAdapter = new ObjectRecordAdapter(R.layout.item_feedback_layout, dataList);
        recyclerView.setAdapter(recordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
    }

    @OnClick({R.id.back,R.id.more_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
            case R.id.more_tv:
            {
                FeedbackAddActivity.startActivity(FeedbackActivity.this,username);
            }
            break;
        }
    }

    class ObjectRecordAdapter extends BaseQuickAdapter<FeedbackBean, BaseViewHolder> {
        public ObjectRecordAdapter(int layoutResId, @Nullable List<FeedbackBean> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, FeedbackBean item) {
            ((TextView)helper.getView(R.id.createTimeTV)).setText(item.getCreateTime()+"");
            ((TextView)helper.getView(R.id.contentTV)).setText(item.getContent()+"");
            if(!TextUtils.isEmpty(item.getReply())){
                ((LinearLayout)helper.getView(R.id.replyLayout)).setVisibility(View.VISIBLE);
                ((TextView)helper.getView(R.id.replyTV)).setText(item.getReply()+"");
            }else{
                ((LinearLayout)helper.getView(R.id.replyLayout)).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onFeedbackListCallBack(List<FeedbackBean> feedbackBeans) {
        //记录详情
        dataList.clear();
        if(feedbackBeans!=null && feedbackBeans.size()>0){
            emptyTv.setVisibility(View.GONE);
            dataList.addAll(feedbackBeans);
        }else{
            emptyTv.setVisibility(View.VISIBLE);
        }
        recordAdapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(dataList.size()-1);
    }
}
