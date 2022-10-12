//
//  FeedBackAddViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/7/17.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "FeedBackAddViewController.h"
#import "UITextView+Placeholder.h"

@interface FeedBackAddViewController ()

@property (strong, nonatomic) UIButton *backBtn;
@property (weak, nonatomic) IBOutlet UIView *inputView;
@property (weak, nonatomic) IBOutlet UITextView *inputTextview;
@property (weak, nonatomic) IBOutlet UIButton *sumbitView;

@end

@implementation FeedBackAddViewController

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
    
    self.inputView.layer.borderWidth = 1;
    self.inputView.layer.borderColor = [UIColor colorWithRed:192/255.0 green:192/255.0 blue:192/255.0 alpha:0.5].CGColor;
    self.inputView.layer.masksToBounds = YES;
    self.inputView.layer.cornerRadius = 10;
    
    self.inputTextview.placeholder = NSLocalizedString(@"faceback.tip", @"");
}

- (IBAction)sumbitButtonOnClick:(UIButton *)sender {
    [self sumbitData];
}

- (void)sumbitData{
    /**
     {
       "channel": 0,
       "content": "string",
       "createTime": "2021-07-17T08:20:12.678Z",
       "id": 0,
       "reply": "string",
       "status": 0,
       "userId": 0,
       "username": "string"
     }
     */
    if(!strIsEmpty(self.inputTextview.text)){
        NSMutableDictionary *dataDict = [[NSMutableDictionary alloc]init];
        NSMutableDictionary *params = [[NSMutableDictionary alloc]init];
        [params setObject:self.inputTextview.text forKey:@"content"];
        [params setObject:[NSNumber numberWithInt:2] forKey:@"channel"];
        [params setObject:[NSString stringWithFormat:@"%@",[self.userInfo objectForKey:@"nickName"]] forKey:@"username"];
        [params setObject:[NSString stringWithFormat:@"%@",[GJDateUtils getCurrentDate:@"yyyy-MM-dd HH:mm:ss"]] forKey:@"createTime"];
        [dataDict setObject:params forKey:@"feedback"];
        
        [GJHttpTool POST:APP_FEEDBACK_ADD parameters:params headerParams:@{} awaitingView:self.view success:^(id responseObject) {
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    [SVProgressHUD showSuccessWithStatus:NSLocalizedString(@"send.success", @"")];
                    [self.navigationController popViewControllerAnimated:YES];
                }else{
                    [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                }
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            NSLog(@"%@",error);
        }];
    }else{
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"cannotempty", @"")];
    }
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
