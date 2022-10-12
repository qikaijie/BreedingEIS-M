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
import net.cnbec.catering.network.api.NetWorkManager;
import net.cnbec.catering.network.requestBean.BaseRequestBean;
import net.cnbec.catering.network.requestBean.CollectAddRequest;
import net.cnbec.catering.network.requestBean.CollectAddRequest1;
import net.cnbec.catering.network.requestBean.FeedBackAddRequestBean;
import net.cnbec.catering.network.requestBean.LoginRequest;
import net.cnbec.catering.network.requestBean.PlantRecordAddRequestBean;
import net.cnbec.catering.network.requestBean.SeedlingRecordAddRequestBean;
import net.cnbec.catering.util.JsonUtil;
import net.cnbec.catering.util.UserInfoUtil;

//import com.lwy.smartupdate.data.VersionBean;
import net.cnbec.catering.bean.VersionBean;
import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Describe: 业务层实现
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class DataProviderImpl implements DataProvider {

    @Override
    public Flowable<BaseBean<UserInfo>> login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.userName  = username;
        loginRequest.password = password;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(loginRequest));
        return NetWorkManager.getInstance().getNetwork().login(requestBody);
    }

    @Override
    public Flowable<BaseBean<UserDetailsBean>> userInfo() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(new BaseRequestBean()));
        return NetWorkManager.getInstance().getNetwork().userInfo(getAuthorization(),requestBody);
    }

    @Override
    public Flowable<BaseBean<Object>> logout() {
        return NetWorkManager.getInstance().getNetwork().logout(getAuthorization());
    }

    @Override
    public Flowable<BaseBean<String>> uploadFile(File file, String fileType, String keyStr,Integer valueId,Integer businessType) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(fileType),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        return NetWorkManager.getInstance().getNetwork().uploadFile(getAuthorization(),body,keyStr,valueId);
    }

    @Override
    public Flowable<BaseBean<List<GroupListBean>>> groupList(Integer speciesId) {
        return NetWorkManager.getInstance().getNetwork().groupList(getAuthorization(),speciesId);
    }

    @Override
    public Flowable<BaseBean<List<PlantRecordBean>>> plantRecordList(Integer delayDay,Integer hybridId, Integer plantId) {
        return NetWorkManager.getInstance().getNetwork().plantRecordList(getAuthorization(),delayDay,hybridId,plantId);
    }

    @Override
    public Flowable<BaseBean<List<PlantRecordBean>>> plantRecordList(Integer delayDay,Integer hybridId, Integer plantId, Integer pageNum, Integer pageSize) {
        return NetWorkManager.getInstance().getNetwork().plantRecordList(getAuthorization(),delayDay,hybridId,plantId,pageNum,pageSize);
    }

    @Override
    public Flowable<BaseBean<PlantInfoBean>> plantInfo(String code) {
        return NetWorkManager.getInstance().getNetwork().plantInfo(getAuthorization(),code);
    }



    /**
     * 种质信息查询
     */
    @Override
    public Flowable<BaseBean<SeedlingInfoBean>> seedlingInfo(String code){
        return NetWorkManager.getInstance().getNetwork().seedlingInfo(getAuthorization(),code);
    }

    /**
     * 查询个人填报的种质记录列表
     */
    @Override
    public Flowable<BaseBean<List<SeedlingRecordBean>>> seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId){
        return NetWorkManager.getInstance().getNetwork().seedlingRecordList(getAuthorization(),delayDay,germplasmId,seedlingId);
    }

    @Override
    public Flowable<BaseBean<List<SeedlingRecordBean>>> seedlingRecordList(Integer delayDay,Integer germplasmId, Integer seedlingId, Integer pageNum, Integer pageSize){
        return NetWorkManager.getInstance().getNetwork().seedlingRecordList(getAuthorization(),delayDay,germplasmId,seedlingId,pageNum,pageSize);
    }



//    @Override
//    public Flowable<BaseBean<CollectAddBean>> collectAdd(CollectAddRequestBean collectAddRequestBean) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(collectAddRequestBean));
//        return NetWorkManager.getInstance().getNetwork().collectAdd(getAuthorization(),requestBody);
//    }
//
//    @Override
//    public Flowable<BaseBean<CollectDelBean>> collectDel(Integer id) {
//        return NetWorkManager.getInstance().getNetwork().collectDel(getAuthorization(),id);
//    }

    @Override
    public Flowable<BaseBean<PlantRecordAddBean>> plantRecordAdd(PlantRecordAddRequestBean plantRecordAddRequestBean) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(plantRecordAddRequestBean));
        return NetWorkManager.getInstance().getNetwork().plantRecordAdd(getAuthorization(),requestBody);
    }

    @Override
    public Flowable<BaseBean<SeedlingRecordAddBean>> seedlingRecordAdd(SeedlingRecordAddRequestBean seedlingRecordAddRequestBean) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(seedlingRecordAddRequestBean));
        return NetWorkManager.getInstance().getNetwork().seedlingRecordAdd(getAuthorization(),requestBody);
    }

    @Override
    public Flowable<BaseBean<PlantRecordDelBean>> plantRecordDel(Integer id) {
        return NetWorkManager.getInstance().getNetwork().plantRecordDel(getAuthorization(),id);
    }

    @Override
    public Flowable<BaseBean<PlantRecordInfoBean>> plantRecordInfo(Integer id) {
        return NetWorkManager.getInstance().getNetwork().plantRecordInfo(getAuthorization(),id);
    }

    @Override
    public Flowable<BaseBean<PlantRecordHistoryInfoBean1>> plantRecordHistoryInfo(Integer plantId) {
        return NetWorkManager.getInstance().getNetwork().plantRecordHistoryInfo(getAuthorization(),plantId);
    }



    /**
     * 通过年份查询种质组合库列表
     */
    @Override
    public Flowable<BaseBean<List<GermplasmListByYearBean>>> germplasmListByYear(String introductionYear) {
        return NetWorkManager.getInstance().getNetwork().germplasmListByYear(getAuthorization(),introductionYear);
    }

    /**
     * 通过年份查询杂交组合库列表
     */
    @Override
    public Flowable<BaseBean<List<HybridListByYearBean>>> hybridListByYear(String hybridYear) {
        return NetWorkManager.getInstance().getNetwork().hybridListByYear(getAuthorization(),hybridYear);
    }

    /**
     * 查询物种信息列表
     */
    @Override
    public Flowable<BaseBean<List<SpeciesListBean>>> speciesList(){
        return NetWorkManager.getInstance().getNetwork().speciesList(getAuthorization());
    }

    /**
     * 物种信息管理（属性挂靠在物种下）
     */
    @Override
    public Flowable<BaseBean<SpeciesInfoBean>> speciesDetails(Integer speciesId){
        return NetWorkManager.getInstance().getNetwork().speciesDetails(getAuthorization(),speciesId);
    }

    /**
     * 用户收藏植物列表
     */
    @Override
    public Flowable<BaseBean<List<CollectListBean>>> plantCollectList(String plantCode,String order,String prop){
        return NetWorkManager.getInstance().getNetwork().plantCollectList(getAuthorization(),plantCode,order,prop);
    }

    /**
     * 收藏
     *
     * level
     */
    @Override
    public Flowable<BaseBean<Object>> plantCollectAdd(Integer level,Integer plantId){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(new CollectAddRequest(level,plantId)));
        return NetWorkManager.getInstance().getNetwork().plantCollectAdd(getAuthorization(),requestBody,level,plantId);
    }

    /**
     * 取消收藏
     */
    @Override
    public Flowable<BaseBean<Object>> plantCollectDel(Integer collectId){
        return NetWorkManager.getInstance().getNetwork().plantCollectDel(getAuthorization(),collectId);
    }

    /**
     * 收藏
     *
     * level
     */
    @Override
    public Flowable<BaseBean<Object>> seedlingCollectAdd(Integer level,Integer seedlingId){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(new CollectAddRequest1(level,seedlingId)));
        return NetWorkManager.getInstance().getNetwork().seedlingCollectAdd(getAuthorization(),requestBody,level,seedlingId);
    }

    /**
     * 取消收藏
     */
    @Override
    public Flowable<BaseBean<Object>> seedlingCollectDel(Integer collectId){
        return NetWorkManager.getInstance().getNetwork().seedlingCollectDel(getAuthorization(),collectId);
    }

    /**
     * 收藏记录查询
     */
    @Override
    public Flowable<BaseBean<List<SeedlingCollectListBean>>> seedlingCollectList(String seedlingCode,String order,String prop) {
        return NetWorkManager.getInstance().getNetwork().seedlingCollectList(getAuthorization(),seedlingCode,order,prop);
    }

    @Override
    public Flowable<BaseBean<SeedlingRecordInfoBean>> seedlingRecordInfo(Integer id) {
        return NetWorkManager.getInstance().getNetwork().seedlingRecordInfo(getAuthorization(),id);
    }

    @Override
    public Flowable<BaseBean<Object>> seedlingRecordDel(Integer id) {
        return NetWorkManager.getInstance().getNetwork().seedlingRecordDel(getAuthorization(),id);
    }

    @Override
    public Flowable<BaseBean<List<SeedlingHistoryRecordBean>>> seedlingRecordHistoryList(Integer seedlingId, Integer type) {
        return NetWorkManager.getInstance().getNetwork().seedlingRecordHistoryList(getAuthorization(),seedlingId,type);
    }

    @Override
    public Flowable<BaseBean<List<PlantHistoryRecordBean>>> plantRecordHistoryList(Integer plantId, Integer type) {
        return NetWorkManager.getInstance().getNetwork().plantRecordHistoryList(getAuthorization(),plantId,type);
    }

    @Override
    public Flowable<BaseBean<List<GermplasmListByNameBean>>> germplasmListByName(String name) {
        return NetWorkManager.getInstance().getNetwork().germplasmListByName(getAuthorization(),name);
    }

    @Override
    public Flowable<BaseBean<List<PlantListByCodeBean>>> plantListByName(String code) {
        return NetWorkManager.getInstance().getNetwork().plantListByName(getAuthorization(),code);
    }

    @Override
    public Flowable<BaseBean<List<SeedlingListByCodeBean>>> seedlingListByName(String code) {
        return NetWorkManager.getInstance().getNetwork().seedlingListByName(getAuthorization(),code);
    }

    @Override
    public Flowable<BaseBean<VersionBean>> downloadAndroid() {
        return NetWorkManager.getInstance().getNetwork().downloadAndroid(getAuthorization());
    }

    @Override
    public Flowable<BaseBean<Object>> feedbackAdd(String username, String content) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(new FeedBackAddRequestBean(content, username)));
        return NetWorkManager.getInstance().getNetwork().feedbackAdd(getAuthorization(),requestBody);
    }

    @Override
    public Flowable<BaseBean<List<FeedbackBean>>> feedbackList() {
        return NetWorkManager.getInstance().getNetwork().feedbackList(getAuthorization());
    }

    public String getAuthorization(){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo != null){
            return userInfo.getToken();
        }
        return "";
    }
}
