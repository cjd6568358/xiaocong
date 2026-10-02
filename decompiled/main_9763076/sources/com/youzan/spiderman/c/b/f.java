package com.youzan.spiderman.c.b;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: compiled from: ResourceConfig.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {

    @SerializedName("ignore_resource")
    private List<String> a;

    @SerializedName("enable_cache")
    private boolean b = true;

    @SerializedName("ignore_extension")
    private List<String> c;

    public List<String> a() {
        return this.a;
    }

    public void a(List<String> ignoreResource) {
        this.a = ignoreResource;
    }

    public boolean b() {
        return this.b;
    }

    public void a(boolean enableCache) {
        this.b = enableCache;
    }

    public List<String> c() {
        return this.c;
    }

    public void b(List<String> ignoreExtension) {
        this.c = ignoreExtension;
    }
}
