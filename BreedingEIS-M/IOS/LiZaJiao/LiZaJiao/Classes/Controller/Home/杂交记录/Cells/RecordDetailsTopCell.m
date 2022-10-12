//
//  RecordDetailsTopCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "RecordDetailsTopCell.h"

@implementation RecordDetailsTopCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    RecordDetailsTopCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"RecordDetailsTopCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

@end
