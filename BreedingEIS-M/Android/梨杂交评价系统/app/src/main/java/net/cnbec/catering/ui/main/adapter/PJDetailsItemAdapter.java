package net.cnbec.catering.ui.main.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PJDetailsBean;
import net.cnbec.catering.bean.PJKVBean;

import java.util.List;

public class PJDetailsItemAdapter extends BaseQuickAdapter<PJKVBean, BaseViewHolder> {

    public PJDetailsItemAdapter(int layoutResId, @Nullable List<PJKVBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PJKVBean item) {

        /**
         storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
         storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
         storeUserCell.priceLable.text = [NSString stringWithFormat:@"¥%@",[dataDict objectForKey:@"balance"]];
         */

        TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
        TextView detailsTv = ((TextView)helper.getView(R.id.detailsTv));

        titleTv.setText(item.key);
        detailsTv.setText(item.value);

    }

}
