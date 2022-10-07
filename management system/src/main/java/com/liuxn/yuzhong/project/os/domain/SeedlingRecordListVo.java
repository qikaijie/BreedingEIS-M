package com.liuxn.yuzhong.project.os.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Excel.Type;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;
import com.liuxn.yuzhong.framework.web.domain.BaseEntity;

/**
 * 植被记录对象 os_seedling_record
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SeedlingRecordListVo extends SeedlingRecord {

	private static final long serialVersionUID=1L;

	@Excel(name="种质信息", type=Type.EXPORT)
	private String germplasmName;
	
	@Excel(name="条码信息", type=Type.EXPORT)
	private String seedlingCode;
	
	private String seedlingCodeOne;
	
	private String seedlingCodeTwo;
	
	private Integer isCollect;
    
    private Integer collectLevel;
}
