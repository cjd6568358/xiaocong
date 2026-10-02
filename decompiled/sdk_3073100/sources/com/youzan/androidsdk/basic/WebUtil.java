package com.youzan.androidsdk.basic;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.basic.tool.WebParameter;
import com.youzan.androidsdk.basic.tool.a;
import com.youzan.androidsdk.basic.tool.d;
import com.youzan.androidsdk.basic.tool.e;
import com.youzan.androidsdk.tool.Environment;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class WebUtil {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final String f10 = "http";

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final String f11 = "https";

    public static boolean dealWAPWxPay(Activity activity, String url) {
        return !TextUtils.isEmpty(url) && d.m49(activity, Uri.parse(url));
    }

    public static void initWebViewParameter(WebView webView) {
        WebParameter.initWebViewParameter(webView);
    }

    public static void clearCookie(Context context) {
        a.C0016a.m14(context);
    }

    public static void clearLocalStorage() {
        a.C0016a.m8();
    }

    public static boolean isYouzanPage(String url) {
        return WebParameter.isYouzanPage(url);
    }

    public static boolean isYouzanHost(String host) {
        return WebParameter.isYouzanHost(host);
    }

    public static void sync(Context context, YouzanToken token) {
        e.m52(context, token);
    }

    public static boolean isTokenInactive(int errorCode) {
        return errorCode == 40009 || errorCode == 40010 || errorCode == 42000;
    }

    public static int generateRequestId() {
        return Environment.generateRequestId();
    }

    public static void copyText(Context context, String content) {
        Environment.copyText(context, content);
    }

    public static boolean isNetworkConnect(Context context) {
        return Environment.isNetworkConnect(context);
    }

    public static String checkIfAddScheme(String url) {
        Uri uri = Uri.parse(url);
        if (TextUtils.isEmpty(uri.getScheme())) {
            String fixedScheme = isYouzanHost(uri.getHost()) ? f11 : f10;
            Uri.Builder builder = uri.buildUpon();
            builder.scheme(fixedScheme);
            return builder.toString();
        }
        return url;
    }

    public static void setCookie(Context context, String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size() > 0) {
            if (context != null) {
                try {
                    CookieSyncManager.createInstance(context);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return;
                }
            }
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            for (String cookie : cookies) {
                cookieManager.setCookie(url, cookie);
            }
        }
    }
}
