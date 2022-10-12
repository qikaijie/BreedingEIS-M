package net.cnbec.catering.ui.main.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.PlantRecordBean;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<PlantRecordBean, BaseViewHolder> {

    public RecordAdapter(int layoutResId, @Nullable List<PlantRecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlantRecordBean item) {
        ((TextView)helper.getView(R.id.plantIdTv)).setText(item.getPlantCode());
        ((TextView)helper.getView(R.id.nameTv)).setText(item.getHybridName()+"");
        ((TextView)helper.getView(R.id.timeTv)).setText(item.getCreateTime()+"");

    }

}
