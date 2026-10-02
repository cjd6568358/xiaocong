package com.youzan.androidsdk.loader.http;

/* JADX INFO: compiled from: BizQuery.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class b<MODEL> extends Query<MODEL> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected final int getHTTPMethod() {
        return 2;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected final int getAuthType() {
        return 2;
    }
}
