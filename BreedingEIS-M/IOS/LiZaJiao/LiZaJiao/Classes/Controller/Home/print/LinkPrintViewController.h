//
//  LinkPrintViewController.h
//  LiZaJiao
//
//  Created by 顾杰 on 2021/10/24.
//  Copyright © 2021 GuJie. All rights reserved.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface LinkPrintViewController : BaseViewController


@property (strong, nonatomic) NSString *codeStr;
@property (weak, nonatomic) IBOutlet UITextField *textField;

@end

NS_ASSUME_NONNULL_END
