package com.liuxn.yuzhong.project.os.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxn.yuzhong.common.utils.ServletUtils;
import com.liuxn.yuzhong.common.utils.poi.AttrExcelUtil;
import com.liuxn.yuzhong.common.utils.poi.ExcelUtil;
import com.liuxn.yuzhong.framework.aspectj.lang.annotation.Log;
import com.liuxn.yuzhong.framework.aspectj.lang.enums.BusinessType;
import com.liuxn.yuzhong.framework.security.LoginUser;
import com.liuxn.yuzhong.framework.security.service.TokenService;
import com.liuxn.yuzhong.framework.web.controller.BaseController;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.framework.web.page.TableDataInfo;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;
import com.liuxn.yuzhong.project.os.domain.Plant;
import com.liuxn.yuzhong.project.os.domain.PlantAttributeList;
import com.liuxn.yuzhong.project.os.domain.PlantCollect;
import com.liuxn.yuzhong.project.os.domain.PlantRecord;
import com.liuxn.yuzhong.project.os.domain.PlantRecordListVo;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.domain.PlantRecordVo;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupService;
import com.liuxn.yuzhong.project.os.service.IPlantCollectService;
import com.liuxn.yuzhong.project.os.service.IPlantRecordService;
import com.liuxn.yuzhong.project.os.service.IPlantService;
import com.liuxn.yuzhong.project.system.domain.SysUser;
import com.liuxn.yuzhong.project.system.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ????????????Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags="?????????-????????????????????????")
@RestController
@RequestMapping("/os/plant/record" )
public class PlantRecordController extends BaseController {

	@Autowired
    private IPlantRecordService iPlantRecordService;
	
	@Autowired
    private IPlantService iPlantService;

	@Autowired
	private IPlantCollectService iPlantCollectService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private IAttributeGroupService iAttributeGroupService;
	
    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="plantId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="startTime", value="??????????????????", dataType="date", dataTypeClass=Date.class),
    	@ApiImplicitParam(name="endTime", value="??????????????????", dataType="date", dataTypeClass=Date.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant/record:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long hybridId, Long plantId, Date startTime, Date endTime)
    {
        startPage();
      
        PlantRecord plantRecord = new PlantRecord();
        plantRecord.setHybridId(hybridId);
        plantRecord.setPlantId(plantId);
        plantRecord.setStartTime(startTime);
        plantRecord.setEndTime(endTime);
        List<PlantRecordListVo> list = iPlantRecordService.queryList(plantRecord);
        if(list != null && list.size() > 0)
        {
        	for(PlantRecordListVo record : list)
        	{
        		LambdaQueryWrapper<PlantCollect> lqw = new LambdaQueryWrapper<PlantCollect>();
                lqw.eq(PlantCollect::getPlantId, record.getPlantId());
                lqw.eq(PlantCollect::getUserId, record.getCreateById());
                
        		List<PlantCollect> collectList = iPlantCollectService.list(lqw);
        		if(collectList != null && collectList.size() > 0)
        		{
        			record.setIsCollect(1);
        			record.setCollectLevel(collectList.get(0).getLevel());
        		}
        	}
        }
        return getDataTable(list);
    }
    
    /**
     * ????????????????????????
     */
    @ApiOperation("????????????id????????????????????????")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="plantId", value="??????id", dataType="long", dataTypeClass=Long.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant/record:list')")
    @GetMapping("/listByPlantId")
    public TableDataInfo listByPlantId(Long plantId)
    {
        startPage();
        LambdaQueryWrapper<PlantRecord> lqw = new LambdaQueryWrapper<PlantRecord>();
        lqw.eq(PlantRecord::getPlantId ,plantId);
        lqw.orderByDesc(PlantRecord::getId);
        
        List<PlantRecord> list = iPlantRecordService.list(lqw);
        return getDataTable(list);
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @ApiImplicitParam(name="plantRecord", value="plantRecord??????", required=true, dataType="PlantRecord", dataTypeClass=PlantRecord.class)
    @PreAuthorize("@ss.hasPermi('os:plant/record:add')" )
    @Log(title = "????????????" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlantRecord plantRecord) {
        return toAjax(iPlantRecordService.save(plantRecord) ? 1 : 0);
    }
    
    /**
     * ????????????????????????
     */
    @PreAuthorize("@ss.hasPermi('os:plant/record:export')" )
    @ApiImplicitParams({
    	@ApiImplicitParam(name="hybridId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="plantId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="startTime", value="??????????????????", dataType="date", dataTypeClass=Date.class),
    	@ApiImplicitParam(name="endTime", value="??????????????????", dataType="date", dataTypeClass=Date.class)
    })
    @Log(title = "??????????????????" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Long hybridId, Long plantId, Date startTime, Date endTime) {
    	
    	PlantRecord plantRecord = new PlantRecord();
        plantRecord.setHybridId(hybridId);
        plantRecord.setPlantId(plantId);
        plantRecord.setStartTime(startTime);
        plantRecord.setEndTime(endTime);
    	List<PlantRecordListVo> list = iPlantRecordService.queryList(plantRecord);
        ExcelUtil<PlantRecordListVo> util = new ExcelUtil<PlantRecordListVo>(PlantRecordListVo.class);
        return util.exportExcel(list, "record" );
    }
    
    @ApiOperation("????????????????????????")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="file", value="file", dataType="MultipartFile", dataTypeClass=MultipartFile.class)
    })
    @PreAuthorize("@ss.hasPermi('os:plant/record:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	
    	List<SysUser> userList = userService.selectUserIdList(loginUser.getUser().getDeptId());
    	
		List<AttributeGroupVo> attributeGroupVoList = null;
		if(userList != null && userList.size() > 0)
		{
			List<Long> userIds = new ArrayList<Long>();
			for(SysUser user : userList)
			{
				userIds.add(user.getUserId());
			}
			attributeGroupVoList = iAttributeGroupService.queryAttributeGroupVoBySpeciesId(loginUser.getUser().getSpeciesId(), userIds);
		}
		
    	AttrExcelUtil util = new AttrExcelUtil(attributeGroupVoList);
    	Map<Integer, PlantAttributeList> plantRecordMap = util.importExcel(file.getInputStream());
        if(plantRecordMap == null || plantRecordMap.size() == 0)
        {
        	return AjaxResult.error("????????????????????????excel????????????????????????????????????");
        }
        
        String message = "";
        for (Map.Entry<Integer, PlantAttributeList> entry : plantRecordMap.entrySet()) 
        {
        	PlantAttributeList obj = entry.getValue();
        	
        	LambdaQueryWrapper<Plant> lqw = new LambdaQueryWrapper<Plant>();
        	lqw.eq(Plant::getDelFlag, 0);//?????????
        	lqw.eq(Plant::getCode, obj.getCodeTwo()).or().eq(Plant::getCodeTwo, obj.getCodeTwo()).or().eq(Plant::getCodeOne, obj.getCodeTwo());
        	List<Plant> plantList = iPlantService.list(lqw);
        	if(plantList == null || plantList.size() == 0)
        	{
        		message += "???" + (entry.getKey() + 1) + "??????code[" + obj.getCodeTwo() + "]????????????????????????????????????????????? <br/>";
        	}
        	else
        	{
        		PlantRecordVo plantRecordVo = new PlantRecordVo();
        		
        		Plant plant = plantList.get(0);
        		plantRecordVo.setPlantId(plant.getId());
        		plantRecordVo.setHybridId(plant.getHybridId());
        		plantRecordVo.setCreateById(loginUser.getUser().getUserId());
            	plantRecordVo.setCreateByName(loginUser.getUser().getUserName());
            	plantRecordVo.setEnterMethod("2");//????????????
        		
        		List<PlantRecordLog> plantRecodeLogList = new ArrayList<>();
        		for(Attribute attribute : obj.getAttributeList())
        		{
        			PlantRecordLog log = new PlantRecordLog();
            		log.setAttributeId(attribute.getId());
            		log.setAttributeName(attribute.getFieldName());
            		log.setAttributeValue(attribute.getValue());
            		
            		plantRecodeLogList.add(log);
        		}
        		
        		plantRecordVo.setLogList(plantRecodeLogList);
        		
        		try 
        		{
        			iPlantRecordService.save(plantRecordVo);
        			
        			message += "???" + (entry.getKey() + 1) + "??????code[" + obj.getCodeTwo() + "]????????????????????????<br/>";
        		}
        		catch (Exception e) {
        			message += "???" + (entry.getKey() + 1) + "??????code[" + obj.getCodeTwo() + "]????????????????????????<br/>";
				}
        	}
        }
       
        return AjaxResult.success(message);
    }

    @ApiOperation("????????????????????????????????????")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	
    	List<SysUser> userList = userService.selectUserIdList(loginUser.getUser().getDeptId());
    	
		List<AttributeGroupVo> attributeGroupVoList = null;
		if(userList != null && userList.size() > 0)
		{
			List<Long> userIds = new ArrayList<Long>();
			for(SysUser user : userList)
			{
				userIds.add(user.getUserId());
			}
			attributeGroupVoList = iAttributeGroupService.queryAttributeGroupVoBySpeciesId(loginUser.getUser().getSpeciesId(), userIds);
		}
		
    	AttrExcelUtil util = new AttrExcelUtil(attributeGroupVoList);
    	
        return util.importTemplateExcel("??????????????????");
    }

}
