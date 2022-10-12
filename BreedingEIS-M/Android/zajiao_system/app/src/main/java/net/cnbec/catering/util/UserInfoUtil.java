package net.cnbec.catering.util;

import net.cnbec.catering.bean.UserInfo;
import net.cnbec.catering.network.dataProvider.DataProvider;
import net.cnbec.catering.network.dataProvider.DataProviderImpl;

/**
 * @Describe: 用户token管理工具类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class UserInfoUtil {
    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null && !StringUtil.isEmpty(userInfo.getAuthorities())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存token
     *
     * @param userInfo
     */
    public static void saveUserInfo(UserInfo userInfo) {
        SPUtils.put(SPUtils.KEY_USER_INFO, userInfo);
    }

    public static UserInfo getUserInfo() {
        return (UserInfo) SPUtils.get(SPUtils.KEY_USER_INFO, new Object());
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            return userInfo.getToken();
        } else {
            return "";
        }
    }

    /**
     * 获取手机号
     *
     * @return
     */
    public static String getMobile() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            return userInfo.getUsername();
        } else {
            return "";
        }
    }


    /**
     * 删除用户信息
     */
    public static void clearUserInfo() {
        SPUtils.remove(SPUtils.KEY_USER_INFO);
    }

    /**
     * 退出登录时，清除用户信息配置
     */
    public static void exitLoginClearRecord() {
        //2.清除内存用户信息
        UserInfoUtil.clearUserInfo();
    }
}
