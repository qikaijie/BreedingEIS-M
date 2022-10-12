//
//  WHSelectGoodsView.m
//  PublicIntelligentCall
//
//  Created by wuhao on 2017/2/8.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "WHSelectGoodsView.h"
#define kPVH (SCREEN_HEIGHT*0.35>230?230:(SCREEN_HEIGHT*0.35<200?200:SCREEN_HEIGHT*0.35))

@interface WHSelectGoodsView ()<UIPickerViewDelegate,UIPickerViewDataSource>

@property (nonatomic, assign) NSInteger selectIndex;
@property (nonatomic, strong) NSArray * dataSource;
@property (nonatomic, strong) NSString * titleKey;

/** 选择器文本内容字体属性,默认:蓝色,14号 */
@property (strong, nonatomic) NSDictionary *textAttributes;

@property (nonatomic, strong) NSDictionary *industrydic;
@end

@implementation WHSelectGoodsView


- (instancetype)init:(NSMutableArray *)array andIndex:(NSInteger)index showTitleKey:(NSString *)titleKey{
    self = [super init];
    if (self) {
        self.dataSource = [array copy];
        self.selectIndex = index;
        self.titleKey = titleKey;
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

- (void)createUI{
    self.backgroundColor = [UIColor whiteColor];
    _cancleBtn = [[UIButton alloc]init];
    [_cancleBtn setTitle:NSLocalizedString(@"quexiao", @"") forState:UIControlStateNormal];
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
    [_pickerView selectRow:self.selectIndex inComponent:0 animated:YES];
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
    
    
    NSMutableDictionary *mutaDict = [self.dataSource[row] mutableCopy];
    NSAttributedString *attStr = [[NSAttributedString alloc]initWithString:[NSString stringWithFormat:@"%@",[mutaDict objectForKey:self.titleKey]] attributes:self.textAttributes];
    label.attributedText = attStr;
   
    return label;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    //NSMutableDictionary *industry = [self.dataSource objectAtIndex:row];
    if (_selectBlock) {
        _selectBlock(row);
    }
    
}


@end
