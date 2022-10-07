package com.liuxn.yuzhong.app.v10.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.common.utils.file.FileUploadUtils;
import com.liuxn.yuzhong.common.utils.file.MimeTypeUtils;
import com.liuxn.yuzhong.framework.config.ServerConfig;
import com.liuxn.yuzhong.framework.config.YuZhongConfig;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.service.IPlantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api(tags = "移动端-杂交信息的植物图片、视频上传管理")
@RestController
@RequestMapping(value="/app/v10/plant/upload")
public class PlantUploadController extends APPBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(PlantUploadController.class);
	
	@Autowired
	private IPlantService iPlantService;
	
	@Autowired
	private ServerConfig serverConfig;
	
	/**
	 * POST 文件上传
	 * @param avatar_file
	 * @param avatar_src
	 * @param avatar_data
	 * @return
	 */
	@ApiOperation("图片、视频上传")
	@ApiImplicitParams({
		@ApiImplicitParam(name="file", value="上传的文件", required=true),
		@ApiImplicitParam(name="plantId", value="植物id", required=true, dataTypeClass=Long.class)
	})
	@PostMapping(value = "/file/{plantId}", produces = "application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult uploadFile(MultipartFile file, @PathVariable("plantId")Long plantId) {
		if (!file.isEmpty()) {
			try {
				// 判断文件的MIMEtype
				String type = file.getContentType();
				if (type == null || (!type.toLowerCase().startsWith("image/") && !type.toLowerCase().startsWith("video/"))) {
					logger.error("不支持的文件类型，仅支持图片和视频文件:{}", type);
					return AjaxResult.error(0, "不支持的文件类型，仅支持图片和视频文件!");
				}
				
				Plant plant = iPlantService.getById(plantId);
				if(plant == null)
				{
					logger.error("植物信息不存在，plantId:{}", plantId);
					return AjaxResult.error(0, "植物信息不存在，文件保存失败!");
				}
				
				// 上传文件路径
				StringBuilder uploadPath = new StringBuilder(YuZhongConfig.getUploadPath());
				uploadPath.append(File.separator);
				uploadPath.append(plant.getCodeTwo());
				
	            // 上传并返回新文件名称
	            String fileName = FileUploadUtils.uploadPlantMaterial(uploadPath.toString(), file, MimeTypeUtils.IMAGE_VEDIO_EXTENSION);
	            String url = serverConfig.getUrl() + fileName;
	            AjaxResult ajax = AjaxResult.success("上传成功", fileName);
	            ajax.put("url", url);
	            
				return ajax;
			} catch (Exception e) {
				logger.error("ImageUploadController.uploadHeadPortrait={}", e);
				return AjaxResult.error(0, "上传失败，出现异常：" + e.getMessage());
			}
		}
		return AjaxResult.error(0, "文件不存在,请确认文件地址重新上传!");
	}
}
