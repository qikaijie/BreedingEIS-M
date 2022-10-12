package net.cnbec.catering.bean;

import java.io.Serializable;

/**
 * @Describe: 基本数据
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class BaseBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public int code;

    public Integer total;

    public String msg;

    public T data;

    public T rows;
}
