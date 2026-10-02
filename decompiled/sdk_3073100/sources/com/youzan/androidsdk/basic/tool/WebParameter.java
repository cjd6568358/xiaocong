package com.youzan.androidsdk.basic.tool;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.youzan.androidsdk.YouzanLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class WebParameter {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private static final String[] f18 = {"tenpay.com", "alipay.com", "qq.com"};

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final String f19 = "redirect_uri";

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final String f20 = "koudaitong.com";

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private static final String f21 = "youzan.com";

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private static final String f22 = "kdt.im";

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private static final String f23 = "database";

    @SuppressLint({"SetJavaScriptEnabled"})
    public static void initWebViewParameter(WebView webView) {
        if (webView != null) {
            webView.setOverScrollMode(2);
            try {
                Context context = webView.getContext();
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setCacheMode(-1);
                if (Build.VERSION.SDK_INT >= 14) {
                    settings.setTextZoom(100);
                }
                settings.setSavePassword(false);
                settings.setSaveFormData(false);
                settings.setDomStorageEnabled(true);
                settings.setDatabaseEnabled(true);
                if (Build.VERSION.SDK_INT < 19) {
                    String path = context.getApplicationContext().getDir(f23, 0).getPath();
                    settings.setDatabasePath(path);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    settings.setMixedContentMode(0);
                }
                settings.setGeolocationEnabled(true);
                settings.setGeolocationDatabasePath(context.getFilesDir().getPath());
            } catch (Throwable e) {
                YouzanLog.w("WARNING: init WebView Failed");
                e.printStackTrace();
            }
        }
    }

    public static void blockDangerJsInterface(WebView web) {
        if (web != null) {
            web.removeJavascriptInterface("searchBoxJavaBridge_");
            web.removeJavascriptInterface("accessibility");
            web.removeJavascriptInterface("accessibilityTraversal");
        }
    }

    @TargetApi(19)
    public static void webOpenDebug(boolean debug) {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(debug);
        }
    }

    public static void webViewUAConfiguration(WebView webView, String UA, String appVersion) {
        if (!TextUtils.isEmpty(UA) && webView != null) {
            if (appVersion == null) {
                appVersion = "";
            }
            WebSettings settings = webView.getSettings();
            settings.setUserAgentString(settings.getUserAgentString() + " " + UA + " " + appVersion);
            return;
        }
        YouzanLog.w("UserAgent Is Null");
    }

    public static String getPreviousUrl(WebView webView) {
        WebHistoryItem item;
        if (webView == null) {
            return null;
        }
        WebBackForwardList list = webView.copyBackForwardList();
        int curIndex = list.getCurrentIndex();
        int preIndex = curIndex > 0 ? curIndex - 1 : -1;
        if (preIndex < 0 || (item = list.getItemAtIndex(preIndex)) == null) {
            return null;
        }
        return item.getUrl();
    }

    public static boolean validPreviousUrl(WebView webView) {
        WebHistoryItem item;
        if (webView == null) {
            return false;
        }
        WebBackForwardList list = webView.copyBackForwardList();
        int curIndex = list.getCurrentIndex();
        int preIndex = curIndex > 0 ? curIndex - 1 : -1;
        if (preIndex < 0 || (item = list.getItemAtIndex(preIndex)) == null) {
            return false;
        }
        return (shouldSkipUrl(item.getUrl()) && preIndex == 0) ? false : true;
    }

    public static boolean shouldSkipUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        return !TextUtils.isEmpty(getKdtUnionUrl(uri)) || TextUtils.isEmpty(host) || isBlockHost(host);
    }

    public static String getKdtUnionUrl(Uri uri) {
        if (uri.isOpaque()) {
            return null;
        }
        return uri.getQueryParameter(f19);
    }

    public static boolean isBlockHost(String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        for (String item : f18) {
            if (host.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isYouzanPage(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        return isYouzanHost(uri.getHost());
    }

    public static boolean isYouzanHost(String host) {
        return !TextUtils.isEmpty(host) && (host.contains(f21) || host.contains(f20) || host.contains(f22));
    }
}
