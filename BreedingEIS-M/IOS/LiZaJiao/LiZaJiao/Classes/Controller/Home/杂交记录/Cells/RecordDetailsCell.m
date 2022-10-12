//
//  RecordDetailsCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "RecordDetailsCell.h"

@implementation RecordDetailsCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    RecordDetailsCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"RecordDetailsCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

@end
