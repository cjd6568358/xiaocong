package com.huawei.hms.core.aidl;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ResponseHeader implements IMessageEntity {

    @com.huawei.hms.core.aidl.a.a
    protected int statusCode;

    public ResponseHeader() {
    }

    public ResponseHeader(int i) {
        this.statusCode = i;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
