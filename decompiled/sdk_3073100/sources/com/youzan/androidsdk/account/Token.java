package com.youzan.androidsdk.account;

import android.content.Context;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.tool.Preference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Token {
    private static final String SP_KEY_ACCOUNT_ACCESS_TOKEN = "token.access_token";
    private static final String SP_KEY_ACCOUNT_COOKIE_KEY = "token.cookie_key";
    private static final String SP_KEY_ACCOUNT_COOKIE_VALUE = "token.cookie_value";

    public static void save(YouzanToken token) {
        setAccessToken(token.getAccessToken());
        setCookieKey(token.getCookieKey());
        setCookieValue(token.getCookieValue());
    }

    public static void clear(Context context) {
        Preference.renew(context);
        setAccessToken(null);
        setCookieKey(null);
        setCookieValue(null);
    }

    public static String getAccessToken() {
        return Preference.instance().getString(SP_KEY_ACCOUNT_ACCESS_TOKEN, null);
    }

    public static void setAccessToken(String value) {
        Preference.instance().setString(SP_KEY_ACCOUNT_ACCESS_TOKEN, value);
    }

    public static String getCookieKey() {
        return Preference.instance().getString(SP_KEY_ACCOUNT_COOKIE_KEY, null);
    }

    public static void setCookieKey(String value) {
        Preference.instance().setString(SP_KEY_ACCOUNT_COOKIE_KEY, value);
    }

    public static String getCookieValue() {
        return Preference.instance().getString(SP_KEY_ACCOUNT_COOKIE_VALUE, null);
    }

    public static void setCookieValue(String value) {
        Preference.instance().setString(SP_KEY_ACCOUNT_COOKIE_VALUE, value);
    }
}
