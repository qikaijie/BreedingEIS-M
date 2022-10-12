//
//  PJDetailsViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "PJDetailsViewController.h"
#import "PJDetailsCellSelect.h"
#import "PJDetailsCellSelectDate.h"
#import "PJDetailsCellSelectInput.h"
#import "PJHistoryViewController.h"

#import "GJLBXScanViewController.h"
#import "Global.h"
#import "StyleDIY.h"

#import "WSDatePickerView.h"

#import "HistoryRecordDetailsViewController.h"

#import "UpdatingFilesViewController.h"

#import "EasyShowView.h"

#import <CoreNFC/CoreNFC.h>

@interface PJDetailsViewController ()<UITableViewDelegate,UITableViewDataSource,NFCNDEFReaderSessionDelegate>{
    Boolean isCollect;
    Boolean isUploadResult;
}

@property (strong, nonatomic) UIButton *backBtn;

@property (weak, nonatomic) IBOutlet UIButton *scanBtn;
@property (weak, nonatomic) IBOutlet UITextField *scanTf;
@property (weak, nonatomic) IBOutlet UIButton *collectBtn;

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) NSMutableArray *dataArray;

@property (strong, nonatomic) NSMutableDictionary *plantInfoBean;

@property (weak, nonatomic) IBOutlet UILabel *photoNumTv;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *photoNumTvWidth;

/**
 图片文件
 */
@property (strong, nonatomic) NSMutableArray *uploadedDataList;

@property (weak, nonatomic) IBOutlet UIButton *sumbitBtn;

@end

@implementation PJDetailsViewController

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
    
    int imageNum = (int)self.uploadedDataList.count;
    if(imageNum != 0){
        self.photoNumTv.hidden = NO;
        NSString *imageNumStr = [NSString stringWithFormat:@"%d",imageNum];
        float labelWidth = [imageNumStr boundingRectWithSize:CGSizeMake(MAXFLOAT, 0.0) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName : [UIFont systemFontOfSize:10.0]} context:nil].size.width+16;
        self.photoNumTv.text = imageNumStr;
        self.photoNumTvWidth.constant = labelWidth > 18 ? labelWidth : 18;
    }else{
        self.photoNumTv.hidden = YES;
    }
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

    isCollect = NO;
    isUploadResult = false;
    self.uploadedDataList = [[NSMutableArray alloc]init];
        
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
    
    [self initTableView];

        
    [[NSNotificationCenter defaultCenter]addObserver:self
                                            selector:@selector(keyboardWillShow:)
                                                name:UIKeyboardWillShowNotification
                                              object:nil];
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification
                                              object:nil];
}

- (void)initTableView {
    [self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    self.tableView.keyboardDismissMode = UIScrollViewKeyboardDismissModeOnDrag;
}

-(void)keyboardWillShow:(NSNotification*)note
{
    if ([[[UIDevice currentDevice] systemVersion] floatValue]>=12.0) {
        CGRect keyBoardRect=[note.userInfo[UIKeyboardFrameEndUserInfoKey] CGRectValue];
        _tableView.contentInset = UIEdgeInsetsMake(0,0,keyBoardRect.size.height-35,0);
    }
    
}

#pragma mark 键盘消失
-(void)keyboardWillHide:(NSNotification*)note
{
    if ([[[UIDevice currentDevice] systemVersion] floatValue]>=12.0) {
        _tableView.contentInset = UIEdgeInsetsZero;
    }
    
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
        self->isCollect = NO;
        [self queryCodeInfo:resultAsString.strScanned];
    }];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark -查询种质资源名称库
- (void)queryCodeInfo:(NSString *)code{
    [GJHttpTool GET:APP_PLANT_INFO parameters:@{@"code":code} headerParams:nil awaitingView:self.view success:^(id responseObject) {
        /**
         * code : Z8-BMZ2-2601-1@1704-1
         * collectId : 10
         * isCollect : true
         * codeOne : Z8-BMZ2-2601-1
         * id : 1
         * codeTwo : 1704-1
         * hybridName : 美人酥x南水
         * nextCode : Z8-BMZ2-2601-2@1704-2
         */
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            
            [self.sumbitBtn setBackgroundColor:navigationBarColor];
            //赋值列表
            self.plantInfoBean = [[NSMutableDictionary alloc]init];
            self.plantInfoBean = [[responseObject objectForKey:@"data"] mutableCopy];
            self.scanTf.text = [NSString stringWithFormat:@"%@ %@",[self.plantInfoBean objectForKey:@"code"],[self.plantInfoBean objectForKey:@"hybridName"]];
            bool isCollect = [[self.plantInfoBean objectForKey:@"isCollect"] boolValue];
            
            //是否被收藏
            [self.collectBtn setImage:[UIImage imageNamed:(isCollect ? ([[self.plantInfoBean objectForKey:@"collectLevel"] intValue]==1 ? @"icon_sc_s" : @"icon_sc_ss") : @"icon_sc")] forState:UIControlStateNormal];

            if(!self->isCollect){
                //清空图片
                [self.uploadedDataList removeAllObjects];
                self.photoNumTv.hidden = YES;
            }
            self->isCollect = NO;
            
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
    }];
}

#pragma mark -收藏
- (IBAction)collectBtnOnClick:(UIButton *)sender {
    if(self.plantInfoBean != nil){
        NSMutableDictionary *params = [[NSMutableDictionary alloc]init];
        [params setObject:[NSNumber numberWithInt:[[self.plantInfoBean objectForKey:@"id"] intValue]] forKey:@"plantId"];
        bool isCollect = [[self.plantInfoBean objectForKey:@"isCollect"] boolValue];
        if(isCollect){
            //判断是不是二级收藏
            if([[self.plantInfoBean objectForKey:@"collectLevel"] intValue] == 2){
                //取消收藏
                [GJHttpTool GET:APP_PLANT_COLLECT_DEL parameters:@{@"id":[NSNumber numberWithInteger:[[self.plantInfoBean objectForKey:@"collectId"] intValue]]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
                    if([[responseObject objectForKey:@"code"] intValue] == 200){
                        //没有收藏
                        [SVProgressHUD showSuccessWithStatus:NSLocalizedString(@"cancel.collect", @"")];
                        self->isCollect = YES;
                        [self queryCodeInfo:[self.plantInfoBean objectForKey:@"code"]];
                    }else{
                        [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                    }
                } failure:^(NSError *error) {
                    NSLog(@"%@",error);
                }];
            }else{
                //转二级收藏
                [params setObject:[NSNumber numberWithInt:2] forKey:@"level"];
                [GJHttpTool POST:APP_PLANT_COLLECT_ADD parameters:params headerParams:@{} awaitingView:nil constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
                    
                } success:^(id responseObject) {
                    if([[responseObject objectForKey:@"code"] intValue] == 200){
                        [SVProgressHUD showSuccessWithStatus:NSLocalizedString(@"two.collect", @"")];
                        self->isCollect = YES;
                        [self queryCodeInfo:[self.plantInfoBean objectForKey:@"code"]];
                    }else{
                        [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                    }
                } failure:^(NSError *error) {
                    NSLog(@"%@",error);
                }];
            }
        }else{
            //转一级收藏
            [params setObject:[NSNumber numberWithInt:1] forKey:@"level"];
            [GJHttpTool POST:APP_PLANT_COLLECT_ADD parameters:params headerParams:@{} awaitingView:nil constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
                
            } success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    [SVProgressHUD showSuccessWithStatus:NSLocalizedString(@"one.collect", @"")];
                    self->isCollect = YES;
                    [self queryCodeInfo:[self.plantInfoBean objectForKey:@"code"]];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }
    }else{
        [self.view toast:NSLocalizedString(@"qxsmxxzjzh", @"")];
    }
}

#pragma mark -上一条
- (IBAction)prevCodeOnClick:(UIButton *)sender {
    if(self.plantInfoBean != nil){
        if(!strIsEmpty([self.plantInfoBean objectForKey:@"prevCode"])){
            if(!self->isUploadResult){
                UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:NSLocalizedString(@"dqbdzwtj.s", @"")
                                                                                  preferredStyle:UIAlertControllerStyleAlert];
                [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"out", @"") style:UIAlertActionStyleDestructive handler:^(UIAlertAction * _Nonnull action) {
                    self->isUploadResult = false;
                    [self queryCodeInfo:[self.plantInfoBean objectForKey:@"prevCode"]];
                }])];
                [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"sendnow", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                    [self sumbitDataOnClick:self.sumbitBtn];
                }])];
                [self presentViewController:alertController animated:YES completion:nil];
            }else{
                self->isUploadResult = false;
                [self queryCodeInfo:[self.plantInfoBean objectForKey:@"prevCode"]];
            }
        }else{
            [self.view toast:NSLocalizedString(@"emptyforshang", @"")];
        }
    }else{
        [self.view toast:NSLocalizedString(@"qxsmxxzjzh", @"")];
    }
}

#pragma mark -下一条
- (IBAction)nextCodeOnClick:(UIButton *)sender {
    if(self.plantInfoBean != nil){
        if(!strIsEmpty([self.plantInfoBean objectForKey:@"nextCode"])){
            if(!self->isUploadResult){
                UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:NSLocalizedString(@"dqbdzwtj.n", @"")
                                                                                  preferredStyle:UIAlertControllerStyleAlert];
                [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"out", @"") style:UIAlertActionStyleDestructive handler:^(UIAlertAction * _Nonnull action) {
                    self->isUploadResult = false;
                    [self queryCodeInfo:[self.plantInfoBean objectForKey:@"nextCode"]];
                }])];
                [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"sendnow", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                    [self sumbitDataOnClick:self.sumbitBtn];
                }])];
                [self presentViewController:alertController animated:YES completion:nil];
            }else{
                self->isUploadResult = false;
                [self queryCodeInfo:[self.plantInfoBean objectForKey:@"nextCode"]];
            }
        }else{
            [self.view toast:NSLocalizedString(@"emptyforxia", @"")];
        }
    }else{
        [self.view toast:NSLocalizedString(@"qxsmxxzjzh", @"")];
    }
}

#pragma mark -拍照
- (IBAction)makePhotoOnClick:(UIButton *)sender {
    if(self.plantInfoBean != nil){
        UpdatingFilesViewController *updatingFilesViewController = [[UpdatingFilesViewController alloc]init];
        updatingFilesViewController.businessType = self.businessType;
        updatingFilesViewController.title = NSLocalizedString(@"fileinfo", @"");
        NSMutableArray *tempUploadedDataList = [self.uploadedDataList mutableCopy];
        [tempUploadedDataList addObject:[NSNull null]];
        updatingFilesViewController.imageList = [tempUploadedDataList mutableCopy];
        updatingFilesViewController.plantCode = [NSString stringWithFormat:@"%@",[self.plantInfoBean objectForKey:@"code"]];
        updatingFilesViewController.plantId = [NSString stringWithFormat:@"%d",[[self.plantInfoBean objectForKey:@"id"] intValue]];
        [updatingFilesViewController setBreakValueBlock:^(NSMutableArray * _Nonnull array) {
            [self.uploadedDataList removeAllObjects];
            if(array.count>0){
                [self.uploadedDataList addObjectsFromArray:[array mutableCopy]];
                self.photoNumTv.hidden = NO;
                NSString *imageNumStr = [NSString stringWithFormat:@"%d",(int)array.count];
                float labelWidth = [imageNumStr boundingRectWithSize:CGSizeMake(MAXFLOAT, 0.0) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName : [UIFont systemFontOfSize:10.0]} context:nil].size.width+16;
                self.photoNumTv.text = imageNumStr;
                self.photoNumTvWidth.constant = labelWidth > 18 ? labelWidth : 18;
            }else{
                self.photoNumTv.hidden = YES;
            }
        }];
        [self.navigationController pushViewController:updatingFilesViewController animated:YES];
    }else{
        [self.view toast:NSLocalizedString(@"qxsmxxzjzh", @"")];
    }
}

#pragma mark -历史记录
- (IBAction)recordOnClick:(UIButton *)sender {
    if(self.plantInfoBean != nil){
        int itemVersion = 2;
        if(itemVersion == 2){
            //历史记录版本2
            PJHistoryViewController *historyViewController = [[PJHistoryViewController alloc]init];
            historyViewController.title = NSLocalizedString(@"his.record", @"");
            historyViewController.plantId = [[self.plantInfoBean objectForKey:@"id"] intValue];
            [historyViewController copyValue:^(NSMutableArray * _Nonnull value) {
                //一键克隆
                NSLog(@"%@", [GJUtils objectToJson:value]);//plantRecordHistoryInfoBeanList
                
                for (int z = 0; z<self.groupListBeanList.count; z++) {
                    NSMutableDictionary *groupDict1 = [self.groupListBeanList[z] mutableCopy];
                    NSMutableArray *attributeList1 = [[groupDict1 objectForKey:@"attributeList"] mutableCopy];
                    for(int i = 0;i<attributeList1.count;i++){
                        //答案数据
                        NSMutableDictionary *attributeDict1 = [attributeList1[i] mutableCopy];
                        Boolean itNeedCopy = true;
                        //赋值
                        if(itNeedCopy){
                            for (int x = 0;x<value.count;x++){
                                if([[value[x] objectForKey:@"id"] intValue] == [[attributeDict1 objectForKey:@"id"] intValue]){
                                    if([[attributeDict1 objectForKey:@"fieldType"] isEqualToString:@"radio"]){
                                        NSMutableArray *selectResultBeans = [attributeDict1 objectForKey:@"results"];
                                        for (int y = 0;y<selectResultBeans.count;y++){
                                            NSMutableDictionary *selectResultBean = [selectResultBeans[y] mutableCopy];
                                            BOOL flag = [[selectResultBean objectForKey:@"title"] isEqualToString:[value[x] objectForKey:@"value"]];
                                            [selectResultBean setObject:[NSNumber numberWithInt:(flag ? 1 : 0)] forKey:@"selectValue"];
                                            [selectResultBeans replaceObjectAtIndex:y withObject:selectResultBean];
                                        }
                                        [attributeDict1 setObject:selectResultBeans forKey:@"results"];

                                    }else if([[attributeDict1 objectForKey:@"fieldType"] isEqualToString:@"checkbox"]){
                                        NSMutableArray *selectResultBeans = [attributeDict1 objectForKey:@"results"];
                                        for (int y = 0;y<selectResultBeans.count;y++){
                                            NSMutableDictionary *selectResultBean = [selectResultBeans[y] mutableCopy];
                                            NSArray *nameValueArray = [[value[x] objectForKey:@"value"] componentsSeparatedByString:@","];
                                            BOOL flag = [nameValueArray containsObject:[selectResultBean objectForKey:@"title"]];
                                            [selectResultBean setObject:[NSNumber numberWithInt:(flag ? 1 : 0)] forKey:@"selectValue"];
                                            [selectResultBeans replaceObjectAtIndex:y withObject:selectResultBean];
                                        }
                                        [attributeDict1 setObject:selectResultBeans forKey:@"results"];
                                    }else{
                                        [attributeDict1 setObject:[value[x] objectForKey:@"value"] forKey:@"resultStr"];
                                    }
                                }
                            }
                            [attributeList1 replaceObjectAtIndex:i withObject:attributeDict1];
                            //替换原来数据
                            [groupDict1 setObject:attributeList1 forKey:@"attributeList"];
                            [self.groupListBeanList replaceObjectAtIndex:z withObject:groupDict1];
                        }
                    }
                }

                [self.tableView reloadData];
            }];
            [self.navigationController pushViewController:historyViewController animated:YES];
        }else if(itemVersion == 1){
            //历史记录版本1
            [GJHttpTool GET:APP_PLANT_RECORD_HISTORY_INFO parameters:@{@"plantId":[NSString stringWithFormat:@"%d",[[self.plantInfoBean objectForKey:@"id"] intValue]]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    //赋值列表
                    NSMutableDictionary *detailsDict = [[responseObject objectForKey:@"data"] mutableCopy];
                    HistoryRecordDetailsViewController *historyRecordDetailsViewController = [[HistoryRecordDetailsViewController alloc]init];
                    historyRecordDetailsViewController.title = NSLocalizedString(@"his.record", @"");
                    historyRecordDetailsViewController.hidesBottomBarWhenPushed = YES;
                    historyRecordDetailsViewController.plantId = [[self.plantInfoBean objectForKey:@"id"] intValue];
                    historyRecordDetailsViewController.detailsDict = detailsDict;
                    historyRecordDetailsViewController.hybridName = [NSString stringWithFormat:@"%@",[self.plantInfoBean objectForKey:@"hybridName"]];
                    historyRecordDetailsViewController.plantCode = [NSString stringWithFormat:@"%@",[self.plantInfoBean objectForKey:@"code"]];
                    historyRecordDetailsViewController.createTime = [NSString stringWithFormat:@"%@",[detailsDict objectForKey:@"createTime"]];
                    historyRecordDetailsViewController.attributeValues = [NSString stringWithFormat:@"%@",[detailsDict objectForKey:@"attributeValues"]];
                    [historyRecordDetailsViewController setCopyValueBlock:^(NSMutableArray * _Nonnull value) {
                        //一键克隆
                        NSLog(@"%@", [GJUtils objectToJson:value]);//plantRecordHistoryInfoBeanList
                        
                        for (int z = 0; z<self.groupListBeanList.count; z++) {
                            NSMutableDictionary *groupDict1 = [self.groupListBeanList[z] mutableCopy];
                            NSMutableArray *attributeList1 = [[groupDict1 objectForKey:@"attributeList"] mutableCopy];
                            for(int i = 0;i<attributeList1.count;i++){
                                //答案数据
                                NSMutableDictionary *attributeDict1 = [attributeList1[i] mutableCopy];
                                Boolean itNeedCopy = true;
                                //赋值
                                if(itNeedCopy){
                                    for (int x = 0;x<value.count;x++){
                                        if([[value[x] objectForKey:@"id"] intValue] == [[attributeDict1 objectForKey:@"id"] intValue]){
                                            if([[attributeDict1 objectForKey:@"fieldType"] isEqualToString:@"radio"]){
                                                NSMutableArray *selectResultBeans = [attributeDict1 objectForKey:@"results"];
                                                for (int y = 0;y<selectResultBeans.count;y++){
                                                    NSMutableDictionary *selectResultBean = [selectResultBeans[y] mutableCopy];
                                                    BOOL flag = [[selectResultBean objectForKey:@"title"] isEqualToString:[value[x] objectForKey:@"value"]];
                                                    [selectResultBean setObject:[NSNumber numberWithInt:(flag ? 1 : 0)] forKey:@"selectValue"];
                                                    [selectResultBeans replaceObjectAtIndex:y withObject:selectResultBean];
                                                }
                                                [attributeDict1 setObject:selectResultBeans forKey:@"results"];

                                            }else if([[attributeDict1 objectForKey:@"fieldType"] isEqualToString:@"checkbox"]){
                                                NSMutableArray *selectResultBeans = [attributeDict1 objectForKey:@"results"];
                                                for (int y = 0;y<selectResultBeans.count;y++){
                                                    NSMutableDictionary *selectResultBean = [selectResultBeans[y] mutableCopy];
                                                    NSArray *nameValueArray = [[value[x] objectForKey:@"value"] componentsSeparatedByString:@","];
                                                    BOOL flag = [nameValueArray containsObject:[selectResultBean objectForKey:@"title"]];
                                                    [selectResultBean setObject:[NSNumber numberWithInt:(flag ? 1 : 0)] forKey:@"selectValue"];
                                                    [selectResultBeans replaceObjectAtIndex:y withObject:selectResultBean];
                                                }
                                                [attributeDict1 setObject:selectResultBeans forKey:@"results"];
                                            }else{
                                                [attributeDict1 setObject:[value[x] objectForKey:@"value"] forKey:@"resultStr"];
                                            }
                                        }
                                    }
                                    [attributeList1 replaceObjectAtIndex:i withObject:attributeDict1];
                                    //替换原来数据
                                    [groupDict1 setObject:attributeList1 forKey:@"attributeList"];
                                    [self.groupListBeanList replaceObjectAtIndex:z withObject:groupDict1];
                                }
                            }
                        }

                        [self.tableView reloadData];
                    }];
                    [self.navigationController pushViewController:historyRecordDetailsViewController animated:YES];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }
    }else{
        [self.view toast:NSLocalizedString(@"qxsmxxzjzh", @"")];
    }
}

#pragma mark -提交记录
- (IBAction)sumbitDataOnClick:(id)sender {
    if(self.plantInfoBean != nil){
        /**
         * hybridId : 0
         * imgList : [{"path":"string","type":0}]
         * logList : [{"attributeId":0,"attributeName":"string","attributeValue":"string"}]
         * plantId : 0
         */
        NSMutableDictionary *dataDict = [[NSMutableDictionary alloc]init];
        [dataDict setObject:[NSString stringWithFormat:@"%d",[[self.plantInfoBean objectForKey:@"hybridId"] intValue]] forKey:@"hybridId"];
        [dataDict setObject:[NSString stringWithFormat:@"%d",[[self.plantInfoBean objectForKey:@"id"] intValue]] forKey:@"plantId"];
        NSMutableArray *imgListBeans = [[NSMutableArray alloc]init];
        for (NSMutableDictionary *imageDict in self.uploadedDataList) {
            NSMutableDictionary *imgBean = [[NSMutableDictionary alloc]init];
            [imgBean setObject:[NSString stringWithFormat:@"%@",[imageDict objectForKey:@"urlPath"]] forKey:@"path"];
            [imgBean setObject:[NSNumber numberWithInt:[[imageDict objectForKey:@"fileType"] intValue]] forKey:@"type"];
            [imgListBeans addObject:imgBean];
        }
        [dataDict setObject:imgListBeans forKey:@"imgList"];
        NSMutableArray *logListBeans = [[NSMutableArray alloc]init];
        for (NSMutableDictionary *groupDict in self.groupListBeanList) {
            NSMutableArray *attributeList = [groupDict objectForKey:@"attributeList"];
            for (NSMutableDictionary *attributeDict in attributeList) {
                NSString *fieldTypeStr = [attributeDict objectForKey:@"fieldType"];
                /**
                 * attributeId : 0
                 * attributeName : string
                 * attributeValue : string
                 */
                NSMutableDictionary *logBean = [[NSMutableDictionary alloc]init];
                [logBean setObject:[NSNumber numberWithInt:[[attributeDict objectForKey:@"id"] intValue]] forKey:@"attributeId"];
                [logBean setObject:[NSString stringWithFormat:@"%@",[attributeDict objectForKey:@"fieldName"]] forKey:@"attributeName"];
                if([fieldTypeStr isEqualToString:@"radio"] || [fieldTypeStr isEqualToString:@"checkbox"]){
                    NSMutableArray *selectResultBeans = [attributeDict objectForKey:@"results"];
                    NSMutableArray *selectTitle = [[NSMutableArray alloc]init];
                    for (NSMutableDictionary *selectResultsBean in selectResultBeans){
                        if([[selectResultsBean objectForKey:@"selectValue"] intValue] == 1){
                            [selectTitle addObject:[NSString stringWithFormat:@"%@",[selectResultsBean objectForKey:@"title"]]];
                        }
                    }
                    if(selectTitle.count > 0){
                        [logBean setObject:[selectTitle componentsJoinedByString:@","] forKey:@"attributeValue"];
                    }
                }else if([fieldTypeStr isEqualToString:@"input"] || [fieldTypeStr isEqualToString:@"text"] || [fieldTypeStr isEqualToString:@"date"]){
                    [logBean setObject:[NSString stringWithFormat:@"%@",[attributeDict objectForKey:@"resultStr"]] forKey:@"attributeValue"];
                }
                if(!strIsEmpty([logBean objectForKey:@"attributeValue"])){
                    [logListBeans addObject:logBean];
                }
            }
        }
        [dataDict setObject:logListBeans forKey:@"logList"];
        [GJHttpTool POST:APP_PLANT_RECORD_ADD parameters:dataDict headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                self->isUploadResult = true;
                //[SVProgressHUD showSuccessWithStatus:@"申报成功"];
                [EasyTextView showImageText:NSLocalizedString(@"upload.success", @"") imageName:@"HUD_NF.png" config:^EasyTextConfig *{
                    return [EasyTextConfig shared].setAnimationType(TextAnimationTypeNone).setShadowColor([UIColor clearColor]).setBgColor([UIColor blackColor]).setTitleColor([UIColor whiteColor]).setSuperReceiveEvent(NO);
                }];
                
                [self.sumbitBtn setBackgroundColor:[UIColor grayColor]];
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            NSLog(@"%@",error);
        }];
    }else{
        [self.view toast:NSLocalizedString(@"qxsmxxzjzh", @"")];
    }
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
    return self.groupListBeanList.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    NSMutableDictionary *groupDict = self.groupListBeanList[section];
    NSMutableArray *attributeList = [groupDict objectForKey:@"attributeList"];
    return attributeList.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 56;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 32;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    NSMutableDictionary *groupDict = self.groupListBeanList[section];
    UILabel *headerLabel = [[UILabel alloc]init];
    [headerLabel setBackgroundColor:[UIColor clearColor]];
    [headerLabel setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 32)];
    [headerLabel setText:[NSString stringWithFormat:@"%@",[groupDict objectForKey:@"name"]]];
    [headerLabel setTextColor:navigationBarColor];
    [headerLabel setFont:[UIFont boldSystemFontOfSize:14]];
    [headerLabel setTextAlignment:NSTextAlignmentCenter];
    return headerLabel;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section{
    UIView *heaView = [[UIView alloc]init];
    [heaView setBackgroundColor:[UIColor clearColor]];
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 12)];
    return heaView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 12;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    
    NSMutableDictionary *groupDict = [self.groupListBeanList[indexPath.section] mutableCopy];
    NSMutableArray *attributeList = [[groupDict objectForKey:@"attributeList"] mutableCopy];
    NSMutableDictionary *attributeDict = [attributeList[indexPath.row] mutableCopy];
    NSString *fieldTypeStr = [attributeDict objectForKey:@"fieldType"];
    
    if([fieldTypeStr isEqualToString:@"checkbox"] || [fieldTypeStr isEqualToString:@"radio"]){
        PJDetailsCellSelect *pjDetailsCellSelect = [PJDetailsCellSelect cellInTableView:tableView withIdentifier:identifier];
        pjDetailsCellSelect.fieldNameLabel.text = [NSString stringWithFormat:@"%@",[attributeDict objectForKey:@"fieldName"]];
        //pjDetailsCellSelect.scrollView.delegate = self;
        [self initSelectLabel:pjDetailsCellSelect.selectMainView andSC:pjDetailsCellSelect.scrollView andWidthView:pjDetailsCellSelect.selectMainViewWidth andSelectLabel:[attributeDict objectForKey:@"results"] cellForRowAtIndexPath:indexPath];
        return pjDetailsCellSelect;
    }else if([fieldTypeStr isEqualToString:@"input"] || [fieldTypeStr isEqualToString:@"text"]){
        NSString *resultStr = [attributeDict objectForKey:@"resultStr"];
        PJDetailsCellSelectInput *pjDetailsCellSelectInput = [PJDetailsCellSelectInput cellInTableView:tableView withIdentifier:identifier];
        pjDetailsCellSelectInput.fieldNameLabel.text = [NSString stringWithFormat:@"%@",[attributeDict objectForKey:@"fieldName"]];
        pjDetailsCellSelectInput.textField.placeholder = [NSString stringWithFormat:@"%@%@",NSLocalizedString(@"please.input", @""),[attributeDict objectForKey:@"fieldName"]];
        pjDetailsCellSelectInput.textField.tag = indexPath.row;
        pjDetailsCellSelectInput.textField.text = strIsEmpty(resultStr) ? @"" : resultStr;
        [pjDetailsCellSelectInput textFieldChangedValue:^(NSString * _Nonnull value) {
            NSLog(@"%d-%d的值是---%@",(int)indexPath.section,(int)indexPath.row,value);
            
            NSMutableDictionary *groupDict1 = [self.groupListBeanList[indexPath.section] mutableCopy];
            NSMutableArray *attributeList1 = [[groupDict1 objectForKey:@"attributeList"] mutableCopy];
            NSMutableDictionary *attributeDict1 = [attributeList1[indexPath.row] mutableCopy];
            //修改值
            [attributeDict1 setObject:value forKey:@"resultStr"];
            [attributeList1 replaceObjectAtIndex:indexPath.row withObject:attributeDict1];
            [groupDict1 setObject:attributeList1 forKey:@"attributeList"];
            [self.groupListBeanList replaceObjectAtIndex:indexPath.section withObject:groupDict1];
            
            NSLog(@"修改后的值是---%@",[[self.groupListBeanList[indexPath.section] objectForKey:@"attributeList"][indexPath.row] objectForKey:@"resultStr"]);
        }];
        return pjDetailsCellSelectInput;
    }else if([fieldTypeStr isEqualToString:@"date"]){
        NSString *resultStr = [attributeDict objectForKey:@"resultStr"];
        PJDetailsCellSelectDate *pjDetailsCellSelectDate = [PJDetailsCellSelectDate cellInTableView:tableView withIdentifier:identifier];
        pjDetailsCellSelectDate.fieldNameLabel.text = [NSString stringWithFormat:@"%@",[attributeDict objectForKey:@"fieldName"]];
        if(strIsEmpty(resultStr)){
            //空的
            pjDetailsCellSelectDate.dateLabel.text = NSLocalizedString(@"please.selectdate", @"");
            [pjDetailsCellSelectDate.dateLabel setTextColor:[UIColor colorWithRed:105/255.0 green:105/255.0 blue:105/255.0 alpha:1]];
        }else{
            //不是空的
            pjDetailsCellSelectDate.dateLabel.text = resultStr;
            [pjDetailsCellSelectDate.dateLabel setTextColor:[UIColor blackColor]];
        }
        [pjDetailsCellSelectDate.putView whenTapped:^{
            //_________________________年-月-日-时-分（滚动到指定的日期）_________________________
            NSDateFormatter *minDateFormater = [[NSDateFormatter alloc] init];
            [minDateFormater setDateFormat:@"yyyy-MM-dd"];
            NSDate *scrollToDate = [minDateFormater dateFromString:(strIsEmpty(resultStr) ? [GJDateUtils getCurrentDate:@"yyyy-MM-dd"] : resultStr)];
            WSDatePickerView *datepicker = [[WSDatePickerView alloc] initWithDateStyle:DateStyleShowYearMonthDay scrollToDate:scrollToDate CompleteBlock:^(NSDate *selectDate) {
                NSString *date = [selectDate stringWithFormat:@"yyyy-MM-dd"];
                NSLog(@"选择的日期：%@",date);
                pjDetailsCellSelectDate.dateLabel.text = date;
                [pjDetailsCellSelectDate.dateLabel setTextColor:[UIColor blackColor]];
                
                NSMutableDictionary *groupDict1 = [self.groupListBeanList[indexPath.section] mutableCopy];
                NSMutableArray *attributeList1 = [[groupDict1 objectForKey:@"attributeList"] mutableCopy];
                NSMutableDictionary *attributeDict1 = [attributeList1[indexPath.row] mutableCopy];
                //修改值
                [attributeDict1 setObject:date forKey:@"resultStr"];
                [attributeList1 replaceObjectAtIndex:indexPath.row withObject:attributeDict1];
                [groupDict1 setObject:attributeList1 forKey:@"attributeList"];
                [self.groupListBeanList replaceObjectAtIndex:indexPath.section withObject:groupDict1];
                [self.tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationNone];
                
                NSLog(@"修改后的值是---%@",[[self.groupListBeanList[indexPath.section] objectForKey:@"attributeList"][indexPath.row] objectForKey:@"resultStr"]);
            }];
            datepicker.dateLabelColor = navigationBarColor;//年-月-日-时-分 颜色
            datepicker.datePickerColor = [UIColor blackColor];//滚轮日期颜色
            datepicker.doneButtonColor = navigationBarColor;//确定按钮的颜色
            datepicker.yearLabelColor = [UIColor clearColor];//大号年份字体颜色
            [datepicker show];
        }];
        return pjDetailsCellSelectDate;
    }
    
    return [UITableViewCell new];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
}

/**
 添加Label
 */
- (void)initSelectLabel:(UIView *)mainView andSC:(UIScrollView *)mScrollView andWidthView:(NSLayoutConstraint *)mainViewWidth andSelectLabel:(NSMutableArray *)selectLabel cellForRowAtIndexPath:(NSIndexPath *)indexPath{

    //清除所有的控件
    for(UIView *view in mainView.subviews){
        [view removeFromSuperview];
    }
    int x = 0;
    int middle = 8;
    for (int i = 0; i<selectLabel.count; i++) {
        NSMutableDictionary *selectDict = [selectLabel[i] mutableCopy];
        NSString *title = [NSString stringWithFormat:@"%@",[selectDict objectForKey:@"title"]];
        Boolean isSelect = [[selectDict objectForKey:@"selectValue"] intValue] == 1;
        /**
         计算文字长度
         */
        CGSize labelSize = [title boundingRectWithSize:CGSizeMake(MAXFLOAT, 0.0) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName : [UIFont systemFontOfSize:14.0]} context:nil].size;
        
        UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(x, 6, 24+labelSize.width, 44)];
        [label setText:title];
        label.layer.cornerRadius = 4;
        label.layer.masksToBounds = YES;
        [label setFont:[UIFont systemFontOfSize:14]];
        [label setTextAlignment:NSTextAlignmentCenter];
        if(isSelect){
            //选中状态
            [label setTextColor:[UIColor whiteColor]];
            [label setBackgroundColor:navigationBarColor];
        }else{
            //非选中状态
            [label setTextColor:[UIColor colorWithRed:105/255.0 green:105/255.0 blue:105/255.0 alpha:1]];
            [label setBackgroundColor:[UIColor whiteColor]];
        }
        
        [label whenTapped:^{

            NSMutableDictionary *groupDict = [self.groupListBeanList[indexPath.section] mutableCopy];
            NSMutableArray *attributeList = [[groupDict objectForKey:@"attributeList"] mutableCopy];
            NSMutableDictionary *attributeDict = [attributeList[indexPath.row] mutableCopy];
            NSMutableArray *results = [[attributeDict objectForKey:@"results"] mutableCopy];
            NSString *fieldTypeStr = [attributeDict objectForKey:@"fieldType"];
            
            if(isSelect){
                //直接取消选中
                [selectDict setObject:[NSNumber numberWithInt:0] forKey:@"selectValue"];
            }else{
                //选中
                if([fieldTypeStr isEqualToString:@"checkbox"]){
                    //多选，直接添加选中
                    [selectDict setObject:[NSNumber numberWithInt:1] forKey:@"selectValue"];
                }else if([fieldTypeStr isEqualToString:@"radio"]){
                    //单选，取消其他全部选择
                    for (int j = 0;j<results.count;j++){
                        NSMutableDictionary *selectResultBean = [[NSMutableDictionary alloc] init];
                        [selectResultBean setObject:[NSString stringWithFormat:@"%@",[results[j] objectForKey:@"title"]] forKey:@"title"];
                        [selectResultBean setObject:[NSNumber numberWithInt:0] forKey:@"selectValue"];
                        [results replaceObjectAtIndex:j withObject:selectResultBean];
                    }
                    [selectDict setObject:[NSNumber numberWithInt:1] forKey:@"selectValue"];
                }
            }
            [results replaceObjectAtIndex:i withObject:selectDict];
            [attributeDict setObject:results forKey:@"results"];
            [attributeList replaceObjectAtIndex:indexPath.row withObject:attributeDict];
            [groupDict setObject:attributeList forKey:@"attributeList"];
            
            [self.groupListBeanList replaceObjectAtIndex:indexPath.section withObject:groupDict];
            
            //刷新
            //[self.tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationNone];
            [self initSelectLabel:mainView andSC:mScrollView andWidthView:mainViewWidth andSelectLabel:results cellForRowAtIndexPath:indexPath];
        }];
        
        [mainView addSubview:label];
        
        x = x+24+labelSize.width+middle;
    }
    mainViewWidth.constant = x+middle;
}

//- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
//    float y = scrollView.contentOffset.y;// 获取bai纵向滑动的距du离zhi
//    float x = scrollView.contentOffset.x;//获取横向滑动的距离
//
//    NSLog(@"%.2f_%.2f",x,y);
//}

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
        if(dataStrArray.count>0){
            NSArray *lines = [dataStrArray[0] componentsSeparatedByString:@"\n"];
            NSString *nameString = [NSString stringWithFormat:@"%@",lines[0]];
            nameString = [nameString stringByReplacingOccurrencesOfString:NSLocalizedString(@"device.name", @"") withString:@""];
            nameString = [nameString stringByReplacingOccurrencesOfString:@" " withString:@""];
            
            UIAlertController *alert = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:[NSString stringWithFormat:@"%@:%@，%@",NSLocalizedString(@"scan.xp", @""),nameString,NSLocalizedString(@"isread", @"")] preferredStyle:UIAlertControllerStyleAlert];
            [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:nil]];
            [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"sure", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                //查询公共库
                self->isCollect = NO;
                [self queryCodeInfo:nameString];
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
