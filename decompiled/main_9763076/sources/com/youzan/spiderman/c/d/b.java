package com.youzan.spiderman.c.d;

import com.google.gson.annotations.SerializedName;
import com.youzan.spiderman.utils.JsonUtil;

/* JADX INFO: compiled from: ErrorResponse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {

    @SerializedName("code")
    private int a;

    public int a() {
        return this.a;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }
}
