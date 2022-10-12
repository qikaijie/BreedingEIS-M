package com.liuxn.yuzhong.project.os.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;
import com.liuxn.yuzhong.framework.web.domain.BaseEntity;

/**
 * 植物记录日志对象 os_seedling_record_log
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_seedling_record_log")
public class SeedlingRecordLog implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 记录表id */
    @Excel(name = "记录表id")
    private Long recordId;

    /** 种质id */
    @Excel(name = "种质id")
    private Long germplasmId;

    /** 种苗父id */
    @Excel(name = "种苗父id")
    private Long seedlingParentId;

    /** 种苗id */
    @Excel(name = "种苗id")
    private Long seedlingId;

    /** 类型（1代表初选，2代表高接，3代表区试） */
    @Excel(name = "类型" , readConverterExp = "1=代表初选，2代表高接，3代表区试")
    private Integer seedlingType;

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
    private Date createTime;

    /** 记录年份 */
    @Excel(name = "记录年份")
    private String createYear;
}
