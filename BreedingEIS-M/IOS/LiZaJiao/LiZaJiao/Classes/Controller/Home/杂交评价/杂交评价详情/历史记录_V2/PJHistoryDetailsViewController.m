//
//  PJHistoryDetailsViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/12.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "PJHistoryDetailsViewController.h"
#import "RecordDetailsCell.h"
#import "HistoryDetailsTopCell.h"
#import "RecordDetailsBottomCell.h"
#import "LinkPrintViewController.h"

#import "PopView.h"
#import "RecordDateChooseView.h"

@interface PJHistoryDetailsViewController ()<UITableViewDelegate,UITableViewDataSource>{
    int groupIndex;
    int plantId;
    int selectIndex;
}

@property (nonatomic, copy) CopyValueBlock copyValueBlock;

@property (strong, nonatomic) NSMutableArray *detailsList;
@property (weak, nonatomic) IBOutlet UITableView *tableView;


@property (weak, nonatomic) IBOutlet UIButton *yCopyButton;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *yCopyButtonWidth;
@property (weak, nonatomic) IBOutlet UILabel *emptyDataLabel;

@end

@implementation PJHistoryDetailsViewController

- (instancetype)initGroupIndex:(int)index andPlantId:(int)plantId blockValue:(CopyValueBlock)block
{
    //1.初始化父类
    self = [super init];
    //2.判断父类是否初始化成功
    if(self)
    {
        //3.初始化子类
        self->groupIndex = index;
        self->plantId = plantId;
        self->selectIndex = -1;
        self.copyValueBlock = block;
        switch (index) {
            case 0:
                self.title = NSLocalizedString(@"cxjl", @"");
                break;
            case 1:
                self.title = NSLocalizedString(@"gjjl", @"");
                break;
            case 2:
                self.title = NSLocalizedString(@"qsjl", @"");
                break;
                
            default:
                self.title = @"----";
                break;
        }
    }
    //4.返回地址
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.yCopyButtonWidth.constant = kTabbarSafeBottomMargin+kStatusBarAndNavigationBarHeight - 34;
    
    [self initTableView];
    
    [self queryData];
}

- (void)queryData{
    [GJHttpTool GET:APP_PLANT_RECORD_HISTORY_LIST_INFO parameters:@{@"plantId":[NSNumber numberWithInt:self->plantId],@"type":[NSNumber numberWithInt:self->groupIndex+1]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
        
        NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:responseObject options:NSJSONWritingPrettyPrinted error:&error];
        if (jsonData) {
            NSString *responseJsonStr =[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
            if (self->groupIndex == 0) {
                NSLog(@"%@",responseJsonStr);
            }else if (self->groupIndex == 1) {
                NSLog(@"%@",responseJsonStr);
            }else if (self->groupIndex == 2) {
                NSLog(@"%@",responseJsonStr);
            }
        }
        
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            self.detailsList = [[responseObject objectForKey:@"data"] mutableCopy];
            //赋值列表
            if(self.detailsList != nil && self.detailsList != [NSNull null] && self.detailsList.count>0){
                self.tableView.hidden = NO;
                self.yCopyButton.hidden = NO;
                self.emptyDataLabel.hidden = YES;
                if(self->selectIndex == -1){
                    self->selectIndex = 0;
                }
                
                [self.tableView reloadData];
            }else{
                self.tableView.hidden = YES;
                self.yCopyButton.hidden = YES;
                self.emptyDataLabel.hidden = NO;
                self.emptyDataLabel.text = NSLocalizedString(@"empty.his.data", @"");
            }
        }else{
            self.tableView.hidden = YES;
            self.yCopyButton.hidden = YES;
            self.emptyDataLabel.hidden = NO;
            self.emptyDataLabel.text = [NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]];
            //[SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
        self.tableView.hidden = YES;
        self.yCopyButton.hidden = YES;
        self.emptyDataLabel.hidden = NO;
        self.emptyDataLabel.text = NSLocalizedString(@"service.get.fain", @"");
    }];
}

- (void)initTableView {
    [self.tableView setBackgroundColor:kTableViewBackCoclor];//
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    [self.tableView reloadData];
}

#pragma mark - UITableViewDelegate/UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return (self->selectIndex != -1) ? 2 : 0;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if(section == 0){
        return 1;
    }else if(section == 2){
        return 1;
    }
    NSMutableDictionary *tempDict = [[self.detailsList objectAtIndex:self->selectIndex] mutableCopy];
    NSString *attributeValues = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"attributeValues"]];
    NSMutableArray *tempArray = !strIsEmpty(attributeValues) ? [GJUtils jsonToObject:attributeValues] : nil;
    return tempArray!=nil&&tempArray!=[NSNull null] ? tempArray.count : 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if(indexPath.section == 0){
        return 122;
    }else if(indexPath.section == 2){
        return 90;
    }
    return 45;
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
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, section != 1 ? 0.01 : kStatusBarAndNavigationBarHeight+kTabbarSafeBottomMargin+48)];
    return heaView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return section != 1 ? 0.01 : kStatusBarAndNavigationBarHeight+kTabbarSafeBottomMargin+48;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    
    NSMutableDictionary *tempDict = [[self.detailsList objectAtIndex:self->selectIndex] mutableCopy];
    
    if(indexPath.section == 0){
        HistoryDetailsTopCell *historyDetailsTopCell = [HistoryDetailsTopCell cellInTableView:tableView withIdentifier:identifier];
        historyDetailsTopCell.nameTitleLabel.text = NSLocalizedString(@"zjzymc", @"");
        historyDetailsTopCell.codeLabel.text = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"plantCode"]];
        historyDetailsTopCell.nameLabel.text = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"hybridName"]];
        historyDetailsTopCell.timeLabel.text = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"createTime"]];
        
        [historyDetailsTopCell.printBtn whenTapped:^{
            LinkPrintViewController *linkPrintViewController = [[LinkPrintViewController alloc]init];
            linkPrintViewController.title = NSLocalizedString(@"ljbxbqj", @"");
            linkPrintViewController.hidesBottomBarWhenPushed = YES;
            linkPrintViewController.codeStr = historyDetailsTopCell.codeLabel.text;
            [self.navigationController pushViewController:linkPrintViewController animated:YES];
        }];
        
        [historyDetailsTopCell.timeView whenTapped:^{
            //选择记录时间
            RecordDateChooseView *recordDateChooseView = [[NSBundle mainBundle] loadNibNamed:@"RecordDateChooseView" owner:nil options:nil].firstObject;
            recordDateChooseView.frame = CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT);
            recordDateChooseView.backgroundColor = [UIColor clearColor];
            recordDateChooseView.selectDict = tempDict;
            recordDateChooseView.dataArray = self.detailsList;
            [recordDateChooseView returnValues:^(NSInteger selectIndex) {
                self->selectIndex = (int)selectIndex;
                //刷新列表
                [self.tableView reloadData];
            }];
            PopView *popView = [PopView popSideContentView:recordDateChooseView direct:PopViewDirection_SlideFromRight];
            popView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.0];
        }];
        return historyDetailsTopCell;
    }else if(indexPath.section == 2){
        RecordDetailsBottomCell *recordDetailsBottomCell = [RecordDetailsBottomCell cellInTableView:tableView withIdentifier:identifier];
        [recordDetailsBottomCell.deleteBtn setTitle:NSLocalizedString(@"onekeycopy", @"") forState:UIControlStateNormal];
        [recordDetailsBottomCell.deleteBtn whenTapped:^{
            //一键克隆
            UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:@"已填写的数据也会被覆盖，是否确认克隆历史数据？"
                                                                              preferredStyle:UIAlertControllerStyleAlert];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
                NSLog(@"取消了");
            }])];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"yes", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                //一键克隆
                if(self.copyValueBlock!=nil){
                    NSString *attributeValues = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"attributeValues"]];
                    NSMutableArray *tempArray = [GJUtils jsonToObject:attributeValues];
                    self.copyValueBlock(tempArray);
                }
                [self.navigationController popViewControllerAnimated:YES];
            }])];
            [self presentViewController:alertController animated:YES completion:nil];
        }];
        return recordDetailsBottomCell;
    }else{
        RecordDetailsCell *recordDetailsCell = [RecordDetailsCell cellInTableView:tableView withIdentifier:identifier];
        
        NSString *attributeValues = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"attributeValues"]];
        NSMutableArray *tempArray = [GJUtils jsonToObject:attributeValues];
        NSDictionary *dataDict = tempArray[indexPath.row];
        recordDetailsCell.titleLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
        recordDetailsCell.detailsLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"value"]];
        recordDetailsCell.gjBackgroundView.backgroundColor = [UIColor whiteColor];
        if(tempArray.count!=1){
            if(indexPath.row == 0){
                //上边圆角
                [recordDetailsCell.gjBackgroundView addRoundedCorners:UIRectCornerTopLeft|UIRectCornerTopRight withRadii:CGSizeMake(6.0, 6.0) viewRect:CGRectMake(0, 0, kSCREEN_WIDTH-32, 45)];
                recordDetailsCell.lineView.hidden = NO;
            }else if(indexPath.row == tempArray.count-1){
                //底部圆角
                [recordDetailsCell.gjBackgroundView addRoundedCorners:UIRectCornerBottomLeft|UIRectCornerBottomRight withRadii:CGSizeMake(6.0, 6.0) viewRect:CGRectMake(0, 0, kSCREEN_WIDTH-32, 45)];
                //隐藏线
                recordDetailsCell.lineView.hidden = YES;
            }
        }else{
            //四角圆角
            [recordDetailsCell.gjBackgroundView addRoundedCorners:UIRectCornerAllCorners withRadii:CGSizeMake(8.0, 8.0) viewRect:CGRectMake(0, 0, kSCREEN_WIDTH-32, 45)];
            //隐藏线
            recordDetailsCell.lineView.hidden = YES;
        }
        return recordDetailsCell;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
}

- (IBAction)yCopyButtonClick:(UIButton *)sender {
    //一键克隆
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:@"已填写的数据也会被覆盖，是否确认克隆历史数据？"
                                                                      preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        NSLog(@"取消了");
    }])];
    [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"yes", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        //一键克隆
        if(self.copyValueBlock!=nil){
            NSMutableDictionary *tempDict = [[self.detailsList objectAtIndex:self->selectIndex] mutableCopy];
            NSString *attributeValues = [NSString stringWithFormat:@"%@",[tempDict objectForKey:@"attributeValues"]];
            NSMutableArray *tempArray = [GJUtils jsonToObject:attributeValues];
            self.copyValueBlock(tempArray);
        }
        [self.navigationController popViewControllerAnimated:YES];
    }])];
    [self presentViewController:alertController animated:YES completion:nil];
}

@end
