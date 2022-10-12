//
//  GJTapPage.h
//  GJTabPage
//
//  Created by zhangju on 15/12/25.
//  Copyright © 2015年 ZJ. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^ButtonOnClickBlock)(NSInteger tag, NSString * title);

@protocol GJTapPageControlDelegate <NSObject>

- (void)buttonOnClick:(NSInteger)tag title:(NSString *)title;

@end

@interface GJTapPageControl : UIScrollView
@property (nonatomic,assign) CGFloat itemWidth;//字体item宽
@property (nonatomic,assign) CGFloat font;//字体大小
@property (nonatomic,strong) UIColor * titleColor;//字体颜色
@property (nonatomic,strong) UIColor * titleSelectedColor;//选中状态下字体颜色
@property (nonatomic,strong) UIColor * indicatorColor;//下面指示线的颜色
@property (nonatomic,strong) CAGradientLayer *indicatorGradientLayer;
@property (nonatomic,assign) CGFloat indicatorWidth;//指示线的宽度
@property (nonatomic,assign) CGFloat indicatorHeight;//指示线的高度
@property(nonatomic,strong) UIView *indicatorLine;//指示线

@property (nonatomic,strong) NSArray * titleArray;
@property (nonatomic,strong) NSArray * normarImages;
@property (nonatomic,strong) NSArray * selectedImages;
@property (nonatomic,assign) BOOL isAnimation;//是否有动画
@property (nonatomic,assign) BOOL isScrollContent;//是否contentView为ScrollView 默认位Yes
@property (nonatomic,assign) id<GJTapPageControlDelegate> GJdelegate;

/**
 *  初始化控件
 *
 *  @param frame 控件的frame
 *  @param titleArray 控件的item 的 title
 */
- (instancetype)initWithFrame:(CGRect)frame titleArray:(NSArray *)titleArray normarImages:(NSArray *)normarImages selectedImages:(NSArray *)selectedImages;
/**
 *  改变选中状态下的字体颜色
 *
 *  @param index item下标
 */

- (void)changeTextColor:(NSInteger)index;

/**
 *  抖动效果
 *
 *  @param view 要抖动的view
 */
- (void)shakeAnimationForView:(UIView *) view;

/**
 *  根据偏移量改变指示线位置
 *
 *  @param x offset的x
 */
- (void)changeIndicatorPoint:(CGFloat)x;


-(void)itemButtonClickedOpen:(int)tag;

@end
