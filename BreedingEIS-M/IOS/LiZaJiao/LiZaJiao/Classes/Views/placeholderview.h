//
//  placeholderview.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/7/17.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface placeholderview : UITextView

/** 占位文字 */
@property (nonatomic, copy) NSString *placeholder;
/** 占位文字颜色 */
@property (nonatomic, strong) UIColor *placeholderColor;

@end
