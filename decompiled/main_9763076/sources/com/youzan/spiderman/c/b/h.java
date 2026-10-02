package com.youzan.spiderman.c.b;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: compiled from: UploadConfig.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {

    @SerializedName("enable_upload")
    private boolean a;

    @SerializedName("url_pattern")
    private List<String> b;

    public boolean a() {
        return this.a;
    }

    public void a(boolean enableUpload) {
        this.a = enableUpload;
    }

    public List<String> b() {
        return this.b;
    }

    public void a(List<String> urlPattern) {
        this.b = urlPattern;
    }
}
