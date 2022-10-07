package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.liuxn.yuzhong.project.os.domain.Hybrid;
import com.liuxn.yuzhong.project.os.service.IHybridService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 杂交组合库Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="管理端-杂交组合库管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/hybrid" )
public class HybridController extends BaseController {

    private final IHybridService iHybridService;
    
    private final TokenService tokenService;

    /**
     * 查询杂交组合库列表
     */
    @ApiOperation("查询杂交组合库列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="maleParent", value="maleParent", dataType="String", dataTypeClass=String.class),
    	@ApiImplicitParam(name="femaleParent", value="femaleParent", dataType="String", dataTypeClass=String.class),
    	@ApiImplicitParam(name="hybridYear", value="hybridYear", dataType="String", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:hybrid:list')")
    @GetMapping("/list")
    public TableDataInfo list(Hybrid hybrid)
    {
        startPage();
        
        List<Hybrid> list = iHybridService.queryList(hybrid);
        return getDataTable(list);
    }
    
    /**
     * 查询杂交组合库列表
     */
    @ApiOperation("通过年份查询杂交组合库列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridYear", value="hybridYear", dataType="String", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:hybrid:list')")
    @GetMapping("/list_by_year")
    public AjaxResult yearList(Hybrid hybrid)
    {
        if (hybrid == null || StringUtils.isEmpty(hybrid.getHybridYear())){
            return AjaxResult.error("年份不能为空");
        }

        
        List<Hybrid> list = iHybridService.queryList(hybrid);
        
        if (list == null || list.size() == 0){
            return AjaxResult.success();
        }
        
        JSONArray jsonArray = new JSONArray();
        for(Hybrid hybridObj : list)
        {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", hybridObj.getId());
        	jsonObject.put("name", hybridObj.getName());
        	jsonObject.put("sowingCode", hybridObj.getSowingCode());
        	jsonArray.add(jsonObject);
        }
        
        return AjaxResult.success(jsonArray);
    }
    
    @ApiOperation("导入杂交数据")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="file", value="file", dataType="MultipartFile", dataTypeClass=MultipartFile.class)
    })
    @PreAuthorize("@ss.hasPermi('os:hybrid:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<Hybrid> util = new ExcelUtil<Hybrid>(Hybrid.class);
        List<Hybrid> hybridList = util.importExcel(file.getInputStream());
        if(hybridList == null || hybridList.size() == 0)
        {
        	return AjaxResult.error("导入数据为空");
        }
        for(int i = 0; i < hybridList.size(); i++)
        {
        	Hybrid hybrid = hybridList.get(i);
        	if(StringUtils.isEmpty(hybrid.getMaleParent()))
        	{
        		return AjaxResult.error("第" + (i+1) + "行的父本信息为空");
        	}
        	if(StringUtils.isEmpty(hybrid.getFemaleParent()))
        	{
        		return AjaxResult.error("第" + (i+1) + "行的母本信息为空");
        	}
        	if(StringUtils.isEmpty(hybrid.getSowingCode()))
        	{
        		return AjaxResult.error("第" + (i+1) + "行的组合代号为空");
        	}
        	if(hybrid.getColonizationTotal() == null)
        	{
        		return AjaxResult.error("第" + (i+1) + "行的定植总数为空");
        	}
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        boolean updateSupport = true;
        String message = iHybridService.importHybrid(hybridList, updateSupport, loginUser.getUser());
        return AjaxResult.success(message);
    }

    @ApiOperation("获取杂交数据导入模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Hybrid> util = new ExcelUtil<Hybrid>(Hybrid.class);
        return util.importTemplateExcel("杂交数据");
    }

    /**
     * 导出杂交组合库列表
     */
    @ApiOperation("导出杂交组合库列表")
    @ApiImplicitParam(name="hybrid", value="hybrid", dataType="Hybrid", dataTypeClass=Hybrid.class)
    @PreAuthorize("@ss.hasPermi('os:hybrid:export')" )
    @Log(title = "杂交组合库" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Hybrid hybrid) {

        List<Hybrid> list = iHybridService.queryList(hybrid);
        ExcelUtil<Hybrid> util = new ExcelUtil<Hybrid>(Hybrid. class);
        return util.exportExcel(list, "hybrid" );
    }

    /**
     * 获取杂交组合库详细信息
     */
    @ApiOperation("获取杂交组合库详细信息")
    @ApiImplicitParam(name="id", value="id", dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:hybrid:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iHybridService.getById(id));
    }

    /**
     * 新增杂交组合库
     */
    @ApiOperation("获取杂交组合库详细信息")
    @ApiImplicitParam(name="id", value="id", dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:hybrid:add')" )
    @Log(title = "杂交组合库" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Hybrid hybrid) {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	hybrid.setCreateById(loginUser.getUser().getUserId());
    	hybrid.setCreateByName(loginUser.getUser().getUserName());
    	hybrid.setHybridYear(DateUtils.parseDateToStr(DateUtils.YYYY, hybrid.getHybridDate()));
    	hybrid.setName(hybrid.getFemaleParent() + " X " + hybrid.getMaleParent());
    	hybrid.setDelFlag(0);
        return toAjax(iHybridService.save(hybrid) ? 1 : 0);
    }

    /**
     * 修改杂交组合库
     */
    @ApiOperation("修改杂交组合库")
    @ApiImplicitParam(name="hybrid", value="杂交对象", dataType="Hybrid", dataTypeClass=Hybrid.class)
    @PreAuthorize("@ss.hasPermi('os:hybrid:edit')" )
    @Log(title = "杂交组合库" , businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody Hybrid hybrid) {
    	
    	hybrid.setName(hybrid.getFemaleParent() + " X " + hybrid.getMaleParent());
        return toAjax(iHybridService.updateById(hybrid) ? 1 : 0);
    }

    /**
     * 删除杂交组合库
     */
    @PreAuthorize("@ss.hasPermi('os:hybrid:remove')" )
    @Log(title = "杂交组合库" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iHybridService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
