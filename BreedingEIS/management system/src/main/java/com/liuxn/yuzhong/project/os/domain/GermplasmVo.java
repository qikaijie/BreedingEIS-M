package com.liuxn.yuzhong.project.os.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 种质信息对象 os_germplasm
 * 
 * @author liuxn
 * @date 2021-05-05
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class GermplasmVo extends Germplasm {

private static final long serialVersionUID=1L;

    private List<GermplasmImg> germplasmImgList;

}
