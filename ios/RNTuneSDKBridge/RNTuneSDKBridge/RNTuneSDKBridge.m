//
//  RNTuneSDKBridge.m
//  RNTuneSDKBridge
//
//  Created by Zola Richards on 3/16/16.
//  Copyright © 2016 Zola Richards. All rights reserved.
//


#import "Tune.h"
#import "RCTLog.h"
#import "RCTConvert.h"
#import "RNTuneSDKBridge.h"

@implementation RNTuneSDKBridge {}

- (instancetype)init
{
    if ((self = [super init])) {
        [ self initializeTune];
    }
    
    return self;
}

//- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
//    return YES;
//}
//
//- (void)applicationDidBecomeActive:(UIApplication *)application
//{
//    // Attribution will not function without the measureSession call included
//    [Tune measureSession];
//}
//
//- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
//{
//    // when the app is opened due to a deep link, call the Tune deep link setter
//    [Tune applicationDidOpenURL:[url absoluteString] sourceApplication:sourceApplication];
//    
//    return YES;
//}

- (void)initializeTune {
    
    NSLog(@"TuneSDKBridge IOS didFinishLaunchingWithOptions");
    
    NSDictionary *tuneConfig = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"Tune"];
    NSString *advertiserId   =  [tuneConfig objectForKey:@"advertiserId"];
    NSString *conversionKey  = [tuneConfig objectForKey:@"conversionKey"];
    NSArray *powerHooks       = [tuneConfig objectForKey:@"powerHooks"];
    
    [Tune initializeWithTuneAdvertiserId:advertiserId tuneConversionKey:conversionKey];
    [ self setTunePowerHooks:powerHooks];
    
    // Attribution will not function without the measureSession call included
    [Tune measureSession];
    
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
    NSNumber *unitPrice = [RCTConvert NSNumber:event[@"unitPrice"]];
    NSNumber *quantity  = [RCTConvert NSNumber:event[@"quantity"]];
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
        [events addObject:[ self getEventItem:item]];
    }
    
    return events;
}

-(NSDate *)getDateObject:(NSDictionary *)dateDic {
    NSNumber *day = [RCTConvert NSNumber:dateDic[@"day"]];
    NSNumber *year  = [RCTConvert NSNumber:dateDic[@"year"]];
    NSNumber *month   = [RCTConvert NSNumber:dateDic[@"month"]];
    
    
    
    NSDateComponents *comps = [[NSDateComponents alloc] init];
    [comps setDay:day.integerValue];
    [comps setMonth:month.integerValue];
    [comps setYear:year.integerValue];
    
    NSCalendar *gregorian = [[NSCalendar alloc] initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
    NSDate *date = [gregorian dateFromComponents:comps];
    
    return date;
}

- (void)setTunePowerHooks:(NSArray *)powerHooks {
    
    for (NSDictionary *hook in powerHooks) {
        [ self setHook:hook];
    }
    
}

- (void)setHook:(NSDictionary *)hook {
    
    NSString *hookId = [RCTConvert NSString:hook[@"hookId"]];
    NSString *hookName  = [RCTConvert NSString:hook[@"hookName"]];
    NSString *hookDefault   = [RCTConvert NSString:hook[@"hookDefault"]];
    
    [Tune registerHookWithId:hookId friendlyName:hookName defaultValue:hookDefault];
    
}

// START OF THE BRIDGED OVER METHODS
RCT_EXPORT_MODULE(TuneSDKBridge);

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
    [ self setTuneGender:gender];
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];

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
    [ self setTuneGender:gender];
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    
    [Tune measureEventName:TUNE_EVENT_REGISTRATION];
    
}

RCT_EXPORT_METHOD(addToCart:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  gender:(nonnull NSString *)gender
                  age:(nonnull NSNumber *)age
                  revenue:(nonnull NSNumber *)revenue
                  currencyCode:(nonnull NSString *)currencyCode
                  location:(nonnull NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    
    [Tune setAge:age.intValue];
    [ self setTuneGender:gender];
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_ADD_TO_CART];
    event.eventItems = [ self getEventItems:eventItems];
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(addToWishList:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  currencyCode:(nonnull NSString *)currencyCode
                  location:(nonnull NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_ADD_TO_WISHLIST];
    event.eventItems = [ self getEventItems:eventItems];
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

// (String id,String userIdType)
RCT_EXPORT_METHOD(addedPaymentInfo:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType) {
    [ self setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_ADDED_PAYMENT_INFO];
}

RCT_EXPORT_METHOD(checkoutInitiated:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  revenue:(nonnull NSNumber *)revenue
                  currencyCode:(nonnull NSString *)currencyCode
                  advertiserRefId:(nonnull NSString *)advertiserRefId
                  location:(nonnull NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    NSLog(@"TuneSDKBridge IOS checkoutInitiated");
    
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_CHECKOUT_INITIATED];
    event.eventItems = [ self getEventItems:eventItems];
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
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_PURCHASE];
    event.eventItems = [ self getEventItems:eventItems];
    event.refId = advertiserRefId;
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(reservation:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  gender:(nonnull NSString *)gender
                  age:(nonnull NSNumber *)age
                  revenue:(nonnull NSNumber *)revenue
                  quantity:(nonnull NSNumber *)quantity
                  currencyCode:(nonnull NSString *)currencyCode
                  advertiserRefId:(nonnull NSString *)advertiserRefId
                  location:(NSDictionary *)location
                  date1:(nonnull NSDictionary *)date1
                  date2:(nonnull NSDictionary *)date2)
{
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_RESERVATION];
    event.date1 = [ self getDateObject:date2];
    event.date2 = [ self getDateObject:date2];
    event.quantity = quantity.integerValue;
    event.refId = advertiserRefId;
    event.revenue = revenue.floatValue;
    event.currencyCode = currencyCode;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(search:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  currencyCode:(nonnull NSString *)currencyCode
                  searchString:(nonnull NSString *)searchString
                  quantity:(nonnull NSNumber *)quantity
                  location:(NSDictionary *)location
                  date1:(nonnull NSDictionary *)date1
                  date2:(nonnull NSDictionary *)date2
                  eventItems:(nonnull NSArray *)eventItems)
{
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_SEARCH];
    event.currencyCode = currencyCode;
    event.searchString = searchString;
    event.date1 = [NSDate date];
    event.date2 = [NSDate dateWithTimeIntervalSinceNow:86400];
    event.quantity = quantity.intValue;
    event.eventItems = [ self getEventItems:eventItems];
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(contentView:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  currencyCode:(nonnull NSString *)currencyCode
                  location:(NSDictionary *)location
                  eventItems:(nonnull NSArray *)eventItems)
{
    [ self setTuneLocation:location];
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_CONTENT_VIEW];
    event.currencyCode = currencyCode;
    event.eventItems = [ self getEventItems:eventItems];
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(achievementUnlocked:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType contentId:(nonnull NSString *)contentId)
{
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_ACHIEVEMENT_UNLOCKED];
    event.contentId = contentId;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(levelAchieved:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType level:(nonnull NSNumber *)level)
{
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_LEVEL_ACHIEVED];
    event.level = level.intValue;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(spentCredits:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType credits:(nonnull NSNumber *)credits)
{
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_SPENT_CREDITS];
    event.quantity = credits.intValue;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(tutorialComplete:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType) {

    [ self setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_TUTORIAL_COMPLETE];
}

RCT_EXPORT_METHOD(invite:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType)
{
    [ self setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_INVITE];
}

RCT_EXPORT_METHOD(rated:(nonnull NSString *)id
                  userIdType:(nonnull NSString *)userIdType
                  rating:(nonnull NSNumber *)rating
                  contentId:(nonnull NSString *)contentId)
{
    [ self setTuneUserType:id type:userIdType];
    TuneEvent *event = [TuneEvent eventWithName:TUNE_EVENT_RATED];
    event.rating = rating.floatValue;
    event.contentId = contentId;
    
    [Tune measureEvent:event];
}

RCT_EXPORT_METHOD(share:(nonnull NSString *)id userIdType:(nonnull NSString *)userIdType)
{
    [ self setTuneUserType:id type:userIdType];
    [Tune measureEventName:TUNE_EVENT_SHARE];
}

RCT_REMAP_METHOD(getPowerHookValues,
                 hookIds:(nonnull NSArray *)hookIds
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    NSMutableDictionary *hookValues = [[NSMutableDictionary alloc] init];
    
    for (NSString *hookId in hookIds) {
        hookValues[hookId] = [Tune getValueForHookById:hookId];
    }

    
    if (hookValues) {
        resolve(hookValues);
    } else {
        NSError *error = [NSError errorWithDomain:@"Tune Hook Values Erorr" code:404 userInfo:nil];
        reject(@"no_events", @"There were no events", error);
    }
}

@end







