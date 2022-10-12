//
//  HomeScheduleViewController.m
//  UinChat
//
//  Created by Apple on 2018/10/11.
//  Copyright © 2018年 GuJie. All rights reserved.
//

#import "HomeScheduleViewController.h"
#import <CMPageTitleView/CMPageTitleView.h>
#import "PJItemViewController.h"
#import "PJDetailsViewController.h"
#import "ZZDetailsViewController.h"

@interface HomeScheduleViewController ()<CMPageTitleViewDelegate>

@property (strong, nonatomic) UIButton *backBtn;

@property (strong, nonatomic) NSMutableArray *groupList;
/**视图控制器数组*/
@property (nonatomic,strong) NSMutableArray *childControllers;
/**default notes*/
@property (nonatomic,weak) CMPageTitleView *pageTitleView;

@property (nonatomic,strong) UIButton *nextBtn;

@end

@implementation HomeScheduleViewController

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
    
    self.childControllers = [[NSMutableArray alloc]init];
    
    self.groupList = [[NSMutableArray alloc]init];
    [self queryData];

}

- (void)queryData{
    [GJHttpTool GET:APP_ATTRIBUTE_GROUP_LIST parameters:@{@"speciesId":[NSNumber numberWithInt:[[self.groupMain objectForKey:@"id"] intValue]]} headerParams:@{} awaitingView:self.view success:^(id responseObject) {
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            NSMutableArray *tmpArr = [[NSMutableArray alloc]init];
            tmpArr = [[responseObject objectForKey:@"data"] mutableCopy];
            for (int i = 0; i<tmpArr.count; i++) {
                NSMutableDictionary *groupDict = [tmpArr[i] mutableCopy];
                NSMutableArray *attributeList = [[groupDict objectForKey:@"attributeList"] mutableCopy];
                for (int j = 0; j<attributeList.count; j++) {
                    //答案条例
                    NSMutableDictionary *attributeDict = [attributeList[j] mutableCopy];
                    [attributeDict setValue:[NSNumber numberWithInt:0] forKey:@"selectValue"];
                    [attributeList setObject:attributeDict atIndexedSubscript:j];
                    [groupDict setValue:attributeList forKey:@"attributeList"];
                    [tmpArr setObject:groupDict atIndexedSubscript:i];
                }
            }
            
            if(tmpArr!=nil && tmpArr!=[NSNull null] && tmpArr.count>0){
                [self.groupList addObjectsFromArray:tmpArr];
                
                //保存这个数值
                kSetUserDefaults(PINGJIA_LIST,[GJUtils objectToJson:self.groupList]);
                
                //添加进去
                for (int i = 0; i<self.groupList.count; i++) {
                    PJItemViewController *pjItemViewController = [[PJItemViewController alloc] initGroupIndex:i];
                    pjItemViewController.title = [NSString stringWithFormat:@"%@",[self.groupList[i] objectForKey:@"name"]];
                    pjItemViewController.view.backgroundColor = kTableViewBackCoclor;
                    //pjItemViewController.groupIndex = [NSString stringWithFormat:@"%d",i];
                    [self.childControllers addObject:pjItemViewController];
                }
                [self reloadConfig];
            }
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

- (void)reloadConfig{
    CMPageTitleView *pageTitleView = [[CMPageTitleView alloc] initWithFrame:CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT-kTabbarSafeBottomMargin-kStatusBarAndNavigationBarHeight-20-38-30)];
    self.pageTitleView = pageTitleView;
    self.pageTitleView.delegate = self;
    CMPageTitleConfig *config = [CMPageTitleConfig defaultConfig];
    config.cm_childControllers = [self.childControllers copy]; //必传参数
    config.cm_switchMode = CMPageTitleSwitchMode_Underline;
    config.cm_underlineStretch = YES;
    config.cm_gradientStyle = CMTitleColorGradientStyle_RGB;
    config.cm_contentMode = CMPageTitleContentMode_Left;
    config.cm_scaleGradientContentMode = CMPageTitleScaleGradientContentMode_Bottom;
    config.cm_font = [UIFont systemFontOfSize:14];
    config.cm_defaultIndex = 0;
    config.cm_backgroundColor = kTableViewBackCoclor;
    config.cm_normalColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1];
    config.cm_selectedColor = navigationBarColor;
    config.cm_underlineColor = navigationBarColor;
    self.pageTitleView.cm_config = config;
    [self.view addSubview:self.pageTitleView];
    
    UILabel *tipsLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, kSCREEN_HEIGHT-kTabbarSafeBottomMargin-kStatusBarAndNavigationBarHeight-20-38-30, kSCREEN_WIDTH, 30)];
    [tipsLabel setText:NSLocalizedString(@"qxxxbcxyjldsx", @"")];
    [tipsLabel setFont:[UIFont systemFontOfSize:10]];
    [tipsLabel setTextColor:[UIColor orangeColor]];
    [tipsLabel setTextAlignment:NSTextAlignmentCenter];
    [self.view addSubview:tipsLabel];
    
    self.nextBtn = [[UIButton alloc]initWithFrame:CGRectMake(30, kSCREEN_HEIGHT-kTabbarSafeBottomMargin-kStatusBarAndNavigationBarHeight-20-38, kSCREEN_WIDTH-60, 38)];
    self.nextBtn.backgroundColor = navigationBarColor;
    [self.nextBtn setTitle:NSLocalizedString(@"next", @"") forState:UIControlStateNormal];
    self.nextBtn.layer.cornerRadius = 6;
    self.nextBtn.layer.masksToBounds = YES;
    [self.nextBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.nextBtn setFont:[UIFont boldSystemFontOfSize:14]];
    [self.view addSubview:self.nextBtn];
    
    /**
     下一步
     */
    [self.nextBtn whenTapped:^{
        NSMutableArray *groupListNew = [GJUtils jsonToObject:kUserDefaults(PINGJIA_LIST)];
        NSString *groupListNewStr = [GJUtils objectToJson:groupListNew];
        NSLog(@"%@",groupListNewStr);
        
        NSMutableArray *groupListBeanList = [[NSMutableArray alloc]init];
        for (int i = 0;i<groupListNew.count;i++){
            //临时存储进此数组
            NSMutableDictionary *groupListBeanTemp = [groupListNew[i] mutableCopy];
            [groupListBeanTemp setObject:[[NSMutableArray alloc] init] forKey:@"attributeList"];

            NSMutableDictionary *tempGroupListBean = [groupListNew[i] mutableCopy];

            NSMutableArray *tempAttributeListBean = [[tempGroupListBean objectForKey:@"attributeList"] mutableCopy];

            //临时存储进此数组
            NSMutableArray *attributeListBeanListTemp = [[NSMutableArray alloc]init];

            for (int j = 0;j<tempAttributeListBean.count;j++){
                if([[tempAttributeListBean[j] objectForKey:@"selectValue"] intValue] == 1){
                    //加入选择数据中
                    NSMutableDictionary *alB =  [tempAttributeListBean[j] mutableCopy];
                    NSString *fieldTypeStr = [alB objectForKey:@"fieldType"];
                    if([fieldTypeStr isEqualToString:@"checkbox"] || [fieldTypeStr isEqualToString:@"radio"]){
                        NSString *fieldContentStr = [alB objectForKey:@"fieldContent"];
                        NSArray *fieldContentList = [fieldContentStr componentsSeparatedByString:@"|"];
                        NSMutableArray *selectResultBeanList = [[NSMutableArray alloc]init];
                        for (int x = 0;x<fieldContentList.count;x++){
                            NSMutableDictionary *selectResultBean = [[NSMutableDictionary alloc] init];
                            [selectResultBean setObject:[NSString stringWithFormat:@"%@",fieldContentList[x]] forKey:@"title"];
                            [selectResultBean setObject:[NSNumber numberWithInt:0] forKey:@"selectValue"];
                            [selectResultBeanList addObject:selectResultBean];
                        }
                        [alB setObject:selectResultBeanList forKey:@"results"];
                    }else{
                        [alB setObject:@"" forKey:@"resultStr"];
                    }
                    [attributeListBeanListTemp addObject:alB];
                }
            }
            if(attributeListBeanListTemp.count>0){
                [groupListBeanTemp setObject:attributeListBeanListTemp forKey:@"attributeList"];
                //存储进总数据
                [groupListBeanList addObject:groupListBeanTemp];
            }
        }

        if(groupListBeanList.count>0){
            /**
             *  存储选择后的属性列表
             */
            if(self.businessType == 0){
                PJDetailsViewController *pjDetailsViewController = [[PJDetailsViewController alloc]init];
                pjDetailsViewController.title = [NSString stringWithFormat:@"%@-%@",NSLocalizedString(@"zjpj", @""),[self.groupMain objectForKey:@"name"]];
                pjDetailsViewController.businessType = self.businessType;
                pjDetailsViewController.hidesBottomBarWhenPushed = YES;
                pjDetailsViewController.groupListBeanList = groupListBeanList;
                [self.navigationController pushViewController:pjDetailsViewController animated:YES];
            }else{
                ZZDetailsViewController *zzDetailsViewController = [[ZZDetailsViewController alloc]init];
                zzDetailsViewController.title = [NSString stringWithFormat:@"%@-%@",NSLocalizedString(@"zztb", @""),[self.groupMain objectForKey:@"name"]];
                zzDetailsViewController.businessType = self.businessType;
                zzDetailsViewController.hidesBottomBarWhenPushed = YES;
                zzDetailsViewController.groupListBeanList = groupListBeanList;
                [self.navigationController pushViewController:zzDetailsViewController animated:YES];
            }
        }else{
            [self.view toast:NSLocalizedString(@"qxxxtbdsx", @"")];
        }
        
    }];
    
}

@end
