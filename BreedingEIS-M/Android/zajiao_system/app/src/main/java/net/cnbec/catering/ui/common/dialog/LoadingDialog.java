package net.cnbec.catering.ui.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import net.cnbec.catering.R;
import net.cnbec.catering.util.LogUtil;

/**
 * @Describe: 加载框
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/21
 */


public class LoadingDialog extends Dialog {


    /**
     * 对话框
     */
    private static Dialog loadingDialog;

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public static void show(Context context) {
        LogUtil.d("LoadingDialog : show");
        if (loadingDialog != null) {
            release();
        }
        loadingDialog = new LoadingDialog(context ,R.style.AppTheme_ConfirmDialog1);
        loadingDialog.show();
        Window dialogWindow = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER );
        dialogWindow.setAttributes(lp);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading);
    }


    public static void close() {
        LogUtil.d("LoadingDialog : close");
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            //loadingDialog = null;
        }
    }

    public static void release() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
