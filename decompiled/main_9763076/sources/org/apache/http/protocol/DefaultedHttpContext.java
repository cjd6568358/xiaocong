package org.apache.http.protocol;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public final class DefaultedHttpContext implements HttpContext {
    public DefaultedHttpContext(HttpContext local, HttpContext defaults) {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.protocol.HttpContext
    public Object getAttribute(String id) {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.protocol.HttpContext
    public Object removeAttribute(String id) {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.protocol.HttpContext
    public void setAttribute(String id, Object obj) {
        throw new RuntimeException("Stub!");
    }

    public HttpContext getDefaults() {
        throw new RuntimeException("Stub!");
    }
}
