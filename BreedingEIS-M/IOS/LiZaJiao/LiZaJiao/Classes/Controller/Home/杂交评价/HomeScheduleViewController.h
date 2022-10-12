//
//  HomeScheduleViewController.h
//  UinChat
//
//  Created by Apple on 2018/10/11.
//  Copyright © 2018年 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface HomeScheduleViewController : UIViewController

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@property (nonatomic,strong) NSMutableDictionary *groupMain;

@end

NS_ASSUME_NONNULL_END
