//
//  AvatarBrowser.m
//  UMeeting
//
//  Created by UIN on 16/6/20.
//  Copyright © 2016年 UIN. All rights reserved.
//
#import "PCBlockedActionSheet.h"
#import "AvatarBrowser.h"
static CGRect oldframe;

@interface AvatarBrowser ()<UIScrollViewDelegate>



@property (nonatomic,strong) UIView *baseView;
@property (nonatomic,strong) UIImage *sendImage;



@end

@implementation AvatarBrowser

+(void)showImage:(UIImageView *)avatarImageView{
    UIImage *image=avatarImageView.image;
    UIWindow *window=[UIApplication sharedApplication].keyWindow;
    UIView *backgroundView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height)];
    oldframe=[avatarImageView convertRect:avatarImageView.bounds toView:window];
    backgroundView.backgroundColor=[UIColor blackColor];
    backgroundView.alpha=0;
    UIImageView *imageView=[[UIImageView alloc]initWithFrame:oldframe];
    imageView.image=image;
    imageView.tag=1;
    [backgroundView addSubview:imageView];
    [window addSubview:backgroundView];
    
    UITapGestureRecognizer *tap=[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(hideImage:)];
    [backgroundView addGestureRecognizer: tap];
    
    [UIView animateWithDuration:0.3 animations:^{
        imageView.frame=CGRectMake(0,([UIScreen mainScreen].bounds.size.height-image.size.height*[UIScreen mainScreen].bounds.size.width/image.size.width)/2, [UIScreen mainScreen].bounds.size.width, image.size.height*[UIScreen mainScreen].bounds.size.width/image.size.width);
        backgroundView.alpha=1;
    } completion:^(BOOL finished) {
        
    }];
}

+(void)hideImage:(UITapGestureRecognizer*)tap{
    UIView *backgroundView=tap.view;
    UIImageView *imageView=(UIImageView*)[tap.view viewWithTag:1];
    [UIView animateWithDuration:0.3 animations:^{
        imageView.frame=oldframe;
        backgroundView.alpha=0;
    } completion:^(BOOL finished) {
        [backgroundView removeFromSuperview];
    }];
}











+(void)hideImage2:(UITapGestureRecognizer*)tap{
    UIView *backgroundView=tap.view;
    UIImageView *imageView=(UIImageView*)[tap.view viewWithTag:2];
    [UIView animateWithDuration:0.3 animations:^{
        imageView.frame=CGRectMake(0, 0, 1, 1);
        backgroundView.alpha=0;
    } completion:^(BOOL finished) {
        [backgroundView removeFromSuperview];
    }];
}




+(void)jf_showImage:(UIImage *)image
{
    
    if (!image) {
        return;
    }
    
    
    
    UIWindow *window=[UIApplication sharedApplication].keyWindow;
    UIView *backgroundView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height)];
    backgroundView.backgroundColor=[UIColor blackColor];
    backgroundView.alpha=1;
    
//    UIViewController *vc = [[UIViewController alloc]init];
    

    
//    CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height)]
    UIImageView *imageView=[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height)];
    
    imageView.contentMode = UIViewContentModeScaleAspectFit;
    [imageView setImage:image];
    
    [window addSubview:backgroundView];
    
    
    
    //添加捏合手势，放大与缩小图片
    
    UIScrollView *scrollview=[[UIScrollView alloc]initWithFrame:backgroundView.bounds];
    
    [scrollview addSubview:imageView];
    
    [backgroundView addSubview:scrollview];
    
    //设置UIScrollView的滚动范围和图片的真实尺寸一致
    
    scrollview.contentSize = image.size;
    
    //设置实现缩放
    
    //设置代理scrollview的代理对象
    
    scrollview.delegate = self;
    
    //设置最大伸缩比例
    
    scrollview.maximumZoomScale=3;
    
    //设置最小伸缩比例
    
    scrollview.minimumZoomScale=1;
    
//    [scrollview setZoomScale:1 animated:NO];
    
    
//    scrollview.scrollsToTop =NO;
    
//    scrollview.scrollEnabled =YES;
    
    scrollview.showsHorizontalScrollIndicator=NO;
    
    scrollview.showsVerticalScrollIndicator=NO;
    
    
    

    
//    self.baseView = backgroundView;
//    self.sendImage = image;
    
    UITapGestureRecognizer *tap=[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(hideImage2:)];
    [backgroundView addGestureRecognizer: tap];
    
    
//    [UIView animateWithDuration:0.3 animations:^{
//        imageView.frame=CGRectMake(0,([UIScreen mainScreen].bounds.size.height-image.size.height*[UIScreen mainScreen].bounds.size.width/image.size.width)/2, [UIScreen mainScreen].bounds.size.width, image.size.height*[UIScreen mainScreen].bounds.size.width/image.size.width);
//        backgroundView.alpha=1;
//    } completion:^(BOOL finished) {
//        
//        
//        
//    }];
    
//    [UIView animateWithDuration:0.3 animations:^{
//        imageView.frame=CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height);
//        backgroundView.alpha=1;
//    } completion:^(BOOL finished) {
//        
//        
//        
//    }];
    
    


    
    
//    [backgroundView whenDoubleTapped:^{
//        
//        
//        [UIView animateWithDuration:0.3 animations:^{
//            
//            imageView.mj_size = CGSizeMake([UIScreen mainScreen].bounds.size.width*2, [UIScreen mainScreen].bounds.size.height*2);
//            
//        } completion:^(BOOL finished) {
//    
//            
//            
//        }];
    
        
 
    
    
}


+ (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo
{
}


-(void)longTap:(UILongPressGestureRecognizer *)tap
{
    PCBlockedActionSheet *sheet = [[PCBlockedActionSheet alloc]initWithTitle:NSLocalizedString(@"caozuo", @"") delegate:self cancelButtonTitle:NSLocalizedString(@"quxiao", @"") destructiveButtonTitle:NSLocalizedString(@"save.photo", @"") otherButtonTitles: nil];
    
    [sheet showInView:self.baseView onDismiss:^(NSInteger buttonIndex) {
        
        if (self.sendImage) {
            UIImageWriteToSavedPhotosAlbum(self.sendImage, self, @selector(image:didFinishSavingWithError:contextInfo:), (__bridge void *)self);
        }
        
    } onCancel:^{
        
    }];
    
}




@end
