package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.PlantRecordLogMapper;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.service.IPlantRecordLogService;

/**
 * 植物记录日志Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class PlantRecordLogServiceImpl extends ServiceImpl<PlantRecordLogMapper, PlantRecordLog> implements IPlantRecordLogService {
	
	@Autowired
	private PlantRecordLogMapper plantRecordLogMapper;
	
	public List<Attribute> queryPlantRecordAttribute(Long hybridId, Long plantId)
	{
		return plantRecordLogMapper.queryPlantRecordAttribute(hybridId, plantId);
	}
	
	public List<String> queryPlantRecordYear(Long hybridId, Long plantId)
	{
		return plantRecordLogMapper.queryPlantRecordYear(hybridId, plantId);
	}
	
	public PlantRecordLog getAttributeLastLog(Long plantId, Long attributeId)
	{
		return plantRecordLogMapper.getAttributeLastLog(plantId, attributeId);
	}
}
