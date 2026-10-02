package org.apache.http.client;

import java.io.IOException;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HttpRequestRetryHandler {
    boolean retryRequest(IOException iOException, int i, HttpContext httpContext);
}
