//
//  XXBMessageTableView.h
//  SoftCall
//
//  Created by wuhao on 2017/12/13.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^messageSelect)(NSString *selectString,NSInteger row);

@interface XXBMessageTableView : UIView
@property (strong, nonatomic) UIButton *cancleBtn;
@property (strong, nonatomic) UIButton *confirmBtn;
@property (strong, nonatomic) UITableView *tableView;
@property (strong, nonatomic) NSLayoutConstraint *lineWidth;
@property (strong, nonatomic) NSLayoutConstraint *lineHeight;


@property (nonatomic, copy) messageSelect selectBlock;

- (void)createDataWithArray:(NSMutableArray *)array;

@end
