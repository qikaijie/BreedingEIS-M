//
//  ZZHistoryDetailsViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/12.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface ZZHistoryDetailsViewController : BaseViewController

typedef void(^CopyValueBlock)(NSMutableArray *value);

/**
 0  初选记录
 1  高接记录
 2  区试记录
 */
- (instancetype)initGroupIndex:(int)index andSeedlingId:(int)seedlingId blockValue:(CopyValueBlock)block;

@end

NS_ASSUME_NONNULL_END
