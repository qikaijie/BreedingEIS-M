//
//  FeedBackCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/7/17.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "FeedBackCell.h"

@implementation FeedBackCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    FeedBackCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"FeedBackCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

@end
