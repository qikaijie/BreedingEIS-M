//
//  HomeMeViewController.m
//  UinChat
//
//  Created by Apple on 2018/10/11.
//  Copyright © 2018年 GuJie. All rights reserved.
//

#import "HomeMeViewController.h"
#import "LoginViewController.h"
#import "CollectViewController.h"
#import "FeedBackViewController.h"

@interface HomeMeViewController ()

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *paddingBottom;

@property (weak, nonatomic) IBOutlet UIView *collectView;
@property (weak, nonatomic) IBOutlet UILabel *nikenameLabel;

@property (weak, nonatomic) IBOutlet UIView *feedbackView;

@property (weak, nonatomic) IBOutlet UIView *versionView;
@property (weak, nonatomic) IBOutlet UIImageView *versionTip;
@property (weak, nonatomic) IBOutlet UILabel *versionLabel;

@end

@implementation HomeMeViewController

- (void)viewWillAppear:(BOOL)animated{
    self.navigationController.navigationBar.barTintColor = navigationBarColor;
    //    self.navigationController.navigationBar.shadowImage = [UIImage new];
    //设置导航栏文字颜色
    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObjectsAndKeys:
                                                                   [UIColor whiteColor],
                                                                   NSForegroundColorAttributeName,
                                                                   [UIFont boldSystemFontOfSize:16],
                                                                   NSFontAttributeName,nil];
    self.navigationController.navigationBar.tintColor = [UIColor whiteColor];
    
    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
    backgroundView.subviews.firstObject.hidden = YES;
    
    [self.navigationController setNavigationBarHidden:YES animated:NO];//隐藏UINavigationBar
    
    [GJHttpTool POST:APP_LOGIN_2 parameters:@{} headerParams:@{} awaitingView:nil success:^(id responseObject) {
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            self.nikenameLabel.text = [NSString stringWithFormat:@"%@",[[responseObject objectForKey:@"data"] objectForKey:@"nickName"]];
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
    }];
}

- (void)viewWillDisappear:(BOOL)animated{
    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObjectsAndKeys:
                                                                   defaultTextColor,
                                                                   NSForegroundColorAttributeName,
                                                                   [UIFont boldSystemFontOfSize:16],
                                                                   NSFontAttributeName,nil];
    self.navigationController.navigationBar.tintColor = [UIColor whiteColor];
    
    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
    backgroundView.subviews.firstObject.hidden = NO;
    
    [self.navigationController setNavigationBarHidden:NO animated:NO];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"";
    self.navigationController.navigationBar.translucent = NO;
    
    self.paddingBottom.constant = kTabbarSafeBottomMargin+64;
    
    [self.collectView whenTapped:^{
        //我的收藏列表
        CollectViewController *myCollectViewController = [[CollectViewController alloc]init];
        myCollectViewController.title = NSLocalizedString(@"me.collect", @"");
        myCollectViewController.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:myCollectViewController animated:YES];
    }];
    
    
    /**
     版本号
     */
    [self queryNewVersionForAppStore];
    [self.versionLabel setText:[NSString stringWithFormat:@"V%@",APP_VERSION]];
    [self.versionView whenTapped:^{
        //
        [self queryNewVersionForAppStore];
    }];
    
    [self.feedbackView whenTapped:^{
        //意见反馈
        FeedBackViewController *feedBackViewController = [[FeedBackViewController alloc]init];
        feedBackViewController.title = NSLocalizedString(@"me.faceback", @"");
        feedBackViewController.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:feedBackViewController animated:YES];
    }];
}

- (IBAction)logoutOnClick:(UIButton *)sender {
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:NSLocalizedString(@"me.logouttip", @"")
                                                                      preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        NSLog(@"取消了");
    }])];
    [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"logout", @"") style:UIAlertActionStyleDestructive handler:^(UIAlertAction * _Nonnull action) {
        [GJHttpTool GET:APP_LOGOUT parameters:@{} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            kSetUserDefaults(USER_TOKEN,@"");

            //跳转登录
            ((AppDelegate *)[[UIApplication sharedApplication] delegate]).window.rootViewController = [[UINavigationController alloc] initWithRootViewController:[[LoginViewController alloc]init]];
            [((AppDelegate *)[[UIApplication sharedApplication] delegate]).window makeKeyAndVisible];
        } failure:^(NSError *error) {
            [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"service.get.error", @"")];
        }];
    }])];
    [self presentViewController:alertController animated:YES completion:nil];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/


- (void)queryNewVersionForAppStore{
    NSError *error;
    NSData *response = [NSURLConnection sendSynchronousRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://itunes.apple.com/cn/lookup?id=%@",@"1570277391"]]] returningResponse:nil error:nil];
    NSDictionary *appInfoDic = [NSJSONSerialization JSONObjectWithData:response options:NSJSONReadingMutableLeaves error:&error];
    NSLog(@"可输出一下看看%@",appInfoDic);
    NSArray *array = appInfoDic[@"results"];
    if(array.count < 1) {
        NSLog(@"此APPID为未上架的APP或者查询不到");
        return;
    }
    NSDictionary*dic = array[0];
    //AppStore版本号
    NSString *appStoreVersion = dic[@"version"];
    NSLog(@"当前版本号:%@---商店版本号:%@",APP_VERSION,appStoreVersion);
    
    if([self versionCompareOldStr:APP_VERSION andNewStr:appStoreVersion]){
        self.versionTip.hidden = NO;
        UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:[NSString stringWithFormat:@"当前最新版本为V%@，请去App Store进行更新!",appStoreVersion]
                                                                          preferredStyle:UIAlertControllerStyleAlert];
        [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
            NSLog(@"取消了");
        }])];[alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"goupload", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            //跳转App store
            NSURL *url = [NSURL URLWithString:@"itms-apps://itunes.apple.com/app/id1570277391"];
            if (@available(iOS 10.0, *)){
                 [[UIApplication sharedApplication]openURL:url options:@{UIApplicationOpenURLOptionsSourceApplicationKey:@YES} completionHandler:^(BOOL success) {
                     if (success) {
                         NSLog(@"10以后可以跳转url");
                     }else{
                         NSLog(@"10以后不可以跳转url");
                     }
                 }];
             }else{
                 BOOL success = [[UIApplication sharedApplication]openURL:url];
                 if (success) {
                      NSLog(@"10以前可以跳转url");
                 }else{
                      NSLog(@"10以前不可以跳转url");
                 }
             }
        }])];
        [self presentViewController:alertController animated:YES completion:nil];
    }else{
        self.versionTip.hidden = YES;
    }
}

- (BOOL)versionCompareOldStr:(NSString *)first andNewStr: (NSString *)second{
    if ([first compare:second options:NSNumericSearch] == NSOrderedDescending)
    {
        return NO;
    }else if ([first compare:second options:NSNumericSearch] == NSOrderedSame)
    {
        return NO;
    }else{
        return YES;
    }
}

@end
