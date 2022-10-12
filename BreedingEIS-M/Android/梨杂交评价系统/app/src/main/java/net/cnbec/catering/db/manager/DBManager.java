package net.cnbec.catering.db.manager;

import net.cnbec.catering.app.GJApplication;
import net.cnbec.catering.constant.Config;
import net.cnbec.catering.db.DaoMaster;
import net.cnbec.catering.db.DaoSession;

/**
 * @Describe: 数据库管理类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/31
 */


public class DBManager {

    private final static String DB_NAME = Config.DB_NAME;

    private static DBManager instance;

    private DaoSession daoSession;

    private DBManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(GJApplication.getContext(), DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public DaoSession getSession() {
        return daoSession;
    }

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public static void init() {
        getInstance();
    }

}
