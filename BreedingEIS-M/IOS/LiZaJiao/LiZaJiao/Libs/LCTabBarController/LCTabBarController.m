//
//  LCTabBarController.m
//  LCTabBarControllerDemo
//
//  Created by Leo on 15/12/2.
//  Copyright © 2015 Leo <leodaxia@gmail.com>
//
//  Copyright (c) 2015-2017 Leo
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//  SOFTWARE.

#import "LCTabBarController.h"
#import "LCTabBarItem.h"

#import "HomeSquareViewController.h"//广场
#import "HomeScheduleViewController.h"//日程
#import "HomeMeViewController.h"//我

@interface LCTabBarController () <LCTabBarDelegate>

@end

@implementation LCTabBarController

@synthesize badgeTitleFont;
@synthesize itemTitleFont;
@synthesize itemTitleColor;
@synthesize selectedItemTitleColor;

-(instancetype)init{
    if (self=[super init]) {
        HomeSquareViewController *vc1 = [[HomeSquareViewController alloc]init];
        vc1.title = @"广场";
        vc1.tabBarItem.image = [UIImage imageNamed:@"found_n"];
        vc1.tabBarItem.selectedImage = [UIImage imageNamed:@"found_h"];
        
        HomeScheduleViewController *vc2 = [[HomeScheduleViewController alloc]init];
        vc2.title = @"日程";
        vc2.tabBarItem.image = [UIImage imageNamed:@"newCalendar"];
        vc2.tabBarItem.selectedImage = [UIImage imageNamed:@"newCalendarH"];
        vc2.tabBarItem.badgeValue = @"8";
        
        HomeMeViewController *vc4 = [[HomeMeViewController alloc]init];
        vc4.title = @"我";
        vc4.tabBarItem.image = [UIImage imageNamed:@"newPersonal"];
        vc4.tabBarItem.selectedImage = [UIImage imageNamed:@"newPersonalH"];
        
        UINavigationController *navC1 = [[UINavigationController alloc] initWithRootViewController:vc1];
        //navC1.fullScreenPopGestureEnabled = YES;
        UINavigationController *navC2 = [[UINavigationController alloc] initWithRootViewController:vc2];
        //navC2.fullScreenPopGestureEnabled = YES;
        UINavigationController *navC4 = [[UINavigationController alloc] initWithRootViewController:vc4];
        //navC2.fullScreenPopGestureEnabled = YES;
        
        //    self.itemTitleFont          = [UIFont boldSystemFontOfSize:11.0f];
        self.itemTitleColor         = [UIColor grayColor];
        self.selectedItemTitleColor = navigationBarColor;
        //    self.itemImageRatio         = 0.5f;
        //    self.badgeTitleFont         = [UIFont boldSystemFontOfSize:12.0f];
        
        self.viewControllers = @[navC1, navC2 , navC4];
    }
    return self;
}

- (void)setBadgeTitleFont:(UIFont *)aBadgeTitleFont {
    badgeTitleFont = aBadgeTitleFont;
    
    self.lcTabBar.badgeTitleFont = aBadgeTitleFont;
}

- (void)setItemTitleFont:(UIFont *)aItemTitleFont {
    itemTitleFont = aItemTitleFont;
    
    self.lcTabBar.itemTitleFont = aItemTitleFont;
}

- (void)setItemTitleColor:(UIColor *)aItemTitleColor {
    itemTitleColor = aItemTitleColor;
    
    self.lcTabBar.itemTitleColor = aItemTitleColor;
}

- (void)setItemImageRatio:(CGFloat)itemImageRatio {
    _itemImageRatio = itemImageRatio;
    
    self.lcTabBar.itemImageRatio = itemImageRatio;
}

- (void)setSelectedItemTitleColor:(UIColor *)aSelectedItemTitleColor {
    selectedItemTitleColor = aSelectedItemTitleColor;
    
    self.lcTabBar.selectedItemTitleColor = aSelectedItemTitleColor;
}

- (UIFont *)itemTitleFont {
    if (!itemTitleFont) {
        itemTitleFont = [UIFont systemFontOfSize:10.0f];
    }
    return itemTitleFont;
}

- (UIFont *)badgeTitleFont {
    if (!badgeTitleFont) {
        badgeTitleFont = [UIFont systemFontOfSize:11.0f];
    }
    return badgeTitleFont;
}

- (UIColor *)itemTitleColor {
    if (!itemTitleColor) {
        itemTitleColor = LCColorForTabBar(117, 117, 117);
    }
    return itemTitleColor;
}

- (UIColor *)selectedItemTitleColor {
    if (!selectedItemTitleColor) {
        selectedItemTitleColor = LCColorForTabBar(234, 103, 7);
    }
    return selectedItemTitleColor;
}

#pragma mark -

- (void)loadView {
    
    [super loadView];
    
    self.itemImageRatio = 0.70f;
}

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    [self.tabBar addSubview:({
        
        LCTabBar *tabBar = [[LCTabBar alloc] init];
        tabBar.frame     = self.tabBar.bounds;
        tabBar.delegate  = self;
        
        tabBar.badgeTitleFont         = self.badgeTitleFont;
        tabBar.itemTitleFont          = self.itemTitleFont;
        tabBar.itemTitleColor         = self.itemTitleColor;
        tabBar.itemImageRatio         = self.itemImageRatio;
        tabBar.selectedItemTitleColor = self.selectedItemTitleColor;
        
        self.lcTabBar = tabBar;
    })];
}

- (void)handleTabBarItemChanged {
    [self hideOriginControls];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self hideOriginControls];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [self hideOriginControls];
}

- (void)hideOriginControls {
    if ([UIDevice currentDevice].systemVersion.floatValue >= 1.0) {
        // iOS 11.0+
        [self.tabBar.subviews enumerateObjectsUsingBlock:^(__kindof UIView * obj, NSUInteger idx, BOOL * stop) {
            if ([obj isKindOfClass:[UIControl class]]) {
                [obj setHidden:YES];
            }
        }];
    } else {
        // TODO: fix iOS 11.0-
        
    }
}

- (void)viewWillLayoutSubviews {
    [super viewWillLayoutSubviews];
    
    CGRect frame = self.lcTabBar.frame;
    frame.size.width = self.tabBar.bounds.size.width;
    self.lcTabBar.frame = frame;
}

- (void)removeOriginControls {
    [self hideOriginControls];
}

- (void)addChildViewController:(UIViewController *)childController {
    [super addChildViewController:childController];
    
    self.lcTabBar.tabBarItemCount = self.viewControllers.count;
    
    UIImage *selectedImage = childController.tabBarItem.selectedImage;
    childController.tabBarItem.selectedImage = [selectedImage imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    
    [self.lcTabBar addTabBarItem:childController.tabBarItem];
}

- (void)setSelectedIndex:(NSUInteger)selectedIndex {
    
    [super setSelectedIndex:selectedIndex];
    
    self.lcTabBar.selectedItem.selected = NO;
    self.lcTabBar.selectedItem = self.lcTabBar.tabBarItems[selectedIndex];
    self.lcTabBar.selectedItem.selected = YES;
}

#pragma mark - XXTabBarDelegate Method

- (void)tabBar:(LCTabBar *)tabBarView didSelectedItemFrom:(NSInteger)from to:(NSInteger)to {
    
    self.selectedIndex = to;
}

@end
