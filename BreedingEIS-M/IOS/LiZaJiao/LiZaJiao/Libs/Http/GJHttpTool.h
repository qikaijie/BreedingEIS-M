//
//  GJHttpTool.h
//  MerchantAssistant
//
//  Created by Apple on 5/5/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFURLRequestSerialization.h"

#define APP_FILE_HTTP @"http://47.100.201.80:8081/yuzhong"//文件前缀

//登录模块
#define APP_LOGIN_1 @"v10/login"//POST登录接口
#define APP_LOGOUT @"v10/logout"//POST退出登录接口
#define APP_LOGIN_2 @"v10/user/info"//POST登录用户信息
#define APP_UPLOAD_FILE @"v10/%@/upload/file/%@"//POST上传文件
//#define APP_UPLOAD_FILE @"v10/upload/file/%@"//POST上传文件
#define APP_RECORD_LIST @"v10/plant/record/list"//GET杂交记录历史查询
#define APP_RECORD_INFO @"v10/plant/record/info"//GET获取杂交记录详情
#define APP_RECORD_DELETE @"v10/plant/record/del"//GET删除杂交记录
#define APP_ATTRIBUTE_GROUP_LIST @"v10/attribute/group/list"//属性分组列表查询
#define APP_SPECIES_LIST @"v10/species/list"//查询物种信息列表
#define APP_SPECIES @"v10/species/%d"//物种信息管理（属性挂靠在物种下）
#define APP_PLANT_INFO @"v10/plant/info"//杂交信息查询
#define APP_PLANT_COLLECT_ADD @"v10/plantCollect/add"//POST收藏
#define APP_PLANT_COLLECT_DEL @"v10/plantCollect/del"//GET取消收藏
#define APP_COLLECT_LIST @"v10/plantCollect/list"//GET收藏记录查询
#define APP_PLANT_RECORD @"v10/plant/record/list"//GET杂交记录历史查询
#define APP_PLANT_RECORD_HISTORY_INFO @"v10/plant/record/historyInfo"//GET查询杂交记录的历史信息
#define APP_PLANT_RECORD_HISTORY_LIST_INFO @"v10/plant/record/historyList"//GET查询植物记录的历史信息列表
#define APP_PLANT_RECORD_ADD @"v10/plant/record/add"//POST杂交记录添加
#define APP_HYBRID_LIST_BY_YEAR @"v10/hybrid/listByYear"//GET通过年份查询杂交组合库列表


#define APP_SEEDLING_INFO @"v10/seedling/info"//种质信息查询
#define APP_RECORD_LIST_1 @"v10/seedling/record/list"//GET种质记录历史查询
#define APP_RECORD_INFO_1 @"v10/seedling/record/info"//GET获取种质记录详情
#define APP_RECORD_DELETE_1 @"v10/seedling/record/del"//GET删除种质记录
#define APP_SEEDLING_COLLECT_ADD @"v10/seedlingCollect/add"//POST收藏
#define APP_SEEDLING_COLLECT_DEL @"v10/seedlingCollect/del"//GET取消收藏
#define APP_SEEDLING_COLLECT_LIST @"v10/seedlingCollect/list"//GET收藏记录查询
#define APP_PLANT_RECORD_1 @"v10/seedling/record/list"//GET查询个人填报的种质记录列表
#define APP_SEEDLING_RECORD_HISTORY_INFO @"v10/seedling/record/historyInfo"//GET查询种质记录的历史信息
#define APP_SEEDLING_RECORD_HISTORY_LIST_INFO @"v10/seedling/record/historyList"//GET查询种质记录的历史信息列表
#define APP_SEEDLING_RECORD_ADD @"v10/seedling/record/add"//POST种质记录添加
#define APP_GERMPLASM_LIST_BY_YEAR @"v10/germplasm/listByYear"//GET通过年份查询杂交种质组合库列表
#define APP_GERMPLASM_LIST_BY_NAME @"v10/germplasm/listByName"
#define APP_PLANT_LIST_BY_CODE @"v10/plant/listByCode"

#define APP_FEEDBACK_LIST @"v10/feedback/list"//GET查询用户意见反馈列表
#define APP_FEEDBACK_ADD @"v10/feedback/add"//POST用户意见反馈





enum RSPCOD
{
    RSPCOD_SUCCESS = 200,//处理成功
    RSPCOD__ERROR_CHAT = 300,//会话失效
    RSPCOD_ERROR_PARAM = 400,//参数校验错误
    RSPCOD_ERROR_VERSION = 205//版本号不存在
};

@interface GJHttpTool : NSObject
/**
 *  发送get请求
 *
 *  @param URLString  url
 *  @param parameters 请求参数字典
 *  @param success    请求成功回调
 *  @param failure    请求失败回调
 */
+ (void)GET:(NSString *)URLString
 parameters:(NSDictionary *)parameters
headerParams:(NSDictionary *)headerParams
awaitingView:(UIView *)view
    success:(void (^)(id responseObject))success
    failure:(void (^)(NSError *error))failure;

/**
 *  发送POST请求
 *
 *  @param URLString  url
 *  @param parameters 请求参数字典
 *  @param success    请求成功回调
 *  @param failure    请求失败回调
 */

+ (void)POST:(NSString *)URLString
  parameters:(NSDictionary *)parameters
headerParams:(NSDictionary *)headerParams
awaitingView:(UIView *)view
     success:(void (^)(id responseObject))success
     failure:(void (^)(NSError *error))failure;

/**
 *  上传图片或视频
 *
 *  @param URLString  url
 *  @param parameters 请求字典
 *  @param block      上传数据
 *  @param success    上传成功回调
 *  @param failure    上传失败回调
 */
+ (void)POST:(NSString *)URLString
  parameters:(NSDictionary *)parameters
headerParams:(NSDictionary *)headerParams
awaitingView:(UIView *)view
constructingBodyWithBlock:(void(^)(id <AFMultipartFormData> formData))block
     success:(void (^)(id responseObject))success
     failure:(void (^)(NSError *error))failure;

@end











