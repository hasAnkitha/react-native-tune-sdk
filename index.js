/*
 *  TUNE SDK v4.* REACT NATIVE BRIDGE
 *
 *
 *
 * */

"use strict";

const TuneSDKBridge = require("react-native").NativeModules.TuneSDKBridge;

class MobileAppTracking {

  /**
   * Track an share event
   * @param  {String} id           The User Id
   * @param  {String} type         The User Id type : facebook, twitter, google or user
   * @param  {String} email        The user email address
   * @param  {String} name         The user name
   * @param  {Int}    age          The user age
   * @param  {String} gender       The user gender, MALE or FEMALE
   * @param  {String} location     The address of the location
   */
  static login({id = '', type = '', email = '', name = '', age = 0, gender = 'MALE', location = {}}) {
    TuneSDKBridge.login(id, type, email, name, age, gender, location);
  }


  /**
   * Track an share event
   * @param  {String} id           The User Id
   * @param  {String} type         The User Id type : facebook, twitter, google or user
   * @param  {String} email        The user email address
   * @param  {String} name         The user name
   * @param  {Int}    age          The user age
   * @param  {String} gender       The user gender, MALE or FEMALE
   * @param  {String} location     The address of the location
   */
  static registration({id = '', type = '', email = '', name = '', age = 0, gender = 'MALE', location = {}}) {
    TuneSDKBridge.registration(id, type, email, name, age, gender, location);
  }

  // eCommerce
  static addToCart({id = '', userIdType = '', gender = 'MALE', age = 0, revenue = 0.0, currencyCode = '',location = {}, eventItems = []}) {
    TuneSDKBridge.addToCart(id, userIdType, gender, age, revenue, currencyCode, location, eventItems);
  }

  /**
   * Track an share event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   */
  static addToWishlist({id = '', userIdType = '', currencyCode = '', location = {}, eventItems = []}) {
    TuneSDKBridge.addToWishlist( id, userIdType, currencyCode,location, eventItems);
  }

  /**
   * Add Payment Info
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   */
  static addedPaymentInfo({id = '', userIdType = ''}) {
    TuneSDKBridge.addedPaymentInfo(id, userIdType);
  }

  //
  static checkoutInitiated({id = '', userIdType = '', revenue = 0.0, currencyCode = '', advertiserRefId = '', location ={}, eventItems = []}) {
    TuneSDKBridge.checkoutInitiated( id, userIdType, revenue, currencyCode, advertiserRefId, location, eventItems);
  }

  //
  static purchase({id = '', userIdType = '', currencyCode = '', revenue = 0.0, advertiserRefId = '', location = {}, eventItems = {}}) {
    TuneSDKBridge.purchase(id, userIdType, currencyCode, revenue, advertiserRefId, location, eventItems);
  }

  //
  static reservation({id = '', userIdType = '', gender = '', age = 0, revenue = 0.0, quantity = 0, currencyCode = 'USD', advertiserRefId = '', location = {}, date1 = {}, date2 = {}}) {
    TuneSDKBridge.reservation (id, userIdType, gender, age, revenue, quantity, currencyCode, advertiserRefId, location, date1, date2);
  }

  //
  static search({id = '', userIdType = '', currencyCode = '', searchString = '', quantity = 0, location = {}, date1 = {}, date2 = {}, eventItems = []}) {
    TuneSDKBridge.search (id, userIdType, currencyCode, searchString, quantity, location, date1, date2, eventItems);
  }

  /**
   * Track an share event
   * @param  {String} id           The User Id
   * @param  {String} type         The User Id type : facebook, twitter, google or user
   * @param  {String} currencyCode The currency Code, ex. USD
   * @param  {Object} location     The address of the location
   *    @params {String} description
   *    @params {Float} latitude
   *    @params {Float} longitude
   *
   * @param  {Array[Object]}  eventItems   An array of Event objects item with required keys
   * EXAMPLE
   * @eventItem {
   *    @param  {String} itemName
   *    @param  {Float}  unitPrice
   *    @param  {Int}    quantity
   *    @param  {Float}  revenue
   *    @param  {String} attribute1
   *    @param  {String} attribute2
   *    @param  {String} attribute3
   *    @param  {String} attribute4
   *    @param  {String} attribute5
   * }
   */
  static contentView({id = '', type = '', currencyCode = 'USD', location = {}, eventItems = []}) {
    TuneSDKBridge.contentView(id, type, currencyCode, location, eventItems);
  }

  /**
   * Track an share event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   * @param  {String} contentId The content Id string
   */
  static achievementUnlocked({id = '', type = '', contentId = ''}) {
    TuneSDKBridge.achievementUnlocked(id, type, contentId);
  }

  /**
   * Track an share event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   * @param  {Int}    level    The level that the user acheved
   */
  static levelAchieved({ id = '', type = '', level = 0}) {
    TuneSDKBridge.levelAchieved(id, type, level);
  }


  /**
   * Track an spent credits event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   * @param  {String} credits  The credits for the user
   */
  static spentCredits({id = '', type = '', credits = ''}) {
    TuneSDKBridge.spentCredits(id, type, credits);
  }

  /**
   * Track an tutorial Completed  event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   */
  static tutorialComplete({id = '', type = ''}) {
    TuneSDKBridge.tutorialComplete(id, type);
  }

  /**
   * Track an invite event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   */
  static invite({ id = '', type = ''}) {
    TuneSDKBridge.invite(id, type);
  }

  /**
   * Track an rating event
   * @param  {String} id        The User Id
   * @param  {String} type      The User Id type : facebook, twitter, google or user
   * @param  {Float}  rating    The rating
   * @param  {String} contentId The content Id string
   *
   */
  static rated({id = '', type = '', rating = 0.0, contentId = ''}) {
    TuneSDKBridge.rated(id, type, rating, contentId);
  }

  /**
   * Track an share event
   * @param  {String} id       The User Id
   * @param  {String} type     The User Id type : facebook, twitter, google or user
   */
  static share({id = '', type = ''}) {
    TuneSDKBridge.share(id, type);
  }

}

module.export = MobileAppTracking;