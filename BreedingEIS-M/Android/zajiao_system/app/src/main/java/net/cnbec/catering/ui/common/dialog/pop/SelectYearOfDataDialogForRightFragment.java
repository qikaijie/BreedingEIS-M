package net.cnbec.catering.ui.common.dialog.pop;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
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

public class SelectYearOfDataDialogForRightFragment extends DialogFragment{

    private RecyclerView recyclerView;

    private List<String> options1Items = new ArrayList<>();

    private List<Object> allList = new ArrayList<>();

    private Button selectYearLabel;

    private Button sumbitBtn;

    private Context mContext;
    private BaseQuickAdapter<Object, BaseViewHolder> mBaseAdapter;
    public static final String HOBBY_KEY_ALL = "all_data";
    public static final String HOBBY_KEY_SELECT = "select_data";

    private SelectResultListener selectResultListener;
    public interface SelectResultListener{
        public void resultListener(Object selectData);
    }
    public void setSelectResultListener(SelectYearOfDataDialogForRightFragment.SelectResultListener selectResultListener) {
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
        Dialog dialog = new Dialog(getActivity(), R.style.MyDialogNoAlphe);
        mContext = getActivity();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.fragment_dialog_select_single_bottom);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimRight);
        final WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.RIGHT; // 紧贴底部
//
//        Rect frame = new Rect();
//        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//
//        lp.y = statusBarHeight;
//        // 获得转换后的px值
//        // int pxDimension = WindowUtils.dip2px(getActivity(),48+44);
//        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight();//WindowManager.LayoutParams.MATCH_PARENT;//getActivity().getWindowManager().getDefaultDisplay().getWidth();//-pxDimension;//WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        lp.width = getActivity().getWindowManager().getDefaultDisplay().getWidth();//WindowManager.LayoutParams.MATCH_PARENT;//getActivity().getWindowManager().getDefaultDisplay().getWidth();///3*2;//WindowUtils.dip2px(getActivity(),32)+(allList.size()<6 ? allList.size()*pxDimension : pxDimension*6);
//        window.setAttributes(lp);

        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setAttributes(lp);


        initView(dialog);
        // 窗口初始化后 请求网络数据
        return dialog;
    }

    private void getDataFromBefore() {
        Bundle bundle = getArguments();
        if (bundle != null){
//            allList = new Gson().fromJson(bundle.getString(HOBBY_KEY_ALL), new TypeToken<List<PlantRecordBean>>(){}.getType());
        }
    }

    private void initView(Dialog dialog) {
        recyclerView =  (RecyclerView)dialog.findViewById(R.id.recycle_view);

        options1Items.add("2021");
        options1Items.add("2020");
        options1Items.add("2019");
        options1Items.add("2018");
        options1Items.add("2017");
        options1Items.add("2016");

        selectYearLabel = (Button)dialog.findViewById(R.id.selectYearLabel);
        selectYearLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(dialog.getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(option2)
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                        tvOptions.setText(tx);
                    }
                }).build();
                pvOptions.setPicker(options1Items);
                pvOptions.show();
            }
        });

        sumbitBtn = (Button) dialog.findViewById(R.id.btn_sumbit);
        Button cancelBtn = (Button) dialog.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        View topView = (View)dialog.findViewById(R.id.topView);
        topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        View leftView = (View)dialog.findViewById(R.id.leftView);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setAdapter();
    }

    private void setAdapter() {
        if (mBaseAdapter == null) {
            mBaseAdapter = new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.item_record_layout,allList) {
                @Override
                protected void convert(BaseViewHolder helper, Object item) {

//                    ((TextView)helper.getView(R.id.plantIdTv)).setText("编号："+item.getPlantId());
//                    ((TextView)helper.getView(R.id.nameTv)).setText("混合Id:"+item.getHybridId());
//                    ((TextView)helper.getView(R.id.timeTv)).setText(item.getCreateTime()+"");

                }
            };
            mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Object selectItemBean = allList.get(position);
                    //返回结果
                    if(selectResultListener!=null){
                        selectResultListener.resultListener(position);
                    }
                    dismiss();
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mBaseAdapter);
    }
}
