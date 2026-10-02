package com.youzan.spiderman.c.b;

import com.google.gson.annotations.SerializedName;
import com.youzan.spiderman.utils.JsonUtil;
import java.util.List;

/* JADX INFO: compiled from: ModifiedResource.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {

    @SerializedName("global_resource_list")
    private List<String> a;

    @SerializedName("private_resource_list")
    private List<String> b;

    @SerializedName("timestamp")
    private long c;

    @SerializedName("config_last_modify_time")
    private long d;

    public List<String> a() {
        return this.a;
    }

    public List<String> b() {
        return this.b;
    }

    public long c() {
        return this.c;
    }

    public long d() {
        return this.d;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }
}
