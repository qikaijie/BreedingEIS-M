package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.Plant;

/**
 * 植物Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IPlantService extends IService<Plant> {

	public List<Plant> queryList(Plant plant);
	
	boolean removeByIds(List<Long> idList);
	
	Long getMaxNumber(String fieldNumber);
	
	Long getMaxGaojieNumber(Long parentId);
}
