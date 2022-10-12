package com.liuxn.yuzhong.project.os.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel.Type;
import com.liuxn.yuzhong.framework.web.domain.OSBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 植物对象 os_seedling
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_seedling")
public class Seedling extends OSBaseEntity {

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 父id */
    @Excel(name = "父id")
    private Long parentId;

    /** 种质ID */
    @Excel(name = "种质ID")
    private Long germplasmId;

    /** 类型（1代表初选，2代表高接，3代表区试） */
    @Excel(name = "类型" , readConverterExp = "1=代表初选，2代表高接，3代表区试")
    private Integer type;

    /** 杂交代号（杂交组合的播种代号） */
    @Excel(name = "杂交代号" , readConverterExp = "杂=交组合的播种代号")
    private String sowingCode;

    /** 种植基地 */
    @Excel(name = "种植基地")
    private String seedlingBase;

    /** 种植区域 */
    @Excel(name = "种植区域")
    private String seedlingArea;

    /** 种植垄 */
    @Excel(name = "种植垄")
    private String seedlingRidge;

    /** 种植行 */
    @Excel(name = "种植行")
    private String seedlingRow;

    /** 种植-植物代号 */
    @Excel(name = "种植-植物代号")
    private Long seedlingNumber;

    /** 条码1 */
    @Excel(name = "条码1")
    private String codeOne;

    /** 田间编号 */
    @Excel(name = "田间编号")
    private String fieldNumber;

    /** 田间-植物代号 */
    @Excel(name = "田间-植物代号")
    private Long number;

    /** 条码2 */
    @Excel(name = "条码2")
    private String codeTwo;

    /** 完整条码（1+2） */
    @Excel(name = "完整条码" , readConverterExp = "1=+2")
    private String code;
    
    /** 删除标志（0代表存在 2代表删除） */
    private Integer delFlag;
    
    /** $column.columnComment */
    private Long createById;

    /** $column.columnComment */
    @Excel(name = "填报人", type = Type.EXPORT)
    private String createByName;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
