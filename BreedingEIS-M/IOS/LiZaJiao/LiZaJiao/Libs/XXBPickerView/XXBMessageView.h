//
//  XXBMessageView.h
//  SoftCall
//
//  Created by wuhao on 2017/12/13.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void (^MessageSelect)(NSString *selectString,NSInteger messageRow);

@interface XXBMessageView : UIView

@property (nonatomic, strong) NSArray * dataArray;

@property (nonatomic, copy) MessageSelect  stringBlock;

@property (nonatomic, strong) NSString * selectString;


@property (nonatomic, assign) NSInteger messagerow;

- (void)didFinishSelected:(MessageSelect)selectString;

- (void)initDataWithArray:(NSMutableArray *)array;
@end
