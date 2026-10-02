package org.apache.http.protocol;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class HttpRequestHandlerRegistry implements HttpRequestHandlerResolver {
    public HttpRequestHandlerRegistry() {
        throw new RuntimeException("Stub!");
    }

    public void register(String pattern, HttpRequestHandler handler) {
        throw new RuntimeException("Stub!");
    }

    public void unregister(String pattern) {
        throw new RuntimeException("Stub!");
    }

    public void setHandlers(Map map) {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.protocol.HttpRequestHandlerResolver
    public HttpRequestHandler lookup(String requestURI) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    protected boolean matchUriRequestPattern(String pattern, String requestUri) {
        throw new RuntimeException("Stub!");
    }
}
