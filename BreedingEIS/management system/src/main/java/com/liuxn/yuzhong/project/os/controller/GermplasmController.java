package com.liuxn.yuzhong.project.os.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.liuxn.yuzhong.project.os.domain.Germplasm;
import com.liuxn.yuzhong.project.os.domain.GermplasmVo;
import com.liuxn.yuzhong.project.os.service.IGermplasmService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 种质信息Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags = "管理端-种质信息管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/germplasm" )
public class GermplasmController extends BaseController {

    private final IGermplasmService iGermplasmService;

    private final TokenService tokenService;
    /**
     * 查询种质信息列表
     */
    @ApiOperation("种质信息列表查询")
    @ApiImplicitParam(name="germplasm", value="种质信息对象", dataType="Germplasm", dataTypeClass=Germplasm.class)
    @PreAuthorize("@ss.hasPermi('os:germplasm:list')")
    @GetMapping("/list")
    public TableDataInfo list(Germplasm germplasm)
    {
        startPage();
        
        List<Germplasm> list = iGermplasmService.queryList(germplasm);
        return getDataTable(list);
    }
    
    /**
     * 查询杂交组合库列表
     */
    @ApiOperation("通过名称查询种质信息列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="name", value="种质信息名称", dataType="String", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:germplasm:list')")
    @GetMapping("/listByName")
    public AjaxResult listByName(String name)
    {
        if (StringUtils.isEmpty(name)){
            return AjaxResult.error("种质信息名称不能为空");
        }
        LambdaQueryWrapper<Germplasm> lqw = new LambdaQueryWrapper<Germplasm>();
        lqw.like(Germplasm::getName, name);
        lqw.eq(Germplasm::getDelFlag, 0);//未删除
        lqw.last("limit 10");
        List<Germplasm> list = iGermplasmService.list(lqw);
        
        if (list == null || list.size() == 0){
            return AjaxResult.success();
        }
        
        JSONArray jsonArray = new JSONArray();
        for(Germplasm germplasm : list)
        {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", germplasm.getId());
        	jsonObject.put("name", germplasm.getName());
        	jsonObject.put("code", germplasm.getCode());
        	jsonArray.add(jsonObject);
        }
        
        return AjaxResult.success(jsonArray);
    }
    
    /**
     * 查询杂交组合库列表
     */
    @ApiOperation("通过年份查询种质信息列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="introductionYear", value="引进年份", dataType="String", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:germplasm:list')")
    @GetMapping("/listByYear")
    public AjaxResult yearList(String introductionYear)
    {
        if (StringUtils.isEmpty(introductionYear)){
            return AjaxResult.error("年份不能为空");
        }
        
        Germplasm germplasm = new Germplasm();
        germplasm.setIntroductionYear(introductionYear);
        List<Germplasm> list = iGermplasmService.queryList(germplasm);
        
        if (list == null || list.size() == 0){
            return AjaxResult.success();
        }
        
        JSONArray jsonArray = new JSONArray();
        for(Germplasm germplasmObj : list)
        {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", germplasmObj.getId());
        	jsonObject.put("name", germplasmObj.getName());
        	jsonObject.put("code", germplasmObj.getCode());
        	jsonArray.add(jsonObject);
        }
        
        return AjaxResult.success(jsonArray);
    }

    /**
     * 导出种质信息列表
     */
    @ApiOperation("种质信息导出")
    @ApiImplicitParam(name="germplasm", value="种质信息对象", dataType="Germplasm", dataTypeClass=Germplasm.class)
    @PreAuthorize("@ss.hasPermi('os:germplasm:export')" )
    @Log(title = "种质信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Germplasm germplasm) {

        List<Germplasm> list = iGermplasmService.queryList(germplasm);
        ExcelUtil<Germplasm> util = new ExcelUtil<Germplasm>(Germplasm. class);
        return util.exportExcel(list, "germplasm" );
    }

    /**
     * 获取种质信息详细信息
     */
    @ApiOperation("种质详细信息查询")
    @ApiImplicitParam(name="id", value="种质信息id", dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:germplasm:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iGermplasmService.getById(id));
    }

    /**
     * 新增种质信息
     */
    @ApiOperation("添加种质详细信息")
    @ApiImplicitParam(name="germplasmVo", value="种质信息", dataType="GermplasmVo", dataTypeClass=GermplasmVo.class)
    @PreAuthorize("@ss.hasPermi('os:germplasm:add')" )
    @Log(title = "种质信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GermplasmVo germplasmVo) {
    	
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	germplasmVo.setCreateById(loginUser.getUser().getUserId());
    	germplasmVo.setCreateBy(loginUser.getUser().getUserName());
    	germplasmVo.setDelFlag(0);
        return toAjax(iGermplasmService.save(germplasmVo) ? 1 : 0);
    }

    /**
     * 修改种质信息
     */
    @ApiOperation("修改种质详细信息")
    @ApiImplicitParam(name="germplasmVo", value="种质信息", dataType="GermplasmVo", dataTypeClass=GermplasmVo.class)
    @PreAuthorize("@ss.hasPermi('os:germplasm:edit')" )
    @Log(title = "种质信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GermplasmVo germplasmVo) {
        return toAjax(iGermplasmService.update(germplasmVo) ? 1 : 0);
    }
    
    
    @ApiOperation("导入种质数据")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="file", value="file", dataType="MultipartFile", dataTypeClass=MultipartFile.class)
    })
    @PreAuthorize("@ss.hasPermi('os:germplasm:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<Germplasm> util = new ExcelUtil<Germplasm>(Germplasm.class);
        List<Germplasm> germplasmList = util.importExcel(file.getInputStream());
        if(germplasmList == null || germplasmList.size() == 0)
        {
        	return AjaxResult.error("导入数据为空");
        }
        for(int i = 0; i < germplasmList.size(); i++)
        {
        	Germplasm germplasm = germplasmList.get(i);
        	
        	if(StringUtils.isEmpty(germplasm.getName()))
        	{
        		return AjaxResult.error("第" + (i+1) + "行的种质名称为空");
        	}
        	if(StringUtils.isEmpty(germplasm.getCode()))
        	{
        		return AjaxResult.error("第" + (i+1) + "行的种质编码为空");
        	}
        	if(StringUtils.isEmpty(germplasm.getIntroductionYear()))
        	{
        		return AjaxResult.error("第" + (i+1) + "行的引进年份为空");
        	}
        }
        
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        boolean updateSupport = true;
        String message = iGermplasmService.importGermplasm(germplasmList, updateSupport, loginUser.getUser());
        return AjaxResult.success(message);
    }

    @ApiOperation("获取种质据导入模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Germplasm> util = new ExcelUtil<Germplasm>(Germplasm.class);
        return util.importTemplateExcel("种质数据");
    }

    /**
     * 删除种质信息	
     */
    @PreAuthorize("@ss.hasPermi('os:germplasm:remove')" )
    @Log(title = "种质信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iGermplasmService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
