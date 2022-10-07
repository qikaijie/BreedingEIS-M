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
import com.liuxn.yuzhong.project.os.domain.SeedlingImage;
import com.liuxn.yuzhong.project.os.service.ISeedlingImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;

/**
 * 植物图片、视频记录Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags = "管理端-种质植物记录中的图片管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/seedlingImage" )
public class SeedlingImageController extends BaseController {

    private final ISeedlingImageService iSeedlingImageService;

    /**
     * 查询植物图片、视频记录列表
     */
    @ApiOperation("查询种质植物图片列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="seedlingId", value="种植植物id", required = true, dataType="Long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="recordId", value="recordId记录id", required = true, dataType="Long", dataTypeClass=Long.class)
    })
    @PreAuthorize("@ss.hasPermi('os:seedlingImage:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long seedlingId, Long recordId)
    {
        startPage();
        LambdaQueryWrapper<SeedlingImage> lqw = new LambdaQueryWrapper<SeedlingImage>();
        lqw.eq(SeedlingImage::getGermplasmId ,seedlingId);
        lqw.eq(SeedlingImage::getRecordId ,recordId);
        List<SeedlingImage> list = iSeedlingImageService.list(lqw);
        return getDataTable(list);
    }

//    /**
//     * 导出植物图片、视频记录列表
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingImage:export')" )
//    @Log(title = "植物图片、视频记录" , businessType = BusinessType.EXPORT)
//    @GetMapping("/export" )
//    public AjaxResult export(SeedlingImage seedlingImage) {
//        LambdaQueryWrapper<SeedlingImage> lqw = new LambdaQueryWrapper<SeedlingImage>(seedlingImage);
//        List<SeedlingImage> list = iSeedlingImageService.list(lqw);
//        ExcelUtil<SeedlingImage> util = new ExcelUtil<SeedlingImage>(SeedlingImage. class);
//        return util.exportExcel(list, "seedlingImage" );
//    }
//
//    /**
//     * 获取植物图片、视频记录详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingImage:query')" )
//    @GetMapping(value = "/{id}" )
//    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
//        return AjaxResult.success(iSeedlingImageService.getById(id));
//    }
//
//    /**
//     * 新增植物图片、视频记录
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingImage:add')" )
//    @Log(title = "植物图片、视频记录" , businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SeedlingImage seedlingImage) {
//        return toAjax(iSeedlingImageService.save(seedlingImage) ? 1 : 0);
//    }
//
//    /**
//     * 修改植物图片、视频记录
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingImage:edit')" )
//    @Log(title = "植物图片、视频记录" , businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SeedlingImage seedlingImage) {
//        return toAjax(iSeedlingImageService.updateById(seedlingImage) ? 1 : 0);
//    }
//
//    /**
//     * 删除植物图片、视频记录
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingImage:remove')" )
//    @Log(title = "植物图片、视频记录" , businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}" )
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(iSeedlingImageService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
//    }
}
