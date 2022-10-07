package com.liuxn.yuzhong.project.os.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxn.yuzhong.project.os.domain.Plant;

/**
 * 植物Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface PlantMapper extends BaseMapper<Plant> {

	public List<Plant> queryList(Plant plant);
	
	public int removeByIds(@Param("ids") List<Long> idList);
	
	public Long getMaxNumber(@Param("fieldNumber")String fieldNumber);
	
	public Long getMaxGaojieNumber(@Param("parentId")Long parentId);
}
