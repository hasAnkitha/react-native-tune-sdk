package com.tune.react.TuneSDKBridge;

import android.app.Activity;

import java.util.List;
import java.util.Arrays;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;

public class TuneSDKBridgePackage implements ReactPackage {

    private static Boolean debugMode;
    private static Activity activity;
    private static String tuneSenderId;
    private static String tuneAdvertiserId;
    private static String tuneConversionKey;

    public TuneSDKBridgePackage(Activity activity,String tuneSenderId, String tuneAdvertiserId,String tuneConversionKey, Boolean debugMode) {
        this.activity = activity;
        this.debugMode = debugMode;
        this.tuneSenderId = tuneSenderId;
        this.tuneAdvertiserId = tuneAdvertiserId;
        this.tuneConversionKey = tuneConversionKey;

        System.out.println("TuneSDKBridgePackage constructor");
    }
    // JUST IN CASE THEY DO NOT PASS IN A DEBUG VALUE AND PASS IN A SENDER ID, ADD ANOTHER CONSTRUCTOR
    public TuneSDKBridgePackage(Activity activity, String tuneAdvertiserId,String tuneConversionKey, String tuneSenderId) {
        this(activity, tuneAdvertiserId, tuneConversionKey, tuneSenderId, false);
    }

    // JUST IN CASE THEY DO NOT PASS IN A SENDER  ID AND PASS IN A DEBUG VALUE
    public TuneSDKBridgePackage(Activity activity, String tuneAdvertiserId,String tuneConversionKey, Boolean debugMode) {
        this(activity,tuneAdvertiserId,tuneConversionKey, "", debugMode);
    }

    // CONSTRUCTOR for optional Sender Id and debug mode
    public TuneSDKBridgePackage(Activity activity, String tuneAdvertiserId,String tuneConversionKey) {
        this(activity,tuneAdvertiserId,tuneConversionKey, "", false);
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new TuneSDKBridge(reactContext, activity, tuneAdvertiserId, tuneConversionKey,tuneSenderId, debugMode));
        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList();
    }
}
