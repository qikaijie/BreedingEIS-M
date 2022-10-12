//
//  RecordYearListCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/11.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "RecordYearListCell.h"

@implementation RecordYearListCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    self.nameLabel.layer.borderColor = [[UIColor colorWithRed:160/255.0f green:160/255.0f blue:160/255.0f alpha:0.6] CGColor];
    self.nameLabel.layer.borderWidth = 1;
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    RecordYearListCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"RecordYearListCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

@end
