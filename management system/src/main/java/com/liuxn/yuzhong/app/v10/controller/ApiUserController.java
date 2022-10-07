package com.liuxn.yuzhong.app.v10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.system.domain.SysUser;
import com.liuxn.yuzhong.project.system.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "移动端-用户信息管理")
@RestController
@RequestMapping("/app/v10/user")
public class ApiUserController extends APPBaseController{
    
    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("用户信息查询")
    @LoginRequired
    @PostMapping("/info")
    public AjaxResult info()
    {
    	SysUser sysUser = getLoginUser();
    	
    	SysUser newUser = new SysUser();
    	newUser.setUserId(sysUser.getUserId());
    	newUser.setUserName(sysUser.getUserName());
    	newUser.setAvatar(sysUser.getAvatar());
    	newUser.setNickName(sysUser.getNickName());
    	newUser.setSex(sysUser.getSex());
        return AjaxResult.success("操作成功", newUser);
    }


}
