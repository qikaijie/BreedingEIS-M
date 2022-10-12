//
//  PJItemViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface PJItemViewController : BaseViewController

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@property (nonatomic,strong) NSString *groupIndex;

- (instancetype)initGroupIndex:(int)index;

@end

NS_ASSUME_NONNULL_END
