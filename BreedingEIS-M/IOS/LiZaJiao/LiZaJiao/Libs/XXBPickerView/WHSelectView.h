//
//  WHSelectView.h
//  PublicIntelligentCall
//
//  Created by wuhao on 2017/2/8.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^StringSelect)(NSString *selectString);
@interface WHSelectView : UIView
@property (strong, nonatomic) UIButton *cancleBtn;
@property (strong, nonatomic) UIButton *confirmBtn;
@property (strong, nonatomic) UIPickerView *pickerView;
@property (strong, nonatomic) NSLayoutConstraint *lineWidth;
@property (strong, nonatomic) NSLayoutConstraint *lineHeight;

@property (nonatomic, copy) StringSelect selectBlock;
@end
