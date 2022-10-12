//
//  NSArray+JSONString.h
//  storemanager
//
//  Created by Apple on 18/3/22.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSArray (JSONString)

/**
 *  转换成JSON串字符串（没有可读性）
 *
 *  @return JSON字符串
 */
- (NSString *)toJSONString;

/**
 *  转换成JSON串字符串（有可读性）
 *
 *  @return JSON字符串
 */
- (NSString *)toReadableJSONString;

/**
 *  转换成JSON数据
 *
 *  @return JSON数据
 */
- (NSData *)toJSONData;

@end
