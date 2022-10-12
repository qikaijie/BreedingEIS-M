//
//  MyCollectListViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "MyCollectListViewController.h"
#import "RecordListCell.h"
#import "RecordDetailsViewController.h"

@interface MyCollectListViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UIButton *backBtn;

@property (strong, nonatomic) NSMutableArray *dataArray;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UILabel *emptyDataView;

@end

@implementation MyCollectListViewController

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

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.navigationController.navigationBar.translucent = NO;
        
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
    
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"cancel.collect", @"") style:UIBarButtonItemStylePlain target:self action:@selector(delCollect)];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    self.dataArray = [[NSMutableArray alloc]init];
    [self initTableView];
    
    [self.tableView.mj_header beginRefreshing];
}

- (void)collectDelBlockValues:(CollectDelBlock)block{
    self.collectDelBlock = block;
}

#pragma mark - 取消收藏
- (void)delCollect {
    //删除
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:NSLocalizedString(@"qrqxsc", @"") preferredStyle:UIAlertControllerStyleAlert];
    [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:nil]];
    [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"sure", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [GJHttpTool GET:self.businessType == 0 ? APP_PLANT_COLLECT_DEL : APP_SEEDLING_COLLECT_DEL parameters:@{@"id":[NSNumber numberWithInteger:self.collectId]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                if(self.collectDelBlock != nil){
                    self.collectDelBlock(YES);
                }
                [self.navigationController popViewControllerAnimated:YES];
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            NSLog(@"%@",error);
        }];
    }]];
    // 弹出对话框
    [self.navigationController presentViewController:alert animated:true completion:nil];
}

- (void)initTableView {
    [self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    // 下拉刷新
    self.tableView.mj_header= [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        [self queryData];
    }];
    
    // 设置自动切换透明度(在导航栏下面自动隐藏)
    self.tableView.mj_header.automaticallyChangeAlpha = YES;
}

- (void)queryData{
    NSMutableDictionary *params = [[NSMutableDictionary alloc]init];
    if(self.businessType == 0){
        [params setObject:[NSNumber numberWithInteger:self.plantId] forKey:@"plantId"];
    }else{
        [params setObject:[NSNumber numberWithInteger:self.seedlingId] forKey:@"seedlingId"];
    }
    [GJHttpTool GET:self.businessType == 0 ? APP_PLANT_RECORD : APP_PLANT_RECORD_1 parameters:params headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            [self.tableView.mj_header endRefreshing];
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                //赋值列表
                NSArray *tmpArr = [NSArray array];
                [self.dataArray removeAllObjects];
                tmpArr = [responseObject objectForKey:@"data"];
                [self.dataArray addObjectsFromArray:tmpArr];
                [self.tableView.mj_footer resetNoMoreData];
                [self.tableView reloadData];
                
                self.emptyDataView.hidden = self.dataArray.count>0;
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            [self.tableView.mj_header endRefreshing];
            NSLog(@"%@",error);
        }];
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
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 100;
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
    
    RecordListCell *recordListCell = [RecordListCell cellInTableView:tableView withIdentifier:identifier];
    
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    
    if(self.businessType == 0){
        recordListCell.codeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"plantCode"]];
        recordListCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"hybridName"]];
        recordListCell.timeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"createTime"]];
    }else{
        recordListCell.codeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"seedlingCode"]];
        recordListCell.nameLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"germplasmName"]];
        recordListCell.timeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"createTime"]];
    }
    
    return recordListCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
    
    //查询会员详情
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    
    RecordDetailsViewController *recordDetailsViewController = [[RecordDetailsViewController alloc]init];
    recordDetailsViewController.title = NSLocalizedString(@"record.detain", @"");
    recordDetailsViewController.businessType = self.businessType;
    
    if(self.businessType == 0){
        recordDetailsViewController.plantId = [[dataDict objectForKey:@"id"] integerValue];
        recordDetailsViewController.hybridName = [dataDict objectForKey:@"hybridName"];
        recordDetailsViewController.plantCode = [dataDict objectForKey:@"plantCode"];
    }else{
        recordDetailsViewController.seedlingId = [[dataDict objectForKey:@"id"] integerValue];
        recordDetailsViewController.germplasmName = [dataDict objectForKey:@"germplasmName"];
        recordDetailsViewController.seedlingCode = [dataDict objectForKey:@"seedlingCode"];
    }
    
    recordDetailsViewController.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:recordDetailsViewController animated:YES];
}


@end

