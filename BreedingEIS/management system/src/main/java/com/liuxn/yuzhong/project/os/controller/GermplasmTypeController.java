package com.liuxn.yuzhong.project.os.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.liuxn.yuzhong.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.project.os.domain.GermplasmType;
import com.liuxn.yuzhong.project.os.service.IGermplasmTypeService;

import io.swagger.annotations.Api;

import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;

/**
 * 种质类型Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags = "管理端-种质信息类型管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/germplasmType" )
public class GermplasmTypeController extends BaseController {

    private final IGermplasmTypeService iGermplasmTypeService;

//    /**
//     * 查询种质类型列表
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmType:list')")
//    @GetMapping("/list")
//    public AjaxResult list(GermplasmType germplasmType) {
//        List<GermplasmType> list = iGermplasmTypeService.list(lqw);
//        return AjaxResult.success(list);
//    }
//
//    /**
//     * 导出种质类型列表
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmType:export')" )
//    @Log(title = "种质类型" , businessType = BusinessType.EXPORT)
//    @GetMapping("/export" )
//    public AjaxResult export(GermplasmType germplasmType) {
//        LambdaQueryWrapper<GermplasmType> lqw = new LambdaQueryWrapper<GermplasmType>(germplasmType);
//        List<GermplasmType> list = iGermplasmTypeService.list(lqw);
//        ExcelUtil<GermplasmType> util = new ExcelUtil<GermplasmType>(GermplasmType. class);
//        return util.exportExcel(list, "germplasmType" );
//    }
//
//    /**
//     * 获取种质类型详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmType:query')" )
//    @GetMapping(value = "/{id}" )
//    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
//        return AjaxResult.success(iGermplasmTypeService.getById(id));
//    }
//
//    /**
//     * 新增种质类型
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmType:add')" )
//    @Log(title = "种质类型" , businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody GermplasmType germplasmType) {
//        return toAjax(iGermplasmTypeService.save(germplasmType) ? 1 : 0);
//    }
//
//    /**
//     * 修改种质类型
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmType:edit')" )
//    @Log(title = "种质类型" , businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody GermplasmType germplasmType) {
//        return toAjax(iGermplasmTypeService.updateById(germplasmType) ? 1 : 0);
//    }
//
//    /**
//     * 删除种质类型
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmType:remove')" )
//    @Log(title = "种质类型" , businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}" )
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(iGermplasmTypeService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
//    }
}
