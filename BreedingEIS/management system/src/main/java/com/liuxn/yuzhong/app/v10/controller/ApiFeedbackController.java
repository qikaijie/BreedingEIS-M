package com.liuxn.yuzhong.app.v10.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.Feedback;
import com.liuxn.yuzhong.project.os.service.IFeedbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-用户反馈管理")
@RestController
@RequestMapping("/app/v10/feedback" )
public class ApiFeedbackController extends APPBaseController {

	@Autowired
    private IFeedbackService iFeedbackService;

	/**
     * 新增意见反馈
     */
	@ApiOperation("用户意见反馈")
	@ApiParam(name = "feedback", value = "反馈信息", required = true)
	@LoginRequired
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Feedback feedback) {

    	feedback.setUserId(getLoginUser().getUserId());
    	feedback.setStatus(0);//待处理
    	feedback.setCreateTime(new Date());
    	
        return toAjax(iFeedbackService.save(feedback) ? 1 : 0);
    }

	/**
     * 查询用户意见反馈列表
     */
    @ApiOperation("查询用户意见反馈列表")
    @LoginRequired
    @GetMapping("/list")
    public AjaxResult list(Long hybridId, String plantCode, String prop, String order)
    {
        startPage();
        
        LambdaQueryWrapper<Feedback> lqw = new LambdaQueryWrapper<Feedback>();
        lqw.eq(Feedback::getUserId, getLoginUser().getUserId());
        List<Feedback> list = iFeedbackService.list(lqw);
        
        AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
}
