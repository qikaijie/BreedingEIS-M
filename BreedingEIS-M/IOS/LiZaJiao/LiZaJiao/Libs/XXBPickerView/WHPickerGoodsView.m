//
//  WHPickerGoodsView.m
//  PublicIntelligentCall
//
//  Created by wuhao on 2017/2/8.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "WHPickerGoodsView.h"
#import "WHSelectGoodsView.h"
// pickerView高度
#define kPVH (SCREEN_HEIGHT*0.35>230?230:(SCREEN_HEIGHT*0.35<200?200:SCREEN_HEIGHT*0.35))

@interface WHPickerGoodsView ()
@property (nonatomic, strong) WHSelectGoodsView * pickView;
@property (nonatomic, strong) UIButton * backBtn;

@end
@implementation WHPickerGoodsView

- (instancetype)init:(NSMutableArray *)array andIndex:(NSInteger)index showTitleKey:(NSString *)titleKey{
    self = [super initWithFrame:[[UIScreen mainScreen] bounds]];
    if (self) {
        [self initData:array andIndex:index showTitleKey:titleKey];
    }
    return self;
}

- (void)initData:(NSMutableArray *)array andIndex:(NSInteger)index showTitleKey:(NSString *)titleKey{
    [[[UIApplication sharedApplication] keyWindow] addSubview:self];
    
    _backBtn = [[UIButton alloc]init];
    [self addSubview:_backBtn];
    [_backBtn addTarget:self action:@selector(dismissDatePicker) forControlEvents:UIControlEventTouchUpInside];
    _backBtn.backgroundColor = [UIColor blackColor];
    _backBtn.alpha = 0.0;
    _backBtn.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    
    _pickView = [[WHSelectGoodsView alloc]init:array andIndex:index showTitleKey:titleKey];
    [self addSubview:_pickView];
    _pickView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, kPVH);
    [_pickView.cancleBtn addTarget:self action:@selector(dismissDatePicker) forControlEvents:UIControlEventTouchUpInside];
    [_pickView.confirmBtn addTarget:self action:@selector(confirmBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    
    _pickView.selectBlock = ^(NSInteger index){
        _selectIndex = [NSString stringWithFormat:@"%d",index];
    };
    [self pushDatePicker];
}

- (void)didFinishSelected:(IndexSelect)selectIndex{
    _indexBlock = selectIndex;
}

//确定
- (void)confirmBtnClick:(id)sender
{
    if (_indexBlock) {
        _indexBlock([_selectIndex intValue]);
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
