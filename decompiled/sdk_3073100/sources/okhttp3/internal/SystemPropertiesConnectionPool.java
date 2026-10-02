package okhttp3.internal;

import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class SystemPropertiesConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    public static final ConnectionPool INSTANCE;

    static {
        int maxIdleConnections;
        String keepAlive = System.getProperty("http.keepAlive");
        if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
            maxIdleConnections = 0;
        } else {
            String maxIdleConnectionsString = System.getProperty("http.maxConnections");
            if (maxIdleConnectionsString != null) {
                maxIdleConnections = Integer.parseInt(maxIdleConnectionsString);
            } else {
                maxIdleConnections = 5;
            }
        }
        String keepAliveDurationString = System.getProperty("http.keepAliveDuration");
        long keepAliveDurationMs = keepAliveDurationString != null ? Long.parseLong(keepAliveDurationString) : DEFAULT_KEEP_ALIVE_DURATION_MS;
        INSTANCE = new ConnectionPool(maxIdleConnections, keepAliveDurationMs, TimeUnit.MILLISECONDS);
    }

    private SystemPropertiesConnectionPool() {
    }
}
