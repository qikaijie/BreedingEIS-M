package com.liuxn.yuzhong.project.os.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantCollectVo;

/**
 * 用户收藏植物Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface PlantCollectMapper extends BaseMapper<PlantCollect> {

	public List<PlantCollectVo> querList(@Param("userId")Long userId, @Param("hybridId")Long hybridId, @Param("plantCode")String plantCode, @Param("orderAttr")String orderAttr, @Param("orderType")String orderType);
}
