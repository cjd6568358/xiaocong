package com.huawei.hms.support.api;

import com.huawei.hms.support.api.client.Result;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ResolveResult<T> extends Result {
    private T a;

    public ResolveResult() {
        this.a = null;
    }

    public ResolveResult(T t) {
        this.a = t;
    }

    public T getValue() {
        return this.a;
    }
}
