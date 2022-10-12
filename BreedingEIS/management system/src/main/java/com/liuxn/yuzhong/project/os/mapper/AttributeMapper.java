package com.liuxn.yuzhong.project.os.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeSortVo;
import com.liuxn.yuzhong.project.os.domain.AttributeVo;

/**
 * 属性池Mapper接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface AttributeMapper extends BaseMapper<Attribute> {
	
	public List<AttributeVo> queryList(@Param("speciesId")Long speciesId, @Param("fieldName")String fieldName);
	
	/** 描述: .查询分组下的属性集合
	 * @param groupId
	 * @return
	 * @author     king
	 * <p>Sample: 该方法使用样例</p>
	 * date        2020年6月20日
	 * -----------------------------------------------------------
	 * 修改人                                             修改日期                                   修改描述
	 * king                2020年6月20日               创建
	 * -----------------------------------------------------------
	 * @Version  Ver1.0
	 */
	public List<Attribute> queryAttributeExistentByGroupId(Long groupId);
	
	public List<AttributeSortVo> queryAttributeSortByGroupId(Long groupId);
}
