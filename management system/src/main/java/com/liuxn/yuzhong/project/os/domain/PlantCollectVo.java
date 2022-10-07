package com.liuxn.yuzhong.project.os.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class PlantCollectVo extends PlantCollect {

private static final long serialVersionUID=1L;

    /** 用户id */
    private String userName;
    
    private String codeOne;
    
    private String codeTwo;

    /** 植物Code */
    private String plantCode;
    
    private String hybridYear;
    
    private String hybridName;
}
