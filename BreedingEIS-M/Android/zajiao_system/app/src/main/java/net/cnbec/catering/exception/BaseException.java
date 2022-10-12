package net.cnbec.catering.exception;

/**
 * @Describe: 异常基类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class BaseException extends RuntimeException {
    public BaseException(String msg) {
        super(msg);
    }
}
