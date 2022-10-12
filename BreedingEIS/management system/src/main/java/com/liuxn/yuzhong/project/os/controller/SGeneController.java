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
import com.liuxn.yuzhong.project.os.domain.SGene;
import com.liuxn.yuzhong.project.os.service.ISGeneService;

import lombok.AllArgsConstructor;

/**
 * sgeneController
 *
 * @author liuxn
 * @date 2022-09-23
 */
@AllArgsConstructor
@RestController
@RequestMapping("/os/sgene" )
public class SGeneController extends BaseController {

    private final ISGeneService iSGeneService;

    /**
     * 查询sgene列表
     */
    //@PreAuthorize("@ss.hasPermi('os:sgene:list')")
    @GetMapping("/list")
    public TableDataInfo list(SGene sGene)
    {
        startPage();
        LambdaQueryWrapper<SGene> lqw = new LambdaQueryWrapper<SGene>();
        if (StringUtils.isNotBlank(sGene.getSpeciesName())){
            lqw.like(SGene::getSpeciesName ,sGene.getSpeciesName());
        }
        if (StringUtils.isNotBlank(sGene.getSpeciesName2())){
            lqw.eq(SGene::getSpeciesName2 ,sGene.getSpeciesName2());
        }
        List<SGene> list = iSGeneService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出sgene列表
     */
    //@PreAuthorize("@ss.hasPermi('os:sgene:export')" )
    @Log(title = "sgene" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(SGene sGene) {
        LambdaQueryWrapper<SGene> lqw = new LambdaQueryWrapper<SGene>(sGene);
        List<SGene> list = iSGeneService.list(lqw);
        ExcelUtil<SGene> util = new ExcelUtil<SGene>(SGene. class);
        return util.exportExcel(list, "sgene" );
    }

    /**
     * 获取sgene详细信息
     */
    //@PreAuthorize("@ss.hasPermi('os:sgene:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iSGeneService.getById(id));
    }

    /**
     * 新增sgene
     */
    @PreAuthorize("@ss.hasPermi('os:sgene:add')" )
    @Log(title = "sgene" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SGene sGene) {
        return toAjax(iSGeneService.save(sGene) ? 1 : 0);
    }

    /**
     * 修改sgene
     */
    @PreAuthorize("@ss.hasPermi('os:sgene:edit')" )
    @Log(title = "sgene" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SGene sGene) {
        return toAjax(iSGeneService.updateById(sGene) ? 1 : 0);
    }

    /**
     * 删除sgene
     */
    @PreAuthorize("@ss.hasPermi('os:sgene:remove')" )
    @Log(title = "sgene" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iSGeneService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
