package com.liuxn.yuzhong.app.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuxn.yuzhong.app.constant.Constant;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.system.domain.SysUser;

public class APPBaseController extends BaseController{

	/** 基于@ExceptionHandler异常处理 */ 
    @ResponseBody
    @ExceptionHandler  
    public AjaxResult exp(HttpServletRequest request, Exception ex) {  
          
        logger.info("错误：" + ex.getMessage(), ex);
        return AjaxResult.error("系统错误，请稍后再试！");
    } 
    
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public SysUser getLoginUser()
	{
		return (SysUser)getRequest().getAttribute(Constant.CURRENT_USER);
	}
}
