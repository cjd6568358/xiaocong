package org.apache.http.conn;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface ConnectionKeepAliveStrategy {
    long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext);
}
