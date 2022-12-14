//
//  LBXContactPermission.m
//  LBXKits
//
//  Created by lbx on 2017/9/3.
//  Copyright © 2017年 lbx. All rights reserved.
//

#import "LBXPermissionContacts.h"
#import <UIKit/UIKit.h>
#import <AddressBook/AddressBook.h>

@implementation LBXPermissionContacts

+ (BOOL)authorized
{
    ABAuthorizationStatus status = ABAddressBookGetAuthorizationStatus();
    return status == kABAuthorizationStatusAuthorized;
}

/**
 access authorizationStatus

 @return ABAuthorizationStatus:prior to iOS 9 or CNAuthorizationStatus after iOS 9
 */
+ (NSInteger)authorizationStatus
{
    NSInteger status;
    status = ABAddressBookGetAuthorizationStatus();
    return status;
}

+ (void)authorizeWithCompletion:(void(^)(BOOL granted,BOOL firstTime))completion
{
    ABAuthorizationStatus status = ABAddressBookGetAuthorizationStatus();
    
    switch (status) {
        case kABAuthorizationStatusAuthorized: {
            if (completion) {
                completion(YES,NO);
            }
        } break;
        case kABAuthorizationStatusNotDetermined: {
            ABAddressBookRef addressBook = ABAddressBookCreateWithOptions(NULL, NULL);
            ABAddressBookRequestAccessWithCompletion(addressBook, ^(bool granted, CFErrorRef error) {
                if (completion) {
                    dispatch_async(dispatch_get_main_queue(), ^{
                        completion(granted,YES);
                    });
                }
            });
        } break;
        case kABAuthorizationStatusRestricted:
        case kABAuthorizationStatusDenied: {
            if (completion) {
                completion(NO,NO);
            }
        } break;
    }
}



@end
