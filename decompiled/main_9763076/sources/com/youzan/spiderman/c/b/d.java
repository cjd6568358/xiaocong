package com.youzan.spiderman.c.b;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: compiled from: HtmlConfig.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {

    @SerializedName("enable_html_cache")
    private boolean a = true;

    @SerializedName("sync_html_interval")
    private long b = 7200000;

    @SerializedName("html_download_condition")
    private String c = "wifi";

    @SerializedName("local_html_load_valid")
    private long d = 43200000;

    @SerializedName("cache_html_url")
    private List<String> e;

    public boolean a() {
        return this.a;
    }

    public void a(boolean enableHtmlCache) {
        this.a = enableHtmlCache;
    }

    public long b() {
        return this.b;
    }

    public void a(long syncHtmlInterval) {
        this.b = syncHtmlInterval;
    }

    public String c() {
        return this.c;
    }

    public void a(String htmlDownloadCondition) {
        this.c = htmlDownloadCondition;
    }

    public long d() {
        return this.d;
    }

    public void b(long localHtmlLoadValid) {
        this.d = localHtmlLoadValid;
    }

    public List<String> e() {
        return this.e;
    }

    public void a(List<String> cacheHtmlUrl) {
        this.e = cacheHtmlUrl;
    }
}
