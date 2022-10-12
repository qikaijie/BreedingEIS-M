//
//  ZZHistoryViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/12.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^CopyValueBlock)(NSMutableArray *value);

@interface ZZHistoryViewController : BaseViewController

@property (nonatomic,assign) NSInteger seedlingId;

@property (nonatomic, copy) CopyValueBlock copyValueBlock;
- (void)copyValue:(CopyValueBlock)block;

@end

NS_ASSUME_NONNULL_END
