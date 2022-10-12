//
//  XXBMessageTableViewCell.h
//  SoftCall
//
//  Created by wuhao on 2017/12/13.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XXBNewMessageModel.h"

@class XXBMessageTableViewCell;

@protocol MyMsgCellDelegate <NSObject>
- (void)clickFoldLabel:(XXBMessageTableViewCell *)cell;
@end

@interface XXBMessageTableViewCell : UITableViewCell
@property (nonatomic, strong) UILabel * nameLabel;

@property (nonatomic, strong) UIButton *detailBtn;

@property (nonatomic, strong) XXBNewMessageModel *model;

@property (nonatomic, weak) id<MyMsgCellDelegate> myMsgCellDelegate;

+(CGFloat)heightOfCellWithModel:(XXBNewMessageModel *)model;
@end
