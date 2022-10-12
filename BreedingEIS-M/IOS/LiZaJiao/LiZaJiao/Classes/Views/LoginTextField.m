//
//  LoginTextField.m
//  LiZaJiao
//
//  Created by 顾杰 on 2020/7/14.
//  Copyright © 2020 GuJie. All rights reserved.
//

#import "LoginTextField.h"

@implementation LoginTextField

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (void)drawPlaceholderInRect:(CGRect)rect{
    // 计算占位文字的 Size

        CGSize placeholderSize = [self.placeholder sizeWithAttributes:
                                  @{NSFontAttributeName : self.font}];    [self.placeholder drawInRect:CGRectMake(0, (rect.size.height - placeholderSize.height)/2, rect.size.width, rect.size.height) withAttributes:

        @{NSForegroundColorAttributeName : [UIColor colorWithRed:227/255.0 green:227/255.0 blue:227/255.0 alpha:1],
                    NSFontAttributeName : self.font}];
}

@end
