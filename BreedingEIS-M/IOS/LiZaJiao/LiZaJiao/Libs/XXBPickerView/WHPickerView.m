//
//  WHPickerView.m
//  PublicIntelligentCall
//
//  Created by wuhao on 2017/2/8.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "WHPickerView.h"
#import "WHSelectView.h"
// pickerView高度
#define kPVH (SCREEN_HEIGHT*0.35>230?230:(SCREEN_HEIGHT*0.35<200?200:SCREEN_HEIGHT*0.35))

@interface WHPickerView ()
@property (nonatomic, strong) WHSelectView * pickView;
@property (nonatomic, strong) UIButton * backBtn;

@end
@implementation WHPickerView
- (instancetype)init{
    self = [super initWithFrame:[[UIScreen mainScreen] bounds]];
    if (self) {
        [self initData];
    }
    return self;
}

- (void)initData{
    [[[UIApplication sharedApplication] keyWindow] addSubview:self];
    
    _backBtn = [[UIButton alloc]init];
    [self addSubview:_backBtn];
    [_backBtn addTarget:self action:@selector(dismissDatePicker) forControlEvents:UIControlEventTouchUpInside];
    _backBtn.backgroundColor = [UIColor blackColor];
    _backBtn.alpha = 0.0;
    _backBtn.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    
    _pickView = [[WHSelectView alloc]init];
    [self addSubview:_pickView];
    _pickView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, kPVH);
    [_pickView.cancleBtn addTarget:self action:@selector(dismissDatePicker) forControlEvents:UIControlEventTouchUpInside];
    [_pickView.confirmBtn addTarget:self action:@selector(confirmBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    
    _pickView.selectBlock = ^(NSString *str){
        _selectString = str;
    };
    [self pushDatePicker];
}

- (void)didFinishSelected:(StringSelect)selectString{
    _stringBlock = selectString;
}

//确定
- (void)confirmBtnClick:(id)sender
{
    if (_stringBlock) {
        _stringBlock(_selectString);
    }
    [self dismissDatePicker];
}

//出现
- (void)pushDatePicker
{
    __weak typeof(self) weakSelf = self;
    [UIView animateWithDuration:0.2 animations:^{
        weakSelf.pickView.frame = CGRectMake(0, SCREEN_HEIGHT - kPVH, SCREEN_WIDTH, kPVH);
        weakSelf.backBtn.alpha = 0.2;
    }];
}
// 消失
- (void)dismissDatePicker{
    __weak typeof(self) weakSelf = self;
    [UIView animateWithDuration:0.2 animations:^{
        weakSelf.pickView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, kPVH);
        weakSelf.backBtn.alpha = 0.0;
    } completion:^(BOOL finished) {
        [weakSelf.pickView removeFromSuperview];
        [weakSelf.backBtn removeFromSuperview];
        [weakSelf removeFromSuperview];
    }];
}

@end
