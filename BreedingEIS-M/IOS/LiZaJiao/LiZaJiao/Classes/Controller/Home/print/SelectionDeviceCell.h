//
//  SelectionDeviceCell.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/10/24.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface SelectionDeviceCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *titleLable;
@property (weak, nonatomic) IBOutlet UILabel *subTitleLabel;

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
