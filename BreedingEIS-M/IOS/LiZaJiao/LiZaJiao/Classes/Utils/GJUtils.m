//
//  GJUtils.m
//  storemanager
//
//  Created by Apple on 18/3/16.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import "GJUtils.h"
//#import <AVFoundation/AVFoundation.h>
//#import <AVKit/AVKit.h> // 1. 导入头文件  iOS 9 新增

@implementation GJUtils

/**
 * 根据通讯录首字母排序
 * modelArr 要排序的数组
 * string   要排序的字段
 * return   NSArray<NAArray>
 */
+ (NSDictionary *)userSorting:(NSMutableArray *)modelArr andChar:(NSString *)string{
    NSMutableArray *array = [[NSMutableArray alloc] init];//列表数据
    NSMutableArray *indexArray = [[NSMutableArray alloc] init];//索引数组
    for(int i='A';i<='Z';i++){
        NSMutableArray *rulesArray = [[NSMutableArray alloc] init];
        NSString *str1=[NSString stringWithFormat:@"%c",i];
        for(int j=0;j<modelArr.count;j++)
        {
            NSDictionary *model = [modelArr objectAtIndex:j];  //这个model 是我自己创建的 里面包含用户的姓名 手机号 和 转化成功后的首字母
            
            
            NSMutableString *ms = [[NSMutableString alloc]initWithString:model[string]];
            //带声仄 //不能注释掉
            if (CFStringTransform((__bridge CFMutableStringRef)ms, 0,kCFStringTransformMandarinLatin, NO)) {
            }
            if (CFStringTransform((__bridge CFMutableStringRef)ms, 0,kCFStringTransformStripDiacritics, NO)) {
                NSString *bigStr = [ms uppercaseString]; // bigStr 是转换成功后的拼音
                NSString *cha = [bigStr substringToIndex:1];
                
                if([cha isEqualToString:str1])
                {
                    [rulesArray addObject:model];    //把首字母相同的人物model 放到同一个数组里面
                    [modelArr removeObject:model];   //model 放到 rulesArray 里面说明这个model 已经拍好序了 所以从总的modelArr里面删除
                    j--;
                }
            }
        }
        if (rulesArray.count !=0) {
            [array addObject:rulesArray];
            [indexArray addObject:[NSString stringWithFormat:@"%c",i]]; //把大写字母也放到一个数组里面
        }
    }
    if (modelArr.count !=0) {
        [array addObject:modelArr];
        [indexArray addObject:@"#"];  //把首字母不是A~Z里的字符全部放到 array里面 然后返回
    }
    return [NSDictionary dictionaryWithObjectsAndKeys:
            array,@"data",
            indexArray,@"index", nil];
}


//利用正则表达式验证邮箱
+ (BOOL)isValidateEmail:(NSString *)email {
    
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    
    return [emailTest evaluateWithObject:email];
    
}

//检测手机号码的合法性
+ (BOOL)isValidMobile:(NSString *)mobile{
    /**
     * 移动号段正则表达式
     */
    NSString *CM_NUM = @"^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
    /**
     * 联通号段正则表达式
     */
    NSString *CU_NUM = @"^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
    /**
     * 电信号段正则表达式
     */
    NSString *CT_NUM = @"^((133)|(153)|(177)|(173)|(18[0,1,9]))\\d{8}$";
    NSPredicate *pred1 = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CM_NUM];
    BOOL isMatch1 = [pred1 evaluateWithObject:mobile];
    NSPredicate *pred2 = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CU_NUM];
    BOOL isMatch2 = [pred2 evaluateWithObject:mobile];
    NSPredicate *pred3 = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CT_NUM];
    BOOL isMatch3 = [pred3 evaluateWithObject:mobile];
    
    if (isMatch1 || isMatch2 || isMatch3) {
        return YES;
    }else{
        return NO;
    }
}

#pragma mark 判断身份证号是否合法
+ (BOOL)judgeIdentityStringValid:(NSString *)identityString {
    if (identityString.length != 18) return NO;
    // 正则表达式判断基本 身份证号是否满足格式
    NSString *regex2 = @"^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
    NSPredicate *identityStringPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",regex2];
    //如果通过该验证，说明身份证格式正确，但准确性还需计算
    if(![identityStringPredicate evaluateWithObject:identityString]) return NO;
    //** 开始进行校验 *//
    //将前17位加权因子保存在数组里
    NSArray *idCardWiArray = @[@"7", @"9", @"10", @"5", @"8", @"4", @"2", @"1", @"6", @"3", @"7", @"9", @"10", @"5", @"8", @"4", @"2"];
    //这是除以11后，可能产生的11位余数、验证码，也保存成数组
    NSArray *idCardYArray = @[@"1", @"0", @"10", @"9", @"8", @"7", @"6", @"5", @"4", @"3", @"2"];
    //用来保存前17位各自乖以加权因子后的总和
    NSInteger idCardWiSum = 0;
    for(int i = 0;i < 17;i++) {
        NSInteger subStrIndex  = [[identityString substringWithRange:NSMakeRange(i, 1)] integerValue];
        NSInteger idCardWiIndex = [[idCardWiArray objectAtIndex:i] integerValue];
        idCardWiSum      += subStrIndex * idCardWiIndex;
    }
    //计算出校验码所在数组的位置
    NSInteger idCardMod=idCardWiSum%11;
    //得到最后一位身份证号码
    NSString *idCardLast= [identityString substringWithRange:NSMakeRange(17, 1)];
    //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
    if(idCardMod==2) {
        if(![idCardLast isEqualToString:@"X"]||[idCardLast isEqualToString:@"x"]) {
            return NO;
        }
    }else{
        //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
        if(![idCardLast isEqualToString: [idCardYArray objectAtIndex:idCardMod]]) {
            return NO;
        }
    }
    return YES;
}

#pragma mark 判断银行卡号是否合法
+ (BOOL)isBankCard:(NSString *)cardNumber{
    if(cardNumber.length==0){
        return NO;
    }
    NSString *digitsOnly = @"";
    char c;
    for (int i = 0; i < cardNumber.length; i++){
        c = [cardNumber characterAtIndex:i];
        if (isdigit(c)){
            digitsOnly =[digitsOnly stringByAppendingFormat:@"%c",c];
        }
    }
    int sum = 0;
    int digit = 0;
    int addend = 0;
    BOOL timesTwo = false;
    for (NSInteger i = digitsOnly.length - 1; i >= 0; i--){
        digit = [digitsOnly characterAtIndex:i] - '0';
        if (timesTwo){
            addend = digit * 2;
            if (addend > 9) {
                addend -= 9;
            }
        }
        else {
            addend = digit;
        }
        sum += addend;
        timesTwo = !timesTwo;
    }
    int modulus = sum % 10;
    return modulus == 0;
}

//NSDictionary转JSON
+ (NSString *)dictionaryToJSON:(NSDictionary *)dict{
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dict options:0 error:&error];//NSJSONWritingPrettyPrinted
    if (!jsonData) {
        return @"{}";
    } else {
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    }
}

//NSMutableArray转JSON
+ (NSString *)mutableArrayToJSON:(NSMutableArray *)dictArr{
    NSError *error;
    //convert object to data
    NSData* jsonData = [NSJSONSerialization dataWithJSONObject:dictArr options:0 error:&error];
    if (!jsonData) {
        return @"{}";
    } else {
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    }
}

//NSArray、NSDictionary转换为json
+ (NSString *)objectToJson:(id)obj{
    if (obj == nil) {
        return nil;
    }
    NSError *error = nil;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:obj
                                                       options:0
                                                         error:&error];
    
    if ([jsonData length] && error == nil){
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    }else{
        return nil;
    }
}

//json转NSArray、NSDictionary
+ (id)jsonToObject:(NSString *)json{
    //string转data
    NSData * jsonData = [json dataUsingEncoding:NSUTF8StringEncoding];
    //json解析
    id obj = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:nil];
    return obj;
}



+ (int)stringToInt:(NSString *)str{
    int value = 0;
    @try {
        NSLog(@"doing...");
        value = [str intValue];
    } @catch(NSException *exception) {
        NSLog(@"cathch error...");
        // @throw exception; 写了会正常crash
        NSLog(@"exception = %@", exception);
    } @finally {
        NSLog(@"finish...");
    }
    return value;
}

+ (float)stringToFloat:(NSString *)str{
    float value = 0.0;
    @try {
        NSLog(@"doing...");
        value = [str floatValue];
    } @catch(NSException *exception) {
        NSLog(@"cathch error...");
        // @throw exception; 写了会正常crash
        NSLog(@"exception = %@", exception);
    } @finally {
        NSLog(@"finish...");
    }
    return value;
}

+ (NSString *)orderStateForString:(int)orderState{
    /**
     orderState    int    订单状态 0：待审核，1：未通过，2：已取消，3：待出库，4：已拦截，5：部分出库，6：已出库，7：已完成，8：待发货，9：配送中，10：
     */
    NSString *orderStateStr = @"";
    switch (orderState) {
        case 0:
            orderStateStr = @"待审核";
            break;
            case 1:
                orderStateStr = @"未通过";
                break;
                case 2:
                    orderStateStr = @"已取消";
                    break;
                    case 3:
                        orderStateStr = @"待出库";
                        break;
                        case 4:
                            orderStateStr = @"已拦截";
                            break;
                            case 5:
                                orderStateStr = @"部分出库";
                                break;
                                case 6:
                                    orderStateStr = @"已出库";
                                    break;
                                    case 7:
                                        orderStateStr = @"已完成";
                                        break;
                                        case 8:
                                            orderStateStr = @"待发货";
                                            break;
                                            case 9:
                                                orderStateStr = @"配送中";
                                                break;
                                                case 10:
                                                    orderStateStr = @"已签收";
                                                    break;
            
        default:
        orderStateStr = @"未知状态";
            break;
    }
    return orderStateStr;
}


+ (NSString *)orderReturnStateForString:(int)orderState{
    /**
     orderState    int   退单状态 0：待审核，1：未通过，2：待入库，3：已入库，4：已完成，5: 已取消
     */
    NSString *orderStateStr = @"";
    switch (orderState) {
        case 0:
            orderStateStr = @"待审核";
            break;
            case 1:
                orderStateStr = @"未通过";
                break;
                case 2:
                    orderStateStr = @"待入库";
                    break;
                    case 3:
                        orderStateStr = @"已入库";
                        break;
                        case 4:
                            orderStateStr = @"已完成";
                            break;
                            case 5:
                                orderStateStr = @"已取消";
                                break;
            
        default:
        orderStateStr = @"未知状态";
            break;
    }
    return orderStateStr;
}


/*!
 @brief 修正浮点型精度丢失
 @param str 传入接口取到的数据
 @return 修正精度后的数据
 */
+(NSDecimalNumber *)reviseString:(NSString *)str
{
    //直接传入精度丢失有问题的Double类型
    double conversionValue = [str doubleValue];
    NSString *doubleString = [NSString stringWithFormat:@"%lf", conversionValue];
    NSDecimalNumber *decNumber = [NSDecimalNumber decimalNumberWithString:doubleString];
    return decNumber;
}

///**
// *  视频压缩
// *  @param originFilePath       视频资源的原始路径
// *  @param outputPath      输出路径
// */
//+ (void)compressVideoAccroding:(NSURL *)originFilePath withOutputUrl:(NSString *)outputPath SuccessBlock:(void(^)(NSData *))successBlock {
//    //转码配置
//    AVURLAsset *asset = [AVURLAsset URLAssetWithURL:originFilePath options:nil];
//    AVAssetExportSession *exportSession= [[AVAssetExportSession alloc] initWithAsset:asset presetName:AVAssetExportPresetMediumQuality];
//    exportSession.shouldOptimizeForNetworkUse = YES;
//    exportSession.outputURL = [NSURL fileURLWithPath:outputPath];
//    exportSession.outputFileType = AVFileTypeMPEG4;
//
//    [exportSession exportAsynchronouslyWithCompletionHandler:^{
//        int exportStatus = exportSession.status;
//        NSLog(@"转码状态:%d",exportStatus);
//
//        switch (exportStatus)         {
//            case AVAssetExportSessionStatusFailed:
//            {
//                // log error to text view
//                NSError *exportError = exportSession.error;
//                NSLog (@"AVAssetExportSessionStatusFailed: %@", exportError);
//                break;
//            }
//            case AVAssetExportSessionStatusCompleted:
//            {
//                if (outputPath) {
//                    NSURL *url = [NSURL fileURLWithPath:outputPath];
//                    NSData *videoData = [NSData dataWithContentsOfURL:url];
//                    NSLog(@"视频转码成功:%@",[self getSizeWithData:videoData]);
//                    BLOCK_EXEC(successBlock,videoData);
//                }
//            }
//        }
//    }];
//}
//
////以当前时间合成视频名称
//+ (NSString *)getVideoNameBaseCurrentTime {
//    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
//    [dateFormatter setDateFormat:@"yyyyMMddHHmmss"];
//    return [[dateFormatter stringFromDate:[NSDate date]] stringByAppendingString:@".MP4"];
//}
//
///**获取文件大小*/
//+ (NSString *)getSizeWithData:(NSData *)data{
//
//    double convertedValue = data.length;
//    int multiplyFactor = 0;
//    NSArray *tokens = [NSArray arrayWithObjects:@"bytes",@"KB",@"MB",@"GB",@"TB",@"PB", @"EB", @"ZB", @"YB",nil];
//    while (convertedValue > 1024) {
//        convertedValue /= 1024;
//        multiplyFactor++;
//    }
//    return [NSString stringWithFormat:@"%.2f %@",convertedValue, [tokens objectAtIndex:multiplyFactor]];
//}

@end
