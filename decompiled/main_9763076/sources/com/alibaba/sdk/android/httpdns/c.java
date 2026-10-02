package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.alibaba.sdk.android.httpdns.probe.IPProbeService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c {
    private static c a = new c();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static IPProbeService f57a = com.alibaba.sdk.android.httpdns.probe.d.a(new com.alibaba.sdk.android.httpdns.probe.b() { // from class: com.alibaba.sdk.android.httpdns.c.1
        @Override // com.alibaba.sdk.android.httpdns.probe.b
        public void a(String str, String[] strArr) {
            d dVar;
            if (str == null || strArr == null || strArr.length == 0 || (dVar = (d) c.f58a.get(str)) == null) {
                return;
            }
            d dVar2 = new d(str, strArr, dVar.a(), dVar.b());
            c.f58a.put(str, dVar2);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dVar2.m46a().length; i++) {
                sb.append(dVar2.m46a()[i] + ",");
            }
            h.f("optimized host:" + str + ", ip:" + sb.toString());
            if (com.alibaba.sdk.android.httpdns.b.b.m31a()) {
                com.alibaba.sdk.android.httpdns.b.e eVarM45a = dVar2.m45a();
                if (eVarM45a.a == null || eVarM45a.a.size() <= 0) {
                    com.alibaba.sdk.android.httpdns.b.b.b(eVarM45a);
                } else {
                    com.alibaba.sdk.android.httpdns.b.b.a(eVarM45a);
                }
            }
        }
    });

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static ConcurrentMap<String, d> f58a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static ConcurrentSkipListSet<String> f59a;

    private c() {
        f58a = new ConcurrentHashMap();
        f59a = new ConcurrentSkipListSet<>();
    }

    static c a() {
        return a;
    }

    private IPProbeItem a(String str) {
        List<IPProbeItem> list = e.f62a;
        if (list != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= list.size()) {
                    break;
                }
                if (str.equals(list.get(i2).getHostName())) {
                    return list.get(i2);
                }
                i = i2 + 1;
            }
        }
        return null;
    }

    private boolean a(com.alibaba.sdk.android.httpdns.b.e eVar) {
        return (System.currentTimeMillis() / 1000) - com.alibaba.sdk.android.httpdns.b.c.a(eVar.j) > 604800;
    }

    private boolean a(String str, d dVar) {
        if (dVar == null || dVar.m46a() == null || dVar.m46a().length <= 1 || f57a == null) {
            return false;
        }
        IPProbeItem iPProbeItemA = a(str);
        if (iPProbeItemA == null) {
            return false;
        }
        if (f57a.getProbeStatus(str) == IPProbeService.a.PROBING) {
            f57a.stopIPProbeTask(str);
        }
        h.f("START PROBE");
        f57a.launchIPProbeTask(str, iPProbeItemA.getPort(), dVar.m46a());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        List<com.alibaba.sdk.android.httpdns.b.e> listA = com.alibaba.sdk.android.httpdns.b.b.a();
        String strG = com.alibaba.sdk.android.httpdns.b.b.g();
        for (com.alibaba.sdk.android.httpdns.b.e eVar : listA) {
            if (a(eVar)) {
                com.alibaba.sdk.android.httpdns.b.b.b(eVar);
            } else if (strG.equals(eVar.i)) {
                eVar.j = String.valueOf(System.currentTimeMillis() / 1000);
                d dVar = new d(eVar);
                f58a.put(eVar.h, dVar);
                a(eVar.h, dVar);
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    int m37a() {
        return f58a.size();
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    d m38a(String str) {
        return f58a.get(str);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    ArrayList<String> m39a() {
        return new ArrayList<>(f58a.keySet());
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    void m40a() {
        if (com.alibaba.sdk.android.httpdns.b.b.m31a()) {
            b.a().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.c.2
                @Override // java.lang.Runnable
                public void run() {
                    c.this.b();
                }
            });
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    void m41a(String str) {
        f59a.add(str);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    void m42a(String str, d dVar) {
        f58a.put(str, dVar);
        if (com.alibaba.sdk.android.httpdns.b.b.m31a()) {
            com.alibaba.sdk.android.httpdns.b.e eVarM45a = dVar.m45a();
            if (eVarM45a.a == null || eVarM45a.a.size() <= 0) {
                com.alibaba.sdk.android.httpdns.b.b.b(eVarM45a);
            } else {
                com.alibaba.sdk.android.httpdns.b.b.a(eVarM45a);
            }
        }
        a(str, dVar);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    boolean m43a(String str) {
        return f59a.contains(str);
    }

    void b(String str) {
        f59a.remove(str);
    }

    void clear() {
        f58a.clear();
        f59a.clear();
    }
}
