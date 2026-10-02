package com.youzan.androidsdk.basic;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.youzan.androidsdk.YouzanLog;
import com.youzan.androidsdk.account.Token;
import com.youzan.androidsdk.event.EventAPI;
import com.youzan.androidsdk.tool.AnalyticsUtil;
import com.youzan.androidsdk.tool.UserAgent;
import com.youzan.spiderman.cache.SpiderCacheCallback;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class YouzanBrowser$2 implements SpiderCacheCallback {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    final /* synthetic */ YouzanBrowser f14;

    YouzanBrowser$2(YouzanBrowser this$0) {
        this.f14 = this$0;
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public String onTokenNeeded() {
        String token = Token.getAccessToken();
        if (token == null) {
            YouzanBrowser.ˊ(this.f14).dispatch(this.f14.getContext(), EventAPI.EVENT_AUTHENTICATION, EventAPI.SIGN_NOT_NEED_LOGIN);
            return null;
        }
        return token;
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public String onTokenInactive(String preToken) {
        String token = Token.getAccessToken();
        if (token == null || token.equals(preToken)) {
            YouzanBrowser.ˊ(this.f14).dispatch(this.f14.getContext(), EventAPI.EVENT_AUTHENTICATION, EventAPI.SIGN_NOT_NEED_LOGIN);
            return null;
        }
        return token;
    }

    @Override // com.youzan.spiderman.cache.SpiderCacheCallback
    public void onCustomRequestHeader(String url, Map<String, String> headerMap) {
        try {
            CookieSyncManager.createInstance(this.f14.getContext());
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
        AnalyticsUtil.doStatistic(this.f14.getContext(), name, desc, staticData);
    }
}
