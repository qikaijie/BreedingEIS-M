//
//  GJDateUtils.m
//  storemanager
//
//  Created by Apple on 18/3/16.
//  Copyright © 2018年 江苏银企通科技有限公司. All rights reserved.
//

#import "GJDateUtils.h"
#import "NSDate+Reporting.h"

@implementation GJDateUtils



+(NSString *)getCurrentDate{
    NSDateFormatter *date_formatter = [[NSDateFormatter alloc] init];
    [date_formatter setDateFormat:@"yyyy-MM-dd"];
    NSString *current_date_str = [date_formatter stringFromDate:[NSDate date]];
    return current_date_str;
}

+(NSString *)getCurrentDate:(NSString *)formatter{
    NSDateFormatter *date_formatter = [[NSDateFormatter alloc] init];
    [date_formatter setDateFormat:formatter];
    NSString *current_date_str = [date_formatter stringFromDate:[NSDate date]];
    return current_date_str;
}

+(NSString *)getNDay:(NSInteger)n{
    NSDate*nowDate = [NSDate date];
    NSDate* theDate;
    if(n!=0){
        NSTimeInterval  oneDay = 24*60*60*1;  //1天的长度
        theDate = [nowDate initWithTimeIntervalSinceNow: oneDay*n ];//initWithTimeIntervalSinceNow是从现在往前后推的秒数
    }else{
        theDate = nowDate;
    }
    NSDateFormatter *date_formatter = [[NSDateFormatter alloc] init];
    [date_formatter setDateFormat:@"yyyy-MM-dd"];
    NSString *the_date_str = [date_formatter stringFromDate:theDate];
    
    return the_date_str;
}

#pragma mark - 获取上周日期区间
+ (NSString *)getPWeek{
    NSTimeInterval  oneDay = 24*60*60*1;  //1天的长度
    NSDate *nowDate = [[NSDate date] initWithTimeIntervalSinceNow: oneDay*(-7) ];//initWithTimeIntervalSinceNow是从现在往前后推的秒数
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *comp = [calendar components:NSYearCalendarUnit | NSMonthCalendarUnit | NSDayCalendarUnit | NSWeekdayCalendarUnit | NSDayCalendarUnit fromDate:nowDate];
    // 获取今天是周几
    NSInteger weekDay = [comp weekday];
    // 获取几天是几号
    NSInteger day = [comp day];
    // 计算当前日期和本周的星期一和星期天相差天数
    long firstDiff,lastDiff;
    //    weekDay = 1;
    if (weekDay == 1)
    {
        firstDiff = -6;
        lastDiff = 0;
    }
    else
    {
        firstDiff = [calendar firstWeekday] - weekDay + 1;
        lastDiff = 8 - weekDay;
    }
    NSLog(@"firstDiff: %ld   lastDiff: %ld",firstDiff,lastDiff);
    NSDate *stratDate = [[NSDate date] initWithTimeIntervalSinceNow: oneDay*(firstDiff-7) ];//initWithTimeIntervalSinceNow是从现在往前后推的秒数
    NSDate *endDate = [[NSDate date] initWithTimeIntervalSinceNow: oneDay*(lastDiff-7) ];//initWithTimeIntervalSinceNow是从现在往前后推的秒数
    //用于格式化NSDate对象
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    //设置格式：zzz表示时区
    [dateFormatter setDateFormat:@"yyyy-MM-dd"];
    //获取上月第一天
    NSString *startTime = [NSString stringWithFormat:@"%@",[dateFormatter stringFromDate:stratDate]];
    //获取上月最后一天
    NSString *endTime = [NSString stringWithFormat:@"%@",[dateFormatter stringFromDate:endDate]];
    return [NSString stringWithFormat:@"%@|%@",startTime,endTime];
}

#pragma mark - 获取上月日期区间
+ (NSString *)getPMonth{
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *comp = [calendar components:NSYearCalendarUnit | NSMonthCalendarUnit | NSDayCalendarUnit | NSWeekdayCalendarUnit | NSDayCalendarUnit fromDate:[NSDate date]];
    
    NSInteger yearDay = [comp year];
    NSInteger monthDay = [comp month];
    
    int newYear;
    int newMonth;
    //获取上个月的最后一天
    if(monthDay != 1){
        newMonth = (int)monthDay-1;
        newYear = (int)yearDay;
    }else{
        newMonth = 12;
        newYear = (int)yearDay-1;
    }
    //上个月的天数
    int newMonthWithDay;
    if((newMonth == 0)||(newMonth == 1)||(newMonth == 3)||(newMonth == 5)||(newMonth == 7)||(newMonth == 8)||(newMonth == 10)||(newMonth == 12)){
        newMonthWithDay = 31;
    }else if((newMonth == 4)||(newMonth == 6)||(newMonth == 9)||(newMonth == 11)){
        newMonthWithDay =  30;
    }else if((newYear%4 == 1)||(newYear%4 == 2)||(newYear%4 == 3))
    {
        newMonthWithDay =  28;
    }else if(newYear%400 == 0){
        newMonthWithDay =  29;
    }else if(newYear%100 == 0){
        newMonthWithDay =  28;
    }else{
        newMonthWithDay =  29;
    }
    //获取上月第一天
    NSString *startTime = [NSString stringWithFormat:@"%d-%@-01",newYear,(newMonth<10 ? [NSString stringWithFormat:@"0%d",newMonth] : [NSString stringWithFormat:@"%d",newMonth])];
    //获取上月最后一天
    NSString *endTime = [NSString stringWithFormat:@"%d-%@-%@",newYear,(newMonth<10 ? [NSString stringWithFormat:@"0%d",newMonth] : [NSString stringWithFormat:@"%d",newMonth]),(newMonthWithDay<10 ? [NSString stringWithFormat:@"0%d",newMonthWithDay] : [NSString stringWithFormat:@"%d",newMonthWithDay])];
    return [NSString stringWithFormat:@"%@|%@",startTime,endTime];
}

// 获取某年某月总共多少天
- (int)getDaysInMonth:(int)year month:(int)imonth {
    // imonth == 0的情况是应对在CourseViewController里month-1的情况
    if((imonth == 0)||(imonth == 1)||(imonth == 3)||(imonth == 5)||(imonth == 7)||(imonth == 8)||(imonth == 10)||(imonth == 12))
        return 31;
    if((imonth == 4)||(imonth == 6)||(imonth == 9)||(imonth == 11))
        return 30;
    if((year%4 == 1)||(year%4 == 2)||(year%4 == 3))
    {
        return 28;
    }
    if(year%400 == 0)
        return 29;
    if(year%100 == 0)
        return 28;
    return 29;
}

+(NSArray *)getMonthBeginAndEndWith:(NSDate *)date
{
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDate *firstDay;
    [calendar rangeOfUnit:NSMonthCalendarUnit startDate:&firstDay interval:nil forDate:date];
    NSDateComponents *lastDateComponents = [calendar components:NSMonthCalendarUnit | NSYearCalendarUnit |NSDayCalendarUnit fromDate:firstDay];
    NSUInteger dayNumberOfMonth = [calendar rangeOfUnit:NSDayCalendarUnit inUnit:NSMonthCalendarUnit forDate:[NSDate date]].length;
    NSInteger day = [lastDateComponents day];
    [lastDateComponents setDay:day+dayNumberOfMonth];
    NSDate *lastDay = [calendar dateFromComponents:lastDateComponents];
    return [NSArray arrayWithObjects:firstDay,lastDay, nil];
}

#pragma mark - 获取去年日期区间
+ (NSString *)getPYear{
    NSString *nowDateString = [self getCurrentDate];
    int nowYear = [[nowDateString substringWithRange:NSMakeRange(0, 4)] intValue];
    //获取上月第一天
    NSString *startTime = [NSString stringWithFormat:@"%d-01-01",nowYear-1];
    //获取上月最后一天
    NSString *endTime = [NSString stringWithFormat:@"%d-12-31",nowYear-1];
    return [NSString stringWithFormat:@"%@|%@",startTime,endTime];
}

#pragma mark - 获取当前时间的 时间戳
+(NSInteger)getNowTimestamp{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"YYYY-MM-dd HH:mm:ss"]; // ----------设置你想要的格式,hh与HH的区别:分别表示12小时制,24小时制
    //设置时区,这个对于时间的处理有时很重要
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Beijing"];
    [formatter setTimeZone:timeZone];
    NSDate *datenow = [NSDate date];//现在时间
    NSLog(@"设备当前的时间:%@",[formatter stringFromDate:datenow]);
    //时间转时间戳的方法:
    NSInteger timeSp = [[NSNumber numberWithDouble:[datenow timeIntervalSince1970]] integerValue];
    NSLog(@"设备当前的时间戳:%ld",(long)timeSp); //时间戳的值
    return timeSp;
}

#pragma mark - 将某个时间转化成 时间戳
+(NSInteger)timeSwitchTimestamp:(NSString *)formatTime andFormatter:(NSString *)format{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:format]; //(@"YYYY-MM-dd hh:mm:ss") ----------设置你想要的格式,hh与HH的区别:分别表示12小时制,24小时制
    NSTimeZone* timeZone = [NSTimeZone timeZoneWithName:@"Asia/Beijing"];
    [formatter setTimeZone:timeZone];
    NSDate* date = [formatter dateFromString:formatTime]; //------------将字符串按formatter转成nsdate
    //时间转时间戳的方法:
    NSInteger timeSp = [[NSNumber numberWithDouble:[date timeIntervalSince1970]] integerValue];
    NSLog(@"将某个时间转化成 时间戳&&&&&&&timeSp:%ld",(long)timeSp); //时间戳的值
    return timeSp;
}

#pragma mark - 将某个时间戳转化成 时间
+(NSString *)timestampSwitchTime:(NSInteger)timestamp andFormatter:(NSString *)format{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:format]; // （@"YYYY-MM-dd hh:mm:ss"）----------设置你想要的格式,hh与HH的区别:分别表示12小时制,24小时制
    NSTimeZone *timeZone = [NSTimeZone timeZoneWithName:@"Asia/Beijing"];
    [formatter setTimeZone:timeZone];
    NSDate *confromTimesp = [NSDate dateWithTimeIntervalSince1970:timestamp/1000.0];
    NSLog(@"1296035591  = %@",confromTimesp);
    NSString *confromTimespStr = [formatter stringFromDate:confromTimesp];
    return confromTimespStr;
}

@end
