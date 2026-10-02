package com.youzan.systemweb;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class StorageManager {
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
