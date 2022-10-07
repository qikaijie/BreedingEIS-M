package com.liuxn.yuzhong.project.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.common.exception.CustomException;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.common.utils.bean.BeanUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.DataScope;
import com.liuxn.yuzhong.project.os.domain.Germplasm;
import com.liuxn.yuzhong.project.os.domain.GermplasmImg;
import com.liuxn.yuzhong.project.os.domain.GermplasmVo;
import com.liuxn.yuzhong.project.os.mapper.GermplasmImgMapper;
import com.liuxn.yuzhong.project.os.mapper.GermplasmMapper;
import com.liuxn.yuzhong.project.os.service.IGermplasmService;
import com.liuxn.yuzhong.project.system.domain.SysUser;

/**
 * 种质信息Service业务层处理
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Service
public class GermplasmServiceImpl extends ServiceImpl<GermplasmMapper, Germplasm> implements IGermplasmService {

	@Autowired
	private GermplasmMapper germplasmMapper;
	
	@Autowired
	private GermplasmImgMapper germplasmImgMapper;
	
	@Override
	public boolean save(GermplasmVo germplasmVO) {
		
		try
		{
			germplasmMapper.insert(germplasmVO);
			
			if(germplasmVO.getGermplasmImgList() != null && germplasmVO.getGermplasmImgList().size() > 0)
			{
				for(GermplasmImg germplasmImg : germplasmVO.getGermplasmImgList())
				{
					if(germplasmImg != null)
					{
						germplasmImg.setGermplasmId(germplasmVO.getId());
						
						germplasmImgMapper.insert(germplasmImg);
					}
				}
			}
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean update(GermplasmVo germplasmVO)
	{
		try
		{
				
			germplasmMapper.updateById(germplasmVO);
			
			//修改图片
			GermplasmImg germplasmImg = new GermplasmImg();
			germplasmImg.setGermplasmId(germplasmVO.getId());
			germplasmImgMapper.delete(new LambdaQueryWrapper<GermplasmImg>(germplasmImg));
			if(germplasmVO.getGermplasmImgList() != null && germplasmVO.getGermplasmImgList().size() > 0)
			{
				for(GermplasmImg img : germplasmVO.getGermplasmImgList())
				{
					img.setGermplasmId(germplasmVO.getId());
					germplasmImgMapper.insert(img);
				}
			}
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public GermplasmVo getGermplasmVOById(Long id) {

		GermplasmVo germplasmVo = null;
		
		Germplasm germplasm = germplasmMapper.selectById(id);
		
		if(germplasm != null)
		{
			GermplasmImg germplasmImg = new GermplasmImg();
			germplasmImg.setGermplasmId(id);
			
			List<GermplasmImg> imgList = germplasmImgMapper.selectList(new LambdaQueryWrapper<GermplasmImg>(germplasmImg));
			
			germplasmVo = new GermplasmVo();
			BeanUtils.copyProperties(germplasm, germplasmVo);
			germplasmVo.setGermplasmImgList(imgList);
		}
		
		return germplasmVo;
	}
	
	@Override
	public String importGermplasm(List<Germplasm> germplasmList, Boolean isUpdateSupport, SysUser operUser)
	{
		if (StringUtils.isNull(germplasmList) || germplasmList.size() == 0)
        {
            throw new CustomException("导入杂交数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Germplasm germplasm : germplasmList)
        {
            try
            {
            	LambdaQueryWrapper<Germplasm> wrapper = new LambdaQueryWrapper<Germplasm>();
            	wrapper.eq(Germplasm::getName, germplasm.getName());
            	List<Germplasm> list = germplasmMapper.selectList(wrapper);
            	if(list == null || list.size() == 0)
            	{
            		germplasm.setCreateById(operUser.getUserId());
                	germplasm.setCreateBy(operUser.getUserName());
                	germplasm.setCreateDate(DateUtils.getNowDate());
                	germplasm.setDelFlag(0);
                	germplasmMapper.insert(germplasm);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、种质 " + germplasm.getName() + " 导入成功");
            	}
            	else if(isUpdateSupport)
            	{
            		germplasm.setId(list.get(0).getId());
                	germplasmMapper.insert(germplasm);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、种质 " + germplasm.getName() + " 更新成功");
            	}
            	else
            	{
            		failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、种质 " + germplasm.getName() + " 已存在");
            	}
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、种质 " + germplasm.getName() + " 导入失败：";
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
	public List<Germplasm> queryList(Germplasm germplasm)
	{
		return germplasmMapper.queryList(germplasm);
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
