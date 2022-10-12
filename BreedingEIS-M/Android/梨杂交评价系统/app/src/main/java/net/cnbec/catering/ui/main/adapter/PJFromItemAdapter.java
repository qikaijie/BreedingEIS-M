//package net.cnbec.catering.ui.main.adapter;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.View;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import net.cnbec.catering.R;
//import net.cnbec.catering.bean.PJFromBean;
//
//import java.util.List;
//
//public class PJFromItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
//
//    public PJFromItemAdapter(int layoutResId, @Nullable List<String> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, String item) {
//
//        /**
//         storeUserCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
//         storeUserCell.phoneLable.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"phone"]];
//         storeUserCell.priceLable.text = [NSString stringWithFormat:@"¥%@",[dataDict objectForKey:@"balance"]];
//         */
//
//        TextView titleTv = ((TextView)helper.getView(R.id.titleTv));
//        titleTv.setText(item);
//
//        helper.addOnClickListener(R.id.titleTv);
//
//    }
//
//}
