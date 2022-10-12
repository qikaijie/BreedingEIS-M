package com.liuxn.yuzhong.app.v10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.PlantImage;
import com.liuxn.yuzhong.project.os.service.IPlantImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 植物图片、视频记录Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-杂交植物图片信息管理")
@RestController
@RequestMapping("/app/v10/plant/image" )
public class ApiPlantImageController extends BaseController {

	@Autowired
    private IPlantImageService iPlantImageService;


    
    
}
