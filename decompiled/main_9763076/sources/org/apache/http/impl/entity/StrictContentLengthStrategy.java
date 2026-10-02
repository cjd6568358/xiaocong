package org.apache.http.impl.entity;

import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.entity.ContentLengthStrategy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class StrictContentLengthStrategy implements ContentLengthStrategy {
    public StrictContentLengthStrategy() {
        throw new RuntimeException("Stub!");
    }

    @Override // org.apache.http.entity.ContentLengthStrategy
    public long determineLength(HttpMessage message) throws HttpException {
        throw new RuntimeException("Stub!");
    }
}
