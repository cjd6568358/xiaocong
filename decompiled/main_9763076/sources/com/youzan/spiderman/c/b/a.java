package com.youzan.spiderman.c.b;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: compiled from: Certificate.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {

    @SerializedName("sign_date")
    private long a;

    @SerializedName("vaild_interval")
    private long b;

    public long a() {
        return this.a;
    }

    public void a(long signDate) {
        this.a = signDate;
    }

    public long b() {
        return this.b;
    }

    public void b(long vaildInterval) {
        this.b = vaildInterval;
    }
}
