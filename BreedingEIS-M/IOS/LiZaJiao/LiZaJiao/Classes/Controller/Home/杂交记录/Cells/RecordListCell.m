//
//  RecordListCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "RecordListCell.h"

@implementation RecordListCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    RecordListCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"RecordListCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        cell.backgroundColor = [UIColor whiteColor];
    }
    return cell;
}

@end
