package net.cnbec.catering.ui.common.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cnbec.catering.R;

public class NFCDialog {
    private final Display display;
    private Context context;
    private TextView mTvTitle;
    private TextView mTvSubTitle;
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvOK;
    private ImageView mIvIcon;
    private Dialog dialog;
    private Window dialogWindow;
    private LinearLayout mLinearBottom;

    public NFCDialog(Context context) {
        this.context = context;
        @SuppressLint("WrongConstant") WindowManager windowManager = (WindowManager)context.getSystemService("window");
        this.display = windowManager.getDefaultDisplay();
        this.dialog = new Dialog(context, R.style.NFC_Custom_Dialog_Style);
        this.dialogWindow = this.dialog.getWindow();
    }

    public NFCDialog builder() {
        View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_ensure_layout, (ViewGroup)null, false);
        LinearLayout mLinearDialog = (LinearLayout)view.findViewById(R.id.linear_dialog);
        this.mLinearBottom = (LinearLayout)view.findViewById(R.id.linear_bottom);
        this.mIvIcon = (ImageView)view.findViewById(R.id.iv_icon);
        this.mTvTitle = (TextView)view.findViewById(R.id.tv_title);
        this.mTvSubTitle = (TextView)view.findViewById(R.id.tv_sub_title);
        this.mTvSure = (TextView)view.findViewById(R.id.tv_sure);
        this.mTvOK = (TextView)view.findViewById(R.id.tv_ok);
        this.mTvCancel = (TextView)view.findViewById(R.id.tv_cancel);
        this.dialog.setContentView(view);
        mLinearDialog.setLayoutParams(new FrameLayout.LayoutParams((int)((double)this.display.getWidth() * 0.8D), -2));
        this.dialogWindow.setGravity(17);
        return this;
    }

    public NFCDialog setGravity(int gravity) {
        this.dialogWindow.setGravity(gravity);
        return this;
    }

    @SuppressLint("WrongConstant")
    public NFCDialog setIncon(int drawable) {
        this.mIvIcon.setVisibility(0);
        this.mIvIcon.setImageResource(drawable);
        return this;
    }

    public NFCDialog setTitle(String title) {
        if ("".equals(title)) {
            this.mTvTitle.setText("标题");
        } else {
            this.mTvTitle.setText(title);
        }

        return this;
    }

    public NFCDialog setTitle(String title, int color) {
        if ("".equals(title)) {
            this.mTvTitle.setText("标题");
        } else {
            this.mTvTitle.setText(title);
        }

        this.mTvTitle.setTextColor(color);
        return this;
    }

    @SuppressLint("WrongConstant")
    public NFCDialog setSubTitle(String title) {
        if ("".equals(title)) {
            this.mTvSubTitle.setVisibility(8);
        } else {
            this.mTvSubTitle.setVisibility(0);
            this.mTvTitle.setGravity(80);
            this.mTvSubTitle.setText(title);
        }

        return this;
    }

    @SuppressLint("WrongConstant")
    public NFCDialog setSubTitle(String title, int color) {
        if ("".equals(title)) {
            this.mTvSubTitle.setVisibility(8);
            this.mTvSubTitle.setText("标题");
        } else {
            this.mTvSubTitle.setVisibility(0);
            this.mTvTitle.setGravity(80);
            this.mTvSubTitle.setText(title);
        }

        this.mTvSubTitle.setTextColor(color);
        return this;
    }

    public NFCDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            this.mTvSure.setText("确认");
        } else {
            this.mTvSure.setText(text);
        }

        this.mTvSure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public NFCDialog setPositiveButton(String text, int color, final View.OnClickListener listener) {
        if ("".equals(text)) {
            this.mTvSure.setText("确认");
        } else {
            this.mTvSure.setText(text);
        }

        this.mTvSure.setTextColor(color);
        this.mTvSure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public NFCDialog setNegativeButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            this.mTvCancel.setText("取消");
        } else {
            this.mTvCancel.setText(text);
        }

        this.mTvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
                NFCDialog.this.dialog.dismiss();
            }
        });
        return this;
    }

    public NFCDialog setNegativeButton(String text, int color, final View.OnClickListener listener) {
        if ("".equals(text)) {
            this.mTvCancel.setText("取消");
        } else {
            this.mTvCancel.setText(text);
        }

        this.mTvCancel.setTextColor(color);
        this.mTvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
                NFCDialog.this.dialog.dismiss();
            }
        });
        return this;
    }

    @SuppressLint("WrongConstant")
    public NFCDialog setCenterButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            this.mTvOK.setText("确认");
        } else {
            this.mTvOK.setText(text);
        }

        this.mLinearBottom.setVisibility(8);
        this.mTvOK.setVisibility(0);
        this.mTvOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
                NFCDialog.this.dialog.dismiss();
            }
        });
        return this;
    }

    @SuppressLint("WrongConstant")
    public NFCDialog setCenterButton(String text, int color, final View.OnClickListener listener) {
        if ("".equals(text)) {
            this.mTvOK.setText("确认");
        } else {
            this.mTvOK.setText(text);
        }

        this.mTvOK.setTextColor(color);
        this.mLinearBottom.setVisibility(8);
        this.mTvOK.setVisibility(0);
        this.mTvOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
                NFCDialog.this.dialog.dismiss();
            }
        });
        return this;
    }

    public NFCDialog setCancelable(boolean cancelable) {
        this.dialog.setCancelable(cancelable);
        return this;
    }

    public NFCDialog dismiss() {
        this.dialog.dismiss();
        return this;
    }

    public void show() {
        this.dialog.show();
    }
}
