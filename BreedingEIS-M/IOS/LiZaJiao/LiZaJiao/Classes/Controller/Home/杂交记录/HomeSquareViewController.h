//
//  HomeSquareViewController.h
//  UinChat
//
//  Created by Apple on 2018/10/11.
//  Copyright © 2018年 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface HomeSquareViewController : BaseViewController

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@end

NS_ASSUME_NONNULL_END
