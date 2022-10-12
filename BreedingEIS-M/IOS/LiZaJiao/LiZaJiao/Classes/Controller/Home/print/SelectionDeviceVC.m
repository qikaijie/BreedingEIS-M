//
//  SelectionDeviceVC.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/10/24.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "SelectionDeviceVC.h"
#import "SelectionDeviceCell.h"
#import "XYBLEManager.h"
#import "ProgressHUD.h"
#import "AppDelegate.h"
#import "PosCommand.h"
#import "TscCommand.h"

@interface SelectionDeviceVC ()<XYBLEManagerDelegate,UITableViewDelegate,UITableViewDataSource>

@property (weak, nonatomic) IBOutlet UITableView *myTable;

@property (nonatomic,strong) NSMutableArray *dataArr;
@property (nonatomic,strong) NSArray *rssiList;
@property (nonatomic,strong) XYBLEManager *manager;


@property (strong, nonatomic) UIButton *backBtn;
@property (strong, nonatomic) UIButton *refleshBtn;

@end

@implementation SelectionDeviceVC

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

#pragma mark - lazy
//- (NSMutableArray *)dataArr {
//    if (!_dataArr) {
//        _dataArr = [NSMutableArray array];
//    }
//    return _dataArr;
//}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationController.navigationBar.translucent = NO;
    
    self.backBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 28, 28)];
    [self.backBtn.widthAnchor constraintEqualToConstant:28].active = YES;
    [self.backBtn.heightAnchor constraintEqualToConstant:28].active = YES;
    [self.backBtn setImage:[UIImage imageNamed:@"icon_return"] forState:UIControlStateNormal];
    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:self.backBtn];
    self.navigationItem.leftBarButtonItem = leftItem;
    [self.backBtn whenTapped:^{
        [self.navigationController popViewControllerAnimated:YES];
    }];
    
    self.refleshBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 28, 28)];
    [self.refleshBtn.widthAnchor constraintEqualToConstant:28].active = YES;
    [self.refleshBtn.heightAnchor constraintEqualToConstant:28].active = YES;
    [self.refleshBtn setImage:[UIImage imageNamed:@"icon_search_bluetooth"] forState:UIControlStateNormal];
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:self.refleshBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    [self.refleshBtn whenTapped:^{
        //搜索行为
        [self scanAgain];
    }];
    
    [self initTableView];

    self.navigationItem.title = NSLocalizedString(@"blue.select", @"");
    self.manager = [XYBLEManager sharedInstance];
    self.manager.delegate = self;
    [self.manager XYstartScan];
    
}

- (void)initTableView {
    self.dataArr = [[NSMutableArray alloc]init];
//    [self.myTable setBackgroundColor:kTableViewBackCoclor];
    [self.myTable setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.myTable.delegate = self;
    self.myTable.dataSource = self;
}

#pragma mark - Table view data source


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {

    return self.dataArr.count;
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 68;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    SelectionDeviceCell *cell = [SelectionDeviceCell cellInTableView:tableView withIdentifier:identifier];
    
    CBPeripheral *peripheral = self.dataArr[indexPath.row];
    
    cell.titleLable.text = peripheral.name;
    
    NSNumber *rssi =_rssiList[indexPath.row];

    cell.subTitleLabel.text = [NSString stringWithFormat:@"RSSI:%zd",rssi.integerValue];

    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
   
    CBPeripheral *peripheral = self.dataArr[indexPath.row];
    NSString *message =  [NSString stringWithFormat:@"%@%@",NSLocalizedString(@"linking", @""),peripheral.name];
    [ProgressHUD show:message];
    // 连接周边
    [self.manager XYconnectDevice:peripheral];
    self.manager.writePeripheral = peripheral;
}

#pragma mark - XYSDKDelegate
- (void)XYdidUpdatePeripheralList:(NSArray *)peripherals RSSIList:(NSArray *)rssiList{
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        self->_dataArr = [NSMutableArray arrayWithArray:peripherals];
        self->_rssiList = rssiList;
        [self->_myTable reloadData];

    });

}

/** 连接成功 */
- (void)XYdidConnectPeripheral:(CBPeripheral *)peripheral{
    NSLog(@"%s",__func__);
    
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        //        [MBProgressHUD hideHUDForView:self.view animated:NO];
        [ProgressHUD dismiss];
        NSNotification *notification =[NSNotification notificationWithName:ConnectBleSuccessNote object:nil userInfo:nil];
        //通过通知中心发送行程开始通知
        [[NSNotificationCenter defaultCenter] postNotification:notification];
            // 返回主页控制器
        [self.navigationController popViewControllerAnimated:YES];
        if (self->_callBack){
            self->_callBack(nil);
        }

    });

    
    SharedAppDelegate.isConnectedBLE = YES;
    SharedAppDelegate.peripheral = peripheral;
    
}

// 连接失败
- (void)XYdidFailToConnectPeripheral:(CBPeripheral *)peripheral error:(NSError *)error {
    //    [MBProgressHUD hideHUDForView:self.view animated:NO];
    //    [MBProgressHUD showError:@"连接失败" toView:self.view];
    [ProgressHUD dismiss];
    NSNotification *notification =[NSNotification notificationWithName:ConnectBleFailNote object:nil userInfo:nil];
    //通过通知中心发送行程开始通知
    [[NSNotificationCenter defaultCenter] postNotification:notification];

    [ProgressHUD showError:NSLocalizedString(@"link.fain", @"")];
    SharedAppDelegate.isConnectedBLE = NO;
}

// 写入数据成功
- (void)XYdidWriteValueForCharacteristic:(CBCharacteristic *)character error:(NSError *)error {
    
}
// 断开连接
- (void)XYdidDisconnectPeripheral:(CBPeripheral *)peripheral isAutoDisconnect:(BOOL)isAutoDisconnect{
    
    NSNotification *notification =[NSNotification notificationWithName:ConnectBleFailNote object:nil userInfo:nil];
    //通过通知中心发送行程开始通知
    [[NSNotificationCenter defaultCenter] postNotification:notification];
    if (isAutoDisconnect) {
        NSLog(@"自动断开...");
        [self.navigationController popToViewController:self animated:YES];
        [[[UIAlertView alloc] initWithTitle:NSLocalizedString(@"link.duankai", @"") message:nil delegate:nil cancelButtonTitle:NSLocalizedString(@"iknow", @"") otherButtonTitles: nil] show];
        [self scanAgain];
    }else {
        NSLog(@"手动断开...");
    }
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        //        [MBProgressHUD hideHUDForView:self.view animated:NO];
    });
    
    NSLog(@"%s",__func__);
}

- (void)scanAgain{
    [self.dataArr removeAllObjects];
    [self.myTable reloadData];
    [self.manager XYdisconnectRootPeripheral];
    [self.manager XYstartScan];
}

@end
