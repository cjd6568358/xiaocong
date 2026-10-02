package com.youzan.systemweb;

import android.content.Context;
import com.youzan.spiderman.cache.SpiderCacheCallback;
import com.youzan.spiderman.cache.SpiderMan;
import com.youzan.spiderman.html.HtmlCacheStrategy;
import com.youzan.spiderman.utils.StringUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class YZWebSDK {
    public static void init(Context context, String bizTag, SpiderCacheCallback spiderCacheCallback) {
        if (context == null || StringUtils.isEmpty(bizTag)) {
            throw new IllegalArgumentException("params should be valid when init web sdk");
        }
        SpiderMan.getInstance().init(context, bizTag, spiderCacheCallback);
    }

    public static void syncToken(String token) {
        SpiderMan.getInstance().sync(token);
    }

    public static void setWeakRefCacheCallback(SpiderCacheCallback cacheCallback) {
        SpiderMan.getInstance().setWeakRefCacheCallback(cacheCallback);
    }

    public static void preloadModifyFromRemote(Context context) {
        SpiderMan.getInstance().preloadModifyFromRemote(context);
    }

    public static void preloadCacheFromAsset(Context context, String zipPath) {
        SpiderMan.getInstance().preloadZipFromAsset(context, zipPath);
    }

    public static void setHtmlCacheStrategy(HtmlCacheStrategy strategy) {
        SpiderMan.getInstance().setHtmlCacheStrategy(strategy);
    }

    public static void preloadHtml(Context context, String url) {
        SpiderMan.getInstance().fetchHtml(context, url, null);
    }
}
