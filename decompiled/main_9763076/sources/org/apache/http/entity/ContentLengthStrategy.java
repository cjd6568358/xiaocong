package org.apache.http.entity;

import org.apache.http.HttpException;
import org.apache.http.HttpMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface ContentLengthStrategy {
    public static final int CHUNKED = -2;
    public static final int IDENTITY = -1;

    long determineLength(HttpMessage httpMessage) throws HttpException;
}
