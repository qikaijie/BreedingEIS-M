package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeSortVo;
import com.liuxn.yuzhong.project.os.domain.AttributeVo;
import com.liuxn.yuzhong.project.os.service.IAttributeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 属性池Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "管理端-属性管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/attribute" )
public class AttributeController extends BaseController {

    private final IAttributeService iAttributeService;

    private final TokenService tokenService;
    /**
     * 查询属性池列表
     */
    @ApiOperation("属性列表查询")
    @ApiImplicitParam(name="attribute", value="属性对象", dataType="Attribute", dataTypeClass=Attribute.class)
    @PreAuthorize("@ss.hasPermi('os:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attribute attribute)
    {
        startPage();
        LambdaQueryWrapper<Attribute> lqw = new LambdaQueryWrapper<Attribute>();
        if (StringUtils.isNotBlank(attribute.getFieldType())){
            lqw.eq(Attribute::getFieldType ,attribute.getFieldType());
        }
        if (StringUtils.isNotBlank(attribute.getFieldCode())){
            lqw.like(Attribute::getFieldCode ,attribute.getFieldCode());
        }
        if (StringUtils.isNotBlank(attribute.getFieldName())){
            lqw.like(Attribute::getFieldName ,attribute.getFieldName());
        }
        if (StringUtils.isNotBlank(attribute.getFieldContent())){
            lqw.like(Attribute::getFieldContent ,attribute.getFieldContent());
        }
        lqw.orderByDesc(Attribute::getId);
        List<Attribute> list = iAttributeService.list(lqw);
        return getDataTable(list);
    }
    
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
    @ApiOperation("查询物种下的所有属性列表信息，属性信息附带属性组信息（不分页）")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="speciesId", value="物种id", required=true, dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="fieldName", value="属性名", dataType="string", dataTypeClass=String.class)
    })
    @GetMapping("/list/all")
	public AjaxResult listAll(Long speciesId, String fieldName) {
    	
    	if(speciesId == null)
    	{
    		return AjaxResult.error("物种id不能为空");
    	}
    	List<AttributeVo> list = iAttributeService.queryList(speciesId, fieldName);
    	
		return AjaxResult.success(list);
	}
    
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
    @ApiOperation("查询属性分类下的属性列表信息（不分页）")
    @ApiImplicitParam(name="groupId", value="属性组id", dataType="Long", dataTypeClass=Long.class)
    @GetMapping("/list/existent")
	public TableDataInfo queryAttributeExistentByGroupId(Long groupId) {
    	List<AttributeSortVo> list = iAttributeService.queryAttributeSortByGroupId(groupId);
		return getDataTable(list);
	}
    
    /**
     * 
     * @param speciesId
     * @param fieldName
     * @return
     */
    @ApiOperation("查询物种或者属性分类下关联的属性列表信息（分页）")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="id", value="关联id", required=true, dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="type", value="关联id类型（species：表示物种，attributeGroup：表示属性分类）", required=true, dataType="string", dataTypeClass=String.class)
    })
    @GetMapping("/queryList")
	public TableDataInfo queryList(Long id, String type) {
    	
    	if("species".equals(type))
    	{
    		LambdaQueryWrapper<Attribute> lqw = new LambdaQueryWrapper<Attribute>();
    		lqw.eq(Attribute::getSpeciesId, id);
    		List<Attribute> attributeList = iAttributeService.list(lqw);
    		
    		return getDataTable(attributeList);
    	}
    	else if("attributeGroup".equals(type))
    	{
    		List<AttributeSortVo> list = iAttributeService.queryAttributeSortByGroupId(id);
    		return getDataTable(list);
    	}
    	else
    	{
    		return getDataTable(null);
    	}
	}

    /**
     * 导出属性池列表
     */
    @PreAuthorize("@ss.hasPermi('os:attribute:export')" )
    @Log(title = "属性池" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Attribute attribute) {
        LambdaQueryWrapper<Attribute> lqw = new LambdaQueryWrapper<Attribute>(attribute);
        List<Attribute> list = iAttributeService.list(lqw);
        ExcelUtil<Attribute> util = new ExcelUtil<Attribute>(Attribute. class);
        return util.exportExcel(list, "attribute" );
    }

    /**
     * 获取属性池详细信息
     */
    @ApiOperation("查询属性详细信息")
    @ApiImplicitParam(name="id", value="属性id", required=true, dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:attribute:list')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iAttributeService.getById(id));
    }

    /**
     * 新增属性池
     */
    @ApiOperation("保存属性信息")
    @ApiImplicitParam(name="attribute", value="属性对象", required=true, dataType="Attribute", dataTypeClass = Attribute.class)
    @PreAuthorize("@ss.hasPermi('os:attribute:add')" )
    @Log(title = "属性池" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Attribute attribute) {
    	
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

    	attribute.setCreateById(loginUser.getUser().getUserId());
    	attribute.setCreateByName(loginUser.getUser().getUserName());
    	attribute.setCreateTime(DateUtils.getNowDate());
    	return toAjax(iAttributeService.save(attribute) ? 1 : 0);
    }

    /**
     * 修改属性池
     */
    @ApiOperation("修改属性信息")
    @ApiImplicitParam(name="attribute", value="属性对象", required=true, dataType="Attribute", dataTypeClass = Attribute.class)
    @PreAuthorize("@ss.hasPermi('os:attribute:edit')" )
    @Log(title = "属性池" , businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody Attribute attribute) {
        return toAjax(iAttributeService.updateById(attribute) ? 1 : 0);
    }

}
