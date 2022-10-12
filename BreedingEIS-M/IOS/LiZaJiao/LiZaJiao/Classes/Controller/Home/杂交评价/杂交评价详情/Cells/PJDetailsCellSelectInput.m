//
//  PJDetailsCellSelectInput.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/17.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "PJDetailsCellSelectInput.h"

@interface PJDetailsCellSelectInput()<UITextFieldDelegate>

@end

@implementation PJDetailsCellSelectInput

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    
    [self.textField addTarget:self action:@selector(changedTextField:) forControlEvents:UIControlEventEditingChanged];
    self.textField.delegate = self;
}

+ (instancetype)cellInTableView:(UITableView *)tableView withIdentifier:(NSString *)identifier
{
    PJDetailsCellSelectInput *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"PJDetailsCellSelectInput" owner:self options:nil]firstObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
    }
    return cell;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

- (void)textFieldChangedValue:(TextFieldChangedValueBlock)block{
    self.textFieldChangedValueBlock = block;
}

#pragma mark -给每个cell中的textfield添加事件，只要值改变就调用此函数
-(void)changedTextField:(UITextField *)textField
{
    if(self.textFieldChangedValueBlock!=nil){
        self.textFieldChangedValueBlock(textField.text);
    }
}


@end
