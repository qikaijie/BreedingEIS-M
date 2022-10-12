//
//  MyCollectViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "MyCollectViewController.h"
#import "MyCollectCell.h"
#import "MyCollectListViewController.h"

#import "PopView.h"
#import "RecordChooseView.h"

@interface MyCollectViewController ()<UITableViewDelegate,UITableViewDataSource>{
    int groupIndex;
    int orderByInt;
    
    NSString *hybridId;
    NSString *germplasmId;
}
//
//@property (strong, nonatomic) UIButton *backBtn;
@property (weak, nonatomic) IBOutlet UIButton *moreSelectBtn;
@property (weak, nonatomic) IBOutlet UIView *selectView;
@property (weak, nonatomic) IBOutlet UILabel *selectLabel;
@property (weak, nonatomic) IBOutlet UIImageView *cleanSelectBtn;
/**
 筛选条件
 */
@property (strong, nonatomic) NSMutableDictionary *selectDict;

@property (weak, nonatomic) IBOutlet UIView *paixuView;
@property (weak, nonatomic) IBOutlet UIImageView *paixuIV;
@property (weak, nonatomic) IBOutlet UILabel *paixuLabel;

@property (strong, nonatomic) NSMutableArray *dataArray;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UILabel *emptyDataView;

@end

@implementation MyCollectViewController

- (instancetype)initGroupIndex:(int)index
{
    //1.初始化父类
    self = [super init];
    //2.判断父类是否初始化成功
    if(self)
    {
        //3.初始化子类
        self->groupIndex = index;
    }
    //4.返回地址
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
//    self.navigationController.navigationBar.translucent = NO;
        
//    self.backBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 28, 28)];
//    [self.backBtn.widthAnchor constraintEqualToConstant:28].active = YES;
//    [self.backBtn.heightAnchor constraintEqualToConstant:28].active = YES;
//    [self.backBtn setImage:[UIImage imageNamed:@"icon_return"] forState:UIControlStateNormal];
//    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:self.backBtn];
//    self.navigationItem.leftBarButtonItem = leftItem;
//    [self.backBtn whenTapped:^{
//        //搜索行为
//        [self.navigationController popViewControllerAnimated:YES];
//    }];
    
    self->hybridId = @"";
    self->germplasmId = @"";
    self.selectDict = nil;
    self->orderByInt = -1;
    self.selectView.hidden = YES;
    self->orderByInt = -1;
    [self.paixuIV setImage:[UIImage imageNamed:@"icon_paixu_defult"]];
    [self.paixuLabel setText:NSLocalizedString(@"mrpx", @"")];
    self.dataArray = [[NSMutableArray alloc]init];
    [self initTableView];
    
    [self.tableView.mj_header beginRefreshing];
    
    [self.cleanSelectBtn whenTapped:^{
        //清除
        self.selectView.hidden = YES;
        self->hybridId = @"";
        self->germplasmId = @"";
        self.selectDict = nil;
        [self.moreSelectBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
        [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_n"] forState:UIControlStateNormal];
        //刷新列表
        [self.tableView.mj_header beginRefreshing];
    }];
    
    [self.paixuView whenTapped:^{
        //排序
        [self setOrderBy];
    }];
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
    NSMutableDictionary *param = [[NSMutableDictionary alloc]init];
    if(!strIsEmpty(self -> hybridId)){
        [param setObject:self -> hybridId forKey:@"hybridId"];
    }
    if(!strIsEmpty(self -> germplasmId)){
        [param setObject:self -> germplasmId forKey:@"germplasmId"];
    }
    if(self->orderByInt != -1){
        [param setObject:@"codeTwo" forKey:@"prop"];
        [param setObject:(self->orderByInt == 0 ? @"ascending" : @"descending") forKey:@"order"];
    }
    [GJHttpTool GET:self->groupIndex == 0 ? APP_COLLECT_LIST : APP_SEEDLING_COLLECT_LIST parameters:param headerParams:@{} awaitingView:nil success:^(id responseObject) {
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

/**
 排序
 */
- (void)setOrderBy{
    if(self->orderByInt == -1){
        self->orderByInt = 0;
        [self.paixuIV setImage:[UIImage imageNamed:@"icon_paixu_sheng"]];
        [self.paixuLabel setText:NSLocalizedString(@"dmsx", @"")];
    }else if(self->orderByInt == 0){
        self->orderByInt = 1;
        [self.paixuIV setImage:[UIImage imageNamed:@"icon_paixu_jiang"]];
        [self.paixuLabel setText:NSLocalizedString(@"dmjx", @"")];
    }else if(self->orderByInt == 1){
        self->orderByInt = -1;
        [self.paixuIV setImage:[UIImage imageNamed:@"icon_paixu_defult"]];
        [self.paixuLabel setText:NSLocalizedString(@"mrpx", @"")];
    }
    [self.tableView.mj_header beginRefreshing];
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
    return 72;
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
    
    MyCollectCell *myCollectCell = [MyCollectCell cellInTableView:tableView withIdentifier:identifier];
    
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    
    if(self->groupIndex == 0){
        myCollectCell.codeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"plantCode"]];
        myCollectCell.timeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"createTime"]];
        [myCollectCell.levelImageView setImage:[UIImage imageNamed:[[dataDict objectForKey:@"level"] intValue] == 1 ? @"icon_sc_s" : @"icon_sc_ss"]];
    }else{
        myCollectCell.codeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"seedlingCode"]];
        myCollectCell.timeLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"createTime"]];
        [myCollectCell.levelImageView setImage:[UIImage imageNamed:[[dataDict objectForKey:@"level"] intValue] == 1 ? @"icon_sc_s" : @"icon_sc_ss"]];
    }
    
    /**
     收藏级别
     */
    [myCollectCell.levelView whenTapped:^{
        NSMutableDictionary *param = [self.dataArray[indexPath.row] mutableCopy];
        NSMutableDictionary *params = [[NSMutableDictionary alloc]init];
        int oldlevel = [[param objectForKey:@"level"] intValue];
        int newlevel = oldlevel==1 ? 2 : 1;
        [params setObject:[NSNumber numberWithInt:newlevel] forKey:@"level"];
        if(self->groupIndex == 0){
            [params setObject:[NSNumber numberWithInt:[[param objectForKey:@"plantId"] intValue]] forKey:@"plantId"];
        }else{
            [params setObject:[NSNumber numberWithInt:[[param objectForKey:@"seedlingId"] intValue]] forKey:@"seedlingId"];
        }
        
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:[NSString stringWithFormat:@"确认转%@收藏吗？",oldlevel==1 ? NSLocalizedString(@"level.two", @"") : NSLocalizedString(@"level.one", @"")] preferredStyle:UIAlertControllerStyleAlert];
        [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:nil]];
        [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"sure", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            [GJHttpTool POST:self->groupIndex == 0 ? APP_PLANT_COLLECT_ADD : APP_SEEDLING_COLLECT_ADD parameters:params headerParams:@{} awaitingView:nil constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {

            } success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    [param setObject:[NSNumber numberWithInt:newlevel] forKey:@"level"];
                    [myCollectCell.levelImageView setImage:[UIImage imageNamed:newlevel == 1 ? @"icon_sc_s" : @"icon_sc_ss"]];
                    [self.dataArray replaceObjectAtIndex:indexPath.row withObject:param];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }]];
        // 弹出对话框
        [self.navigationController presentViewController:alert animated:true completion:nil];
    }];
    
    return myCollectCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
    
    //查询会员详情
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    
    MyCollectListViewController *myCollectListViewController = [[MyCollectListViewController alloc]init];
    myCollectListViewController.title = NSLocalizedString(@"tbjl", @"");
    myCollectListViewController.collectId = [[dataDict objectForKey:@"id"] integerValue];
    if(self->groupIndex == 0){
        myCollectListViewController.plantId = [[dataDict objectForKey:@"plantId"] integerValue];
    }else{
        myCollectListViewController.seedlingId = [[dataDict objectForKey:@"seedlingId"] integerValue];
    }
    myCollectListViewController.businessType = self->groupIndex;
    [myCollectListViewController collectDelBlockValues:^(BOOL isDel) {
        if(isDel){
            //删除
            [self.tableView beginUpdates];
            [self.dataArray removeObjectAtIndex:indexPath.row];
            [self.tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationLeft];
            [self.tableView endUpdates];
        }
    }];
    [self.navigationController pushViewController:myCollectListViewController animated:YES];
}


- (IBAction)moreSelectBtnOnClick:(UIButton *)sender {
    //弹出筛选条件
    RecordChooseView *recordChooseView = [[NSBundle mainBundle] loadNibNamed:@"RecordChooseView" owner:nil options:nil].firstObject;
    recordChooseView.frame = CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT);
    recordChooseView.backgroundColor = [UIColor clearColor];
    recordChooseView.businessType = self->groupIndex;
    recordChooseView.selectDict = self.selectDict;
    [recordChooseView returnValues:^(NSMutableDictionary * _Nonnull selectDataDict) {
        // 传回来的数据
        self.selectDict = [selectDataDict mutableCopy];
        //[self resetTitleTv:5];
        NSDictionary *dataDict = [self.selectDict objectForKey:@"data"];
        //[dataDict objectForKey:(self.businessType == 0 ? @"sowingCode" : @"code")],
        self.selectLabel.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
        if(self->groupIndex == 0){
            self->hybridId = [NSString stringWithFormat:@"%d",[[dataDict objectForKey:@"id"] intValue]];
        }else{
            self->germplasmId = [NSString stringWithFormat:@"%d",[[dataDict objectForKey:@"id"] intValue]];
        }
        self.selectView.hidden = NO;
        
        [self.moreSelectBtn setTitleColor:navigationBarColor forState:UIControlStateNormal];
        [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_s"] forState:UIControlStateNormal];
        //刷新列表
        [self.tableView.mj_header beginRefreshing];
    }];
    PopView *popView = [PopView popSideContentView:recordChooseView direct:PopViewDirection_SlideFromRight];
    popView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.0];
}


@end
