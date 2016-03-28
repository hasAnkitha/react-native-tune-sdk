//
//  RNTuneSDKBridge.m
//  RNTuneSDKBridge
//
//  Created by Zola Richards on 3/16/16.
//  Copyright © 2016 Zola Richards. All rights reserved.
//


#import "RCTLog.h"
#import "RCTConvert.h"
#import "RNTuneSDKBridge.h"
#import "Tune.framework/Headers/Tune.h"

@implementation RNTuneSDKBridge {}

- (instancetype)init
{
    if ((self = [super init])) {
        [Tune initializeWithTuneAdvertiserId:[[NSBundle mainBundle] objectForInfoDictionaryKey:@"TUNEadvertiserId"]
                           tuneConversionKey:[[NSBundle mainBundle] objectForInfoDictionaryKey:@"TUNEconversionKey"]
        ];
    }
    
    return self;
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Attribution will not function without the measureSession call included
    [Tune measureSession];
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    // when the app is opened due to a deep link, call the Tune deep link setter
    [Tune applicationDidOpenURL:[url absoluteString] sourceApplication:sourceApplication];
    
    return YES;
}

// START OF THE BRIDGED HELPER METHODS
- (void)setTuneUserType:(NSString *)id type:(NSString *)type {
    
    if ([type isEqualToString:@"facebook"]){
        [Tune setFacebookUserId:id];
    } else if([type isEqualToString:@"google"]) {
        [Tune setGoogleUserId:id];
    } else if([type isEqualToString:@"twitter"]) {
        [Tune setTwitterUserId:id];
    } else {
        [Tune setUserId:id];
    }
}

- (void)setTuneGender:(NSString *)gender {
    if([gender isEqualToString:@"MALE"]) {
        [Tune setGender:TuneGenderMale];
    } else {
        [Tune setGender:TuneGenderFemale];
    }
}

-(void)setTuneLocation:(NSDictionary *)location {
    NSNumber *altitude;
    NSNumber *latitude;
    NSNumber *longitude;
    
    TuneLocation *loc = [TuneLocation new];
    
    if ((latitude=[location objectForKey:@"latitude"])) {
        latitude = [RCTConvert NSNumber:location[@"latitude"]];
        loc.latitude = latitude;
    }
    
    if ((longitude=[location objectForKey:@"longitude"])) {
        longitude = [RCTConvert NSNumber:location[@"longitude"]];
        loc.longitude = longitude;
    }
    
    
    if ((altitude=[location objectForKey:@"altitude"])) {
        altitude = [RCTConvert NSNumber:location[@"altitude"]];
        loc.altitude = altitude;
    }
    
    [Tune setLocation:loc];
}

-(TuneEventItem *)getEventItem:(NSDictionary *)event {
    NSNumber *unitPrice = [RCTConvert NSNumber:event[@"unitPrice"]];;
    NSNumber *quantity  = [RCTConvert NSNumber:event[@"quantity"]];;
    NSNumber *revenue   = [RCTConvert NSNumber:event[@"revenue"]];
    
    TuneEventItem *item = [TuneEventItem eventItemWithName:[RCTConvert NSString:event[@"name"]]
                                        unitPrice:unitPrice.floatValue
                                        quantity:quantity.integerValue
                                        revenue:revenue.floatValue
                                        attribute1:[RCTConvert NSString:event[@"attribute1"]]
                                        attribute2:[RCTConvert NSString:event[@"attribute2"]]
                                        attribute3:[RCTConvert NSString:event[@"attribute3"]]
                                        attribute4:[RCTConvert NSString:event[@"attribute4"]]
                                        attribute5:[RCTConvert NSString:event[@"attribute5"]]
                           ];
    return item;
}

-(NSArray *)getEventItems:(NSArray *)eventItems {
    NSMutableArray *events;
    for (NSDictionary *item in eventItems) {
        [events addObject:[[self class] getEventItem:item]];
    }
    
    return events;
}

-(NSDate *)getDateObject:(NSDictionary *)dateDic {
    NSNumber *day = [RCTConvert NSNumber:dateDic[@"day"]];;
    NSNumber *year  = [RCTConvert NSNumber:dateDic[@"year"]];;
    NSNumber *month   = [RCTConvert NSNumber:dateDic[@"month"]];
    
    
    
    NSDateComponents *comps = [[NSDateComponents alloc] init];
    [comps setDay:day.integerValue];
    [comps setMonth:month.integerValue];
    [comps setYear:year.integerValue];
    
    NSCalendar *gregorian = [[NSCalendar alloc] initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
    NSDate *date = [gregorian dateFromComponents:comps];
    
    return date;
}

// START OF THE BRIDGED OVER METHODS
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  email:(nonnull NSString *)email
                  name:(nonnull NSString *)name
                  age:(nonnull NSNumber *)age
                  gender:(nonnull NSString *)gender
                  location:(NSDictionary *)location)
{
    [Tune setAge:age.intValue];
    [Tune setUserName:name];
    [Tune setUserEmail:email];
    [[self class] setTuneGender:gender];
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];

    [Tune measureEventName:TUNE_EVENT_LOGIN];
}

RCT_EXPORT_METHOD(registration:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  email:(nonnull NSString *)email
                  name:(nonnull NSString *)name
                  age:(nonnull NSNumber *)age
                  gender:(nonnull NSString *)gender
                  location:(NSDictionary *)location)
{
    [Tune setAge:age.intValue];
    [Tune setUserName:name];
    [Tune setUserEmail:email];
    [[self class] setTuneGender:gender];
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    
    [Tune measureEventName:TUNE_EVENT_REGISTRATION];
    
}

RCT_EXPORT_METHOD(addToCart:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  gender:(nonnull NSString *)gender
                  location:(nonnull NSDictionary *)location
                  age:(nonnull NSNumber *)age
                  revenue:(nonnull NSNumber *)revenue
                  currencyCode:(nonnull NSString *)currencyCode
                  eventItems:(nonnull NSArray *)eventItems)
{
    
    [Tune setAge:age.intValue];
    [[self class] setTuneGender:gender];
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_ADD_TO_CART];
    event.eventItems = [[self class] getEventItems:eventItems];
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(addToWishlist:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  currencyCode:(nonnull NSString *)currencyCode
                  location:(nonnull NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_ADD_TO_WISHLIST];
    event.eventItems = [[self class] getEventItems:eventItems];
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

// (String id,String userIdType)
RCT_EXPORT_METHOD(addedPaymentInfo:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType) {
    [[self class] setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_ADDED_PAYMENT_INFO];
}

RCT_EXPORT_METHOD(checkoutInitiated:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  revenue:(nonnull NSNumber *)revenue
                  currencyCode:(nonnull NSString *)currencyCode
                  advertiserRefId:(nonnull NSString *)advertiserRefId
                  eventItems:(nonnull NSArray *)eventItems)
{
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_CHECKOUT_INITIATED];
    event.eventItems = [[self class] getEventItems:eventItems];
    event.refId = advertiserRefId;
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];

}

RCT_EXPORT_METHOD(purchase:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  revenue:(nonnull NSNumber *)revenue
                  currencyCode:(nonnull NSString *)currencyCode
                  advertiserRefId:(nonnull NSString *)advertiserRefId
                  location:(NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_PURCHASE];
    event.eventItems = [[self class] getEventItems:eventItems];
    event.refId = advertiserRefId;
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(reservation:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  gender:(nonnull NSString *)gender
                  location:(NSDictionary *)location
                  age:(nonnull NSNumber *)age
                  revenue:(nonnull NSNumber *)revenue
                  quantity:(nonnull NSNumber *)quantity
                  currencyCode:(nonnull NSString *)currencyCode
                  advertiserRefId:(nonnull NSString *)advertiserRefId
                  date1:(nonnull NSDictionary *)date1
                  date2:(nonnull NSDictionary *)date2)
{
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_RESERVATION];
    event.date1 = [[self class] getDateObject:date2];
    event.date2 = [[self class] getDateObject:date2];
    event.quantity = quantity.integerValue;
    event.refId = advertiserRefId;
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(search:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  quantity:(nonnull NSNumber *)quantity
                  currencyCode:(nonnull NSString *)currencyCode
                  searchString:(nonnull NSString *)searchString
                  location:(NSDictionary *)location
                  date1:(nonnull NSDictionary *)date1
                  date2:(nonnull NSDictionary *)date2
                  eventItems:(nonnull NSArray *)eventItems)
{
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_SEARCH];
    event.currencyCode = currencyCode;
    event.searchString = searchString;
    event.date1 = [NSDate date];
    event.date2 = [NSDate dateWithTimeIntervalSinceNow:86400];
    event.quantity = quantity.intValue;
    event.eventItems = [[self class] getEventItems:eventItems];
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(contentView:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  currencyCode:(nonnull NSString *)currencyCode
                  location:(NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    [[self class] setTuneLocation:location];
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_CONTENT_VIEW];
    event.currencyCode = currencyCode;
    event.eventItems = [[self class] getEventItems:eventItems];
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(achievementUnlocked:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType contentId:(nonnull NSString *)contentId)
{
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_ACHIEVEMENT_UNLOCKED];
    event.contentId = contentId;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(levelAchieved:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType level:(nonnull NSNumber *)level)
{
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_LEVEL_ACHIEVED];
    event.level = level.intValue;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(spentCredits:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType credits:(nonnull NSNumber *)credits)
{
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_SPENT_CREDITS];
    event.quantity = credits.intValue;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(tutorialComplete:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType) {

    [[self class] setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_TUTORIAL_COMPLETE];
}

RCT_EXPORT_METHOD(invite:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType)
{
    [[self class] setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_INVITE];
}

RCT_EXPORT_METHOD(rated:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  rating:(nonnull NSNumber *)rating
                  contentId:(nonnull NSString *)contentId)
{
    [[self class] setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_RATED];
    event.rating = rating.floatValue;
    event.contentId = contentId;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(share:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType)
{
    [[self class] setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_SHARE];
}

@end








