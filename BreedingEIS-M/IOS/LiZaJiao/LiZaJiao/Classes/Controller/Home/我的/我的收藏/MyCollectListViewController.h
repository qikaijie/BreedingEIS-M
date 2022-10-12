//
//  MyCollectListViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^CollectDelBlock)(BOOL isDel);

@interface MyCollectListViewController : BaseViewController

@property (nonatomic, copy) CollectDelBlock collectDelBlock;
- (void)collectDelBlockValues:(CollectDelBlock)block;

@property (assign,nonatomic) NSInteger collectId;

@property (assign,nonatomic) NSInteger plantId;
@property (assign,nonatomic) NSInteger seedlingId;
/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@end

NS_ASSUME_NONNULL_END
