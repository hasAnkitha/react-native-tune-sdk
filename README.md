Tune SDK Bridge
=============
**Tune SDK Bridge** is built to provide an easy interface to the native Tune native mobile app tracking libraries on both **iOS** and **Android**.


## Example Usage

```javascript

    const MobileAppTracking = require('react-native-tune-sdk');

    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        email        : 'somebody@somewhere.com',
        name         : 'Some Body', 
        age          : 50, 
        gender       : 'FEMALE', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }
    };
    
    MobileAppTracking.login( params );
    
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

6. Under your project properties ➜ "Info", add a new line with the following for you Tune SDK Keys:
  1. Key: TUNEadvertiserId.
  2. Type: String
  3. Value: Your Tune advertiser id.
  4. Key: TUNEconversionKey.
  5. Type String
  6. Value: Your Tune conversion Key.
  
  
  
## Prerequisites for Android


Make sure you have the following SDK packages installed in the Android SDK Manager:
  * npm install react-native-tune-sdk
  * Add Tune package to the android/setting.gradle file
  * Add Tune package to your android/app/build.gradle
  * Add and initialize Tune SDK in MainActivity.java

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
  import com.tune.react.TuneSDKBridge.TuneSDKBridgePackage; // <!---- 

  public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
      ...

    // React Native 0.16+ and above currently to 0.22
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        // INITIALIZE TRACKING APP WITH YOUR advertiser id, conversion id, and Google api IMA sender id ( optional ) 
        new TuneSDKBridgePackage(this, "your_advertisment_id", "your_conversion_id", "sender_id"),
        );
    }
      ...
    
    // React Native Versions 0.16 < and lower
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        
        mReactInstanceManager = ReactInstanceManager.builder()
            .setApplication(getApplication())
            .setBundleAssetName("index.android.bundle")
            .setJSMainModuleName("index.android")
            .addPackage(new MainReactPackage())
            // INITIALIZE TRACKING APP WITH YOUR advertiser id, conversion id, and Google api IMA sender id ( optional ) 
            .addPackage(new TuneSDKBridgePackage(this, "your_advertisment_id", "your_conversion_id", "sender_id"))
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build();
 
          ...
       }   
       
      
  ```
  
# Javascript API



## Authentication


### login


See the [Tune SDK docs](http://developers.mobileapptracking.com/mobile-sdks-app-events-auth-login/) for more info

Example :

```js

    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        email        : 'somebody@somewhere.com',
        name         : 'Some Body', 
        age          : 50, 
        gender       : 'FEMALE', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }
    };
    
    MobileAppTracking.login( params );
    
```    

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **email (required):** String - user email address
* **name:** String - name of the user
* **age :** Integer - user age
* **gender (required):** String - MALE or FEMALE all upper case.
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location



### registration


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-auth-registration/) for more info

Example :

```js
   
   const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        email        : 'somebody@somewhere.com',
        name         : 'Some Body', 
        age          : 50, 
        gender       : 'FEMALE', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }   
   };
   
   MobileAppTracking.registration( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **email (required):** String - user email address
* **name:** String - name of the user
* **age :** Integer - user age
* **gender (required):** String - MALE or FEMALE all upper case.
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location


##eCommerce



### Add To Cart


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-ecom-add-to-cart/) for more info

Example :

```js
    
    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        gender       : 'MALE', 
        age          : 50, 
        revenue      : 0.0, 
        currencyCode : 'USD', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        eventItems   : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };
    
    MobileAppTracking.addToCart( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **gender (required):** String - MALE or FEMALE all upper case.
* **age :** Integer - user age
* **revenue :** Float - the revenue
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String
       
       
       
### Add To Wishlist


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-ecom-add-to-wishlist/) for more info

Example :

```js
    
    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        currencyCode : 'USD', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        eventItems   : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };
    
    MobileAppTracking.addToWishlist( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String
       
       
       
### Added Payment Info


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-ecom-added-payment-info/) for more info

Example :

```js

    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        currencyCode : 'USD', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        eventItems   : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };
    
    MobileAppTracking.addedPaymentInfo( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String
       


### Checkout Initiated


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-ecom-checkout/) for more info

Example :

```js

    const params = {
        id              : '0001', 
        userIdType      : 'facebook',
        revenue         : 0.0
        currencyCode    : 'USD', 
        advertiserRefId : '00001',
        location        : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        eventItems      : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };

    MobileAppTracking.checkoutInitiated( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **revenue :** Float - the revenue
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **advertiserRefId:** String - advertiser reference Id
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String
       
       
       
### Purchase


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-ecom-purchase/) for more info

Example :

```js
    
    const params = {
        id              : '0001', 
        userIdType      : 'facebook',
        revenue         : 0.0
        currencyCode    : 'USD', 
        advertiserRefId : '00001',
        location        : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        eventItems      : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };
    
    MobileAppTracking.purchase( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **revenue :** Float - the revenue
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **advertiserRefId:** String - advertiser reference Id
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String
       
       
       
### Reservation


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-ecom-reservation/) for more info

Example :

```js

    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        gender       : 'FEMALE',
        age          : 50,
        revenue      : 0.0,
        quantity     : 1,
        currencyCode : 'USD', 
        '00001',
        {day : 1. month : 1, year : 2016},
        {day : 1. month : 1, year : 2016},
        { latitude : '120.999', longitude : '90.000',  description : '' }, 
        [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };

    MobileAppTracking.reservation( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **gender (required):** String - MALE or FEMALE all upper case.
* **age:** Int - user age
* **revenue :** Float - the revenue
* **quantity:** Int 
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **advertiserRefId:** String - advertiser reference Id
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **date1:** Object
  * **day:**   Int 
  * **month:** Int  
  * **year**   Int 
* **date2:** Object
  * **day:**   Int 
  * **month:** Int  
  * **year**   Int   
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String      
       


## Content



### Content View


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-content-view/) for more info

Example :

```js

    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        currencyCode : 'USD', 
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        eventItems   : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };
    
    MobileAppTracking.contentView( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String



### Search


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-content-search/) for more info

Example :

```js

    const params = {
        id           : '0001', 
        userIdType   : 'facebook',
        quantity     : 1,
        currencyCode : 'USD', 
        searchString : 'Searching string is this',
        location     : { latitude : '120.999', longitude : '90.000',  description : '' }, 
        data1        : {day : 1. month : 1, year : 2016},
        date2        : {day : 1. month : 1, year : 2016},
        eventItems   : [{itemName : 'book', unitPrice : 1.00, quantity : 1, revenue : 0.0, attribute1 : '',attribute2 : '',attribute3 : '',attribute4 : '',attribute5 : '' }]    
    };
    
    MobileAppTracking.search( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **quantity:** Int 
* **currencyCode:** String - the currency code of the transaction exp. 'USD'
* **searchString:** String - search string
* **location:** Object
  * **longitude:** Float - latitude
  * **longitude:** Float - longitude 
  * **description** String - description or Street address of the location
* **date1:** Object
  * **day:**   Int 
  * **month:** Int  
  * **year**   Int 
* **date2:** Object
  * **day:**   Int 
  * **month:** Int  
  * **year**   Int   
* **eventItems:** Array of Objects
  * **event:** Object - example object
    * **itemName:** String - item name
    * **unitPrice:** - Float - unit price of item added to cart
    * **quantity:** - Integer - the quantity number of the item added to cart
    * **revenue:** - Float - the revenue 
    * **attribute1:** - String
    * **attribute2:** - String
    * **attribute3:** - String
    * **attribute4:** - String
    * **attribute5:** - String 
       
    
       
## Gaming



### Invite


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-invite/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook'
        contentId  : 'Something the user achieved or id reference to that achievement'
    };

    MobileAppTracking.achievementUnlocked( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **contentId (required):** String - Something the user achieved or id reference to that achievement



### Level Achieved


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-invite/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook',
        level      : '45'
    };

    MobileAppTracking.levelAchieved( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **level (required):** String - level in your game



### Spent Credits


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-invite/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook'
        credits    : '1009009'
    };

    MobileAppTracking.spentCredits( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **credits (required):** String - credits related to your game



### Tutorial Complete


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-invite/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook'
    };

    MobileAppTracking.tutorialComplete( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.



## Sharing  



### Invite


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-invite/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook'
    };

    MobileAppTracking.invite( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.



### Rated


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-rated/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook',
        rating     : 5.0
        contentId  : '000001'
    };

    MobileAppTracking.rated( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.
* **rating :** Float - the revenue
* **contentId :** String - any descriptive information about the item being rated
 
 
 
### Share


See the [Tune SDK docs](https://developers.mobileapptracking.com/mobile-sdks-app-events-social-share/) for more info

Example :

```js
    
    const params = {
        id         : '0001', 
        userIdType : 'facebook'
    };

    MobileAppTracking.share( params );
    
```

* **id (required):** String - id of the customer
* **userIdType (required):** String - the user id type, must be one of these facebook, twitter, google or user if you are using a non-third party id.

