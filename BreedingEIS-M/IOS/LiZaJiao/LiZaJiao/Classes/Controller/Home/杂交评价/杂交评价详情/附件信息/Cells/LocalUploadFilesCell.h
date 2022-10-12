//
//  LocalUploadFilesCell.h
//  SupplyChain
//
//  Created by Apple on 7/3/19.
//  Copyright © 2019 GuJie. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface LocalUploadFilesCell : UICollectionViewCell

@property (weak, nonatomic) IBOutlet UIButton *deleteBtn;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UIImageView *playMark;
@property (weak, nonatomic) IBOutlet UILabel *uploadState;
@property (weak, nonatomic) IBOutlet UIView *actionView;

@end

NS_ASSUME_NONNULL_END
