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
import com.liuxn.yuzhong.project.os.domain.PlantAttributeList;
import com.liuxn.yuzhong.project.os.domain.Seedling;
import com.liuxn.yuzhong.project.os.domain.SeedlingCollect;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecord;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordListVo;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordLog;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordVo;
import com.liuxn.yuzhong.project.os.service.IAttributeGroupService;
import com.liuxn.yuzhong.project.os.service.ISeedlingCollectService;
import com.liuxn.yuzhong.project.os.service.ISeedlingRecordService;
import com.liuxn.yuzhong.project.os.service.ISeedlingService;
import com.liuxn.yuzhong.project.system.domain.SysUser;
import com.liuxn.yuzhong.project.system.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * ????????????Controller
 *
 * @author liuxn
 * @date 2021-05-05
 */
@Api(tags="?????????-????????????????????????")
@AllArgsConstructor
@RestController
@RequestMapping("/os/seedling/record" )
public class SeedlingRecordController extends BaseController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private IAttributeGroupService iAttributeGroupService;
	
	@Autowired
    private ISeedlingService iSeedlingService;
	
	@Autowired
    private ISeedlingRecordService iSeedlingRecordService;

	@Autowired
	private ISeedlingCollectService iSeedlingCollectService;
    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="germplasmId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="seedlingId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="startTime", value="??????????????????", dataType="date", dataTypeClass=Date.class),
    	@ApiImplicitParam(name="endTime", value="??????????????????", dataType="date", dataTypeClass=Date.class)
    })
    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long germplasmId, Long seedlingId, Date startTime, Date endTime)
    {
        startPage();
        
        SeedlingRecord seedlingRecord = new SeedlingRecord();
        seedlingRecord.setGermplasmId(germplasmId);
        seedlingRecord.setSeedlingId(seedlingId);
        seedlingRecord.setStartTime(startTime);
        seedlingRecord.setEndTime(endTime);
        List<SeedlingRecordListVo> list = iSeedlingRecordService.queryList(seedlingRecord);
        if(list != null && list.size() > 0)
        {
        	for(SeedlingRecordListVo record : list)
        	{
        		LambdaQueryWrapper<SeedlingCollect> lqw = new LambdaQueryWrapper<SeedlingCollect>();
                lqw.eq(SeedlingCollect::getSeedlingId, record.getSeedlingId());
                lqw.eq(SeedlingCollect::getUserId, record.getCreateById());
                
        		List<SeedlingCollect> collectList = iSeedlingCollectService.list(lqw);
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
    	@ApiImplicitParam(name="seedlingId", value="??????id", dataType="long", dataTypeClass=Long.class)
    })
    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:list')")
    @GetMapping("/listBySeedlingId")
    public TableDataInfo listBySeedlingId(Long seedlingId)
    {
        startPage();
        LambdaQueryWrapper<SeedlingRecord> lqw = new LambdaQueryWrapper<SeedlingRecord>();
        lqw.eq(SeedlingRecord::getSeedlingId ,seedlingId);
        lqw.orderByDesc(SeedlingRecord::getId);
        
        List<SeedlingRecord> list = iSeedlingRecordService.list(lqw);
        return getDataTable(list);
    }
    
    /**
     * ??????????????????
     */
    @ApiOperation("????????????????????????")
    @ApiImplicitParam(name="seedlingRecord", value="seedlingRecord??????", required=true, dataType="SeedlingRecord", dataTypeClass=SeedlingRecord.class)
    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:add')" )
    @Log(title = "????????????" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SeedlingRecord seedlingRecord) {
        return toAjax(iSeedlingRecordService.save(seedlingRecord) ? 1 : 0);
    }
//
//    /**
//     * ??????????????????
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:edit')" )
//    @Log(title = "????????????" , businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SeedlingRecord seedlingRecord) {
//        return toAjax(iSeedlingRecordService.updateById(seedlingRecord) ? 1 : 0);
//    }
//
//    /**
//     * ??????????????????
//     */
//    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:remove')" )
//    @Log(title = "????????????" , businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}" )
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(iSeedlingRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
//    }
    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="germplasmId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="seedlingId", value="??????id", dataType="long", dataTypeClass=Long.class),
    	@ApiImplicitParam(name="startTime", value="??????????????????", dataType="date", dataTypeClass=Date.class),
    	@ApiImplicitParam(name="endTime", value="??????????????????", dataType="date", dataTypeClass=Date.class)
    })
    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:export')")
    @GetMapping("/export")
    public AjaxResult export(Long germplasmId, Long seedlingId, Date startTime, Date endTime)
    {
    	SeedlingRecord seedlingRecord = new SeedlingRecord();
        seedlingRecord.setGermplasmId(germplasmId);
        seedlingRecord.setSeedlingId(seedlingId);
        seedlingRecord.setStartTime(startTime);
        seedlingRecord.setEndTime(endTime);
        List<SeedlingRecordListVo> list = iSeedlingRecordService.queryList(seedlingRecord);
        ExcelUtil<SeedlingRecordListVo> util = new ExcelUtil<SeedlingRecordListVo>(SeedlingRecordListVo.class);
        return util.exportExcel(list, "record" );
    }
    
    @ApiOperation("????????????????????????")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="file", value="file", dataType="MultipartFile", dataTypeClass=MultipartFile.class)
    })
    @PreAuthorize("@ss.hasPermi('os:seedlingRecord:import')")
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
        	
        	LambdaQueryWrapper<Seedling> lqw = new LambdaQueryWrapper<Seedling>();
        	lqw.eq(Seedling::getCode, obj.getCodeTwo()).or().eq(Seedling::getCodeTwo, obj.getCodeTwo()).or().eq(Seedling::getCodeOne, obj.getCodeTwo());
        	List<Seedling> seedlingList = iSeedlingService.list(lqw);
        	if(seedlingList == null || seedlingList.size() == 0)
        	{
        		message += "???" + (entry.getKey() + 1) + "??????code[" + obj.getCodeTwo() + "]????????????????????????????????????????????? <br/>";
        	}
        	else
        	{
        		SeedlingRecordVo seedlingRecordVo = new SeedlingRecordVo();
        		
        		Seedling seedling = seedlingList.get(0);
        		seedlingRecordVo.setSeedlingId(seedling.getId());
        		seedlingRecordVo.setGermplasmId(seedling.getGermplasmId());
        		seedlingRecordVo.setCreateById(loginUser.getUser().getUserId());
        		seedlingRecordVo.setCreateByName(loginUser.getUser().getUserName());
        		seedlingRecordVo.setEnterMethod("2");//????????????
        		
        		List<SeedlingRecordLog> seedlingRecordLogList = new ArrayList<>();
        		for(Attribute attribute : obj.getAttributeList())
        		{
        			SeedlingRecordLog log = new SeedlingRecordLog();
            		log.setAttributeId(attribute.getId());
            		log.setAttributeName(attribute.getFieldName());
            		log.setAttributeValue(attribute.getValue());
            		
            		seedlingRecordLogList.add(log);
        		}
        		
        		seedlingRecordVo.setLogList(seedlingRecordLogList);
        		
        		try 
        		{
        			iSeedlingRecordService.save(seedlingRecordVo);
        			
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
