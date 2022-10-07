package com.liuxn.yuzhong.project.os.domain;

import java.util.List;

import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel.Type;

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
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Accessors(chain = true)
public class PlantRecordListVo extends PlantRecord {

	private static final long serialVersionUID=1L;

	@Excel(name = "杂交组合", type = Type.EXPORT)
    private String hybridName;
    
	@Excel(name = "条码信息", type = Type.EXPORT)
    private String plantCode;
    
    private String plantCodeOne;
    
    private String plantCodeTwo;
    
    private Integer isCollect;
    
    private Integer collectLevel;
}
