//
//  RecordDetailsViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface RecordDetailsViewController : BaseViewController

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

@property (nonatomic,assign) NSInteger plantId;
@property (nonatomic,strong) NSString *hybridName;
@property (nonatomic,strong) NSString *plantCode;

@property (nonatomic,assign) NSInteger seedlingId;
@property (nonatomic,strong) NSString *germplasmName;
@property (nonatomic,strong) NSString *seedlingCode;

@end

NS_ASSUME_NONNULL_END
