package com.liuxn.yuzhong.project.os.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.PlantRecord;
import com.liuxn.yuzhong.project.os.domain.PlantRecordListVo;
import com.liuxn.yuzhong.project.os.domain.PlantRecordVo;

/**
 * 植被记录Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IPlantRecordService extends IService<PlantRecord> {

	public void save(PlantRecordVo plantRecordVo) throws Exception;
	
	public boolean update(PlantRecordVo plantRecordVo);
	
	public List<PlantRecordListVo> queryListByUserId(Long userid, Long hybridId, Long plantId, Date startTime, Date endTime);
	
	public List<PlantRecordListVo> historyList(Long plantId, Integer type);
	
	public List<PlantRecordListVo> queryList(PlantRecord plantRecord);
}
