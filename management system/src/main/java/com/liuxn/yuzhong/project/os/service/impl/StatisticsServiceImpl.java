package com.liuxn.yuzhong.project.os.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupRelation;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.mapper.AttributeGroupRelationMapper;
import com.liuxn.yuzhong.project.os.mapper.PlantMapper;
import com.liuxn.yuzhong.project.os.mapper.PlantRecordLogMapper;
import com.liuxn.yuzhong.project.os.service.IStatisticsService;

/**
 * 属性与分组关系Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
	
	@Autowired
	private PlantRecordLogMapper plantRecordLogMapper;
	
	@Autowired
	private PlantMapper plantMapper;

	@Override
	public AjaxResult statisticsHybrid(Long hybridId) {
		List<Attribute> attributeList = plantRecordLogMapper.queryPlantRecordAttribute(hybridId, null);
    	if(attributeList == null || attributeList.size() == 0)
    	{
    		return AjaxResult.success();
    	}
    	
    	LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        lqw.eq(Plant::getHybridId ,hybridId);
        lqw.orderByAsc(Plant::getNumber);
        List<Plant> plantList = plantMapper.selectList(lqw);
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
        	for(Attribute attribute : attributeList)
        	{
                PlantRecordLog plantRecordLog = plantRecordLogMapper.getAttributeLastLog(plant.getId(), attribute.getId());
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
}
