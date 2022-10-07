package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 植物记录日志Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface PlantRecordLogMapper extends BaseMapper<PlantRecordLog> {

	public List<Attribute> queryPlantRecordAttribute(@Param("hybridId")Long hybridId, @Param("plantId")Long plantId);
	
	public List<String> queryPlantRecordYear(@Param("hybridId")Long hybridId, @Param("plantId")Long plantId);
	
	public PlantRecordLog getAttributeLastLog(@Param("plantId")Long plantId, @Param("attributeId")Long attributeId);
}
