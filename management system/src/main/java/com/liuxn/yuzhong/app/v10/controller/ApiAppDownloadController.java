package com.liuxn.yuzhong.app.v10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.AppDownload;
import com.liuxn.yuzhong.project.os.service.IAppDownloadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-app下载管理")
@RestController
@RequestMapping("/app/v10/download" )
public class ApiAppDownloadController extends APPBaseController {

	@Autowired
	private IAppDownloadService iAppDownloadService;

	/**
     * 查询最新的android版本
     */
    @ApiOperation("查询最新的android版本")
    @LoginRequired
    @GetMapping("/android")
    public AjaxResult android()
    {
        LambdaQueryWrapper<AppDownload> lqw = new LambdaQueryWrapper<AppDownload>();
        lqw.eq(AppDownload::getType, 0);//android
        List<AppDownload> list = iAppDownloadService.list(lqw);
        if(list != null && list.size() > 0)
        {
        	return AjaxResult.success(list.get(0));
        }
        else
        {
        	return AjaxResult.error("没有app的下载链接");
        }
    }
    
    /**
     * 查询最新的android版本
     */
    @ApiOperation("查询最新的android版本")
    @LoginRequired
    @GetMapping("/ios")
    public AjaxResult ios()
    {
        LambdaQueryWrapper<AppDownload> lqw = new LambdaQueryWrapper<AppDownload>();
        lqw.eq(AppDownload::getType, 1);//ios
        List<AppDownload> list = iAppDownloadService.list(lqw);
        if(list != null && list.size() > 0)
        {
        	return AjaxResult.success(list.get(0));
        }
        else
        {
        	return AjaxResult.error("没有app的下载链接");
        }
    }
}
