package com.liuxn.yuzhong.app.v10.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupService;
import com.liuxn.yuzhong.project.system.domain.SysUser;
import com.liuxn.yuzhong.project.system.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 属性分组Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-属性分组管理")
@RestController
@RequestMapping("/app/v10/attribute/group" )
public class ApiAttributeGroupController extends APPBaseController {

	@Autowired
    private IAttributeGroupService iAttributeGroupService;
	
	@Autowired
	private ISysUserService userService;
    
    /**
     * 查询属性分组列表
     */
	@ApiOperation("查询物种分组信息以及分组对应的属性信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "speciesId", value = "物种id", required = true)
	})
	@LoginRequired
    @GetMapping("/list")
    public AjaxResult list(Long speciesId)
    {
		if(speciesId == null)
		{
			return AjaxResult.error("物种id不能为空");
		}
		
		List<SysUser> userList = userService.selectUserIdList(getLoginUser().getDeptId());
	
		List<AttributeGroupVo> attributeGroupVoList = null;
		if(userList != null && userList.size() > 0)
		{
			List<Long> userIds = new ArrayList<Long>();
			for(SysUser user : userList)
			{
				userIds.add(user.getUserId());
			}
			attributeGroupVoList = iAttributeGroupService.queryAttributeGroupVoBySpeciesId(speciesId, userIds);
		}
        
        return AjaxResult.success(attributeGroupVoList);
    }

}
