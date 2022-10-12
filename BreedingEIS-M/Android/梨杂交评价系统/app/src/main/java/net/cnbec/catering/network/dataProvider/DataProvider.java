package net.cnbec.catering.network.dataProvider;

import net.cnbec.catering.bean.BaseBean;
import net.cnbec.catering.bean.CollectListBean;
import net.cnbec.catering.bean.FeedbackBean;
import net.cnbec.catering.bean.GermplasmListByNameBean;
import net.cnbec.catering.bean.GermplasmListByYearBean;
import net.cnbec.catering.bean.GroupListBean;
import net.cnbec.catering.bean.HybridListByYearBean;
import net.cnbec.catering.bean.PlantHistoryRecordBean;
import net.cnbec.catering.bean.PlantInfoBean;
import net.cnbec.catering.bean.PlantListByCodeBean;
import net.cnbec.catering.bean.PlantRecordAddBean;
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.PlantRecordDelBean;
import net.cnbec.catering.bean.PlantRecordHistoryInfoBean1;
import net.cnbec.catering.bean.PlantRecordInfoBean;
import net.cnbec.catering.bean.SeedlingCollectListBean;
import net.cnbec.catering.bean.SeedlingHistoryRecordBean;
import net.cnbec.catering.bean.SeedlingInfoBean;
import net.cnbec.catering.bean.SeedlingListByCodeBean;
import net.cnbec.catering.bean.SeedlingRecordAddBean;
import net.cnbec.catering.bean.SeedlingRecordBean;
import net.cnbec.catering.bean.SeedlingRecordInfoBean;
import net.cnbec.catering.bean.SpeciesInfoBean;
import net.cnbec.catering.bean.SpeciesListBean;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.bean.UserInfo;
import net.cnbec.catering.network.requestBean.PlantRecordAddRequestBean;
import net.cnbec.catering.network.requestBean.SeedlingRecordAddRequestBean;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
//import com.lwy.smartupdate.data.VersionBean;
import net.cnbec.catering.bean.VersionBean;

/**
 * @Describe: 业务层接口
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public interface DataProvider {

    /**
     * 登录
     *
     * @param username   手机号码
     * @param password 密码
     * @return
     */
    Flowable<BaseBean<UserInfo>> login(String username, String password);

    Flowable<BaseBean<UserDetailsBean>> userInfo();

    /**
     * 退出登录
     */
    Flowable<BaseBean<Object>> logout();

    /**
     * 上传文件
     */
    Flowable<BaseBean<String>> uploadFile(File file,String fileType,String keyStr,Integer valueId,Integer businessType);



    /**
     * 属性分组列表查询
     */
    Flowable<BaseBean<List<GroupListBean>>> groupList(Integer speciesId);


    /**
     * 植物记录历史查询
     */
    Flowable<BaseBean<List<PlantRecordBean>>> plantRecordList(Integer delayDay,Integer hybridId,Integer plantId);
    Flowable<BaseBean<List<PlantRecordBean>>> plantRecordList(Integer delayDay,Integer hybridId,Integer plantId,Integer pageNum,Integer pageSize);

    /**
     * 植物信息查询
     */
    Flowable<BaseBean<PlantInfoBean>> plantInfo(String code);


//    /**
//     * 收藏
//     */
//    Flowable<BaseBean<CollectAddBean>> collectAdd(CollectAddRequestBean collectAddRequestBean);
//
//    /**
//     * 取消收藏
//     */
//    Flowable<BaseBean<CollectDelBean>> collectDel(Integer id);

    /**
     * 植物记录添加
     */
    Flowable<BaseBean<PlantRecordAddBean>> plantRecordAdd(PlantRecordAddRequestBean plantRecordAddRequestBean);
    Flowable<BaseBean<SeedlingRecordAddBean>> seedlingRecordAdd(SeedlingRecordAddRequestBean seedlingRecordAddRequestBean);

    /**
     * 植物记录删除
     */
    Flowable<BaseBean<PlantRecordDelBean>> plantRecordDel(Integer id);

    /**
     * 获取植物记录详情
     */
    Flowable<BaseBean<PlantRecordInfoBean>> plantRecordInfo(Integer id);

    /**
     * 查询植物记录的历史信息
     */
    Flowable<BaseBean<PlantRecordHistoryInfoBean1>> plantRecordHistoryInfo(Integer plantId);


    /**
     * 用户收藏植物列表
     */
    Flowable<BaseBean<List<CollectListBean>>> plantCollectList(String plantCode,String order,String prop);

    /**
     * 收藏
     *
     * level
     */
    Flowable<BaseBean<Object>> plantCollectAdd(Integer level,Integer plantId);

    /**
     * 取消收藏
     */
    Flowable<BaseBean<Object>> plantCollectDel(Integer collectId);

    /**
     * 收藏
     *
     * level
     */
    Flowable<BaseBean<Object>> seedlingCollectAdd(Integer level,Integer seedlingId);

    /**
     * 取消收藏
     */
    Flowable<BaseBean<Object>> seedlingCollectDel(Integer collectId);

    /**
     * 收藏记录查询
     */
    Flowable<BaseBean<List<SeedlingCollectListBean>>> seedlingCollectList(String seedlingCode,String order,String prop);

    /**
     * 通过年份查询种质组合库列表
     */
    Flowable<BaseBean<List<GermplasmListByYearBean>>> germplasmListByYear(String introductionYear);

    /**
     * 通过年份查询杂交组合库列表
     */
    Flowable<BaseBean<List<HybridListByYearBean>>> hybridListByYear(String hybridYear);

    /**
     * 种质信息查询
     */
    Flowable<BaseBean<SeedlingInfoBean>> seedlingInfo(String code);

    /**
     * 查询个人填报的种质记录列表
     */
    Flowable<BaseBean<List<SeedlingRecordBean>>> seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId);
    Flowable<BaseBean<List<SeedlingRecordBean>>> seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId, Integer pageNum, Integer pageSize);

    /**
     * 查询物种信息列表
     */
    Flowable<BaseBean<List<SpeciesListBean>>> speciesList();

    /**
     * 物种信息管理（属性挂靠在物种下）
     */
    Flowable<BaseBean<SpeciesInfoBean>> speciesDetails(Integer speciesId);


    /**
     * 获取种质记录详情
     */
    Flowable<BaseBean<SeedlingRecordInfoBean>> seedlingRecordInfo(Integer id);
    Flowable<BaseBean<Object>> seedlingRecordDel(Integer id);

    /**
     * 查询种质记录的历史信息列表
     */
    Flowable<BaseBean<List<SeedlingHistoryRecordBean>>> seedlingRecordHistoryList(Integer seedlingId, Integer type);

    Flowable<BaseBean<List<PlantHistoryRecordBean>>> plantRecordHistoryList(Integer plantId, Integer type);



    /**
     * 通过名称查询种质信息列表
     */
    Flowable<BaseBean<List<GermplasmListByNameBean>>> germplasmListByName(String name);
    /**
     * 通过名称查询植物列表
     */
    Flowable<BaseBean<List<PlantListByCodeBean>>> plantListByName(String code);

    Flowable<BaseBean<List<SeedlingListByCodeBean>>> seedlingListByName(String code);

    /**
     * v10/download/android
     * 查询最新的android版本
     */
    Flowable<BaseBean<VersionBean>> downloadAndroid();

    /**
     /app/v10/feedback/add
     用户意见反馈
     */
    Flowable<BaseBean<Object>> feedbackAdd(String username,String content);


    /**
     /app/v10/feedback/list
     查询用户意见反馈列表
     */
    Flowable<BaseBean<List<FeedbackBean>>> feedbackList();

}
