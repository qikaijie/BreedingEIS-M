//
//  RecordDateChooseView.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/13.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef void(^RecordDateChooseValueBlock)(NSInteger selectIndex);

@interface RecordDateChooseView : UIView

@property (nonatomic, copy) RecordDateChooseValueBlock selectedValueBlock;
- (void)returnValues:(RecordDateChooseValueBlock)block;

@property (weak, nonatomic) IBOutlet UIView *leftView;
@property (weak, nonatomic) IBOutlet UIView *mainView;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *topPadding;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *leftPadding;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bottomPadding;

/**
 筛选条件在内部
 */
@property (strong, nonatomic) NSMutableDictionary *selectDict;
@property (strong, nonatomic) NSMutableArray *dataArray;

@end

NS_ASSUME_NONNULL_END
