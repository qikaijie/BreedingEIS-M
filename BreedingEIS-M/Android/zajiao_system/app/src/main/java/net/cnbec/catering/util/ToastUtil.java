package net.cnbec.catering.util;

import android.widget.Toast;

import net.cnbec.catering.app.GJApplication;

/**
 * @Describe: Toast显示工具类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class ToastUtil {
    private static Toast toast;

    /**
     * 显示Toast消息
     * @param msg   消息
     */
    public static void show(String msg) {
        if (toast == null) {
            toast = Toast.makeText(GJApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 显示Toast消息
     * @param resId   消息资源id
     */
    public static void show(int resId) {
        show(GJApplication.getContext().getString(resId));
    }
}
