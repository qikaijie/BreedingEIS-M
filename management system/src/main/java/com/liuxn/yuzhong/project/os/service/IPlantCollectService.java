package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantCollectVo;

/**
 * 用户收藏植物Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IPlantCollectService extends IService<PlantCollect> {

	public List<PlantCollectVo> querList(Long userId, Long hybridId, String plantCode, String orderAttr, String orderType);
}
