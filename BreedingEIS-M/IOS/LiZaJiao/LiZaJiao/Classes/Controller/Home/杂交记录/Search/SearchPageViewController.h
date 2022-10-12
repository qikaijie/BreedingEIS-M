//
//  SearchPageViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/21.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^SearchResultValueBlock)(NSDictionary *result);

@interface SearchPageViewController : BaseViewController

@property (nonatomic, copy) SearchResultValueBlock searchResultValueBlock;
- (void)returnValues:(SearchResultValueBlock)block;

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@end

NS_ASSUME_NONNULL_END
