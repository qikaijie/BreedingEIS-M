//
//  HistoryDetailsTopCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/12.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "HistoryDetailsTopCell.h"

@implementation HistoryDetailsTopCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    HistoryDetailsTopCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"HistoryDetailsTopCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}


@end
