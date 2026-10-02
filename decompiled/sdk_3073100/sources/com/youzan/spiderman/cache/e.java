package com.youzan.spiderman.cache;

import java.io.File;

/* JADX INFO: compiled from: CacheSearcher.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class e {
    private static e a = null;
    private final File b = new File(g.f());
    private final File c = new File(g.g());

    public static e a() {
        if (a == null) {
            a = new e();
        }
        return a;
    }

    private e() {
    }

    public File a(CacheUrl cacheUrl) {
        File resFile = null;
        String fileName = cacheUrl.getMd5();
        if (cacheUrl.isScript()) {
            resFile = new File(this.b, fileName);
        } else if (cacheUrl.isImg()) {
            resFile = new File(this.c, fileName);
        }
        if (resFile == null || !resFile.exists()) {
            return null;
        }
        return resFile;
    }
}
