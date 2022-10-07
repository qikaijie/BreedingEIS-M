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
 * 意见反馈对象 os_feedback
 * 
 * @author liuxn
 * @date 2021-07-15
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("os_feedback")
public class Feedback implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 反馈人id */
    @Excel(name = "反馈人id")
    private Long userId;

    /** 反馈人 */
    @Excel(name = "反馈人")
    private String username;
    
    /** 反馈渠道（0表示web，1表示android，2表示ios） */
    @Excel(name = "反馈渠道（0表示web，1表示android，2表示ios）")
    private Integer channel;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String content;
    
    /** 答复 */
    @Excel(name = "答复")
    private String reply;

    /** 0表示待处理；1表示已处理 */
    @Excel(name = "0表示待处理；1表示已处理")
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
