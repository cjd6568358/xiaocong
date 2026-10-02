package com.tencent.mid.a;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c extends DefaultConnectionKeepAliveStrategy {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy, org.apache.http.conn.ConnectionKeepAliveStrategy
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAliveDuration = super.getKeepAliveDuration(httpResponse, httpContext);
        if (keepAliveDuration == -1) {
            return 20000L;
        }
        return keepAliveDuration;
    }
}
