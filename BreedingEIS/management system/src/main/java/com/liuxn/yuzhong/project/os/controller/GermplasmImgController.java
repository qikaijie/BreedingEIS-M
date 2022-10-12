package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.GermplasmImg;
import com.liuxn.yuzhong.project.os.service.IGermplasmImgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 种质图片Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags = "管理端-种质图片管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/germplasmImg" )
public class GermplasmImgController extends BaseController {

    private final IGermplasmImgService iGermplasmImgService;

    /**
     * 查询种质图片列表
     */
    @ApiOperation("种质图片列表查询")
    @ApiImplicitParam(name="germplasmId", value="种质对象id", required=true, dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:germplasmImg:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long germplasmId)
    {
        startPage();
        LambdaQueryWrapper<GermplasmImg> lqw = new LambdaQueryWrapper<GermplasmImg>();
        if (germplasmId != null){
            lqw.eq(GermplasmImg::getGermplasmId ,germplasmId);
        }
        List<GermplasmImg> list = iGermplasmImgService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 获取种质图片详细信息
     */
    @ApiOperation("种质图片信息查询")
    @ApiImplicitParam(name="id", value="种质图片id", required=true, dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:germplasmImg:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iGermplasmImgService.getById(id));
    }

//    /**
//     * 新增种质图片
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmImg:add')" )
//    @Log(title = "种质图片" , businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody GermplasmImg germplasmImg) {
//        return toAjax(iGermplasmImgService.save(germplasmImg) ? 1 : 0);
//    }
//
//    /**
//     * 修改种质图片
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmImg:edit')" )
//    @Log(title = "种质图片" , businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody GermplasmImg germplasmImg) {
//        return toAjax(iGermplasmImgService.updateById(germplasmImg) ? 1 : 0);
//    }
//
//    /**
//     * 删除种质图片
//     */
//    @PreAuthorize("@ss.hasPermi('os:germplasmImg:remove')" )
//    @Log(title = "种质图片" , businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}" )
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(iGermplasmImgService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
//    }
}
