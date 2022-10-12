//
//  ChangeLanguage.m
//  LiZaJiao
//
//  Created by 顾杰 on 2022/9/24.
//  Copyright © 2022 GuJie. All rights reserved.
//

#import "ChangeLanguage.h"

@implementation ChangeLanguage

+ (ChangeLanguage *)sharedInstance {
    static ChangeLanguage *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[ChangeLanguage alloc] init];
    });
    return instance;
}

- (void)initLanguage{
    NSString *language=[self currentLanguage];
    if (language.length>0) {
        NSLog(@"自设置语言:%@",language);
    }else{
        [self systemLanguage];
    }
}

- (NSString *)currentLanguage{
    NSString *language=[[NSUserDefaults standardUserDefaults]objectForKey:AppLanguage];
    return language;
}

- (void)setLanguage:(NSString *)language{
    [[NSUserDefaults standardUserDefaults] setObject:language forKey:AppLanguage];
    
    
    // 切换语言
    NSArray *lans = @[language];
    [[NSUserDefaults standardUserDefaults] setObject:lans forKey:@"AppleLanguages"];
}

- (void)systemLanguage{
    NSString *languageCode = [[NSUserDefaults standardUserDefaults] objectForKey:@"AppleLanguages"][0];
    NSLog(@"系统语言:%@",languageCode);
    if([languageCode hasPrefix:@"zh-Hans"]){
        languageCode = @"zh-Hans";//简体中文
    }else if([languageCode hasPrefix:@"en"]){
        languageCode = @"en";//英语
    }
    [self setLanguage:languageCode];
}

@end
