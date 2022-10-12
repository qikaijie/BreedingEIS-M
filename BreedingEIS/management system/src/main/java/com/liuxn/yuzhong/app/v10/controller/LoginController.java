package com.liuxn.yuzhong.app.v10.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.app.constant.Constant;
import com.liuxn.yuzhong.app.util.JwtUtils;
import com.liuxn.yuzhong.common.utils.MessageUtils;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.system.domain.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "移动端-登录管理")
@RestController
@RequestMapping("/app/v10")
public class LoginController extends APPBaseController{
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;

    @ApiOperation("用户登录")
    @ApiParam(name = "sysUser", value = "用户信息", required = true)
    @PostMapping("/login")
    public AjaxResult login(@RequestBody SysUser sysUser)
    {
    	if(sysUser == null || StringUtils.isEmpty(sysUser.getUserName()) || StringUtils.isEmpty(sysUser.getPassword()))
    	{
    		return AjaxResult.error("用户名或者密码不能为空");
    	}
    	// 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword()));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                return AjaxResult.error(MessageUtils.message("user.password.not.match"));
            }
            else
            {
            	return AjaxResult.error(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        
        
        LoginUser newLoginUser = new LoginUser();
        // 生成token
        String token = jwtUtils.generateToken(loginUser.getUser().getUserId()+"");
        newLoginUser.setToken(token);
        
        SysUser newUser = new SysUser();
    	newUser.setUserId(loginUser.getUser().getUserId());
    	newUser.setUserName(loginUser.getUser().getUserName());
    	newUser.setAvatar(loginUser.getUser().getAvatar());
    	newUser.setNickName(loginUser.getUser().getNickName());
    	newUser.setSex(loginUser.getUser().getSex());
    	newUser.setSpeciesId(loginUser.getUser().getSpeciesId());
    	newLoginUser.setUser(newUser);
        
        return AjaxResult.success("登录成功", newLoginUser);
    }
    
    @ApiOperation("用户退出")
    @GetMapping("/logout")
    public AjaxResult logout(HttpServletRequest request)
    {
    	try
    	{
	        //需要验证，获取用户凭证
	        String token = request.getHeader(Constant.CURRENT_TOKEN);
	
	        if(StringUtils.isEmpty(token)){
	            token = request.getParameter(Constant.CURRENT_TOKEN);
	        }
	    	// 删除用户缓存记录
	    	tokenService.delLoginUser(token);
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return AjaxResult.success("退出成功");
    }

}
