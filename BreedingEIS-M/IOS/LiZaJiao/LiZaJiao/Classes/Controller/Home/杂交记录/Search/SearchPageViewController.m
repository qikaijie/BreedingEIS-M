//
//  SearchPageViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/21.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "SearchPageViewController.h"
#import "RecordListCell.h"

@interface SearchPageViewController ()<UISearchBarDelegate,UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UIButton *backBtn;

@property (weak, nonatomic) IBOutlet UISearchBar *searchBar;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UILabel *emptyLabel;
@property (strong, nonatomic) NSMutableArray *searchList;//满足搜索条件的数组

@end

@implementation SearchPageViewController

- (void)returnValues:(SearchResultValueBlock)block{
    self.searchResultValueBlock = block;
}

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
    
    self.searchBar.placeholder = self.businessType == 0 ? NSLocalizedString(@"qsryssdzjbm", @"") : NSLocalizedString(@"qsryssdzzzw", @"");
        
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
    
    self.searchList = [[NSMutableArray alloc]init];
    self.searchBar.delegate = self;
    
    [self initTableView];
}

- (void)initTableView {
    [self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
}

#pragma mark - UITableViewDelegate/UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.searchList.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 50;
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
    
    UITableViewCell *recordListCell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
    NSDictionary *searchDict = self.searchList[indexPath.row];
    if(self.businessType == 0){
        //搜索杂交
        recordListCell.textLabel.text = [NSString stringWithFormat:@"%@",[searchDict objectForKey:@"code"]];
    }else{
        //搜索种质
        recordListCell.textLabel.text = [NSString stringWithFormat:@"%@",[searchDict objectForKey:@"name"]];
    }
    
    return recordListCell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
    
    if(self.searchResultValueBlock){
        self.searchResultValueBlock(self.searchList[indexPath.row]);
    }
    
    [self.navigationController popViewControllerAnimated:YES];
}


- (void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
    if(!strIsEmpty(self.searchBar.text)){
        [searchBar resignFirstResponder];
        if(self.businessType == 0){
            //搜索杂交
            [GJHttpTool GET:APP_PLANT_LIST_BY_CODE parameters:@{@"code":self.searchBar.text} headerParams:nil awaitingView:nil success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    //赋值列表
                    NSMutableArray *array = [[responseObject objectForKey:@"data"] mutableCopy];
                    [self.searchList removeAllObjects];
                    if(array != nil && array.count>0){
                        self.tableView.hidden = NO;
                        self.emptyLabel.hidden = YES;
                        [self.searchList addObjectsFromArray:array];
                    }else{
                        self.tableView.hidden = YES;
                        self.emptyLabel.hidden = NO;
                    }
                    //刷新列表
                    [self.tableView reloadData];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }else{
            //搜索种质
            [GJHttpTool GET:APP_GERMPLASM_LIST_BY_NAME parameters:@{@"name":self.searchBar.text} headerParams:nil awaitingView:nil success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    //赋值列表
                    NSMutableArray *array = [[responseObject objectForKey:@"data"] mutableCopy];
                    [self.searchList removeAllObjects];
                    if(array != nil && array.count>0){
                        self.tableView.hidden = NO;
                        self.emptyLabel.hidden = YES;
                        [self.searchList addObjectsFromArray:array];
                    }else{
                        self.tableView.hidden = YES;
                        self.emptyLabel.hidden = NO;
                    }
                    //刷新列表
                    [self.tableView reloadData];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }
    }else{
        [self.view toast:NSLocalizedString(@"ssnrbnwk", @"")];
    }
}

@end

