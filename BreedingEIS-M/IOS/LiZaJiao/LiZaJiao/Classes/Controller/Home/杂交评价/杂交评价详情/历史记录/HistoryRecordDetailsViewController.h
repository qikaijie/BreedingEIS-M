//
//  HistoryRecordDetailsViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/18.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^CopyValueBlock)(NSMutableArray *value);

@interface HistoryRecordDetailsViewController : BaseViewController

@property (nonatomic,strong) NSMutableDictionary *detailsDict;


@property (nonatomic,assign) NSInteger plantId;

@property (nonatomic,strong) NSString *hybridName;
@property (nonatomic,strong) NSString *plantCode;
@property (nonatomic,strong) NSString *createTime;
@property (nonatomic,strong) NSString *attributeValues;

@property (nonatomic, copy) CopyValueBlock copyValueBlock;
- (void)copyValue:(CopyValueBlock)block;

@end

NS_ASSUME_NONNULL_END
