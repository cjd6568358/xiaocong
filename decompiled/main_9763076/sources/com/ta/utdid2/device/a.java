package com.ta.utdid2.device;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: Device.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private String g = Constants.MAIN_VERSION_TAG;
    private String h = Constants.MAIN_VERSION_TAG;
    private String i = Constants.MAIN_VERSION_TAG;
    private String j = Constants.MAIN_VERSION_TAG;
    private long a = 0;
    private long b = 0;

    void a(long j) {
        this.b = j;
    }

    long a() {
        return this.a;
    }

    void b(long j) {
        this.a = j;
    }

    public String d() {
        return this.g;
    }

    void b(String str) {
        this.g = str;
    }

    public String e() {
        return this.h;
    }

    void c(String str) {
        this.h = str;
    }

    public String getDeviceId() {
        return this.i;
    }

    void d(String str) {
        this.i = str;
    }

    public String f() {
        return this.j;
    }

    void e(String str) {
        this.j = str;
    }
}
