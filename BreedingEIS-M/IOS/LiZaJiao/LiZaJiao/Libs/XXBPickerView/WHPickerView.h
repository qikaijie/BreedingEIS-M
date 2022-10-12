//
//  WHPickerView.h
//  PublicIntelligentCall
//
//  Created by wuhao on 2017/2/8.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^StringSelect)(NSString *selectString);
@interface WHPickerView : UIView
@property (nonatomic, strong) NSArray * dataArray;
@property (nonatomic, copy) StringSelect  stringBlock;
@property (nonatomic, strong) NSString * selectString;
- (void)didFinishSelected:(StringSelect)selectString;
@end
