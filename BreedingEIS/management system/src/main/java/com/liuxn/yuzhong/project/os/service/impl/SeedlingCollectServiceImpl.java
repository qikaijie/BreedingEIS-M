package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.SeedlingCollectMapper;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollectVo;
import com.liuxn.yuzhong.project.os.service.ISeedlingCollectService;

/**
 * 用户收藏种植植物Service业务层处理
 *
 * @author liuxn
 * @date 2021-05-08
 */
@Service
public class SeedlingCollectServiceImpl extends ServiceImpl<SeedlingCollectMapper, SeedlingCollect> implements ISeedlingCollectService {
	
	@Autowired
	private SeedlingCollectMapper seedlingCollectMapper;
	
	@Override
	public List<SeedlingCollectVo> quertList(Long userId, Long germplasmId, String seedlingCode, String orderAttr, String orderType)
	{
		return seedlingCollectMapper.quertList(userId, germplasmId, seedlingCode, orderAttr, orderType);
	}
}
