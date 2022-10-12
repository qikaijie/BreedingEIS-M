package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.PearSpecies;
import com.liuxn.yuzhong.project.os.service.IPearSpeciesService;

import lombok.AllArgsConstructor;

/**
 * 梨物种Controller
 *
 * @author liuxn
 * @date 2022-09-23
 */	
@AllArgsConstructor
@RestController
@RequestMapping("/os/pearSpecies" )
public class PearSpeciesController extends BaseController {

    private final IPearSpeciesService iPearSpeciesService;

    /**
     * 查询梨物种列表
     */
    //@PreAuthorize("@ss.hasPermi('os:PearSpecies:list')")
    @GetMapping("/list")
    public TableDataInfo list(PearSpecies pearSpecies)
    {
        startPage();
        LambdaQueryWrapper<PearSpecies> lqw = new LambdaQueryWrapper<PearSpecies>();
        if (StringUtils.isNotBlank(pearSpecies.getSpeciesName())){
            lqw.like(PearSpecies::getSpeciesName ,pearSpecies.getSpeciesName());
        }
        if (StringUtils.isNotBlank(pearSpecies.getMaleParent())){
            lqw.eq(PearSpecies::getMaleParent ,pearSpecies.getMaleParent());
        }
        if (StringUtils.isNotBlank(pearSpecies.getFemaleParent())){
            lqw.eq(PearSpecies::getFemaleParent ,pearSpecies.getFemaleParent());
        }
        List<PearSpecies> list = iPearSpeciesService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出梨物种列表
     */
    //@PreAuthorize("@ss.hasPermi('os:PearSpecies:export')" )
    @Log(title = "梨物种" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PearSpecies pearSpecies) {
        LambdaQueryWrapper<PearSpecies> lqw = new LambdaQueryWrapper<PearSpecies>(pearSpecies);
        List<PearSpecies> list = iPearSpeciesService.list(lqw);
        ExcelUtil<PearSpecies> util = new ExcelUtil<PearSpecies>(PearSpecies. class);
        return util.exportExcel(list, "PearSpecies" );
    }

    /**
     * 获取梨物种详细信息
     */
    //@PreAuthorize("@ss.hasPermi('os:PearSpecies:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPearSpeciesService.getById(id));
    }

    /**
     * 新增梨物种
     */
    @PreAuthorize("@ss.hasPermi('os:PearSpecies:add')" )
    @Log(title = "梨物种" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PearSpecies pearSpecies) {
        return toAjax(iPearSpeciesService.save(pearSpecies) ? 1 : 0);
    }

    /**
     * 修改梨物种
     */
    @PreAuthorize("@ss.hasPermi('os:PearSpecies:edit')" )
    @Log(title = "梨物种" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PearSpecies pearSpecies) {
        return toAjax(iPearSpeciesService.updateById(pearSpecies) ? 1 : 0);
    }

    /**
     * 删除梨物种
     */
    @PreAuthorize("@ss.hasPermi('os:PearSpecies:remove')" )
    @Log(title = "梨物种" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPearSpeciesService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
