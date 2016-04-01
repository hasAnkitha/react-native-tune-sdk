package com.tune.react.TuneSDKBridge;

import android.app.Activity;
import android.location.Location;

import com.tune.Tune;
import com.tune.TuneEvent;
import com.tune.TuneGender;
import com.tune.TuneEventItem;
import com.tune.ma.application.TuneApplication;
import com.tune.ma.configuration.TuneConfiguration;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;

import java.util.List;
import java.util.Date;
import java.lang.Float;
import java.lang.System;
import java.lang.Integer;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class TuneSDKApplication extends TuneApplication {
    private static Boolean debugMode;
    private static Tune tuneInstance;
    private static String tuneSenderId;
    private static Activity activity;
    private static List<HashMap> powerHooks;
    private static String tuneAdvertiserId;
    private static String tuneConversionKey;
    private static ReactApplicationContext reactContext;
    private static LifecycleEventListener tuneLifecycleEventListener;

    public TuneSDKApplication(ReactApplicationContext reactContext, Activity activity, String tuneAdvertiserId, String tuneConversionKey, String tuneSenderId, List<HashMap> powerHooks, Boolean debugMode) {
        this.activity = activity;
        this.debugMode = debugMode;
        this.powerHooks = powerHooks;
        this.reactContext = reactContext;
        this.tuneSenderId = tuneSenderId;
        this.tuneAdvertiserId = tuneAdvertiserId;
        this.tuneConversionKey = tuneConversionKey;

        System.out.println("TuneSDKApplication constructor");
        initilizeTuneInstanceWithHoods();
    }

    public TuneSDKApplication(ReactApplicationContext reactContext, Activity activity, String tuneAdvertiserId, String tuneConversionKey, String tuneSenderId, Boolean debugMode) {
        this.activity = activity;
        this.debugMode = debugMode;
        this.reactContext = reactContext;
        this.tuneSenderId = tuneSenderId;
        this.tuneAdvertiserId = tuneAdvertiserId;
        this.tuneConversionKey = tuneConversionKey;

        System.out.println("TuneSDKApplication constructor");
        initilizeTuneInstance();
    }

    private void initilizeTuneInstanceWithHoods () {

        System.out.println("TuneSDKApplication.initilizeTuneInstance");

        // Init TUNE SDK with TMA
        tuneInstance = Tune.init( reactContext, tuneAdvertiserId, tuneConversionKey, true);
        tuneInstance.setPushNotificationSenderId(tuneSenderId);

        if( debugMode ) {
            tuneInstance.setDebugMode(true);
        }

        tuneLifecycleEventListener = new LifecycleEventListener() {

            @Override
            public void onHostResume() {
                tuneInstance.setReferralSources(activity);
                tuneInstance.measureSession();
            }

            @Override
            public void onHostPause() {}

            @Override
            public void onHostDestroy() {}
        };

        reactContext.addLifecycleEventListener(tuneLifecycleEventListener);

        this.setPowerHooks();
    }

    private void initilizeTuneInstance () {

        System.out.println("TuneSDKApplication.initilizeTuneInstance");

        // Init TUNE SDK with TMA
        tuneInstance = Tune.init( reactContext, tuneAdvertiserId, tuneConversionKey, true);
        tuneInstance.setPushNotificationSenderId(tuneSenderId);

        if( debugMode ) {
            tuneInstance.setDebugMode(true);
        }

        tuneLifecycleEventListener = new LifecycleEventListener() {

            @Override
            public void onHostResume() {
                tuneInstance.setReferralSources(activity);
                tuneInstance.measureSession();
            }

            @Override
            public void onHostPause() {}

            @Override
            public void onHostDestroy() {}
        };

        reactContext.addLifecycleEventListener(tuneLifecycleEventListener);
    }

    private void setPowerHooks () {

        Tune tune = Tune.getInstance();

        for (int i = 0; i < powerHooks.size(); i++) {
            HashMap<String, String> hook = powerHooks.get(i);
            System.out.println("Tune.registerPowerHook: " + hook.get("hook_id"));
            tune.registerPowerHook(hook.get("hook_id"), hook.get("hook_name"), hook.get("hook_default") );
        }

    }

    public void getPowerHookValues ( ReadableArray hookIds, Promise promise ) {

        Tune tune = Tune.getInstance();
        WritableMap hookValues = Arguments.createMap();

        try {

            for (int i = 0; i < hookIds.size(); i++) {
                System.out.println("TuneSDKApplication.getPowerHookValues: " + hookIds.getString(i));
                hookValues.putString(hookIds.getString(i), tune.getValueForHookById(hookIds.getString(i) ) );
            }

            promise.resolve(hookValues);

        } catch (Exception e) {
            System.out.println( e );
        }
    }

    // Authentication
    public void login (String id,String userIdType,String email, String name, Integer age, String gender, ReadableMap location) {
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

    // Registration
    public void registration (String id,String userIdType, String email, String name, Integer age, String gender, ReadableMap location) {
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
    public void addToCart (String id,String userIdType, String gender, Integer age, Float revenue, String currencyCode, ReadableMap location, ReadableArray eventItems) {
        Tune tune = Tune.getInstance();
        TuneGender tuneGender = ( gender.equals("MALE") ) ? TuneGender.MALE : TuneGender.FEMALE;

        this.setUserId(tune, userIdType, id);

        tune.setAge(35);
        tune.setGender(tuneGender);
        tune.setLocation(this.getLocation(location));
        tune.measureEvent(new TuneEvent(TuneEvent.ADD_TO_CART)
                .withEventItems(this.getTuneEventItemList(eventItems))
                .withRevenue(revenue)
                .withCurrencyCode(currencyCode));
    }

    public void addToWishList (String id,String userIdType, String currencyCode, ReadableMap location, ReadableArray eventItems) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune, userIdType, id);
        tune.setLocation(this.getLocation(location));
        tune.setCurrencyCode(currencyCode);
        tune.measureEvent(new TuneEvent(TuneEvent.ADD_TO_WISHLIST).withEventItems(this.getTuneEventItemList(eventItems)));
    }

    public void addedPaymentInfo (String id,String userIdType) {
        Tune tune = Tune.getInstance();
        this.setUserId(tune, userIdType, id);

        tune.measureEvent(TuneEvent.ADDED_PAYMENT_INFO);
    }

    public void checkoutInitiated (String id,String userIdType, Float revenue, String currencyCode, String advertiserRefId, ReadableMap location, ReadableArray eventItems) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune, userIdType, id);
        tune.setLocation(this.getLocation(location));
        tune.measureEvent(new TuneEvent(TuneEvent.CHECKOUT_INITIATED)
                .withEventItems(this.getTuneEventItemList(eventItems))
                .withRevenue(revenue)
                .withCurrencyCode(currencyCode)
                .withAdvertiserRefId(advertiserRefId));
    }

    public void purchase (String id,String userIdType, Float revenue, String currencyCode, String advertiserRefId, ReadableMap location, ReadableArray eventItems) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune, userIdType, id);
        tune.setLocation(this.getLocation(location));
        tune.measureEvent(new TuneEvent(TuneEvent.PURCHASE)
                .withEventItems(this.getTuneEventItemList(eventItems))
                .withRevenue(revenue)
                .withCurrencyCode(currencyCode)
                .withAdvertiserRefId(advertiserRefId));
    }

    public void reservation (String id,String userIdType, String gender, Integer age, Float revenue, Integer quantity, String currencyCode, String advertiserRefId, ReadableMap location, ReadableMap date1, ReadableMap date2) {
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

    public void search (String id,String userIdType, String currencyCode, String searchString, Integer quantity, ReadableMap location, ReadableMap date1, ReadableMap date2, ReadableArray eventItems) {
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

    // Content
    public void contentView (String id,String userIdType, String currencyCode, ReadableMap location, ReadableArray eventItems) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune, userIdType, id);
        tune.setLocation(this.getLocation(location));
        tune.setCurrencyCode(currencyCode);
        tune.measureEvent(new TuneEvent(TuneEvent.CONTENT_VIEW).withEventItems(this.getTuneEventItemList(eventItems)));
    }

    //
    public void achievementUnlocked (String id,String userIdType, String contentId) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune, userIdType, id);
        tune.measureEvent(new TuneEvent(TuneEvent.ACHIEVEMENT_UNLOCKED).withContentId(contentId));
    }

    //
    public void levelAchieved (String id, String userIdType, Integer level) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune,userIdType, id);
        tune.measureEvent(new TuneEvent(TuneEvent.LEVEL_ACHIEVED).withLevel(level));
    }

    public void spentCredits (String id,String userIdType, Integer credits) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune,userIdType, id);
        tune.measureEvent(new TuneEvent(TuneEvent.SPENT_CREDITS).withQuantity(credits));
    }

    public void tutorialComplete (String id,String userIdType) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune,userIdType, id);
        tune.measureEvent(TuneEvent.TUTORIAL_COMPLETE);
    }

    // Invited Methods
    public void invite (String id,String userIdType) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune,userIdType, id);
        tune.measureEvent(TuneEvent.INVITE);
    }

    // Rated Methods
    public void rated (String id,String userIdType, Float rating, String contentId) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune,userIdType, id);
        tune.measureEvent(new TuneEvent(TuneEvent.RATED)
                .withRating(rating)
                .withContentId(contentId));
    }
    // Share Methods
    public void share (String id, String userIdType) {
        Tune tune = Tune.getInstance();

        this.setUserId(tune,userIdType, id);
        tune.measureEvent(TuneEvent.SHARE);
    }

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

    private Date getDate(ReadableMap date) {

        if(date.hasKey("year") && date.hasKey("month") && date.hasKey("day")) {
            return new GregorianCalendar(date.getInt("year"), date.getInt("month"), date.getInt("day")).getTime();
        } else {
            return null;
        }
    }
}