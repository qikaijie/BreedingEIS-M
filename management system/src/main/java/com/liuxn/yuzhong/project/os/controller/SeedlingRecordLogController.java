package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordLog;
import com.liuxn.yuzhong.project.os.service.ISeedlingRecordLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 植物记录日志Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags="管理端-种质植物记录详细日志管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/seedlingRecordLog" )
public class SeedlingRecordLogController extends BaseController {

    private final ISeedlingRecordLogService iSeedlingRecordLogService;

    /**
     * 查询植物记录日志列表
     */
    @ApiOperation("查询记录日志列表")
    @ApiImplicitParam(name="seedlingRecordLog", value="seedlingRecordLog对象", dataType="SeedlingRecordLog", dataTypeClass=SeedlingRecordLog.class)
    @PreAuthorize("@ss.hasPermi('os:seedlingRecordLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeedlingRecordLog seedlingRecordLog)
    {
        startPage();
        LambdaQueryWrapper<SeedlingRecordLog> lqw = new LambdaQueryWrapper<SeedlingRecordLog>();
        if (seedlingRecordLog.getRecordId() != null){
            lqw.eq(SeedlingRecordLog::getRecordId ,seedlingRecordLog.getRecordId());
        }
        if (seedlingRecordLog.getGermplasmId() != null){
            lqw.eq(SeedlingRecordLog::getGermplasmId ,seedlingRecordLog.getGermplasmId());
        }
        if (seedlingRecordLog.getSeedlingParentId() != null){
            lqw.eq(SeedlingRecordLog::getSeedlingParentId ,seedlingRecordLog.getSeedlingParentId());
        }
        if (seedlingRecordLog.getSeedlingId() != null){
            lqw.eq(SeedlingRecordLog::getSeedlingId ,seedlingRecordLog.getSeedlingId());
        }
        if (seedlingRecordLog.getSeedlingType() != null){
            lqw.eq(SeedlingRecordLog::getSeedlingType ,seedlingRecordLog.getSeedlingType());
        }
        if (seedlingRecordLog.getAttributeId() != null){
            lqw.eq(SeedlingRecordLog::getAttributeId ,seedlingRecordLog.getAttributeId());
        }
        if (StringUtils.isNotBlank(seedlingRecordLog.getAttributeName())){
            lqw.like(SeedlingRecordLog::getAttributeName ,seedlingRecordLog.getAttributeName());
        }
        if (StringUtils.isNotBlank(seedlingRecordLog.getAttributeValue())){
            lqw.eq(SeedlingRecordLog::getAttributeValue ,seedlingRecordLog.getAttributeValue());
        }
        if (StringUtils.isNotBlank(seedlingRecordLog.getCreateYear())){
            lqw.eq(SeedlingRecordLog::getCreateYear ,seedlingRecordLog.getCreateYear());
        }
        List<SeedlingRecordLog> list = iSeedlingRecordLogService.list(lqw);
        return getDataTable(list);
    }

//    /**
//     * 导出植物记录日志列表
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecordLog:export')" )
//    @Log(title = "植物记录日志" , businessType = BusinessType.EXPORT)
//    @GetMapping("/export" )
//    public AjaxResult export(SeedlingRecordLog seedlingRecordLog) {
//        LambdaQueryWrapper<SeedlingRecordLog> lqw = new LambdaQueryWrapper<SeedlingRecordLog>(seedlingRecordLog);
//        List<SeedlingRecordLog> list = iSeedlingRecordLogService.list(lqw);
//        ExcelUtil<SeedlingRecordLog> util = new ExcelUtil<SeedlingRecordLog>(SeedlingRecordLog. class);
//        return util.exportExcel(list, "seedlingRecordLog" );
//    }
//
//    /**
//     * 获取植物记录日志详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecordLog:query')" )
//    @GetMapping(value = "/{id}" )
//    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
//        return AjaxResult.success(iSeedlingRecordLogService.getById(id));
//    }
//
//    /**
//     * 新增植物记录日志
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecordLog:add')" )
//    @Log(title = "植物记录日志" , businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SeedlingRecordLog seedlingRecordLog) {
//        return toAjax(iSeedlingRecordLogService.save(seedlingRecordLog) ? 1 : 0);
//    }
//
//    /**
//     * 修改植物记录日志
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecordLog:edit')" )
//    @Log(title = "植物记录日志" , businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SeedlingRecordLog seedlingRecordLog) {
//        return toAjax(iSeedlingRecordLogService.updateById(seedlingRecordLog) ? 1 : 0);
//    }
//
//    /**
//     * 删除植物记录日志
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecordLog:remove')" )
//    @Log(title = "植物记录日志" , businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}" )
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(iSeedlingRecordLogService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
//    }
}
