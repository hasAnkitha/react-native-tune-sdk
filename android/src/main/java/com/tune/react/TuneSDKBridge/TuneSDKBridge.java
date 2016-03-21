package com.tune.react.TuneSDKBridge;

import java.lang.System;
import android.app.Activity;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class TuneSDKBridge extends ReactContextBaseJavaModule {

    private static TuneSDKApplication tuneApplicationInstance;

    public TuneSDKBridge(ReactApplicationContext reactContext, Activity activity, String tuneAdvertiserId,String tuneConversionKey, String tuneSenderId, Boolean debugMode) {
        super(reactContext);
        System.out.println("TuneSDKBridge constructor");
        tuneApplicationInstance = new TuneSDKApplication(reactContext,activity, tuneAdvertiserId, tuneConversionKey, tuneSenderId, debugMode);
    }

    @Override
    public String getName() {
        return "TuneSDKBridge";
    }

    @ReactMethod
    public void login(String id,String userIdType,String email, String name, Integer age, String gender, ReadableMap location) {
        System.out.println(" TuneSDKApplication.login");
        tuneApplicationInstance.login(id, userIdType, email, name, age, gender, location);
    }

    @ReactMethod
    public void registration(String id,String userIdType, String email, String name, Integer age, String gender, ReadableMap location) {
        System.out.println(" TuneSDKApplication.registration");
        tuneApplicationInstance.registration(id, userIdType, email, name, age, gender, location);
    }

    // eCommerce
    @ReactMethod
    public void addToCart(String id,String userIdType, String gender, Integer age, Float revenue, String currencyCode,ReadableMap location, ReadableArray eventItems) {
        System.out.println(" TuneSDKApplication.addToCart");
        tuneApplicationInstance.addToCart(id, userIdType, gender, age, revenue, currencyCode,location, eventItems);
    }

    //
    @ReactMethod
    public void addToWishlist(String id,String userIdType, String currencyCode, ReadableMap location, ReadableArray eventItems) {
        System.out.println(" TuneSDKApplication.addToWishlist");
        addToWishlist( id, userIdType, currencyCode, location, eventItems);
    }

    //
    @ReactMethod
    public void addedPaymentInfo(String id,String userIdType) {
        System.out.println(" TuneSDKApplication.addedPaymentInfo");
        addedPaymentInfo(id, userIdType);
    }

    //
    @ReactMethod
    public void checkoutInitiated(String id,String userIdType, Integer revenue, String currencyCode, String advertiserRefId, ReadableMap location, ReadableArray eventItems) {
        System.out.println(" TuneSDKApplication.checkoutInitiated");
        checkoutInitiated( id, userIdType, revenue, currencyCode, advertiserRefId,location, eventItems);
    }

    //
    @ReactMethod
    public void purchase(String id,String userIdType,String currencyCode,Float revenue, String advertiserRefId, ReadableMap location, ReadableArray eventItems) {
        System.out.println(" TuneSDKApplication.purchase");
        purchase(id, userIdType, currencyCode, revenue, advertiserRefId, location, eventItems);
    }

    //
    @ReactMethod
    public void reservation(String id,String userIdType, String gender, Integer age, Float revenue, Integer quantity, String currencyCode, String advertiserRefId, ReadableMap location, ReadableMap date1, ReadableMap date2) {
        System.out.println(" TuneSDKApplication.reservation");
        reservation (id, userIdType, gender, age, revenue, quantity, currencyCode, advertiserRefId,location, date1, date2);
    }

    //
    @ReactMethod
    public void search (String id,String userIdType, String currencyCode, String searchString, Integer quantity, ReadableMap location, ReadableMap date1, ReadableMap date2, ReadableArray eventItems) {
        System.out.println(" TuneSDKApplication.search");
        tuneApplicationInstance.search (id, userIdType, currencyCode, searchString, quantity, location, date1, date2, eventItems);
    }

    //
    @ReactMethod
    public void contentView (String id, String userIdType, String currencyCode, ReadableMap location, ReadableArray eventItem) {
        System.out.println(" TuneSDKApplication.contentView");
        tuneApplicationInstance.contentView(id, userIdType, currencyCode, location, eventItem);
    }

    //
    @ReactMethod
    public void achievementUnlocked (String id, String userIdType, String contentId) {
        System.out.println(" TuneSDKApplication.achievementUnlocked");
        tuneApplicationInstance.achievementUnlocked(id, userIdType, contentId);
    }

    //
    @ReactMethod
    public void levelAchieved (String id, String userIdType, Integer level) {
        System.out.println(" TuneSDKApplication.levelAchieved");
        tuneApplicationInstance.levelAchieved(id, userIdType, level);
    }

    //
    @ReactMethod
    public void spentCredits (String id, String userIdType, Integer credits) {
        System.out.println(" TuneSDKApplication.spentCredits");
        tuneApplicationInstance.spentCredits(id, userIdType, credits);
    }

    // tutorial complete Methods
    @ReactMethod
    public void tutorialComplete (String id, String userIdType) {
        System.out.println(" TuneSDKApplication.tutorialComplete");
        tuneApplicationInstance.tutorialComplete(id, userIdType);
    }

    // invite Methods
    @ReactMethod
    public void invite (String id, String userIdType) {
        System.out.println(" TuneSDKApplication.invite");
        tuneApplicationInstance.invite(id, userIdType);
    }

    // rated Method
    @ReactMethod
    public void rated (String id, String userIdType,Float rating, String contentId) {
        System.out.println(" TuneSDKApplication.rated");
        tuneApplicationInstance.rated(id, userIdType, rating, contentId);
    }

    // Share Methods
    @ReactMethod
    public void share (String id, String userIdType) {
        System.out.println(" TuneSDKApplication.share");
        tuneApplicationInstance.share(id, userIdType);
    }
}
