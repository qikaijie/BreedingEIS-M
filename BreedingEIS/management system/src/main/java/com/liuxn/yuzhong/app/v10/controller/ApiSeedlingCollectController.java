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
import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollectVo;
import com.liuxn.yuzhong.project.os.service.ISeedlingCollectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 用户收藏植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-种质植物收藏管理")
@RestController
@RequestMapping("/app/v10/seedlingCollect" )
public class ApiSeedlingCollectController extends APPBaseController {

	@Autowired
    private ISeedlingCollectService iSeedlingCollectService;

	/**
     * 查询用户收藏植物列表
     */
	@ApiOperation("收藏列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "germplasmId", value = "种质id", dataType="long", dataTypeClass=Long.class, required = false),
    	@ApiImplicitParam(name = "seedlingCode", value = "植物编码code，支持模糊匹配", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "prop", value = "排序字段：例如codeTwo", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "order", value = "排序顺序：descending降序，ascending升序", dataType="string", dataTypeClass=String.class, required = false)
    })
    @LoginRequired
    @GetMapping("/list")
    public AjaxResult list(Long germplasmId, String seedlingCode, String prop, String order)
    {
        startPage();
        List<SeedlingCollectVo> list = iSeedlingCollectService.quertList(getLoginUser().getUserId(), germplasmId, seedlingCode, prop, order);
        
        AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
    
    /**
     * 新增用户收藏植物
     */
	@ApiOperation("收藏")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "seedlingId", value = "植物id", dataType="long", dataTypeClass=Long.class, required = true),
    	@ApiImplicitParam(name = "level", value = "收藏级别（1：一级，2：二级）", dataType="int", dataTypeClass=Integer.class, required = true)
    })
	@LoginRequired
    @PostMapping("/add")
    public AjaxResult add(Long seedlingId, Integer level) {
		SeedlingCollect seedlingCollect = new SeedlingCollect();
		seedlingCollect.setSeedlingId(seedlingId);
		seedlingCollect.setLevel(level);
		seedlingCollect.setUserId(getLoginUser().getUserId());
		seedlingCollect.setCreateTime(DateUtils.getNowDate());
		
		LambdaQueryWrapper<SeedlingCollect> collectLQW = new LambdaQueryWrapper<SeedlingCollect>();
    	collectLQW.eq(SeedlingCollect::getUserId, seedlingCollect.getUserId());
    	collectLQW.eq(SeedlingCollect::getSeedlingId, seedlingCollect.getSeedlingId());
		List<SeedlingCollect> collectList = iSeedlingCollectService.list(collectLQW);
		if(collectList != null && collectList.size() > 0)
		{
			seedlingCollect.setId(collectList.get(0).getId());
		}
		
        return toAjax(iSeedlingCollectService.saveOrUpdate(seedlingCollect) ? 1 : 0);
    }

    /**
     * 删除用户收藏植物
     */
	@ApiOperation("取消收藏")
	@ApiImplicitParam(name = "id", value = "收藏id", required = true, dataType="long", dataTypeClass=Long.class)
	@LoginRequired
    @GetMapping("/del" )
    public AjaxResult remove(Long id) {
		
		SeedlingCollect collect = iSeedlingCollectService.getById(id);
		if(collect == null)
		{
			return AjaxResult.error("收藏信息不存在，删除失败");
		}
		
		if(collect.getUserId() != getLoginUser().getUserId())
		{
			return AjaxResult.error("您没有权限删除");
		}
		
        return toAjax(iSeedlingCollectService.removeById(id) ? 1 : 0);
    }
}
