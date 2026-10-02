package com.alibaba.sdk.android.httpdns;

import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f {
    private int b;
    private String c;

    f(int i, String str) {
        this.b = i;
        this.c = new JSONObject(str).getString("code");
    }

    public String a() {
        return this.c;
    }

    public int getErrorCode() {
        return this.b;
    }
}
