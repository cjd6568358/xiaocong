package com.youzan.androidsdk;

import android.content.Context;
import com.youzan.spiderman.html.HtmlCacheStrategy;
import com.youzan.systemweb.YZWebSDK;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class YouzanPreloader {
    public static void preloadCacheFromAsset(Context context, String zipPath) {
        YZWebSDK.preloadCacheFromAsset(context, zipPath);
    }

    public static void setHtmlCacheStrategy(HtmlCacheStrategy strategy) {
        YZWebSDK.setHtmlCacheStrategy(strategy);
    }

    public static void preloadHtml(Context context, String url) {
        YZWebSDK.preloadHtml(context, url);
    }
}
