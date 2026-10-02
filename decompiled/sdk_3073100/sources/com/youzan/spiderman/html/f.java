package com.youzan.spiderman.html;

import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: FetchingPool.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class f {
    private ConcurrentHashMap<String, e> a;

    /* JADX INFO: compiled from: FetchingPool.java */
    private static class a {
        static f a = new f();
    }

    public static f a() {
        return a.a;
    }

    private f() {
        this.a = new ConcurrentHashMap<>();
    }

    public boolean a(o htmlUrl) {
        return this.a.containsKey(htmlUrl.c());
    }

    public synchronized e b(o htmlUrl) {
        e fetchSession;
        fetchSession = this.a.get(htmlUrl.c());
        if (fetchSession == null) {
            fetchSession = new e(htmlUrl);
            this.a.put(htmlUrl.c(), fetchSession);
        } else {
            long now = System.currentTimeMillis();
            if (fetchSession.a(now)) {
                fetchSession.b(now);
            }
        }
        return fetchSession;
    }
}
