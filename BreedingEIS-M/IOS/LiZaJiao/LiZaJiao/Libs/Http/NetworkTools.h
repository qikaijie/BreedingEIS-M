//
//  NetworkTools.h
//  Weibo
//
//  Created by qiang on 4/28/16.
//  Copyright © 2016 QiangTech. All rights reserved.
//

#import "AFNetworking.h"

@interface NetworkTools : AFHTTPSessionManager

+ (NetworkTools *)sharedNetwrokTools;

+ (void)setInstanceNil;

@end
