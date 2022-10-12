//
//  RecordDetailsBottomCell.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface RecordDetailsBottomCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIButton *deleteBtn;

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
