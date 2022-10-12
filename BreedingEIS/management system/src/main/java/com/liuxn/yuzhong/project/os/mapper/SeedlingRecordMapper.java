package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.SeedlingRecord;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordListVo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 植被记录Mapper接口
 *
 * @author liuxn
 * @date 2021-05-05
 */
public interface SeedlingRecordMapper extends BaseMapper<SeedlingRecord> {

	public List<SeedlingRecordListVo> queryListByUserId(@Param("userId")Long userId, @Param("germplasmId")Long germplasmId, @Param("seedlingId")Long seedlingId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

	public List<SeedlingRecordListVo> historyList(@Param("seedlingId")Long seedlingId, @Param("type")Integer type);
	
	public List<SeedlingRecordListVo> queryList(SeedlingRecord seedlingRecord);

}
