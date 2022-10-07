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
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.service.IHybridService;
import com.liuxn.yuzhong.project.os.service.IPlantCollectService;
import com.liuxn.yuzhong.project.os.service.IPlantService;

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
@Api(tags = "移动端-杂交植物信息管理")
@RestController
@RequestMapping("/app/v10/plant" )
public class ApiPlantController extends APPBaseController {

	@Autowired
    private IPlantService iPlantService;
	
	@Autowired
	private IHybridService iHybridService;
	
	@Autowired
	private IPlantCollectService iPlantCollectService;

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
    	LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
    	lqw.eq(Plant::getDelFlag, 0);//未删除
    	lqw.eq(Plant::getCode, code).or().eq(Plant::getCodeTwo, code).or().eq(Plant::getCodeOne, code);
    	List<Plant> plantList = iPlantService.list(lqw);
    	if(plantList == null || plantList.size() == 0)
    	{
    		return AjaxResult.error("code信息不正确，没有对应的植物信息");
    	}
    	
    	JSONObject json = new JSONObject();
    	
    	//植物信息
    	Plant plant = plantList.get(0);
    	json.put("id", plant.getId());
    	json.put("code", plant.getCode());
    	json.put("codeOne", plant.getCodeOne());
    	json.put("codeTwo", plant.getCodeTwo());
    	
    	String codeOne_prev = plant.getSowingCode() + "-" + plant.getPlantBase() + plant.getPlantArea() + "-" + plant.getPlantRidge() + plant.getPlantRow();
    	String codeTwo_prev = plant.getFieldNumber();
    	
    	//下一颗植物编码
    	String nextCode = codeOne_prev + "-" + (plant.getPlantNumber() + 1) + "@" + codeTwo_prev + "-" + (plant.getNumber() + 1);
    	LambdaQueryWrapper<Plant> nextLQW = new LambdaQueryWrapper<Plant>();
    	nextLQW.eq(Plant::getCode, nextCode);
    	if(iPlantService.count(nextLQW) > 0)
    	{
    		json.put("nextCode", nextCode);
    	}
    	
    	//上一颗植物编码
    	String prevCode = codeOne_prev + "-" + (plant.getPlantNumber() - 1) + "@" + codeTwo_prev + "-" + (plant.getNumber() - 1);
    	LambdaQueryWrapper<Plant> prevLQW = new LambdaQueryWrapper<Plant>();
    	prevLQW.eq(Plant::getCode, prevCode);
    	if(iPlantService.count(prevLQW) > 0)
    	{
    		json.put("prevCode", prevCode);
    	}
    	
    	//杂交信息
    	Hybrid hybrid = iHybridService.getById(plant.getHybridId());
    	if(hybrid != null)
    	{
    		json.put("hybridName", hybrid.getMaleParent() + "x" + hybrid.getFemaleParent());
    		json.put("hybridId", hybrid.getId());
    	}
    	
    	//收藏信息
    	LambdaQueryWrapper<PlantCollect> collectLQW = new LambdaQueryWrapper<PlantCollect>();
    	collectLQW.eq(PlantCollect::getUserId, getLoginUser().getUserId());
    	collectLQW.eq(PlantCollect::getPlantId, plant.getId());
    	List<PlantCollect> collectList = iPlantCollectService.list(collectLQW);
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
     * 查询杂交组合植物列表
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
        LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        lqw.eq(Plant::getDelFlag, 0);//未删除
        lqw.like(Plant::getCode, code);
        lqw.last("limit 10");
        List<Plant> list = iPlantService.list(lqw);
        
        if (list == null || list.size() == 0){
            return AjaxResult.success();
        }
        
        JSONArray jsonArray = new JSONArray();
        for(Plant plant : list)
        {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", plant.getId());
        	jsonObject.put("code", plant.getCode());
        	jsonArray.add(jsonObject);
        }
        
        return AjaxResult.success(jsonArray);
    }

}
