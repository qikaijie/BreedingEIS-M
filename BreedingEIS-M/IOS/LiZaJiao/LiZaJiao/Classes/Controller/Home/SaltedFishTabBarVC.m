//
//  SaltedFishTabBarVC.m
//  AxcAE_TabBar
//
//  Created by Axc on 2018/6/4.
//  Copyright © 2018年 AxcLogo. All rights reserved.
//

#import "SaltedFishTabBarVC.h"
#import "HomeSquareViewController.h"//记录
#import "HomeScheduleViewController.h"//评价
#import "HomeMeViewController.h"//我的

@interface SaltedFishTabBarVC ()<AxcAE_TabBarDelegate>

@end

@implementation SaltedFishTabBarVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // 添加子VC
    [self addChildViewControllers];
}
- (void)addChildViewControllers{

    HomeSquareViewController *vc1 = [[HomeSquareViewController alloc]init];
    vc1.businessType = 0;
    vc1.title = NSLocalizedString(@"TabTitleOne.Title", @"");
    
    HomeSquareViewController *vc2 = [[HomeSquareViewController alloc]init];
    vc2.businessType = 1;
    vc2.title = NSLocalizedString(@"TabTitleTwo.Title", @"");
    
    HomeMeViewController *vc3 = [[HomeMeViewController alloc]init];
    vc3.title = NSLocalizedString(@"TabTitleThree", @"");
    
    UINavigationController *navC1 = [[UINavigationController alloc] initWithRootViewController:vc1];
    //navC1.fullScreenPopGestureEnabled = YES;
    UINavigationController *navC2 = [[UINavigationController alloc] initWithRootViewController:vc2];
    //navC2.fullScreenPopGestureEnabled = YES;
    UINavigationController *navC3 = [[UINavigationController alloc] initWithRootViewController:vc3];
    //navC2.fullScreenPopGestureEnabled = YES;
    
    // 创建选项卡的数据 想怎么写看自己，这块我就写笨点了
    NSArray <NSDictionary *>*VCArray =
    @[@{@"vc":navC1,@"normalImg":@"icon_tab_record_n",@"selectImg":@"icon_tab_record_s",@"itemTitle":NSLocalizedString(@"TabTitleOne", @"")},
      @{@"vc":navC2,@"normalImg":@"icon_tab_record_n",@"selectImg":@"icon_tab_record_s",@"itemTitle":NSLocalizedString(@"TabTitleTwo", @"")},
      @{@"vc":navC3,@"normalImg":@"icon_tab_me_n",@"selectImg":@"icon_tab_me_s",@"itemTitle":NSLocalizedString(@"TabTitleThree", @"")}];
    // 1.遍历这个集合
    // 1.1 设置一个保存构造器的数组
    NSMutableArray *tabBarConfs = @[].mutableCopy;
    // 1.2 设置一个保存VC的数组
    NSMutableArray *tabBarVCs = @[].mutableCopy;
    [VCArray enumerateObjectsUsingBlock:^(NSDictionary * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        // 2.根据集合来创建TabBar构造器
        AxcAE_TabBarConfigModel *model = [AxcAE_TabBarConfigModel new];
        // 3.item基础数据三连
        model.itemTitle = [obj objectForKey:@"itemTitle"];
        model.selectImageName = [obj objectForKey:@"selectImg"];
        model.normalImageName = [obj objectForKey:@"normalImg"];
        // 4.设置单个选中item标题状态下的颜色
        model.selectColor = navigationBarColor;
        model.normalColor = [UIColor colorWithRed:191/255.0 green:191/255.0 blue:191/255.0 alpha:1];

        model.interactionEffectStyle = AxcAE_TabBarInteractionEffectStyleSpring;
        
        /***********************************/
//        if (idx == 1 ) { // 如果是中间的
//            // 设置凸出 矩形
//            model.bulgeStyle = AxcAE_TabBarConfigBulgeStyleSquare;
//            // 设置凸出高度
//            model.bulgeHeight = 10;
//            // 设置成图片文字展示
//            model.itemLayoutStyle = AxcAE_TabBarItemLayoutStyleTopPictureBottomTitle;
//            // 设置图片
//            model.selectImageName = @"icon_tianbao";
//            model.normalImageName = @"icon_tianbao";
//            model.selectBackgroundColor = model.normalBackgroundColor = [UIColor clearColor];
//            model.backgroundImageView.hidden = YES;
//            // 设置图片大小c上下左右全边距
//            model.componentMargin = UIEdgeInsetsMake(0, 0, 0, 0);
//            // 设置图片的高度为40
//            model.icomImgViewSize = CGSizeMake(self.tabBar.frame.size.width / 5, 50);
//            model.titleLabelSize = CGSizeMake(self.tabBar.frame.size.width / 5, 20);
//            // 图文间距0
//            model.pictureWordsMargin = 0;
//            // 设置标题文字字号
//            model.titleLabel.font = [UIFont systemFontOfSize:11];
//            // 设置大小/边长 自动根据最大值进行裁切
//            model.itemSize = CGSizeMake(self.tabBar.frame.size.width / 5 - 5.0 ,self.tabBar.frame.size.height + 20);
//        }else{  // 其他的按钮来点小动画吧
//            // 来点效果好看
//            //model.interactionEffectStyle = AxcAE_TabBarInteractionEffectStyleSpring;
//            // 点击背景稍微明显点吧
//            //model.selectBackgroundColor = AxcAE_TabBarRGBA(248, 248, 248, 1);
//            model.normalBackgroundColor = [UIColor clearColor];
//        }
        model.normalBackgroundColor = [UIColor clearColor];
        // 备注 如果一步设置的VC的背景颜色，VC就会提前绘制驻留，优化这方面的话最好不要这么写
        // 示例中为了方便就在这写了
        UIViewController *vc = [obj objectForKey:@"vc"];
        vc.view.backgroundColor = [UIColor whiteColor];
        // 5.将VC添加到系统控制组
        [tabBarVCs addObject:vc];
        // 5.1添加构造Model到集合
        [tabBarConfs addObject:model];
    }];
    // 使用自定义的TabBar来帮助触发凸起按钮点击事件
    TestTabBar *testTabBar = [TestTabBar new];
    [self setValue:testTabBar forKey:@"tabBar"];
    // 5.2 设置VCs -----
    // 一定要先设置这一步，然后再进行后边的顺序，因为系统只有在setViewControllers函数后才不会再次创建UIBarButtonItem，以免造成遮挡
    // 大意就是一定要让自定义TabBar遮挡住系统的TabBar
    self.viewControllers = tabBarVCs;
    //////////////////////////////////////////////////////////////////////////
    // 注：这里方便阅读就将AE_TabBar放在这里实例化了 使用懒加载也行
    // 6.将自定义的覆盖到原来的tabBar上面
    // 这里有两种实例化方案：
    // 6.1 使用重载构造函数方式：
    //    self.axcTabBar = [[AxcAE_TabBar alloc] initWithTabBarConfig:tabBarConfs];
    // 6.2 使用Set方式：
    self.axcTabBar = [AxcAE_TabBar new] ;
    self.axcTabBar.tabBarConfig = tabBarConfs;
    // 7.设置委托
    self.axcTabBar.delegate = self;
    self.axcTabBar.backgroundColor = [UIColor whiteColor];
    // 8.添加覆盖到上边
    [self.tabBar addSubview:self.axcTabBar];
    [self addLayoutTabBar]; // 10.添加适配
}
// 9.实现代理，如下：
static NSInteger lastIdx = 0;
- (void)axcAE_TabBar:(AxcAE_TabBar *)tabbar selectIndex:(NSInteger)index{
    // 通知 切换视图控制器
    [self setSelectedIndex:index];
    lastIdx = index;
}
- (void)setSelectedIndex:(NSUInteger)selectedIndex{
    [super setSelectedIndex:selectedIndex];
    if(self.axcTabBar){
        self.axcTabBar.selectIndex = selectedIndex;
    }
}

// 10.添加适配
- (void)addLayoutTabBar{
    // 使用重载viewDidLayoutSubviews实时计算坐标 （下边的 -viewDidLayoutSubviews 函数）
    // 能兼容转屏时的自动布局
}
- (void)viewDidLayoutSubviews{
    [super viewDidLayoutSubviews];
    self.axcTabBar.frame = self.tabBar.bounds;
    [self.axcTabBar viewDidLayoutItems];
}


@end
