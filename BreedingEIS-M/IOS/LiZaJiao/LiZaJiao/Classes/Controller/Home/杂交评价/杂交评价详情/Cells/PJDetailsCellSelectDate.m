//
//  PJDetailsCellSelectDate.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/17.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "PJDetailsCellSelectDate.h"

@implementation PJDetailsCellSelectDate

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    PJDetailsCellSelectDate *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"PJDetailsCellSelectDate" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

@end
