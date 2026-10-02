package com.youzan.spiderman.c.d;

import com.google.gson.annotations.SerializedName;
import com.youzan.spiderman.c.b.e;
import com.youzan.spiderman.utils.JsonUtil;

/* JADX INFO: compiled from: SyncResponse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {

    @SerializedName("error_response")
    private b a;

    @SerializedName("response")
    private e b;

    public b a() {
        return this.a;
    }

    public e b() {
        return this.b;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }
}
