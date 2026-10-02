package com.youzan.spiderman.b;

import com.youzan.spiderman.utils.Logger;
import java.io.File;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: CheckCacheJob.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class b extends com.youzan.spiderman.a.a {
    b() {
    }

    @Override // com.youzan.spiderman.a.a
    public void a() throws Throwable {
        b();
        c();
        d();
    }

    @Override // com.youzan.spiderman.a.a
    public void a(Throwable throwable) {
        Logger.e("CheckCacheJob", throwable);
    }

    private void b() {
        String[] files;
        g scriptLruCache = g.a();
        LinkedHashMap<String, Long> diffMap = new LinkedHashMap<>();
        File fileDir = new File(com.youzan.spiderman.cache.g.f());
        if (fileDir.exists() && fileDir.isDirectory() && (files = fileDir.list()) != null) {
            for (String fileName : files) {
                if (!scriptLruCache.a(fileName)) {
                    File file = new File(fileDir, fileName);
                    diffMap.put(fileName, Long.valueOf(file.length()));
                }
            }
        }
        if (!diffMap.isEmpty()) {
            f.a().a(diffMap);
        }
    }

    private void c() {
        String[] files;
        e imageLruCache = e.a();
        LinkedHashMap<String, Long> diffMap = new LinkedHashMap<>();
        File fileDir = new File(com.youzan.spiderman.cache.g.g());
        if (fileDir.exists() && fileDir.isDirectory() && (files = fileDir.list()) != null) {
            for (String fileName : files) {
                if (!imageLruCache.a(fileName)) {
                    File file = new File(fileDir, fileName);
                    diffMap.put(fileName, Long.valueOf(file.length()));
                }
            }
        }
        if (!diffMap.isEmpty()) {
            f.a().b(diffMap);
        }
    }

    private void d() {
        String[] files;
        d htmlDataLruCache = d.a();
        LinkedHashMap<String, Long> diffMap = new LinkedHashMap<>();
        File fileDir = new File(com.youzan.spiderman.cache.g.h());
        if (fileDir.exists() && fileDir.isDirectory() && (files = fileDir.list()) != null) {
            for (String fileName : files) {
                if (!htmlDataLruCache.a(fileName)) {
                    File file = new File(fileDir, fileName);
                    diffMap.put(fileName, Long.valueOf(file.length()));
                }
            }
        }
        if (!diffMap.isEmpty()) {
            f.a().c(diffMap);
        }
    }
}
