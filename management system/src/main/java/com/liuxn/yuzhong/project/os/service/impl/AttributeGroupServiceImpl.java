package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeGroup;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupRelation;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;
import com.liuxn.yuzhong.project.os.mapper.AttributeGroupMapper;
import com.liuxn.yuzhong.project.os.mapper.AttributeGroupRelationMapper;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupService;

/**
 * 属性分组Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class AttributeGroupServiceImpl extends ServiceImpl<AttributeGroupMapper, AttributeGroup> implements IAttributeGroupService {
	
	@Resource
	private AttributeGroupMapper attributeGroupMapper;
	
	@Autowired
	private AttributeGroupRelationMapper attributeGroupRelationMapper;
	
	public boolean save(AttributeGroupVo attributeGroupVo)
	{
		try
		{
			//保存属性组信息
			attributeGroupMapper.insert(attributeGroupVo);
			
			//保存属性与属性组关系
			List<Attribute> attrList = attributeGroupVo.getAttributeList();
			if(attrList != null && attrList.size() > 0)
			{
				for(Attribute attr : attrList)
				{
					AttributeGroupRelation attributeGroupRelation = new AttributeGroupRelation();
					attributeGroupRelation.setGroupId(attributeGroupVo.getId());
					attributeGroupRelation.setAttributeId(attr.getId());
					attributeGroupRelation.setSort(0);
					attributeGroupRelation.setCreateTime(DateUtils.getNowDate());
					attributeGroupRelationMapper.insert(attributeGroupRelation);
				}
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean update(AttributeGroupVo attributeGroupVo)
	{
		try
		{
			//先删除分组下的所有属性
			LambdaQueryWrapper<AttributeGroupRelation> lqw = new LambdaQueryWrapper<AttributeGroupRelation>();
	    	lqw.eq(AttributeGroupRelation::getGroupId ,attributeGroupVo.getId());
			attributeGroupRelationMapper.delete(lqw);
			
			//更新属性组信息
			attributeGroupMapper.updateById(attributeGroupVo);
			
			//保存属性与属性组关系
			List<Attribute> attrList = attributeGroupVo.getAttributeList();
			if(attrList != null && attrList.size() > 0)
			{
				for(Attribute attr : attrList)
				{
					AttributeGroupRelation attributeGroupRelation = new AttributeGroupRelation();
					attributeGroupRelation.setGroupId(attributeGroupVo.getId());
					attributeGroupRelation.setAttributeId(attr.getId());
					attributeGroupRelation.setSort(0);
					attributeGroupRelation.setCreateTime(DateUtils.getNowDate());
					attributeGroupRelationMapper.insert(attributeGroupRelation);
				}
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<AttributeGroup> queryList(AttributeGroup attributeGroup)
	{
		return attributeGroupMapper.queryList(attributeGroup);
	}
	
	/**
	 * 查询物种的分组及分组下的属性集合
	 * @return
	 */
	public List<AttributeGroupVo> queryAttributeGroupVoBySpeciesId(Long speciesId, List<Long> userIds)
	{
		return attributeGroupMapper.queryAttributeGroupVoBySpeciesId(speciesId, userIds);
	}
	
	public AttributeGroupVo getAttributeGroupVoById(Long id)
	{
		return attributeGroupMapper.getAttributeGroupVoById(id);
	}

	@Override
	public void sort(Long[] groupIds)
	{
		if(groupIds != null && groupIds.length > 0) {
			for(int i = 0; i < groupIds.length; i++) {
				AttributeGroup attributeGroup = new AttributeGroup();
				attributeGroup.setId(groupIds[i]);
				attributeGroup.setSort(i);
				
				attributeGroupMapper.updateById(attributeGroup);
			}
		}
	}
}
