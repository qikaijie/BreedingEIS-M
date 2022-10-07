package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.PlantMapper;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.service.IPlantService;

/**
 * 植物Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class PlantServiceImpl extends ServiceImpl<PlantMapper, Plant> implements IPlantService {

	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<Plant> queryList(Plant plant)
	{
		return this.baseMapper.queryList(plant);
	}
	
	@Override
	public boolean removeByIds(List<Long> idList)
	{
		int result = this.baseMapper.removeByIds(idList);
		if(result >= 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public Long getMaxNumber(String fieldNumber)
	{
		Long maxNumber = baseMapper.getMaxNumber(fieldNumber);
		if(maxNumber == null)
		{
			return (long)0;
		}
		else
		{
			return maxNumber;
		}
	}
	
	@Override
	public Long getMaxGaojieNumber(Long parentId)
	{
		Long maxNumber = baseMapper.getMaxGaojieNumber(parentId);
		if(maxNumber == null)
		{
			return (long)0;
		}
		else
		{
			return maxNumber;
		}
	}
}
