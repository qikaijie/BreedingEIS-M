package com.liuxn.yuzhong.app.v10.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.Species;
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
@Api(tags = "移动端-物种信息管理（属性挂靠在物种下）")
@AllArgsConstructor
@RestController
@RequestMapping("/app/v10/species" )
public class ApiSpeciesController extends BaseController {

    private final ISpeciesService iSpeciesService;

    /**
     * 查询物种信息列表
     */
    @ApiOperation("查询物种信息列表")
    @ApiImplicitParam(name="species", value="物种信息", dataType="species", dataTypeClass=Species.class)
    @LoginRequired
    @GetMapping("/list")
    public TableDataInfo list(Species species)
    {
        startPage();
        LambdaQueryWrapper<Species> lqw = new LambdaQueryWrapper<Species>();
        if (StringUtils.isNotBlank(species.getName())){
            lqw.like(Species::getName ,species.getName());
        }
        List<Species> list = iSpeciesService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 获取物种信息详细信息
     */
    @ApiOperation("查询物种信息")
    @ApiImplicitParam(name="id", value="物种信息id", dataType="long", dataTypeClass=Long.class)
    @LoginRequired
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iSpeciesService.getById(id));
    }
}
