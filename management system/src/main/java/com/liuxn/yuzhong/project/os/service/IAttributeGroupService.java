package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.AttributeGroup;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;

/**
 * 属性分组Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IAttributeGroupService extends IService<AttributeGroup> {
	
	public boolean save(AttributeGroupVo attributeGroupVo);
	
	public boolean update(AttributeGroupVo attributeGroupVo);
	
	public List<AttributeGroup> queryList(AttributeGroup attributeGroup);

	/**
	 * 查询物种的分组及分组下的属性集合
	 * @return
	 */
	public List<AttributeGroupVo> queryAttributeGroupVoBySpeciesId(Long speciesId, List<Long> userIds);
	
	public AttributeGroupVo getAttributeGroupVoById(Long id);
	
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
	public void sort(Long[] groupIds);
}
