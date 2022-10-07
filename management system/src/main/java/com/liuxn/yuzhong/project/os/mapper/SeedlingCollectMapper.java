package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollectVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户收藏种植植物Mapper接口
 *
 * @author liuxn
 * @date 2021-05-08
 */
public interface SeedlingCollectMapper extends BaseMapper<SeedlingCollect> {

	public List<SeedlingCollectVo> quertList(@Param("userId")Long userId, @Param("germplasmId")Long germplasmId, @Param("seedlingCode")String seedlingCode, @Param("orderAttr")String orderAttr, @Param("orderType")String orderType);
}
