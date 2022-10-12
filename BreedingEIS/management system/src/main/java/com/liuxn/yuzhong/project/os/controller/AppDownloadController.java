package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.List;

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
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.AppDownload;
import com.liuxn.yuzhong.project.os.service.IAppDownloadService;

import lombok.AllArgsConstructor;

/**
 * app版本下载Controller
 *
 * @author liuxn
 * @date 2021-07-17
 */
@AllArgsConstructor
@RestController
@RequestMapping("/os/appdown" )
public class AppDownloadController extends BaseController {

    private final IAppDownloadService iAppDownloadService;

    /**
     * 查询app版本下载列表
     */
    @PreAuthorize("@ss.hasPermi('os:appdown:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppDownload appDownload)
    {
        startPage();
        LambdaQueryWrapper<AppDownload> lqw = new LambdaQueryWrapper<AppDownload>();
        if (appDownload.getType() != null){
            lqw.eq(AppDownload::getType ,appDownload.getType());
        }
        if (StringUtils.isNotBlank(appDownload.getVersion())){
            lqw.eq(AppDownload::getVersion ,appDownload.getVersion());
        }
        if (StringUtils.isNotBlank(appDownload.getDownUrl())){
            lqw.eq(AppDownload::getDownUrl ,appDownload.getDownUrl());
        }
        List<AppDownload> list = iAppDownloadService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出app版本下载列表
     */
    @PreAuthorize("@ss.hasPermi('os:appdown:export')" )
    @Log(title = "app版本下载" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(AppDownload appDownload) {
        LambdaQueryWrapper<AppDownload> lqw = new LambdaQueryWrapper<AppDownload>(appDownload);
        List<AppDownload> list = iAppDownloadService.list(lqw);
        ExcelUtil<AppDownload> util = new ExcelUtil<AppDownload>(AppDownload. class);
        return util.exportExcel(list, "appdown" );
    }

    /**
     * 获取app版本下载详细信息
     */
    @PreAuthorize("@ss.hasPermi('os:appdown:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iAppDownloadService.getById(id));
    }

    /**
     * 新增app版本下载
     */
    @PreAuthorize("@ss.hasPermi('os:appdown:add')" )
    @Log(title = "app版本下载" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppDownload appDownload) {
        return toAjax(iAppDownloadService.save(appDownload) ? 1 : 0);
    }

    /**
     * 修改app版本下载
     */
    @PreAuthorize("@ss.hasPermi('os:appdown:edit')" )
    @Log(title = "app版本下载" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppDownload appDownload) {
        return toAjax(iAppDownloadService.updateById(appDownload) ? 1 : 0);
    }

    /**
     * 删除app版本下载
     */
    @PreAuthorize("@ss.hasPermi('os:appdown:remove')" )
    @Log(title = "app版本下载" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iAppDownloadService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
