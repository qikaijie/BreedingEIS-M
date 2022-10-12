//
//  GJUtils.h
//  storemanager
//
//  Created by Apple on 18/3/16.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GJUtils : NSObject

//文字排序
+ (NSDictionary *)userSorting:(NSMutableArray *)modelArr andChar:(NSString *)string;

//检查手机号格式是否正确
+ (BOOL)isValidMobile:(NSString *)mobile;

//检查邮箱格式是否正确
+ (BOOL)isValidateEmail:(NSString *)email;

//检查银行卡格式是否正确
+ (BOOL)isBankCard:(NSString *)cardNumber;

//检查身份证格式是否正确
+ (BOOL)judgeIdentityStringValid:(NSString *)identityString;

//NSArray、NSDictionary转换为json
+ (NSString *)objectToJson:(id)obj;

//json转NSArray、NSDictionary
+ (id)jsonToObject:(NSString *)json;

+ (int)stringToInt:(NSString *)str;

+ (float)stringToFloat:(NSString *)str;


+ (NSString *)orderStateForString:(int)orderState;
+ (NSString *)orderReturnStateForString:(int)orderState;

/*!
 @brief 修正浮点型精度丢失
 @param str 传入接口取到的数据
 @return 修正精度后的数据
 */
+(NSDecimalNumber *)reviseString:(NSString *)str;

@end
