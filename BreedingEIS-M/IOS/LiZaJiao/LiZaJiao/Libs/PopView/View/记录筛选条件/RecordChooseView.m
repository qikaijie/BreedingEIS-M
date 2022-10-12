//
//  RecordChooseView.m
//  MerchantAssistant
//
//  Created by Apple on 4/26/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import "RecordChooseView.h"
#import "PopView.h"
#import "WHPickerGoodsView.h"
#import "RecordYearListCell.h"

@interface RecordChooseView ()<UITableViewDelegate,UITableViewDataSource>
{
    int selectYearIndex;
    NSString *selectYearString;
    // 选择数据
    NSMutableDictionary *selectDataDict;
}

@property (weak, nonatomic) IBOutlet UILabel *emptyDataLabel;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (nonatomic, strong) WHPickerGoodsView *pickerView;// 自定义的pickerview

@property (strong, nonatomic) NSMutableArray *dataArray;

@end

@implementation RecordChooseView

- (void)layoutSubviews {
    
    // 阴影颜色
    self.mainView.layer.shadowColor = [UIColor grayColor].CGColor;
    // 阴影偏移，默认(0, -3)
    self.mainView.layer.shadowOffset = CGSizeMake(-10,10);
    // 阴影透明度，默认0
    self.mainView.layer.shadowOpacity = 0.5;
    // 阴影半径，默认3
    self.mainView.layer.shadowRadius = 5;
    
    self.topPadding.constant = 48+kStatusBarHeight+kNavigationBarHeight;
    self.bottomPadding.constant = kTabbarSafeBottomMargin;
    self.bottomViewPadding.constant = kTabbarSafeBottomMargin;
    self.leftPadding.constant = kSCREEN_WIDTH/3;
    
    self.dataArray = [[NSMutableArray alloc] init];
    [self initTableView];
    /**
     初始化时间列表
     */
    self.yearArray = [[NSMutableArray alloc] init];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy"];
    int toyear = [[dateFormatter stringFromDate:[NSDate date]] intValue];
    for (int i = 0; i > 2000-toyear; i--) {
        [self.yearArray addObject:@{@"year":[NSString stringWithFormat:@"%d",toyear+i],@"id":[NSNumber numberWithInt:i]}];
    }
    self->selectYearIndex = 0;
    self->selectYearString = [NSString stringWithFormat:@"%d",toyear];
    if(self.selectDict != nil){
        NSMutableDictionary *tempDateDict = [self.selectDict objectForKey:@"date"];
        self->selectYearIndex = [[tempDateDict objectForKey:@"id"] intValue];
        self->selectYearString = [NSString stringWithFormat:@"%@",[tempDateDict objectForKey:@"year"]];
        self->selectDataDict = [[self.selectDict objectForKey:@"data"] mutableCopy];
    }
    self.yearLabel.layer.borderColor = [[UIColor colorWithRed:160/255.0f green:160/255.0f blue:160/255.0f alpha:0.6] CGColor];
    self.yearLabel.layer.borderWidth = 1;
    self.yearLabel.text = self->selectYearString;
    __weak typeof(self) weakSelf = self;
    [self.yearLabel whenTapped:^{
        // 年份选择
        weakSelf.pickerView = [[WHPickerGoodsView alloc]init:self.yearArray andIndex:self->selectYearIndex showTitleKey:@"year"];
        [weakSelf.pickerView didFinishSelected:^(NSInteger selectIndex) {
            if(self->selectYearIndex != selectIndex){
                self->selectYearIndex = (int)selectIndex;
                NSMutableDictionary *tempYearDict = [self.yearArray objectAtIndex:selectIndex];
                self->selectYearString = [NSString stringWithFormat:@"%@",[tempYearDict objectForKey:@"year"]];
                self.yearLabel.text = self->selectYearString;
                
                self->selectDataDict = nil;
                // 通过年份查询杂交组合库列表
                [self hybridListByYear];
            }
        }];
    }];
    
    [self.topView whenTapped:^{
        [PopView hidenPopView];
    }];
    [self.leftView whenTapped:^{
        [PopView hidenPopView];
    }];
    
    // 通过年份查询杂交组合库列表
    [self hybridListByYear];
}

#pragma mark - 通过年份查询杂交组合库列表
- (void)hybridListByYear{
    [GJHttpTool GET:self.businessType == 0 ? APP_HYBRID_LIST_BY_YEAR : APP_GERMPLASM_LIST_BY_YEAR parameters:@{(self.businessType == 0 ? @"hybridYear" : @"introductionYear"):self->selectYearString} headerParams:@{} awaitingView:self.mainView success:^(id responseObject) {
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            NSMutableArray *tmpArr = [[responseObject objectForKey:@"data"] mutableCopy];
            [self.dataArray removeAllObjects];
            if(tmpArr != nil && tmpArr != [NSNull null] && tmpArr.count>0){
                [self.dataArray addObjectsFromArray:tmpArr];
                self.tableView.hidden = NO;
                self.emptyDataLabel.hidden = YES;
            }else{
                self.tableView.hidden = YES;
                self.emptyDataLabel.hidden = NO;
            }
            // 刷新列表
            [self.tableView reloadData];
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
    }];
}


- (IBAction)closePopVIewClick:(UIButton *)sender {
    [PopView hidenPopView];
}

- (IBAction)sumbitClick:(UIButton *)sender {
    
    if(self->selectDataDict != nil){
        NSMutableDictionary *sumbitDict = [[NSMutableDictionary alloc]init];
        [sumbitDict setObject:self->selectDataDict forKey:@"data"];
        [sumbitDict setObject:@{@"year":self->selectYearString,@"id":[NSNumber numberWithInt:self->selectYearIndex]} forKey:@"date"];
        if(self.selectedValueBlock){
            self.selectedValueBlock(sumbitDict);
            [PopView hidenPopView];
        }
    }else{
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"qxxzzh", @"")];
    }
}

- (void)returnValues:(RecordChooseValueBlock)block{
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
    recordListCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
    
    recordListCell.nameLabel.layer.borderColor = [[UIColor colorWithRed:160/255.0f green:160/255.0f blue:160/255.0f alpha:0.6] CGColor];
    if(self->selectDataDict != nil){
        if([[self->selectDataDict objectForKey:@"id"] intValue] == [[dataDict objectForKey:@"id"] intValue]){
            recordListCell.nameLabel.layer.borderColor = [navigationBarColor CGColor];
            recordListCell.nameLabel.textColor = navigationBarColor;
        }
    }
    
    // 点击事件
    [recordListCell.nameLabel whenTapped:^{
        self->selectDataDict = [dataDict mutableCopy];
        [self.tableView reloadData];
    }];
    
    return recordListCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
}

@end
