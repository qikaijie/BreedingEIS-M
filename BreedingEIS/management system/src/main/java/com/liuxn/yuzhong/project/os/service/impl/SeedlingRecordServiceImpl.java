package com.liuxn.yuzhong.project.os.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Seedling;
import com.liuxn.yuzhong.project.os.domain.SeedlingImage;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecord;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordListVo;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordLog;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordVo;
import com.liuxn.yuzhong.project.os.mapper.SeedlingImageMapper;
import com.liuxn.yuzhong.project.os.mapper.SeedlingMapper;
import com.liuxn.yuzhong.project.os.mapper.SeedlingRecordLogMapper;
import com.liuxn.yuzhong.project.os.mapper.SeedlingRecordMapper;
import com.liuxn.yuzhong.project.os.service.ISeedlingRecordService;

/**
 * 植被记录Service业务层处理
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Service
public class SeedlingRecordServiceImpl extends ServiceImpl<SeedlingRecordMapper, SeedlingRecord> implements ISeedlingRecordService {

	@Autowired
	private SeedlingRecordMapper seedlingRecordMapper;
	
	@Autowired
	private SeedlingMapper seedlingMapper;
	
	@Autowired
	private SeedlingRecordLogMapper SeedlingRecordLogMapper;
	
	@Autowired
	private SeedlingImageMapper SeedlingImageMapper;
	
	@Override
	public boolean save(SeedlingRecordVo seedlingRecordVo)
	{
		try
		{
        	Seedling seedling = seedlingMapper.selectById(seedlingRecordVo.getSeedlingId());
        	if(seedling != null)
        	{
        		seedlingRecordVo.setSeedlingParentId(seedling.getParentId());
        		seedlingRecordVo.setSeedlingType(seedling.getType());
        	}
			//1、保存记录
			seedlingRecordVo.setCreateTime(DateUtils.getNowDate());
			seedlingRecordVo.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
			seedlingRecordMapper.insert(seedlingRecordVo);
			
			//2、保存记录日志
			JSONArray jsonArray = new JSONArray();
			for(SeedlingRecordLog log : seedlingRecordVo.getLogList())
			{
				if(seedling != null)
	        	{
					log.setSeedlingParentId(seedling.getParentId());
					log.setSeedlingType(seedling.getType());
	        	}
				log.setGermplasmId(seedlingRecordVo.getGermplasmId());
				log.setSeedlingId(seedlingRecordVo.getSeedlingId());
				log.setRecordId(seedlingRecordVo.getId());
				log.setCreateTime(DateUtils.getNowDate());
				log.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
				SeedlingRecordLogMapper.insert(log);
				
				JSONObject json = new JSONObject();
				json.put("id", log.getAttributeId());
				json.put("name", log.getAttributeName());
				json.put("value", log.getAttributeValue());
				jsonArray.add(json);
			}
			
			log.error("属性集合：" + jsonArray.toJSONString());
			//3、更新记录
			seedlingRecordVo.setAttributeValues(jsonArray.toJSONString());
			seedlingRecordMapper.updateById(seedlingRecordVo);
			
			//4、保存记录图片
			for(SeedlingImage img : seedlingRecordVo.getImgList())
			{
				img.setGermplasmId(seedlingRecordVo.getGermplasmId());
				img.setSeedlingId(seedlingRecordVo.getSeedlingId());
				img.setRecordId(seedlingRecordVo.getId());
				img.setCreateById(seedlingRecordVo.getCreateById());
				img.setCreateByName(seedlingRecordVo.getCreateByName());
				img.setCreateTime(DateUtils.getNowDate());
				SeedlingImageMapper.insert(img);
			}
			
			return true;
		}
		catch (Exception e) {
			
			log.error("保存植物记录失败", e);
			return false;
		}
	}
	
	@Override
	public boolean update(SeedlingRecordVo seedlingRecordVo)
	{
		try
		{
			//1、删除记录日志
			LambdaQueryWrapper<SeedlingRecordLog> lqw = new LambdaQueryWrapper<SeedlingRecordLog>();
			lqw.eq(SeedlingRecordLog::getRecordId, seedlingRecordVo.getId());
			SeedlingRecordLogMapper.delete(lqw);
			
			Seedling seedling = seedlingMapper.selectById(seedlingRecordVo.getSeedlingId());
			//2、保存记录日志
			JSONArray jsonArray = new JSONArray();
			for(SeedlingRecordLog log : seedlingRecordVo.getLogList())
			{
				if(seedling != null)
	        	{
					log.setSeedlingParentId(seedling.getParentId());
					log.setSeedlingType(seedling.getType());
	        	}
				
				log.setGermplasmId(seedlingRecordVo.getGermplasmId());
				log.setSeedlingId(seedlingRecordVo.getSeedlingId());
				log.setRecordId(seedlingRecordVo.getId());
				log.setCreateTime(DateUtils.getNowDate());
				log.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
				SeedlingRecordLogMapper.insert(log);
				
				JSONObject json = new JSONObject();
				json.put("id", log.getAttributeId());
				json.put("name", log.getAttributeName());
				json.put("value", log.getAttributeValue());
				jsonArray.add(json);
			}
			
			log.error("属性集合：" + jsonArray.toJSONString());
			//3、更新记录
			seedlingRecordVo.setAttributeValues(jsonArray.toJSONString());
			seedlingRecordVo.setCreateTime(DateUtils.getNowDate());
			seedlingRecordVo.setCreateYear(DateUtils.dateTimeNow(DateUtils.YYYY));
			seedlingRecordMapper.updateById(seedlingRecordVo);
			
			//4、保存记录图片(图片要累加)
			for(SeedlingImage img : seedlingRecordVo.getImgList())
			{
				img.setGermplasmId(seedlingRecordVo.getGermplasmId());
				img.setSeedlingId(seedlingRecordVo.getSeedlingId());
				img.setRecordId(seedlingRecordVo.getId());
				img.setCreateById(seedlingRecordVo.getCreateById());
				img.setCreateByName(seedlingRecordVo.getCreateByName());
				img.setCreateTime(DateUtils.getNowDate());
				SeedlingImageMapper.insert(img);
			}
			
			return true;
		}
		catch (Exception e) {
			
			log.error("保存植物记录失败", e);
			return false;
		}
	}
	
	@Override
	public List<SeedlingRecordListVo> queryListByUserId(Long userId, Long germplasmId, Long seedlingId, Date startTime, Date endTime)
	{
		return seedlingRecordMapper.queryListByUserId(userId, germplasmId, seedlingId, startTime, endTime);
	}
	
	@Override
	public List<SeedlingRecordListVo> historyList(Long seedlingId, Integer type)
	{
		return seedlingRecordMapper.historyList(seedlingId, type);
	}

	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SeedlingRecordListVo> queryList(SeedlingRecord seedlingRecord) {
		
		return seedlingRecordMapper.queryList(seedlingRecord);
	}
	
	
}
