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
import com.liuxn.yuzhong.project.os.domain.Germplasm;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.domain.Seedling;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.service.IGermplasmService;
import com.liuxn.yuzhong.project.os.service.ISeedlingCollectService;
import com.liuxn.yuzhong.project.os.service.ISeedlingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-种质植物信息管理")
@RestController
@RequestMapping("/app/v10/seedling" )
public class ApiSeedlingController extends APPBaseController {

	@Autowired
    private ISeedlingService iSeedlingService;
	
	@Autowired
	private IGermplasmService iGermplasmService;
	
	@Autowired
	private ISeedlingCollectService iSeedlingCollectService;

    /**
     * 获取植物详细信息
     */
	@ApiOperation("植物信息查询")
	@ApiParam(name = "code", value = "植物条形码", required = true)
	@LoginRequired
    @GetMapping(value = "/info" )
    public AjaxResult getInfo(String code) {
    	if(StringUtils.isEmpty(code))
    	{
    		return AjaxResult.error("code不能为空");
    	}
    	LambdaQueryWrapper<Seedling> lqw = new LambdaQueryWrapper<Seedling>();
    	lqw.eq(Seedling::getCode, code).or().eq(Seedling::getCodeTwo, code).or().eq(Seedling::getCodeOne, code);
    	List<Seedling> seedlingList = iSeedlingService.list(lqw);
    	if(seedlingList == null || seedlingList.size() == 0)
    	{
    		return AjaxResult.error("code信息不正确，没有对应的植物信息");
    	}
    	
    	JSONObject json = new JSONObject();
    	
    	//植物信息
    	Seedling seedling = seedlingList.get(0);
    	json.put("id", seedling.getId());
    	json.put("code", seedling.getCode());
    	json.put("codeOne", seedling.getCodeOne());
    	json.put("codeTwo", seedling.getCodeTwo());
    	
    	String codeOne_prev = seedling.getSowingCode() + "-" + seedling.getSeedlingBase() + seedling.getSeedlingArea() + "-" + seedling.getSeedlingRidge() + seedling.getSeedlingRow();
    	String codeTwo_prev = seedling.getFieldNumber();
    	
    	//下一颗植物编码
    	String nextCode = codeOne_prev + "-" + (seedling.getSeedlingNumber() + 1) + "@" + codeTwo_prev + "-" + (seedling.getNumber() + 1);
    	LambdaQueryWrapper<Seedling> nextLQW = new LambdaQueryWrapper<Seedling>();
    	nextLQW.eq(Seedling::getCode, nextCode);
    	if(iSeedlingService.count(nextLQW) > 0)
    	{
    		json.put("nextCode", nextCode);
    	}
    	
    	//上一颗植物编码
    	String prevCode = codeOne_prev + "-" + (seedling.getSeedlingNumber() - 1) + "@" + codeTwo_prev + "-" + (seedling.getNumber() - 1);
    	LambdaQueryWrapper<Seedling> prevLQW = new LambdaQueryWrapper<Seedling>();
    	prevLQW.eq(Seedling::getCode, prevCode);
    	if(iSeedlingService.count(prevLQW) > 0)
    	{
    		json.put("prevCode", prevCode);
    	}
    	
    	//种质信息
    	Germplasm germplasm = iGermplasmService.getById(seedling.getGermplasmId());
    	if(germplasm != null)
    	{
    		json.put("germplasmName", germplasm.getName());
    		json.put("germplasmId", germplasm.getId());
    	}
    	
    	//收藏信息
    	LambdaQueryWrapper<SeedlingCollect> collectLQW = new LambdaQueryWrapper<SeedlingCollect>();
    	collectLQW.eq(SeedlingCollect::getUserId, getLoginUser().getUserId());
    	collectLQW.eq(SeedlingCollect::getSeedlingId, seedling.getId());
    	List<SeedlingCollect> collectList = iSeedlingCollectService.list(collectLQW);
    	if(collectList != null && collectList.size() > 0)
    	{
    		json.put("isCollect", true);
    		json.put("collectId", collectList.get(0).getId());
    		json.put("collectLevel", collectList.get(0).getLevel());
    	}
    	else
    	{
    		json.put("isCollect", false);
    	}
    	
        return AjaxResult.success(json);
    }
	
	/**
     * 查询种质植物列表
     */
    @ApiOperation("通过名称查询植物列表（返回前10条）")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="code", value="植物code", dataType="String", dataTypeClass=String.class)
    })
    @LoginRequired
    @GetMapping("/listByCode")
    public AjaxResult listByName(String code)
    {
        if (StringUtils.isEmpty(code)){
            return AjaxResult.error("植物编码不能为空");
        }
        LambdaQueryWrapper<Seedling> lqw = new LambdaQueryWrapper<Seedling>();
        lqw.like(Seedling::getCode, code);
        lqw.last("limit 10");
        List<Seedling> list = iSeedlingService.list(lqw);
        
        if (list == null || list.size() == 0){
            return AjaxResult.success();
        }
        
        JSONArray jsonArray = new JSONArray();
        for(Seedling seedling : list)
        {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", seedling.getId());
        	jsonObject.put("code", seedling.getCode());
        	jsonArray.add(jsonObject);
        }
        
        return AjaxResult.success(jsonArray);
    }

}
