package org.apache.http.cookie;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface CookieAttributeHandler {
    boolean match(Cookie cookie, CookieOrigin cookieOrigin);

    void parse(SetCookie setCookie, String str) throws MalformedCookieException;

    void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException;
}
