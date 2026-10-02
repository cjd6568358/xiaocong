package org.apache.http.client;

import java.util.Date;
import java.util.List;
import org.apache.http.cookie.Cookie;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface CookieStore {
    void addCookie(Cookie cookie);

    void clear();

    boolean clearExpired(Date date);

    List<Cookie> getCookies();
}
