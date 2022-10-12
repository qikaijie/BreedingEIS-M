//
//  HistoryRecordDetailsViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/18.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "HistoryRecordDetailsViewController.h"
#import "RecordDetailsCell.h"
#import "RecordDetailsTopCell.h"
#import "RecordDetailsBottomCell.h"
#import "LinkPrintViewController.h"

@interface HistoryRecordDetailsViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UIButton *backBtn;

@property (strong, nonatomic) NSMutableArray *dataArray;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation HistoryRecordDetailsViewController

- (void)viewWillAppear:(BOOL)animated{
    self.navigationController.navigationBar.barTintColor = [UIColor whiteColor];
    //    self.navigationController.navigationBar.shadowImage = [UIImage new];
    //设置导航栏文字颜色
    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObjectsAndKeys:
                                                                   [UIColor blackColor],
                                                                   NSForegroundColorAttributeName,
                                                                   [UIFont systemFontOfSize:17],
                                                                   NSFontAttributeName,nil];
    self.navigationController.navigationBar.tintColor = [UIColor blackColor];
    
    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
    backgroundView.subviews.firstObject.hidden = YES;
}

- (void)viewWillDisappear:(BOOL)animated{
    self.navigationController.navigationBar.barTintColor = [UIColor whiteColor];
    //设置导航栏文字颜色
    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObjectsAndKeys:
                                                                    [UIColor blackColor],
                                                                    NSForegroundColorAttributeName,
                                                                    [UIFont systemFontOfSize:17],
                                                                    NSFontAttributeName,nil];
    self.navigationController.navigationBar.tintColor = [UIColor blackColor];
    
    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
    backgroundView.subviews.firstObject.hidden = YES;
}

- (void)copyValue:(CopyValueBlock)block{
    self.copyValueBlock = block;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.navigationController.navigationBar.translucent = NO;
    
    self.dataArray = [[NSMutableArray alloc]init];
    [self.dataArray addObjectsFromArray:[GJUtils jsonToObject:self.attributeValues]];
        
    self.backBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 28, 28)];
    [self.backBtn.widthAnchor constraintEqualToConstant:28].active = YES;
    [self.backBtn.heightAnchor constraintEqualToConstant:28].active = YES;
    [self.backBtn setImage:[UIImage imageNamed:@"icon_return"] forState:UIControlStateNormal];
    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:self.backBtn];
    self.navigationItem.leftBarButtonItem = leftItem;
    [self.backBtn whenTapped:^{
        //搜索行为
        [self.navigationController popViewControllerAnimated:YES];
    }];
    
    [self initTableView];
}

- (void)initTableView {
    [self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    [self.tableView reloadData];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

#pragma mark - UITableViewDelegate/UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if(section == 0){
        return 1;
    }else if(section == 2){
        return 1;
    }
    return self.dataArray.count;
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
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 0.01)];
    return heaView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 0.01;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    
    if(indexPath.section == 0){
        RecordDetailsTopCell *recordDetailsTopCell = [RecordDetailsTopCell cellInTableView:tableView withIdentifier:identifier];
        recordDetailsTopCell.codeLabel.text = self.plantCode;
        recordDetailsTopCell.nameLabel.text = self.hybridName;
        recordDetailsTopCell.timeLabel.text = self.createTime;
        recordDetailsTopCell.phoneLabel.hidden = YES;
        recordDetailsTopCell.phoneBtn.hidden = YES;
        recordDetailsTopCell.photoTitleLabel.hidden = YES;
        
        [recordDetailsTopCell.printBtn whenTapped:^{
            LinkPrintViewController *linkPrintViewController = [[LinkPrintViewController alloc]init];
            linkPrintViewController.title = NSLocalizedString(@"ljbxbqj", @"");
            linkPrintViewController.hidesBottomBarWhenPushed = YES;
            linkPrintViewController.codeStr = self.plantCode;
            [self.navigationController pushViewController:linkPrintViewController animated:YES];
        }];
        return recordDetailsTopCell;
    }else if(indexPath.section == 2){
        RecordDetailsBottomCell *recordDetailsBottomCell = [RecordDetailsBottomCell cellInTableView:tableView withIdentifier:identifier];
        [recordDetailsBottomCell.deleteBtn setTitle:NSLocalizedString(@"onekeycopy", @"") forState:UIControlStateNormal];
        [recordDetailsBottomCell.deleteBtn whenTapped:^{
            //一键克隆
            UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:@"已填写的数据也会被覆盖，是否确认克隆历史数据？"
                                                                              preferredStyle:UIAlertControllerStyleAlert];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
                NSLog(@"取消了");
            }])];[alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"yes", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                //一键克隆
                if(self.copyValueBlock!=nil){
                    self.copyValueBlock(self.dataArray);
                }
                [self.navigationController popViewControllerAnimated:YES];
            }])];
            [self presentViewController:alertController animated:YES completion:nil];
        }];
        return recordDetailsBottomCell;
    }else{
        RecordDetailsCell *recordDetailsCell = [RecordDetailsCell cellInTableView:tableView withIdentifier:identifier];
        NSDictionary *dataDict = self.dataArray[indexPath.row];
        recordDetailsCell.titleLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
        recordDetailsCell.detailsLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"value"]];
        recordDetailsCell.gjBackgroundView.backgroundColor = [UIColor whiteColor];
        if(self.dataArray.count!=1){
            if(indexPath.row == 0){
                //上边圆角
                [recordDetailsCell.gjBackgroundView addRoundedCorners:UIRectCornerTopLeft|UIRectCornerTopRight withRadii:CGSizeMake(6.0, 6.0) viewRect:CGRectMake(0, 0, kSCREEN_WIDTH-32, 45)];
                recordDetailsCell.lineView.hidden = NO;
            }else if(indexPath.row == self.dataArray.count-1){
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

@end

