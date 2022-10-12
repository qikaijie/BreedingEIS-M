package com.liuxn.yuzhong.project.os.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.liuxn.yuzhong.framework.web.domain.OSBaseEntity;

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
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_attribute_group")
public class AttributeGroup extends OSBaseEntity {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String name;
    
    @Excel(name = "物种id")
    private Long speciesId;
    
    /** 排序 */
    @Excel(name = "排序")
    private int sort;
    
    /** 是否隐藏 */
    @Excel(name = "是否隐藏")
    private int isHidden;
    
    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createById;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String createByName;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
