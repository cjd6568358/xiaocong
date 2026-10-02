package com.youzan.spiderman.html;

/* JADX INFO: compiled from: HtmlDataPool.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class j {
    private k a;

    /* JADX INFO: compiled from: HtmlDataPool.java */
    private static class a {
        static j a = new j();
    }

    public static j a() {
        return a.a;
    }

    private j() {
        c();
    }

    private void c() {
        this.a = (k) com.youzan.spiderman.cache.d.a(k.class, "html_data");
    }

    public void b() {
        com.youzan.spiderman.cache.d.a(this.a, "html_data");
    }

    public i a(String hash) {
        return this.a.a(hash);
    }

    public void a(i data) {
        this.a.a(data.b(), data);
    }

    public i b(String hash) {
        return this.a.b(hash);
    }
}
