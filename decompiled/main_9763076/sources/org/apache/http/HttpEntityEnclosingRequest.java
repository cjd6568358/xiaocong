package org.apache.http;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HttpEntityEnclosingRequest extends HttpRequest {
    boolean expectContinue();

    HttpEntity getEntity();

    void setEntity(HttpEntity httpEntity);
}
