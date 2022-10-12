package com.liuxn.yuzhong.project.os.domain;

import java.util.List;

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
public class SeedlingRecordVo extends SeedlingRecord {

	private static final long serialVersionUID=1L;

    private List<SeedlingRecordLog> logList;
    
    private List<SeedlingImage> imgList;
}
