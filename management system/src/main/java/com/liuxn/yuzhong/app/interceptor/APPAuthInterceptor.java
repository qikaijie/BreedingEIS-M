/*
 * Copyright (C), 2014-2017, 食药监
 * FileName: LoginInterceptor.java
 * Author:   刘晓宁
 * Date:     2017年4月6日 下午2:10:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.liuxn.yuzhong.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.constant.Constant;
import com.liuxn.yuzhong.app.util.JwtUtils;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.system.domain.SysUser;
import com.liuxn.yuzhong.project.system.service.ISysUserService;

import io.jsonwebtoken.Claims;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 刘晓宁
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class APPAuthInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	LoginRequired annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(LoginRequired.class);
        }else{
            // 如果不是映射到方法直接通过
            return true;
        }
        //如果不需要登陆验证，直接通过
        if(annotation == null){
            return true;
        }

        //需要验证，获取用户凭证
        String token = request.getHeader(Constant.CURRENT_TOKEN);

        if(StringUtils.isEmpty(token)){
            token = request.getParameter(Constant.CURRENT_TOKEN);
        }

        //凭证为空
        if(StringUtils.isEmpty(token)){
        	response.setContentType("text/html;charset=UTF-8");
        	response.getWriter().print(JSON.toJSONString(AjaxResult.error("-1", "参数不合法，请重新操作")));
        	return false;
        }

        //验证token
        Claims claims = jwtUtils.getClaimByToken(token);
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
        	response.setContentType("text/html;charset=UTF-8");
        	response.getWriter().print(JSON.toJSONString(AjaxResult.error("-1", "凭证失效，请重新登录")));
        	return false;
        }

        //验证用户信息
        SysUser sysUser = sysUserService.selectUserById(Long.parseLong(claims.getSubject()));
		if(sysUser == null) {
        	response.setContentType("text/html;charset=UTF-8");
        	response.getWriter().print(JSON.toJSONString(AjaxResult.error("-1", "用户不存在，请重新登录")));
        	return false;
		}
        
        //设置user到request里，后续获取用户信息
        request.setAttribute(Constant.CURRENT_USER, sysUser);

        return true;
    }
}
