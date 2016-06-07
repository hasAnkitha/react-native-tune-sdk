package com.tune.react.TuneSDKBridge;

 import com.tune.Tune;
 import com.tune.TuneEvent;
 import com.tune.TuneGender;
 import com.tune.TuneLocation;
 import com.tune.TuneEventItem;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.List;
 import java.lang.System;

 import android.location.Location;

 import com.facebook.react.bridge.Arguments;
 import com.facebook.react.bridge.Promise;
 import com.facebook.react.bridge.ReactMethod;
 import com.facebook.react.bridge.ReadableMap;
 import com.facebook.react.bridge.ReadableArray;
 import com.facebook.react.bridge.ReactApplicationContext;
 import com.facebook.react.bridge.ReactContextBaseJavaModule;
 import com.facebook.react.bridge.WritableMap;

 public class TuneSDKBridge extends ReactContextBaseJavaModule {

     public TuneSDKBridge(ReactApplicationContext reactContext) {
         super(reactContext);
         System.out.println("TuneSDKBridge constructor");
     }

     @Override
     public String getName() {
         return "TuneSDKBridge";
     }

     @ReactMethod
     public void login(String id,String userIdType,String email, String name, Integer age, String gender, ReadableMap location) {
         System.out.println(" TuneSDKBridge.login");
         Tune tune = Tune.getInstance();
         TuneGender tuneGender = ( gender.equals("MALE") ) ? TuneGender.MALE : TuneGender.FEMALE;

         this.setUserId(tune, userIdType, id);

         tune.setAge(age);
         tune.setUserName(name);
         tune.setLocation(this.getLocation(location));
         tune.setUserEmail(email);
         tune.setGender(tuneGender);
         tune.measureEvent(TuneEvent.LOGIN);
     }

     @ReactMethod
     public void registration(String id,String userIdType, String email, String name, Integer age, String gender, ReadableMap location) {
         System.out.println(" TuneSDKBridge.registration");
         Tune tune = Tune.getInstance();
         TuneGender tuneGender = ( gender.equals("MALE") ) ? TuneGender.MALE : TuneGender.FEMALE;

         this.setUserId(tune, userIdType, id);
         tune.setAge(age);
         tune.setUserName(name);
         tune.setLocation(this.getLocation(location));
         tune.setUserEmail(email);
         tune.setGender(tuneGender);
         tune.measureEvent(TuneEvent.REGISTRATION);
     }

     // eCommerce
     @ReactMethod
     public void addToCart(String id,String userIdType, String gender, Integer age, Float revenue, String currencyCode,ReadableMap location, ReadableArray eventItems) {
         System.out.println(" TuneSDKBridge.addToCart");
         Tune tune = Tune.getInstance();
         TuneGender tuneGender = ( gender.equals("MALE") ) ? TuneGender.MALE : TuneGender.FEMALE;

         this.setUserId(tune, userIdType, id);

         tune.setAge(age);
         tune.setGender(tuneGender);
         tune.setLocation(this.getLocation(location));
         tune.measureEvent(new TuneEvent(TuneEvent.ADD_TO_CART)
                 .withEventItems(this.getTuneEventItemList(eventItems))
                 .withRevenue(revenue)
                 .withCurrencyCode(currencyCode));
     }

     //
     @ReactMethod
     public void addToWishList(String id, String userIdType, String currencyCode, ReadableMap location, ReadableArray eventItems) {
         System.out.println(" TuneSDKBridge.addToWishlist");
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.setLocation(this.getLocation(location));
         tune.setCurrencyCode(currencyCode);
         tune.measureEvent(new TuneEvent(TuneEvent.ADD_TO_WISHLIST).withEventItems(this.getTuneEventItemList(eventItems)));
     }

     //
     @ReactMethod
     public void addedPaymentInfo(String id, String userIdType) {
         System.out.println(" TuneSDKBridge.addedPaymentInfo");
         Tune tune = Tune.getInstance();
         this.setUserId(tune, userIdType, id);

         tune.measureEvent(TuneEvent.ADDED_PAYMENT_INFO);
     }

     //
     @ReactMethod
     public void checkoutInitiated(String id, String userIdType, Float revenue, String currencyCode, String advertiserRefId, ReadableMap location, ReadableArray eventItems) {
         System.out.println(" TuneSDKBridge.checkoutInitiated");
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.setLocation(this.getLocation(location));
         tune.measureEvent(new TuneEvent(TuneEvent.CHECKOUT_INITIATED)
                 .withEventItems(this.getTuneEventItemList(eventItems))
                 .withRevenue(revenue)
                 .withCurrencyCode(currencyCode)
                 .withAdvertiserRefId(advertiserRefId));
     }

     //
     @ReactMethod
     public void purchase(String id, String userIdType,  Float revenue, String currencyCode, String advertiserRefId, ReadableMap location, ReadableArray eventItems) {
         System.out.println(" TuneSDKBridge.purchase");
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.setLocation(this.getLocation(location));
         tune.measureEvent(new TuneEvent(TuneEvent.PURCHASE)
                 .withEventItems(this.getTuneEventItemList(eventItems))
                 .withRevenue(revenue)
                 .withCurrencyCode(currencyCode)
                 .withAdvertiserRefId(advertiserRefId));
     }

     //
     @ReactMethod
     public void reservation(String id, String userIdType, String gender, Integer age, Float revenue, Integer quantity, String currencyCode, String advertiserRefId, ReadableMap location, ReadableMap date1, ReadableMap date2) {
         System.out.println(" TuneSDKBridge.reservation");
         Tune tune = Tune.getInstance();
         TuneGender tuneGender = ( gender.equals("MALE") ) ? TuneGender.MALE : TuneGender.FEMALE;

         this.setUserId(tune, userIdType, id);
         tune.setAge(age);
         tune.setGender(tuneGender);
         tune.setLocation(this.getLocation(location));
         tune.measureEvent(new TuneEvent(TuneEvent.RESERVATION)
                 .withRevenue(revenue)
                 .withCurrencyCode(currencyCode)
                 .withAdvertiserRefId(advertiserRefId)
                 .withDate1(this.getDate(date1))
                 .withDate2(this.getDate(date2))
                 .withQuantity(quantity));
     }

     //
     @ReactMethod
     public void search (String id, String userIdType, String currencyCode, String searchString, Integer quantity, ReadableMap location, ReadableMap date1, ReadableMap date2, ReadableArray eventItems) {
         System.out.println(" TuneSDKBridge.search");
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.setLocation(this.getLocation(location));
         tune.measureEvent(new TuneEvent(TuneEvent.SEARCH)
                 .withCurrencyCode(currencyCode)
                 .withEventItems(this.getTuneEventItemList(eventItems))
                 .withSearchString(searchString)
                 .withDate1(this.getDate(date1))
                 .withDate2(this.getDate(date2))
                 .withQuantity(quantity));
     }

     //
     @ReactMethod
     public void contentView (String id, String userIdType, String currencyCode, ReadableMap location, ReadableArray eventItems) {
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.setLocation(this.getLocation(location));
         tune.setCurrencyCode(currencyCode);
         tune.measureEvent(new TuneEvent(TuneEvent.CONTENT_VIEW).withEventItems(this.getTuneEventItemList(eventItems)));
     }

     //
     @ReactMethod
     public void achievementUnlocked (String id, String userIdType, String contentId) {
         Tune tune = Tune.getInstance();
         this.setUserId(tune, userIdType, id);
         tune.measureEvent(new TuneEvent(TuneEvent.ACHIEVEMENT_UNLOCKED).withContentId(contentId));
     }

     //
     @ReactMethod
     public void levelAchieved (String id, String userIdType, Integer level) {
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.measureEvent(new TuneEvent(TuneEvent.LEVEL_ACHIEVED).withLevel(level));
     }

     //
     @ReactMethod
     public void spentCredits (String id, String userIdType, Integer credits) {
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.measureEvent(new TuneEvent(TuneEvent.SPENT_CREDITS).withQuantity(credits));
     }

     // tutorial complete Methods
     @ReactMethod
     public void tutorialComplete (String id, String userIdType) {
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.measureEvent(TuneEvent.TUTORIAL_COMPLETE);
     }

     // invite Methods
     @ReactMethod
     public void invite (String id, String userIdType) {
         Tune tune = Tune.getInstance();

         this.setUserId(tune,userIdType, id);
         tune.measureEvent(TuneEvent.INVITE);
     }

     // rated Method
     @ReactMethod
     public void rated (String id, String userIdType, Float rating, String contentId) {
         Tune tune = Tune.getInstance();

         this.setUserId(tune, userIdType, id);
         tune.measureEvent(new TuneEvent(TuneEvent.RATED)
                 .withRating(rating)
                 .withContentId(contentId));
     }

     // Share Methods
     @ReactMethod
     public void share (String id, String userIdType) {
         Tune tune = Tune.getInstance();
         this.setUserId(tune, userIdType, id);
         tune.measureEvent(TuneEvent.SHARE);
     }

     //
     @ReactMethod
     public void registerCustomProfileString(String name, String value) {
         Tune tune = Tune.getInstance();
         tune.registerCustomProfileString(name, value);
     }

     // empty method, is only supported for iOS, but, is part of the bridge pacakge for iOS, so just incase a
     // user uses this method, have a blank method
     @ReactMethod
     public void registerCustomProfileBoolean(String name, Boolean value) {}

     @ReactMethod
     public void registerCustomProfileDate(String name, ReadableMap value) {
         Tune tune = Tune.getInstance();
         Date date = this.getDate(value);
         tune.registerCustomProfileDate(name, date);
     }

     @ReactMethod
     public void registerCustomProfileNumber(String name, Integer value) {
         Tune tune = Tune.getInstance();
         tune.registerCustomProfileNumber(name, value);
     }

     @ReactMethod
     public void registerCustomProfileGeolocation (String name, ReadableMap value) {
         Tune tune = Tune.getInstance();
         TuneLocation location = this.getTuneLocation(value);
         tune.registerCustomProfileGeolocation(name, location);
     }

     @ReactMethod
     public void setCustomProfileStringValue(String name, String value) {
         Tune tune = Tune.getInstance();
         tune.setCustomProfileStringValue(name, value);
     }

     // empty method, is only supported for iOS, but, is part of the bridge pacakge for iOS, so just incase a
     // user uses this method, have a blank method
     @ReactMethod
     public void setCustomProfileBooleanValue(String name, Boolean value) {

     }

     @ReactMethod
     public void setCustomProfileDate(String name, ReadableMap value) {
         Tune tune = Tune.getInstance();
         Date date = this.getDate(value);
         tune.setCustomProfileDate(name, date);
     }

     @ReactMethod
     public void setCustomProfileNumber(String name, Integer value) {
         Tune tune = Tune.getInstance();
         tune.setCustomProfileNumber(name, value);
     }

     @ReactMethod
     public void setCustomProfileGeolocation(String name, ReadableMap value) {
         Tune tune = Tune.getInstance();
         TuneLocation location = this.getTuneLocation(value);
         tune.setCustomProfileGeolocation(name, location);
     }

     //
     @ReactMethod
     public void clearCustomProfileVariable( String name) {
         Tune tune = Tune.getInstance();
         tune.clearCustomProfileVariable(name);
     }

     //
     @ReactMethod
     public void clearAllCustomProfileVariables() {
         Tune tune = Tune.getInstance();
         tune.clearAllCustomProfileVariables();
     }

     // POWER HOOK METHODS
     @ReactMethod
     public void getPowerHookValues( ReadableArray hookIds, Promise promise) {
         System.out.println(" TuneSDKBridge.getPowerHookValues");
         Tune tune = Tune.getInstance();
         WritableMap hookValues = Arguments.createMap();

         try {

             for (int i = 0; i < hookIds.size(); i++) {
                 System.out.println("TuneSDKBridge.getPowerHookValues: " + hookIds.getString(i));
                 hookValues.putString(hookIds.getString(i), tune.getValueForHookById(hookIds.getString(i) ) );
             }

             promise.resolve(hookValues);

         } catch (Exception e) {
             System.out.println( e );
         }
     }

     //


     // PRIVATE HELPER METHODS
     private void setUserId(Tune tune, String userIdType, String id ) {
         switch( userIdType ) {
             case "facebook" : tune.setFacebookUserId(id);break;
             case "google"   : tune.setGoogleUserId(id); break;
             case "twitter"  : tune.setFacebookUserId(id);break;
             default : tune.setUserId(id); break;
         }
     }

     private List<TuneEventItem> getTuneEventItemList(ReadableArray tuneEventArray) {
         List<TuneEventItem> tuneEventItemList = new ArrayList<>(tuneEventArray.size());

         for (int i = 0; i < tuneEventArray.size(); i++) {
             ReadableMap  event = tuneEventArray.getMap(i);
             tuneEventItemList.add( this.getTuneEventItem(event));
         }

         return tuneEventItemList;
     }

     private TuneEventItem getTuneEventItem(ReadableMap eventMap ) {
         return new TuneEventItem(eventMap.getString("itemName"))
                 .withUnitPrice(eventMap.getDouble("unitPrice"))
                 .withQuantity(eventMap.getInt("quantity"))
                 .withRevenue(eventMap.getDouble("revenue"))
                 .withAttribute1(eventMap.getString("attribute1"))
                 .withAttribute2(eventMap.getString("attribute2"))
                 .withAttribute3(eventMap.getString("attribute3"))
                 .withAttribute4(eventMap.getString("attribute4"))
                 .withAttribute5(eventMap.getString("attribute5"));
     }

     private Location getLocation ( ReadableMap loc ) {
         Location location = (loc.hasKey("description")) ? new Location(loc.getString("description")) : new Location("");

         if(loc.hasKey("longitude")) {
             location.setLongitude(loc.getDouble("longitude"));
         }

         if(loc.hasKey("latitude")) {
             location.setLatitude(loc.getDouble("latitude"));
         }

         return location;
     }

     private TuneLocation getTuneLocation (ReadableMap loc) {
         return new TuneLocation(this.getLocation(loc));
     }

     private Date getDate(ReadableMap date) {

         if(date.hasKey("year") && date.hasKey("month") && date.hasKey("day")) {
             return new GregorianCalendar(date.getInt("year"), date.getInt("month"), date.getInt("day")).getTime();
         } else {
             return null;
         }
     }
 }
