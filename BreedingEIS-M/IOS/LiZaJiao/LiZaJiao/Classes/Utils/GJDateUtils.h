//
//  GJDateUtils.h
//  storemanager
//
//  Created by Apple on 18/3/16.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GJDateUtils : NSObject

// 获取当前日期
+(NSString *)getCurrentDate;
+(NSString *)getCurrentDate:(NSString *)formatter;

// 获取当日日期前后指定几天的日期
+(NSString *)getNDay:(NSInteger)n;

#pragma mark - 获取上周日期区间
+ (NSString *)getPWeek;
#pragma mark - 获取上月日期区间
+ (NSString *)getPMonth;
#pragma mark - IOS 获取指定月的第一天和最后一天
+ (NSString *)getMonthBeginAndEndWith:(NSString *)dateStr;
- (int)getDaysInMonth:(int)year month:(int)imonth;
#pragma mark - 获取去年日期区间
+ (NSString *)getPYear;

#pragma mark - 获取当前时间的 时间戳
+(NSInteger)getNowTimestamp;

#pragma mark - 将某个时间转化成 时间戳
+(NSInteger)timeSwitchTimestamp:(NSString *)formatTime andFormatter:(NSString *)format;

#pragma mark - 将某个时间戳转化成 时间
+(NSString *)timestampSwitchTime:(NSInteger)timestamp andFormatter:(NSString *)format;

@end
