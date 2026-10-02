package com.youzan.spiderman.b;

import com.youzan.spiderman.utils.Logger;
import java.io.File;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: HtmlDataLruCache.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class d extends h<String, Long> {
    private static d c;
    private String a;
    private String b;

    static d a() {
        if (c == null) {
            c = new d(c.d());
        }
        return c;
    }

    private d(long maxSize) {
        super(maxSize);
        this.a = com.youzan.spiderman.cache.g.i();
        this.b = com.youzan.spiderman.cache.g.h();
    }

    @Override // com.youzan.spiderman.b.h
    protected long b() {
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.spiderman.b.h
    public void a(boolean evicted, String key, Long oldValue, Long newValue) {
        super.a(evicted, key, oldValue, newValue);
        File contentFile = new File(this.b, key);
        File headerFile = new File(this.a, key);
        if (contentFile.exists() && !contentFile.delete()) {
            Logger.e("HtmlDataLruCache", "delete return false, file: " + contentFile, new Object[0]);
        }
        if (headerFile.exists() && !headerFile.delete()) {
            Logger.e("HtmlDataLruCache", "delete return false, file: " + headerFile, new Object[0]);
        }
    }

    void c() {
        a.c(e());
    }

    void d() {
        a((LinkedHashMap) a.c());
    }
}
