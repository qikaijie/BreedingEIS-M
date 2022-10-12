//
//  XXBDatePickerView.h
//  SoftCall
//
//  Created by wuhao on 2017/7/4.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
@class XXBDatePickerView;
@protocol TXTimeDelegate <NSObject>
//必须实现的两个代理
@required
//当时间改变时触发
- (void)changeTime:(NSDate *)date withPickView:(XXBDatePickerView *)pickerView;
//确定时间
- (void)determine:(NSDate *)date withPickView:(XXBDatePickerView *)pickerView;

@end

@interface XXBDatePickerView : UIView
//初始化方法
- (instancetype)initWithFrame:(CGRect)frame type:(UIDatePickerMode)type VCType:(NSString *)vcType;
//设置初始时间
- (void)setNowTime:(NSString *)dateStr;

// NSDate --> NSString
- (NSString*)stringFromDate:(NSDate*)date;
//NSDate <-- NSString
- (NSDate*)dateFromString:(NSString*)dateString;

- (void)end;

@property (assign,nonatomic)id<TXTimeDelegate>delegate;

@property (strong, nonatomic) NSDate *maxSelectDate;
///优先级低于isBeforeTime
@property (strong, nonatomic) NSDate *minSelectDate;

///是否可选择当前时间之前的时间,默认为NO
@property (nonatomic,assign) BOOL isBeforeTime;

@property (nonatomic,strong) NSString *VCtype;

@end
