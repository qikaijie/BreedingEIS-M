//
//  RecordChooseView.h
//  MerchantAssistant
//
//  Created by Apple on 4/26/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

/**
 date :
    id = 0;
    year = 2021;
 data:
     id = 111;
     name = "\U79cb\U8363x\U96ea\U82b1";
     sowingCode = QX;
 */
typedef void(^RecordChooseValueBlock)(NSMutableDictionary *selectDataDict);

@interface RecordChooseView : UIView

@property (nonatomic, copy) RecordChooseValueBlock selectedValueBlock;
- (void)returnValues:(RecordChooseValueBlock)block;

@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UIView *leftView;
@property (weak, nonatomic) IBOutlet UIView *mainView;

@property (weak, nonatomic) IBOutlet UILabel *yearLabel;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *topPadding;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *leftPadding;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bottomPadding;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bottomViewPadding;

/**
 业务类型
 0  杂交
 1  种质
 */
@property (assign,nonatomic) NSInteger businessType;

/**
 筛选条件在内部
 */
@property (strong, nonatomic) NSMutableDictionary *selectDict;
@property (strong, nonatomic) NSMutableArray *yearArray;

@end

NS_ASSUME_NONNULL_END
