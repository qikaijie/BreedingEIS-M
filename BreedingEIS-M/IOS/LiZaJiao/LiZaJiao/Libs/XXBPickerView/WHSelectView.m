//
//  WHSelectView.m
//  PublicIntelligentCall
//
//  Created by wuhao on 2017/2/8.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "WHSelectView.h"
#define kPVH (SCREEN_HEIGHT*0.35>230?230:(SCREEN_HEIGHT*0.35<200?200:SCREEN_HEIGHT*0.35))

@interface WHSelectView ()<UIPickerViewDelegate,UIPickerViewDataSource>

@property (nonatomic, strong) NSArray * dataSource;

/** 选择器文本内容字体属性,默认:蓝色,14号 */
@property (strong, nonatomic) NSDictionary *textAttributes;

@property (nonatomic, strong) NSDictionary *industrydic;
@end

@implementation WHSelectView
- (instancetype)init{
    self = [super init];
    if (self) {
        [self createData];
        [self createUI];
    }
    return self;
}
//- (NSMutableArray *)dataSource{
//    if (!_dataSource) {
//        _dataSource = [NSMutableArray array];
//    }
//    return _dataSource;
//}

- (NSDictionary *)textAttributes {
    if (_textAttributes == nil) {
        _textAttributes = @{NSFontAttributeName:[UIFont systemFontOfSize:18],NSForegroundColorAttributeName:RGBCOLOR(102, 102, 102)};
    }
    
    return _textAttributes;
}

- (void)createData{
    self.dataSource = @[@"预约办理",@"转派划小",@"拒绝办理"];
}

- (void)createUI{
    self.backgroundColor = [UIColor whiteColor];
    _cancleBtn = [[UIButton alloc]init];
    [_cancleBtn setTitle:NSLocalizedString(@"quxiao", @"") forState:UIControlStateNormal];
    [_cancleBtn setTitleColor:RGBCOLOR(102, 102, 102) forState:UIControlStateNormal];
    [self addSubview:_cancleBtn];
    _cancleBtn.frame = CGRectMake(0, 0, 100, 50);
    
    
    _confirmBtn = [[UIButton alloc]init];
    [_confirmBtn setTitle:NSLocalizedString(@"sure", @"") forState:UIControlStateNormal];
    [_confirmBtn setTitleColor:RGBCOLOR(102, 102, 102) forState:UIControlStateNormal];
    [self addSubview:_confirmBtn];
    _confirmBtn.frame = CGRectMake(SCREEN_WIDTH-100, 0, 100, 50);
    
    _pickerView = [[UIPickerView alloc]init];
    _pickerView.backgroundColor = [UIColor whiteColor];
    
    _pickerView.delegate = self;
    _pickerView.dataSource = self;
    [_pickerView selectRow:0 inComponent:0 animated:YES];
    [self addSubview:_pickerView];
    _pickerView.frame = CGRectMake(0, 50, SCREEN_WIDTH, kPVH-50);
    
}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return 35;
}
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    return self.dataSource.count;
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(nullable UIView *)view {
    
    UILabel *label = [[UILabel alloc]init];
    label.font = [UIFont systemFontOfSize:16];
    label.textAlignment = NSTextAlignmentCenter;
    
    NSAttributedString *attStr = [[NSAttributedString alloc]initWithString:self.dataSource[row] attributes:self.textAttributes];
    label.attributedText = attStr;
   
    return label;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    NSString *industry = @"";

        
    industry = [self.dataSource objectAtIndex:row];
    
    
////    [pickerView reloadComponent:1];
////    [pickerView selectRow:0 inComponent:1 animated:YES];
//    NSArray *arr = [_industrydic objectForKey:self.dataSource[row]];
//    subIndustry = [arr objectAtIndex:0];
    
    
    if (_selectBlock) {
        NSString *address = [NSString stringWithFormat:@"%@",industry];
        
        _selectBlock(address);
    }
    
}


@end
