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
 * 种质图片对象 os_germplasm_img
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_germplasm_img")
public class GermplasmImg implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 种质id */
    @Excel(name = "种质id")
    private Long germplasmId;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
