package com.youzan.spiderman.b;

import com.youzan.spiderman.utils.Logger;
import java.io.File;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: ImageLruCache.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class e extends h<String, Long> {
    private static e b;
    private String a;

    static e a() {
        if (b == null) {
            b = new e(c.b());
        }
        return b;
    }

    private e(int maxSize) {
        super(maxSize);
        this.a = com.youzan.spiderman.cache.g.g();
    }

    @Override // com.youzan.spiderman.b.h
    protected long b() {
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.spiderman.b.h
    public void a(boolean evicted, String key, Long oldValue, Long newValue) {
        super.a(evicted, key, oldValue, newValue);
        File resFile = new File(this.a, key);
        if (resFile.exists() && !resFile.delete()) {
            Logger.e("ImageLruCache", "delete return false, file:" + resFile, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.spiderman.b.h
    public long a(String key, Long value) {
        return value.longValue();
    }

    void c() {
        a.a(e());
    }

    void d() {
        a((LinkedHashMap) a.a());
    }
}
