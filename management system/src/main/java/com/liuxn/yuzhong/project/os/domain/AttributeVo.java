package com.liuxn.yuzhong.project.os.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 属性池对象 os_attribute
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class AttributeVo extends Attribute {

	private static final long serialVersionUID=1L;

	/** 属性分类id */
	private List<AttributeGroup> attributeGroupsList;
}
