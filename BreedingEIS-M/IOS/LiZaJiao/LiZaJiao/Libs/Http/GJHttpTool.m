//
//  GJHttpTool.m
//  MerchantAssistant
//
//  Created by Apple on 5/5/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import "GJHttpTool.h"
#import "NetworkTools.h"
@implementation GJHttpTool


+ (void)GET:(NSString *)URLString
 parameters:(NSDictionary *)parameters
headerParams:(NSDictionary *)headerParams
awaitingView:(UIView *)view
    success:(void (^)(id responseObject))success
    failure:(void (^)(NSError *error))failure
{
    if(view != nil){
        [MBProgressHUD showHUDAddedTo:view animated:YES];
    }
    
    if(headerParams != nil){
        for (NSString *key in headerParams) {
            NSLog(@"Add Header Key: %@ value: %@", key, headerParams[key]);
            [[NetworkTools sharedNetwrokTools].requestSerializer setValue:headerParams[key] forHTTPHeaderField:key];
        }
    }
    
    if(!strIsEmpty(kUserDefaults(USER_TOKEN))){
        [[NetworkTools sharedNetwrokTools].requestSerializer setValue:[NSString stringWithFormat:@"%@",kUserDefaults(USER_TOKEN)] forHTTPHeaderField:@"token"];
    }
    
    
    [[NetworkTools sharedNetwrokTools] GET:URLString parameters:parameters success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if(view != nil){
            [MBProgressHUD hideHUDForView:view animated:YES];
        }if (success) {
            NSError *error;
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:responseObject options:NSJSONWritingPrettyPrinted error:&error];
            if (jsonData) {
                NSString *responseJsonStr =[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
                NSLog(@"%@",responseJsonStr);
            }
            success(responseObject);
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",responseObject[@"msg"]]];
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if(view != nil){
            [MBProgressHUD hideHUDForView:view animated:YES];
        }
        if (failure) {
            failure(error);
        }
    }];
}

+ (void)POST:(NSString *)URLString
  parameters:(NSDictionary *)parameters
headerParams:(NSDictionary *)headerParams
awaitingView:(UIView *)view
     success:(void (^)(id responseObject))success
     failure:(void (^)(NSError *error))failure
{
    if(view != nil){
        [MBProgressHUD showHUDAddedTo:view animated:YES];
    }
    
    if(headerParams != nil){
        for (NSString *key in headerParams) {
            NSLog(@"Add Header Key: %@ value: %@", key, headerParams[key]);
            [[NetworkTools sharedNetwrokTools].requestSerializer setValue:headerParams[key] forHTTPHeaderField:key];
        }
    }
    
    if(!strIsEmpty(kUserDefaults(USER_TOKEN))){
        [[NetworkTools sharedNetwrokTools].requestSerializer setValue:[NSString stringWithFormat:@"%@",kUserDefaults(USER_TOKEN)] forHTTPHeaderField:@"token"];
    }
    
    [[NetworkTools sharedNetwrokTools] POST:URLString parameters:parameters success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if(view != nil){
            [MBProgressHUD hideHUDForView:view animated:YES];
        }
        
        if (success) {
            NSError *error;
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:responseObject options:NSJSONWritingPrettyPrinted error:&error];
            if (jsonData) {
                NSString *responseJsonStr =[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
                NSLog(@"%@",responseJsonStr);
            }
            success(responseObject);
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",responseObject[@"msg"]]];
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if(view != nil){
            [MBProgressHUD hideHUDForView:view animated:YES];
        }
        if (failure) {
            failure(error);
        }
    }];
}

+ (void)POST:(NSString *)URLString
  parameters:(NSDictionary *)parameters
headerParams:(NSDictionary *)headerParams
awaitingView:(UIView *)view
constructingBodyWithBlock:(void(^)(id <AFMultipartFormData> formData))block
     success:(void (^)(id responseObject))success
     failure:(void (^)(NSError *error))failure
{
    if(view != nil){
        [MBProgressHUD showHUDAddedTo:view animated:YES];
    }
    
    if(headerParams != nil){
        for (NSString *key in headerParams) {
            NSLog(@"Add Header Key: %@ value: %@", key, headerParams[key]);
            [[NetworkTools sharedNetwrokTools].requestSerializer setValue:headerParams[key] forHTTPHeaderField:key];
        }
    }
    
    if(!strIsEmpty(kUserDefaults(USER_TOKEN))){
        [[NetworkTools sharedNetwrokTools].requestSerializer setValue:[NSString stringWithFormat:@"%@",kUserDefaults(USER_TOKEN)] forHTTPHeaderField:@"token"];
    }
    
    
    NetworkTools *tool = [NetworkTools sharedNetwrokTools];
    tool.requestSerializer.timeoutInterval = 300.0f;  //  秒后 请求超时
    
    
    [tool POST:URLString parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        if (block) {
            block(formData);
        }
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if(view != nil){
            [MBProgressHUD hideHUDForView:view animated:YES];
        }
        if (success) {
            NSError *error;
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:responseObject options:NSJSONWritingPrettyPrinted error:&error];
            if (jsonData) {
                NSString *responseJsonStr =[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
                NSLog(@"%@",responseJsonStr);
            }
            success(responseObject);
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",responseObject[@"msg"]]];
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if(view != nil){
            [MBProgressHUD hideHUDForView:view animated:YES];
        }
        if (failure) {
            failure(error);
        }
    }];
}

@end
