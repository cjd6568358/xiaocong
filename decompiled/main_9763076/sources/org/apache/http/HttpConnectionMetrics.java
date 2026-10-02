package org.apache.http;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HttpConnectionMetrics {
    Object getMetric(String str);

    long getReceivedBytesCount();

    long getRequestCount();

    long getResponseCount();

    long getSentBytesCount();

    void reset();
}
