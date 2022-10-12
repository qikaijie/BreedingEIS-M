package com.liuxn.yuzhong.project.os.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxn.yuzhong.project.os.domain.AttributeGroup;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;

/**
 * 属性分组Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface AttributeGroupMapper extends BaseMapper<AttributeGroup> {
	
	public List<AttributeGroup> queryList(AttributeGroup attributeGroup);
	/**
	 * 查询分组及分组下的属性集合
	 * @return
	 */
	public List<AttributeGroupVo> queryAttributeGroupVoBySpeciesId(@Param("speciesId")Long speciesId, @Param("userIds")List<Long> userIds);
	
	public AttributeGroupVo getAttributeGroupVoById(@Param("id")Long id);
}
