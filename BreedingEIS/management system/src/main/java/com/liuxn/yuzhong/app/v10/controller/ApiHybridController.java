package com.liuxn.yuzhong.app.v10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.liuxn.yuzhong.project.os.domain.Hybrid;
import com.liuxn.yuzhong.project.os.service.IHybridService;

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
@Api(tags = "移动端-杂交信息管理")
@RestController
@RequestMapping("/app/v10/hybrid" )
public class ApiHybridController extends APPBaseController {

	@Autowired
    private IHybridService iHybridService;

	/**
     * 查询杂交组合库列表
     */
    @ApiOperation("通过年份查询杂交组合库列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridYear", value="hybridYear", dataType="String", dataTypeClass=String.class)
    })
    @LoginRequired
    @GetMapping("/listByYear")
    public AjaxResult yearList(String hybridYear)
    {
        if (StringUtils.isEmpty(hybridYear)){
            return AjaxResult.error("年份不能为空");
        }
        LambdaQueryWrapper<Hybrid> lqw = new LambdaQueryWrapper<Hybrid>();
        lqw.eq(Hybrid::getHybridYear, hybridYear);
        lqw.eq(Hybrid::getDelFlag, 0);//未删除
        List<Hybrid> list = iHybridService.list(lqw);
        
        if (list == null || list.size() == 0){
            return AjaxResult.success();
        }
        
        JSONArray jsonArray = new JSONArray();
        for(Hybrid hybrid : list)
        {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", hybrid.getId());
        	jsonObject.put("name", hybrid.getName());
        	jsonObject.put("sowingCode", hybrid.getSowingCode());
        	jsonArray.add(jsonObject);
        }
        
        return AjaxResult.success(jsonArray);
    }

}
