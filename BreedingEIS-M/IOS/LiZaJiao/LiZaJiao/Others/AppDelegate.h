//
//  AppDelegate.h
//  LiZaJiao
//
//  Created by Apple on 2018/10/28.
//  Copyright © 2018年 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XYBLEManager.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

//是否连接蓝牙
@property (assign, nonatomic) BOOL isConnectedBLE;
@property (strong, nonatomic) CBPeripheral *peripheral;


@end

