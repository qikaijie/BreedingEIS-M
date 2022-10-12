//package net.cnbec.catering.ui.main.adapter;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import net.cnbec.catering.R;
//import net.cnbec.catering.bean.GroupListBean;
//import net.cnbec.catering.bean.PJFromBean;
//import net.cnbec.catering.util.ToastUtil;
//
//import java.util.List;
//
//public class PJFromAdapter extends BaseQuickAdapter<GroupListBean, BaseViewHolder> {
//
//    public PJFromAdapter(int layoutResId, @Nullable List<GroupListBean> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, GroupListBean item) {
//
//        /**
//         storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
//         storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
//         storeUserCell.priceLable.text = [NSString stringWithFormat:@"¥%@",[dataDict objectForKey:@"balance"]];
//         */
//
//        TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
//        RecyclerView recyclerView = ((RecyclerView)helper.getView(R.id.recycle_view));
//
//        titleTv.setText(item.getName()+"");
//
//        PJFromItemAdapter pjFromItemAdapter = new PJFromItemAdapter(R.layout.item_pj_from_item_item_layout, item.getAttributeList());
//        recyclerView.setAdapter(pjFromItemAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//
//        pjFromItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()){
//                    case R.id.titleTv:
//                    {
//                        //点击事件
//                        ToastUtil.show(item.getItemStrs().get(position));
//                    }
//                        break;
//                }
//            }
//        });
//
//    }
//
//}
