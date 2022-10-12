package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantCollectVo;
import com.liuxn.yuzhong.project.os.service.IPlantCollectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 用户收藏植物Controller
 *2w
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="管理端-杂交植物收藏管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/plantCollect" )
public class PlantCollectController extends BaseController {

    private final IPlantCollectService iPlantCollectService;

    @Autowired
    private TokenService tokenService;
    
    /**
     * 查询用户收藏植物列表
     */
    @ApiOperation("收藏列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "hybridId", value = "杂交id", dataType="long", dataTypeClass=Long.class, required = false),
    	@ApiImplicitParam(name = "plantCode", value = "植物编码code，支持模糊匹配", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "prop", value = "排序字段", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "order", value = "排序顺序：", dataType="string", dataTypeClass=String.class, required = false)
    })
    @PreAuthorize("@ss.hasPermi('os:plantCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long hybridId, String plantCode, String prop, String order)
    {
        startPage();
        
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        long userId = loginUser.getUser().getUserId();
        List<PlantCollectVo> list = iPlantCollectService.querList(userId, hybridId, plantCode, prop, order);
        return getDataTable(list);
    }
    
    /**
     * 删除用户收藏植物
     */
	@ApiOperation("取消收藏")
	@ApiImplicitParam(name = "id", value = "收藏id", required = true)
	@PreAuthorize("@ss.hasPermi('os:plantCollect:remove')")
    @GetMapping("/del" )
    public AjaxResult remove(Long id) {
		
		PlantCollect collect = iPlantCollectService.getById(id);
		if(collect == null)
		{
			return AjaxResult.error("收藏信息不存在，删除失败");
		}
		
        return toAjax(iPlantCollectService.removeById(id) ? 1 : 0);
    }
}
