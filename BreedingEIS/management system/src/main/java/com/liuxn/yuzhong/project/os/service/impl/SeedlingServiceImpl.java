package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.SeedlingMapper;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Seedling;
import com.liuxn.yuzhong.project.os.service.ISeedlingService;

/**
 * 植物Service业务层处理
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Service
public class SeedlingServiceImpl extends ServiceImpl<SeedlingMapper, Seedling> implements ISeedlingService {

	@Autowired
	private SeedlingMapper seedlingMapper;
	
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<Seedling> queryList(Seedling seedling)
	{
		return seedlingMapper.queryList(seedling);
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
}
