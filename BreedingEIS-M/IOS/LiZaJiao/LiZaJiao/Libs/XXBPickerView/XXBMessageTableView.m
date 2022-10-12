//
//  XXBMessageTableView.m
//  SoftCall
//
//  Created by wuhao on 2017/12/13.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "XXBMessageTableView.h"
#import "XXBMessageTableViewCell.h"
#import "XXBNewMessageModel.h"

#define kPVH (SCREEN_HEIGHT*0.35>230?230:(SCREEN_HEIGHT*0.35<200?200:SCREEN_HEIGHT*0.35))
@interface XXBMessageTableView ()<UITableViewDataSource,UITableViewDelegate,MyMsgCellDelegate>

@property (nonatomic, strong) NSMutableArray * dataSource;

/** 选择器文本内容字体属性,默认:蓝色,14号 */
@property (strong, nonatomic) NSDictionary *textAttributes;

@property (nonatomic, strong) NSDictionary *industrydic;

@property(nonatomic,strong)NSIndexPath *lastPath;


@end

@implementation XXBMessageTableView

- (NSMutableArray *)dataSource{
    if (!_dataSource) {
        _dataSource = [NSMutableArray array];
    }
    return _dataSource;
}

- (NSDictionary *)textAttributes {
    if (_textAttributes == nil) {
        _textAttributes = @{NSFontAttributeName:[UIFont systemFontOfSize:18],NSForegroundColorAttributeName:RGBCOLOR(102, 102, 102)};
    }
    
    return _textAttributes;
}

- (void)createDataWithArray:(NSMutableArray *)array{
    //    self.dataSource = [NSMutableArray array];
//    [self.dataSource addObjectsFromArray:array];
    
    for (NSString *info in array) {
        XXBNewMessageModel *model = [[XXBNewMessageModel alloc]init];
        model.name = info;
        [self.dataSource addObject:model];
    }
    [self createUI];
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
    
    _tableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 50, SCREEN_WIDTH, kPVH-50) style:UITableViewStyleGrouped];
    _tableView.backgroundColor = [UIColor whiteColor];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    [self addSubview:_tableView];
    _tableView.frame = CGRectMake(0, 50, SCREEN_WIDTH, kPVH-50);
    
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataSource.count;
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 0.01;
}

-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 0.01;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    XXBMessageTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    if (!cell) {
        cell = [[XXBMessageTableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"cell"];
    }
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.model = self.dataSource[indexPath.row];
    cell.myMsgCellDelegate = self;
    NSInteger row = [indexPath row];
    
    NSInteger oldRow = [_lastPath row];
    
    if (row == oldRow && _lastPath!=nil) {
        
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        
    }else{
        
        cell.accessoryType = UITableViewCellAccessoryNone;
        
    }
    
  
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *industry = @"";
    
    NSInteger newRow = [indexPath row];
    
    NSInteger oldRow = (self .lastPath !=nil)?[self .lastPath row]:-1;
    
    if (newRow != oldRow) {
        
        UITableViewCell *newCell = [tableView cellForRowAtIndexPath:indexPath];
        
        newCell.accessoryType = UITableViewCellAccessoryCheckmark;
        
        UITableViewCell *oldCell = [tableView cellForRowAtIndexPath:_lastPath];
        
        oldCell.accessoryType = UITableViewCellAccessoryNone;
        
        self .lastPath = indexPath;
    }
    
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    XXBNewMessageModel *model = self.dataSource[indexPath.row];
    
    industry = model.name;
    
    if ([industry containsString:@"#TIME#"] || [industry containsString:@"#ADDRESS#"] || [industry containsString:@"#PHONE#"]) {
        [_confirmBtn setTitle:@"编辑" forState:UIControlStateNormal];
    }else{
        [_confirmBtn setTitle:NSLocalizedString(@"sure", @"") forState:UIControlStateNormal];
    }
    
    if (_selectBlock) {
        NSString *address = [NSString stringWithFormat:@"%@",industry];
        
        _selectBlock(address,indexPath.row);
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    XXBNewMessageModel *model = self.dataSource[indexPath.row];

    return [XXBMessageTableViewCell heightOfCellWithModel:model];

}

-(void)clickFoldLabel:(XXBMessageTableViewCell *)cell{
    NSIndexPath * indexPath = [self.tableView indexPathForCell:cell];
    XXBNewMessageModel *flashModel = self.dataSource[indexPath.row];
    
    flashModel.isOpening = !flashModel.isOpening;
    // 刷新点击的cell
    [UIView animateWithDuration:0.2 animations:^{
        [self.tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
        [self.tableView scrollToRowAtIndexPath:indexPath atScrollPosition:UITableViewScrollPositionTop animated:YES];
    }];

}

@end
