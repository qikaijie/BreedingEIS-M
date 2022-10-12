package com.liuxn.yuzhong.project.os.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.framework.config.ServerConfig;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.PlantImage;
import com.liuxn.yuzhong.project.os.service.IPlantImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 植物图片、视频记录Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "管理端-杂交植物记录中的图片管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/plant/image" )
public class PlantImageController extends BaseController {

    private final IPlantImageService iPlantImageService;
    
    private final ServerConfig serverConfig;

    /**
     * 查询植物图片、视频记录列表
     */
    @ApiOperation("查询植物图片列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="plantId", value="plantId植物id", required = true, dataType="Long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="recordId", value="recordId记录id", required = true, dataType="Long", dataTypeClass=Long.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant/image:list')")
    @GetMapping("/list")
    public AjaxResult list(Long plantId, Long recordId)
    {
        LambdaQueryWrapper<PlantImage> lqw = new LambdaQueryWrapper<PlantImage>();
        lqw.eq(PlantImage::getPlantId ,plantId);
        lqw.eq(PlantImage::getRecordId ,recordId);
        List<PlantImage> imgList = iPlantImageService.list(lqw);
        if(imgList != null && imgList.size() > 0)
		{
			for(PlantImage img : imgList)
			{
				img.setPath(serverConfig.getUrl() + img.getPath());
			}
		}
        
        return AjaxResult.success("查询成功", imgList);
    }

}
