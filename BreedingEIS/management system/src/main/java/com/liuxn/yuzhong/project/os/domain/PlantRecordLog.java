package com.liuxn.yuzhong.project.os.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 植物记录日志对象 os_plant_record_log
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_plant_record_log")
public class PlantRecordLog implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 记录表id */
    @Excel(name = "记录表id")
    private Long recordId;

    /** 杂交id */
    @Excel(name = "杂交id")
    private Long hybridId;
    
    /** 种苗父id */
    @Excel(name = "植物父id")
    private Long plantParentId;

    /** 植物id */
    @Excel(name = "植物id")
    private Long plantId;
    
    /** 类型（1代表初选，2代表高接，3代表区试） */
    @Excel(name = "类型" , readConverterExp = "1=代表初选，2代表高接，3代表区试")
    private Integer plantType;

    /** 属性id */
    @Excel(name = "属性id")
    private Long attributeId;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String attributeName;

    /** 属性值 */
    @Excel(name = "属性值")
    private String attributeValue;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 记录年份 */
    @Excel(name = "记录年份")
    private String createYear;
}
