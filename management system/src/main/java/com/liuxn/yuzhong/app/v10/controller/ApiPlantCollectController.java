package com.liuxn.yuzhong.app.v10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantCollectVo;
import com.liuxn.yuzhong.project.os.service.IPlantCollectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户收藏植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-杂交植物收藏管理")
@RestController
@RequestMapping("/app/v10/plantCollect" )
public class ApiPlantCollectController extends APPBaseController {

	@Autowired
    private IPlantCollectService iCollectService;

	/**
     * 查询用户收藏植物列表
     */
    @ApiOperation("用户收藏植物列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "hybridId", value = "杂交id", dataType="long", dataTypeClass=Long.class, required = false),
    	@ApiImplicitParam(name = "plantCode", value = "植物编码code，支持模糊匹配", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "prop", value = "排序字段：例如codeTwo", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "order", value = "排序顺序：descending降序，ascending升序", dataType="string", dataTypeClass=String.class, required = false)
    })
    @LoginRequired
    @GetMapping("/list")
    public AjaxResult list(Long hybridId, String plantCode, String prop, String order)
    {
        startPage();
        List<PlantCollectVo> list = iCollectService.querList(getLoginUser().getUserId(), hybridId, plantCode, prop, order);
        
        AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
    
    /**
     * 新增用户收藏植物
     */
	@ApiOperation("收藏")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "plantId", value = "植物id", dataType="long", dataTypeClass=Long.class, required = true),
    	@ApiImplicitParam(name = "level", value = "收藏级别（1：一级，2：二级）", dataType="int", dataTypeClass=Integer.class, required = true)
    })
	@LoginRequired
    @PostMapping("/add")
    public AjaxResult add(Long plantId, Integer level) {
		PlantCollect collect = new PlantCollect();
		collect.setPlantId(plantId);
		collect.setLevel(level);
		collect.setUserId(getLoginUser().getUserId());
		collect.setCreateTime(DateUtils.getNowDate());
		
		LambdaQueryWrapper<PlantCollect> collectLQW = new LambdaQueryWrapper<PlantCollect>();
    	collectLQW.eq(PlantCollect::getUserId, collect.getUserId());
    	collectLQW.eq(PlantCollect::getPlantId, collect.getPlantId());
		List<PlantCollect> collectList = iCollectService.list(collectLQW);
		if(collectList != null && collectList.size() > 0)
		{
			collect.setId(collectList.get(0).getId());
		}
		
        return toAjax(iCollectService.saveOrUpdate(collect) ? 1 : 0);
    }

    /**
     * 删除用户收藏植物
     */
	@ApiOperation("取消收藏")
	@ApiParam(name = "id", value = "收藏id", required = true)
	@LoginRequired
    @GetMapping("/del" )
    public AjaxResult remove(Long id) {
		
		PlantCollect collect = iCollectService.getById(id);
		if(collect == null)
		{
			return AjaxResult.error("收藏信息不存在，删除失败");
		}
		
		if(collect.getUserId() != getLoginUser().getUserId())
		{
			return AjaxResult.error("您没有权限删除");
		}
		
        return toAjax(iCollectService.removeById(id) ? 1 : 0);
    }
}
