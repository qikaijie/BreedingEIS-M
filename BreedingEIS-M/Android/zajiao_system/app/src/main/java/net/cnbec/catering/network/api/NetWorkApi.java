package net.cnbec.catering.network.api;

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
import net.cnbec.catering.bean.PlantRecordBean;
import net.cnbec.catering.bean.PlantRecordDelBean;
import net.cnbec.catering.bean.UserDetailsBean;
import net.cnbec.catering.bean.UserInfo;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
//import com.lwy.smartupdate.data.VersionBean;
import net.cnbec.catering.bean.VersionBean;

/**
 * @Describe: 网络请求API
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public interface NetWorkApi {

    /**
     * 登录
     * @param requestBody
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("v10/login")
    Flowable<BaseBean<UserInfo>> login(@Body RequestBody requestBody);


    /**
     * 登录用户信息
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("v10/user/info")
    Flowable<BaseBean<UserDetailsBean>> userInfo(@Header("token") String token, @Body RequestBody requestBody);


    /**
     * 退出登录
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/logout")
    Flowable<BaseBean<Object>> logout(@Header("token") String token);


    /**     v10/%@/upload/file/%@
     * 上传文件
     * @param
     * @return
     */
    @Multipart
    @POST("v10/{key}/upload/file/{value}")//@Header("token") String token,
    Flowable<BaseBean<String>> uploadFile(@Header("token") String token,@Part MultipartBody.Part file, @Path("key") String key,@Path("value") Integer value);
//    Flowable<BaseBean<String>> uploadFile(@Header("token") String token, @Part MultipartBody.Part file, @Path("key") String key,@Path("value") Integer value);

    /**
     * 属性分组列表查询
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/attribute/group/list")
    Flowable<BaseBean<List<GroupListBean>>> groupList(@Header("token") String token, @Query("speciesId") Integer speciesId);


    /**
     *
     * 植物记录历史查询
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/record/list")
    Flowable<BaseBean<List<PlantRecordBean>>> plantRecordList(@Header("token") String token,
                                                        @Query("delayDay") Integer delayDay,
                                                        @Query("hybridId") Integer hybridId,
                                                        @Query("plantId") Integer plantId);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/record/list")
    Flowable<BaseBean<List<PlantRecordBean>>> plantRecordList(@Header("token") String token,
                                                              @Query("delayDay") Integer delayDay,
                                                              @Query("hybridId") Integer hybridId,
                                                              @Query("plantId") Integer plantId,
                                                              @Query("pageNum") Integer pageNum,
                                                              @Query("pageSize") Integer pageSize);

    /**
     * 植物信息查询
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/info")
    Flowable<BaseBean<PlantInfoBean>> plantInfo(@Header("token") String token,
                                                @Query("code") String code);

//    /**
//     * 收藏
//     */
//    @Headers({"Content-Type:application/json;charset=UTF-8"})
//    @POST("v10/collect/add")
//    Flowable<BaseBean<CollectAddBean>> collectAdd(@Header("token") String token, @Body RequestBody requestBody);
//
//    /**
//     * 取消收藏
//     */
//    @Headers({"Content-Type:application/json;charset=UTF-8"})
//    @GET("v10/collect/del")
//    Flowable<BaseBean<CollectDelBean>> collectDel(@Header("token") String token, @Query("id") Integer id);

    /**
     * 植物记录添加
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("v10/plant/record/add")
    Flowable<BaseBean<PlantRecordAddBean>> plantRecordAdd(@Header("token") String token, @Body RequestBody requestBody);

    /**
     * 植物记录添加
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("v10/seedling/record/add")
    Flowable<BaseBean<SeedlingRecordAddBean>> seedlingRecordAdd(@Header("token") String token, @Body RequestBody requestBody);

    /**
     *  植物记录删除
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/record/del")
    Flowable<BaseBean<PlantRecordDelBean>> plantRecordDel(@Header("token") String token, @Query("id") Integer id);

    /**
     * 获取植物记录详情
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/record/info")
    Flowable<BaseBean<PlantRecordInfoBean>> plantRecordInfo(@Header("token") String token, @Query("id") Integer id);

    /**
     * 查询植物记录的历史信息
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/record/historyInfo")
    Flowable<BaseBean<PlantRecordHistoryInfoBean1>> plantRecordHistoryInfo(@Header("token") String token, @Query("plantId") Integer plantId);

    /**
     * 种质信息查询
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/info")
    Flowable<BaseBean<SeedlingInfoBean>> seedlingInfo(@Header("token") String token, @Query("code") String code);

    /**
     * 查询个人填报的种质记录列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/record/list")
    Flowable<BaseBean<List<SeedlingRecordBean>>> seedlingRecordList(@Header("token") String token,
                                                  @Query("delayDay") Integer delayDay,
                                                  @Query("germplasmId") Integer germplasmId,
                                                  @Query("seedlingId") Integer seedlingId,
                                                  @Query("pageNum") Integer pageNum,
                                                  @Query("pageSize") Integer pageSize);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/record/list")
    Flowable<BaseBean<List<SeedlingRecordBean>>> seedlingRecordList(@Header("token") String token,
                                                  @Query("delayDay") Integer delayDay,
                                                  @Query("germplasmId") Integer germplasmId,
                                                  @Query("seedlingId") Integer seedlingId);

    /**
     * 获取种质记录详情
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/record/info")
    Flowable<BaseBean<SeedlingRecordInfoBean>> seedlingRecordInfo(@Header("token") String token, @Query("id") Integer id);

    /**
     * 删除种质记录
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/record/del")
    Flowable<BaseBean<Object>> seedlingRecordDel(@Header("token") String token, @Query("id") Integer id);

    /**
     * 查询种质记录的历史信息
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/record/historyInfo")
    Flowable<BaseBean<Object>> seedlingRecordHistoryInfo(@Header("token") String token);

    /**
     * 查询种质记录的历史信息列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/record/historyList")
    Flowable<BaseBean<List<SeedlingHistoryRecordBean>>> seedlingRecordHistoryList(@Header("token") String token, @Query("seedlingId")Integer seedlingId, @Query("type") Integer type);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/record/historyList")
    Flowable<BaseBean<List<PlantHistoryRecordBean>>> plantRecordHistoryList(@Header("token") String token, @Query("plantId")Integer plantId, @Query("type") Integer type);

    /**
     * 通过年份查询种质组合库列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/germplasm/listByYear")
    Flowable<BaseBean<List<GermplasmListByYearBean>>> germplasmListByYear(@Header("token") String token, @Query("introductionYear") String introductionYear);

    /**
     * 通过年份查询杂交组合库列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/hybrid/listByYear")
    Flowable<BaseBean<List<HybridListByYearBean>>> hybridListByYear(@Header("token") String token, @Query("hybridYear") String hybridYear);

    /**
     * 查询物种信息列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/species/list")
    Flowable<BaseBean<List<SpeciesListBean>>> speciesList(@Header("token") String token);

    /**
     * 物种信息管理（属性挂靠在物种下）
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/species/{speciesId}")
    Flowable<BaseBean<SpeciesInfoBean>> speciesDetails(@Header("token") String token, @Path("speciesId") Integer speciesId);

    /**
     * 用户收藏植物列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plantCollect/list")
    Flowable<BaseBean<List<CollectListBean>>> plantCollectList(@Header("token") String token,@Query("plantCode") String plantCode,@Query("order")String order,@Query("prop")String prop);

    /**
     * 收藏
     *
     * level
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("v10/plantCollect/add")
    Flowable<BaseBean<Object>> plantCollectAdd(@Header("token") String token, @Body RequestBody requestBody,@Query("level") Integer level,@Query("plantId") Integer plantId);

    /**
     * 取消收藏
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plantCollect/del")
    Flowable<BaseBean<Object>> plantCollectDel(@Header("token") String token,@Query("id") Integer id);

    /**
     * 收藏
     *
     * level
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("v10/seedlingCollect/add")
    Flowable<BaseBean<Object>> seedlingCollectAdd(@Header("token") String token, @Body RequestBody requestBody,@Query("level") Integer level,@Query("seedlingId") Integer seedlingId);

    /**
     * 取消收藏
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedlingCollect/del")
    Flowable<BaseBean<Object>> seedlingCollectDel(@Header("token") String token,@Query("id") Integer id);

    /**
     * 收藏记录查询
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedlingCollect/list")
    Flowable<BaseBean<List<SeedlingCollectListBean>>> seedlingCollectList(@Header("token") String token,@Query("seedlingCode")String seedlingCode,@Query("order")String order,@Query("prop")String prop);

    /**
     * 通过名称查询种质信息列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/germplasm/listByName")
    Flowable<BaseBean<List<GermplasmListByNameBean>>> germplasmListByName(@Header("token") String token, @Query("name") String name);

    /**
     * 通过名称查询植物列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/plant/listByCode")
    Flowable<BaseBean<List<PlantListByCodeBean>>> plantListByName(@Header("token") String token, @Query("code") String code);

    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/seedling/listByCode")
    Flowable<BaseBean<List<SeedlingListByCodeBean>>> seedlingListByName(@Header("token") String token, @Query("code") String code);

    /**
     * v10/download/android
     * 查询最新的android版本
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/download/android")
    Flowable<BaseBean<VersionBean>> downloadAndroid(@Header("token") String token);

    /**
     /app/v10/feedback/add
     用户意见反馈
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("v10/feedback/add")
    Flowable<BaseBean<Object>> feedbackAdd(@Header("token") String token, @Body RequestBody requestBody);


    /**
     /app/v10/feedback/list
     查询用户意见反馈列表
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @GET("v10/feedback/list")
    Flowable<BaseBean<List<FeedbackBean>>> feedbackList(@Header("token") String token);

}
