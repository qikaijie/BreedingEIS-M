//
//  GJTapPage.m
//  GJTabPage
//
//  Created by zhangju on 15/12/25.
//  Copyright © 2015年 ZJ. All rights reserved.
//

#import "GJTapPageControl.h"

@interface GJTapPageControl()

@property (nonatomic, strong) UIView * heightLightView;
@property (nonatomic, strong) UIView * heightTopView;

@property (nonatomic, strong) NSMutableArray * labelMutableArray;
@end

@implementation GJTapPageControl

//- (CGSize)intrinsicContentSize
//{
//    return CGSizeMake(150, 44);
//}

- (instancetype)initWithFrame:(CGRect)frame titleArray:(NSArray *)titleArray normarImages:(NSArray *)normarImages selectedImages:(NSArray *)selectedImages{
    if (self = [super initWithFrame:frame]) {
        self.showsHorizontalScrollIndicator = NO;
        self.showsVerticalScrollIndicator = NO;
        self.scrollsToTop = NO;
        self.isAnimation = YES;
        self.isScrollContent = YES;
        self.titleArray = titleArray;
        self.normarImages = normarImages;
        self.selectedImages = selectedImages;
        
        _itemWidth = 0;
        _font = 16.0;
        _titleColor = [UIColor blackColor];
        _titleSelectedColor = [UIColor colorWithRed:246/255.0 green:84/255.0 blue:71/255.0 alpha:1.0];
        _indicatorColor = [UIColor colorWithRed:246/255.0 green:84/255.0 blue:71/255.0 alpha:1.0];
        _indicatorHeight = 4.0;
        _indicatorWidth = _itemWidth;
    }
    return self;
}
- (void)layoutSubviews{
    [self createBottomLabels];
    [self createTopLables];
    [self createItemWithTitleArray];
}

/**
 *  创建按钮
 */
- (void)createItemWithTitleArray{
    
    for (NSInteger i = 0; i < _titleArray.count; i++) {
        CGRect tempFrame = [self countCurrentRectWithIndex:i];
        UIButton *tempButton = [[UIButton alloc] initWithFrame:tempFrame];
        tempButton.tag = i;
        [tempButton addTarget:self action:@selector(itemButtonClicked:) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:tempButton];
    }
}
/**
 *  计算当前高亮的Frame
 *
 *  @param index 当前点击按钮的Index
 *
 *  @return 返回当前点击按钮的Frame
 */
- (CGRect) countCurrentRectWithIndex: (NSInteger) index {
    return  CGRectMake(_itemWidth * index, 0, _itemWidth, self.frame.size.height);
}


/**
 *  根据索引创建Label
 *
 *  @param index     创建的第几个Index
 *  @param textColor Label字体颜色
 *
 *  @return 返回创建好的label
 */
- (UIView *) createLabelWithTitlesIndex: (NSInteger) index
                               textColor: (UIColor *) textColor
                              isSelected: (BOOL) isSelected{
    CGRect currentLabelFrame = [self countCurrentRectWithIndex:index];
    
    UIView *tempView = [[UIView alloc]initWithFrame:currentLabelFrame];
    
    
    UILabel *tempLabel = [[UILabel alloc] initWithFrame:CGRectMake(currentLabelFrame.size.width/2-5, 0, currentLabelFrame.size.width/2, currentLabelFrame.size.height)];
    tempLabel.textAlignment = NSTextAlignmentLeft;
    
    if(_selectedImages.count == _titleArray.count){
        UIImageView *iconIV = [[UIImageView alloc]initWithFrame:CGRectMake(currentLabelFrame.size.width/2-25, currentLabelFrame.size.height/2-9, 18, 18)];
        [iconIV setImage:[UIImage imageNamed:(isSelected ? _selectedImages[index] : _normarImages[index])]];
        [iconIV setContentMode:UIViewContentModeScaleToFill];
        [tempView addSubview:iconIV];
    }else{
        [tempLabel setFrame:CGRectMake(0, 0, currentLabelFrame.size.width, currentLabelFrame.size.height)];
        [tempLabel setTextAlignment:NSTextAlignmentCenter];
    }
    
    tempLabel.textColor = textColor;
    tempLabel.text = _titleArray[index];
    tempLabel.font = [UIFont boldSystemFontOfSize:_font];
    [tempView addSubview:tempLabel];
    
    return tempView;
}


/**
 *  创建最底层的Label
 */
- (void) createBottomLabels {
    for (int i = 0; i < _titleArray.count; i ++) {
        UIView *tempLabel = [self createLabelWithTitlesIndex:i textColor:_titleColor isSelected:NO];
        [self addSubview:tempLabel];
        [_labelMutableArray addObject:tempLabel];
    }
}

/**
 *  创建上一层高亮使用的Label
 */
- (void) createTopLables {
    CGRect heightLightViewFrame = CGRectMake(0, 0, _itemWidth, self.frame.size.height);
    _heightLightView = [[UIView alloc] initWithFrame:heightLightViewFrame];
    _heightLightView.clipsToBounds = YES;
    if (_indicatorWidth > 0) {
        _indicatorLine = [[UIView alloc] initWithFrame:CGRectMake((_itemWidth - _indicatorWidth)/2, self.frame.size.height - _indicatorHeight, _indicatorWidth,_indicatorHeight)];
    }else{
        _indicatorLine = [[UIView alloc] initWithFrame:CGRectMake(0, self.frame.size.height - _indicatorHeight, _itemWidth,_indicatorHeight)];
    }
    if(_indicatorGradientLayer==nil){
        _indicatorLine.backgroundColor = _indicatorColor;
    }else{
        [_indicatorLine.layer addSublayer:_indicatorGradientLayer];
    }
    [_heightLightView addSubview:_indicatorLine];
    
    _heightTopView = [[UIView alloc] initWithFrame: CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
    for (int i = 0; i < _titleArray.count; i ++) {
        UIView *label = [self createLabelWithTitlesIndex:i textColor:_titleSelectedColor isSelected:YES];
        [_heightTopView addSubview:label];
    }
    [_heightLightView addSubview:_heightTopView];
    [self addSubview:_heightLightView];
}


#pragma mark - 点击事件
/**
 *  抖动效果
 *
 *  @param view 要抖动的view
 */
- (void)shakeAnimationForView:(UIView *) view {
    CALayer *viewLayer = view.layer;
    CGPoint position = viewLayer.position;
    CGPoint x = CGPointMake(position.x + 1, position.y);
    CGPoint y = CGPointMake(position.x - 1, position.y);
    CABasicAnimation *animation = [CABasicAnimation animationWithKeyPath:@"position"];
    [animation setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionDefault]];
    [animation setFromValue:[NSValue valueWithCGPoint:x]];
    [animation setToValue:[NSValue valueWithCGPoint:y]];
    [animation setAutoreverses:YES];
    [animation setDuration:.06];
    [animation setRepeatCount:3];
    [viewLayer addAnimation:animation forKey:nil];
}


-(void)itemButtonClickedOpen:(int)tag{
    if ([_GJdelegate respondsToSelector:@selector(buttonOnClick:title:)]) {
        [_GJdelegate buttonOnClick:tag title:_titleArray[tag]];
        [self changeTextColor:tag];
    }
}

/**
 *  点击按钮事件
 *
 *  @param sender 点击的相应的按钮
 */
-(void)itemButtonClicked:(UIButton*)sender{
    if ([_GJdelegate respondsToSelector:@selector(buttonOnClick:title:)]) {
        [_GJdelegate buttonOnClick:sender.tag title:_titleArray[sender.tag]];
        [self changeTextColor:sender.tag];
    }
}

/**
 *  改变选中状态下的字体颜色
 *
 *  @param index item下标
 */

- (void)changeTextColor:(NSInteger)index{
    CGRect frame = [self countCurrentRectWithIndex:index];
    CGRect changeFrame = [self countCurrentRectWithIndex:-index];
    [self changeScrollOfSet:index];
    __weak typeof(self) weak_self = self;
    if (!_isScrollContent) {
        [UIView animateWithDuration:0.5 animations:^{
            _heightLightView.frame = frame;
            _heightTopView.frame = changeFrame;
        } completion:^(BOOL finished) {
            [weak_self shakeAnimationForView:_indicatorLine];
        }];
    }
}
/**
 *  根据偏移量改变指示线位置
 *
 *  @param x offset的x
 */
- (void)changeIndicatorPoint:(CGFloat)x{
    CGRect frame = _heightLightView.frame;
    frame.origin.x = x;
    CGRect changeFrame = _heightTopView.frame;
    changeFrame.origin.x = - x;
    _heightLightView.frame = frame;
    _heightTopView.frame = changeFrame;
}


//移动ScrollView
-(void)changeScrollOfSet:(NSInteger)index{
    float  halfWidth = CGRectGetWidth(self.frame)/2.0;
    float  scrollWidth = self.contentSize.width;
    if (scrollWidth == 0) {
        return;
    }
    
    float leftSpace = _itemWidth*index - halfWidth + _itemWidth/2.0;
    
    if(leftSpace<0){
        leftSpace = 0;
    }
    if(leftSpace > scrollWidth- 2*halfWidth){
        leftSpace = scrollWidth-2*halfWidth;
    }
    [self setContentOffset:CGPointMake(leftSpace, 0) animated:YES];
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
