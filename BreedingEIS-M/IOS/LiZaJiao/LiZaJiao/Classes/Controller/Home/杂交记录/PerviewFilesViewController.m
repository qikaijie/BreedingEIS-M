//
//  PerviewFilesViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/20.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "PerviewFilesViewController.h"
#import "LocalUploadFilesCell.h"

#import <AVKit/AVKit.h>
#import "TZImagePreviewController.h"

#import <AVFoundation/AVAsset.h>
#import <AVFoundation/AVAssetImageGenerator.h>
#import <AVFoundation/AVTime.h>

#import "SKFPreViewNavController.h"



@interface PerviewFilesViewController ()<UICollectionViewDelegate,UICollectionViewDataSource,TZImagePickerControllerDelegate>

@property (strong, nonatomic) UIButton *backBtn;

@property (nonatomic,strong) UICollectionView *collectionView;

@end

@implementation PerviewFilesViewController

- (void)viewWillAppear:(BOOL)animated{
    self.navigationController.navigationBar.barTintColor = kTableViewBackCoclor;
    //    self.navigationController.navigationBar.shadowImage = [UIImage new];
    //设置导航栏文字颜色
    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObjectsAndKeys:
                                                                   defaultTextColor,
                                                                   NSForegroundColorAttributeName,
                                                                   [UIFont boldSystemFontOfSize:16],
                                                                   NSFontAttributeName,nil];
    self.navigationController.navigationBar.tintColor = defaultTextColor;
    
    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
    backgroundView.subviews.firstObject.hidden = YES;
}

- (void)viewWillDisappear:(BOOL)animated{
    self.navigationController.navigationBar.barTintColor = [UIColor whiteColor];
    //设置导航栏文字颜色
    self.navigationController.navigationBar.titleTextAttributes = [NSDictionary dictionaryWithObject:[UIColor blackColor] forKey:NSForegroundColorAttributeName];
    self.navigationController.navigationBar.tintColor = [UIColor grayColor];
    
    UIView *backgroundView = [self.navigationController.navigationBar subviews].firstObject;
    backgroundView.subviews.firstObject.hidden = NO;
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
        [self.navigationController popViewControllerAnimated:YES];
    }];
    
    [self initTableView];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (void)initTableView{
    //1.初始化layout
    UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
    //设置collectionView滚动方向
    //    [layout setScrollDirection:UICollectionViewScrollDirectionHorizontal];
    //设置headerView的尺寸大小
    layout.headerReferenceSize = CGSizeMake(kSCREEN_WIDTH, 0.01);
    //该方法也可以设置itemSize
    layout.itemSize =CGSizeMake(kSCREEN_WIDTH/3, 120);
    
    //2.初始化collectionView
    _collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT) collectionViewLayout:layout];
    [self.view addSubview:_collectionView];
    _collectionView.backgroundColor = [UIColor whiteColor];
    
    //3.注册collectionViewCell
    //注意，此处的ReuseIdentifier 必须和 cellForItemAtIndexPath 方法中 一致 均为 cellId
    //    [_collectionView registerClass:[HouseInfoUploadImageCell class] forCellWithReuseIdentifier:@"HouseInfoUploadImageCell"];
    //    [_collectionView registerNib:[UINib nibWithNibName:@"UploadedFilesCell" bundle:nil] forCellWithReuseIdentifier:@"UploadedFilesCell"];
    [_collectionView registerNib:[UINib nibWithNibName:@"LocalUploadFilesCell" bundle:nil] forCellWithReuseIdentifier:@"LocalUploadFilesCell"];
    
    //注册headerView  此处的ReuseIdentifier 必须和 cellForItemAtIndexPath 方法中 一致  均为reusableView
    [_collectionView registerClass:[UICollectionReusableView class] forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:@"reusableView"];
    
    //4.设置代理
    _collectionView.delegate = self;
    _collectionView.dataSource = self;
}


#pragma mark - UICollectionViewDelegate/UICollectionViewDataSource
//返回section个数
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView
{
    return 1;
}

//每个section的item个数
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return self.imageList.count;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    LocalUploadFilesCell *cell = (LocalUploadFilesCell *)[collectionView dequeueReusableCellWithReuseIdentifier:@"LocalUploadFilesCell" forIndexPath:indexPath];
    
    
    NSMutableDictionary *imageDict = [self.imageList[indexPath.row] mutableCopy];
    
    int type = [[imageDict objectForKey:@"type"] intValue];
    NSString *path = [NSString stringWithFormat:@"%@%@",APP_FILE_HTTP,[imageDict objectForKey:@"path"]];
    cell.uploadState.hidden = YES;
    cell.deleteBtn.hidden = YES;
    if(type == 0){
        cell.playMark.hidden = YES;
        [cell.imageView sd_setImageWithURL:[NSURL URLWithString:path] placeholderImage:[UIImage imageNamed:@"image_goods_default"]];
    }else{
        cell.playMark.hidden = NO;
        cell.imageView.image = [self getVideoPreViewImage:[NSURL URLWithString:path]];
    }
    
    [cell.actionView whenTapped:^{
        //预览
//        if(type == 0){
//            [TZImageManager manager].isPreviewNetworkImage = YES;
//            TZImagePickerController *imagePickerVc = [[TZImagePickerController alloc] initWithMaxImagesCount:9 columnNumber:4 delegate:self pushPhotoPickerVc:NO];
//            imagePickerVc.isSelectOriginalPhoto = NO;
//            imagePickerVc.showSelectedIndex = NO;
//            [imagePickerVc setPhotoPreviewPageDidLayoutSubviewsBlock:^(UICollectionView *collectionView, UIView *naviBar, UIButton *backButton, UIButton *selectButton, UILabel *indexLabel, UIView *toolBar, UIButton *originalPhotoButton, UILabel *originalPhotoLabel, UIButton *doneButton, UIImageView *numberImageView, UILabel *numberLabel) {
//                if (numberLabel) {
//                    [numberLabel removeFromSuperview];
//                    numberLabel = nil;
//                }
//                if (numberImageView) {
//                    [numberImageView removeFromSuperview];
//                    numberImageView = nil;
//                }
//                if (doneButton) {
//                    [doneButton removeFromSuperview];
//                    doneButton = nil;
//                }
//                if (selectButton) {
//                    [selectButton removeFromSuperview];
//                    selectButton = nil;
//                }
//            }];
//            NSMutableArray *selectedPhotos = [[NSMutableArray alloc]init];
//            [selectedPhotos addObject:[NSURL URLWithString:path]];
//            TZImagePreviewController *previewVc = [[TZImagePreviewController alloc] initWithPhotos:selectedPhotos currentIndex:0 tzImagePickerVc:imagePickerVc];
//            previewVc.isSelectOriginalPhoto = NO;
//            [previewVc setBackButtonClickBlock:^(BOOL isSelectOriginalPhoto) {
//                //self.isSelectOriginalPhoto = isSelectOriginalPhoto;
//                NSLog(@"预览页 返回 isSelectOriginalPhoto:%d", isSelectOriginalPhoto);
//            }];
//            [previewVc setSetImageWithURLBlock:^(NSURL *URL, UIImageView *imageView, void (^completion)(void)) {
//                [self configImageView:imageView URL:URL completion:completion];
//            }];
//            [previewVc setDoneButtonClickBlock:^(NSArray *photos, BOOL isSelectOriginalPhoto) {
//                //self.isSelectOriginalPhoto = isSelectOriginalPhoto;
//                //self.selectedPhotos = [NSMutableArray arrayWithArray:photos];
//                NSLog(@"预览页 完成 isSelectOriginalPhoto:%d photos.count:%zd", isSelectOriginalPhoto, photos.count);
//                //[self.collectionView reloadData];
//            }];
//            previewVc.modalPresentationStyle = UIModalPresentationFullScreen;
//            [self presentViewController:previewVc animated:YES completion:nil];
//
//        }else if(type == 1){
//            AVPlayer *avPlayer = [[AVPlayer alloc]initWithURL:[NSURL URLWithString:path]];
//            AVPlayerViewController *avPlayerViewController = [[AVPlayerViewController alloc]init];
//            avPlayerViewController.title = NSLocalizedString(@"video.perview", @"");
//            avPlayerViewController.hidesBottomBarWhenPushed = YES;
//            avPlayerViewController.player = avPlayer;
//            avPlayerViewController.modalPresentationStyle = UIModalPresentationFullScreen;
//            [self.navigationController presentViewController:avPlayerViewController animated:YES completion:nil];
//        }
        
        // 新版预览
        NSMutableArray *images = [[NSMutableArray alloc]init];
        for (int i = 0;i<self.imageList.count;i++) {
            NSMutableDictionary *image = [self.imageList[i] mutableCopy];
            int imageType = [[image objectForKey:@"type"] intValue];
            NSString *imagePath = [NSString stringWithFormat:@"%@%@",APP_FILE_HTTP,[image objectForKey:@"path"]];
            
            LocalUploadFilesCell *cell = (LocalUploadFilesCell *)[self.collectionView cellForItemAtIndexPath:[NSIndexPath indexPathForRow:i inSection:indexPath.section]];
            
            if(imageType == 0){
                //图片
                YBIBImageData *data1 = [YBIBImageData new];
                data1.imageURL = [NSURL URLWithString:imagePath];
                data1.projectiveView = cell.imageView;
                [images addObject:data1];
            }else{
                //视频
                YBIBVideoData *data2 = [YBIBVideoData new];
                data2.videoURL = [NSURL URLWithString:imagePath];
                data2.projectiveView = cell.imageView;
                [images addObject:data2];
            }
        }
        YBImageBrowser *browser = [YBImageBrowser new];
        browser.dataSourceArray = [images copy];
        browser.currentPage = indexPath.row;
        [browser show];

    }];
    
    return cell;
}

- (void)configImageView:(UIImageView *)imageView URL:(NSURL *)URL completion:(void (^)(void))completion{
    if ([URL.absoluteString.lowercaseString hasSuffix:@"gif"]) {
        // 先显示静态图占位
//        [[SDWebImageManager sharedManager] downloadImageWithURL:URL options:0 progress:nil completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, BOOL finished, NSURL *imageURL) {
//            if (!imageView.image) {
//                imageView.image = image;
//            }
//        }];
//        // 动图加载完再覆盖掉
//        [[SDWebImageDownloader sharedDownloader] downloadImageWithURL:URL options:0 progress:nil completed:^(UIImage * _Nullable image, NSData * _Nullable data, NSError * _Nullable error, BOOL finished) {
//            imageView.image = [UIImage sd_animatedGIFWithData:data];
//            if (completion) {
//                completion();
//            }
//        }];
        [[SDWebImageDownloader sharedDownloader] downloadImageWithURL:URL options:0 context:nil progress:nil completed:^(UIImage * _Nullable image, NSData * _Nullable data, NSError * _Nullable error, BOOL finished) {
            imageView.image = image;//[UIImage sd_animatedGIFWithData:data];
            if (completion) {
                completion();
            }
        }];
    } else {
        [imageView sd_setImageWithURL:URL completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
            if (completion) {
                completion();
            }
        }];
    }
}

//设置每个item的尺寸
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return CGSizeMake(kSCREEN_WIDTH/3, 120);
}

//设置每个item的UIEdgeInsets
- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout insetForSectionAtIndex:(NSInteger)section
{
    return UIEdgeInsetsMake(0, 0, 0, 0);
}

//设置每个item水平间距
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section
{
    return 0;
}


//设置每个item垂直间距
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section
{
    return 0;
}

//通过设置SupplementaryViewOfKind 来设置头部或者底部的view，其中 ReuseIdentifier 的值必须和 注册是填写的一致，本例都为 “reusableView”
- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath
{
    UICollectionReusableView *headerView = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:@"reusableView" forIndexPath:indexPath];
    headerView.backgroundColor = [UIColor whiteColor];
    return headerView;
}

//点击item方法
- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
}


// 获取视频第一帧
- (UIImage*) getVideoPreViewImage:(NSURL *)path
{
    AVURLAsset *asset = [[AVURLAsset alloc] initWithURL:path options:nil];
    AVAssetImageGenerator *assetGen = [[AVAssetImageGenerator alloc] initWithAsset:asset];
    
    assetGen.appliesPreferredTrackTransform = YES;
    CMTime time = CMTimeMakeWithSeconds(0.0, 600);
    NSError *error = nil;
    CMTime actualTime;
    CGImageRef image = [assetGen copyCGImageAtTime:time actualTime:&actualTime error:&error];
    UIImage *videoImage = [[UIImage alloc] initWithCGImage:image];
    CGImageRelease(image);
    return videoImage;
}



@end
