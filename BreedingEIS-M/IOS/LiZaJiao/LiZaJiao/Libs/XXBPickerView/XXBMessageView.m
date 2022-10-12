//
//  XXBMessageView.m
//  SoftCall
//
//  Created by wuhao on 2017/12/13.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "XXBMessageView.h"
#import "XXBMessageTableView.h"
// pickerView高度
#define kPVH (SCREEN_HEIGHT*0.35>230?230:(SCREEN_HEIGHT*0.35<200?200:SCREEN_HEIGHT*0.35))

@interface XXBMessageView ()

@property (nonatomic, strong) XXBMessageTableView * tableView;

@property (nonatomic, strong) UIButton * backBtn;

@end

@implementation XXBMessageView

- (void)initDataWithArray:(NSMutableArray *)array{
    
    _backBtn = [[UIButton alloc]init];
    [self addSubview:_backBtn];
    [_backBtn addTarget:self action:@selector(dismissDatePicker) forControlEvents:UIControlEventTouchUpInside];
    _backBtn.backgroundColor = [UIColor blackColor];
    _backBtn.alpha = 0.0;
    _backBtn.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    
    _tableView = [[XXBMessageTableView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-kPVH, SCREEN_WIDTH, kPVH)];
    [_tableView createDataWithArray:array];
    [self addSubview:_tableView];
    
    [_tableView.cancleBtn addTarget:self action:@selector(dismissDatePicker) forControlEvents:UIControlEventTouchUpInside];
    [_tableView.confirmBtn addTarget:self action:@selector(confirmBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    NSString *string = array[0];
    if ([string containsString:@"#TIME#"] || [string containsString:@"#ADDRESS#"] || [string containsString:@"#PHONE#"] ) {
        [_tableView.confirmBtn setTitle:@"编辑" forState:UIControlStateNormal];
    }

    
    _tableView.selectBlock = ^(NSString *selectString, NSInteger row) {
        _selectString = selectString;
        _messagerow = row;
    };
    
    [self pushDatePicker];
}

- (void)didFinishSelected:(MessageSelect)selectString{
    _stringBlock = selectString;
}

//确定
- (void)confirmBtnClick:(id)sender
{
    if (_stringBlock) {
        _stringBlock(_selectString,_messagerow);
    }
    [self dismissDatePicker];
}

//出现
- (void)pushDatePicker
{
    __weak typeof(self) weakSelf = self;
    [UIView animateWithDuration:0.2 animations:^{
        weakSelf.tableView.frame = CGRectMake(0, SCREEN_HEIGHT - kPVH, SCREEN_WIDTH, kPVH);
        weakSelf.backBtn.alpha = 0.2;
    }];
}
// 消失
- (void)dismissDatePicker{
    __weak typeof(self) weakSelf = self;
    [UIView animateWithDuration:0.2 animations:^{
        weakSelf.tableView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, kPVH);
        weakSelf.backBtn.alpha = 0.0;
    } completion:^(BOOL finished) {
        [weakSelf.tableView removeFromSuperview];
        [weakSelf.backBtn removeFromSuperview];
        [weakSelf removeFromSuperview];
    }];
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
