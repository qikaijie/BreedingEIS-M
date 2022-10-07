package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.AttributeGroup;
import com.liuxn.yuzhong.project.os.domain.Species;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupService;
import com.liuxn.yuzhong.project.os.service.ISpeciesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 物种信息Controller
 *
 * @author liuxn
 * @date 2021-05-09
 */
@Api(tags = "管理端-物种信息管理（属性挂靠在物种下）")
@AllArgsConstructor
@RestController
@RequestMapping("/os/species" )
public class SpeciesController extends BaseController {

	@Autowired
    private final ISpeciesService iSpeciesService;

	@Autowired
    private final IAttributeGroupService iAttributeGroupService;
	
	/**
     * 获取部门下拉树列表
     */
    @ApiOperation("物种信息树列表（用于树显示）")
    @ApiImplicitParam(name="name", value="物种名称", dataType="name", dataTypeClass=String.class)
    @PreAuthorize("@ss.hasPermi('os:species:list')")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(String name)
    {
        LambdaQueryWrapper<Species> lqw = new LambdaQueryWrapper<Species>();
        if (StringUtils.isNotBlank(name)){
            lqw.like(Species::getName, name);
        }
        lqw.orderByAsc(Species::getSort);
        List<Species> speciesList = iSpeciesService.list(lqw);
        
        return AjaxResult.success(speciesList);
    }
	
    /**
     * 查询物种信息列表
     */
    @ApiOperation("查询物种信息列表（包含了对应的属性分类信息）")
    @ApiImplicitParam(name="name", value="物种名称", dataType="name", dataTypeClass=String.class)
    @PreAuthorize("@ss.hasPermi('os:speciesAndGroup:list')")
    @GetMapping("/list")
    public AjaxResult list(String name)
    {
    	int index = 0;
    	JSONArray jsonArray = new JSONArray();
        LambdaQueryWrapper<Species> lqw = new LambdaQueryWrapper<Species>();
        if (StringUtils.isNotBlank(name)){
            lqw.like(Species::getName, name);
        }
        lqw.orderByAsc(Species::getSort);
        List<Species> speciesList = iSpeciesService.list(lqw);
        if(speciesList != null && speciesList.size() > 0)
        {
        	for(int i = 0; i < speciesList.size(); i++)
        	{
        		JSONObject json = new JSONObject();
        		json.put("id", index++);
        		json.put("businessId", speciesList.get(i).getId());
        		json.put("type", "species");
        		json.put("name", speciesList.get(i).getName());
        		
        		JSONArray child = new JSONArray();
        		
        		AttributeGroup attributeGroup = new AttributeGroup();
        		attributeGroup.setSpeciesId(speciesList.get(i).getId());
        		List<AttributeGroup> groupList = iAttributeGroupService.queryList(attributeGroup);
        		if(groupList != null && groupList.size() > 0)
        		{
        			for(int j = 0; j < groupList.size(); j++)
            		{
        				JSONObject childJson = new JSONObject();
        				childJson.put("id", index++);
        				childJson.put("businessId", groupList.get(j).getId());
        				childJson.put("type", "attributeGroup");
        				childJson.put("name", groupList.get(j).getName());
        				
        				child.add(childJson);
            		}
        		}
        		
        		json.put("children", child);
        		
        		jsonArray.add(json);
        	}
        }
        
        return AjaxResult.success(jsonArray);
    }

    /**
     * 获取物种信息详细信息
     */
    @ApiOperation("查询物种信息")
    @ApiImplicitParam(name="id", value="物种信息id", dataType="long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:species:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iSpeciesService.getById(id));
    }

    /**
     * 新增物种信息
     */
    @ApiOperation("添加物种信息")
    @ApiImplicitParam(name="species", value="物种信息", dataType="species", dataTypeClass=Species.class)
    @PreAuthorize("@ss.hasPermi('os:species:add')" )
    @Log(title = "物种信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Species species) {
        return toAjax(iSpeciesService.save(species) ? 1 : 0);
    }

    /**
     * 修改物种信息
     */
    @ApiOperation("更新物种信息")
    @ApiImplicitParam(name="species", value="物种信息", dataType="species", dataTypeClass=Species.class)
    @PreAuthorize("@ss.hasPermi('os:species:edit')" )
    @Log(title = "物种信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Species species) {
        return toAjax(iSpeciesService.updateById(species) ? 1 : 0);
    }

//    /**
//     * 删除物种信息
//     */
//    @PreAuthorize("@ss.hasPermi('os:species:remove')" )
//    @Log(title = "物种信息" , businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}" )
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(iSpeciesService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
//    }
}
