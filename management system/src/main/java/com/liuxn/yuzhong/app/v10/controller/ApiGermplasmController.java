package com.liuxn.yuzhong.app.v10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.Germplasm;
import com.liuxn.yuzhong.project.os.service.IGermplasmService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-种质信息管理")
@RestController
@RequestMapping("/app/v10/germplasm" )
public class ApiGermplasmController extends APPBaseController {

	@Autowired
    private IGermplasmService iGermplasmService;

	/**
     * 查询杂交组合库列表
     */
    @ApiOperation("通过名称查询种质信息列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="name", value="种质信息名称", dataType="String", dataTypeClass=String.class)
    })
    @LoginRequired
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
    @LoginRequired
    @GetMapping("/listByYear")
    public AjaxResult yearList(String introductionYear)
    {
        if (StringUtils.isEmpty(introductionYear)){
            return AjaxResult.error("年份不能为空");
        }
        LambdaQueryWrapper<Germplasm> lqw = new LambdaQueryWrapper<Germplasm>();
        lqw.eq(Germplasm::getIntroductionYear, introductionYear);
        lqw.eq(Germplasm::getDelFlag, 0);//未删除
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

}
