package com.liuxn.yuzhong.project.os.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.domain.PlantRecord;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.service.IAttributeService;
import com.liuxn.yuzhong.project.os.service.IPlantRecordLogService;
import com.liuxn.yuzhong.project.os.service.IPlantRecordService;
import com.liuxn.yuzhong.project.os.service.IPlantService;
import com.liuxn.yuzhong.project.os.service.IStatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 属性池Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="管理端-统计管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/statistics" )
public class StatisticsController extends BaseController {

    private final IAttributeService iAttributeService;

    private final IPlantRecordLogService iPlantRecordLogService;
    
    private final IPlantRecordService iPlantRecordService;
    
    private final IPlantService iPlantService;
    
    private final IStatisticsService iStatisticsService; 
    
    /**
     * 查询属性池列表
     */
    @ApiOperation("杂交组合整体评价")
    @ApiImplicitParam(name="hybridId", value="杂交组合id", dataType="long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:statistics:hybrid')")
    @GetMapping("/hybrid")
    public AjaxResult statisticsHybrid(Long hybridId)
    {
        return iStatisticsService.statisticsHybrid(hybridId);
    }
    
    /**
     * 查询属性池列表
     */
    @ApiOperation("单株评价")
    @ApiImplicitParam(name="plantId", value="植物id", dataType="long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:statistics:plant')")
    @GetMapping("/plant")
    public AjaxResult statisticsPlant(Long plantId)
    {
    	List<String> yearList = iPlantRecordLogService.queryPlantRecordYear(null, plantId);
    	if(yearList == null || yearList.size() == 0)
    	{
    		return AjaxResult.success();
    	}
    	
    	List<Attribute> attributeList = iPlantRecordLogService.queryPlantRecordAttribute(null, plantId);
    	if(attributeList == null || attributeList.size() == 0)
    	{
    		return AjaxResult.success();
    	}
        
        List<JSONObject> attrValues = new ArrayList<JSONObject>();
        for(String year : yearList)
        {
        	JSONObject json = new JSONObject();
        	json.put("year", year);
        	
        	int index = 0;
        	for(Attribute attribute : attributeList)
        	{
                PlantRecordLog plantRecordLog = iPlantRecordLogService.getAttributeLastLog(plantId, attribute.getId());
                if(plantRecordLog != null)
                {
                	json.put("value" + index, plantRecordLog.getAttributeValue());
                }
                else
                {
                	json.put("value" + index, "-");
                }
                
                index++;
        	}
        	
        	attrValues.add(json);
        }
        
        AjaxResult ajaxResult = AjaxResult.success("统计成功", attrValues);
        ajaxResult.put("attributeList", attributeList);
        return ajaxResult;
    }
    
    /**
     * 查询属性池列表
     */
    @ApiOperation("调查分布统计")
    @ApiImplicitParam(name="hybridId", value="杂交组合id", dataType="long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:statistics:year')")
    @GetMapping("/year")
    public AjaxResult statisticsYear(Long hybridId)
    {
    	List<String> yearList = iPlantRecordLogService.queryPlantRecordYear(hybridId, null);
    	if(yearList == null || yearList.size() == 0)
    	{
    		return AjaxResult.success();
    	}
    	
    	LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        lqw.eq(Plant::getHybridId ,hybridId);
        lqw.eq(Plant::getDelFlag, 0);//未删除
        lqw.orderByAsc(Plant::getNumber);
        List<Plant> plantList = iPlantService.list(lqw);
        if(plantList == null || plantList.size() == 0)
    	{
    		return AjaxResult.success();
    	}
        
        List<JSONObject> attrValues = new ArrayList<JSONObject>();
        for(Plant plant : plantList)
        {
        	JSONObject json = new JSONObject();
        	json.put("code", plant.getCode());
        	
        	int index = 0;
        	for(String year : yearList)
        	{
        		LambdaQueryWrapper<PlantRecord> prlqw = new LambdaQueryWrapper<PlantRecord>();
        		prlqw.eq(PlantRecord::getPlantId, plant.getId());
        		prlqw.eq(PlantRecord::getCreateYear, year);
                int count = iPlantRecordService.count(prlqw);
                json.put("value" + index, count);
                
                index++;
        	}
        	
        	attrValues.add(json);
        }
        
        AjaxResult ajaxResult = AjaxResult.success("统计成功", attrValues);
        ajaxResult.put("yearList", yearList);
        return ajaxResult;
    }
}
