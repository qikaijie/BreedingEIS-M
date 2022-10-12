package com.liuxn.yuzhong.project.os.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecord;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordListVo;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordVo;

/**
 * 植被记录Service接口
 *
 * @author liuxn
 * @date 2021-05-05
 */
public interface ISeedlingRecordService extends IService<SeedlingRecord> {

	public boolean save(SeedlingRecordVo seedlingRecordVo);
	
	public boolean update(SeedlingRecordVo seedlingRecordVo);
	
	public List<SeedlingRecordListVo> queryListByUserId(Long userid, Long germplasmId, Long seedlingId, Date startTime, Date endTime);

	public List<SeedlingRecordListVo> historyList(Long seedlingId, Integer type);
	
	public List<SeedlingRecordListVo> queryList(SeedlingRecord seedlingRecord);
}
