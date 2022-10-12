//
//  GJImageUtils.m
//  MerchantAssistant
//
//  Created by Apple on 4/8/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import "GJImageUtils.h"
//#import "TZImagePickerController.h"

@implementation GJImageUtils

+ (NSString *)appedNetworkImageUrl:(NSString *)suffix{
    /**
     * 图片前缀
     * 研发环境：http://bec-baik.oss-cn-hangzhou.aliyuncs.com/baik-develop/
     * 测试环境：http://bec-baik.oss-cn-hangzhou.aliyuncs.com/baik-test/
     * 生产环境：http://bec-baik.oss-cn-hangzhou.aliyuncs.com/baik/
     */
    NSString *base_iamge_url = @"http://bec-baik.oss-cn-hangzhou.aliyuncs.com/baik/";
    return [NSString stringWithFormat:@"%@%@",base_iamge_url,suffix];
}

+ (UIImage*)imageCompressWithSimple:(UIImage*)image scaledToSize:(CGSize)size
{
    UIGraphicsBeginImageContext(size);
    [image drawInRect:CGRectMake(0,0,size.width,size.height)];
    UIImage* newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return newImage;
}

+ (UIImage*)transformImage:(UIImage *)image
                     width:(CGFloat)width
                    height:(CGFloat)height {
    CGFloat destW = width;
    CGFloat destH = height;
    CGFloat sourceW = width;
    CGFloat sourceH = height;
    CGImageRef imageRef = image.CGImage;
    CGContextRef bitmap = CGBitmapContextCreate(NULL,
                                                destW,
                                                destH,
                                                CGImageGetBitsPerComponent(imageRef),
                                                4*destW,
                                                CGImageGetColorSpace(imageRef),
                                                (kCGBitmapByteOrder32Little | kCGImageAlphaPremultipliedFirst));
    CGContextDrawImage(bitmap, CGRectMake(0, 0, sourceW, sourceH), imageRef);
    CGImageRef ref = CGBitmapContextCreateImage(bitmap);
    UIImage *resultImage = [UIImage imageWithCGImage:ref];
    CGContextRelease(bitmap);
    CGImageRelease(ref);
    return resultImage;
}

//UIImage图片转成Base64字符串：
+ (NSString *)base64WithImage:(UIImage *)originImage{
    NSData *data = UIImageJPEGRepresentation(originImage, 1.0f);
    return [data base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
}

//Base64字符串转UIImage图片：
+ (UIImage *)imageWithBase64:(NSString *)encodedImageStr{
    NSData *decodedImageData = [[NSData alloc]initWithBase64EncodedString:encodedImageStr options:NSDataBase64DecodingIgnoreUnknownCharacters];
    return [UIImage imageWithData:decodedImageData];
}


+ (UIImage *)imageWithBankENName:(NSString *)bankENName{
    NSString *imageName;
    if([bankENName isEqualToString:@"ICBC"]){
        imageName = @"bank_icon_工商";
    }else if([bankENName isEqualToString:@"ABC"]){
        imageName = @"bank_icon_农业";
    }else if([bankENName isEqualToString:@"BOC"]){
        imageName = @"bank_icon_中行";
    }else if([bankENName isEqualToString:@"CCB"]){
        imageName = @"bank_icon_建行";
    }else if([bankENName isEqualToString:@"BCM"]){
        imageName = @"bank_icon_交通";
    }else if([bankENName isEqualToString:@"CMB"]){
        imageName = @"bank_icon_招商";
    }else if([bankENName isEqualToString:@"CITIC"]){
        imageName = @"bank_icon_中行";
    }else if([bankENName isEqualToString:@"PINGANBANK"]){
        imageName = @"bank_icon_平安";
    }else if([bankENName isEqualToString:@"CIB"]){
        imageName = @"bank_icon_兴业";
    }else if([bankENName isEqualToString:@"SPDB"]){
        imageName = @"bank_icon_浦发";
    }else if([bankENName isEqualToString:@"CEB"]){
        imageName = @"bank_icon_光大";
    }else if([bankENName isEqualToString:@"HXB"]){
        imageName = @"bank_icon_华夏";
    }else if([bankENName isEqualToString:@"CMBC"]){
        imageName = @"bank_icon_民生";
    }else if([bankENName isEqualToString:@"PSBC"]){
        imageName = @"bank_icon_邮政";
    }else if([bankENName isEqualToString:@"BOB"]){
        imageName = @"bank_icon_北京";
    }else if([bankENName isEqualToString:@"BankofShanghai"]){
        imageName = @"bank_icon_上海";
    }else if([bankENName isEqualToString:@"HFBANK"]){
        imageName = @"bank_icon_恒丰";
    }else{
        imageName = @"";
    }
    return [UIImage imageNamed:imageName];
}


/**
 *  1, 按图片最大边成比例缩放图片
 *
 *  @param image   图片
 *  @param maxSize 图片的较长那一边目标缩到的(宽度／高度)
 *
 *  @return        等比缩放后的图片
 */
+ (UIImage *)scaleImage:(UIImage *)image maxSize:(CGFloat)maxSize {
    NSData *data = UIImageJPEGRepresentation(image, 1.0);
    if(data.length < 200 * 1024){//0.25M-0.5M(当图片小于此范围不压缩)
        return image;
    }
    CGFloat imageWidth = image.size.width;
    CGFloat imageHeight = image.size.height;
    CGFloat targetWidth = imageWidth;
    CGFloat targetHeight = imageHeight;
    CGFloat imageMaxSize = MAX(imageWidth, imageHeight);
    if (imageMaxSize > maxSize) {
        CGFloat scale = 0;
        if (imageWidth >= imageHeight) {// 宽长
            scale = maxSize / imageWidth;
            targetWidth = maxSize;
            targetHeight = imageHeight * scale;
        } else { // 高长
            scale = maxSize / imageHeight;
            targetHeight = maxSize;
            targetWidth = imageWidth * scale;
        }
        UIGraphicsBeginImageContext(CGSizeMake(targetWidth, targetHeight));
        [image drawInRect:CGRectMake(0, 0, targetWidth, targetHeight)];
        UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        return scaledImage;
    }
    return image;
}

/**
 *  2, 图片支持等比缩放
 *
 *  @param image   图片
 *  @param maxSize 缩放比例(通常0～1之间)
 *
 *  @return        等比缩放后的图片
 */
+ (UIImage *)scaleImage:(UIImage *)image toScale:(float)scaleSize {
    UIGraphicsBeginImageContext(CGSizeMake(image.size.width *scaleSize, image.size.height * scaleSize));
    [image drawInRect:CGRectMake(0, 0, image.size.width * scaleSize, image.size.height * scaleSize)];
    UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return scaledImage;
}

/**
 *  3, 等比缩放成自定长宽的图片
 *
 *  @param image      源图片
 *  @param targetSize 自定义目标图片的size
 *
 *  @return 处理后图片
 */
+ (UIImage *)scaleImage:(UIImage *)image toSize:(CGSize)targetSize {
    UIGraphicsBeginImageContext(CGSizeMake(targetSize.width, targetSize.height));
    [image drawInRect:CGRectMake(0, 0, targetSize.width, targetSize.height)];
    UIImage *targetSizeImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return targetSizeImage;
}

//根据图片附获取图片大小(多少M)方法
+ (NSData *)imageData:(UIImage *)image {
    NSData *data = UIImageJPEGRepresentation(image, 1.0);
    if (data.length > 100*1024) {
        if (data.length > 1024*1024) {//1M以及以上
            data = UIImageJPEGRepresentation(image, 0.1);
        }else if (data.length > 512*1024) {//0.5M-1M
            data = UIImageJPEGRepresentation(image, 0.5);
        }else if (data.length > 200*1024) {//0.25M-0.5M
            data = UIImageJPEGRepresentation(image, 0.9);
        }
    }
    return data;
}


/**
 selectImages : url、image
 */
+ (void)initSelectImage:(UIView *)mainView andWidthView:(NSLayoutConstraint *)mainViewWidth andSelectImages:(NSMutableArray *)selectImages andVC:(UIViewController *)vc andNum:(int)maxNum{
    //清除所有的控件
    for(UIView *view in mainView.subviews){
        [view removeFromSuperview];
    }
    int x = 0;
    int middle = 10;
    for (int i = 0; i<selectImages.count; i++) {
        UIView *view = [[UIView alloc]initWithFrame:CGRectMake(x, 0, 80, 80)];
        
        UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 80, 80)];
        NSMutableDictionary *imageDict = selectImages[i];
        [imageView setImage:[imageDict objectForKey:@"image"]];
        [view addSubview:imageView];
        [imageView whenTapped:^{
            //预览
        }];
        
        UIButton *button = [[UIButton alloc]initWithFrame:CGRectMake(63, 2, 15, 15)];
        [button setBackgroundColor:[UIColor clearColor]];
        [button setImage:[UIImage imageNamed:@"btn_remove"] forState:UIControlStateNormal];
        [button whenTapped:^{
            //删除当前图片，初始化对话框
            UIAlertController *alert = [UIAlertController alertControllerWithTitle:NSLocalizedString(@"tishi", @"") message:@"确认移除当前图片吗？" preferredStyle:UIAlertControllerStyleAlert];
            [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"quxiao", @"") style:UIAlertActionStyleCancel handler:nil]];
            [alert addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"sure", @"") style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                [selectImages removeObjectAtIndex:i];
                [self initSelectImage:mainView andWidthView:mainViewWidth andSelectImages:selectImages andVC:vc andNum:maxNum];
            }]];
            // 弹出对话框
            [vc presentViewController:alert animated:true completion:nil];
        }];
        [view addSubview:button];
        
        [mainView addSubview:view];
        
        x = x+80+middle;
    }
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(x, 0, 80, 80)];
    UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 80, 80)];
    [imageView setImage:[UIImage imageNamed:@"icon_pictrue_add"]];
    [view addSubview:imageView];
    [imageView whenTapped:^{
        //新增图片
//        TZImagePickerController *imagePickerVc = [[TZImagePickerController alloc] initWithMaxImagesCount:maxNum delegate:nil];
//        // You can get the photos by block, the same as by delegate.
//        // 你可以通过block或者代理，来得到用户选择的照片.
//        [imagePickerVc setDidFinishPickingPhotosHandle:^(NSArray<UIImage *> *photos, NSArray *assets, BOOL isSelectOriginalPhoto) {
//            //            selectImages = [photos mutableCopy];
//            if(photos.count+selectImages.count>maxNum){
//                [SVProgressHUD showErrorWithStatus:@"图片数量超出限制"];
//            }else{
//                for (UIImage *photo in photos) {
//                    [selectImages addObject:[NSMutableDictionary dictionaryWithObjectsAndKeys:photo,@"image",
//                                             @"",@"url", nil]];
//                }
//                [self initSelectImage:mainView andWidthView:mainViewWidth andSelectImages:selectImages andVC:vc andNum:maxNum];
//            }
//        }];
//        [vc presentViewController:imagePickerVc animated:YES completion:nil];
    }];
    
    [mainView addSubview:view];
    mainViewWidth.constant = x+80+middle;
}


+ (void)showImage:(UIView *)mainView andWidthView:(NSLayoutConstraint *)mainViewWidth andShowImages:(NSMutableArray *)showImages andVC:(UIViewController *)vc{
    //清除所有的控件
    for(UIView *view in mainView.subviews){
        [view removeFromSuperview];
    }
    int x = 0;
    int middle = 10;
    for (int i = 0; i<showImages.count; i++) {
        UIView *view = [[UIView alloc]initWithFrame:CGRectMake(x, 0, 80, 80)];
        
        UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 80, 80)];
        NSMutableDictionary *imageDict = showImages[i];
        [imageView sd_setImageWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"%@",[imageDict objectForKey:@"url"]]] placeholderImage:[UIImage imageNamed:@"load_default_image"] options:SDWebImageAllowInvalidSSLCertificates];
        [view addSubview:imageView];
        [imageView whenTapped:^{
            //预览
        }];
        
        [mainView addSubview:view];
        
        x = x+80+middle;
    }
    
    mainViewWidth.constant = x+middle;
}



+ (UIImage *)compressImageQuality:(UIImage *)image toByte:(NSInteger)maxLength {
    CGFloat compression = 1;
    NSData *data = UIImageJPEGRepresentation(image, compression);
    if (data.length < maxLength) return image;
    CGFloat max = 1;
    CGFloat min = 0;
    for (int i = 0; i < 6; ++i) {
        compression = (max + min) / 2;
        data = UIImageJPEGRepresentation(image, compression);
        if (data.length < maxLength * 0.9) {
            min = compression;
        } else if (data.length > maxLength) {
            max = compression;
        } else {
            break;
        }
    }
    UIImage *resultImage = [UIImage imageWithData:data];
    return resultImage;
}


@end
