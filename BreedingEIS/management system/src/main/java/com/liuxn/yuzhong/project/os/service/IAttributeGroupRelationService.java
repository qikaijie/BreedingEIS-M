package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupRelation;

/**
 * 属性与分组关系Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IAttributeGroupRelationService extends IService<AttributeGroupRelation> {
	
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
	public void sort(Long[] relationIds);
}
