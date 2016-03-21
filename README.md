TuneSDKBridge ![npm version](https://img.shields.io/npm/v/react-native-tune-sdk.svg)
=============
**Tune SDK Bridge** is built to provide an easy interface to the native Tune native mobile app tracking libraries on both **iOS** and **Android**.

## Why a native bridge? Why not use just JavaScript?
The key difference with the native bridge is that you get a lot of the metadata handled automatically by the Google Analytics native library. This will include the device UUID, device model, viewport size, OS version etc.

You will only have to send in a few parameteres when tracking, e.g:
```javascript
const MobileAppTracking = require('react-native-tune-sdk');

MobileAppTracking.trackScreenView('Home');
MobileAppTracking.trackEvent('testcategory', 'testaction');
```

## Installation with rnpm
1. `npm install --save react-native-tune-sdk`
2. `rnpm link react-native-tune-sdk`

With this, [rnpm](https://github.com/rnpm/rnpm) will do most of the heavy lifting for linking, **but** you will still need to do some of the manual steps below.

These are step 5 and 6 from the iOS installation, and step 4 from the Android installation. Specifically for Android step 4, you'll have to add the tracking id.

## Manual installation iOS

1. `npm install --save react-native-tune-sdk`
2. In XCode, right-click the Libraries folder under your project ➜ `Add Files to <your project>`.
3. Go to `node_modules` ➜ `react-native-tune-sdk` ➜ `ios` ➜ `RNTuneSDKBridge` and add the `RNTuneSDKBridge.xcodeproj` file.
4. Add libRNTuneSDKBridge.a from the linked project to your project properties ➜ "Build Phases" ➜ "Link Binary With Libraries"
5. Next you will have to link a few more SDK framework/libraries which are required by GA (if you do not already have them linked.) Under the same "Link Binary With Libraries", click the + and add the following:
  1. AdSupport.framework
  2. CoreTelephony.framework
  3. iAd.framework
  4. libz.tbd
  5. MobileCoreServices.framework
  6. Security.framework
  7. StoreKit.framework
  8. SystemConfiguration.framework  

        [Tune initializeWithTuneAdvertiserId:[[NSBundle mainBundle] objectForInfoDictionaryKey:@"TUNEadvertiserId"]
                           tuneConversionKey:[[NSBundle mainBundle] objectForInfoDictionaryKey:@"TUNEconversionKey"]];

6. Under your project properties ➜ "Info", add a new line with the following for you Tune SDK Keys:
  1. Key: TUNE advertiser Id TUNEadvertiserId - 
  2. Type: String
  3. Value: UA-12345-1 (in other words, your own tracking id).
  4. Key: TUNE conversion Key.
  5.
7. **Optional step**: If you plan on using the advertising identifier (IDFA), then you need to do two things:
  1. Add AdSupport.framework under "Link Binary With Libraries". (As with the other frameworks in step 5).
  2. Go to Xcode ➜ `Libraries` ➜ `RNTuneSDKBridge.xcodeproj` ➜ right-click `Tune.framework`. Here you need to `Add files to ..`, and add `libAdIdAccess.a` from the `google-analytics-lib` directory. This directory is located in the same `node_modules` path as in step 3.

## Prerequisites for Android
Make sure you have the following SDK packages installed in the Android SDK Manager:
  * Google Repository
  * Google Play services
  * Google APIs (Atom) system image

Consult [this guide](https://developer.android.com/sdk/installing/adding-packages.html) if you are unsure how to do this. Specifically step 3 for the mentioned packages.

## Manual installation Android

1. `npm install --save react-native-tune-sdk`
2. Add the following in `android/setting.gradle`

  ```gradle
  ...
  include ':TuneSDKBridge', ':app'
  project(':TuneSDKBridge').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-tune-sdk/android')
  ```

3. And the following in `android/app/build.gradle`

  ```gradle
  ...
  dependencies {
      ...
      compile project(':TuneSDKBridge')
  }
  ```

4. Register package in `MainActivity.java`

  ```java
  // Step 1; import package:
  import com.tune.react.TuneSDKBridge.TuneSDKBridgePackage;

  public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
      ...

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new TuneSDKBridgePackage(this, "325311215424", "173018", "156b8c08da54005909bcb292bc457013"),
        );
    }
      ...
  ```

## Javascript API

### trackScreenView(screenName)

* **screenName (required):** String, name of current screen

**Important**: Calling this will also set the "current view" for other calls. So events tracked will be tagged as having occured on the current view, `Home` in this example. This means it is important to track navigation, especially if events can fire on different views.

See the [Google Analytics docs](https://developers.google.com/analytics/devguides/collection/ios/v3/screens) for more info

```javascript
MobileAppTracking.trackScreenView('Home')
```

### trackEvent(category, action, optionalValues)

* **category (required):** String, category of event
* **action (required):** String, name of action
* **optionalValues:** Object
  * **label:** String
  * **value:** Number