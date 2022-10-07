package com.liuxn.yuzhong.app.v10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.AttributeSortVo;
import com.liuxn.yuzhong.project.os.service.IAttributeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 属性池Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-属性信息管理")
@RestController
@RequestMapping("/app/v10/attribute" )
public class ApiAttributeController extends APPBaseController {

	@Autowired
    private IAttributeService iAttributeService;

	/** 描述: .查询非分组下的属性集合，供分组下新增属性使用
	 * @param groupId
	 * @return
	 * @author     king
	 * <p>Sample: 该方法使用样例</p>
	 * date        2020年6月20日
	 * -----------------------------------------------------------
	 * 修改人                                             修改日期                                   修改描述
	 * king                2020年6月20日               创建
	 * -----------------------------------------------------------
	 * @Version  Ver1.0
	 */
    @ApiOperation("查询分组下的属性集合(不分页)")
    @ApiImplicitParam(name="groupId", value="属性组id", dataType="Long", dataTypeClass=Long.class)
    @GetMapping("/list/existent")
	public AjaxResult queryAttributeExistentByGroupId(Long groupId) {
    	if(groupId == null)
		{
			return AjaxResult.error("属性组id不能为空");
		}
    	
    	List<AttributeSortVo> list = iAttributeService.queryAttributeSortByGroupId(groupId);
		return AjaxResult.success(list);
	}
    
}
