package com.liuxn.yuzhong.project.os.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel.Color;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel.Type;
import com.liuxn.yuzhong.framework.web.domain.OSBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 杂交组合库对象 os_hybrid
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_hybrid")
public class Hybrid extends OSBaseEntity {

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /** 杂交名称 */
    @Excel(name = "杂交名称", type = Type.EXPORT)
    private String name;
    
    /** 杂交日期 */
    @Excel(name = "杂交日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hybridDate;

    /** 父本 */
    @Excel(name = "父本", color = Color.RED)
    private String maleParent;

    /** 父本来源 */
    @Excel(name = "父本来源")
    private String maleParentSource;

    /** 母本 */
    @Excel(name = "母本", color = Color.RED)
    private String femaleParent;

    /** 母本来源 */
    @Excel(name = "母本来源")
    private String femaleParentSource;
    
    /** 组合代号 */
    @Excel(name = "组合代号", color = Color.RED)
    private String sowingCode;

    /** 杂交年份 */
    @Excel(name = "杂交年份", type = Type.EXPORT)
    private String hybridYear;

    /** 操作人员 */
    @Excel(name = "操作人员")
    private String operator;

    /** 采果日期 */
    @Excel(name = "采果日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caiguoDate;

    /** 种子数量(粒) */
    @Excel(name = "种子数量(粒)")
    private Long seedNums;

    /** 种子层积时间 */
    @Excel(name = "种子层积时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date seedStratificationDate;

    /** 取种日期 */
    @Excel(name = "取种日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date quzhongDate;

    /** 播种日期 */
    @Excel(name = "播种日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sowingDate;

    /** 播种方式 */
    @Excel(name = "播种方式")
    private String sowingMethod;

    /** 定植方式 */
    @Excel(name = "定植方式")
    private String colonizationMode;

    /** 定植地点 */
    @Excel(name = "定植地点")
    private String colonizationPlace;

    /** 定植日期 */
    @Excel(name = "定植日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date colonizationDate;

    /** 定植总数(株) */
    @Excel(name = "定植总数(株)", color = Color.RED)
    private Long colonizationTotal;

    /** 定植负责人 */
    @Excel(name = "定植负责人")
    private String colonizationHead;

    /** 定植人联系方式 */
    @Excel(name = "定植人联系方式")
    private String colonizationPhone;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
    
    /** 删除标志（0代表存在 2代表删除） */
    private Integer delFlag;

    /** 创建者id */
    private Long createById;

    /** 创建者 */
    private String createByName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
