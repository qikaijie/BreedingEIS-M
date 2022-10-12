//
//  RecordDateChooseView.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/13.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "RecordDateChooseView.h"
#import "RecordYearListCell.h"
#import "PopView.h"

@interface RecordDateChooseView ()<UITableViewDelegate,UITableViewDataSource>

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation RecordDateChooseView

- (void)layoutSubviews {
    
    // 阴影颜色
    self.mainView.layer.shadowColor = [UIColor grayColor].CGColor;
    // 阴影偏移，默认(0, -3)
    self.mainView.layer.shadowOffset = CGSizeMake(-10,10);
    // 阴影透明度，默认0
    self.mainView.layer.shadowOpacity = 0.5;
    // 阴影半径，默认3
    self.mainView.layer.shadowRadius = 5;
    
    self.topPadding.constant = kStatusBarHeight+28;
    self.bottomPadding.constant = kTabbarSafeBottomMargin+28;
    self.leftPadding.constant = kSCREEN_WIDTH/3;
    [self initTableView];
    
    [self.leftView whenTapped:^{
        [PopView hidenPopView];
    }];
}

- (void)returnValues:(RecordDateChooseValueBlock)block{
    self.selectedValueBlock = block;
}

- (void)initTableView {
    [self.tableView setBackgroundColor:[UIColor clearColor]];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
}

#pragma mark - UITableViewDelegate/UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 40;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 0.01;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView *heaView = [[UIView alloc]init];
    [heaView setBackgroundColor:[UIColor clearColor]];
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 0.01)];
    return heaView;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section{
    UIView *heaView = [[UIView alloc]init];
    [heaView setBackgroundColor:[UIColor clearColor]];
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 0.01)];
    return heaView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 0.01;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    RecordYearListCell *recordListCell = [RecordYearListCell cellInTableView:tableView withIdentifier:identifier];
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    recordListCell.nameLabel.textColor = [UIColor grayColor];
    recordListCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"createTime"]];
    
    recordListCell.nameLabel.layer.borderColor = [[UIColor colorWithRed:160/255.0f green:160/255.0f blue:160/255.0f alpha:0.6] CGColor];
    
    if(self.selectDict != nil){
        if([[self.selectDict objectForKey:@"id"] intValue] == [[dataDict objectForKey:@"id"] intValue]){
            recordListCell.nameLabel.layer.borderColor = [navigationBarColor CGColor];
            recordListCell.nameLabel.textColor = navigationBarColor;
        }
    }
    
    return recordListCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
    
    if(self.selectedValueBlock){
        self.selectedValueBlock(indexPath.row);
        [PopView hidenPopView];
    }
}

@end
