//
//  HomeSquareViewController.m
//  UinChat
//
//  Created by Apple on 2018/10/11.
//  Copyright © 2018年 GuJie. All rights reserved.
//

#import "HomeSquareViewController.h"
#import "RecordListCell.h"
#import "RecordDetailsViewController.h"
#import "HomeScheduleViewController.h"
#import "GJLBXScanViewController.h"
#import "Global.h"
#import "StyleDIY.h"
#import "WHPickerGoodsView.h"

#import "PopView.h"
#import "RecordChooseView.h"

#import "SearchPageViewController.h"

#import "LinkPrintViewController.h"

#import <CoreNFC/CoreNFC.h>

@interface HomeSquareViewController ()<UITableViewDelegate,UITableViewDataSource,NFCNDEFReaderSessionDelegate>{
    NSString *delayDay;
    NSString *plantId;
    
    NSString *hybridId;
    NSString *germplasmId;
    
    /**
     * 当前记录起始索引
     */
    int pageNum;

    /**
     * 每页显示记录数
     */
    int pageSize;
    
}

@property (weak, nonatomic) IBOutlet UIButton *scanBtn;
@property (weak, nonatomic) IBOutlet UITextField *scanTf;
@property (weak, nonatomic) IBOutlet UIButton *cleanBtn;
@property (weak, nonatomic) IBOutlet UIButton *createButton;
@property (weak, nonatomic) IBOutlet UIView *createButtonView;

@property (weak, nonatomic) IBOutlet UIButton *delayDayBtnOne;
@property (weak, nonatomic) IBOutlet UIButton *delayDayBtnTwo;
@property (weak, nonatomic) IBOutlet UIButton *delayDayBtnThree;
@property (weak, nonatomic) IBOutlet UIButton *delayDayBtnFour;
@property (weak, nonatomic) IBOutlet UIButton *moreSelectBtn;

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) NSMutableArray *dataArray;

@property (weak, nonatomic) IBOutlet UILabel *emptyDataView;

@property (nonatomic, strong) WHPickerGoodsView *pickerView;// 自定义的pickerview

//@property (weak, nonatomic) IBOutlet NSLayoutConstraint *inputButtonWidth;

/**
 筛选条件
 */
@property (strong, nonatomic) NSMutableDictionary *selectDict;

@property (strong, nonatomic) SearchPageViewController *searchVC;

@end

@implementation HomeSquareViewController

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
    
    UIButton *bluetoothBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 38, 38)];
    [bluetoothBtn.widthAnchor constraintEqualToConstant:38].active = YES;
    [bluetoothBtn.heightAnchor constraintEqualToConstant:38].active = YES;
    [bluetoothBtn setImage:[UIImage imageNamed:(SharedAppDelegate.isConnectedBLE ? @"icon_printer_state_success" : @"icon_printer_state_fail")] forState:UIControlStateNormal];
    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:bluetoothBtn];
    self.navigationItem.leftBarButtonItem = leftItem;
    [bluetoothBtn whenTapped:^{
        LinkPrintViewController *linkPrintViewController = [[LinkPrintViewController alloc]init];
        linkPrintViewController.title = NSLocalizedString(@"ljbxbqj", @"");
        linkPrintViewController.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:linkPrintViewController animated:YES];
    }];
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
    
//    self.inputButtonWidth.constant = SCREEN_WIDTH/8;
    
    
    if (@available(iOS 11.0, *)) {
        UIButton *scanBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 38, 38)];
        [scanBtn.widthAnchor constraintEqualToConstant:38].active = YES;
        [scanBtn.heightAnchor constraintEqualToConstant:38].active = YES;
        [scanBtn setImage:[UIImage imageNamed:@"icon_nfc"] forState:UIControlStateNormal];
        UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:scanBtn];
        self.navigationItem.rightBarButtonItem = rightItem;
        [scanBtn whenTapped:^{
            /*
                 条件：iphone7/7plus运行iOS11及以上
                 invalidateAfterFirstRead 属性表示是否需要识别多个NFC标签，
                 如果是YES，则会话会在第一次识别成功后终止。否则会话会持续。
                 不过有一种例外情况，就是如果响应了-readerSession:didInvalidateWithError:方法，
                 则是否为YES，会话都会被终止
                 */
            if (@available(iOS 11.0, *)) {
                NFCNDEFReaderSession *session =
                [[NFCNDEFReaderSession alloc] initWithDelegate:self
                                                         queue:dispatch_get_main_queue()
                                      invalidateAfterFirstRead:NO];
                // session.alertMessage = @"请";
                // 开始扫描
                [session beginSession];
            } else {
                // Fallback on earlier versions
                [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"system.low", @"")];
            }
        }];
    }
    
    
    self->pageNum = 1;
    self->pageSize = 10;
    
    self.dataArray = [[NSMutableArray alloc]init];
    [self initTableView];
    self->plantId = @"";
    self->hybridId = @"";
    self->germplasmId = @"";
    [self resetTitleTv:1];
    self.scanTf.placeholder = self.businessType == 0 ? NSLocalizedString(@"TabTitleOne.ScanTip", @"") : NSLocalizedString(@"TabTitleTwo.ScanTip", @"");
    
    [self.cleanBtn whenTapped:^{
        //清除条码
        self->plantId = @"";
        self->hybridId = @"";
        self->germplasmId = @"";
        self.scanTf.text = @"";
        self.cleanBtn.hidden = YES;
        if(self.selectDict != nil){
            self.selectDict = nil;
            [self resetTitleTv:1];
        }else{
            //刷新列表
            [self.tableView.mj_header beginRefreshing];
        }
    }];
    
    [self addShadowToView:self.createButton withColor:[UIColor blackColor]];
    [self.createButton whenTapped:^{
        //评价
        [self queryGroupList];
    }];
    [self.createButtonView whenTapped:^{
        //评价
        [self queryGroupList];
    }];
    
}

/// 添加四边阴影效果
- (void)addShadowToView:(UIView *)theView withColor:(UIColor *)theColor {
    // 阴影颜色
    theView.layer.shadowColor = theColor.CGColor;
    // 阴影偏移，默认(0, -3)
    theView.layer.shadowOffset = CGSizeMake(0,3);
    // 阴影透明度，默认0
    theView.layer.shadowOpacity = 0.5;
    // 阴影半径，默认3
    theView.layer.shadowRadius = 3;
}

#pragma mark - 查询属性分组管理
- (void)queryGroupList{
    __weak typeof(self) weakSelf = self;
    NSNumber *speciesId = kUserDefaults(SEPCIES_ID);
    if(speciesId.intValue != -1){
        [GJHttpTool GET:[NSString stringWithFormat:APP_SPECIES,speciesId.intValue] parameters:@{} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                [self querySpecies:[[responseObject objectForKey:@"data"] mutableCopy]];
                
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            NSLog(@"%@",error);
        }];
    }else{
        [GJHttpTool GET:APP_SPECIES_LIST parameters:@{} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                NSMutableArray *tmpArr = [[responseObject objectForKey:@"rows"] mutableCopy];
                weakSelf.pickerView = [[WHPickerGoodsView alloc]init:tmpArr andIndex:0 showTitleKey:@"name"];
                [weakSelf.pickerView didFinishSelected:^(NSInteger selectIndex) {
                    [self querySpecies:[[tmpArr objectAtIndex:selectIndex] mutableCopy]];
                }];
                
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            NSLog(@"%@",error);
        }];
    }
}

- (void)querySpecies:(NSMutableDictionary *)groupMain{
    HomeScheduleViewController *homeScheduleViewController = [[HomeScheduleViewController alloc]init];
    homeScheduleViewController.title = [NSString stringWithFormat:@"%@-%@",(self.businessType == 0 ? NSLocalizedString(@"zjpj", @"") : NSLocalizedString(@"zztb", @"")),[groupMain objectForKey:@"name"]];
    homeScheduleViewController.hidesBottomBarWhenPushed = YES;
    homeScheduleViewController.groupMain = groupMain;
    homeScheduleViewController.businessType = self.businessType;
    [self.navigationController pushViewController:homeScheduleViewController animated:YES];
}

/**
 搜索
 */
- (IBAction)searchBtnOnClick:(UIButton *)sender {
    SearchPageViewController *searchVC = [[SearchPageViewController alloc]init];
    searchVC.hidesBottomBarWhenPushed = YES;
    searchVC.businessType = self.businessType;
    searchVC.title = self.businessType == 0 ? NSLocalizedString(@"sszjpz", @"") : NSLocalizedString(@"sszzpz", @"");
    searchVC.searchResultValueBlock = ^(NSDictionary * _Nonnull result) {
        if(self.businessType == 0){
            //搜索杂交
            [GJHttpTool GET:APP_PLANT_INFO parameters:@{@"code":[result objectForKey:@"code"]} headerParams:nil awaitingView:self.view success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    //赋值列表
                    NSMutableDictionary *plantDict = [[responseObject objectForKey:@"data"] mutableCopy];
                    self.scanTf.text = [NSString stringWithFormat:@"%@",[plantDict objectForKey:@"code"]];
                    self->plantId = [NSString stringWithFormat:@"%d",[[plantDict objectForKey:@"id"] intValue]];
                    self.cleanBtn.hidden = NO;
                    //刷新列表
                    [self.tableView.mj_header beginRefreshing];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }else{
            //搜索种质
            self.scanTf.text = [NSString stringWithFormat:@"%@",[result objectForKey:@"name"]];
            self->hybridId = [NSString stringWithFormat:@"%d",[[result objectForKey:@"id"] intValue]];
            self.cleanBtn.hidden = NO;
            //刷新列表
            [self.tableView.mj_header beginRefreshing];
        }
    };
    [self.navigationController pushViewController:searchVC animated:YES];
}

/**
 扫码
 */
- (IBAction)scanBtnOnClick:(UIButton *)sender {
    GJLBXScanViewController *vc = [GJLBXScanViewController new];
    vc.hidesBottomBarWhenPushed = YES;
    vc.libraryType = [Global sharedManager].libraryType;
    vc.scanCodeType = [Global sharedManager].scanCodeType;
    vc.style = [StyleDIY notSquare];
    vc.title = NSLocalizedString(@"scan", @"");
    vc.isVideoZoom = YES;//镜头拉远拉近功能
    [vc setResultWithBlock:^(LBXScanResult * _Nullable resultAsString) {
        //查询公共库
        [GJHttpTool GET:self.businessType == 0 ? APP_PLANT_INFO : APP_SEEDLING_INFO parameters:@{@"code":resultAsString.strScanned} headerParams:nil awaitingView:self.view success:^(id responseObject) {
            /**
             * code : Z8-BMZ2-2601-1@1704-1
             * collectId : 10
             * isCollect : true
             * codeOne : Z8-BMZ2-2601-1
             * id : 1
             * codeTwo : 1704-1
             * hybridName : 美人酥x南水
             * nextCode : Z8-BMZ2-2601-2@1704-2
             * prevCode : Z8-BMZ2-2601-2@1704-2 
             */
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                //赋值列表
                NSMutableDictionary *plantDict = [[responseObject objectForKey:@"data"] mutableCopy];
                self.scanTf.text = [NSString stringWithFormat:@"%@",[plantDict objectForKey:@"code"]];
                if(self.businessType == 0){
                    self->plantId = [NSString stringWithFormat:@"%d",[[plantDict objectForKey:@"id"] intValue]];
                }else{
                    self->hybridId = [NSString stringWithFormat:@"%d",[[plantDict objectForKey:@"id"] intValue]];
                }
                self->germplasmId = @"";
                if(self.selectDict != nil){
                    self.selectDict = nil;
                    [self resetTitleTv:1];
                }
                [self.moreSelectBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
                [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_n"] forState:UIControlStateNormal];
                self.cleanBtn.hidden = NO;
                //刷新列表
                [self.tableView.mj_header beginRefreshing];
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            NSLog(@"%@",error);
        }];
    }];
    [self.navigationController pushViewController:vc animated:YES];
}


- (IBAction)delayDayBtnOneOnClick:(UIButton *)sender {
    [self resetTitleTv:1];
}
- (IBAction)delayDayBtnTwoOnClick:(UIButton *)sender {
    [self resetTitleTv:2];
}
- (IBAction)delayDayBtnThreeOnClick:(UIButton *)sender {
    [self resetTitleTv:3];
}
- (IBAction)delayDayBtnFourOnClick:(UIButton *)sender {
    [self resetTitleTv:4];
}
- (IBAction)moreSelectBtnOnClick:(UIButton *)sender {
    //弹出筛选条件
    RecordChooseView *recordChooseView = [[NSBundle mainBundle] loadNibNamed:@"RecordChooseView" owner:nil options:nil].firstObject;
    recordChooseView.frame = CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT);
    recordChooseView.backgroundColor = [UIColor clearColor];
    recordChooseView.businessType = self.businessType;
    recordChooseView.selectDict = self.selectDict;
    [recordChooseView returnValues:^(NSMutableDictionary * _Nonnull selectDataDict) {
        // 传回来的数据
        self.selectDict = [selectDataDict mutableCopy];
        //[self resetTitleTv:5];
        NSDictionary *dataDict = [self.selectDict objectForKey:@"data"];
        //[dataDict objectForKey:(self.businessType == 0 ? @"sowingCode" : @"code")],
        self.scanTf.text = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"name"]];
        self->plantId = @"";
        if(self.businessType == 0){
            self->hybridId = [NSString stringWithFormat:@"%d",[[dataDict objectForKey:@"id"] intValue]];
        }else{
            self->germplasmId = [NSString stringWithFormat:@"%d",[[dataDict objectForKey:@"id"] intValue]];
        }
        self.cleanBtn.hidden = NO;
        
        [self.moreSelectBtn setTitleColor:navigationBarColor forState:UIControlStateNormal];
        [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_s"] forState:UIControlStateNormal];
        //刷新列表
        [self.tableView.mj_header beginRefreshing];
    }];
    PopView *popView = [PopView popSideContentView:recordChooseView direct:PopViewDirection_SlideFromRight];
    popView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.0];
}


- (void)resetTitleTv:(int)indexDay{
    self.delayDayBtnOne.backgroundColor = [UIColor grayColor];
    self.delayDayBtnTwo.backgroundColor = [UIColor grayColor];
    self.delayDayBtnThree.backgroundColor = [UIColor grayColor];
    self.delayDayBtnFour.backgroundColor = [UIColor grayColor];
    [self.moreSelectBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
    [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_n"] forState:UIControlStateNormal];
    if(indexDay == 1){
        self->delayDay = @"";
        self.delayDayBtnOne.backgroundColor = navigationBarColor;
    }else if(indexDay == 2){
        self->delayDay = @"1";
        self.delayDayBtnTwo.backgroundColor = navigationBarColor;
    }else if(indexDay == 3){
        self->delayDay = @"3";
        self.delayDayBtnThree.backgroundColor = navigationBarColor;
    }else if(indexDay == 4){
        self->delayDay = @"4";
        self.delayDayBtnFour.backgroundColor = navigationBarColor;
    }
//    else if(indexDay == 5){
//        [self.moreSelectBtn setTitleColor:navigationBarColor forState:UIControlStateNormal];
//        [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_s"] forState:UIControlStateNormal];
//        return;
//    }
//    self.selectDict = nil;
//
//    self.scanTf.text = @"";
//    self->plantId = @"";
//    self.cleanBtn.hidden = YES;
    //刷新列表
    [self.tableView.mj_header beginRefreshing];
}

- (void)initTableView {
    //[self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    // 下拉刷新
    self.tableView.mj_header= [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        self->pageNum = 1;
        [self queryData];
    }];
    
    // 设置自动切换透明度(在导航栏下面自动隐藏)
    self.tableView.mj_header.automaticallyChangeAlpha = YES;
    // 上拉刷新
    self.tableView.mj_footer = [MJRefreshBackNormalFooter footerWithRefreshingBlock:^{
        self->pageNum++;
        [self queryData];
    }];
}

- (void)queryData{
    NSMutableDictionary *param = [[NSMutableDictionary alloc]init];
    if(!strIsEmpty(self -> delayDay)){
        [param setObject:self -> delayDay forKey:@"delayDay"];
    }
    if(!strIsEmpty(self -> plantId)){
        [param setObject:self -> plantId forKey:(self.businessType == 0 ? @"plantId" : @"SeedlingId")];
    }
    if(!strIsEmpty(self -> hybridId)){
        [param setObject:self -> hybridId forKey:@"hybridId"];
    }
    if(!strIsEmpty(self -> germplasmId)){
        [param setObject:self -> germplasmId forKey:@"germplasmId"];
    }

    [param setObject:[NSString stringWithFormat:@"%d",pageNum] forKey:@"pageNum"];
    [param setObject:[NSString stringWithFormat:@"%d",pageSize] forKey:@"pageSize"];
    
    [GJHttpTool GET:self.businessType == 0 ? APP_RECORD_LIST : APP_RECORD_LIST_1 parameters:param headerParams:@{} awaitingView:nil success:^(id responseObject) {
        [self.tableView.mj_header endRefreshing];
        [self.tableView.mj_footer endRefreshing];
        
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            //赋值列表
            NSArray *tmpArr = [NSArray array];
            if(self->pageNum == 1){
                [self.dataArray removeAllObjects];
            }
            tmpArr = [responseObject objectForKey:@"data"];
            
            if(tmpArr !=nil && tmpArr != [NSNull null] && tmpArr.count>0){
                [self.dataArray addObjectsFromArray:tmpArr];
                if(tmpArr.count < self->pageSize){
                    [self.tableView.mj_footer endRefreshingWithNoMoreData];
                }else{
                    [self.tableView.mj_footer resetNoMoreData];
                }
            }else{
                [self.tableView.mj_footer endRefreshingWithNoMoreData];
            }
            
            [self.tableView reloadData];
            
            self.emptyDataView.hidden = self.dataArray.count>0;
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
        
    } failure:^(NSError *error) {
        [self.tableView.mj_header endRefreshing];
        //[self.tableView.mj_footer endRefreshing];
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

// 扫描到的回调
-(void)readerSession:(NFCNDEFReaderSession *)session didDetectNDEFs:(NSArray<NFCNDEFMessage *> *)messages API_AVAILABLE(ios(11.0)){
    /*
     数组messages中是NFCNDEFMessage对象
     NFCNDEFMessage对象中有一个records数组，这个数组中是NFCNDEFPayload对象
     参考NFCNDEFMessage、NFCNDEFPayload类
     */
    NSMutableArray *dataStrArray = [[NSMutableArray alloc]init];
    for (NFCNDEFMessage *message in messages) {
        for (NFCNDEFPayload *record in message.records) {
            NSString *dataStr = [[NSString alloc] initWithData:record.payload
                                                      encoding:NSUTF8StringEncoding];
            if ([dataStr rangeOfString:@"zh"].location != NSNotFound || [dataStr rangeOfString:@"en"].location != NSNotFound){
                //干掉国家码
                dataStr = [dataStr substringFromIndex:3];
            }
            NSLog(@"扫描结果 ：%@", dataStr);
            [dataStrArray addObject:dataStr];
        }
    }
    // 主动终止会话，调用如下方法即可。
    [session invalidateSession];
    
//    dispatch_async(dispatch_get_main_queue(), ^{});
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        //[SVProgressHUD showSuccessWithStatus:[NSString stringWithFormat:@"扫描到%lu个结果!",(unsigned long)dataStrArray.count]];
        if(dataStrArray.count > 0){
            NSArray *lines = [dataStrArray[0] componentsSeparatedByString:@"\n"];
            NSString *nameString = [NSString stringWithFormat:@"%@",lines[0]];
            nameString = [nameString stringByReplacingOccurrencesOfString:NSLocalizedString(@"device.name", @"") withString:@""];
            nameString = [nameString stringByReplacingOccurrencesOfString:@" " withString:@""];
            
            UIAlertController *alert = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:[NSString stringWithFormat:@"%@:%@，%@",NSLocalizedString(@"scan.xp", @""),nameString,NSLocalizedString(@"isread", @"")] preferredStyle:UIAlertControllerStyleAlert];
            [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:nil]];
            [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"sure", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                //查询公共库
                [GJHttpTool GET:self.businessType == 0 ? APP_PLANT_INFO : APP_SEEDLING_INFO parameters:@{@"code":nameString} headerParams:nil awaitingView:self.view success:^(id responseObject) {
                    if([[responseObject objectForKey:@"code"] intValue] == 200){
                        //赋值列表
                        NSMutableDictionary *plantDict = [[responseObject objectForKey:@"data"] mutableCopy];
                        self.scanTf.text = [NSString stringWithFormat:@"%@",[plantDict objectForKey:@"code"]];
                        if(self.businessType == 0){
                            self->plantId = [NSString stringWithFormat:@"%d",[[plantDict objectForKey:@"id"] intValue]];
                        }else{
                            self->hybridId = [NSString stringWithFormat:@"%d",[[plantDict objectForKey:@"id"] intValue]];
                        }
                        self->germplasmId = @"";
                        if(self.selectDict != nil){
                            self.selectDict = nil;
                            [self resetTitleTv:1];
                        }
                        [self.moreSelectBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
                        [self.moreSelectBtn setImage:[UIImage imageNamed:@"icon_shuaixuan_n"] forState:UIControlStateNormal];
                        self.cleanBtn.hidden = NO;
                        //刷新列表
                        [self.tableView.mj_header beginRefreshing];
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
    });
}

// 错误回调
- (void)readerSession:(NFCNDEFReaderSession *)session didInvalidateWithError:(NSError *)error API_AVAILABLE(ios(11.0)){
    // 识别出现Error后会话会自动终止，此时就需要程序重新开启会话
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        if(error.code == NFCReaderErrorUnsupportedFeature){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"cannot", @"")];
        }else if(error.code == NFCReaderErrorSecurityViolation){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"safeanswer", @"")];
        }else if(error.code == NFCReaderTransceiveErrorTagConnectionLost){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"bqljds", @"")];
        }else if(error.code == NFCReaderTransceiveErrorRetryExceeded){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"clcsgd", @"")];
        }else if(error.code == NFCReaderTransceiveErrorTagResponseError){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"bqxycw", @"")];
        }else if(error.code == NFCReaderSessionInvalidationErrorSessionTimeout){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"hhsjcs", @"")];
        }else if(error.code == NFCReaderSessionInvalidationErrorSessionTerminatedUnexpectedly){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"hhywzz", @"")];
        }else if(error.code == NFCReaderSessionInvalidationErrorSystemIsBusy){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"xxzmhhsb", @"")];
        }else if(error.code == NFCReaderSessionInvalidationErrorFirstNDEFTagRead){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"dqdygndef", @"")];
        }else if(error.code == NFCTagCommandConfigurationErrorInvalidParameters){
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"bqpzwxcs", @"")];
        }else if(error.code == NFCReaderSessionInvalidationErrorUserCanceled){
            NSLog(NSLocalizedString(@"qxhh", @""));
        }
    });
}

@end
