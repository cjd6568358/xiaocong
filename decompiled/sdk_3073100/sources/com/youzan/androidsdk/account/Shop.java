package com.youzan.androidsdk.account;

import android.content.Context;
import android.text.TextUtils;
import com.youzan.androidsdk.tool.Preference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Shop {
    private static final String SP_KEY_SHOP_CERT_TYPE = "shop.cert_type";
    private static final String SP_KEY_SHOP_LOGO = "shop.logo";
    private static final String SP_KEY_SHOP_NAME = "shop.name";
    private static final String SP_KEY_SHOP_SID = "shop.sid";
    private static final String SP_KEY_SHOP_URL = "shop.url";

    public static String getSid() {
        return Preference.instance().getString(SP_KEY_SHOP_SID, null);
    }

    public static void setSid(String value) {
        Preference.instance().setString(SP_KEY_SHOP_SID, value);
    }

    public static String getShopName() {
        return Preference.instance().getString(SP_KEY_SHOP_NAME, null);
    }

    public static void setShopName(String value) {
        Preference.instance().setString(SP_KEY_SHOP_NAME, value);
    }

    public static String getShopLogo() {
        return Preference.instance().getString(SP_KEY_SHOP_LOGO, null);
    }

    public static void setShopLogo(String value) {
        Preference.instance().setString(SP_KEY_SHOP_LOGO, value);
    }

    public static String getShopUrl() {
        return Preference.instance().getString(SP_KEY_SHOP_URL, null);
    }

    public static void setShopUrl(String value) {
        Preference.instance().setString(SP_KEY_SHOP_URL, value);
    }

    public static int getShopCertType() {
        return Preference.instance().getInt(SP_KEY_SHOP_CERT_TYPE, 0);
    }

    public static void setShopCertType(int value) {
        Preference.instance().setInt(SP_KEY_SHOP_CERT_TYPE, value);
    }

    public static boolean isValid(String sid) {
        return TextUtils.equals(sid, getSid()) && !TextUtils.isEmpty(getShopUrl());
    }

    public static void clear(Context context) {
        Preference.renew(context);
        setSid(null);
        setShopName(null);
        setShopLogo(null);
        setShopUrl(null);
        setShopCertType(Integer.MIN_VALUE);
    }
}
