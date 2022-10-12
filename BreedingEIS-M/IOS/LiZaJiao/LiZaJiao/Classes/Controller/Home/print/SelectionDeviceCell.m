//
//  SelectionDeviceCell.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/10/24.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "SelectionDeviceCell.h"

@implementation SelectionDeviceCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    SelectionDeviceCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"SelectionDeviceCell" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        cell.backgroundColor = [UIColor whiteColor];
    }
    return cell;
}

@end
