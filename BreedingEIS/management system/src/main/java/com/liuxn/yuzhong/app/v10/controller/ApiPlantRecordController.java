package com.liuxn.yuzhong.app.v10.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.liuxn.yuzhong.app.annotation.LoginRequired;
import com.liuxn.yuzhong.app.base.APPBaseController;
import com.liuxn.yuzhong.common.utils.DateUtils;
import com.liuxn.yuzhong.framework.config.ServerConfig;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.PlantImage;
import com.liuxn.yuzhong.project.os.domain.PlantRecord;
import com.liuxn.yuzhong.project.os.domain.PlantRecordListVo;
import com.liuxn.yuzhong.project.os.domain.PlantRecordLog;
import com.liuxn.yuzhong.project.os.domain.PlantRecordVo;
import com.liuxn.yuzhong.project.os.service.IPlantImageService;
import com.liuxn.yuzhong.project.os.service.IPlantRecordLogService;
import com.liuxn.yuzhong.project.os.service.IPlantRecordService;
import com.liuxn.yuzhong.project.system.domain.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 植被记录Controller
 *
 * @author liuxn
 * @date 2020-06-20
 */
@Api(tags = "移动端-杂交植物记录管理")
@RestController
@RequestMapping("/app/v10/plant/record" )
public class ApiPlantRecordController extends APPBaseController {

	@Autowired
    private IPlantRecordService iPlantRecordService;

	@Autowired
    private IPlantRecordLogService iPlantRecordLogService;
	
	@Autowired
    private IPlantImageService iPlantImageService;
	
	@Autowired
	private ServerConfig serverConfig;
	
    /**
     * 查询植被记录列表
     */
	@ApiOperation("查询个人填报的植物记录列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "hybridId", value = "杂交id", dataType = "long", dataTypeClass = Long.class),
		@ApiImplicitParam(name = "plantId", value = "植物id", dataType = "long", dataTypeClass = Long.class),
		@ApiImplicitParam(name = "delayDay", value = "延迟天数", dataType = "int", dataTypeClass = Integer.class),
	})
	@LoginRequired
    @GetMapping("/list")
    public AjaxResult list(Long hybridId, Long plantId, Integer delayDay)
    {
        startPage();
    
        Date startTime = null;
        Date endTime = null;
        if (delayDay != null){
            startTime = DateUtils.getDelayDate(delayDay);
            endTime = DateUtils.getNowDate();
        }

        List<PlantRecordListVo> list = iPlantRecordService.queryListByUserId(getLoginUser().getUserId(), hybridId, plantId, startTime, endTime);
        
        AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
	
	/**
     * 查询植被记录列表
     */
	@ApiOperation("查询植物记录的历史信息列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "plantId", value = "植物id", dataType = "long"),
		@ApiImplicitParam(name = "type", value = "类型：1代表初选，2代表高接，3代表区试", dataType = "int")
	})
	@LoginRequired
    @GetMapping("/historyList")
    public AjaxResult historyList(Long plantId, Integer type)
    {		
		startPage();
		List<PlantRecordListVo> list = iPlantRecordService.historyList(plantId, type);
        
		AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
	
	/**
     * 查询植被记录列表
     */
	@ApiOperation("查询植物记录的历史信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "plantId", value = "植物id", dataType = "long")
	})
	@LoginRequired
    @GetMapping("/historyInfo")
    public AjaxResult historyInfo(Long plantId)
    {
        LambdaQueryWrapper<PlantRecord> lqw = new LambdaQueryWrapper<PlantRecord>();
        lqw.eq(PlantRecord::getPlantId, plantId);
        lqw.orderByDesc(PlantRecord::getId);
    
        List<PlantRecord> list = iPlantRecordService.list(lqw);
        
        if(list == null || list.size() == 0)
        {
        	return AjaxResult.error("没有历史记录信息");
        }
        //----------------待优化---------------
        return AjaxResult.success("查询成功", list.get(0));
    }

    /**
     * 新增植被记录
     */
	@ApiOperation("植物记录添加")
	@ApiParam(name = "plantRecordVo", value = "植物记录信息", required = true)
	@LoginRequired
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PlantRecordVo plantRecordVo) {
		
		SysUser user = getLoginUser();
		
		plantRecordVo.setCreateById(user.getUserId());
		plantRecordVo.setCreateByName(user.getUserName());
		plantRecordVo.setEnterMethod("1");//手动填报
		
		LambdaQueryWrapper<PlantRecord> lqw = new LambdaQueryWrapper<PlantRecord>();
        lqw.eq(PlantRecord::getPlantId, plantRecordVo.getPlantId());
        lqw.eq(PlantRecord::getCreateById, plantRecordVo.getCreateById());
        lqw.ge(PlantRecord::getCreateTime, DateUtils.addMinutes(DateUtils.getNowDate(), -10));
        lqw.orderByDesc(PlantRecord::getId);
        List<PlantRecord> list = iPlantRecordService.list(lqw);
        if(list == null || list.size() == 0)
        {
        	try 
        	{
        		iPlantRecordService.save(plantRecordVo);
        		
        		return toAjax(1);
        	}
        	catch (Exception e) {
        		return toAjax(0);
			}
        }
        else
        {
        	plantRecordVo.setId(list.get(0).getId());
        	return toAjax(iPlantRecordService.update(plantRecordVo) ? 1 : 0);
        }
		
        
    }

	/**
     * 获取植物记录详情
     */
	@ApiOperation("获取植物记录详情")
	@ApiImplicitParam(name = "id", value = "植物记录id", required = true)
	@LoginRequired
    @GetMapping("/info" )
    public AjaxResult info(Long id) {
		
		PlantRecord plantRecord = iPlantRecordService.getById(id);
		
		if(plantRecord == null)
		{
			return AjaxResult.error("取植物记录信息不存在");
		}
		
		PlantRecordVo plantRecordVo = new PlantRecordVo();
		plantRecordVo.setId(plantRecord.getId());
		plantRecordVo.setPlantId(plantRecord.getPlantId());
		plantRecordVo.setHybridId(plantRecord.getHybridId());
		plantRecordVo.setEnterMethod(plantRecord.getEnterMethod());
		plantRecordVo.setCreateYear(plantRecord.getCreateYear());
		plantRecordVo.setCreateTime(plantRecord.getCreateTime());
		plantRecordVo.setAttributeValues(plantRecord.getAttributeValues());
		plantRecordVo.setCreateById(plantRecord.getCreateById());
		plantRecordVo.setCreateByName(plantRecord.getCreateByName());
		
		LambdaQueryWrapper<PlantRecordLog> logLQW = new LambdaQueryWrapper<PlantRecordLog>();
		logLQW.eq(PlantRecordLog::getRecordId, id);
		plantRecordVo.setLogList(iPlantRecordLogService.list(logLQW));
		
		LambdaQueryWrapper<PlantImage> imgLQW = new LambdaQueryWrapper<PlantImage>();
		imgLQW.eq(PlantImage::getRecordId, id);
		List<PlantImage> imgList = iPlantImageService.list(imgLQW);
		if(imgList != null && imgList.size() > 0)
		{
			for(PlantImage img : imgList)
			{
				img.setPath(serverConfig.getUrl() + img.getPath());
			}
		}
		plantRecordVo.setImgList(iPlantImageService.list(imgLQW));
		
        return AjaxResult.success(plantRecordVo);
    }
    
    /**
     * 删除植被记录
     */
	@ApiOperation("植物记录删除")
	@ApiParam(name = "id", value = "植物记录id", required = true)
	@LoginRequired
    @GetMapping("/del" )
    public AjaxResult remove(Long id) {
        return toAjax(iPlantRecordService.removeById(id) ? 1 : 0);
    }
}
