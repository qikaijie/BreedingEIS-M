//
//  WHPickerGoodsView.h
//  GS智呼
//
//  Created by 顾杰 on 2021/5/6.
//  Copyright © 2021 wuhao. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^IndexSelect)(NSInteger selectIndex);
@interface WHPickerGoodsView : UIView
@property (nonatomic, strong) NSArray * dataArray;
@property (nonatomic, copy) IndexSelect indexBlock;
@property (nonatomic, strong) NSString *selectIndex;

- (instancetype)init:(NSMutableArray *)array andIndex:(NSInteger)index showTitleKey:(NSString *)titleKey;

- (void)didFinishSelected:(IndexSelect)selectIndex;

@end
