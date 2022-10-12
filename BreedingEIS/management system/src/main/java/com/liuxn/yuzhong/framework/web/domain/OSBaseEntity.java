package com.liuxn.yuzhong.framework.web.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Entity基类
 * 
 * @author ruoyi
 */
@Data
public class OSBaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 数据权限 */
    @JsonIgnore
    @TableField(exist = false)
    private String dataScope;
    
    /** 开始时间 */
    @JsonIgnore
    @TableField(exist = false)
    private Date startTime;

    /** 结束时间 */
    @JsonIgnore
    @TableField(exist = false)
    private Date endTime;
}
