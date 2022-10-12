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
import com.liuxn.yuzhong.project.os.domain.Seedling;
import com.liuxn.yuzhong.project.os.domain.SeedlingImage;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecord;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordListVo;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordLog;
import com.liuxn.yuzhong.project.os.domain.SeedlingRecordVo;
import com.liuxn.yuzhong.project.os.service.ISeedlingImageService;
import com.liuxn.yuzhong.project.os.service.ISeedlingRecordLogService;
import com.liuxn.yuzhong.project.os.service.ISeedlingRecordService;
import com.liuxn.yuzhong.project.os.service.ISeedlingService;
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
@Api(tags = "移动端-种质植物记录管理")
@RestController
@RequestMapping("/app/v10/seedling/record" )
public class ApiSeedlingRecordController extends APPBaseController {

	@Autowired
    private ISeedlingRecordService iSeedlingRecordService;

	@Autowired
    private ISeedlingRecordLogService iSeedlingRecordLogService;
	
	@Autowired
    private ISeedlingImageService iSeedlingImageService;
	
	@Autowired
	private ServerConfig serverConfig;
	
    /**
     * 查询植被记录列表
     */
	@ApiOperation("查询个人填报的植物记录列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "germplasmId", value = "种质信息id", dataType = "long", dataTypeClass = Long.class),
		@ApiImplicitParam(name = "SeedlingId", value = "植物id", dataType = "long", dataTypeClass = Long.class),
		@ApiImplicitParam(name = "delayDay", value = "延迟天数", dataType = "int", dataTypeClass = Integer.class),
	})
	@LoginRequired
    @GetMapping("/list")
    public AjaxResult list(Long germplasmId, Long SeedlingId, Integer delayDay)
    {
        startPage();
    
        Date startTime = null;
        Date endTime = null;
        if (delayDay != null){
            startTime = DateUtils.getDelayDate(delayDay);
            endTime = DateUtils.getNowDate();
        }

        List<SeedlingRecordListVo> list = iSeedlingRecordService.queryListByUserId(getLoginUser().getUserId(), germplasmId, SeedlingId, startTime, endTime);
        
        AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
	
	/**
     * 查询植被记录列表
     */
	@ApiOperation("查询植物记录的历史信息列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "seedlingId", value = "植物id", dataType = "long"),
		@ApiImplicitParam(name = "type", value = "类型：1代表初选，2代表高接，3代表区试", dataType = "int")
	})
	@LoginRequired
    @GetMapping("/historyList")
    public AjaxResult historyList(Long seedlingId, Integer type)
    {
		startPage();
		List<SeedlingRecordListVo> list = iSeedlingRecordService.historyList(seedlingId, type);
        
		AjaxResult ajaxResult = AjaxResult.success(list);
        ajaxResult.put("total", new PageInfo(list).getTotal());
        return ajaxResult;
    }
	
	/**
     * 查询植被记录列表
     */
	@ApiOperation("查询植物记录的历史信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "SeedlingId", value = "植物id", dataType = "long")
	})
	@LoginRequired
    @GetMapping("/historyInfo")
    public AjaxResult historyInfo(Long SeedlingId)
    {
        LambdaQueryWrapper<SeedlingRecord> lqw = new LambdaQueryWrapper<SeedlingRecord>();
        lqw.eq(SeedlingRecord::getSeedlingId, SeedlingId);
        lqw.orderByDesc(SeedlingRecord::getId);
    
        List<SeedlingRecord> list = iSeedlingRecordService.list(lqw);
        
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
	@ApiParam(name = "SeedlingRecordVo", value = "植物记录信息", required = true)
	@LoginRequired
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SeedlingRecordVo seedlingRecordVo) {
		
		SysUser user = getLoginUser();
		
		seedlingRecordVo.setCreateById(user.getUserId());
		seedlingRecordVo.setCreateByName(user.getUserName());
		seedlingRecordVo.setEnterMethod("1");//手动填报
		
		LambdaQueryWrapper<SeedlingRecord> lqw = new LambdaQueryWrapper<SeedlingRecord>();
        lqw.eq(SeedlingRecord::getSeedlingId, seedlingRecordVo.getSeedlingId());
        lqw.eq(SeedlingRecord::getCreateById, seedlingRecordVo.getCreateById());
        lqw.ge(SeedlingRecord::getCreateTime, DateUtils.addMinutes(DateUtils.getNowDate(), -10));
        lqw.orderByDesc(SeedlingRecord::getId);
        List<SeedlingRecord> list = iSeedlingRecordService.list(lqw);
        if(list == null || list.size() == 0)
        {
        	return toAjax(iSeedlingRecordService.save(seedlingRecordVo) ? 1 : 0);
        }
        else
        {
        	seedlingRecordVo.setId(list.get(0).getId());
        	return toAjax(iSeedlingRecordService.update(seedlingRecordVo) ? 1 : 0);
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
		
		SeedlingRecord SeedlingRecord = iSeedlingRecordService.getById(id);
		
		if(SeedlingRecord == null)
		{
			return AjaxResult.error("取植物记录信息不存在");
		}
		
		SeedlingRecordVo SeedlingRecordVo = new SeedlingRecordVo();
		SeedlingRecordVo.setId(SeedlingRecord.getId());
		SeedlingRecordVo.setSeedlingId(SeedlingRecord.getSeedlingId());
		SeedlingRecordVo.setGermplasmId(SeedlingRecord.getGermplasmId());
		SeedlingRecordVo.setEnterMethod(SeedlingRecord.getEnterMethod());
		SeedlingRecordVo.setCreateYear(SeedlingRecord.getCreateYear());
		SeedlingRecordVo.setCreateTime(SeedlingRecord.getCreateTime());
		SeedlingRecordVo.setAttributeValues(SeedlingRecord.getAttributeValues());
		SeedlingRecordVo.setCreateById(SeedlingRecord.getCreateById());
		SeedlingRecordVo.setCreateByName(SeedlingRecord.getCreateByName());
		
		LambdaQueryWrapper<SeedlingRecordLog> logLQW = new LambdaQueryWrapper<SeedlingRecordLog>();
		logLQW.eq(SeedlingRecordLog::getRecordId, id);
		SeedlingRecordVo.setLogList(iSeedlingRecordLogService.list(logLQW));
		
		LambdaQueryWrapper<SeedlingImage> imgLQW = new LambdaQueryWrapper<SeedlingImage>();
		imgLQW.eq(SeedlingImage::getRecordId, id);
		List<SeedlingImage> imgList = iSeedlingImageService.list(imgLQW);
		if(imgList != null && imgList.size() > 0)
		{
			for(SeedlingImage img : imgList)
			{
				img.setPath(serverConfig.getUrl() + img.getPath());
			}
		}
		SeedlingRecordVo.setImgList(iSeedlingImageService.list(imgLQW));
		
        return AjaxResult.success(SeedlingRecordVo);
    }
    
    /**
     * 删除植被记录
     */
	@ApiOperation("植物记录删除")
	@ApiParam(name = "id", value = "植物记录id", required = true)
	@LoginRequired
    @GetMapping("/del" )
    public AjaxResult remove(Long id) {
        return toAjax(iSeedlingRecordService.removeById(id) ? 1 : 0);
    }
}
