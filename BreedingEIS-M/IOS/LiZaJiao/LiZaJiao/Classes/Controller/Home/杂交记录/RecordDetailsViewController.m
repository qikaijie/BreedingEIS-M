//
//  RecordDetailsViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/15.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "RecordDetailsViewController.h"
#import "RecordDetailsCell.h"
#import "RecordDetailsTopCell.h"
#import "RecordDetailsBottomCell.h"
#import "PerviewFilesViewController.h"
#import "LinkPrintViewController.h"

@interface RecordDetailsViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UIButton *backBtn;

@property (strong,nonatomic) NSMutableDictionary *detailsDict;
@property (strong, nonatomic) NSMutableArray *dataArray;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation RecordDetailsViewController

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
    
    self.dataArray = [[NSMutableArray alloc]init];
    [self initTableView];
    
    [self queryData];
}

- (void)initTableView {
    [self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
}

- (void)queryData{
    [GJHttpTool GET:(self.businessType == 0 ? APP_RECORD_INFO : APP_RECORD_INFO_1) parameters:@{@"id":[NSNumber numberWithInteger:(self.businessType == 0 ? self.plantId : self.seedlingId)]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            //赋值列表
            self.detailsDict = [[responseObject objectForKey:@"data"] mutableCopy];
            NSArray *tmpArr = [NSArray array];
            [self.dataArray removeAllObjects];
            if(self.businessType == 0){
                tmpArr = [self.detailsDict objectForKey:@"logList"];
            }else{
                NSString *attributeValues = [NSString stringWithFormat:@"%@",[self.detailsDict objectForKey:@"attributeValues"]];
                tmpArr = [GJUtils jsonToObject:attributeValues];
            }
            [self.dataArray addObjectsFromArray:tmpArr];
            
            [self.tableView reloadData];
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
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
        return 152;
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
    if(self.businessType == 0){
        //杂质详情
        if(indexPath.section == 0){
            RecordDetailsTopCell *recordDetailsTopCell = [RecordDetailsTopCell cellInTableView:tableView withIdentifier:identifier];
            recordDetailsTopCell.codeLabel.text = self.plantCode;
            recordDetailsTopCell.nameLabel.text = self.hybridName;
            
            [recordDetailsTopCell.printBtn whenTapped:^{
                LinkPrintViewController *linkPrintViewController = [[LinkPrintViewController alloc]init];
                linkPrintViewController.title = NSLocalizedString(@"ljbxbqj", @"");
                linkPrintViewController.hidesBottomBarWhenPushed = YES;
                linkPrintViewController.codeStr = self.plantCode;
                [self.navigationController pushViewController:linkPrintViewController animated:YES];
            }];
            if(self.detailsDict != nil){
                recordDetailsTopCell.timeLabel.text = [self.detailsDict objectForKey:@"createTime"];
                NSMutableArray *imgList = [[self.detailsDict objectForKey:@"imgList"] mutableCopy];
                if(imgList != nil && imgList != [NSNull null] && imgList.count>0){
                    recordDetailsTopCell.phoneLabel.hidden = YES;
                    recordDetailsTopCell.phoneBtn.hidden = NO;
                    [recordDetailsTopCell.phoneBtn whenTapped:^{
                        //预览图片与视频
                        PerviewFilesViewController *perviewFilesViewController = [[PerviewFilesViewController alloc]init];
                        perviewFilesViewController.title = NSLocalizedString(@"fileinfo", @"");
                        perviewFilesViewController.hidesBottomBarWhenPushed = YES;
                        perviewFilesViewController.imageList = [imgList mutableCopy];
                        [self.navigationController pushViewController:perviewFilesViewController animated:YES];
                    }];
                }else{
                    recordDetailsTopCell.phoneLabel.hidden = NO;
                    recordDetailsTopCell.phoneBtn.hidden = YES;
                }
            }
            return recordDetailsTopCell;
        }else if(indexPath.section == 2){
            RecordDetailsBottomCell *recordDetailsBottomCell = [RecordDetailsBottomCell cellInTableView:tableView withIdentifier:identifier];
            [recordDetailsBottomCell.deleteBtn whenTapped:^{
                //删除
                UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:@"是否确认删除该条记录"
                                                                                  preferredStyle:UIAlertControllerStyleAlert];
                [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
                    NSLog(@"取消了");
                }])];[alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"yes", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                    [GJHttpTool GET:APP_RECORD_DELETE parameters:@{@"id":[NSNumber numberWithInteger:self.plantId]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
                        if([[responseObject objectForKey:@"code"] intValue] == 200){
                            [SVProgressHUD showSuccessWithStatus:NSLocalizedString(@"delete.success", @"")];
                            [self.navigationController popViewControllerAnimated:YES];
                        }else{
                            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                        }
                    } failure:^(NSError *error) {
                        NSLog(@"%@",error);
                    }];
                }])];
                [self presentViewController:alertController animated:YES completion:nil];
            }];
            return recordDetailsBottomCell;
        }else{
            RecordDetailsCell *recordDetailsCell = [RecordDetailsCell cellInTableView:tableView withIdentifier:identifier];
            NSDictionary *dataDict = self.dataArray[indexPath.row];
            recordDetailsCell.titleLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"attributeName"]];
            recordDetailsCell.detailsLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"attributeValue"]];
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
    }else{
        //种质详情
        if(indexPath.section == 0){
            RecordDetailsTopCell *recordDetailsTopCell = [RecordDetailsTopCell cellInTableView:tableView withIdentifier:identifier];
            recordDetailsTopCell.codeLabel.text = self.seedlingCode;
            recordDetailsTopCell.nameLabel.text = self.germplasmName;
            
            [recordDetailsTopCell.printBtn whenTapped:^{
                LinkPrintViewController *linkPrintViewController = [[LinkPrintViewController alloc]init];
                linkPrintViewController.title = NSLocalizedString(@"ljbxbqj", @"");
                linkPrintViewController.hidesBottomBarWhenPushed = YES;
                linkPrintViewController.codeStr = self.seedlingCode;
                [self.navigationController pushViewController:linkPrintViewController animated:YES];
            }];
            
            if(self.detailsDict != nil){
                recordDetailsTopCell.timeLabel.text = [self.detailsDict objectForKey:@"createTime"];
                NSMutableArray *imgList = [[self.detailsDict objectForKey:@"imgList"] mutableCopy];
                if(imgList != nil && imgList != [NSNull null] && imgList.count>0){
                    recordDetailsTopCell.phoneLabel.hidden = YES;
                    recordDetailsTopCell.phoneBtn.hidden = NO;
                    [recordDetailsTopCell.phoneBtn whenTapped:^{
                        //预览图片与视频
                        PerviewFilesViewController *perviewFilesViewController = [[PerviewFilesViewController alloc]init];
                        perviewFilesViewController.title = NSLocalizedString(@"fileinfo", @"");
                        perviewFilesViewController.hidesBottomBarWhenPushed = YES;
                        perviewFilesViewController.imageList = [imgList mutableCopy];
                        [self.navigationController pushViewController:perviewFilesViewController animated:YES];
                    }];
                }else{
                    recordDetailsTopCell.phoneLabel.hidden = NO;
                    recordDetailsTopCell.phoneBtn.hidden = YES;
                }
            }
            return recordDetailsTopCell;
        }else if(indexPath.section == 2){
            RecordDetailsBottomCell *recordDetailsBottomCell = [RecordDetailsBottomCell cellInTableView:tableView withIdentifier:identifier];
            [recordDetailsBottomCell.deleteBtn whenTapped:^{
                //删除
                UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:@"是否确认删除该条记录"
                                                                                  preferredStyle:UIAlertControllerStyleAlert];
                [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
                    NSLog(@"取消了");
                }])];[alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"yes", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                    [GJHttpTool GET:APP_RECORD_DELETE_1 parameters:@{@"id":[NSNumber numberWithInteger:self.seedlingId]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
                        if([[responseObject objectForKey:@"code"] intValue] == 200){
                            [SVProgressHUD showSuccessWithStatus:NSLocalizedString(@"delete.success", @"")];
                            [self.navigationController popViewControllerAnimated:YES];
                        }else{
                            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                        }
                    } failure:^(NSError *error) {
                        NSLog(@"%@",error);
                    }];
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
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
}

@end
