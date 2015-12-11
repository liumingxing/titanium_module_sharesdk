/**
 * titanium_module_sharesdk
 *
 * Created by Your Name
 * Copyright (c) 2015 Your Company. All rights reserved.
 */

#import "TiModule.h"

/**
 粘贴板数据编码方式，目前只有两种:
 1. [NSKeyedArchiver archivedDataWithRootObject:data];
 2. [NSPropertyListSerialization dataWithPropertyList:data format:NSPropertyListBinaryFormat_v1_0 options:0 error:&err];
 */
typedef enum : NSUInteger {
    OSPboardEncodingKeyedArchiver,
    OSPboardEncodingPropertyListSerialization,
} OSPboardEncoding;

@interface ComMamashaiSharesdkModule : TiModule
{
    @private
    BOOL hasSinaWeibo, hasWechat, hasWechatMoments, hasWechatFavorite, hasTencentWeibo, hasQZone, hasQQ, hasEmail, hasShortMessage;
    BOOL oneKeyShareSinaWeibo, oneKeyShareWechat, oneKeyShareWechatMoments, oneKeyShareWechatFavorite, oneKeyShareTencentWeibo, oneKeyShareQZone, oneKeyShareQQ, oneKeyShareEmail, oneKeyShareShortMessage;
}

-(void)share:(id)args;

@end
