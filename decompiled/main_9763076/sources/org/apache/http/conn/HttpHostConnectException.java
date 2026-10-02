package org.apache.http.conn;

import java.net.ConnectException;
import org.apache.http.HttpHost;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class HttpHostConnectException extends ConnectException {
    public HttpHostConnectException(HttpHost host, ConnectException cause) {
        throw new RuntimeException("Stub!");
    }

    public HttpHost getHost() {
        throw new RuntimeException("Stub!");
    }
}
