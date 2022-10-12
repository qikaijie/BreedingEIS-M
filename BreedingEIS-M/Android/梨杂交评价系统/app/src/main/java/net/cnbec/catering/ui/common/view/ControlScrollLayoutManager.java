package net.cnbec.catering.ui.common.view;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ControlScrollLayoutManager extends LinearLayoutManager {
    private RecyclerView recyclerView;
    private boolean mCanAutoScroll;//默认不可以自动滑动，可通过setCanAutoScroll(boolean)来设置

    public ControlScrollLayoutManager(Context context, RecyclerView recyclerView) {
        this(context,VERTICAL,false,recyclerView);
    }

    public ControlScrollLayoutManager(Context context, int orientation, boolean reverseLayout, RecyclerView recyclerView) {
        super(context, orientation, reverseLayout);
        this.recyclerView = recyclerView;
    }

    public void setCanAutoScroll(boolean canAutoScroll){
        mCanAutoScroll = canAutoScroll;
    }

    @Override
    public int scrollVerticallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int consumedY = 0;
        // Do not let auto scroll
        if (mCanAutoScroll||recyclerView == null||recyclerView.getScrollState()!= RecyclerView.SCROLL_STATE_SETTLING){
            consumedY = super.scrollVerticallyBy(dx, recycler, state);
        }
        return consumedY;
    }
}