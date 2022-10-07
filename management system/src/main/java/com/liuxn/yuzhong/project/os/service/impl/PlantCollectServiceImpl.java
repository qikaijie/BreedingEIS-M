package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantCollectVo;
import com.liuxn.yuzhong.project.os.mapper.PlantCollectMapper;
import com.liuxn.yuzhong.project.os.service.IPlantCollectService;

/**
 * 用户收藏植物Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class PlantCollectServiceImpl extends ServiceImpl<PlantCollectMapper, PlantCollect> implements IPlantCollectService {

	@Autowired
	private PlantCollectMapper PlantCollectMapper;
	
	public List<PlantCollectVo> querList(Long userId, Long hybridId, String plantCode, String orderAttr, String orderType)
	{
		return PlantCollectMapper.querList(userId, hybridId, plantCode, orderAttr, orderType);
	}
}
