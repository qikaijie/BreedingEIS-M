//
//  PopImageViewVC.m
//  MyChat
//
//  Created by UIN on 17/5/22.
//  Copyright © 2017年 Jeff. All rights reserved.
//

#import "PopImageViewVC.h"
#import "PCBlockedActionSheet.h"
@interface PopImageViewVC ()<UIScrollViewDelegate>
{
    UIScrollView *_scrollview;
    UIImageView *_imageview;
}
@end

@implementation PopImageViewVC

- (void)viewDidLoad
{
     [super viewDidLoad];

    self.view.backgroundColor = [UIColor blackColor];
     //1添加 UIScrollView
     //设置 UIScrollView的位置与屏幕大小相同
     _scrollview=[[UIScrollView alloc]initWithFrame:self.view.bounds];
     [self.view addSubview:_scrollview];

     //2添加图片
     //有两种方式
     //(1)一般方式
//     UIImageView  *imageview=[[UIImageView alloc]init];
 //    UIImage *image=[UIImage imageNamed:@"minion"];
//     imageview.image = self.image;
 //    imageview.frame=CGRectMake(0, 0, image.size.width, image.size.height);

     //(2)使用构造方法
////     UIImage *image=[UIImage imageNamed:@"minion"];
    if (self.image==nil && self.url==nil) {
        return;
    }
//     _imageview=[[UIImageView alloc]initWithImage:self.image];
//     //调用initWithImage:方法，它创建出来的imageview的宽高和图片的宽高一样
    
    
    _imageview=[[UIImageView alloc]init];
    if (self.image) {
        _imageview.image = self.image;
    }
    if (self.url) {
        [_imageview sd_setImageWithURL:[NSURL URLWithString:self.url] placeholderImage:nil];
    }
    _imageview.frame = self.view.bounds;
    _imageview.contentMode = UIViewContentModeScaleAspectFit;
    
     [_scrollview addSubview:_imageview];

     //设置UIScrollView的滚动范围和图片的真实尺寸一致
     _scrollview.contentSize=self.image.size;


     //设置实现缩放
     //设置代理scrollview的代理对象
     _scrollview.delegate=self;
     //设置最大伸缩比例
     _scrollview.maximumZoomScale=3.0;
     //设置最小伸缩比例
     _scrollview.minimumZoomScale=1;
    
    [_scrollview whenTapped:^{
       
        [self dismissViewControllerAnimated:YES completion:^{
            
        }];
    }];
    
    [_scrollview whenDoubleTapped:^{
       
        
        PCBlockedActionSheet *sheet = [[PCBlockedActionSheet alloc]initWithTitle:NSLocalizedString(@"caozuo", @"") delegate:nil cancelButtonTitle:NSLocalizedString(@"quxiao", @"") destructiveButtonTitle:NSLocalizedString(@"save.photo", @"") otherButtonTitles: nil];
        
        [sheet showInView:self.view onDismiss:^(NSInteger buttonIndex) {
            
            if (self.image) {
                UIImageWriteToSavedPhotosAlbum(self.image, self, @selector(image:didFinishSavingWithError:contextInfo:), (__bridge void *)self);
            }
            
        } onCancel:^{
            
        }];
        
    }];
//    _imageview.userInteractionEnabled = YES;
//    UILongPressGestureRecognizer *ltap = [[UILongPressGestureRecognizer alloc]initWithTarget:self action:@selector(longTap:)];
//    [_imageview addGestureRecognizer:ltap];

}


- (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo
{
}


 //告诉scrollview要缩放的是哪个子控件
-(UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView
{
     return _imageview;
}

@end
