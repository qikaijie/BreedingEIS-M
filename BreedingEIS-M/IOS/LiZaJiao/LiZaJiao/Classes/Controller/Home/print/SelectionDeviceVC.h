//
//  SelectionDeviceVC.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/10/24.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "BaseViewController.h"
#import <UIKit/UIKit.h>

typedef void (^VoidBlock_id)(id);

@class CBPeripheral, CBCharacteristic;

NS_ASSUME_NONNULL_BEGIN

@interface SelectionDeviceVC : BaseViewController

@property (nonatomic, strong)VoidBlock_id callBack;

#pragma mark - XYSDKDelegate// 发现周边
//- (void)XYdidUpdatePeripheralList:(NSArray *)peripherals RSSIList:(NSArray *)rssiList;
//// 连接周边
//- (void)XYdidConnectPeripheral:(CBPeripheral *)peripheral;
//// 连接失败
//- (void)XYdidFailToConnectPeripheral:(CBPeripheral *)peripheral error:(NSError *)error;
//// 断开连接
//- (void)XYdidDisconnectPeripheral:(CBPeripheral *)peripheral isAutoDisconnect:(BOOL)isAutoDisconnect;
//// 发送数据成功
//- (void)XYdidWriteValueForCharacteristic:(CBCharacteristic *)character error:(NSError *)error;

@end

NS_ASSUME_NONNULL_END
