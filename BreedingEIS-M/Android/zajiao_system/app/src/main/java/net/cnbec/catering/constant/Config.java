package net.cnbec.catering.constant;

import net.cnbec.catering.R;
import net.cnbec.catering.app.GJApplication;
import net.cnbec.catering.util.PackageUtil;
import net.cnbec.catering.util.SdCardUtil;

import java.io.File;

/**
 * @Describe: 配置相关类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/31
 */


public class Config {

    /**
     * 日志调试开关
     */
    public static boolean DEBUG = true;

    /**
     * 应用名称
     */
    public static String APP_NAME = GJApplication.getContext().getString(R.string.app_name);

    /**
     * 数据库名称
     */
    public static String DB_NAME = "catering";

    /**
     * Sd卡保存路径
     */
    public static String APP_SAVE_PATH = SdCardUtil.getNormalSDCardPath() + File.separator + APP_NAME;

    /**
     * data数据路径
     */
    public static String APP_DATA_PATH = PackageUtil.getAppDataDir(GJApplication.getContext());

    /**
     * data数据库路径
     */
    public static String APP_DATABASES_PATH = PackageUtil.getAppDataBasesDir(GJApplication.getContext());
}
