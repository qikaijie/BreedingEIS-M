package com.liuxn.yuzhong.project.os.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 属性分组对象 os_attribute_group
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Accessors(chain = true)
public class AttributeGroupVo extends AttributeGroup {

	private static final long serialVersionUID=1L;

    private List<Attribute> attributeList;
}
