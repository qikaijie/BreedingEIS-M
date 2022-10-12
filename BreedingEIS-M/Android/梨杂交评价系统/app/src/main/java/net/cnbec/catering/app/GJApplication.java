package net.cnbec.catering.app;

import android.app.Application;
import android.content.Context;


import androidx.multidex.MultiDex;

import com.coder.ffmpeg.jni.FFmpegCommand;
//import com.lwy.smartupdate.Config;
//import com.lwy.smartupdate.UpdateManager;
import com.previewlibrary.ZoomMediaLoader;
import com.tencent.bugly.crashreport.CrashReport;

import net.cnbec.catering.db.manager.DBManager;
import net.cnbec.catering.util.LogUtil;
import net.cnbec.catering.util.PackageUtil;
import net.cnbec.catering.util.SignUtil;

/**
 * @Describe: application
 * @Author: gujie
 * @Email:939395465@qq.com
 * @Date:2018/3/19
 */


public class GJApplication extends Application {

    /**
     * Application
     */
    private static GJApplication instance;
    /**
     * 设备mac
     */
    public static String MD5_LOCAL_MAC = "";
    /**
     * MachineNumber机器码
     */
    public static String MACHINE_NUMBER = "";

    /**
     * 加密秘钥
     */
    public static final String SECRET_KEY = "7BDAD17CE71BF240B2F80F72DA9734F088E4E846";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        FFmpegCommand.setDebug(false);

        CrashReport.initCrashReport(getApplicationContext(), "65a1dbeb99", false);

        //推荐在Application中初始化
//        Config config = new Config.Builder()
//                .isDebug(false)
//                .isShowInternalDialog(true)
//                .build(this);
//        UpdateManager.getInstance().init(config);

        if (PackageUtil.isMainProcess()) {
//            initLeakCanary();
            initLog();
            SignUtil.initPrinter();
            initDB();
        }

        ZoomMediaLoader.getInstance().init(new TestImageLoader());
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initMultiDex();
    }

    /**
     * 获取Context
     *
     * @return context
     */
    public static GJApplication getContext() {
        return instance;
    }

    /**
     * 初始化分包
     */
    private void initMultiDex() {
        MultiDex.install(this);
    }

    /**
     * 内存泄漏检测
     */
//    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//    }

    /**
     * 初始化Log
     */
    private void initLog() {
        LogUtil.init();
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        DBManager.init();
    }


}
