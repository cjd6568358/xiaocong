package com.youzan.androidsdk;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.youzan.androidsdk.account.Token;
import com.youzan.androidsdk.tool.AnalyticsUtil;
import com.youzan.androidsdk.tool.UserAgent;
import com.youzan.spiderman.cache.SpiderCacheCallback;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class YouzanSDK$1 implements SpiderCacheCallback {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    final /* synthetic */ Context f9;

    YouzanSDK$1(Context context) {
        this.f9 = context;
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public String onTokenNeeded() {
        return Token.getAccessToken();
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public String onTokenInactive(String preToken) {
        String token = Token.getAccessToken();
        if (token == null || token.equals(preToken)) {
            return null;
        }
        return token;
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public void onCustomRequestHeader(String url, Map<String, String> headerMap) {
        try {
            CookieSyncManager.createInstance(this.f9);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String cookie = cookieManager.getCookie(url);
            if (cookie != null) {
                headerMap.put("Cookie", cookie);
            }
        } catch (Throwable throwable) {
            YouzanLog.e("get cookie throw" + throwable);
        }
        String ua = UserAgent.httpUA;
        if (ua != null) {
            headerMap.put("User-Agent", ua);
        }
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public void onStatistic(String name, String desc, Map<String, String> staticData) {
        AnalyticsUtil.doStatistic(this.f9, name, desc, staticData);
    }
}
