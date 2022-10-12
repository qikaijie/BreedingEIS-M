//
//  XXBMessageTableViewCell.m
//  SoftCall
//
//  Created by wuhao on 2017/12/13.
//  Copyright © 2017年 wuhao. All rights reserved.
//

#import "XXBMessageTableViewCell.h"

@interface XXBMessageTableViewCell ()



@end

@implementation XXBMessageTableViewCell
- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self initWithUI];
    }
    return self;
}

- (void)initWithUI{
    _nameLabel = [[UILabel alloc]init];
    _nameLabel.frame = CGRectMake(10, 5, SCREEN_WIDTH - 120, 35);
    _nameLabel.font = [UIFont systemFontOfSize:14];
    _nameLabel.textColor = RGBCOLOR(102, 102, 102);
    _nameLabel.numberOfLines = 0;
    [self.contentView addSubview:_nameLabel];
    
    _detailBtn = [[UIButton alloc]init];
    _detailBtn.frame = CGRectMake(SCREEN_WIDTH-100, 5 , 90, 35);
    [_detailBtn setTitle:@"展开" forState:UIControlStateNormal];
    [_detailBtn setTitleColor:RGBCOLOR(90, 90, 90) forState:UIControlStateNormal];
    _detailBtn.titleLabel.textAlignment = NSTextAlignmentLeft;
    _detailBtn.titleLabel.font = [UIFont systemFontOfSize:12];

    _detailBtn.tag = 200;
    [_detailBtn addTarget:self action:@selector(detailBtnClick) forControlEvents:UIControlEventTouchUpInside];
    [self.contentView addSubview:_detailBtn];
}

- (void)detailBtnClick{
    if (self.myMsgCellDelegate && [self.myMsgCellDelegate respondsToSelector:@selector(clickFoldLabel:)]) {
        
        [self.myMsgCellDelegate clickFoldLabel:self];
    }
}

- (void)setModel:(XXBNewMessageModel *)model{
    _nameLabel.text = model.name;
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    paragraphStyle.lineBreakMode = NSLineBreakByCharWrapping;
    paragraphStyle.alignment = NSTextAlignmentLeft;
    paragraphStyle.lineSpacing = 1;
    paragraphStyle.hyphenationFactor = 1.0;
    paragraphStyle.firstLineHeadIndent = 0.0;
    paragraphStyle.paragraphSpacingBefore = 0.0;
    paragraphStyle.headIndent = 0;
    paragraphStyle.tailIndent = 0;
    NSDictionary *dic = @{NSFontAttributeName:[UIFont systemFontOfSize:14], NSParagraphStyleAttributeName:paragraphStyle, NSKernAttributeName:@1.0f};
    CGRect labelSize = [model.name boundingRectWithSize:CGSizeMake(SCREEN_WIDTH - 120, 2000) options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
    
    CGFloat textHeight = labelSize.size.height;
    if (textHeight > 20) {
        self.detailBtn.hidden = NO;
        if (model.isOpening) {
            
            self.nameLabel.numberOfLines = 0;
            [self.detailBtn setTitle:@"收起" forState:UIControlStateNormal];
            
            _nameLabel.frame = CGRectMake(10, 5, SCREEN_WIDTH - 120, textHeight);
        }else{
            
            self.nameLabel.numberOfLines = 1;
            [self.detailBtn setTitle:@"展开" forState:UIControlStateNormal];
            _nameLabel.frame = CGRectMake(10, 5, SCREEN_WIDTH - 120, 35);
        }
    }else{
        self.detailBtn.hidden = YES;
    }
    

}

+ (CGFloat)heightOfCellWithModel:(XXBNewMessageModel *)model{
    CGFloat height = 0;
    
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    paragraphStyle.lineBreakMode = NSLineBreakByCharWrapping;
    paragraphStyle.alignment = NSTextAlignmentLeft;
    paragraphStyle.lineSpacing = 1;
    paragraphStyle.hyphenationFactor = 1.0;
    paragraphStyle.firstLineHeadIndent = 0.0;
    paragraphStyle.paragraphSpacingBefore = 0.0;
    paragraphStyle.headIndent = 0;
    paragraphStyle.tailIndent = 0;
    NSDictionary *dic = @{NSFontAttributeName:[UIFont systemFontOfSize:14], NSParagraphStyleAttributeName:paragraphStyle, NSKernAttributeName:@1.0f};
    CGRect labelSize = [model.name boundingRectWithSize:CGSizeMake(SCREEN_WIDTH - 120, 2000) options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];

    CGFloat textHeight = labelSize.size.height;
    height += textHeight;
    if (model.isOpening) {
        return height + 10;
    }else{
        return 45;
    }
    
}

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
