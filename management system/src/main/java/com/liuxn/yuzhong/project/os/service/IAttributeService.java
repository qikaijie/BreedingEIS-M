package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeSortVo;
import com.liuxn.yuzhong.project.os.domain.AttributeVo;

/**
 * 属性池Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IAttributeService extends IService<Attribute> {
	
	public List<AttributeVo> queryList(Long speciesId, String fieldName);
	
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
