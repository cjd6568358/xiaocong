package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public abstract class AbstractCookieSpec implements CookieSpec {
    public AbstractCookieSpec() {
        throw new RuntimeException("Stub!");
    }

    public void registerAttribHandler(String name, CookieAttributeHandler handler) {
        throw new RuntimeException("Stub!");
    }

    protected CookieAttributeHandler findAttribHandler(String name) {
        throw new RuntimeException("Stub!");
    }

    protected CookieAttributeHandler getAttribHandler(String name) {
        throw new RuntimeException("Stub!");
    }

    protected Collection<CookieAttributeHandler> getAttribHandlers() {
        throw new RuntimeException("Stub!");
    }
}
