//
//  LBXPermissionNetwork.m
//  LBXKits
//
//  Created by lbx on 2017/12/7.
//  Copyright © 2017年 lbx. All rights reserved.
//

#import "LBXPermissionNetwork.h"
@import CoreTelephony;

@implementation LBXPermissionNetwork


+ (BOOL)authorized
{
    
    return YES;
}

+ (void)authorizeWithCompletion:(void(^)(BOOL granted,BOOL firstTime))completion
{
    completion(YES,NO);

}

@end
