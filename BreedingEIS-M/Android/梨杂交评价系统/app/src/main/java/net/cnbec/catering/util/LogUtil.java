package net.cnbec.catering.util;

import android.util.Config;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @Describe: 日志打印工具
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class LogUtil {

    public static void init() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return Config.DEBUG;
            }
        });
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void e(String message) {
        Logger.e(message);
    }

    public static void e(Exception exception, String message) {
        Logger.e(exception, message);
    }

    public static void w(String message) {
        Logger.w(message);
    }

    public static void v(String message) {
        Logger.v(message);
    }

    public static void wtf(String message) {
        Logger.wtf(message);
    }

    public static void json(String message) {
        Logger.json(message);
    }

    public static void xml(String message) {
        Logger.xml(message);
    }
}
