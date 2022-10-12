package com.liuxn.yuzhong.project.os.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxn.yuzhong.project.os.domain.Germplasm;
import com.liuxn.yuzhong.project.os.domain.GermplasmVo;
import com.liuxn.yuzhong.project.system.domain.SysUser;

/**
 * 种质信息Service接口
 *
 * @author liuxn23456789000
 * @date 2021-05-05
 */
public interface IGermplasmService extends IService<Germplasm> {

	public boolean save(GermplasmVo germplasmVo);
	
	public boolean update(GermplasmVo germplasmVO);
	
	public GermplasmVo getGermplasmVOById(Long id);
	
	public String importGermplasm(List<Germplasm> germplasmList, Boolean isUpdateSupport, SysUser operUser);
	
	public List<Germplasm> queryList(Germplasm germplasm);
	
	public boolean removeByIds(List<Long> idList);
}
