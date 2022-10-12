//
//  RecordYearListCell.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/11.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface RecordYearListCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *nameLabel;

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
