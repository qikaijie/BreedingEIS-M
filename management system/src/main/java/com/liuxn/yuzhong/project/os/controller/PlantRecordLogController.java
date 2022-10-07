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
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.service.IPlantRecordLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 植物记录日志Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="管理端-杂交植物记录详细日志管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/plant/record/log" )
public class PlantRecordLogController extends BaseController {

    private final IPlantRecordLogService iPlantRecordLogService;

    /**
     * 查询植物记录日志列表
     */
    @ApiOperation("查询记录日志列表")
    @ApiImplicitParam(name="plantRecordLog", value="plantRecordLog对象", dataType="PlantRecordLog", dataTypeClass=PlantRecordLog.class)
    @PreAuthorize("@ss.hasPermi('os:plant/record/log:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlantRecordLog plantRecordLog)
    {
        startPage();
        LambdaQueryWrapper<PlantRecordLog> lqw = new LambdaQueryWrapper<PlantRecordLog>();
        if (plantRecordLog.getRecordId() != null){
            lqw.eq(PlantRecordLog::getRecordId ,plantRecordLog.getRecordId());
        }
        if (plantRecordLog.getHybridId() != null){
            lqw.eq(PlantRecordLog::getHybridId ,plantRecordLog.getHybridId());
        }
        if (plantRecordLog.getPlantId() != null){
            lqw.eq(PlantRecordLog::getPlantId ,plantRecordLog.getPlantId());
        }
        if (plantRecordLog.getAttributeId() != null){
            lqw.eq(PlantRecordLog::getAttributeId ,plantRecordLog.getAttributeId());
        }
        if (StringUtils.isNotBlank(plantRecordLog.getAttributeName())){
            lqw.like(PlantRecordLog::getAttributeName ,plantRecordLog.getAttributeName());
        }
        if (StringUtils.isNotBlank(plantRecordLog.getAttributeValue())){
            lqw.eq(PlantRecordLog::getAttributeValue ,plantRecordLog.getAttributeValue());
        }
        if (StringUtils.isNotBlank(plantRecordLog.getCreateYear())){
            lqw.eq(PlantRecordLog::getCreateYear ,plantRecordLog.getCreateYear());
        }
        List<PlantRecordLog> list = iPlantRecordLogService.list(lqw);
        return getDataTable(list);
    }
}
