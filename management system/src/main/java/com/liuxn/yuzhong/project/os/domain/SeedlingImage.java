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
 * 植物图片、视频记录对象 os_seedling_image
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_seedling_image")
public class SeedlingImage implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 种质ID */
    @Excel(name = "种质ID")
    private Long germplasmId;

    /** 植物id */
    @Excel(name = "植物id")
    private Long seedlingId;

    /** 记录id */
    @Excel(name = "记录id")
    private Long recordId;

    /** 类型：0图片，1视频 */
    @Excel(name = "类型：0图片，1视频")
    private Integer type;

    /** 相对路径 */
    @Excel(name = "相对路径")
    private String path;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    @Excel(name = "备注")
    private Long createById;

    /** $column.columnComment */
    @Excel(name = "备注")
    private String createByName;
}
