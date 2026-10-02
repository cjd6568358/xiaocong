package com.youzan.spiderman.c.b;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: compiled from: ConfigContent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {

    @SerializedName("resource_config")
    private f a;

    @SerializedName("sync_config")
    private g b;

    @SerializedName("upload_config")
    private h c;

    @SerializedName("html_config")
    private d d;

    public f a() {
        return this.a;
    }

    public void a(f resourceConfig) {
        this.a = resourceConfig;
    }

    public g b() {
        return this.b;
    }

    public void a(g syncConfig) {
        this.b = syncConfig;
    }

    public h c() {
        return this.c;
    }

    public void a(h uploadConfig) {
        this.c = uploadConfig;
    }

    public d d() {
        return this.d;
    }

    public void a(d htmlConfig) {
        this.d = htmlConfig;
    }
}
