package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeSortVo;
import com.liuxn.yuzhong.project.os.domain.AttributeVo;
import com.liuxn.yuzhong.project.os.mapper.AttributeGroupRelationMapper;
import com.liuxn.yuzhong.project.os.mapper.AttributeMapper;
import com.liuxn.yuzhong.project.os.service.IAttributeService;

/**
 * 属性池Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements IAttributeService {
	
	@Resource
	private AttributeMapper attributeMapper;
	
	@Resource
	private AttributeGroupRelationMapper attributeGroupRelationMapper;
	
	public List<AttributeVo> queryList(Long speciesId, String fieldName)
	{
		return attributeMapper.queryList(speciesId, fieldName);
	}
	
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
	public List<Attribute> queryAttributeExistentByGroupId(Long groupId) {
		return attributeMapper.queryAttributeExistentByGroupId(groupId);
	}
	
	public List<AttributeSortVo> queryAttributeSortByGroupId(Long groupId)
	{
		return attributeMapper.queryAttributeSortByGroupId(groupId);
	}
	
}
