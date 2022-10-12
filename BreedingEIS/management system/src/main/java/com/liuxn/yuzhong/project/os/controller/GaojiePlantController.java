package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.AddGaojiePlant;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.service.IPlantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="管理端-杂交植物信息(高接)管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/gaojiePlant" )
public class GaojiePlantController extends BaseController {

    private final IPlantService iPlantService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 新增植物
     */
    @ApiOperation("批量新增植物")
    @ApiImplicitParam(name="addGaojiePlant", value="addGaojiePlant对象", required=true, dataType="AddGaojiePlant", dataTypeClass=AddGaojiePlant.class)
    @PreAuthorize("@ss.hasPermi('os:plant:add')" )
    @Log(title = "植物" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AddGaojiePlant addGaojiePlant) {
        
    	if(addGaojiePlant == null)
    	{
    		return AjaxResult.error("添加失败");
    	}
    	
    	Plant parentPlant = iPlantService.getById(addGaojiePlant.getParentId());
    	if(parentPlant == null)
    	{
    		return AjaxResult.error("父植物信息不存在");
    	}
    	
    	Long maxNum = iPlantService.getMaxGaojieNumber(addGaojiePlant.getParentId());
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	
    	String message = "<div style='height: 300px; overflow-y: auto;'>";
    	for(int i = 0; i < addGaojiePlant.getNumber(); i++)
    	{
    		maxNum++;
    		
    		Plant plant = new Plant();
    		plant.setType(2);//高接
    		plant.setParentId(parentPlant.getId());
    		plant.setGaojieNum(maxNum);
    		plant.setDelFlag(0);
    		plant.setHybridId(parentPlant.getHybridId());
    		plant.setSowingCode(parentPlant.getSowingCode());
    		plant.setPlantBase(parentPlant.getPlantBase());
    		plant.setPlantArea(parentPlant.getPlantArea());
    		plant.setPlantRidge(parentPlant.getPlantRidge());
    		plant.setPlantRow(parentPlant.getPlantRow());
    		plant.setPlantNumber(parentPlant.getPlantNumber());
    		plant.setFieldNumber(parentPlant.getFieldNumber());
    		plant.setNumber(parentPlant.getNumber());
    		plant.setCodeOne(parentPlant.getCodeOne());
    		plant.setCodeTwo(parentPlant.getCodeTwo() + "-" + maxNum);
    		plant.setCode(plant.getCodeOne() + "@" + plant.getCodeTwo());
    		plant.setCreateById(loginUser.getUser().getUserId());
    		plant.setCreateByName(loginUser.getUser().getUserName());
    		plant.setCreateTime(DateUtils.getNowDate());
    		
    		LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        	lqw.eq(Plant::getCodeTwo, plant.getCodeTwo());
        	
        	List<Plant> plantList = iPlantService.list(lqw);
        	if(plantList == null || plantList.size() == 0)
        	{
        		if(iPlantService.save(plant))
        		{
        			message += "<div style='color:green;'>植物编号【" + plant.getCodeTwo() + "】生成成功</div>";
        		}
        		else
        		{
        			message += "<div style='color:red;'>植物编号【" + plant.getCodeTwo() + "】生成失败</div>";
        		}
        	}
        	else
        	{
        		message += "<div style='color:orange;'>植物编号【" + plant.getCodeTwo() + "】已经存在</div>";
        	}
    	}
    	
    	message += "</div>";
    	
    	logger.info(message);
    	
    	return AjaxResult.success(message);
    }
}
