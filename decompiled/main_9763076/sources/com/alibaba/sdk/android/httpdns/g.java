package com.alibaba.sdk.android.httpdns;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends Exception {
    private int b;

    public g(int i, String str) {
        super(str);
        this.b = i;
    }

    public int getErrorCode() {
        return this.b;
    }
}
