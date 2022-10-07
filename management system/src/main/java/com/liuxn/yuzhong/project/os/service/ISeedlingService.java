package com.liuxn.yuzhong.project.os.service;

import com.liuxn.yuzhong.project.os.domain.Seedling;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 植物Service接口
 *
 * @author liuxn
 * @date 2021-05-05
 */
public interface ISeedlingService extends IService<Seedling> {

	public List<Seedling> queryList(Seedling seedling);
	
	boolean removeByIds(List<Long> idList);
	
	Long getMaxNumber(String fieldNumber);
}
