//
//  BaseViewController.m
//  storemanager
//
//  Created by Apple on 18/3/5.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import "BaseViewController.h"

@interface BaseViewController ()

@end

@implementation BaseViewController

//- (void)viewWillAppear:(BOOL)animated{
//    self.navigationController.navigationBar.barTintColor = navigationBarColor;
//    //    self.navigationController.navigationBar.shadowImage = [UIImage new];
//    //设置导航栏文字颜色
//    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObjectsAndKeys:
//                                                                   [UIColor whiteColor],
//                                                                   NSForegroundColorAttributeName,
//                                                                   [UIFont systemFontOfSize:17],
//                                                                   NSFontAttributeName,nil];
//    self.navigationController.navigationBar.tintColor = [UIColor whiteColor];
//    
//    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
//    backgroundView.subviews.firstObject.hidden = YES;
//}
//
//- (void)viewWillDisappear:(BOOL)animated{
//    self.navigationController.navigationBar.barTintColor = [UIColor whiteColor];
//    //设置导航栏文字颜色
//    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObject:[UIColor blackColor] forKey:NSForegroundColorAttributeName];
//    self.navigationController.navigationBar.tintColor = [UIColor grayColor];
//    
//    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
//    backgroundView.subviews.firstObject.hidden = NO;
//}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:self action:nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    [self.view.window endEditing:YES];
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
