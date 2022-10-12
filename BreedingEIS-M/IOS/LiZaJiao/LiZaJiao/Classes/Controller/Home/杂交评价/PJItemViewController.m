//
//  PJItemViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/16.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "PJItemViewController.h"
#import "PJItemCell.h"

@interface PJItemViewController ()<UITableViewDelegate,UITableViewDataSource>{
    Boolean isSelectAll;
}

//全选
@property (weak, nonatomic) IBOutlet UIView *selectAllView;
@property (weak, nonatomic) IBOutlet UIImageView *selectAllImage;

@property (strong, nonatomic) NSMutableArray *groupList;
@property (strong, nonatomic) NSMutableDictionary *groupDict;
@property (strong, nonatomic) NSMutableArray *attributeList;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation PJItemViewController

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
    
    [self checkQuanxuanState];
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

- (instancetype)initGroupIndex:(int)index
{
    //1.初始化父类
    self = [super init];
    //2.判断父类是否初始化成功
    if(self)
    {
        //3.初始化子类
        self.groupIndex = [NSString stringWithFormat:@"%d",index];
    }
    //4.返回地址
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.groupList = [[NSMutableArray alloc]init];
    self.groupDict = [[NSMutableDictionary alloc]init];
    self.attributeList = [[NSMutableArray alloc]init];
    
    NSLog(@"页面编号:%@",self.groupIndex);
    
    self.groupList = [GJUtils jsonToObject:kUserDefaults(PINGJIA_LIST)];
    self.groupDict = [self.groupList[[self.groupIndex intValue]] mutableCopy];
    self.attributeList = [[self.groupDict objectForKey:@"attributeList"] mutableCopy];
    
    [self initTableView];
    
    isSelectAll = false;
    self.selectAllImage.image = [UIImage imageNamed:@"icon_checkbox_normal"];
    [self.selectAllView whenTapped:^{
        //全选或取消
        self -> isSelectAll = !self -> isSelectAll;

        //创建临时对象
        for (int i = 0;i<self.attributeList.count;i++){
            NSMutableDictionary *tempDict = [self.attributeList[i] mutableCopy];
            [tempDict setObject:[NSNumber numberWithInt:(self -> isSelectAll ? 1 : 0)] forKey:@"selectValue"];
            [self.attributeList replaceObjectAtIndex:i withObject:tempDict];
        }
        self.selectAllImage.image = [UIImage imageNamed:(self -> isSelectAll ? @"icon_checkbox_selected" : @"icon_checkbox_normal")];

        [self.groupDict setObject:self.attributeList forKey:@"attributeList"];
        [self.groupList replaceObjectAtIndex:[self.groupIndex intValue] withObject:self.groupDict];
        //刷新主页数据
//        kSetUserDefaults(PINGJIA_LIST,[GJUtils objectToJson:self.groupList]);
        [self resfashData];

        [self.tableView reloadData];
    }];
    
}

/**
 * 检查全选状态
 */
- (void)checkQuanxuanState{
    isSelectAll = true;
    for (int i = 0;i<self.attributeList.count;i++){
        if([[self.attributeList[i] objectForKey:@"selectValue"] intValue] == 0){
            //不是选中的
            isSelectAll = false;
            break;
        }
    }
    self.selectAllImage.image = [UIImage imageNamed:(self -> isSelectAll ? @"icon_checkbox_selected" : @"icon_checkbox_normal")];
}

- (void)initTableView {
    //[self.tableView setBackgroundColor:kTableViewBackCoclor];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
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
    return self.attributeList.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 44.5;
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
    [heaView setFrame:CGRectMake(0, 0, kSCREEN_WIDTH, 0.01)];
    return heaView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 0.01;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *identifier=[NSString stringWithFormat:@"cell_%d_%d",(int)indexPath.section,(int)indexPath.row];
    
    NSMutableDictionary *attributeDict = self.attributeList[indexPath.row];
    
    PJItemCell *pjItemCell = [PJItemCell cellInTableView:tableView withIdentifier:identifier];
    pjItemCell.selectItemTitleLabel.text = [attributeDict objectForKey:@"fieldName"];
    pjItemCell.selectItemImage.image = [UIImage imageNamed:[[attributeDict objectForKey:@"selectValue"] intValue] == 0 ? @"icon_checkbox_normal" : @"icon_checkbox_selected"];
    return pjItemCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES]; //返回后取消cell选中状态
    
    NSMutableDictionary *attributeDict = self.attributeList[indexPath.row];

    //选择或取消选择
    if([[attributeDict objectForKey:@"selectValue"] intValue] == 1){
        //已经是选中状态了
        [attributeDict setObject:[NSNumber numberWithInt:0] forKey:@"selectValue"];
    }else{
        //未选中状态
        [attributeDict setObject:[NSNumber numberWithInt:1] forKey:@"selectValue"];
    }
    [self.attributeList replaceObjectAtIndex:indexPath.row withObject:attributeDict];
    
    [self.groupDict setObject:self.attributeList forKey:@"attributeList"];
    [self.groupList replaceObjectAtIndex:[self.groupIndex intValue] withObject:self.groupDict];
    //刷新主页数据
//    kSetUserDefaults(PINGJIA_LIST,[GJUtils objectToJson:self.groupList]);
    [self resfashData];

    //检查全选状态
    [self checkQuanxuanState];
    [self.tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationNone];
}


/**
 刷新数据
 */
- (void)resfashData{
    
    NSMutableArray *tempGroupList = [GJUtils jsonToObject:kUserDefaults(PINGJIA_LIST)];
    //刷新主页数据
    for (int i = 0;i<tempGroupList.count;i++) {
        if(i != [self.groupIndex intValue]){
            [self.groupList replaceObjectAtIndex:i withObject:tempGroupList[i]];
        }
    }
    kSetUserDefaults(PINGJIA_LIST,[GJUtils objectToJson:self.groupList]);
}

@end
