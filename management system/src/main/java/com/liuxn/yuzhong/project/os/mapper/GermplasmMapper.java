package com.liuxn.yuzhong.project.os.mapper;

import com.liuxn.yuzhong.project.os.domain.Germplasm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 种质信息Mapper接口
 *
 * @author liuxn
 * @date 2021-05-05
 */
public interface GermplasmMapper extends BaseMapper<Germplasm> {

	public List<Germplasm> queryList(Germplasm germplasm);
	
	public int removeByIds(@Param("ids") List<Long> idList);
}
