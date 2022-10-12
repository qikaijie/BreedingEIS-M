//
//  ZZHistoryViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/5/12.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "ZZHistoryViewController.h"
#import <CMPageTitleView/CMPageTitleView.h>
#import "ZZHistoryDetailsViewController.h"

@interface ZZHistoryViewController ()<CMPageTitleViewDelegate>

@property (strong, nonatomic) UIButton *backBtn;

/**视图控制器数组*/
@property (nonatomic,strong) NSMutableArray *childControllers;
/**default notes*/
@property (nonatomic,weak) CMPageTitleView *pageTitleView;

@end

@implementation ZZHistoryViewController

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

- (void)copyValue:(CopyValueBlock)block{
    self.copyValueBlock = block;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
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
    
    self.childControllers = [[NSMutableArray alloc]init];
    [self.childControllers addObject:[[ZZHistoryDetailsViewController alloc] initGroupIndex:0 andSeedlingId:(int)self.seedlingId blockValue:self.copyValueBlock]];
    [self.childControllers addObject:[[ZZHistoryDetailsViewController alloc] initGroupIndex:1 andSeedlingId:(int)self.seedlingId blockValue:self.copyValueBlock]];
    [self.childControllers addObject:[[ZZHistoryDetailsViewController alloc] initGroupIndex:2 andSeedlingId:(int)self.seedlingId blockValue:self.copyValueBlock]];
    
    [self reloadConfig];
}

- (void)reloadConfig{
    
    CMPageTitleView *pageTitleView = [[CMPageTitleView alloc] initWithFrame:CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT-kTabbarSafeBottomMargin-kStatusBarAndNavigationBarHeight)];
    self.pageTitleView = pageTitleView;
    self.pageTitleView.delegate = self;
    CMPageTitleConfig *config = [CMPageTitleConfig defaultConfig];
    config.cm_childControllers = [self.childControllers copy]; //必传参数
    config.cm_switchMode = CMPageTitleSwitchMode_Underline;
    config.cm_underlineStretch = YES;
    config.cm_gradientStyle = CMTitleColorGradientStyle_RGB;
    config.cm_contentMode = CMPageTitleContentMode_SpaceAround;
    config.cm_scaleGradientContentMode = CMPageTitleScaleGradientContentMode_Bottom;
    config.cm_font = [UIFont systemFontOfSize:14];
    config.cm_defaultIndex = 0;
    config.cm_backgroundColor = [UIColor whiteColor];
    config.cm_normalColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1];
    config.cm_selectedColor = navigationBarColor;
    config.cm_underlineColor = navigationBarColor;
    self.pageTitleView.cm_config = config;
    [self.view addSubview:self.pageTitleView];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
