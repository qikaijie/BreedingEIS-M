package net.cnbec.catering.ui.main.activity.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import net.cnbec.catering.ui.main.contract.FeedbackAddContract;
import net.cnbec.catering.ui.main.contract.FeedbackContract;
import net.cnbec.catering.ui.main.presenter.FeedbackAddPresenter;
import net.cnbec.catering.ui.main.presenter.FeedbackPresenter;
import net.cnbec.catering.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class FeedbackAddActivity extends BaseActivity<FeedbackAddContract.View, FeedbackAddContract.Presenter> implements FeedbackAddContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more_tv)
    TextView more_tv;
    @BindView(R.id.inputEdit)
    EditText inputEdit;

    String username;

    public static void startActivity(Context context, String username) {
        Intent intent = new Intent(context, FeedbackAddActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_add;
    }


    @Override
    protected FeedbackAddContract.Presenter initPresenter() {
        return new FeedbackAddPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        title.setText("新建反馈");
        more_tv.setVisibility(View.GONE);

        username = getIntent().getStringExtra("username");

    }

    @OnClick({R.id.back,R.id.btn_sumbit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            {
                finish();
            }
            break;
            case R.id.btn_sumbit:
            {
                //提交
                if(!TextUtils.isEmpty(inputEdit.getText().toString())){
                    getPresenter().feedbackAdd(username,inputEdit.getText().toString());
                }else{
                    ToastUtil.show("反馈内容不能为空~");
                }
            }
        }
    }

    @Override
    public void onFeedbackAddCallBack(Object object) {
        ToastUtil.show("提交成功");
        finish();
    }
}
