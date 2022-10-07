package com.liuxn.yuzhong.app.annotation;

import java.lang.annotation.*;

/**
 * 类Login的功能描述:
 * app登录效验
 * @auther liuxn
 * @date 2019-03-30 14:14:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}