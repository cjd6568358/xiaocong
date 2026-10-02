package com.youzan.spiderman.b;

import com.youzan.spiderman.cache.CacheUrl;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: compiled from: LruCacheWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class f {
    private static f a = null;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private g e = g.a();
    private e f = e.a();
    private d g = d.a();
    private ExecutorService h = Executors.newFixedThreadPool(1);

    public static f a() {
        if (a == null) {
            a = new f();
        }
        return a;
    }

    private f() {
    }

    public void b() {
        if (!this.b) {
            e();
        }
    }

    private void e() {
        this.h.execute(new Runnable() { // from class: com.youzan.spiderman.b.f.1
            @Override // java.lang.Runnable
            public void run() {
                f.this.f.d();
                f.this.e.d();
                f.this.g.d();
                f.this.b = true;
                f.this.d();
            }
        });
    }

    public void c() {
        f();
    }

    public void d() {
        if (this.b) {
            com.youzan.spiderman.a.c.a().a(new b());
            this.c = true;
        }
    }

    public void a(final CacheUrl cacheUrl, final File file) {
        this.h.execute(new Runnable() { // from class: com.youzan.spiderman.b.f.2
            @Override // java.lang.Runnable
            public void run() {
                if (f.this.b && !f.this.d) {
                    if (cacheUrl.isImg()) {
                        f.this.b(cacheUrl.getMd5(), file);
                    } else if (cacheUrl.isScript()) {
                        f.this.a(cacheUrl.getMd5(), file);
                    }
                }
            }
        });
    }

    public void a(final String hash) {
        this.h.execute(new Runnable() { // from class: com.youzan.spiderman.b.f.3
            @Override // java.lang.Runnable
            public void run() {
                if (f.this.b && !f.this.d) {
                    File contentFile = new File(com.youzan.spiderman.cache.g.h());
                    f.this.c(hash, contentFile);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String fileNameMd5, File file) {
        this.c = true;
        if (this.e.b(fileNameMd5) == null) {
            this.e.b(fileNameMd5, Long.valueOf(file.length()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String fileNameMd5, File file) {
        this.c = true;
        if (this.f.b(fileNameMd5) == null) {
            this.f.b(fileNameMd5, Long.valueOf(file.length()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String htmlHash, File file) {
        this.c = true;
        if (this.g.b(htmlHash) == null) {
            this.g.b(htmlHash, Long.valueOf(file.length()));
        }
    }

    void a(LinkedHashMap<String, Long> map) {
        if (!this.d) {
            this.c = true;
            this.e.a((LinkedHashMap) map);
        }
    }

    void b(LinkedHashMap<String, Long> map) {
        if (!this.d) {
            this.c = true;
            this.f.a((LinkedHashMap) map);
        }
    }

    void c(LinkedHashMap<String, Long> map) {
        if (!this.d) {
            this.c = true;
            this.g.a((LinkedHashMap) map);
        }
    }

    private void f() {
        if (this.c) {
            this.h.execute(new Runnable() { // from class: com.youzan.spiderman.b.f.4
                @Override // java.lang.Runnable
                public void run() {
                    if (f.this.b) {
                        f.this.d = true;
                        try {
                            f.this.f.c();
                            f.this.e.c();
                            f.this.g.c();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            f.this.d = false;
                        }
                    }
                }
            });
            this.c = false;
        }
    }
}
