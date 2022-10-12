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
 * 用户收藏植物对象 os_collect
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SeedlingCollectVo extends SeedlingCollect {

private static final long serialVersionUID=1L;

    /** 用户id */
    private String userName;
    
    private String codeOne;
    
    private String codeTwo;

    /** 植物Code */
    private String seedlingCode;
    
    private String germplasmName;
}
