//
//  MyCollectCell.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface MyCollectCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *codeLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UIView *levelView;
@property (weak, nonatomic) IBOutlet UIImageView *levelImageView;

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
