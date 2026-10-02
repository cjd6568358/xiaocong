package com.alibaba.sdk.android.httpdns.probe;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c {
    private String[] a;
    private String hostName;
    private long j;
    private long k;
    private String n;
    private String o;

    c(String str, String[] strArr, String str2, String str3, long j, long j2) {
        this.hostName = str;
        this.a = strArr;
        this.n = str2;
        this.o = str3;
        this.j = j;
        this.k = j2;
    }

    public String[] a() {
        return this.a;
    }

    public long c() {
        return this.j;
    }

    public long d() {
        return this.k;
    }

    public String getHostName() {
        return this.hostName;
    }

    public String h() {
        return this.n;
    }

    public String i() {
        return this.o;
    }
}
