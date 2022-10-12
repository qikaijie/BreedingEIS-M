//
//  NSDictionary+JSONString.h
//  storemanager
//
//  Created by Apple on 18/3/23.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSDictionary (JSONString)


+ (NSString*)convertToJSONData:(id)infoDict;

+ (NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString;

@end
