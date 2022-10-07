package com.liuxn.yuzhong.project.os.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel.Color;
import com.liuxn.yuzhong.framework.web.domain.OSBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 种质信息对象 os_germplasm
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_germplasm")
public class Germplasm extends OSBaseEntity {

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 种质名称 */
    @Excel(name = "种质名称", color = Color.RED)
    private String name;

    /** 别名 */
    @Excel(name = "别名")
    private String alias;

    /** 编码 */
    @Excel(name = "编码", color = Color.RED)
    private String code;

    /** 来源 */
    @Excel(name = "来源")
    private String source;

    /** 种质类型 */
    @Excel(name = "种质类型")
    private String type;

    /** 种质类型备注 */
    @Excel(name = "种质类型备注({'breedingCompany':'', 'breedingMethod':'', 'breedingYear':''})")
    private String typeRemark;

    /** 来源地 */
    @Excel(name = "来源地")
    private String sourceArea;

    /** 原引进人 */
    @Excel(name = "原引进人")
    private String firstIntroducer;

    /** 原引进人联系电话 */
    @Excel(name = "原引进人联系电话")
    private String firstIntroducerPhone;

    /** 引进人 */
    @Excel(name = "引进人")
    private String introducer;

    /** 引进人联系电话 */
    @Excel(name = "引进人联系电话")
    private String introducerPhone;
    
    /** 引进方式 */
    @Excel(name = "引进年份", color = Color.RED)
    private String introductionYear;

    /** 引进方式 */
    @Excel(name = "引进方式")
    private String introductionMethod;

    /** 保存库 */
    @Excel(name = "保存库")
    private String libName;

    /** 保存方式 */
    @Excel(name = "保存方式")
    private String saveMethod;

    /** 特性描述 */
    @Excel(name = "特性描述")
    @TableField("`describe`")
    private String describe;

    /** 应用情况 */
    @Excel(name = "应用情况")
    private String application;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 删除标志（0代表存在 2代表删除） */
    private Integer delFlag;

    /** 创建者姓名 */
    private String createBy;

    /** 创建者id */
    @Excel(name = "创建者id")
    private Long createById;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /** 保存数量 */
    @Excel(name = "保存数量")
    private Long amount;

    /** 是否保护 */
    @Excel(name = "是否保护")
    private Integer isProtect;
}
