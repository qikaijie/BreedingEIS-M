//
//  UpdatingFilesViewController.m
//  SupplyChain
//
//  Created by Apple on 7/2/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import "UpdatingFilesViewController.h"
#import "LocalUploadFilesCell.h"

#import <AVKit/AVKit.h>

//$(PRODUCT_NAME:c99extidentifier)
#import "LiZaJiao-Swift.h"

#import "TZImagePreviewController.h"

#import "SKFPreViewNavController.h"

#import <AssetsLibrary/AssetsLibrary.h>

@interface UpdatingFilesViewController ()<UICollectionViewDelegate,UICollectionViewDataSource,TZImagePickerControllerDelegate>

@property (strong, nonatomic) UIButton *backBtn;

@property (nonatomic,strong) UICollectionView *collectionView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bottomViewHeight;

/**
  文件类型
  0    图片
  1    视频
 private int fileType;

 //本地文件名
 private String localName;
 //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
 private String imageLocalPath;
 //获取拍摄的视频路径
 private String videoLocalPath;
 //是否上传
 private int uploadValue;
 private String urlPath;
 */

@end

@implementation UpdatingFilesViewController

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

- (void)breakValue:(BreakValueBlock)block{
    self.breakValueBlock = block;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.navigationController.navigationBar.translucent = NO;
    
    self.bottomViewHeight.constant = 48+kTabbarSafeBottomMargin;
        
    self.backBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 28, 28)];
    [self.backBtn.widthAnchor constraintEqualToConstant:28].active = YES;
    [self.backBtn.heightAnchor constraintEqualToConstant:28].active = YES;
    [self.backBtn setImage:[UIImage imageNamed:@"icon_return"] forState:UIControlStateNormal];
    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:self.backBtn];
    self.navigationItem.leftBarButtonItem = leftItem;
    [self.backBtn whenTapped:^{
        Boolean isUploadAll = YES;//默认全部上传了
        for (int i = 0;i<self.imageList.count-1;i++){
            if([[self.imageList[i] objectForKey:@"uploadValue"] intValue] == 0){
                isUploadAll = NO;
            }
        }
        if(isUploadAll){
            //全部上传了直接退出当前页
            [self.navigationController popViewControllerAnimated:YES];
        }else{
            UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:NSLocalizedString(@"yzpwsc", @"")
                                                                              preferredStyle:UIAlertControllerStyleAlert];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"jxtc", @"") style:UIAlertActionStyleDestructive handler:^(UIAlertAction * _Nonnull action) {
                [self.navigationController popViewControllerAnimated:YES];
            }])];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"lijishangchuan", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                [self upLoadImages:0];
            }])];
            [self presentViewController:alertController animated:YES completion:nil];
        }
        
    }];
    
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.2 * NSEC_PER_SEC));
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        [self initTableView];
     });

    
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
    _collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(0, 0, kSCREEN_WIDTH, kSCREEN_HEIGHT-kNavigationBarHeight-kStatusBarHeight-kTabbarSafeBottomMargin-48) collectionViewLayout:layout];
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
    
    if(indexPath.row != self.imageList.count-1){
        NSMutableDictionary *imageDict = [self.imageList[indexPath.row] mutableCopy];
        int fileType = [[imageDict objectForKey:@"fileType"] intValue];
        int uploadValue = [[imageDict objectForKey:@"uploadValue"] intValue];
        NSString *localName = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"localName"]];
        NSString *imageLocalPath = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"imageLocalPath"]];
        NSString *videoLocalPath = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"videoLocalPath"]];
        NSString *urlPath = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"urlPath"]];
        
        cell.deleteBtn.hidden = NO;
        cell.playMark.hidden = (fileType == 0);
        cell.uploadState.hidden = NO;
        
        if(uploadValue == 0){
            //未上传
            [cell.uploadState setBackgroundColor:[UIColor orangeColor]];
            cell.imageView.image = [UIImage imageWithContentsOfFile:imageLocalPath];
            [cell.uploadState setText:NSLocalizedString(@"weishangchuan", @"")];
        }else if(uploadValue == 1){
            //已上传
            [cell.uploadState setBackgroundColor:navigationBarColor];
            if(fileType == 0){
                [cell.imageView sd_setImageWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"%@%@",APP_FILE_HTTP,urlPath]]];
            }else{
                cell.imageView.image = [UIImage imageNamed:@"AppIcon"];
            }
            [cell.uploadState setText:NSLocalizedString(@"yishangchuan", @"")];
        }
        
        [cell.deleteBtn whenTapped:^{
            UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:NSLocalizedString(@"sfqrscgfj", @"")
                                                                              preferredStyle:UIAlertControllerStyleAlert];
            [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
                NSLog(@"取消了");
            }])];[alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"yes", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                //删除
                [self.imageList removeObjectAtIndex:indexPath.row];
                [self.collectionView reloadData];
            }])];
            [self presentViewController:alertController animated:YES completion:nil];
        }];
        [cell.actionView whenTapped:^{
            //预览
            if(fileType == 0){
                
                NSMutableArray *selectedPhotos = [[NSMutableArray alloc]init];
                [selectedPhotos addObject:[UIImage imageWithContentsOfFile:imageLocalPath]];
                SKFPreViewNavController *imagePickerVc = [[SKFPreViewNavController alloc] initWithSelectedPhotos:selectedPhotos index:0 DeletePic:NO];
                [self presentViewController:imagePickerVc animated:YES completion:nil];
                
            }else if(fileType == 1){
                AVPlayer *avPlayer = [[AVPlayer alloc]initWithURL:(uploadValue == 0 ? [NSURL fileURLWithPath:videoLocalPath] : [NSURL URLWithString:[NSString stringWithFormat:@"%@%@",APP_FILE_HTTP,urlPath]])];
                AVPlayerViewController *avPlayerViewController = [[AVPlayerViewController alloc]init];
                avPlayerViewController.title = NSLocalizedString(@"video.perview", @"");
                avPlayerViewController.hidesBottomBarWhenPushed = YES;
                avPlayerViewController.player = avPlayer;
                avPlayerViewController.modalPresentationStyle = UIModalPresentationFullScreen;
                [self.navigationController presentViewController:avPlayerViewController animated:YES completion:nil];
            }
        }];
    }else{
        cell.deleteBtn.hidden = YES;
        cell.playMark.hidden = YES;
        cell.uploadState.hidden = YES;
        cell.imageView.image = [UIImage imageNamed:NSLocalizedString(@"icon_takephoto_add", nil)];
        [cell.actionView whenTapped:^{
            WMCameraViewController *wmCameraViewController = [[WMCameraViewController alloc]init];
            wmCameraViewController.hidesBottomBarWhenPushed = YES;
            wmCameraViewController.modalPresentationStyle = UIModalPresentationFullScreen;
            wmCameraViewController.videoMaxLength = 15;
            wmCameraViewController.fileUrlBlock = ^(NSString *url,NSString *type){
                if([type isEqualToString:@"image"]){
                    //图片
                    NSLog(@"图片->%@",url);
                    UIImage *oldImg = [UIImage imageWithContentsOfFile:url];
                    UIImage *compressImg = [GJImageUtils scaleImage:oldImg toScale:1];
                    UIImage *newImg = [self imageWithimage:compressImg content:self.plantCode frame:CGRectMake(0, 0, compressImg.size.width, 30)];
                    NSString *fileName = [NSString stringWithFormat:@"%ld.jpg",(long)[GJDateUtils getNowTimestamp]];
                    NSString *imagePath = [self saveImage:newImg andFilename:fileName];
                    
                    NSMutableDictionary *imgDict = [[NSMutableDictionary alloc]init];
                    [imgDict setObject:[NSNumber numberWithInt:0] forKey:@"fileType"];
                    [imgDict setObject:fileName forKey:@"localName"];
                    [imgDict setObject:imagePath forKey:@"imageLocalPath"];
                    [imgDict setObject:@"" forKey:@"videoLocalPath"];
                    [imgDict setObject:[NSNumber numberWithInt:0] forKey:@"uploadValue"];
                    [imgDict setObject:@"" forKey:@"urlPath"];
                    [self.imageList insertObject:imgDict atIndex:self.imageList.count-1];

                    [MBProgressHUD hideHUDForView:[UIApplication sharedApplication].delegate.window animated:YES];
                    [self.collectionView reloadData];
                }else if([type isEqualToString:@"video"]){
                    //视频
                    NSLog(@"视频->%@",url);
                    __block UpdatingFilesViewController *strongBlock = self;
                    [self addWaterTextWithVideoPath:url andStr:self.plantCode complete:^(NSString *compressStr) {
                        //输出后地址
                        NSLog(@"导出后视频——————》%@",compressStr);
                        NSURL *path = [NSURL fileURLWithPath:compressStr];
                        // 获取第一帧图片
                        AVURLAsset *asset = [[AVURLAsset alloc] initWithURL:path options:nil];
                        AVAssetImageGenerator *generate = [[AVAssetImageGenerator alloc] initWithAsset:asset];
                        generate.appliesPreferredTrackTransform = YES;
                        NSError *err = NULL;
                        CMTime time = CMTimeMake(1, 2);
                        CGImageRef oneRef = [generate copyCGImageAtTime:time actualTime:NULL error:&err];
                        UIImage *oneImg = [[UIImage alloc] initWithCGImage:oneRef];
                        NSString *fileName = [NSString stringWithFormat:@"%ld.jpg",(long)[GJDateUtils getNowTimestamp]];
                        NSString *imagePath = [strongBlock saveImage:oneImg andFilename:fileName];
                        
                        NSMutableDictionary *imgDict = [[NSMutableDictionary alloc]init];
                        [imgDict setObject:[NSNumber numberWithInt:1] forKey:@"fileType"];
                        [imgDict setObject:[path lastPathComponent] forKey:@"localName"];
                        [imgDict setObject:imagePath forKey:@"imageLocalPath"];
                        [imgDict setObject:compressStr forKey:@"videoLocalPath"];
                        [imgDict setObject:[NSNumber numberWithInt:0] forKey:@"uploadValue"];
                        [imgDict setObject:@"" forKey:@"urlPath"];
                        [strongBlock.imageList insertObject:imgDict atIndex:self.imageList.count-1];

                        [MBProgressHUD hideHUDForView:[UIApplication sharedApplication].delegate.window animated:YES];
                        [strongBlock.collectionView reloadData];
                    }];
                }
            };
            [self presentViewController:wmCameraViewController animated:YES completion:nil];
        }];
    }
    
    return cell;
}

- (void)configImageView:(UIImageView *)imageView URL:(NSURL *)URL completion:(void (^)(void))completion{
    if ([URL.absoluteString.lowercaseString hasSuffix:@"gif"]) {
//        // 先显示静态图占位
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

- (NSString *)saveImage:(UIImage *)image andFilename:(NSString *)filename {
    NSArray *paths =NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *filePath = [[paths objectAtIndex:0] stringByAppendingPathComponent:filename];  // 保存文件的名称
    BOOL result = [UIImagePNGRepresentation(image) writeToFile:filePath atomically:YES]; // 保存成功会返回YES
    if (result == YES) {
        NSLog(@"保存成功");
        return filePath;
    }
    return @"";
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




#define degreesToRadians( degrees ) ( ( degrees ) / 180.0 * M_PI )
#define textLabelHeight 300
#define textLabelFontSize 30

- (void)addWaterTextWithVideoPath:(NSString*)path andStr:(NSString *)str complete:(void (^)(NSString *compressStr))complete{
    //1 创建AVAsset实例
    AVURLAsset *videoAsset = [AVURLAsset assetWithURL:[NSURL fileURLWithPath:path]];
    
    AVMutableComposition *mixComposition = [[AVMutableComposition alloc] init];
    
    //3 视频通道
    AVMutableCompositionTrack *videoTrack = [mixComposition addMutableTrackWithMediaType:AVMediaTypeVideo
                                                                        preferredTrackID:kCMPersistentTrackID_Invalid];
    [videoTrack insertTimeRange:CMTimeRangeMake(kCMTimeZero, videoAsset.duration)
                        ofTrack:[[videoAsset tracksWithMediaType:AVMediaTypeVideo] firstObject]
                         atTime:kCMTimeZero error:nil];
    
    //2 音频通道
    AVMutableCompositionTrack *audioTrack = [mixComposition addMutableTrackWithMediaType:AVMediaTypeAudio
                                                                        preferredTrackID:kCMPersistentTrackID_Invalid];
    [audioTrack insertTimeRange:CMTimeRangeMake(kCMTimeZero, videoAsset.duration)
                        ofTrack:[[videoAsset tracksWithMediaType:AVMediaTypeAudio] firstObject]
                         atTime:kCMTimeZero error:nil];
    
    //3.1 AVMutableVideoCompositionInstruction 视频轨道中的一个视频，可以缩放、旋转等
    AVMutableVideoCompositionInstruction *mainInstruction = [AVMutableVideoCompositionInstruction videoCompositionInstruction];
    mainInstruction.timeRange = CMTimeRangeMake(kCMTimeZero, videoAsset.duration);
    
    // 3.2 AVMutableVideoCompositionLayerInstruction 一个视频轨道，包含了这个轨道上的所有视频素材
    AVMutableVideoCompositionLayerInstruction *videolayerInstruction = [AVMutableVideoCompositionLayerInstruction videoCompositionLayerInstructionWithAssetTrack:videoTrack];
    
    [videolayerInstruction setOpacity:0.0 atTime:videoAsset.duration];
    //AVMutableVideoComposition：管理所有视频轨道，水印添加就在这上面
    AVMutableVideoComposition *mainCompositionInst = [AVMutableVideoComposition videoComposition];
    
    //视频旋转处理
    CGAffineTransform t1 = CGAffineTransformMakeTranslation(videoTrack.naturalSize.height, 0.0);
    // Rotate transformation
    CGAffineTransform  t2 = CGAffineTransformRotate(t1, degreesToRadians(90.0));
    [videolayerInstruction setTransform:t2 atTime:kCMTimeZero];
    
    // 3.3 - Add instructions
    mainInstruction.layerInstructions = [NSArray arrayWithObjects:videolayerInstruction,nil];
    
    
    AVAssetTrack *videoAssetTrack = [[videoAsset tracksWithMediaType:AVMediaTypeVideo] firstObject];
//    CGSize naturalSize = videoAssetTrack.naturalSize;
    CGSize naturalSize = CGSizeMake(videoAssetTrack.naturalSize.height, videoAssetTrack.naturalSize.width);
    
    float renderWidth, renderHeight;
    renderWidth = naturalSize.width;
    renderHeight = naturalSize.height;
    mainCompositionInst.renderSize = CGSizeMake(renderWidth, renderHeight);
    mainCompositionInst.instructions = [NSArray arrayWithObject:mainInstruction];
    mainCompositionInst.frameDuration = CMTimeMake(1, 30);
    [self applyVideoEffectsToComposition:mainCompositionInst size:naturalSize andString:str];
    
    //    // 4 - 输出路径
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *myPathDocs =  [documentsDirectory stringByAppendingPathComponent:
                             [NSString stringWithFormat:@"%ld.mp4",(long)[GJDateUtils getNowTimestamp]]];
    //判断文件是否存在，如果已经存在删除
    [[NSFileManager defaultManager] removeItemAtPath:myPathDocs error:nil];
    NSURL* videoUrl = [NSURL fileURLWithPath:myPathDocs];
    
    // 5 - 视频文件输出
    AVAssetExportSession *exporter = [[AVAssetExportSession alloc] initWithAsset:mixComposition
                                                                      presetName:AVAssetExportPresetHighestQuality];
    exporter.outputURL = videoUrl;
    exporter.outputFileType = AVFileTypeMPEG4;
    exporter.shouldOptimizeForNetworkUse = YES;
    exporter.videoComposition = mainCompositionInst;
    [exporter exportAsynchronouslyWithCompletionHandler:^{
        dispatch_async(dispatch_get_main_queue(), ^{
            if( exporter.status == AVAssetExportSessionStatusCompleted ){
                NSLog(@"导出完成");
                NSURL *compressURL = exporter.outputURL;
                NSLog(@"导出完毕,导出后大小 %f MB",[self ytfileSize:compressURL]);
                NSString *compressUrlStr = compressURL.absoluteString;
                if ([compressUrlStr containsString:@"file://"]) {
                    compressUrlStr = [compressUrlStr substringFromIndex:7];
                }
                complete(compressUrlStr);
            }else if( exporter.status == AVAssetExportSessionStatusFailed ){
                NSLog(@"导出failed");
                [MBProgressHUD hideHUDForView:[UIApplication sharedApplication].delegate.window animated:YES];
                complete(nil);
            }
        });
    }];
}

- (void)applyVideoEffectsToComposition:(AVMutableVideoComposition *)composition size:(CGSize)size andString:(NSString *)str
{
    // 文字
    CATextLayer *subtitle1Text = [[CATextLayer alloc] init];
    [subtitle1Text setFont:@"Helvetica-Bold"];
    [subtitle1Text setFontSize:textLabelFontSize];
    subtitle1Text.wrapped = YES;//自动换行
    [subtitle1Text setFrame:CGRectMake(10, size.height-10-textLabelHeight, size.width-20, textLabelHeight)];
    [subtitle1Text setString:str];
//    [subtitle1Text setAlignmentMode:kCAAlignmentCenter];
    [subtitle1Text setForegroundColor:[[UIColor whiteColor] CGColor]];
    [subtitle1Text display];
    
    //图片
//    CALayer*picLayer = [CALayer layer];
//    picLayer.contents = (id)[UIImage imageNamed:@"videoWater2"].CGImage;
//    picLayer.frame = CGRectMake(size.width-15-87, 15, 87, 26);
    
    // 2 - The usual overlay
    CALayer *overlayLayer = [CALayer layer];
//    [overlayLayer addSublayer:picLayer];
    [overlayLayer addSublayer:subtitle1Text];
    overlayLayer.frame = CGRectMake(0, 0, size.width, size.height);
    [overlayLayer setMasksToBounds:YES];
    
    CALayer *parentLayer = [CALayer layer];
    CALayer *videoLayer = [CALayer layer];
    parentLayer.frame = CGRectMake(0, 0, size.width, size.height);
    videoLayer.frame = CGRectMake(0, 0, size.width, size.height);
    [parentLayer addSublayer:videoLayer];
    [parentLayer addSublayer:overlayLayer];
    
    composition.animationTool = [AVVideoCompositionCoreAnimationTool videoCompositionCoreAnimationToolWithPostProcessingAsVideoLayer:videoLayer inLayer:parentLayer];
}

//计算文件大小
-(CGFloat)ytfileSize:(NSURL *)path{
    return [[NSData dataWithContentsOfURL:path] length]/1024.00 /1024.00;
}

//给图片添加文字水印
-(UIImage *)imageWithimage:(UIImage *)image content:(NSString *)content frame:(CGRect)frame {
    // 开启图形'上下文'
    UIGraphicsBeginImageContextWithOptions(image.size, NO, 0);
    // 绘制原生图片
    [image drawAtPoint:CGPointZero];
    // 在原生图上绘制文字
    NSString *str = content;
    // 创建文字属性字典
    NSDictionary *dictionary = @{NSForegroundColorAttributeName: [UIColor whiteColor], NSFontAttributeName: [UIFont systemFontOfSize:textLabelFontSize]};
    // 绘制文字属性
    [str drawInRect:frame withAttributes:dictionary];
    // 从当前上下文获取修改后的图片
    UIImage *imageNew = UIGraphicsGetImageFromCurrentImageContext();
    // 结束图形上下文
    UIGraphicsEndImageContext();
    return imageNew;
}


/**
 提交资料
 */
- (IBAction)sumbitDataOnClick:(UIButton *)sender {
    [self upLoadImages:0];
}

/**
 循环上传图片
 */
- (void)upLoadImages:(int)optionIndex{
    if(optionIndex < self.imageList.count-1){
        NSMutableDictionary *imageDict = [self.imageList[optionIndex] mutableCopy];
        int fileType = [[imageDict objectForKey:@"fileType"] intValue];
        int uploadValue = [[imageDict objectForKey:@"uploadValue"] intValue];
        NSString *localName = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"localName"]];
        NSString *imageLocalPath = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"imageLocalPath"]];
        NSString *videoLocalPath = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"videoLocalPath"]];
        //NSString *urlPath = [NSString stringWithFormat:@"%@",[imageDict objectForKey:@"urlPath"]];
        if(uploadValue == 0){
            //未上传的时候
            [GJHttpTool POST:[NSString stringWithFormat:APP_UPLOAD_FILE,(self.businessType == 0 ? @"plant" : @"seedling"),self.plantId] parameters:@{} headerParams:@{} awaitingView:self.view constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
                NSData *imageData = (fileType == 0 ? [GJImageUtils imageData:[UIImage imageWithContentsOfFile:imageLocalPath]] : [NSData dataWithContentsOfFile:videoLocalPath]);
                [formData appendPartWithFileData:imageData
                                            name:@"file"
                                        fileName:[NSString stringWithFormat:@"IOS_%d_%@",optionIndex,localName]
                                        mimeType:(fileType == 0 ? @"image/jpeg" : @"video/mpeg4")];
                [formData appendPartWithFormData:[self.plantId dataUsingEncoding:NSUTF8StringEncoding]
                             name:(self.businessType == 0 ? @"plantId" : @"seedlingId")];
            } success:^(id responseObject) {
                if([[responseObject objectForKey:@"code"] intValue] == 200){
                    //赋值列表
                    NSString *urlPath = [NSString stringWithFormat:@"%@",[responseObject objectForKey:@"data"]];
                    NSMutableDictionary *imageDict = [self.imageList[optionIndex] mutableCopy];
                    [imageDict setObject:[NSNumber numberWithInt:1] forKey:@"uploadValue"];
                    [imageDict setObject:urlPath forKey:@"urlPath"];
                    [self.imageList replaceObjectAtIndex:optionIndex withObject:imageDict];
                    [self.collectionView reloadItemsAtIndexPaths:@[[NSIndexPath indexPathForRow:optionIndex inSection:0]]];
                    [self upLoadImages:optionIndex+1];
                    
                    if(fileType == 0){
                        //保存到本地
                        //参数1:图片对象
                        //参数2:成功方法绑定的target
                        //参数3:成功后调用方法
                        //参数4:需要传递信息(成功后调用方法的参数)
                        UIImageWriteToSavedPhotosAlbum([UIImage imageWithContentsOfFile:imageLocalPath], self, @selector(image:didFinishSavingWithError:contextInfo:), nil);
                    }else{
                        ALAssetsLibrary  *library =  [[ALAssetsLibrary alloc] init];
                        [library writeVideoAtPathToSavedPhotosAlbum:[NSURL fileURLWithPath:videoLocalPath] completionBlock: ^(NSURL *assetURL, NSError * error) {
                            if(error){
                                NSLog( @" Save video fail:%@ " ,error);
                            }else{
                                NSLog( @" Save video succeed. " );
                            }
                        }];
                    }
                }else{
                    //[SVProgressHUD showErrorWithStatus:[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"msg"]]];
                    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:[NSString stringWithFormat:@"文件%d上传出错：%@。是否删除该附件后继续上传",optionIndex+1,[responseObject objectForKey:@"msg"]]
                                                                                      preferredStyle:UIAlertControllerStyleAlert];
                    [alertController addAction:([UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
                        NSLog(@"取消了");
                    }])];[alertController addAction:([UIAlertAction actionWithTitle:@"删除后上传" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                        //删除
                        [self.imageList removeObjectAtIndex:optionIndex];
                        [self.collectionView reloadData];
                        [self upLoadImages:optionIndex];
                    }])];
                    [self presentViewController:alertController animated:YES completion:nil];
                }
            } failure:^(NSError *error) {
                NSLog(@"%@",error);
            }];
        }else{
            //已上传的跳过
            [self upLoadImages:optionIndex+1];
        }
    }else{
        //保存数据到本地
        [self.imageList removeLastObject];
        if(self.breakValueBlock!=nil){
            self.breakValueBlock([self.imageList mutableCopy]);
        }
        [self.navigationController popViewControllerAnimated:YES];
    }
}


#pragma mark -- <保存到相册>
-(void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo {
    NSString *msg = nil ;
    if(error){
        msg = NSLocalizedString(@"save.image.error", @"") ;
    }else{
        msg = NSLocalizedString(@"save.image.sucess", @"") ;
    }
}

@end
