package com.liuxn.yuzhong.project.os.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupRelationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 属性与分组关系Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "管理端-属性与属性组关系管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/attribute/group/relation" )
public class AttributeGroupRelationController extends BaseController {

    private final IAttributeGroupRelationService iAttributeGroupRelationService;

    /**
     * 导出属性与分组关系列表
     */
    @ApiOperation("属性组下面的属性排序")
    @ApiImplicitParam(name="relationIds", value="relationIds", dataType="relationIds", dataTypeClass=Long[].class)
    @PreAuthorize("@ss.hasPermi('os:attribute/group:edit')" )
    @PostMapping("/sort" )
    public AjaxResult sort(@RequestBody Long[] relationIds) {
    	
    	logger.error("relationIds:" + relationIds);
    	if(relationIds == null || relationIds.length == 0)
    	{
    		return AjaxResult.error("参数不能为空，操作失败");
    	}
    	
    	iAttributeGroupRelationService.sort(relationIds);
    	
    	return AjaxResult.success();
    }
}
