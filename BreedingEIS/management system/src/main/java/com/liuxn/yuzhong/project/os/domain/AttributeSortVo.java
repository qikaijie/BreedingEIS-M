package com.liuxn.yuzhong.project.os.domain;

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
public class AttributeSortVo extends Attribute {

	private static final long serialVersionUID=1L;
	
	private Long attributeGroupRelationId;
}
