package net.cnbec.catering.ui.base;

/**
 * @Describe: 基础回调
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public interface IBaseView {
    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 处理消息
     *
     * @param status
     * @param msg
     */
    void handleMsg(int status, String msg);
}
