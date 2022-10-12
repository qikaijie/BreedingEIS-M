//
//  CollectViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/9.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "CollectViewController.h"
#import <CMPageTitleView/CMPageTitleView.h>
#import "MyCollectViewController.h"
#import "GJTapPageControl.h"

@interface CollectViewController ()<CMPageTitleViewDelegate,GJTapPageControlDelegate,UIScrollViewDelegate>

@property (strong, nonatomic) UIButton *backBtn;

@property (nonatomic,strong) GJTapPageControl *topMenuView;
@property (nonatomic,strong) UIScrollView *scrollView;
@property (nonatomic,strong) MyCollectViewController *zjCollectViewController;
@property (nonatomic,strong) MyCollectViewController *zzCollectViewController;

///**视图控制器数组*/
//@property (nonatomic,strong) NSMutableArray *childControllers;
///**default notes*/
//@property (nonatomic,weak) CMPageTitleView *pageTitleView;

@end

@implementation CollectViewController

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
    
    UIButton *emptyBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 28, 28)];
    [emptyBtn.widthAnchor constraintEqualToConstant:28].active = YES;
    [emptyBtn.heightAnchor constraintEqualToConstant:28].active = YES;
    [emptyBtn setImage:[UIImage imageNamed:@"icon_return_white"] forState:UIControlStateDisabled];
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:emptyBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    [self createTapPage];
    [self createScrollView];
    [self createTableViews];
    
}


- (void)createTapPage
{
    CGRect frame = CGRectMake(0.0, 0.0, 200, 44);
    NSArray *titles = @[NSLocalizedString(@"collect.zj", @""),NSLocalizedString(@"collect.zz", @"")];
    NSArray *normarImages = @[];
    NSArray *selectedImages = @[];
    self.topMenuView = [[GJTapPageControl alloc]initWithFrame:frame titleArray:titles normarImages:normarImages selectedImages:selectedImages];
    self.topMenuView.GJdelegate = self;
    self.topMenuView.font = 14;
    self.topMenuView.backgroundColor = [UIColor clearColor];
    self.topMenuView.titleColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1];
    self.topMenuView.titleSelectedColor = defaultTextColor;
    self.topMenuView.indicatorColor = navigationBarColor;
    
    self.topMenuView.itemWidth = 100;
    self.topMenuView.indicatorWidth = 20;
    
    self.topMenuView.backgroundColor = [UIColor whiteColor];
    
//    [self.navigationItem.titleView addSubview:self.topMenuView];
    self.navigationItem.titleView = self.topMenuView;
    
//    if (@available(iOS 11.0, *)) {
//        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
//            [self.topMenuView mas_makeConstraints:^(MASConstraintMaker *make) {
//                make.top.left.bottom.right.mas_equalTo(0);
//            }];
//        });
//    }
}

- (void)createScrollView
{
    _scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0,kSCREEN_WIDTH, kSCREEN_HEIGHT)];
    _scrollView.contentSize = CGSizeMake(2*kSCREEN_WIDTH,0);
    _scrollView.backgroundColor = [UIColor whiteColor];
    _scrollView.showsVerticalScrollIndicator = NO;
    _scrollView.showsHorizontalScrollIndicator = NO;
    _scrollView.delegate = self;
    _scrollView.pagingEnabled = YES;
    
    [self.view addSubview:_scrollView];
}

- (void)createTableViews
{
    MyCollectViewController *zjCollectViewController = [[MyCollectViewController alloc] initGroupIndex:0];
    _zjCollectViewController = zjCollectViewController;
    MyCollectViewController *zzCollectViewController = [[MyCollectViewController alloc] initGroupIndex:1];
    _zzCollectViewController = zzCollectViewController;
    
    
    [self addChildViewController:zjCollectViewController];
    [self addChildViewController:zzCollectViewController];
    
    zjCollectViewController.view.frame = CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT);
    zzCollectViewController.view.frame = CGRectMake(kSCREEN_WIDTH*1, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT);
    
    [self.scrollView addSubview:zjCollectViewController.view];
    [self.scrollView addSubview:zzCollectViewController.view];
}

#pragma mark - GJTapPageControlDelegate
- (void)buttonOnClick:(NSInteger)tag title:(NSString *)title
{
    [_scrollView setContentOffset:CGPointMake(CGRectGetWidth(_scrollView.frame)*tag, 0)  animated:YES];
}

#pragma mark --ScrollViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    if (scrollView == _scrollView) {
        float offset = scrollView.contentOffset.x;
        offset = offset*(self.topMenuView.itemWidth/CGRectGetWidth(scrollView.frame));
        [self.topMenuView changeIndicatorPoint:offset];
    }
}
- (void)scrollViewDidEndScrollingAnimation:(UIScrollView *)scrollView{
    if (scrollView == _scrollView) {
        float offset = scrollView.contentOffset.x;
        NSInteger index = (NSInteger)offset/CGRectGetWidth(scrollView.frame);
        [self.topMenuView changeTextColor:index];
        [self.topMenuView shakeAnimationForView:self.topMenuView.indicatorLine];
    }
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{
    [self scrollViewDidEndScrollingAnimation:scrollView];
}

//- (void)reloadConfig{
//
//    CMPageTitleView *pageTitleView = [[CMPageTitleView alloc] initWithFrame:CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT-kTabbarSafeBottomMargin-kStatusBarAndNavigationBarHeight)];
//    self.pageTitleView = pageTitleView;
//    self.pageTitleView.delegate = self;
//    CMPageTitleConfig *config = [CMPageTitleConfig defaultConfig];
//    config.cm_childControllers = [self.childControllers copy]; //必传参数
//    config.cm_switchMode = CMPageTitleSwitchMode_Underline;
//    config.cm_underlineStretch = YES;
//    config.cm_gradientStyle = CMTitleColorGradientStyle_RGB;
//    config.cm_contentMode = CMPageTitleContentMode_SpaceAround;
//    config.cm_scaleGradientContentMode = CMPageTitleScaleGradientContentMode_Bottom;
//    config.cm_font = [UIFont systemFontOfSize:14];
//    config.cm_defaultIndex = 0;
//    config.cm_backgroundColor = kTableViewBackCoclor;
//    config.cm_normalColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1];
//    config.cm_selectedColor = navigationBarColor;
//    config.cm_underlineColor = navigationBarColor;
//    self.pageTitleView.cm_config = config;
//    [self.view addSubview:self.pageTitleView];
//}

@end
