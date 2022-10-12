//
//  MyCollectViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface MyCollectViewController : BaseViewController

/**
 0  杂交收藏
 1  种质收藏
 */
- (instancetype)initGroupIndex:(int)index;

@end

NS_ASSUME_NONNULL_END
