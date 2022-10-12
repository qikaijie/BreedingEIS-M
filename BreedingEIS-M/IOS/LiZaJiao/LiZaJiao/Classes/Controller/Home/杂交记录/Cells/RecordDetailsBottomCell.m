//
//  RecordDetailsBottomCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "RecordDetailsBottomCell.h"

@implementation RecordDetailsBottomCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    RecordDetailsBottomCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"RecordDetailsBottomCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

@end
