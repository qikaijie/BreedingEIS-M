package com.liuxn.yuzhong.app.v10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.project.os.service.ISeedlingImageService;

import io.swagger.annotations.Api;

/**
 * 植物图片、视频记录Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-种质植物图片信息管理")
@RestController
@RequestMapping("/app/v10/seedling/image" )
public class ApiSeedlingImageController extends BaseController {

	@Autowired
    private ISeedlingImageService iSeedlingImageService;


    
    
}
