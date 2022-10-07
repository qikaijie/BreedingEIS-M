package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupRelation;
import com.liuxn.yuzhong.project.os.mapper.AttributeGroupRelationMapper;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupRelationService;

/**
 * 属性与分组关系Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class AttributeGroupRelationServiceImpl extends ServiceImpl<AttributeGroupRelationMapper, AttributeGroupRelation> implements IAttributeGroupRelationService {
	
	@Autowired
	private AttributeGroupRelationMapper attributeGroupRelationMapper;

	/** 描述: .排序
	 * @param sortVo
	 * @author     king
	 * <p>Sample: 该方法使用样例</p>
	 * date        2020年6月20日
	 * -----------------------------------------------------------
	 * 修改人                                             修改日期                                   修改描述
	 * king                2020年6月20日               创建
	 * -----------------------------------------------------------
	 * @Version  Ver1.0
	 */
	public void sort(Long[] relationIds) {
		if(relationIds != null && relationIds.length > 0) {
			for(int i = 0; i < relationIds.length; i++) {
				AttributeGroupRelation attributeGroupRelation = new AttributeGroupRelation();
				attributeGroupRelation.setId(relationIds[i]);
				attributeGroupRelation.setSort(i);
				
				attributeGroupRelationMapper.updateById(attributeGroupRelation);
			}
		}
		
	}
}
