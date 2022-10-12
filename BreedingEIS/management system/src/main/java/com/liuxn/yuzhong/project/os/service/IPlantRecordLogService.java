package com.liuxn.yuzhong.project.os.service;

import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 植物记录日志Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IPlantRecordLogService extends IService<PlantRecordLog> {

	public List<Attribute> queryPlantRecordAttribute(Long hybridId, Long plantId);
	
	public List<String> queryPlantRecordYear(Long hybridId, Long plantId);
	
	public PlantRecordLog getAttributeLastLog(Long plantId, Long attributeId);
}
