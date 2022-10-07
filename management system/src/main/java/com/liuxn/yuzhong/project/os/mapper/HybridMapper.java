package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.Hybrid;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 杂交组合库Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface HybridMapper extends BaseMapper<Hybrid> {

	public List<Hybrid> queryList(Hybrid hybrid);
	
	public int removeByIds(@Param("ids") List<Long> idList);
}
