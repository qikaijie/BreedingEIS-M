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
 * 植被记录对象 os_plant_record
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_plant_record")
public class PlantRecord extends OSBaseEntity {

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 杂交id */
    private Long hybridId;
    
    /** 种苗父id */
    private Long plantParentId;

    /** 植物id */
    private Long plantId;
    
    /** 类型（1代表初选，2代表高接，3代表区试） */
    @Excel(name = "类型" , readConverterExp = "1=代表初选,2=代表高接,3=代表区试", type = Type.EXPORT)
    private Integer plantType;

    /** 属性值JSON：[{"id":"1","name":"果实大小","value":"大"},{},{}] */
    @Excel(name = "属性值集合", jsonTransformation = true, type = Type.EXPORT)
    private String attributeValues;

    /** 录入方式：1手动填报，2系统导入 */
    @Excel(name = "录入方式", readConverterExp = "1=手动填报,2=系统导入", type = Type.EXPORT)
    private String enterMethod;

    /** 记录年份 */
    @Excel(name = "记录年份", type = Type.EXPORT)
    private String createYear;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "填报时间", dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date createTime;

    /** $column.columnComment */
    private Long createById;

    /** $column.columnComment */
    @Excel(name = "填报人", type = Type.EXPORT)
    private String createByName;
}
