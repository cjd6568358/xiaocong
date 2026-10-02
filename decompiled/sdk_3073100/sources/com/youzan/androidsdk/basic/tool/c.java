package com.youzan.androidsdk.basic.tool;

import android.webkit.WebView;
import com.youzan.androidsdk.YouzanLog;

/* JADX INFO: compiled from: Javascript.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class c {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final String f56 = "javascript:window.YouzanJSBridge.trigger('share')";

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static void m43(WebView webView) {
        if (webView != null) {
            webView.loadUrl(f56);
        } else {
            YouzanLog.e("WebView Is Null On sharePage");
        }
    }
}
