package com.liuxn.yuzhong.project.os.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * app版本下载对象 os_app_download
 * 
 * @author liuxn
 * @date 2021-07-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_app_download")
public class AppDownload implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 0表示android，1表示ios */
    @Excel(name = "0表示android，1表示ios")
    private Long type;

    /** app版本号 */
    @Excel(name = "app版本号")
    private String version;

    /** app下载地址 */
    @Excel(name = "app下载地址")
    private String downUrl;

    /** app更新时间 */
    private Date updateTime;
}
