package com.liuxn.yuzhong.project.os.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;

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
@TableName("os_attribute")
public class Attribute implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @Excel(name = "物种id")
    private Long speciesId;

    /** 字段类型：text，input，radio，checkbox */
    @Excel(name = "字段类型：text，input，radio，checkbox，date")
    private String fieldType;

    /** 字段code */
    @Excel(name = "字段code")
    private String fieldCode;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String fieldName;

    /** 控件内容，“|”隔开 */
    @Excel(name = "控件内容，“|”隔开")
    private String fieldContent;

    /** 排序 */
    @Excel(name = "排序")
    private int sort;

    /** 创建者id */
    @Excel(name = "创建者id")
    private Long createById;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建者名称 */
    @Excel(name = "创建者名称")
    private String createByName;
    
    @TableField(exist = false)
    private String value;
}
