//
//  PJItemCell.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface PJItemCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *selectItemTitleLabel;
@property (weak, nonatomic) IBOutlet UIImageView *selectItemImage;


+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
