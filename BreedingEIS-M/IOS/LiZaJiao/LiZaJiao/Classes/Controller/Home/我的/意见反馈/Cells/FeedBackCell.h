//
//  FeedBackCell.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/7/17.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface FeedBackCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *createTimeLabel;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *dialog1Width;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *dialog1Height;
@property (weak, nonatomic) IBOutlet UILabel *dialog1Label;

@property (weak, nonatomic) IBOutlet UIView *dialog2View;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *dialog2Width;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *dialog2Height;
@property (weak, nonatomic) IBOutlet UILabel *dialog2Label;

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
