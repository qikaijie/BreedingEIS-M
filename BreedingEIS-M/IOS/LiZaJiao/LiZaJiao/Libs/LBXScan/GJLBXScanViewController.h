//
//  GJLBXScanViewController.h
//  storemanager
//
//  Created by Apple on 18/3/21.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import "LBXAlertAction.h"
#import "LBXScanViewController.h"

#pragma mark -模仿qq界面
//继承LBXScanViewController,在界面上绘制想要的按钮，提示语等
@interface GJLBXScanViewController : LBXScanViewController



/**
 @brief  扫码区域上方提示文字
 */
@property (nonatomic, strong) UILabel *topTitle;

#pragma mark --增加拉近/远视频界面
@property (nonatomic, assign) BOOL isVideoZoom;

#pragma mark - 底部几个功能：开启闪光灯、相册、我的二维码
//底部显示的功能项
@property (nonatomic, strong) UIView *bottomItemsView;


//相册
@property (nonatomic, strong) UIButton *btnPhoto;
//闪光灯
@property (nonatomic, strong) UIButton *btnFlash;

- (void)setResultWithBlock:(nullable void (^) (LBXScanResult * __nullable resultAsString))resultBlock;



@end
