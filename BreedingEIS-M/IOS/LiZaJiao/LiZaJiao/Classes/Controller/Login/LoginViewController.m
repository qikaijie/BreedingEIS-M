//
//  LoginViewController.m
//  MerchantAssistant
//
//  Created by Apple on 4/1/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import "LoginViewController.h"
#import "LCTabBarController.h"
#import "LoginTextField.h"
#import "SaltedFishTabBarVC.h"
#import "WHPickerGoodsView.h"

@interface LoginViewController ()

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *paddingTopHeight;

@property (weak, nonatomic) IBOutlet UIImageView *logoIV;

@property (weak, nonatomic) IBOutlet UIImageView *titleOne;
@property (weak, nonatomic) IBOutlet UIImageView *titleTwo;



@property (weak, nonatomic) IBOutlet LoginTextField *phoneTF;
@property (weak, nonatomic) IBOutlet LoginTextField *passwordTF;

@property (weak, nonatomic) IBOutlet UIButton *changeLauguageButton;
@property (weak, nonatomic) IBOutlet UIButton *loginBtn;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *centerC;

@end

@implementation LoginViewController

- (void)viewWillAppear:(BOOL)animated{
    self.navigationController.navigationBar.hidden = YES;

}

- (void)viewWillDisappear:(BOOL)animated{
    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.navigationController.navigationBar.translucent = NO;
    self.paddingTopHeight.constant = -kTabbarSafeBottomMargin-22;
    
    NSString *language = [[ChangeLanguage sharedInstance] currentLanguage];
    if ([language isEqualToString:@"zh-Hans"]) {
        [self.changeLauguageButton setTitle:@"语言：中文" forState:UIControlStateNormal];
    }else{
        [self.changeLauguageButton setTitle:@"Lauguage：English" forState:UIControlStateNormal];
    }
    
    self.titleOne.image = [UIImage imageNamed:NSLocalizedString(@"assets_text_yshj", nil)];
    self.titleTwo.image = [UIImage imageNamed:NSLocalizedString(@"assets_text_zyyyzpjxt", nil)];
    
//    self.appNameLabel.font = [UIFont fontWithName:@"LiShu-dospy-fei" size:18];
    
    self.passwordTF.secureTextEntry = YES;
    
//    NSArray *familyNames = [UIFont familyNames];
//    for (NSString *familyName in familyNames) {
//        NSLog(@"Family:%s",[familyName UTF8String]);
//        NSArray *fontNames = [UIFont fontNamesForFamilyName:familyName];
//        for (NSString *fontName in fontNames) {
//            NSLog(@"             Font:%s",[fontName UTF8String]);
//        }
//    }
    
    //清除本地数据
    kSetUserDefaults(USER_TOKEN,@"");
    
    if(!strIsEmpty(kUserDefaults(USER_NAME))){
        self.phoneTF.text = kUserDefaults(USER_NAME);
        if(!strIsEmpty(kUserDefaults(USER_PASS_WORD))){
            self.passwordTF.text = kUserDefaults(USER_PASS_WORD);
        }
    }
    
//    self.phoneTF.text = @"liuxn";
//    self.passwordTF.text = @"test123456";
        
    //增加监听，当键盘出现或改变时收出消息
    [[NSNotificationCenter defaultCenter] addObserver:self
                                              selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    //增加监听，当键退出时收出消息
    [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(keyboardWillHide:)
                                                name:UIKeyboardWillHideNotification
                                              object:nil];
}

//适当的位置移除通知

- (void)dealloc{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

//当键盘出现或改变时调用
- (void)keyboardWillShow:(NSNotification *)aNotification {
    //获取键盘的高度
//    NSDictionary *userInfo = [aNotification userInfo];
//    NSValue *aValue = [userInfo objectForKey:UIKeyboardFrameEndUserInfoKey];
//    CGRect keyboardRect = [aValue CGRectValue];
//    float keyboardHeight = keyboardRect.size.height;
    self.centerC.constant = -44;
    [UIView animateWithDuration:0.25 animations:^{
        [self.view layoutIfNeeded];
    }];

}
//当键退出时调用
- (void)keyboardWillHide:(NSNotification *)aNotification {
    self.centerC.constant = 0;
    [UIView animateWithDuration:0.25 animations:^{
        [self.view layoutIfNeeded];
    }];
}

- (IBAction)changeLauguageClick:(UIButton *)sender {
    
    int selected = [[[ChangeLanguage sharedInstance] currentLanguage] isEqualToString:@"en"] ? 1 : 0;
    // 语言选择
    WHPickerGoodsView *pickerView = [[WHPickerGoodsView alloc]init:@[@{@"lauguage":@"中文"},@{@"lauguage":@"English"}] andIndex:selected showTitleKey:@"lauguage"];
    [pickerView didFinishSelected:^(NSInteger selectIndex) {
        if(selected != selectIndex){
            
            if(selectIndex == 0){
                [[ChangeLanguage sharedInstance] setLanguage:@"zh-Hans"];
                [self.changeLauguageButton setTitle:@"语言：中文" forState:UIControlStateNormal];
            }else{
                //英文
                [[ChangeLanguage sharedInstance] setLanguage:@"en"];
                [self.changeLauguageButton setTitle:@"Lauguage：English" forState:UIControlStateNormal];
            }
            
    //        ((AppDelegate *)[[UIApplication sharedApplication] delegate]).window.rootViewController = [[LoginViewController alloc]init];
    //        [((AppDelegate *)[[UIApplication sharedApplication] delegate]).window makeKeyAndVisible];
            
            UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"lauguate.tip", @"") message:NSLocalizedString(@"lauguate.content", @"")
                                                                              preferredStyle:UIAlertControllerStyleAlert];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"lauguate.done", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                exit(0);
            }])];
            [self presentViewController:alertController animated:YES completion:nil];
        }
    }];
}

//登录
- (IBAction)loginClick:(UIButton *)sender {
    if(strIsEmpty(self.phoneTF.text)){
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"yhsjhbnwk", @"")];
        return;
    }else if(strIsEmpty(self.passwordTF.text)){
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"dlmmbnwk", @"")];
        return;
    }
    //登录
    NSDictionary *parameters = @{@"userName":_phoneTF.text,
                                 @"password":_passwordTF.text};
    [GJHttpTool POST:APP_LOGIN_1 parameters:parameters headerParams:@{} awaitingView:self.view success:^(id responseObject) {
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            NSDictionary *loginDict = [responseObject objectForKey:@"data"];
            //保存用户名和密码
            kSetUserDefaults(USER_NAME,self.phoneTF.text);
            kSetUserDefaults(USER_PASS_WORD,self.passwordTF.text);
            kSetUserDefaults(USER_TOKEN,[loginDict objectForKey:@"token"]);
            
            NSDictionary *userDict = [loginDict objectForKey:@"user"];
            if([userDict objectForKey:@"speciesId"] != nil && [userDict objectForKey:@"speciesId"] != [NSNull null]){
                int speciesId = [[userDict objectForKey:@"speciesId"] intValue];
                kSetUserDefaults(SEPCIES_ID,[NSNumber numberWithInt:speciesId]);
            }else{
                kSetUserDefaults(SEPCIES_ID,[NSNumber numberWithInt:-1]);
            }
            
            //跳转首页
            ((AppDelegate *)[[UIApplication sharedApplication] delegate]).window.rootViewController = [[SaltedFishTabBarVC alloc]init];
            [((AppDelegate *)[[UIApplication sharedApplication] delegate]).window makeKeyAndVisible];
            
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
    }];
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
