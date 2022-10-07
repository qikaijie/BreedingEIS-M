package com.liuxn.yuzhong.project.os.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.common.utils.YuZhongCodeUtils;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.config.YuZhongConfig;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.AutoGeneratePlant;
import com.liuxn.yuzhong.project.os.domain.ImportPlantVo;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.service.IPlantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="管理端-杂交植物信息管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/plant" )
public class PlantController extends BaseController {

    private final IPlantService iPlantService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 查询植物列表
     */
    @ApiOperation("根据杂交id查询植物列表")
    @ApiImplicitParam(name="hybridId", value="杂交id", required = true, dataType="long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:plant:list')")
    @GetMapping("/listByHybridId")
    public AjaxResult listByHybridId(Long hybridId)
    {
    	if(hybridId == null)
    	{
    		return AjaxResult.error("杂交id不能为空，查询失败");
    	}
        LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        lqw.eq(Plant::getDelFlag, 0);//未删除
        lqw.eq(Plant::getHybridId ,hybridId);
        lqw.orderByAsc(Plant::getNumber);
        
        List<Plant> list = iPlantService.list(lqw);
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 查询植物列表
     */
    @ApiOperation("查询植物列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridId", value="杂交id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="parentId", value="父植物id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="type", value="类型（1-初选，2-高接）", dataType="Integer", dataTypeClass=Integer.class),
    	@ApiImplicitParam(name="code", value="植物code", dataType="string", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant:list')")
    @GetMapping("/list")
    public TableDataInfo list(Plant plant)
    {
    	startPage();
    	
    	List<Plant> list = iPlantService.queryList(plant);
        return getDataTable(list);
    }
    
    /**
     * 导出植物列表
     */
    @ApiOperation("导出植物列表")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridId", value="杂交id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="parentId", value="父植物id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="type", value="类型（1-初选，2-高接）", dataType="Integer", dataTypeClass=Integer.class),
    	@ApiImplicitParam(name="code", value="植物code", dataType="string", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant:export')" )
    @Log(title = "植物" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Plant plant) {

        List<Plant> list = iPlantService.queryList(plant);
        
        ExcelUtil<Plant> util = new ExcelUtil<Plant>(Plant. class);
        return util.exportExcel(list, "plant" );
    }

    /**
     * 导出植物列表
     */
    @ApiOperation("导出植物二维码信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridId", value="杂交id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="parentId", value="父植物id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="type", value="类型（1-初选，2-高接）", dataType="Integer", dataTypeClass=Integer.class),
    	@ApiImplicitParam(name="code", value="植物code", dataType="string", dataTypeClass=String.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant:export')" )
    @Log(title = "植物" , businessType = BusinessType.EXPORT)
    @GetMapping("/exportQRcode" )
    public void exportQRcode(Plant plant, HttpServletResponse response) throws IOException {

        List<Plant> list = iPlantService.queryList(plant);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        if(list != null && list.size() > 0)
        {
        	for(Plant plantObj : list)
        	{
        		String fileName = plantObj.getCode() + ".png";
        		String filePath = YuZhongConfig.getQRCodePath() + File.separator + fileName;
            	File qrCodeFile = new File(filePath);
            	if(!qrCodeFile.exists())
            	{
            		YuZhongCodeUtils.createPlantCode("果树资源与育种信息管理系统", plantObj.getCodeOne(), plantObj.getCodeTwo(), "南京农业大学 监制", filePath, 400, 400);
            	}
            	
            	try 
        		{
        			// 添加到zip
                    zip.putNextEntry(new ZipEntry(fileName));
                    byte[] fileBytes = FileUtils.readFileToByteArray(qrCodeFile);
                    zip.write(fileBytes);
                    zip.flush();
                    zip.closeEntry();
        	        
        	    } catch (IOException e) {
        	    	
        	    }
        	}
        }
        IOUtils.closeQuietly(zip);
        
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"yuzhong.qrcode.zip\"");
        response.addHeader("Content-Length", "" + outputStream.toByteArray().length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(outputStream.toByteArray(), response.getOutputStream());
    }

    /**
     * 获取植物详细信息
     */
    @ApiOperation("获取植物详细信息")
    @ApiImplicitParam(name="id", value="plant对象id", dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:plant:list')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPlantService.getById(id));
    }
    
    /**
     * 获取植物详细信息
     */
    @ApiOperation("获取植物的二维码")
    @ApiImplicitParam(name="id", value="plant对象id", dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:plant:export')" )
    @GetMapping(value = "/getQRCode/{id}" )
    public AjaxResult getQRCode(@PathVariable("id" ) Long id, String lang) {
    	
    	Plant plant = iPlantService.getById(id);
    	if(plant == null)
    	{
    		return AjaxResult.error("植物信息不存在，请重新选择。");
    	}
    	
    	
    	String filePath = YuZhongConfig.getQRCodePath() + File.separator + plant.getCode() + ".png";
    	File qrCodeFile = new File(filePath);
    	if(qrCodeFile.exists())
    	{
    		ByteArrayOutputStream bos = null;
    		try 
    		{
    			BufferedImage image = ImageIO.read(qrCodeFile);
    			String imageString = null;
    			bos = new ByteArrayOutputStream();
    	   
    	        ImageIO.write(image, "png", bos);
    	        byte[] imageBytes = bos.toByteArray();
    	        imageString = Base64.encodeBase64String(imageBytes);
    	        
    	        return AjaxResult.success("操作成功", "data:image/png;base64," + imageString);
    	        
    	    } catch (IOException e) {
    	    	return AjaxResult.error("二维码加载失败，请重新尝试。");
    	    }finally {
    	    	try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	}
    	
    	String fileString = null;
    	if("en".equals(lang))
    	{
    		fileString = YuZhongCodeUtils.createPlantCode("", plant.getCodeOne(), plant.getCodeTwo(), "", filePath, 400, 400);
    	}
    	else
    	{
    		fileString = YuZhongCodeUtils.createPlantCode("果树资源与育种信息管理系统", plant.getCodeOne(), plant.getCodeTwo(), "南京农业大学 监制", filePath, 400, 400);
    	}
    	
        return AjaxResult.success("操作成功", fileString);
    }

    /**
     * 新增植物
     */
    @ApiOperation("批量新增植物")
    @ApiImplicitParam(name="autoGeneratePlant", value="autoGeneratePlant对象", required=true, dataType="AutoGeneratePlant", dataTypeClass=AutoGeneratePlant.class)
    @PreAuthorize("@ss.hasPermi('os:plant:add')" )
    @Log(title = "植物" , businessType = BusinessType.INSERT)
    @PostMapping("/autoGenerate")
    public AjaxResult autoGenerate(@RequestBody AutoGeneratePlant autoGeneratePlant) {
        
    	//JSONArray jsonArray = JSONArray.
    	JSONObject[] tableData = autoGeneratePlant.getTableData();
    	if(tableData == null || tableData.length == 0)
    	{
    		return AjaxResult.error("定植数量不能小于1");
    	}
    	
    	List<ImportPlantVo> plantCodeList = new ArrayList<ImportPlantVo>();
    	for(JSONObject obj : tableData)
    	{
    		JSONArray jsonArray = obj.getJSONArray("values");
    		if(jsonArray != null && jsonArray.size() > 0)
    		{
    			for(int i = 0; i < jsonArray.size(); i++)
    			{
    				ImportPlantVo importPlantVo = jsonArray.getObject(i, ImportPlantVo.class);

    				if(StringUtils.isNotEmpty(importPlantVo.getValue()))
    				{
    					plantCodeList.add(importPlantVo);
    				}
    			}
    		}
    	}
    	
    	if(plantCodeList.size() == 0)
    	{
    		return AjaxResult.error("定植数量不能小于1");
    	}
    	
    	//列表需要重新排序
    	Collections.sort(plantCodeList);
    	
    	//当前数据库中最大的number值
    	Long maxNumber = iPlantService.getMaxNumber(autoGeneratePlant.getFieldNumber());
    	
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

    	String message = "<div style='height: 300px; overflow-y: auto;'>";
    	for(int j = 0; j < plantCodeList.size(); j++)
    	{
    		maxNumber++;
    		
    		ImportPlantVo plantCode = plantCodeList.get(j);
    		
    		Plant plant = new Plant();
    		plant.setType(1);//初选
    		plant.setDelFlag(0);
    		plant.setHybridId(autoGeneratePlant.getHybridId());
    		plant.setSowingCode(autoGeneratePlant.getSowingCode());
    		plant.setPlantBase(autoGeneratePlant.getPlantBase());
    		plant.setPlantArea(autoGeneratePlant.getPlantArea());
    		plant.setPlantRidge(plantCode.getPlantRidge());
    		plant.setPlantRow(plantCode.getPlantRow());
    		plant.setPlantNumber(plantCode.getLineNum());
    		plant.setFieldNumber(autoGeneratePlant.getFieldNumber());
    		plant.setNumber(maxNumber);
    		plant.setCodeOne(plant.getSowingCode() + "-" + plant.getPlantBase() + plant.getPlantArea() + "-" + plantCode.getValue());
    		plant.setCodeTwo(plant.getFieldNumber() + "-" + plant.getNumber());
    		plant.setCode(plant.getCodeOne() + "@" + plant.getCodeTwo());
    		plant.setCreateById(loginUser.getUser().getUserId());
    		plant.setCreateByName(loginUser.getUser().getUserName());
    		plant.setCreateTime(DateUtils.getNowDate());
    		
    		LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        	lqw.eq(Plant::getCodeOne, plant.getCodeOne());
        	lqw.or();
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
    
    
    @PreAuthorize("@ss.hasPermi('os:plant:remove')" )
    @Log(title = "植物" , businessType = BusinessType.DELETE)
    @GetMapping("/delList" )
    public AjaxResult delList(Plant plant) {

        List<Plant> list = iPlantService.queryList(plant);
        if(list == null || list.size() == 0)
        {
        	return AjaxResult.error("没有可删除的数据");
        }
        
        List<Long> ids = new ArrayList<>();
    	for(Plant plantObj : list)
    	{
    		ids.add(plantObj.getId());
    	}
    	
    	return toAjax(iPlantService.removeByIds(ids) ? 1 : 0);
    }
    
    /**
     * 删除植物
     */
    @PreAuthorize("@ss.hasPermi('os:plant:remove')" )
    @Log(title = "植物" , businessType = BusinessType.DELETE)
    @GetMapping("/del/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPlantService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
