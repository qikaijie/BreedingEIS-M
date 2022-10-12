//
//  XXBDatePickerView.m
//  SoftCall
//
//  Created by wuhao on 2017/7/4.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "XXBDatePickerView.h"
#define kZero 0
#define kFullWidth [UIScreen mainScreen].bounds.size.width
#define kFullHeight self.frame.size.height

#define kDatePicY kFullHeight/3*2
#define kDatePicHeight kFullHeight/3

#define kDateTopBtnY kDatePicY - 30
#define kDateTopBtnHeight 30

#define kDateTopRightBtnWidth kDateTopLeftBtnWidth
#define kDateTopRightBtnX kFullWidth - 30 - kDateTopRightBtnWidth

#define kDateTopLeftbtnX 30
#define kDateTopLeftBtnWidth kFullWidth/5 

@interface XXBDatePickerView()
@property (nonatomic,strong)UIDatePicker *dateP;
@property (nonatomic,strong)UIButton *leftBtn;
@property (nonatomic,strong)UIButton *rightBtn;
@property (nonatomic,strong)UIView *topView;
@property (nonatomic,assign)UIDatePickerMode type;
@property (nonatomic,strong)UIButton *backButton;
@end

@implementation XXBDatePickerView
- (instancetype)initWithFrame:(CGRect)frame type:(UIDatePickerMode)type VCType:(NSString *)vcType{
    self = [super initWithFrame:frame];
    if (self) {
        self.type = type;
        self.VCtype = vcType;
        [self addSubview:self.backButton];
        [self addSubview:self.dateP];
        [self addSubview:self.topView];
        [self addSubview:self.leftBtn];
        [self addSubview:self.rightBtn];
    }
    return self;
}

- (UIButton *)backButton{
    if (!_backButton) {
        _backButton = [[UIButton alloc]init];
        
        [_backButton addTarget:self action:@selector(handleDateTopViewLeft) forControlEvents:UIControlEventTouchUpInside];
        _backButton.backgroundColor = [UIColor blackColor];
        _backButton.alpha = 0.2;
        _backButton.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    return _backButton;
}


- (UIDatePicker *)dateP{
    if (!_dateP) {
        self.dateP = [[UIDatePicker alloc]initWithFrame:CGRectMake(kZero, kDatePicY, kFullWidth, kDatePicHeight)];
        self.dateP.backgroundColor = [UIColor whiteColor];
        NSDate *currentDate = [NSDate date];
        self.dateP.minimumDate = currentDate;
        self.dateP.datePickerMode = self.type;
//        self.dateP.locale = [[NSLocale alloc]initWithLocaleIdentifier:@"zh_CHS_CN"];
//        self.dateP.locale = [[NSLocale alloc]initWithLocaleIdentifier:@"en_GB"];
        [self.dateP setLocale:[[NSLocale alloc]initWithLocaleIdentifier:@"zh-Hans"]];
        //        NSDate *maxDate = [NSDate date];
        //        self.dateP.maximumDate = maxDate;
        [self.dateP addTarget:self action:@selector(handleDateP:) forControlEvents:UIControlEventValueChanged];
    }
    return _dateP;
}



- (UIButton *)leftBtn{
    if (!_leftBtn) {
        self.leftBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        self.leftBtn.frame = CGRectMake(kDateTopLeftbtnX, kDateTopBtnY, kDateTopLeftBtnWidth, kDateTopBtnHeight);
        [self.leftBtn setTitle:NSLocalizedString(@"quxiao", @"") forState:UIControlStateNormal];
        self.leftBtn.titleLabel.font = [UIFont systemFontOfSize:(SCREEN_WIDTH / 375.0)*16];
        [self.leftBtn setTitleColor:RGBCOLOR(102, 102, 102) forState:UIControlStateNormal];
        [self.leftBtn addTarget:self action:@selector(handleDateTopViewLeft) forControlEvents:UIControlEventTouchUpInside];
    }
    return _leftBtn;
}

- (UIButton *)rightBtn {
    if (!_rightBtn) {
        self.rightBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        self.rightBtn.frame = CGRectMake(kDateTopRightBtnX, kDateTopBtnY, kDateTopRightBtnWidth, kDateTopBtnHeight);
        [self.rightBtn setTitleColor:RGBCOLOR(102, 102, 102) forState:UIControlStateNormal];
        if ([_VCtype isEqualToString:@"record"]) {
            [self.rightBtn setTitle:NSLocalizedString(@"sure", @"") forState:UIControlStateNormal];
        }else{
            [self.rightBtn setTitle:NSLocalizedString(@"sure", @"") forState:UIControlStateNormal];
        }
        self.rightBtn.titleLabel.font = [UIFont systemFontOfSize:(SCREEN_WIDTH / 375.0)*16];
        [self.rightBtn addTarget:self action:@selector(handleDateTopViewRight) forControlEvents:UIControlEventTouchUpInside];
    }
    return _rightBtn;
}

- (UIView *)topView {
    if (!_topView) {
        self.topView = [[UIView alloc]initWithFrame:CGRectMake(kZero, kDateTopBtnY, kFullWidth, kDateTopBtnHeight)];
        self.topView.backgroundColor = [UIColor whiteColor];
    }
    return _topView;
}

- (void)setNowTime:(NSString *)dateStr{
    
    
    [self.dateP setDate:[self dateFromString:dateStr] animated:YES];
}

- (void)setMinSelectDate:(NSDate *)minSelectDate
{
    if (minSelectDate) {
        [self.dateP setMinimumDate:minSelectDate];
    }
}

- (void)setIsBeforeTime:(BOOL)isBeforeTime
{
    if (isBeforeTime) {
        [self.dateP setMinimumDate:[NSDate dateWithTimeIntervalSince1970:0]];
    }
    else {
        [self.dateP setMinimumDate:[NSDate date]];
    }
}

- (void)setMaxSelectDate:(NSDate *)maxSelectDate
{
    if (maxSelectDate) {
        [self.dateP setMaximumDate:maxSelectDate];
    }
}

- (void)end{
    [self removeFromSuperview];
}

- (void)handleDateP :(NSDate *)date {
    
    [self.delegate changeTime:self.dateP.date withPickView:self];
}

- (void)handleDateTopViewLeft {
    [self end];
}

- (void)handleDateTopViewRight {
    [self.delegate determine:self.dateP.date withPickView:self];
    [self end];
}



// NSDate --> NSString
- (NSString*)stringFromDate:(NSDate*)date{
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    switch (self.type) {
        case UIDatePickerModeTime:
            [dateFormatter setDateFormat:@"HH:mm"];
            break;
        case UIDatePickerModeDate:
            [dateFormatter setDateFormat:@"yyyy-MM-dd"];
            break;
        case UIDatePickerModeDateAndTime:
            [dateFormatter setDateFormat:@"yyyy年MM月dd日 HH:mm"];
            break;
        case UIDatePickerModeCountDownTimer:
            [dateFormatter setDateFormat:@"HH:mm"];
            break;
        default:
            break;
    }
    NSString *destDateString = [dateFormatter stringFromDate:date];
    
    return destDateString;
    
}

//NSDate <-- NSString
- (NSDate*)dateFromString:(NSString*)dateString{
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    switch (self.type) {
        case UIDatePickerModeTime:
            [dateFormatter setDateFormat:@"HH:mm"];
            break;
        case UIDatePickerModeDate:
            [dateFormatter setDateFormat:@"yyyy-MM-dd"];
            break;
        case UIDatePickerModeDateAndTime:
            [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm"];
            break;
        case UIDatePickerModeCountDownTimer:
            [dateFormatter setDateFormat:@"HH:mm"];
            break;
        default:
            break;
    }
    NSDate *destDate= [dateFormatter dateFromString:dateString];
    
    return destDate;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
