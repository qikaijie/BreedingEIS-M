//
//  WHSelectGoodsView.h
//  GS智呼
//
//  Created by 顾杰 on 2021/5/6.
//  Copyright © 2021 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^IndexSelect)(NSInteger selectIndex);
@interface WHSelectGoodsView : UIView
@property (strong, nonatomic) UIButton *cancleBtn;
@property (strong, nonatomic) UIButton *confirmBtn;
@property (strong, nonatomic) UIPickerView *pickerView;
@property (strong, nonatomic) NSLayoutConstraint *lineWidth;
@property (strong, nonatomic) NSLayoutConstraint *lineHeight;

- (instancetype)init:(NSMutableArray *)array andIndex:(NSInteger)index showTitleKey:(NSString *)titleKey;

@property (nonatomic, copy) IndexSelect selectBlock;
@end
