package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.Seedling;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 植物Mapper接口
 *
 * @author liuxn
 * @date 2021-05-05
 */
public interface SeedlingMapper extends BaseMapper<Seedling> {

	public List<Seedling> queryList(Seedling seedling);
	
	public int removeByIds(@Param("ids") List<Long> idList);
	
	public Long getMaxNumber(@Param("fieldNumber")String fieldNumber);
}
