package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantRecordListVo;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollectVo;
import com.liuxn.yuzhong.project.os.service.ISeedlingCollectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 用户收藏种植植物Controller
 *
 * @author liuxn
 * @date 2021-05-08
 */
@Api(tags = "管理端-种质植物信息收藏管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/seedlingCollect" )
public class SeedlingCollectController extends BaseController {

	@Autowired
    private ISeedlingCollectService iSeedlingCollectService;

    @Autowired
    private TokenService tokenService;
    
    /**
     * 查询用户收藏种植植物列表
     */
    @ApiOperation("收藏列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "germplasmId", value = "种质id", dataType="long", dataTypeClass=Long.class, required = false),
    	@ApiImplicitParam(name = "seedlingCode", value = "植物编码code，支持模糊匹配", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "prop", value = "排序字段", dataType="string", dataTypeClass=String.class, required = false),
    	@ApiImplicitParam(name = "order", value = "排序顺序：", dataType="string", dataTypeClass=String.class, required = false)
    })
    @PreAuthorize("@ss.hasPermi('os:seedlingCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long germplasmId, String seedlingCode, String prop, String order)
    {
        startPage();
        
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        long userId = loginUser.getUser().getUserId();
        List<SeedlingCollectVo> list = iSeedlingCollectService.quertList(userId, germplasmId, seedlingCode, prop, order);
        
        return getDataTable(list);
    }

    /**
     * 删除用户收藏种植植物
     */
    @ApiOperation("取消收藏")
    @ApiImplicitParam(name = "id", value = "收藏id", required = true, dataType="long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:seedlingCollect:remove')" )
    @Log(title = "用户收藏种植植物" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}" )
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(iSeedlingCollectService.removeById(id) ? 1 : 0);
    }
}
