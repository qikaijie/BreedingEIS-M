package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.PlantRecord;
import com.liuxn.yuzhong.project.os.domain.PlantRecordListVo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 植被记录Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface PlantRecordMapper extends BaseMapper<PlantRecord> {

	public List<PlantRecordListVo> queryListByUserId(@Param("userId")Long userid, @Param("hybridId")Long hybridId, @Param("plantId")Long plantId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

	public List<PlantRecordListVo> historyList(@Param("plantId")Long plantId, @Param("type")Integer type);
	
	public List<PlantRecordListVo> queryList(PlantRecord plantRecord);

}
