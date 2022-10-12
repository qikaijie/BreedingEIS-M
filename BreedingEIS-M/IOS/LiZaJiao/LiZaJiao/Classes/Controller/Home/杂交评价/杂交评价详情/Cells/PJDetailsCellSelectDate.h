//
//  PJDetailsCellSelectDate.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/17.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface PJDetailsCellSelectDate : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *fieldNameLabel;

@property (weak, nonatomic) IBOutlet UIView *putView;
@property (weak, nonatomic) IBOutlet UILabel *dateLabel;


+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
