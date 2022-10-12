//
//  ChangeLanguage.h
//  LiZaJiao
//
//  Created by 顾杰 on 2022/9/24.
//  Copyright © 2022 GuJie. All rights reserved.
//

#import <Foundation/Foundation.h>

static NSString * const AppLanguage = @"appLanguage";

NS_ASSUME_NONNULL_BEGIN

@interface ChangeLanguage : NSObject

+ (ChangeLanguage *)sharedInstance;

//初始化多语言功能
- (void)initLanguage;

//当前语言
- (NSString *)currentLanguage;

//设置要转换的语言
- (void)setLanguage:(NSString *)language;

//设置为系统语言
- (void)systemLanguage;

@end

NS_ASSUME_NONNULL_END
