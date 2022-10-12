package com.liuxn.yuzhong.project.os.service;

import com.liuxn.yuzhong.project.os.domain.Hybrid;import com.liuxn.yuzhong.project.system.domain.SysUser;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 杂交组合库Service接口
 *
 * @author liuxn
 * @date 2020-06-20
 */
public interface IHybridService extends IService<Hybrid> {

	public String importHybrid(List<Hybrid> hybridList, Boolean isUpdateSupport, SysUser operUser);
	
	public List<Hybrid> queryList(Hybrid hybrid);
	
	public boolean removeByIds(List<Long> idList);
}
