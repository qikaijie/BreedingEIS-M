package com.liuxn.yuzhong.project.os.domain;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 植物对象 os_plant
 * 
 * @author liuxn
 * @date 2020-06-20
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class AutoGenerateSeedling implements Serializable {

	private static final long serialVersionUID=1L;

    /** 杂交组合ID */
    @Excel(name = "种质ID")
    private Long germplasmId;

    /** 杂交代号（杂交组合的播种代号） */
    private String sowingCode;

    /** 种植基地 */
    private String plantBase;

    /** 种植区域 */
    private String plantArea;

    /** 种植垄 */
    private String plantRidge;

    /** 种植行 */
    private String plantRow;

    /** 田间编号 */
    private String fieldNumber;
    
    private JSONObject[] tableData;
}
