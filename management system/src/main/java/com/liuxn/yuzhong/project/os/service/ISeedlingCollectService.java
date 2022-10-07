package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollectVo;

/**
 * 用户收藏种植植物Service接口
 *
 * @author liuxn
 * @date 2021-05-08
 */
public interface ISeedlingCollectService extends IService<SeedlingCollect> {

	public List<SeedlingCollectVo> quertList(Long userId, Long germplasmId, String seedlingCode, String orderAttr, String orderType);
}
