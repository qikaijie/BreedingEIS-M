package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.Feedback;
import com.liuxn.yuzhong.project.os.service.IFeedbackService;

import lombok.AllArgsConstructor;

/**
 * 意见反馈Controller
 *
 * @author liuxn
 * @date 2021-07-15
 */
@AllArgsConstructor
@RestController
@RequestMapping("/os/feedback" )
public class FeedbackController extends BaseController {

    private final IFeedbackService iFeedbackService;
    
    private final TokenService tokenService;

    /**
     * 查询意见反馈列表
     */
    @PreAuthorize("@ss.hasPermi('os:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(Feedback feedback)
    {
        startPage();
        LambdaQueryWrapper<Feedback> lqw = new LambdaQueryWrapper<Feedback>();
        if (StringUtils.isNotBlank(feedback.getContent())){
            lqw.eq(Feedback::getContent ,feedback.getContent());
        }
        if (feedback.getStatus() != null){
            lqw.eq(Feedback::getStatus ,feedback.getStatus());
        }
        List<Feedback> list = iFeedbackService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出意见反馈列表
     */
    @PreAuthorize("@ss.hasPermi('os:feedback:export')" )
    @Log(title = "意见反馈" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Feedback feedback) {
        LambdaQueryWrapper<Feedback> lqw = new LambdaQueryWrapper<Feedback>(feedback);
        List<Feedback> list = iFeedbackService.list(lqw);
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback. class);
        return util.exportExcel(list, "feedback" );
    }

    /**
     * 获取意见反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('os:feedback:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFeedbackService.getById(id));
    }

    /**
     * 新增意见反馈
     */
    @PreAuthorize("@ss.hasPermi('os:feedback:add')" )
    @Log(title = "意见反馈" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Feedback feedback) {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	
    	feedback.setUserId(loginUser.getUser().getUserId());
    	feedback.setChannel(0);//web端
    	feedback.setStatus(0);//待处理
    	feedback.setCreateTime(new Date());
    	
        return toAjax(iFeedbackService.save(feedback) ? 1 : 0);
    }

    /**
     * 修改意见反馈
     */
    @PreAuthorize("@ss.hasPermi('os:feedback:edit')" )
    @Log(title = "意见反馈" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Feedback feedback) {
    	
    	feedback.setStatus(1);//已处理
        return toAjax(iFeedbackService.updateById(feedback) ? 1 : 0);
    }

    /**
     * 删除意见反馈
     */
    @PreAuthorize("@ss.hasPermi('os:feedback:remove')" )
    @Log(title = "意见反馈" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFeedbackService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
