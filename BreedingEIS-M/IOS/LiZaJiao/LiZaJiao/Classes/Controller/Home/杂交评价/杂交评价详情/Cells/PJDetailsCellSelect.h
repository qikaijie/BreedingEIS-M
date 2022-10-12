//
//  PJDetailsCellSelect.h
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/17.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface PJDetailsCellSelect : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *fieldNameLabel;

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIView *selectMainView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *selectMainViewWidth;

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier;

@end

NS_ASSUME_NONNULL_END
