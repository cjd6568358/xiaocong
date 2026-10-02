package com.facebook.react.modules.network;

import java.util.Collections;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactCookieJarContainer implements CookieJarContainer {
    private CookieJar cookieJar = null;

    @Override // com.facebook.react.modules.network.CookieJarContainer
    public void setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    @Override // com.facebook.react.modules.network.CookieJarContainer
    public void removeCookieJar() {
        this.cookieJar = null;
    }

    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (this.cookieJar != null) {
            this.cookieJar.saveFromResponse(url, cookies);
        }
    }

    public List<Cookie> loadForRequest(HttpUrl url) {
        return this.cookieJar != null ? this.cookieJar.loadForRequest(url) : Collections.emptyList();
    }
}
