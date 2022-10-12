package com.liuxn.yuzhong.project.os.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ImportSeedlingVo implements Serializable, Comparable<ImportSeedlingVo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 种植垄 */
    private String plantRidge;

    /** 种植列 */
    private String plantRow;

    /** 种植-行 */
    private Long lineNum;
    
    /** 对应的值 */
    private String value;

	@Override
	public int compareTo(ImportSeedlingVo o) {
		int reslut1 = this.plantRidge.compareTo(o.plantRidge);
		if(reslut1 == 0)
		{
			int reslut2 = this.plantRow.compareTo(o.plantRow);
			if(reslut2 == 0)
			{
				int reslut3 = this.lineNum.compareTo(o.lineNum);
				
				return reslut3;
			}
			else
			{
				return reslut2;
			}
		}
		else
		{
			return reslut1;
		}
	}
}
