package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.common.utils.SecurityUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.AttributeGroup;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupService;
import com.liuxn.yuzhong.project.system.domain.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 属性分组Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "管理端-属性组管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/attribute/group" )
public class AttributeGroupController extends BaseController {

    private final IAttributeGroupService iAttributeGroupService;
   
    /**
     * 获取部门下拉树列表
     */
    @ApiOperation("属性组列表查询")
    @ApiImplicitParam(name="attributeGroup", value="属性组信息", required=true, dataType="AttributeGroup", dataTypeClass=AttributeGroup.class)
    @PreAuthorize("@ss.hasPermi('os:attribute/group:list')")
    @GetMapping("/list")
    public AjaxResult list(AttributeGroup attributeGroup)
    {
        List<AttributeGroup> AttributeGroupList = iAttributeGroupService.queryList(attributeGroup);
        return AjaxResult.success(AttributeGroupList);
    }

    /**
     * 获取属性分组详细信息
     */
    @ApiOperation("属性组详细信息")
    @ApiImplicitParam(name="id", value="属性组id", required=true, dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:attribute/group:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
    	AttributeGroupVo attributeGroupVo = iAttributeGroupService.getAttributeGroupVoById(id);
    	if(attributeGroupVo == null)
    	{
    		return AjaxResult.error("属性组信息不存在");
    	}
    	
        return AjaxResult.success(attributeGroupVo);
    }

    /**
     * 新增属性分组
     */
    @ApiOperation("添加属性组")
    @ApiImplicitParam(name="attributeGroupVo", value="属性与属性组关系", required=true, dataType="AttributeGroupVo", dataTypeClass=AttributeGroupVo.class)
    @PreAuthorize("@ss.hasPermi('os:attribute/group:add')" )
    @Log(title = "属性分组" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AttributeGroupVo attributeGroupVo) {
    	
    	LambdaQueryWrapper<AttributeGroup> lqw = new LambdaQueryWrapper<AttributeGroup>();
    	lqw.eq(AttributeGroup::getName ,attributeGroupVo.getName());
    	int count = iAttributeGroupService.count(lqw);
    	if(count > 0)
    	{
    		return AjaxResult.error("属性分组名称已经存在，请修改属性分组名称");
    	}

    	//设置属性值
    	SysUser user = SecurityUtils.getLoginUser().getUser();
    	attributeGroupVo.setCreateById(user.getUserId());
    	attributeGroupVo.setCreateByName(user.getUserName());
    	attributeGroupVo.setCreateTime(DateUtils.getNowDate());
    	
        return toAjax(iAttributeGroupService.save(attributeGroupVo) ? 1 : 0);
    }

    /**
     * 修改属性分组
     */
    @ApiOperation("修改属性组")
    @ApiImplicitParam(name="attributeGroupVo", value="属性与属性组关系", required=true, dataType="AttributeGroupVo", dataTypeClass=AttributeGroupVo.class)
    @PreAuthorize("@ss.hasPermi('os:attribute/group:edit')" )
    @Log(title = "属性分组" , businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody AttributeGroupVo attributeGroupVo) {
        return toAjax(iAttributeGroupService.update(attributeGroupVo) ? 1 : 0);
    }

    /**
     * 修改属性分组
     */
    @ApiOperation("隐藏/显示属性组")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="groupId", value="属性组id", required=true, dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="isHidden", value="0表示显示，1表示隐藏", required=true, dataType="int", dataTypeClass=Integer.class)
    })
    @PreAuthorize("@ss.hasPermi('os:attribute/group:hidden')" )
    @Log(title = "属性分组" , businessType = BusinessType.UPDATE)
    @GetMapping("/hidden")
    public AjaxResult edit(Long groupId, Integer isHidden) {
    	AttributeGroup attributeGroup = iAttributeGroupService.getById(groupId);
    	if(attributeGroup == null)
    	{
    		return AjaxResult.error("属性分组信息不存在");
    	}
    	
    	attributeGroup.setIsHidden(isHidden);
        return toAjax(iAttributeGroupService.updateById(attributeGroup) ? 1 : 0);
    }
    
    /**
     * 导出属性与分组关系列表
     */
    @ApiOperation("物种下面的属性组排序")
    @ApiImplicitParam(name="groupIds", value="groupIds", dataType="Long[]", dataTypeClass=Long[].class)
    @PreAuthorize("@ss.hasPermi('os:attribute/group:edit')" )
    @PostMapping("/sort" )
    public AjaxResult sort(@RequestBody Long[] groupIds) {
    	
    	logger.error("groupIds:" + groupIds);
    	if(groupIds == null || groupIds.length == 0)
    	{
    		return AjaxResult.error("参数不能为空，操作失败");
    	}
    	
    	iAttributeGroupService.sort(groupIds);
    	
    	return AjaxResult.success();
    }
    
    /**
     * 删除属性分组
     */
    @PreAuthorize("@ss.hasPermi('os:attribute/group:remove')" )
    @Log(title = "属性分组" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iAttributeGroupService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
