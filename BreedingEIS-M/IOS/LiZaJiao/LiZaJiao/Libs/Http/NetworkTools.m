//
//  NetworkTools.m
//  Weibo
//
//  Created by qiang on 4/28/16.
//  Copyright © 2016 QiangTech. All rights reserved.
//

#import "NetworkTools.h"

static NetworkTools *instance = nil;

@implementation NetworkTools

+ (NetworkTools *)sharedNetwrokTools
{
    if(instance == nil)
    {
        BOOL isForTest = NO;   // YES 调试  NO 上线
        
        //https://test.cnbecs.com:8086/
        //http://192.168.1.127:8086/
        NSURL *url = [NSURL URLWithString:isForTest ? @"http://47.100.201.80:8081/yuzhong/app/":@"http://47.100.201.80:8081/yuzhong/app/"];
        instance = [[NetworkTools alloc] initWithBaseURL:url];
        
        instance.requestSerializer = [AFJSONRequestSerializer serializer];
        instance.responseSerializer = [AFJSONResponseSerializer serializer];
        
        instance.requestSerializer.timeoutInterval = 120.0f;  //20秒后 请求超时
        
        instance.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"application/json", @"text/json", @"text/javascript",@"text/html", @"text/html;charset=utf-8" ,@"text/plain", nil];
        
        //[instance.requestSerializer setValue:@"Basic anNiZWM6YmVjMTIzNCE=" forHTTPHeaderField:@"Authorization"];
        //[instance.requestSerializer setValue:@"13148383493" forHTTPHeaderField:@"deviceId"];
        //instance.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"application/json", @"text/json", @"text/javascript",@"text/html", @"text/html;charset=utf-8" , nil];
        
//        //信任所有证书
//        instance.securityPolicy.allowInvalidCertificates = YES;
//        // 是否在证书域字段中验证域名
//        [instance.securityPolicy setValidatesDomainName:NO];
    }
    return instance;
}

- (instancetype)initWithBaseURL:(NSURL *)url
{
    self = [super initWithBaseURL:url sessionConfiguration:nil];
    return self;
}

+ (void)setInstanceNil
{
    instance = nil;
}

@end
