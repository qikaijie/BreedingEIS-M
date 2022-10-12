package net.cnbec.catering.ui.common.dialog.pop;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.cnbec.catering.R;
import net.cnbec.catering.util.WindowUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zxl on 2018/5/22.
 *         discription:弹出的喜欢的Fragment
 */

public class SelectSpeciesDialogForBottomFragment extends DialogFragment{

    private RecyclerView recyclerView;
    private Button cancelBtn;
    private Button sumbitBtn;

    private List<Object> allList = new ArrayList<>();
    private Object selectData = new Object();

    private Context mContext;
    private BaseQuickAdapter<Object, BaseViewHolder> mBaseAdapter;
    public static final String HOBBY_KEY_ALL = "quanbushuju";
    public static final String HOBBY_KEY_SELECT = "yixuanzedeshuju";

    private SelectResultListener selectResultListener;
    public interface SelectResultListener{
        public void resultListener(Object selectData);
    }
    public void setSelectResultListener(SelectResultListener selectResultListener) {
        this.selectResultListener = selectResultListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getDataFromBefore();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
        mContext = getActivity();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_dialog_select_species_bottom);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        // 获得转换后的px值
        int pxDimension = WindowUtils.dip2px(getActivity(),48);
        lp.height = allList.size()<6 ? allList.size()*pxDimension+pxDimension : pxDimension*6+pxDimension;
        window.setAttributes(lp);
        initView(dialog);
        // 窗口初始化后 请求网络数据
        return dialog;
    }

    private void getDataFromBefore() {
        Bundle bundle = getArguments();
        if (bundle != null){
            allList = new Gson().fromJson(bundle.getString(HOBBY_KEY_ALL), new TypeToken<List<Object>>(){}.getType());
            selectData = new Gson().fromJson(bundle.getString(HOBBY_KEY_SELECT), new TypeToken<Object>(){}.getType());
        }
    }

    private void initView(Dialog dialog) {
        cancelBtn =  (Button) dialog.findViewById(R.id.cancelBtn);
        sumbitBtn =  (Button)dialog.findViewById(R.id.sumbitBtn);
        recyclerView =  (RecyclerView)dialog.findViewById(R.id.recycle_view);
        setAdapter();
        setListener();

    }

    private void setListener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectData != null){
                    Toast.makeText(mContext, "请至少选择一个", Toast.LENGTH_SHORT).show();
                    return;
                }
                //回传值
                if(selectResultListener!=null){
                    selectResultListener.resultListener(selectData);
                }
                dismiss();
            }
        });

    }

    private void setAdapter() {
        if (mBaseAdapter == null) {
            mBaseAdapter = new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.item_string_text,allList) {
                @Override
                protected void convert(BaseViewHolder helper, Object item) {
                    final int position = helper.getAdapterPosition();
                    final TextView tvEnjoy = helper.getView(R.id.tvEnjoy);
//                    tvEnjoy.setText(item.tName);

                    //判断是否选中
//                    boolean isSelect = false;
//                    for (int i = 0;i<selectList.size();i++){
//                        if(selectList.get(i).tId == item.tId){
//                            isSelect = true;
//                        }
//                    }
//
//                    if (isSelect){
//                        tvEnjoy.setTextColor(ContextCompat.getColor(mContext,R.color.text_blue));
//                        ivEnjoy.setVisibility(View.VISIBLE);
//                    }else{
//                        tvEnjoy.setTextColor(ContextCompat.getColor(mContext,R.color.black));
//                        ivEnjoy.setVisibility(View.GONE);
//                    }

                }
            };
            mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Object selectItemBean = allList.get(position);

                    //判断是否选中
//                    int isSelect = -1;
//                    for (int i = 0;i<selectList.size();i++){
//                        if(selectList.get(i).tId == selectItemBean.tId){
//                            isSelect = i;
//                        }
//                    }
//
//                    if (isSelect!=-1){
//                        //取消选中
//                        selectList.remove(isSelect);
//                        mBaseAdapter.notifyDataSetChanged();
//                    }else{
//                        //新增选中
//                        selectList.add(selectItemBean);
//                        mBaseAdapter.notifyDataSetChanged();
//                    }
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mBaseAdapter);
    }
}
