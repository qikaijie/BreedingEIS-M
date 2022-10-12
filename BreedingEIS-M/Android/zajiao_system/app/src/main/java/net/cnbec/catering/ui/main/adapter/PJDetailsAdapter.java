package net.cnbec.catering.ui.main.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PJDetailsBean;

import java.util.List;

public class PJDetailsAdapter extends BaseQuickAdapter<PJDetailsBean, BaseViewHolder> {

    public PJDetailsAdapter(int layoutResId, @Nullable List<PJDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PJDetailsBean item) {

        /**
         storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
         storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
         storeUserCell.priceLable.text = [NSString stringWithFormat:@"¥%@",[dataDict objectForKey:@"balance"]];
         */

        TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
        RecyclerView recyclerView = ((RecyclerView)helper.getView(R.id.recycle_view));

        titleTv.setText(item.getTitleStr());

        PJDetailsItemAdapter pjDetailsItemAdapter = new PJDetailsItemAdapter(R.layout.item_pj_details_item_layout, item.getItemMaps());
        recyclerView.setAdapter(pjDetailsItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

    }

}
