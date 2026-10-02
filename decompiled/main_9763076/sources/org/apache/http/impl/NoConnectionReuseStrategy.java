package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class NoConnectionReuseStrategy implements ConnectionReuseStrategy {
    public NoConnectionReuseStrategy() {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.ConnectionReuseStrategy
    public boolean keepAlive(HttpResponse response, HttpContext context) {
        throw new RuntimeException("Stub!");
    }
}
