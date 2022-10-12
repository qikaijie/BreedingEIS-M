package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.common.exception.CustomException;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Hybrid;
import com.liuxn.yuzhong.project.os.mapper.HybridMapper;
import com.liuxn.yuzhong.project.os.service.IHybridService;
import com.liuxn.yuzhong.project.system.domain.SysUser;

/**
 * 杂交组合库Service业务层处理
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Service
public class HybridServiceImpl extends ServiceImpl<HybridMapper, Hybrid> implements IHybridService {

	@Autowired
	private HybridMapper hybridMapper;
	
	public String importHybrid(List<Hybrid> hybridList, Boolean isUpdateSupport, SysUser operUser)
	{
		if (StringUtils.isNull(hybridList) || hybridList.size() == 0)
        {
            throw new CustomException("导入杂交数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Hybrid hybrid : hybridList)
        {
            try
            {
            	LambdaQueryWrapper<Hybrid> wrapper = new LambdaQueryWrapper<Hybrid>();
            	wrapper.eq(Hybrid::getSowingCode, hybrid.getSowingCode());
            	List<Hybrid> list = hybridMapper.selectList(wrapper);
            	if(list == null || list.size() == 0)
            	{
            		hybrid.setName(hybrid.getFemaleParent() + " X " + hybrid.getMaleParent());
                	hybrid.setCreateById(operUser.getUserId());
                	hybrid.setHybridYear(DateUtils.parseDateToStr(DateUtils.YYYY, hybrid.getHybridDate()));
                	hybrid.setDelFlag(0);
                	hybrid.setCreateByName(operUser.getUserName());
                	hybrid.setCreateTime(DateUtils.getNowDate());
                    hybridMapper.insert(hybrid);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、杂交 " + hybrid.getSowingCode() + " 导入成功");
            	}
            	else if (isUpdateSupport)
                {
            		hybrid.setId(list.get(0).getId());
            		hybrid.setName(hybrid.getFemaleParent() + " X " + hybrid.getMaleParent());
                	hybrid.setHybridYear(DateUtils.parseDateToStr(DateUtils.YYYY, hybrid.getHybridDate()));
                    hybridMapper.updateById(hybrid);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、杂交  " + hybrid.getSowingCode() + " 更新成功");
                }
            	else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、杂交 " + hybrid.getSowingCode() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、杂交 " + hybrid.getSowingCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
	}
	
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<Hybrid> queryList(Hybrid hybrid)
	{
		return hybridMapper.queryList(hybrid);
	}
	
	@Override
	public boolean removeByIds(List<Long> idList)
	{
		int result = this.baseMapper.removeByIds(idList);
		if(result >= 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
