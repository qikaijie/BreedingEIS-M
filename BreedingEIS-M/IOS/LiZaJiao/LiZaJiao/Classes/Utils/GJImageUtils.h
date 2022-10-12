//
//  GJImageUtils.h
//  MerchantAssistant
//
//  Created by Apple on 4/8/19.
//  Copyright © 2019 GuJie. All rights reserved.
//


#import <Foundation/Foundation.h>

@interface GJImageUtils : NSObject

+ (NSString *)appedNetworkImageUrl:(NSString *)suffix;

+ (UIImage*)imageCompressWithSimple:(UIImage*)image scaledToSize:(CGSize)size;

+ (UIImage*)transformImage:(UIImage *)image
                     width:(CGFloat)width
                    height:(CGFloat)height;


//UIImage图片转成Base64字符串：
+ (NSString *)base64WithImage:(UIImage *)originImage;

//Base64字符串转UIImage图片：
+ (UIImage *)imageWithBase64:(NSString *)encodedImageStr;


//根据英文名获取图片的logo
+ (UIImage *)imageWithBankENName:(NSString *)bankENName;

/**
 *  1, 按图片最大边成比例缩放图片
 *
 *  @param image   图片
 *  @param maxSize 图片的较长那一边目标缩到的(宽度／高度)
 *
 *  @return        等比缩放后的图片
 */
+ (UIImage *)scaleImage:(UIImage *)image maxSize:(CGFloat)maxSize;

/**
 *  2, 图片支持等比缩放
 *
 *  @param image   图片
 *  @param maxSize 缩放比例(通常0～1之间)
 *
 *  @return        等比缩放后的图片
 */
+ (UIImage *)scaleImage:(UIImage *)image toScale:(float)scaleSize;

/**
 *  3, 等比缩放成自定长宽的图片
 *
 *  @param image      源图片
 *  @param targetSize 自定义目标图片的size
 *
 *  @return 处理后图片
 */
+ (UIImage *)scaleImage:(UIImage *)image toSize:(CGSize)targetSize;

//根据图片附获取图片大小(多少M)方法
+ (NSData *)imageData:(UIImage *)image;

//动态添加图片
+ (void)initSelectImage:(UIView *)mainView andWidthView:(NSLayoutConstraint *)mainViewWidth andSelectImages:(NSMutableArray *)selectImages andVC:(UIViewController *)vc andNum:(int)maxNum;

//动态显示图片
+ (void)showImage:(UIView *)mainView andWidthView:(NSLayoutConstraint *)mainViewWidth andShowImages:(NSMutableArray *)showImages andVC:(UIViewController *)vc;

+ (UIImage *)compressImageQuality:(UIImage *)image toByte:(NSInteger)maxLength;

@end
