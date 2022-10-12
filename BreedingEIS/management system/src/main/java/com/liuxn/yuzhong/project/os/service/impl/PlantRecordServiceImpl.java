package com.liuxn.yuzhong.project.os.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.domain.PlantImage;
import com.liuxn.yuzhong.project.os.domain.PlantRecord;
import com.liuxn.yuzhong.project.os.domain.PlantRecordListVo;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.domain.PlantRecordVo;
import com.liuxn.yuzhong.project.os.mapper.PlantImageMapper;
import com.liuxn.yuzhong.project.os.mapper.PlantMapper;
import com.liuxn.yuzhong.project.os.mapper.PlantRecordLogMapper;
import com.liuxn.yuzhong.project.os.mapper.PlantRecordMapper;
import com.liuxn.yuzhong.project.os.service.IPlantRecordService;

/**
 * 植被记录Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class PlantRecordServiceImpl extends ServiceImpl<PlantRecordMapper, PlantRecord> implements IPlantRecordService {

	@Autowired
	private PlantRecordMapper plantRecordMapper;
	
	@Autowired
	private PlantMapper plantMapper;
	
	@Autowired
	private PlantRecordLogMapper plantRecordLogMapper;
	
	@Autowired
	private PlantImageMapper plantImageMapper;
	
	@Transactional
	public void save(PlantRecordVo plantRecordVo) throws Exception
	{
		try
		{
			Plant plant = plantMapper.selectById(plantRecordVo.getPlantId());
        	if(plant != null)
        	{
        		plantRecordVo.setPlantParentId(plant.getParentId());
        		plantRecordVo.setPlantType(plant.getType());
        	}
        	
			//1、保存记录
			plantRecordVo.setCreateTime(DateUtils.getNowDate());
			plantRecordVo.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
			plantRecordMapper.insert(plantRecordVo);
			
			//2、保存记录日志
			JSONArray jsonArray = new JSONArray();
			for(PlantRecordLog log : plantRecordVo.getLogList())
			{
				if(plant != null)
	        	{
					log.setPlantParentId(plant.getParentId());
					log.setPlantType(plant.getType());
	        	}
				log.setHybridId(plantRecordVo.getHybridId());
				log.setPlantId(plantRecordVo.getPlantId());
				log.setRecordId(plantRecordVo.getId());
				log.setCreateTime(DateUtils.getNowDate());
				log.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
				plantRecordLogMapper.insert(log);
				
				JSONObject json = new JSONObject();
				json.put("id", log.getAttributeId());
				json.put("name", log.getAttributeName());
				json.put("value", log.getAttributeValue());
				jsonArray.add(json);
			}
			
			log.error("属性集合：" + jsonArray.toJSONString());
			//3、更新记录
			plantRecordVo.setAttributeValues(jsonArray.toJSONString());
			plantRecordMapper.updateById(plantRecordVo);
			
			//4、保存记录图片
			if(plantRecordVo.getImgList() != null && plantRecordVo.getImgList().size() > 0)
			{
				for(PlantImage img : plantRecordVo.getImgList())
				{
					img.setHybridId(plantRecordVo.getHybridId());
					img.setPlantId(plantRecordVo.getPlantId());
					img.setRecordId(plantRecordVo.getId());
					img.setCreateById(plantRecordVo.getCreateById());
					img.setCreateByName(plantRecordVo.getCreateByName());
					img.setCreateTime(DateUtils.getNowDate());
					plantImageMapper.insert(img);
				}
			}
		}
		catch (Exception e) {
			
			log.error("保存植物记录失败", e);
			throw new RuntimeException("保存植物记录失败");
		}
	}
	
	public boolean update(PlantRecordVo plantRecordVo)
	{
		try
		{
			//1、删除记录日志
			LambdaQueryWrapper<PlantRecordLog> lqw = new LambdaQueryWrapper<PlantRecordLog>();
			lqw.eq(PlantRecordLog::getRecordId, plantRecordVo.getId());
			plantRecordLogMapper.delete(lqw);
			
			Plant plant = plantMapper.selectById(plantRecordVo.getPlantId());

			//2、保存记录日志
			JSONArray jsonArray = new JSONArray();
			for(PlantRecordLog log : plantRecordVo.getLogList())
			{
				if(plant != null)
	        	{
					log.setPlantParentId(plant.getParentId());
					log.setPlantType(plant.getType());
	        	}
				log.setHybridId(plantRecordVo.getHybridId());
				log.setPlantId(plantRecordVo.getPlantId());
				log.setRecordId(plantRecordVo.getId());
				log.setCreateTime(DateUtils.getNowDate());
				log.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
				plantRecordLogMapper.insert(log);
				
				JSONObject json = new JSONObject();
				json.put("id", log.getAttributeId());
				json.put("name", log.getAttributeName());
				json.put("value", log.getAttributeValue());
				jsonArray.add(json);
			}
			
			log.error("属性集合：" + jsonArray.toJSONString());
			//3、更新记录
			plantRecordVo.setAttributeValues(jsonArray.toJSONString());
			plantRecordVo.setCreateTime(DateUtils.getNowDate());
			plantRecordVo.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
			plantRecordMapper.updateById(plantRecordVo);
			
			//4、保存记录图片(图片要累加)
			for(PlantImage img : plantRecordVo.getImgList())
			{
				img.setHybridId(plantRecordVo.getHybridId());
				img.setPlantId(plantRecordVo.getPlantId());
				img.setRecordId(plantRecordVo.getId());
				img.setCreateById(plantRecordVo.getCreateById());
				img.setCreateByName(plantRecordVo.getCreateByName());
				img.setCreateTime(DateUtils.getNowDate());
				plantImageMapper.insert(img);
			}
			
			return true;
		}
		catch (Exception e) {
			
			log.error("保存植物记录失败", e);
			return false;
		}
	}
	
	@Override
	public List<PlantRecordListVo> queryListByUserId(Long userid, Long hybridId, Long plantId, Date startTime, Date endTime)
	{
		return plantRecordMapper.queryListByUserId(userid, hybridId, plantId, startTime, endTime);
	}
	
	@Override
	public List<PlantRecordListVo> historyList(Long plantId, Integer type)
	{
		return plantRecordMapper.historyList(plantId, type);
	}
	
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<PlantRecordListVo> queryList(PlantRecord plantRecord) {
		
		return plantRecordMapper.queryList(plantRecord);
	}
}
