package com.xiaocong.smarthome.network.httplib;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class RetryHandler implements HttpRequestRetryHandler {
    private final int maxRetries;
    private final int retrySleepTimeMS;
    private static final HashSet<Class<?>> exceptionWhitelist = new HashSet<>();
    private static final HashSet<Class<?>> exceptionBlacklist = new HashSet<>();

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLException.class);
    }

    public RetryHandler(int maxRetries, int retrySleepTimeMS) {
        this.maxRetries = maxRetries;
        this.retrySleepTimeMS = retrySleepTimeMS;
    }

    @Override // org.apache.http.client.HttpRequestRetryHandler
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean retry = true;
        Boolean b = (Boolean) context.getAttribute("http.request_sent");
        boolean sent = b != null && b.booleanValue();
        if (executionCount > this.maxRetries) {
            retry = false;
        } else if (isInList(exceptionWhitelist, exception)) {
            retry = true;
        } else if (isInList(exceptionBlacklist, exception)) {
            retry = false;
        } else if (!sent) {
            retry = true;
        }
        if (retry) {
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute("http.request");
            if (currentReq == null) {
                return false;
            }
        }
        if (retry) {
            SystemClock.sleep(this.retrySleepTimeMS);
        } else {
            exception.printStackTrace();
        }
        return retry;
    }

    static void addClassToWhitelist(Class<?> cls) {
        exceptionWhitelist.add(cls);
    }

    static void addClassToBlacklist(Class<?> cls) {
        exceptionBlacklist.add(cls);
    }

    protected boolean isInList(HashSet<Class<?>> list, Throwable error) {
        for (Class<?> aList : list) {
            if (aList.isInstance(error)) {
                return true;
            }
        }
        return false;
    }
}
