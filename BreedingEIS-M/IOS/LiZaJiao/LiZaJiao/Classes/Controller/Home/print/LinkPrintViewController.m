//
//  LinkPrintViewController.m
//  LiZaJiao
//
//  Created by 顾杰 on 2021/10/24.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "LinkPrintViewController.h"
#import "XYBLEManager.h"
#import "PosCommand.h"
#import "AppDelegate.h"
#import "ProgressHUD.h"
#import "TscCommand.h"
#import "SelectionDeviceVC.h"

@interface LinkPrintViewController ()<XYBLEManagerDelegate>

@property (strong, nonatomic) UIButton *backBtn;

@property (weak, nonatomic) IBOutlet UILabel *infoLabel;
@property (weak, nonatomic) IBOutlet UIButton *linkButton;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;

/** BLE */
@property (strong, nonatomic) XYBLEManager *manager;


@end

@implementation LinkPrintViewController

- (void)viewWillAppear:(BOOL)animated{
    
   self.manager.delegate = self;
    [self.manager addObserver:self
                   forKeyPath:@"writePeripheral.state"
                      options:NSKeyValueObservingOptionNew | NSKeyValueObservingOptionOld
                      context:nil];
    
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

- (void)dealloc{
    self.manager.delegate = nil;
    [self.manager removeObserver:self forKeyPath:@"writePeripheral.state" context:nil];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.navigationController.navigationBar.translucent = NO;
    
    self.manager = [XYBLEManager sharedInstance];
    self.manager.delegate = self;
        
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
    
    if(!strIsEmpty(self.codeStr)){
        self.textField.text = self.codeStr;
    }
    
    if(SharedAppDelegate.isConnectedBLE){
        self.infoLabel.text = SharedAppDelegate.peripheral.name;
        [self.linkButton setTitle:NSLocalizedString(@"duankai", @"") forState:UIControlStateNormal];
    }else{
        self.infoLabel.text = NSLocalizedString(@"unselect", @"");
        [self.linkButton setTitle:NSLocalizedString(@"link", @"") forState:UIControlStateNormal];
    }
    
    
    UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(handleBackgroundTap:)];
    tapRecognizer.cancelsTouchesInView = NO;
    [self.view addGestureRecognizer:tapRecognizer];

}

- (void) handleBackgroundTap:(UITapGestureRecognizer*)sender {
    [self.textField resignFirstResponder];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary<NSString *,id> *)change context:(void *)context
{
    if (object == self.manager && [keyPath isEqualToString:@"writePeripheral.state"])
    {
        // 更行蓝牙的连接状态
        switch (self.manager.writePeripheral.state) {
            case CBPeripheralStateDisconnected:
            {
                [self.linkButton setTitle:NSLocalizedString(@"link", @"") forState:UIControlStateNormal];
                SharedAppDelegate.isConnectedBLE = NO;
                
                break;
            }
                
            case CBPeripheralStateConnecting:
            {
                [self.linkButton setTitle:NSLocalizedString(@"device.linking", @"") forState:UIControlStateNormal];
                break;
            }
            case CBPeripheralStateConnected:
            {

                
                [self.linkButton setTitle:NSLocalizedString(@"link", @"") forState:UIControlStateNormal];

                SharedAppDelegate.isConnectedBLE = YES;
                
                break;
            }
            case CBPeripheralStateDisconnecting:
            {
                [self.linkButton setTitle:NSLocalizedString(@"link", @"") forState:UIControlStateNormal];
                SharedAppDelegate.isConnectedBLE = NO;

                
                break;
            }
            default:
                break;
        }  ;
    }
}

/**
 连接或者断开
 */
- (IBAction)linkAction:(UIButton *)sender {
    if(SharedAppDelegate.isConnectedBLE){
        //断开连接
        [self.manager XYdisconnectRootPeripheral];
    }else{
        self.manager.delegate = nil;
        //连接蓝牙
        SelectionDeviceVC *vc = [[SelectionDeviceVC alloc]init];
        vc.hidesBottomBarWhenPushed = YES;
        vc.callBack = ^(id x){
            SharedAppDelegate.isConnectedBLE = YES;
            self.infoLabel.text = SharedAppDelegate.peripheral.name;
            [self.linkButton setTitle:NSLocalizedString(@"duankai", @"") forState:UIControlStateNormal];
            
            self.manager.delegate = self;
            
        };
        [self.navigationController pushViewController:vc animated:YES];
    }
}

//打印文字
- (IBAction)printTextAction:(UIButton *)sender {
    
    if(strIsEmpty(self.textField.text)){
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"input.code", @"")];
        return;
    }
    
    NSMutableData* dataM=[NSMutableData dataWithData:[XYCommand initializePrinter]];
    //****tsc
    NSStringEncoding gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
//    [dataM appendData:[XYCommand setLableWidth:57]];
    [dataM appendData:[TscCommand gapBymmWithWidth:0 andHeight:0]];
    [dataM appendData:[TscCommand sizeBymmWithWidth:70 andHeight:25]];
    [dataM appendData:[TscCommand cls]];
    [dataM appendData:[TscCommand textWithX:10 andY:15 andFont:@"TSS24.BF2" andRotation:0 andX_mul:1 andY_mul:1 andContent:self.textField.text usStrEnCoding:gbkEncoding]];
    [dataM appendData:[TscCommand print:1]];
    if (SharedAppDelegate.isConnectedBLE) {
        [self.manager XYWriteCommandWithData:dataM];
    }else{
        [ProgressHUD showError:NSLocalizedString(@"link.blue", @"")];
    }
}

//打印二维码
- (IBAction)printQRAction:(UIButton *)sender {
    
    if(strIsEmpty(self.textField.text)){
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"input.code", @"")];
        return;
    }
    
    NSStringEncoding gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);

    NSMutableData* dataM=[NSMutableData data];
    [dataM appendData:[TscCommand gapBymmWithWidth:0 andHeight:0]];
    [dataM appendData:[TscCommand sizeBymmWithWidth:60 andHeight:30]];
    [dataM appendData:[TscCommand cls]];
    
    [dataM appendData:[TscCommand qrCodeWithX:3 andY:3 andEccLevel:@"H" andCellWidth:6 andMode:@"M" andRotation:0 andContent:self.textField.text usStrEnCoding:gbkEncoding]];
    
    if (SharedAppDelegate.isConnectedBLE) {
        [dataM appendData:[TscCommand print:1]];
        [self.manager XYWriteCommandWithData:dataM];
    }else{
        [ProgressHUD showError:NSLocalizedString(@"link.blue", @"")];
    }
}

//打印一维码
- (IBAction)printCoreAction:(UIButton *)sender {
    
    if(strIsEmpty(self.textField.text)){
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"input.code", @"")];
        return;
    }
    
    NSStringEncoding gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
    
    NSMutableData* dataM=[NSMutableData data];
    [dataM appendData:[TscCommand gapBymmWithWidth:0 andHeight:2]];
    [dataM appendData:[TscCommand sizeBymmWithWidth:50 andHeight:30]];
    [dataM appendData:[TscCommand cls]];
    
    [dataM appendData:[TscCommand barcodeWithX:0 andY:0 andCodeType:@"128" andHeight:30 andHunabReadable:0 andRotation:0 andNarrow:2 andWide:2 andContent:self.textField.text usStrEnCoding:gbkEncoding]];

    if (SharedAppDelegate.isConnectedBLE) {
        [dataM appendData:[TscCommand print:1]];
        [self.manager XYWriteCommandWithData:dataM];
    }else{
        [ProgressHUD showError:NSLocalizedString(@"link.blue", @"")];
    }
}

- (IBAction)printImageAction:(UIButton *)sender {
    
    if(strIsEmpty(self.textField.text)){
        [SVProgressHUD showErrorWithStatus:NSLocalizedString(@"input.code", @"")];
        return;
    }
//    //生成图片
//    UIView *printView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 200, 100)];
//    printView.layer.borderColor = [UIColor blackColor].CGColor;
//    printView.layer.borderWidth = 1.0;
//
//    UILabel *codelabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 10, 200, 20)];
//    codelabel.text = self.textField.text;
//    codelabel.font = [UIFont systemFontOfSize:12];
//    codelabel.textColor = [UIColor colorWithRed:51/255.0 green:51/255.0 blue:51/255.0 alpha:1/1.0];
//    codelabel.textAlignment = NSTextAlignmentCenter;
//    [printView addSubview:codelabel];
//
//    UIImage *codeimage = [self createQRCodeOrBarCode:YES code:self.textField.text width:200 height:70];
//    UIImageView *codeiv = [[UIImageView alloc] initWithFrame:CGRectMake(0, 30, 200, 70)];
//    [codeiv setImage:codeimage];
//    [printView addSubview:codeiv];
//
//    UIImage *image = [self GetmakeImageWithView:printView andWithSize:CGSizeMake(200, 100)];
//
//    //显示图片
//    [self.imageView setImage:image];
    
    
    if (SharedAppDelegate.isConnectedBLE) {
        [MBProgressHUD showHUDAddedTo:self.view animated:YES];
//        NSStringEncoding gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
//        NSMutableData* dataM=[NSMutableData data];
//        [dataM appendData:[TscCommand gapBymmWithWidth:50 andHeight:2]];
//        [dataM appendData:[TscCommand sizeBymmWithWidth:50 andHeight:30]];
//        [dataM appendData:[TscCommand cls]];
//        [dataM appendData:[TscCommand bitmapWithX:0 andY:5 andMode:0 andImage:image andBmpType:Dithering andPaperHeight:30]];
        
        NSMutableData* dataM=[NSMutableData dataWithData:[XYCommand initializePrinter]];

        //****tsc
        NSStringEncoding gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
    //    [dataM appendData:[XYCommand setLableWidth:57]];
        [dataM appendData:[TscCommand gapBymmWithWidth:50 andHeight:2]];
        [dataM appendData:[TscCommand sizeBymmWithWidth:50 andHeight:30]];
        [dataM appendData:[TscCommand cls]];
        [dataM appendData:[TscCommand textWithX:35 andY:3 andFont:@"TSS24.BF2" andRotation:0 andX_mul:1 andY_mul:1 andContent:@"Z9-BM1W-1501-16@1602-434" usStrEnCoding:gbkEncoding]];
        [dataM appendData:[TscCommand qrCodeWithX:100 andY:32 andEccLevel:@"H" andCellWidth:5 andMode:@"M" andRotation:0 andContent:@"Z9-BM1W-1501-16@1602-434" usStrEnCoding:gbkEncoding]];
        [dataM appendData:[TscCommand print:1]];
        
        [self.manager XYWriteCommandWithData:dataM];
    }else{
        [ProgressHUD showError:NSLocalizedString(@"link.blue", @"")];
    }
}

- (void)XYdidWriteValueForCharacteristic:(CBCharacteristic *)character error:(NSError *)error{
    [MBProgressHUD hideHUDForView:self.view animated:YES];
}

#pragma mark 生成image
- (UIImage *)GetmakeImageWithView:(UIView *)view andWithSize:(CGSize)size
{
      
    //下面方法，第一个参数表示区域大小。第二个参数表示是否是非透明的。如果需要显示半透明效果，需要传NO，否则传YES。第三个参数就是屏幕密度了，关键就是第三个参数 [UIScreen mainScreen].scale。
    UIGraphicsBeginImageContextWithOptions(size, NO, 0.0);
    [view.layer renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
  
}

// 生成二维码
- (UIImage *)createQRCodeOrBarCode:(BOOL)QROrBar code:(NSString *)code width:(CGFloat)width height:(CGFloat)height {
    if (!code || code.length == 0) {
         //code为nil, 不能生成二维码
        //code为@“”，可生成二维码，但扫描结果为：(null)，无意义
        return nil;
    }
    CIImage *returnImage;
    NSData *data = [code dataUsingEncoding:NSISOLatin1StringEncoding allowLossyConversion:NO];
    CIFilter *filter;
    if (QROrBar) {
        // 生成条形码
        //创建滤镜,对图像进行滤镜处理
        filter = [CIFilter filterWithName:@"CICode128BarcodeGenerator"];
        [filter setValue:data forKey:@"inputMessage"];
        returnImage = [filter outputImage];
    } else {
        // 生成二维码
        filter = [CIFilter filterWithName:@"CIQRCodeGenerator"];
        [filter setValue:data forKey:@"inputMessage"];
        [filter setValue:@"H" forKey:@"inputCorrectionLevel"];
        returnImage = [filter outputImage];
    }
    //1.保持模糊效果
//    return [UIImage imageWithCIImage:returnImage];
    
     // 2.消除模糊, extent 返回图片的frame
     CGFloat scaleX = width / returnImage.extent.size.width;
     CGFloat scaleY = height / returnImage.extent.size.height;
     CIImage *transformedImage = [returnImage imageByApplyingTransform:CGAffineTransformScale(CGAffineTransformIdentity, scaleX, scaleY)];
     return [UIImage imageWithCIImage:transformedImage];
}

@end
