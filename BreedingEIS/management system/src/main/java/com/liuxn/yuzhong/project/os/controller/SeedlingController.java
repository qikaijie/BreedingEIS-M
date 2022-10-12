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
import com.liuxn.yuzhong.project.os.domain.AutoGenerateSeedling;
import com.liuxn.yuzhong.project.os.domain.ImportSeedlingVo;
import com.liuxn.yuzhong.project.os.domain.Seedling;
import com.liuxn.yuzhong.project.os.service.ISeedlingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 植物Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags="管理端-种质植物信息管理")
@AllArgsConstructor
@RestController
@RequestMapping("/os/seedling" )
public class SeedlingController extends BaseController {

    private final ISeedlingService iSeedlingService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 查询植物列表
     */
    @ApiOperation("查询种质植物列表")
    @ApiImplicitParam(name="seedling", value="种质信息", required = true, dataType="Seedling", dataTypeClass=Seedling.class)
    @PreAuthorize("@ss.hasPermi('os:seedling:list')")
    @GetMapping("/list")
    public TableDataInfo list(Seedling seedling)
    {
        startPage();
        List<Seedling> list = iSeedlingService.queryList(seedling);
        return getDataTable(list);
    }

    /**
     * 导出植物列表
     */
    @ApiOperation("查询种质植物列表")
    @ApiImplicitParam(name="seedling", value="种质信息", required = true, dataType="Seedling", dataTypeClass=Seedling.class)
    @PreAuthorize("@ss.hasPermi('os:seedling:export')" )
    @Log(title = "植物" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Seedling seedling) {
    	
        List<Seedling> list = iSeedlingService.queryList(seedling);
        
        ExcelUtil<Seedling> util = new ExcelUtil<Seedling>(Seedling. class);
        return util.exportExcel(list, "seedling" );
    }
    
    /**
     * 导出植物列表
     */
    @ApiOperation("导出植物二维码信息")
    @ApiImplicitParam(name="seedling", value="种质信息", required = true, dataType="Seedling", dataTypeClass=Seedling.class)
    @PreAuthorize("@ss.hasPermi('os:seedling:export')" )
    @Log(title = "植物" , businessType = BusinessType.EXPORT)
    @GetMapping("/exportQRcode" )
    public void exportQRcode(Seedling seedling, HttpServletResponse response) throws IOException {
        List<Seedling> list = iSeedlingService.queryList(seedling);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        if(list != null && list.size() > 0)
        {
        	for(Seedling tempSeedling : list)
        	{
        		String fileName = tempSeedling.getCode() + ".png";
        		String filePath = YuZhongConfig.getQRCodePath() + File.separator + fileName;
            	File qrCodeFile = new File(filePath);
            	if(!qrCodeFile.exists())
            	{
            		YuZhongCodeUtils.createPlantCode("果树资源与育种信息管理系统", tempSeedling.getCodeOne(), tempSeedling.getCodeTwo(), "南京农业大学 监制", filePath, 400, 400);
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
    @ApiOperation("查询种质植物信息")
    @ApiImplicitParam(name="id", value="种质信息id", required = true, dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:seedling:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iSeedlingService.getById(id));
    }
    
    /**
     * 获取植物详细信息
     */
    @ApiOperation("获取种质植物的二维码")
    @ApiImplicitParam(name="id", value="seedling对象id", dataType="Long", dataTypeClass=Long.class)
    @PreAuthorize("@ss.hasPermi('os:seedling:query')" )
    @GetMapping(value = "/getQRCode/{id}" )
    public AjaxResult getQRCode(@PathVariable("id" ) Long id, String lang) {
    	
    	Seedling seedling = iSeedlingService.getById(id);
    	if(seedling == null)
    	{
    		return AjaxResult.error("植物信息不存在，请重新选择。");
    	}
    	
    	
    	String filePath = YuZhongConfig.getQRCodePath() + File.separator + seedling.getCode() + ".png";
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
    		fileString = YuZhongCodeUtils.createPlantCode("", seedling.getCodeOne(), seedling.getCodeTwo(), "", filePath, 400, 400);
    	}
    	else
    	{
    		fileString = YuZhongCodeUtils.createPlantCode("果树资源与育种信息管理系统", seedling.getCodeOne(), seedling.getCodeTwo(), "南京农业大学 监制", filePath, 400, 400);
    	}
    	
        return AjaxResult.success("操作成功", fileString);
    }

    /**
     * 新增植物
     */
    @ApiOperation("批量新增种质植物")
    @ApiImplicitParam(name="autoGenerateSeedling", value="autoGenerateSeedling对象", required=true, dataType="AutoGenerateSeedling", dataTypeClass=AutoGenerateSeedling.class)
    @PreAuthorize("@ss.hasPermi('os:plant:add')" )
    @Log(title = "植物" , businessType = BusinessType.INSERT)
    @PostMapping("/autoGenerate")
    public AjaxResult autoGenerate(@RequestBody AutoGenerateSeedling autoGenerateSeedling) {
    	//JSONArray jsonArray = JSONArray.
    	JSONObject[] tableData = autoGenerateSeedling.getTableData();
    	if(tableData == null || tableData.length == 0)
    	{
    		return AjaxResult.error("定植数量不能小于1");
    	}
    	
    	List<ImportSeedlingVo> plantCodeList = new ArrayList<ImportSeedlingVo>();
    	for(JSONObject obj : tableData)
    	{
    		JSONArray jsonArray = obj.getJSONArray("values");
    		if(jsonArray != null && jsonArray.size() > 0)
    		{
    			for(int i = 0; i < jsonArray.size(); i++)
    			{
    				ImportSeedlingVo importSeedlingVo = jsonArray.getObject(i, ImportSeedlingVo.class);

    				if(StringUtils.isNotEmpty(importSeedlingVo.getValue()))
    				{
    					plantCodeList.add(importSeedlingVo);
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
    	Long maxNumber = iSeedlingService.getMaxNumber(autoGenerateSeedling.getFieldNumber());
    	
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

    	String message = "<div style='height: 300px; overflow-y: auto;'>";
    	for(int j = 0; j < plantCodeList.size(); j++)
    	{
    		maxNumber++;
    		
    		ImportSeedlingVo plantCode = plantCodeList.get(j);
    		
    		Seedling seedling = new Seedling();
    		seedling.setType(1);//初选
    		seedling.setDelFlag(0);
    		seedling.setGermplasmId(autoGenerateSeedling.getGermplasmId());
    		seedling.setSowingCode(autoGenerateSeedling.getSowingCode());
    		seedling.setSeedlingBase(autoGenerateSeedling.getPlantBase());
    		seedling.setSeedlingArea(autoGenerateSeedling.getPlantArea());
    		seedling.setSeedlingRidge(plantCode.getPlantRidge());
    		seedling.setSeedlingRow(plantCode.getPlantRow());
    		seedling.setSeedlingNumber(plantCode.getLineNum());
    		seedling.setFieldNumber(autoGenerateSeedling.getFieldNumber());
    		seedling.setNumber(maxNumber);
    		seedling.setCodeOne(seedling.getSowingCode() + "-" + seedling.getSeedlingBase() + seedling.getSeedlingArea() + "-" + plantCode.getValue());
    		seedling.setCodeTwo(seedling.getFieldNumber() + "-" + seedling.getNumber());
    		seedling.setCode(seedling.getCodeOne() + "@" + seedling.getCodeTwo());
    		seedling.setCreateById(loginUser.getUser().getUserId());
    		seedling.setCreateByName(loginUser.getUser().getUserName());
    		seedling.setCreateTime(DateUtils.getNowDate());
    		
    		LambdaQueryWrapper<Seedling> lqw = new LambdaQueryWrapper<Seedling>();
        	lqw.eq(Seedling::getCodeOne, seedling.getCodeOne());
        	lqw.or();
        	lqw.eq(Seedling::getCodeTwo, seedling.getCodeTwo());
        	
        	List<Seedling> plantList = iSeedlingService.list(lqw);
        	if(plantList == null || plantList.size() == 0)
        	{
        		if(iSeedlingService.save(seedling))
        		{
        			message += "<div style='color:green;'>植物编号【" + seedling.getCodeTwo() + "】生成成功</div>";
        		}
        		else
        		{
        			message += "<div style='color:red;'>植物编号【" + seedling.getCodeTwo() + "】生成失败</div>";
        		}
        	}
        	else
        	{
        		message += "<div style='color:orange;'>植物编号【" + seedling.getCodeTwo() + "】已经存在</div>";
        	}
    	}
    	
    	message += "</div>";
    	
    	logger.info(message);
    	
    	return AjaxResult.success(message);
    }
    
    @PreAuthorize("@ss.hasPermi('os:seedling:remove')" )
    @Log(title = "删除植物" , businessType = BusinessType.DELETE)
    @GetMapping("/delList" )
    public AjaxResult delList(Seedling seedling) {

        List<Seedling> list = iSeedlingService.queryList(seedling);
        if(list == null || list.size() == 0)
        {
        	return AjaxResult.error("没有可删除的数据");
        }
        
        List<Long> ids = new ArrayList<>();
    	for(Seedling seedlingObj : list)
    	{
    		ids.add(seedlingObj.getId());
    	}
    	
    	return toAjax(iSeedlingService.removeByIds(ids) ? 1 : 0);
    }
    
    /**
     * 删除植物
     */
    @PreAuthorize("@ss.hasPermi('os:seedling:remove')" )
    @Log(title = "删除植物" , businessType = BusinessType.DELETE)
    @GetMapping("/del/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iSeedlingService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

}
