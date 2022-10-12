//
//  FeedBackViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/7/17.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "FeedBackViewController.h"
#import "FeedBackCell.h"
#import "FeedBackAddViewController.h"

@interface FeedBackViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong, nonatomic) UIButton *backBtn;

@property (strong, nonatomic) NSMutableArray *dataArray;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UILabel *emptyDataView;

@end

@implementation FeedBackViewController

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
    
    [self queryData];
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
    
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"add", @"") style:UIBarButtonItemStylePlain target:self action:@selector(addFeedback)];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    self.dataArray = [[NSMutableArray alloc]init];
    [self initTableView];
}

- (void)addFeedback{
    
    [GJHttpTool POST:APP_LOGIN_2 parameters:@{} headerParams:@{} awaitingView:nil success:^(id responseObject) {
        if([[responseObject objectForKey:@"code"] intValue] == 200){
            FeedBackAddViewController *feedBackAddViewController = [[FeedBackAddViewController alloc]init];
            feedBackAddViewController.title = NSLocalizedString(@"add.faceback", @"");
            feedBackAddViewController.userInfo = [[responseObject objectForKey:@"data"] mutableCopy];
            feedBackAddViewController.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:feedBackAddViewController animated:YES];
            
        }else{
            [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
        }
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
    }];
    
}

- (void)initTableView {
    [self.tableView setBackgroundColor:[UIColor clearColor]];//kTableViewBackCoclor
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
//    // 下拉刷新
//    self.tableView.mj_footer= [MJRefreshBackNormalFooter footerWithRefreshingBlock:^{
//        [self queryData];
//    }];
    
    // 设置自动切换透明度(在导航栏下面自动隐藏)
    self.tableView.mj_header.automaticallyChangeAlpha = YES;
}

- (void)queryData{
    NSMutableDictionary *params = [[NSMutableDictionary alloc]init];
    [GJHttpTool GET:APP_FEEDBACK_LIST parameters:params headerParams:@{} awaitingView:nil success:^(id responseObject) {
            [self.tableView.mj_header endRefreshing];
            if([[responseObject objectForKey:@"code"] intValue] == 200){
                //赋值列表
                NSArray *tmpArr = [NSArray array];
                [self.dataArray removeAllObjects];
                tmpArr = [responseObject objectForKey:@"data"];
                [self.dataArray addObjectsFromArray:tmpArr];
                [self.tableView.mj_footer resetNoMoreData];
                [self.tableView reloadData];
                [self doForceScrollToBottom];
                self.emptyDataView.hidden = self.dataArray.count>0;
            }else{
                [SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
            }
        } failure:^(NSError *error) {
            [self.tableView.mj_header endRefreshing];
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

#pragma mark - UITableViewDelegate/UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    CGFloat height = 45;
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    NSString *contentStr = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"content"]];
    //文本最大宽度
    float labelMaxWidth = SCREEN_WIDTH - (80+60+20);
    
    //计算占据了几行
    CGSize label1Size = [contentStr boundingRectWithSize:CGSizeMake(labelMaxWidth,MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
    if(label1Size.height < 25){
       //不超过一行
        height += 40;
    }else{
        height += label1Size.height+25;
    }
    if(!strIsEmpty([dataDict objectForKey:@"reply"])){
        height += 35;
       NSString *replyStr = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"reply"]];
       CGSize label2Size = [replyStr boundingRectWithSize:CGSizeMake(labelMaxWidth,MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
       if(label2Size.height < 25){
           //不超过一行
           height += 40;
       }else{
           height += label2Size.height+25;
       }
    }
    
    return height;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 0.01;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView *heaView = [[UIView alloc]init];
    [heaView setBackgroundColor:[UIColor clearColor]];
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 0.01)];
    return heaView;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section{
    UIView *heaView = [[UIView alloc]init];
    [heaView setBackgroundColor:[UIColor clearColor]];
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 15)];
    return heaView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 15;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    
    FeedBackCell *feedBackCell = [FeedBackCell cellInTableView:tableView withIdentifier:identifier];
    
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
    NSDictionary *dataDict = self.dataArray[indexPath.row];
    
    feedBackCell.createTimeLabel.text = [NSString stringWithFormat:@"  %@  ",[dataDict objectForKey:@"createTime"]];
    
    NSString *contentStr = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"content"]];
    //文本最大宽度
    float labelMaxWidth = SCREEN_WIDTH - (80+60+20);
    //计算占据了几行
    CGSize label1Size = [contentStr boundingRectWithSize:CGSizeMake(labelMaxWidth,MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
    if(label1Size.height < 20){
        //不超过一行
        CGSize label1SizeNew = [contentStr boundingRectWithSize:CGSizeMake(MAXFLOAT,20) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
        feedBackCell.dialog1Width.constant = label1SizeNew.width+25;
        feedBackCell.dialog1Height.constant = 40;
    }else{
        feedBackCell.dialog1Width.constant = labelMaxWidth+25;
        feedBackCell.dialog1Height.constant = label1Size.height+25;
    }
    feedBackCell.dialog1Label.text = contentStr;
    
    if(!strIsEmpty([dataDict objectForKey:@"reply"])){
        NSString *replyStr = [NSString stringWithFormat:@"%@",[dataDict objectForKey:@"reply"]];
        CGSize label2Size = [replyStr boundingRectWithSize:CGSizeMake(labelMaxWidth,MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
        if(label2Size.height < 20){
            //不超过一行
            CGSize label2SizeNew = [contentStr boundingRectWithSize:CGSizeMake(MAXFLOAT,20) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:14]} context:nil].size;
            feedBackCell.dialog2Width.constant = label2SizeNew.width+25;
            feedBackCell.dialog2Height.constant = 40;
        }else{
            feedBackCell.dialog2Width.constant = labelMaxWidth+25;
            feedBackCell.dialog2Height.constant = label2Size.height+25;
        }
        feedBackCell.dialog2Label.text = replyStr;
    }else{
        // 隐藏回复框
        feedBackCell.dialog2View.hidden = YES;
    }
    
    return feedBackCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
}

-(void) doForceScrollToBottom
{
    dispatch_async(dispatch_get_main_queue(), ^
                   {
                      if(self.tableView.contentSize.height - self.tableView.contentOffset.y > self.tableView.frame.size.height )
                       {
                          [self.tableView setContentOffset:CGPointMake(0, self.tableView.contentSize.height - self.tableView.frame.size.height)];
                       }
                      
                      usleep(5000);
                      
                      NSIndexPath* path = [NSIndexPath indexPathForRow: self.dataArray.count - 1 inSection:0];
                      if(self.dataArray.count>0 && path )
                       {
                          [self.tableView scrollToRowAtIndexPath:path atScrollPosition:UITableViewScrollPositionBottom animated:NO];
                       }
                });
}

@end
