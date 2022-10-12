//
//  UpdatingFilesViewController.h
//  SupplyChain
//
//  Created by Apple on 7/2/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^BreakValueBlock)(NSMutableArray *array);

@interface UpdatingFilesViewController : BaseViewController

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@property (strong, nonatomic) NSString *plantId;
@property (strong, nonatomic) NSString *plantCode;
@property (strong, nonatomic) NSMutableArray *imageList;

@property (nonatomic, copy) BreakValueBlock breakValueBlock;
- (void)breakValue:(BreakValueBlock)block;

@end

NS_ASSUME_NONNULL_END
